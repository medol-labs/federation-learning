import unittest
import re

from docx import Document

from sdd_interface_terms import replace_labels
from sdd_interface_ids import PREFIX, identifier_aliases, owner_code
from sdd_interfaces import AGENT, AGG, GROUPS, PLATFORM, SUPPORT, TRAIN, group_operation, interface_data, permission


class InterfaceDesignTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.items = interface_data()
        cls.operations = {
            (route["provider"], route["method"], route["path"]): (item, route)
            for item in cls.items
            for route, _ in item["contracts"]
        }

    def test_identifiers_and_diagram_group_coverage(self):
        self.assertEqual(len(self.operations), sum(len(item["contracts"]) for item in self.items))
        self.assertEqual(len(self.items), len({item["id"] for item in self.items}))
        self.assertTrue(all(re.fullmatch(rf"{PREFIX}-{owner_code(item)}-[A-Z][A-Z0-9]*", item["id"]) for item in self.items))
        self.assertLessEqual({item["id"] for item in self.items}, set(identifier_aliases().values()))
        self.assertEqual(set(GROUPS), {group for item in self.items for group in item["groups"]})

    def test_cross_site_and_file_contracts(self):
        links = [
            (AGENT, "POST", "/roundexecution/receiveparticipantexecutionplan", "G04"),
            (PLATFORM, "POST", "/traininground/submitmodelupdatesubmission", "G05"),
            (SUPPORT, "POST", "/uploadedfile/uploadfile/file", "G07"),
            (PLATFORM, "POST", "/modelartifact/downloadmodelartifact", "G01"),
            (SUPPORT, "GET", "/api/files/{fileId}/content", "G03"),
        ]
        ids = set()
        for provider, method, path, group in links:
            item, route = self.operations[provider, method, path]
            self.assertIn(group, group_operation(route))
            ids.add(item["id"])
        self.assertEqual(len(ids), len(links))
        _, download = self.operations[PLATFORM, "POST", "/modelartifact/downloadmodelartifact"]
        _, file_read = self.operations[SUPPORT, "GET", "/api/files/{fileId}/content"]
        self.assertIn("download_model_artifact:execute", permission(download))
        self.assertEqual(permission(file_read), "授权项：*:*。")

    def test_engines_with_shared_paths_have_distinct_contracts(self):
        train, _ = self.operations[TRAIN, "POST", "/jobs"]
        aggregate, _ = self.operations[AGG, "POST", "/jobs"]
        self.assertNotEqual(train["id"], aggregate["id"])
        self.assertEqual(train["groups"], ["G09"])
        self.assertEqual(aggregate["groups"], ["G08"])
        self.assertIn((TRAIN, "POST", "/jobs/{jobId}/cancel"), self.operations)
        self.assertIn((AGG, "GET", "/jobs/{jobId}/artifacts/{artifactName}"), self.operations)

    def test_global_model_evaluation_uses_the_existing_aggregation_contract(self):
        engine, _ = self.operations[AGG, "POST", "/jobs"]
        evaluation, _ = self.operations[AGG, "POST", "/evaluations"]
        self.assertEqual(engine["id"], evaluation["id"])
        self.assertEqual(evaluation["id"], "LBLS-JK-NB-AGGENG-AGGJOB")
        self.assertNotIn((TRAIN, "POST", "/evaluations"), self.operations)

    def test_contract_and_operation_labels_are_localized(self):
        contract = next(item for item in self.items if item["id"] == "LBLS-JK-NB-SN-MARKALLREAD")
        self.assertEqual(contract["name"], "共享支撑服务标记所有系统通知为已读接口")
        self.assertEqual(contract["contracts"][0][0]["purpose"], "标记所有系统通知为已读")
        self.assertTrue(all(not re.search(r"[A-Z][a-z]+(?: [A-Z][a-z]+)+", item["name"]) for item in self.items))

    def test_previous_interface_ids_resolve_to_purpose_ids(self):
        aliases = identifier_aliases(include_numbered=True)
        self.assertEqual(aliases["LBLS-JK-NB-SN-001"], "LBLS-JK-NB-SN-MARKALLREAD")
        self.assertEqual(aliases["E-21B2B845"], "LBLS-JK-NB-SN-MARKALLREAD")

    def test_localization_preserves_adjacent_route_runs(self):
        paragraph = Document().add_paragraph()
        paragraph.add_run("查询System Notification ")
        paragraph.add_run("Catalog列表：")
        route = "GET /systemnotification/systemnotificationcatalog/{id}"
        paragraph.add_run(route)
        source = "System Notification Catalog"
        replace_labels(paragraph._p, re.compile(re.escape(source)), {source: "系统通知目录"})
        self.assertEqual(paragraph.text, "查询系统通知目录列表：" + route)
        self.assertEqual(paragraph.runs[-1].text, route)


if __name__ == "__main__":
    unittest.main()
