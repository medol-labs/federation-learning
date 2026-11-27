import unittest
import re

from sdd_detail import detail_markdown, detail_translations
from sdd_identifiers import normalize_identifiers


class DetailTranslationTests(unittest.TestCase):
    def test_states_reuse_glossary_display_titles(self):
        model = {"transitions": [{"from": "PlanReceived", "to": "PlanAccepted"}]}

        translations = detail_translations(model)

        self.assertEqual(translations["PlanReceived"], "计划已接收")
        self.assertEqual(translations["PlanAccepted"], "计划已接受")

    def test_detail_localizes_business_terms_without_changing_technical_names(self):
        detail = detail_markdown()

        self.assertIn("追加业务结果事件：所有系统通知已标记为已读", detail)
        self.assertNotIn("All System Notifications Read", detail)
        self.assertNotIn("PlanReceived", detail)
        self.assertIn("FedAvg", detail)
        self.assertIn("PyTorch state_dict", detail)
        self.assertIn("POST /evaluations", detail)
        self.assertIn("LBLS-JK-NB-AGGENG-AGGJOB", detail)

    def test_design_ids_follow_heading_ownership(self):
        parents = {2: "LBLS-GN-LBXX"}
        ids = set()
        operations = set()
        for match in re.finditer(r"^(#{3,5}) (.+)/([A-Z0-9-]+)$", detail_markdown(), re.M):
            level, title, identifier = len(match[1]), match[2], match[3]
            parent, suffix = identifier.rsplit("-", 1)
            self.assertEqual(parent, parents[level - 1], title)
            self.assertNotIn(identifier, ids)
            ids.add(identifier)
            parents[level] = identifier
            if re.fullmatch(r"F\d{4}", suffix):
                self.assertNotIn(suffix, operations)
                operations.add(suffix)

        self.assertEqual(len(operations), 192)
        self.assertIn("LBLS-GN-LBXX-SN-F0175", ids)
        self.assertIn("LBLS-GN-LBXX-TRAIN-SEL-F0066", ids)

    def test_shared_identifiers_preserve_other_modules_and_interface_ids(self):
        source = "FL-SU-UI-P；FL-U-C-TRAIN、FL-U-SEL；LBLS-GN-SZR-HOME；E-5355FEFC；G09"
        expected = ("LBLS-GN-LBXX-DEP-UI-P；LBLS-GN-LBXX-TRAIN、LBLS-GN-LBXX-TRAIN-SEL；"
                    "LBLS-GN-SZR-HOME；E-5355FEFC；G09")
        self.assertEqual(normalize_identifiers(source), expected)
        self.assertEqual(normalize_identifiers(expected), expected)
        self.assertNotRegex(detail_markdown(), r"\bFL-(?:U|SU)-")
