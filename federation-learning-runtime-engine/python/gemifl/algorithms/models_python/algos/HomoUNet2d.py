import pickle
import torch
from importlib import import_module
import torch.nn as nn
from torchvision.models import resnet18
import numpy as np
import torch.optim as optim
from torch.autograd import Variable
from gemifl.utils.metrics_module import MLMetrics
import torch.nn.functional as F

def dice_loss(pred,target):
    numerator = 2 * torch.sum(pred * target)
    denominator = torch.sum(pred + target)
    return  1 - (numerator + 1) / (denominator + 1)


def bce_dice_loss(pred,target):

    bce_loss = torch.nn.BCELoss()

    return bce_loss(pred,target) + dice_loss(pred,target)


def my_dice(y_pred,y_true,device='cpu',thr=0.5):
    thr = Variable(torch.Tensor([thr])).to(device)
    y_pred = (y_pred > thr).float() * 1
    smooth = 1e-4
    iflat = y_pred.view(-1)
    tflat = y_true.view(-1)
    intersection = (iflat * tflat).sum()
    return ((2.0 * intersection + smooth) / (iflat.sum() + tflat.sum() + smooth))

class DoubleConv(nn.Module):
    """(convolution => [BN] => ReLU) * 2"""

    def __init__(self, in_channels, out_channels):
        super(DoubleConv, self).__init__()
        self.conv = nn.Sequential(
            nn.Conv2d(in_channels, out_channels, kernel_size=3, padding=1),
            nn.BatchNorm2d(out_channels),
            nn.ReLU(inplace=True),
            nn.Conv2d(out_channels, out_channels, kernel_size=3, padding=1),
            nn.BatchNorm2d(out_channels),
            nn.ReLU(inplace=True)
        )

    def forward(self, x):
        return self.conv(x)


