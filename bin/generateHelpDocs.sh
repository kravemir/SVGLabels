#!/usr/bin/env bash

SCRIPT_DIRECTORY="$(dirname "$0")"
PROJECT_DIRECTORY="$(dirname "${SCRIPT_DIRECTORY}")"

cd "${PROJECT_DIRECTORY}"

echo -e "# \`svg-labels\` help\n\n\`\`\`" > docs/help.md
# TODO: use correct version
java -jar  ./tool/build/libs/tool-0.2.0-SNAPSHOT-executable.jar --help >> docs/help.md
echo -e "\`\`\`" >> docs/help.md

echo -e "\nCommand \`tile\`:\n\`\`\`" >> docs/help.md
# TODO: use correct version
java -jar  ./tool/build/libs/tool-0.2.0-SNAPSHOT-executable.jar tile --help >> docs/help.md
echo -e "\`\`\`" >> docs/help.md

echo -e "\nCommand \`instance\`:\n\`\`\`" >> docs/help.md
# TODO: use correct version
java -jar  ./tool/build/libs/tool-0.2.0-SNAPSHOT-executable.jar instance --help >> docs/help.md
echo -e "\`\`\`" >> docs/help.md
