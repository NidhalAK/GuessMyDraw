# Agent Instructions — GuessMydraw2

This project follows a feature-layered Clean Architecture (Android/Kotlin, MVI presentation,
Koin DI, Ktor + Room data layer, type-safe Compose Navigation). The full conventions are defined
as agent skills in `.github/skills/`; Copilot automatically loads the relevant ones when working
on matching tasks (scaffolding a feature, writing a ViewModel, adding navigation, writing tests,
etc.). Do not deviate from these conventions without a clear reason stated to the user.

## Architecture skills (`.github/skills/`)

| Skill | Covers |
|---|---|
| `android-module-structure` | Module layout, dependency rules, Gradle convention plugins, version catalogs |
| `android-data-layer` | Repositories, DTOs, mappers, Room entities/DAOs, Ktor `HttpClient`, safe-call helpers, token storage, offline-first |
| `android-presentation-mvi` | State/Action/Event, ViewModel wiring, Root/Screen composable split, `UiText`, `SavedStateHandle` |
| `android-compose-ui` | Composable stability, recomposition, side effects, lazy lists, animations, previews, accessibility, design system |
| `android-navigation` | Type-safe Compose Navigation, per-feature nav graphs, cross-feature callbacks |
| `android-di-koin` | Koin module-per-layer conventions, scoping, `koinViewModel()` |
| `android-error-handling` | `Result<T, E>` / `DataError` / `EmptyResult` and mapping helpers |
| `android-testing` | JUnit5 + AssertK + Turbine, `UnconfinedTestDispatcher`, fake repositories, `ComposeTestRule` |

When implementing a feature, follow the module boundaries and dependency rules in
`android-module-structure` first, then apply the layer-specific skill for whatever you're
building (data source, ViewModel, screen, navigation, DI wiring, tests).

## Code review

Use the `branch-review` skill (say "review") to run the multi-agent bug/convention/security
review gate before merging any branch.
