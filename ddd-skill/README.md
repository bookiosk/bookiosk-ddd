# ddd-skill

Claude Code / Cursor DDD compliance checking skills.

## File Layout

```
ddd-skill/
├── rules/                      # Checked against code — structure, naming, dependency violations
│   ├── domain-layer.md
│   ├── application-layer.md
│   ├── adaptor-layer.md
│   ├── infrastructure-layer.md
│   ├── client-layer.md
│   ├── model-layer.md
│   └── anti-patterns.md
├── references/                 # Not checked — education and onboarding
│   ├── overview.md
│   ├── base-classes-reference.md
│   ├── anemic-vs-ddd.md
│   └── quick-start-tutorial.md
├── ddd-check-incremental.md    # Incremental DDD check (git diff only)
└── ddd-check-full.md           # Full-project DDD audit (all files)
```

## Skills

### `ddd-check-incremental`

Scans **only changed/new Java files** (via `git diff`) against DDD rules. Fast, pre-commit focused.

Trigger: "check my changes", "DDD check", "review this code"

### `ddd-check-full`

Audits **every Java file** in the project against all DDD rules. For architecture reviews and pre-release checks.

Trigger: "full DDD audit", "architecture review", "check entire project"

## Usage

### As Claude Code Skills

Add to `.claude/settings.json`:

```json
{
  "skills": {
    "ddd-check-incremental": {
      "path": "ddd-skill/ddd-check-incremental.md"
    },
    "ddd-check-full": {
      "path": "ddd-skill/ddd-check-full.md"
    }
  }
}
```

Or use the `/skill` command to load directly.

### As Cursor Rules

Copy `rules/*.md` into `.cursor/rules/` directory.

### As GitHub Copilot Instructions

Reference in `.github/copilot-instructions.md`.
