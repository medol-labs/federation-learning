# Dictionary Init

Standalone dictionary initializer. It does not read `codegen-model.json` and is not wired into the code generator.

Run a dry run:

```bash
node init-dictionaries.mjs --base-url http://localhost:8080 --dry-run
```

Initialize through the dictionary backend API:

```bash
node init-dictionaries.mjs --base-url http://localhost:8080
```

Useful options:

```bash
node init-dictionaries.mjs --data dictionaries.json
node init-dictionaries.mjs --force
node init-dictionaries.mjs --header "Authorization: Bearer <token>"
node init-dictionaries.mjs --register-path /dictionary/registerdictionary
node init-dictionaries.mjs --add-value-path /dictionaryvalue/adddictionaryvalue
```
