from xml.dom.minidom import parse
import xml.dom.minidom
import pandas as pd
import os
import sys
import re
from sklearn.model_selection import train_test_split
from torch import utils
from sklearn import preprocessing
import argparse
import xmltodict


def str_clear(answer_s):
    answer_s_li = answer_s.split(' ')
    tmp_str = [
        item.replace("\n", "")
        for item in answer_s_li
        if item != "" or item != "\n" or item != "\t" or item != "-"
    ]

    tmp_str = " ".join(tmp_str).strip().replace("\t", "").replace("\s+- ", " ")

    tmp_str = re.sub(r"\s+ ", " ", tmp_str)
    tmp_str = re.sub(r"-\s+", "", tmp_str)

    return tmp_str[:150]


def extract_xml(xml_file):
    sources = []
    questions = []
    answers = []
    question_types = []
    type2dict = {}

    with open(xml_file, "r") as f:
        try:
            xml2dict = xmltodict.parse(f.read())
            qa_pairs = xml2dict["Document"]["QAPairs"]["QAPair"]
            source = xml2dict["Document"]['@source']

            if type(qa_pairs) == 'dict':
                qa_pairs = [qa_pairs]

            if isinstance(qa_pairs, dict):
                qa_pairs = [qa_pairs]

            for tmp_pair in qa_pairs:

                try:
                    current_qtype = str_clear(tmp_pair["Question"]["@qtype"])
                    current_answer = str_clear(tmp_pair["Answer"])
                    current_question = str_clear(tmp_pair["Question"]["#text"])

                    sources.append(source)
                    questions.append(current_question)
                    answers.append(current_answer)
                    question_types.append(current_qtype)
                except:
                    print("xml_file1:", xml_file)
                    sys.exit(0)
        except:
            print("xml_file2:", xml_file)

    return sources, questions, answers, question_types


def extract_dir(xmls_dir):
    xml_df = pd.DataFrame(
        columns=["source", "question", "answer", "question_type"])
    xml_files = os.listdir(xmls_dir)

    sources = []
    questions = []
    answers = []
    question_types = []
    files = []

    for tmp_file in xml_files:
        tmp_path = os.path.join(xmls_dir, tmp_file)
        tmp_sources, tmp_questions, tmp_answers, tmp_question_types = extract_xml(
            tmp_path)

        sources += tmp_sources
        questions += tmp_questions
        answers += tmp_answers
        question_types += tmp_question_types
        files.extend([tmp_file] * len(tmp_question_types))

    xml_df = pd.DataFrame({
        "source": sources,
        "question": questions,
        "answer": answers,
        "question_type": question_types,
        "files": files
    })
    return xml_df


def extract_dirs(root_path, output_dir):
    dirs = [
        "1_CancerGov_QA", "2_GARD_QA", "3_GHR_QA", "4_MPlus_Health_Topics_QA",
        "5_NIDDK_QA", "6_NINDS_QA", "7_SeniorHealth_QA", "8_NHLBI_QA_XML",
        "9_CDC_QA"
    ]

    for tmp_dir in dirs:
        current_dir = os.path.join(root_path, tmp_dir)
        current_df = extract_dir(current_dir)

        enc = preprocessing.LabelEncoder()

        current_df["question_type"] = enc.fit_transform(
            current_df["question_type"])

        train_df = current_df.sample(frac=0.8)
        test_df = current_df.drop(train_df.index)

        save_dir = os.path.join(output_dir, re.sub("\d+_", "", tmp_dir))

        if not os.path.exists(save_dir):
            os.makedirs(save_dir)

        train_df.to_csv(save_dir + "/train.csv",
                        sep="\t",
                        index=False,
                        header=False)
        test_df.to_csv(save_dir + "/dev.csv",
                       sep="\t",
                       index=False,
                       header=False)


if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument("--xml_dir",
                        type=str,
                        help="the path of xml files",
                        default="")
    parser.add_argument("--output_dir",
                        type=str,
                        help="the path of output files")

    args = parser.parse_args()
    extract_dirs(args.xml_dir, args.output_dir)