class Up(nn.Module):
    """Upsampling + convolution blocks"""

    def __init__(self, in_channels, out_channels, bilinear=True):
        super(Up, self).__init__()

        if bilinear:
            self.up = nn.Upsample(scale_factor=2, mode='bilinear', align_corners=True)
        else:
            self.up = nn.ConvTranspose2d(in_channels // 2, in_channels // 2, kernel_size=2, stride=2)

        self.conv = DoubleConv(in_channels, out_channels)

    def forward(self, x1, x2):
        x1 = self.up(x1)

        diffY = x2.size()[2] - x1.size()[2]
        diffX = x2.size()[3] - x1.size()[3]
        x1 = F.pad(x1, [diffX // 2, diffX - diffX // 2,
                        diffY // 2, diffY - diffY // 2])

        x = torch.cat([x2, x1], dim=1)
        return self.conv(x)


class Encoder(nn.Module):
    def __init__(self, pretrained=True):
        super(Encoder, self).__init__()
        resnet = resnet18(pretrained=pretrained)
        self.conv1 = resnet.conv1
        self.maxpool = resnet.maxpool
        self.layer1 = resnet.layer1
        self.layer2 = resnet.layer2
        self.layer3 = resnet.layer3
        self.layer4 = resnet.layer4

    def forward(self, x):
        x1 = self.conv1(x)
        x2 = self.layer1(self.maxpool(x1))
        x3 = self.layer2(x2)
        x4 = self.layer3(x3)
        x5 = self.layer4(x4)

        return [x1, x2, x3, x4, x5]


class UNet(nn.Module):
    def __init__(self, n_classes, pretrained):
        super(UNet, self).__init__()
        self.encoder = Encoder(pretrained=pretrained)

        self.center = DoubleConv(512, 512)

        self.up4 = Up(768, 256)
        self.up3 = Up(384, 128)
        self.up2 = Up(192, 64)
        self.up1 = Up(128, 64)
        self.up0 = nn.Upsample(scale_factor=2, mode='bilinear', align_corners=True)
        self.outc = nn.Conv2d(64, n_classes, kernel_size=1)

    def forward(self, x):
        x1, x2, x3, x4, x5 = self.encoder(x)
        x5 = self.center(x5)

        x4 = self.up4(x5, x4)
        x3 = self.up3(x4, x3)
        x2 = self.up2(x3, x2)
        x1 = self.up1(x2, x1)
        x0 = self.up0(x1)

        out = self.outc(x0)
        out = torch.sigmoid(out)

        return out

class HomoUNet2d:
    def __init__(self, config, logger):
        self.config = config
        self.logger = logger
        self.get_default_variable()
        if self.train_data_source:
            self.get_data()
        self.init_model()

    def get_default_variable(self):
        self.my_node_name = self.config.get_root_value("my_name")
        # for data
        self.data_type = self.config.get_model_parameter("data_type")
        self.train_data_source, self.val_data_source = self.config.get_data_setting(self.my_node_name)
        # for model config
        self.train_batch_size = self.config.get_model_parameter("train_batch_size")
        self.valid_batch_size = self.config.get_model_parameter("valid_batch_size")
        self.model_save = self.config.get_model_parameter("model_save")
        self.weight_save = self.config.get_model_parameter("weight_save")
        # for model evaluation
        self.best_loss = np.inf
        self.best_model_wts = None
        self.bestauc = -1

    def get_data(self):
        model_lib = import_module(f"gemifl.utils.preprocess")
        args = {'traindir': self.train_data_source, 'valdir': self.val_data_source,
                'train_batch_size': self.train_batch_size, 'valid_batch_size': self.valid_batch_size}
        preprocessor = getattr(model_lib, self.data_type)(**args)
        trainloader = preprocessor.get_dataloader("train")
        valloader = preprocessor.get_dataloader("val")
        self.dataloaders = {"train": trainloader, "val": valloader}
        self.data_sizes = {x: len(self.dataloaders[x].sampler) for x in ['train', 'val']}

    def get_device(self):
        nodes_parameter = self.config.get_nodes_parameter(self.my_node_name)
        userdevice = nodes_parameter.get("device", "cpu").lower()

        if torch.cuda.is_available() and userdevice != 'cpu':
            try:
                self.device = torch.device(userdevice)
            except:
                self.device = torch.device("cuda:0")
            self.logger.info("Training on GPU...")
        else:
            self.device = torch.device("cpu")
            self.logger.info("Training on CPU...")

    def init_model(self):
        net_config = self.config.get_model_parameter("net_config")
        self.model = UNet(**net_config)
        self.get_device()
        self.model.to(self.device)
        # Specify optimizer which performs Gradient Descent
        self.optimizer = optim.Adam(self.model.parameters(), lr=self.config.get_model_parameter("lr"))
        # Decay LR by a factor of 0.1 every 7 epochs
        self.exp_lr_scheduler = optim.lr_scheduler.StepLR(self.optimizer, step_size=7, gamma=0.1)

    def get_weights(self):
        weight_dict = {}

        for name, data in self.model.state_dict().items():
            data_arr = data.cpu().numpy()
            data_arr_flatten = data_arr.flatten()
            data_arr_np = np.array([float(i) for i in data_arr_flatten])
            weight_dict[name] = [data_arr_np, data_arr.shape]
        return weight_dict

    def set_weights(self, parameters):
        weight_input = {}

        for name, data in parameters.items():
            weight_input[name] = torch.tensor(np.reshape(parameters[name][0], parameters[name][1]))

        self.model.load_state_dict(weight_input)

    def set_best_weight(self):
        if self.best_model_wts:
            self.set_weights(self.best_model_wts)

    def train_one_epoch(self):
        self.model.train()
        current_loss = 0.0
        current_corrects = 0
        val_kappa = list()
        phase = "train"
        for inputs, labels in self.dataloaders[phase]:
            inputs = inputs.to(self.device)
            labels = labels.to(self.device)

            # We need to zero the gradients in the Cache.
            self.optimizer.zero_grad()

            # Time to carry out the forward training poss
            # We only need to log the loss stats if we are in training phase
            with torch.set_grad_enabled(phase == 'train'):
                outputs = self.model(inputs)
                loss = bce_dice_loss(outputs, labels.float().unsqueeze(1))
                # backward + optimize only if in training phase
                if phase == 'train':
                    loss.backward()
                    self.optimizer.step()
            if phase == 'train':
                self.exp_lr_scheduler.step()

            # We want variables to hold the loss statistics
            current_loss += loss.item() * inputs.size(0)
            # current_corrects += torch.sum(preds == labels.data)
            # val_kappa.append(cohen_kappa_score(preds.cpu().numpy(), labels.data.cpu().numpy()))
        epoch_loss = current_loss / self.data_sizes[phase]
        # epoch_acc = current_corrects.double() / self.data_sizes[phase]
        if epoch_loss < self.best_loss:
            self.best_loss = epoch_loss
            self.best_model_wts = self.get_weights()

    def evaluate(self, task_type):
        self.model.eval()

        phase = "val"
        labels_all = []
        preds_all = []
        probs_all = []
        for inputs, labels in self.dataloaders[phase]:
            inputs = inputs.to(self.device)
            labels = labels.to(self.device)
            outputs = self.model(inputs)
            labels_all += labels
            preds_all += outputs

        metricsobj = MLMetrics(torch.cat(labels_all), torch.cat(preds_all), [])
        current_score = metricsobj.cal(task_type)
        if current_score["best_score"] > self.bestauc:
            self.bestauc = current_score["best_score"]
        else:
            current_score["best_score"] = self.bestauc
        return current_score

    def dumpfile(self):
        with open(self.model_save, 'wb') as mf:
            pickle.dump(self.model, mf)
        torch.save(self.model.state_dict(), self.weight_save)

