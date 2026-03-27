# Automated PR Review Setup Guide

This repository includes automated code review workflows that will comment on your Pull Requests with improvement suggestions.

## 📋 Available Workflows

### 1. **Basic PR Review** (`.github/workflows/pr-review.yml`)
- Checks for common code issues
- Provides general improvement suggestions
- Runs basic code quality checks

### 2. **AI-Powered PR Review** (`.github/workflows/ai-pr-review.yml`)
- Analyzes changed files in detail
- Provides context-aware suggestions
- Updates comments on subsequent pushes
- More comprehensive recommendations

## 🚀 How It Works

When you create or update a Pull Request, the workflows will:

1. **Automatically trigger** on PR events (opened, synchronized, reopened)
2. **Analyze your code** for common issues and patterns
3. **Post a comment** on the PR with suggestions
4. **Update the comment** if you push more changes

## 📝 What Gets Reviewed

### Code Quality Checks
- ✅ TODO comments detection
- ✅ System.out.println usage (should use logger)
- ✅ printStackTrace usage (should use proper logging)
- ✅ Generic exception usage
- ✅ Missing validation annotations
- ✅ Missing JavaDoc comments

### Suggestions Provided

#### Architecture & Design
- Use DTOs instead of exposing entities
- Implement proper exception handling
- Add API versioning
- Consider HATEOAS implementation

#### Security
- Add Spring Security
- Implement JWT authentication
- Add input validation
- Configure CORS
- Use HTTPS in production

#### Performance
- Add pagination for list endpoints
- Implement caching
- Add database indexes
- Use lazy loading
- Configure connection pooling

#### Testing
- Add unit tests (JUnit 5 + Mockito)
- Add integration tests
- Add API tests
- Aim for 80% code coverage

#### Observability
- Add Spring Boot Actuator
- Implement structured logging
- Add metrics collection
- Consider distributed tracing

## 🔧 Setup Instructions

### For GitHub Enterprise (github.ibm.com)

The workflows are already configured and will work automatically. No additional setup needed!

### Required Permissions

The workflows need these permissions (already configured):
```yaml
permissions:
  pull-requests: write  # To post comments
  contents: read        # To read code
```

## 📖 Usage Examples

### Creating a Pull Request

1. **Create a feature branch:**
   ```bash
   git checkout -b feature/add-validation
   ```

2. **Make your changes and commit:**
   ```bash
   git add .
   git commit -m "Add input validation to controllers"
   ```

3. **Push to remote:**
   ```bash
   git push origin feature/add-validation
   ```

4. **Create PR on GitHub:**
   - Go to your repository
   - Click "Pull Requests" → "New Pull Request"
   - Select your branch
   - Create the PR

5. **Wait for automated review:**
   - The workflow will run automatically
   - A comment will appear with suggestions
   - Review the suggestions and apply what makes sense

### Example Review Comment

The bot will post something like:

```markdown
## 🤖 AI Code Review

### 📝 Changed Files
- `src/main/java/com/example/demo/controller/BookController.java`
- `src/main/java/com/example/demo/service/BookService.java`

### 🔍 Detailed Analysis

#### 📄 `src/main/java/com/example/demo/controller/BookController.java`
- ⚠️ **Generic Exception**: Consider using custom exceptions instead of RuntimeException
- 💡 **Validation**: Consider adding @Valid annotation for request body validation
- 📚 **Documentation**: Add JavaDoc comments for public methods and classes

### 🎯 General Recommendations
[... more suggestions ...]
```

## 🎨 Customization

### Modify Review Rules

Edit `.github/workflows/ai-pr-review.yml` to customize:

1. **Add new checks:**
   ```bash
   if grep -q "YourPattern" "$file"; then
     echo "- ⚠️ **Your Message**: Your suggestion" >> ai_review.md
   fi
   ```

2. **Change file patterns:**
   ```yaml
   files: |
     **/*.java
     **/*.kt      # Add Kotlin
     **/*.scala   # Add Scala
   ```

3. **Modify suggestions:**
   Edit the "General Recommendations" section in the workflow

### Disable Specific Checks

Comment out sections you don't want:
```bash
# if grep -q "System.out.println" "$file"; then
#   echo "- ⚠️ **Logging**: ..." >> ai_review.md
# fi
```

## 🔍 Troubleshooting

### Workflow Not Running

1. **Check workflow file location:**
   - Must be in `.github/workflows/` directory
   - Must have `.yml` or `.yaml` extension

2. **Check permissions:**
   - Repository settings → Actions → General
   - Ensure "Allow all actions" is selected

3. **Check branch protection:**
   - Settings → Branches
   - Ensure workflows can run on your branch

### No Comment Posted

1. **Check workflow logs:**
   - Go to "Actions" tab
   - Click on the workflow run
   - Check for errors

2. **Verify permissions:**
   - Workflow needs `pull-requests: write` permission

### Comment Not Updating

The AI-powered workflow updates existing comments. If you want a new comment each time, remove this section:
```javascript
if (botComment) {
  // Remove this block to always create new comments
}
```

## 📊 Workflow Status

You can see workflow status in:
- PR checks section
- Actions tab in repository
- PR timeline

## 🎯 Best Practices

1. **Review suggestions carefully** - Not all suggestions may apply to your use case
2. **Discuss in PR comments** - If you disagree with a suggestion, explain why
3. **Iterate** - Push changes and the review will update
4. **Learn** - Use suggestions to improve your coding practices

## 🔗 Related Resources

- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [Spring Boot Best Practices](https://spring.io/guides)
- [Java Code Conventions](https://www.oracle.com/java/technologies/javase/codeconventions-contents.html)

## 💡 Tips

- The review is meant to be helpful, not restrictive
- Apply suggestions that make sense for your project
- Feel free to modify the workflows to match your team's standards
- Use the review as a learning tool

---

**Questions?** Open an issue or ask in PR comments!