#!/bin/bash

echo "🚀 Setting up GitHub Webhook Server for Automated PR Reviews"
echo ""

# Check if Node.js is installed
if ! command -v node &> /dev/null; then
    echo "❌ Node.js is not installed. Please install Node.js first:"
    echo "   https://nodejs.org/"
    exit 1
fi

echo "✅ Node.js version: $(node --version)"
echo ""

# Install dependencies
echo "📦 Installing dependencies..."
npm install

if [ $? -ne 0 ]; then
    echo "❌ Failed to install dependencies"
    exit 1
fi

echo "✅ Dependencies installed"
echo ""

# Check if .env exists
if [ ! -f .env ]; then
    echo "⚠️  .env file not found. Creating from .env.example..."
    cp .env.example .env
    echo "✅ Created .env file"
    echo ""
    echo "📝 Please edit webhook-server/.env and add your GitHub token:"
    echo "   1. Go to https://github.ibm.com/settings/tokens"
    echo "   2. Generate a new token with 'repo' scope"
    echo "   3. Replace GITHUB_TOKEN in .env file"
    echo ""
else
    echo "✅ .env file exists"
    echo ""
fi

# Check if ngrok is installed
if command -v ngrok &> /dev/null; then
    echo "✅ ngrok is installed"
    echo ""
    echo "📋 Next steps:"
    echo "   1. Start the server: npm start"
    echo "   2. In another terminal, run: ngrok http 3000"
    echo "   3. Copy the ngrok HTTPS URL"
    echo "   4. Add webhook in GitHub: Settings → Webhooks → Add webhook"
    echo "      - Payload URL: https://your-ngrok-url/webhook"
    echo "      - Content type: application/json"
    echo "      - Secret: (copy from .env WEBHOOK_SECRET)"
    echo "      - Events: Pull requests"
    echo ""
else
    echo "⚠️  ngrok not found. Install it to expose your local server:"
    echo "   brew install ngrok  # macOS"
    echo "   or download from https://ngrok.com"
    echo ""
    echo "📋 Next steps:"
    echo "   1. Install ngrok"
    echo "   2. Start the server: npm start"
    echo "   3. In another terminal, run: ngrok http 3000"
    echo "   4. Add webhook in GitHub (see README.md for details)"
    echo ""
fi

echo "✅ Setup complete!"
echo ""
echo "🎯 Quick start:"
echo "   npm start    # Start the webhook server"
echo "   npm run dev  # Start with auto-reload"
echo ""

# Made with Bob
