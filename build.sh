#!/usr/bin/env bash
set -e
cd "$(dirname "$0")/backend"
if command -v mvn >/dev/null 2>&1; then mvn test; else echo "Maven is required. Install Maven 3.9+ and Java 17+, then run: mvn test"; exit 1; fi
