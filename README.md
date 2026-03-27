# Bob PR Review Demo

A demonstration project showing how Bob can automatically find edge cases and code issues that might be missed in manual PR reviews.

## What is this demo about?

This project demonstrates how to use **Bob** (AI-powered code review assistant) to automatically detect:
- Security vulnerabilities (hardcoded credentials, insecure patterns)
- Null pointer exceptions and missing null checks
- Performance issues (inefficient algorithms, nested loops)
- Code quality problems (poor error handling, resource leaks)
- Best practice violations

## Why use Bob for PR reviews?

Manual code reviews can miss subtle bugs and edge cases. Bob helps by:
- ✅ Automatically analyzing every pull request
- ✅ Catching issues that humans might overlook
- ✅ Providing consistent, objective feedback
- ✅ Saving time on routine code quality checks

## Quick Setup

### 1. Install Bob Plugin
Install the Bob VS Code extension from the marketplace.

### 2. Configure GitHub MCP Tool
1. Open Bob plugin settings in VS Code
2. Add GitHub MCP server configuration
3. Authenticate with your GitHub Personal Access Token

### 3. Set Up GitHub Authentication
1. Generate a GitHub Personal Access Token with repo permissions
2. Add it as a repository secret named `BOB_API_KEY`
3. The workflow will use this for authentication

### 4. Use the Workflow
The `.github/workflows/bob-pr-review.yml` file is ready to use:
- Automatically triggers on pull requests
- Runs Bob review on changed files
- Posts review comments directly on the PR

## How it works

1. **Create a Pull Request** - Make code changes and open a PR
2. **Bob Analyzes** - The workflow automatically runs Bob review
3. **Get Feedback** - Bob posts comments highlighting issues
4. **Fix Issues** - Address the feedback and improve code quality

## Repository Structure

```
.github/
  workflows/
    bob-pr-review.yml          # Bob review workflow
  bob/
    pr-review-prompt.txt       # Review instructions for Bob
src/
  main/java/                   # Sample Java application
```

## Example Issues Bob Can Catch

- Missing null checks before method calls
- Hardcoded passwords and credentials
- Inefficient nested loops (O(n²) complexity)
- Resource leaks (unclosed connections)
- Insecure authentication patterns

## Getting Started

1. Fork this repository
2. Set up Bob plugin with GitHub MCP
3. Add your GitHub token as `BOB_API_KEY` secret
4. Create a PR with some code changes
5. Watch Bob automatically review your code!

---

**Made with Bob** 🤖
