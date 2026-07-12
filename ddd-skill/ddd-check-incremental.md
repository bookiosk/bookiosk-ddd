---
name: ddd-check-incremental
description: Incremental DDD compliance check — scans only changed/new Java files in git diff and validates against DDD layer rules. Use before every commit.
type: skill
rulePath: ddd-skill/rules/
---

# DDD Incremental Compliance Check

Check only **new or modified** Java files against DDD layer rules. Fast, pre-commit focused.

## Trigger

Run when user asks "check my changes", "DDD check", "review this code", or before committing.

## Workflow

### Step 1: Find Changed Files

```bash
# Staged changes (pre-commit)
git diff --cached --name-only --diff-filter=ACMR | grep '\.java$'

# Unstaged changes (working tree)
git diff --name-only --diff-filter=ACMR | grep '\.java$'

# Both
git diff HEAD --name-only --diff-filter=ACMR | grep '\.java$'
```

If no Java files changed, report: "No Java files changed — nothing to check."

### Step 2: Classify Each File by Layer

Map file path to DDD layer:

| Package Pattern | Layer | Rule File |
|---|---|---|
| `**/ddd/common/**` | Common | (structural rules only) |
| `**/ddd/domain/**` | Domain | `domain-layer.md` |
| `**/ddd/application/**` | Application | `application-layer.md` |
| `**/ddd/adaptor/**` | Adaptor | `adaptor-layer.md` |
| `**/ddd/infrastructure/**` | Infrastructure | `infrastructure-layer.md` |
| `**/ddd/client/**` | Client | `client-layer.md` |
| `**/ddd/model/**` | Model | `model-layer.md` |

### Step 3: Read Relevant Rule Files

Load only the rule files matching the changed layers. Plus always load `anti-patterns.md`.

Rule files are at: `ddd-skill/rules/<layer>.md`

### Step 4: Check Each Changed File

For each changed file, read it and validate against:

1. **Structural rules** — correct base class, correct package, correct naming
2. **Dependency rules** — no forbidden imports (e.g. domain must not import infrastructure)
3. **Anti-patterns** — cross-check against `anti-patterns.md`

### Step 5: Report

Format output as:

```
## DDD Check: {branch} → {n} files changed

### {FilePath}.java — {Layer}
<emoji> <SEVERITY>: <problem>. <fix>.

Example:
  src/.../domain/Order.java — Domain
  CRITICAL: setId() is public, must be protected. Change `public void setId` → `protected void setId`.
  HIGH: Direct import of OrderMapper in domain layer. Move to repository interface.

### Summary
- 3 files checked
- 1 CRITICAL, 1 HIGH, 0 MEDIUM, 0 LOW
```

Severity levels:
- **CRITICAL**: Architecture violation (wrong layer dependency, missing base class)
- **HIGH**: Rule violation (wrong naming, public setter on aggregate)
- **MEDIUM**: Convention deviation (missing JavaDoc, wrong file location)
- **LOW**: Style suggestion

### Important

- Do NOT check unchanged files — incremental only
- Do NOT suggest changes that conflict with project conventions in SKILL.md
- If a violation is intentional (e.g. temporary workaround), note it but don't flag as CRITICAL
- Only report real problems — don't report "looks good" for files with zero issues
