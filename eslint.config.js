import globals from "globals"
import pluginJs from "@eslint/js"
import prettierConfig from "eslint-config-prettier"
import prettierPlugin from "eslint-plugin-prettier"

export default [
  {
    languageOptions: {
      globals: globals.browser,
    },
  },
  pluginJs.configs.recommended,
  prettierConfig,
  {
    plugins: {
      prettier: prettierPlugin,
    },
    rules: {
      "prettier/prettier": [
        "error",
        {
          semi: false,
        },
      ],
      semi: ["error", "never"], // ESLint rule to ensure no semicolons
    },
  },
  {
    ignores: [
      "src/main/resources/static/js/",
      "build/resources/main/static/js",
    ],
  },
]
