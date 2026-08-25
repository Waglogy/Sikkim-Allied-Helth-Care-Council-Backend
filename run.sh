#!/usr/bin/env bash
# Loads local environment variables from .env (R2 credentials, JWT secret, …)
# and starts the Spring Boot backend. The .env file is git-ignored.
set -euo pipefail
cd "$(dirname "$0")"

if [ -f .env ]; then
  set -a
  # shellcheck disable=SC1091
  . ./.env
  set +a
fi

exec ./gradlew bootRun "$@"
