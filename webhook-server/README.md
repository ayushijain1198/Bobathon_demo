# GitHub Webhook Server for Automated PR Reviews

## Overview

This webhook server automatically posts code review comments on Pull Requests when commits are pushed. It works **without GitHub Actions** by using GitHub webhooks.

## How It Works

```
1. You push commits to a PR
2. GitHub sends webhook event to your server
3. Server analyzes the code changes
4. Server posts review comment on the PR
```

## Setup Instructions

### Step 1: Install Dependencies

```bash
cd webhook-server
npm install
```

### Step 2: Create GitHub Personal Access Token

1. Go to: https://github.ibm.com/settings/tokens
2. Click "Generate new token" → "Generate new token (classic)"
3. Give it a name: "PR Review Webhook"
4. Select scopes:
   - ✅ `repo` (Full control of private repositories)
   - ✅ `write:discussion` (Read and write team discussions)
5. Click "Generate token"
6. **Copy the token** (you won't see it again!)

### Step 3: Configure Environment Variables

Create a `.env` file in the `webhook-server` directory:

```bash
# GitHub Configuration
GITHUB_TOKEN=your_github_token_here
GITHUB_API_URL=https://github.ibm.com/api/v3

# Webhook Configuration
WEBHOOK_SECRET=your_random_secret_here
PORT=3000
```

Generate a random webhook secret:
```bash
node -e "console.log(require('crypto').randomBytes(32).toString('hex'))"
```

### Step 4: Start the Server

```bash
# Development mode (auto-restart on changes)
npm run dev

# Production mode
npm start
```

The server will start on `http://localhost:3000`

### Step 5: Expose Server to Internet

GitHub needs to reach your server. Options:

#### Option A: Using ngrok (Easiest for testing)

```bash
# Install ngrok
brew install ngrok  # macOS
# or download from https://ngrok.com

# Start ngrok
ngrok http 3000
```

Copy the HTTPS URL (e.g., `https://abc123.ngrok.io`)

#### Option B: Deploy to Cloud

Deploy to:
- Heroku
- AWS Lambda
- Google Cloud Run
- Azure Functions
- Your own server

### Step 6: Configure GitHub Webhook

1. Go to your repository: https://github.ibm.com/Pradumnya-Zendphale/bob_demo
2. Click **Settings** → **Webhooks** → **Add webhook**
3. Configure:
   - **Payload URL**: `https://your-server-url/webhook`
   - **Content type**: `application/json`
   - **Secret**: (paste the WEBHOOK_SECRET from .env)
   - **Which events**: Select "Let me select individual events"
     - ✅ Pull requests
   - **Active**: ✅ Checked
4. Click **Add webhook**

### Step 7: Test It!

1. Create a new branch:
   ```bash
   git checkout -b test-webhook
   ```

2. Make a change (add System.out.println somewhere):
   ```bash
   echo 'System.out.println("test");' >> src/main/java/com/example/demo/DemoApplication.java
   git add .
   git commit -m "Test webhook"
   git push origin test-webhook
   ```

3. Create a PR on GitHub

4. **Watch the magic!** 🎉
   - The webhook server receives the event
   - Analyzes your code
   - Posts a review comment automatically

## What It Checks

The server automatically checks for:

- ✅ `System.out.println` usage
- ✅ `TODO` comments
- ✅ `printStackTrace` calls
- ✅ Code quality issues
- ✅ Best practice violations

## Server Logs

Watch the server logs to see it working:

```
Webhook server listening on port 3000
GitHub API URL: https://github.ibm.com/api/v3
Waiting for webhook events...

Received pull_request event
Analyzing PR #3 in Pradumnya-Zendphale/bob_demo
Posted review comment on PR #3
```

## Troubleshooting

### Webhook not triggering

1. Check webhook deliveries in GitHub:
   - Settings → Webhooks → Click on your webhook
   - Check "Recent Deliveries"
   - Look for errors

2. Verify server is running:
   ```bash
   curl http://localhost:3000/health
   ```

3. Check ngrok is running (if using ngrok):
   ```bash
   curl https://your-ngrok-url/health
   ```

### Authentication errors

- Verify GITHUB_TOKEN has correct permissions
- Check token hasn't expired
- Ensure GITHUB_API_URL is correct for IBM GitHub Enterprise

### Webhook signature verification fails

- Ensure WEBHOOK_SECRET matches in both:
  - `.env` file
  - GitHub webhook configuration

## Customization

Edit `server.js` to:
- Add more code checks
- Customize review messages
- Add different analysis rules
- Integrate with other tools

## Security Notes

- ✅ Webhook signature verification enabled
- ✅ Uses HTTPS (via ngrok or cloud deployment)
- ✅ GitHub token stored in environment variables
- ⚠️ Keep your `.env` file secure (never commit it!)
- ⚠️ Rotate tokens regularly

## Deployment Options

### Heroku

```bash
heroku create pr-review-webhook
heroku config:set GITHUB_TOKEN=your_token
heroku config:set WEBHOOK_SECRET=your_secret
heroku config:set GITHUB_API_URL=https://github.ibm.com/api/v3
git push heroku main
```

### Docker

```dockerfile
FROM node:18-alpine
WORKDIR /app
COPY package*.json ./
RUN npm install --production
COPY . .
EXPOSE 3000
CMD ["npm", "start"]
```

## Cost

- ✅ **FREE** - No GitHub Actions minutes used
- ✅ **FREE** - ngrok free tier for testing
- ✅ **FREE** - Heroku free tier available
- ✅ **FREE** - Most cloud providers have free tiers

## Advantages Over GitHub Actions

- ✅ Works when Actions are disabled
- ✅ No enterprise restrictions
- ✅ More control over review logic
- ✅ Can integrate with external services
- ✅ Faster response time

---

**Now you have automatic PR reviews without GitHub Actions!** 🚀