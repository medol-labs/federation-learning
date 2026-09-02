import os
import unittest
from unittest.mock import patch

from gemifl.runtime.plugins.io import resolve_file_access_url


class FileAccessUrlTest(unittest.TestCase):
    def test_keeps_file_url_when_access_base_is_not_configured(self):
        with patch.dict(os.environ, {}, clear=True):
            self.assertEqual(
                "http://support:8080/api/files/file-1/content",
                resolve_file_access_url("http://support:8080/api/files/file-1/content"),
            )

    def test_replaces_origin_and_keeps_path_and_query(self):
        with patch.dict(
            os.environ,
            {"RUNTIME_ENGINE_FILE_ACCESS_BASE_URL": "http://host.docker.internal:8080/"},
            clear=True,
        ):
            self.assertEqual(
                "http://host.docker.internal:8080/api/files/file-1/content?download=true",
                resolve_file_access_url("http://localhost:8080/api/files/file-1/content?download=true"),
            )


if __name__ == "__main__":
    unittest.main()
