# Deployment

CI/CD builds the executable jar on GitHub's runner, packages it into an ARM64
Docker image, ships it to the server over SSH, and runs it as `sahc-backend`
on port **8085** (→ container 8080), on the shared `waglogy-net` Docker network
so it reaches the `sansaar_db` Postgres container by name.

Push to `master` triggers `.github/workflows/deploy.yml`.

## 1. One-time server prep — create the database

The app uses the existing `sansaar_db` Postgres container. Create a dedicated
database + owner (replace the password):

```bash
docker exec -it sansaar_db psql -U postgres -c "CREATE USER sahc WITH PASSWORD 'CHANGE_ME_STRONG';"
docker exec -it sansaar_db psql -U postgres -c "CREATE DATABASE sikkim_allied_health_care_council OWNER sahc;"
```

> `-U postgres` is the default superuser for the `postgres` image. If this
> server set a custom `POSTGRES_USER`, use that instead.

`ddl-auto: update` creates the tables automatically on first boot (the owner
has the needed schema privileges).

## 2. GitHub repository secrets

Settings → Secrets and variables → Actions → **New repository secret**:

| Secret | Value |
|--------|-------|
| `SSH_PRIVATE_KEY` | Private key that can SSH into the server |
| `SERVER_HOST` | Server IP / hostname |
| `SERVER_USER` | SSH user (must be able to run `docker`) |
| `DB_URL` | full JDBC URL **with credentials embedded**: `jdbc:postgresql://sansaar_db:5432/sikkim_allied_health_care_council?user=sahc&password=YOUR_PASSWORD` |
| `JWT_SECRET` | long random string (≥ 32 bytes) |
| `CORS_ALLOWED_ORIGINS` | frontend origin(s), comma-separated, e.g. `https://your-frontend` |
| `R2_ENDPOINT` | Cloudflare R2 S3 endpoint |
| `R2_BUCKET` | R2 bucket name |
| `R2_ACCESS_KEY_ID` | R2 access key |
| `R2_SECRET_ACCESS_KEY` | R2 secret key |
| `R2_PUBLIC_BASE_URL` | R2 public URL (r2.dev or custom domain) — or leave blank to serve via `/api/files/**` |

## 3. Deploy

Push to `main`. After it's up:

```bash
curl http://<SERVER_HOST>:8085/api/notification   # should return JSON
docker logs -f sahc-backend
```

## 4. Point the frontend at it

Set the frontend's `NEXT_PUBLIC_API_BASE_URL` to `http://<SERVER_HOST>:8085`
(or the Caddy domain if you later put one in front), and make sure that origin
is included in `CORS_ALLOWED_ORIGINS`.

## Notes

- Uses the existing `waglogy-net` network (same one the other backends use to
  reach `sansaar_db`), so no network setup is needed.
- To front the API with Caddy on a domain instead of the raw port, add a
  reverse-proxy entry to `sahc-backend:8080` (Caddy already shares
  `waglogy-net`), and you can drop `-p 8085:8080` from the run command.
