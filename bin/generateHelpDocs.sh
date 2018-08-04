#!/usr/bin/env bash

SCRIPT_DIRECTORY="$(dirname "$0")"
PROJECT_DIRECTORY="$(dirname "${SCRIPT_DIRECTORY}")"

cd "${PROJECT_DIRECTORY}"

TOOL_JAR=`find ./tool/build/libs -name '*-executable.jar'`

echo -e "# \`svg-labels\` help\n\n\`\`\`" > docs/help.md
java -jar  "${TOOL_JAR}" --help >> docs/help.md
echo -e "\`\`\`" >> docs/help.md

echo -e "\nCommand \`tile\`:\n\`\`\`" >> docs/help.md
java -jar  "${TOOL_JAR}" tile --help >> docs/help.md
echo -e "\`\`\`" >> docs/help.md

echo -e "\nCommand \`instance\`:\n\`\`\`" >> docs/help.md
java -jar  "${TOOL_JAR}" instance --help >> docs/help.md
echo -e "\`\`\`" >> docs/help.md
