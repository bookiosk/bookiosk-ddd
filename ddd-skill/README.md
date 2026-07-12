# ddd-skill

Claude Code / Cursor DDD compliance checking skill.

## File Layout

```
ddd-skill/
├── rules/               # Checked against code — structure, naming, dependency violations
│   ├── domain-layer.md
│   ├── application-layer.md
│   ├── adaptor-layer.md
│   ├── infrastructure-layer.md
│   ├── client-layer.md
│   ├── model-layer.md
│   └── anti-patterns.md
└── references/          # Not checked — education and onboarding
    ├── overview.md
    ├── base-classes-reference.md
    ├── anemic-vs-ddd.md
    └── quick-start-tutorial.md
```

## Usage

### As Claude Code Skill

Add to `.claude/settings.json`:

```json
{
  "skills": {
    "ddd-check": {
      "path": "./ddd-skill",
      "rules": "ddd-skill/rules/"
    }
  }
}
```

### As Cursor Rules

Copy `rules/*.md` into `.cursor/rules/` directory.

### As GitHub Copilot Instructions

Reference in `.github/copilot-instructions.md`.
