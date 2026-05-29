# GHCR deploy checklist

After the first successful `build-and-push` workflow:

1. Open https://github.com/users/YOUR_USER/packages (or org packages).
2. For each `profitability-*` package: **Package settings** → **Change visibility** → **Public** (required for pull without `docker login` on VPS).
3. On VPS, set in `.env.prod`:
   - `IMAGE_REGISTRY=your-github-username` (lowercase)
   - `IMAGE_TAG=latest` (or `sha-abcdef1` to pin)
4. First manual pull:
   ```bash
   docker compose -f docker-compose.prod.yml --env-file .env.prod pull
   docker compose -f docker-compose.prod.yml --env-file .env.prod up -d
   ```

Rollback: set `IMAGE_TAG=sha-<commit>` in `.env.prod`, `pull`, rolling `up --no-deps` per service.
