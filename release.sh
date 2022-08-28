#!/bin/bash

gpg -o /tmp/dummy --sign .gitignore \
 && nextVersion=$(npx git-changelog-command-line --print-next-version) \
 && ./mvnw versions:set -DnewVersion=$nextVersion-SNAPSHOT \
 && ./mvnw release:prepare release:perform -B -DperformRelease=true \
 && npx git-changelog-command-line -of CHANGELOG.md \
 && git commit -a -m "chore: updating changelog" \
 && git push \
 || git clean -f && git checkout pom.xml