# Dungeon Crawler

A 2D top-down action RPG built with JavaFX: fight your way through a tile-based dungeon, manage a weapon and potion inventory, gather resources, and upgrade your gear at the anvil.

Originally built as a school end-of-year group project, later cleaned up into a proper English-language, Maven-buildable codebase.

## Features

- **Real-time combat** — melee weapons (sword, longsword) and a ranged bow, each with their own range, damage, and durability that degrades with use.
- **Potions** — heal, poison, stun, and damage-boost effects that can be used on yourself or thrown at enemies.
- **Character progression** — gain experience from defeated enemies, level up, and grow stronger.
- **Resource gathering & crafting** — collect iron, wood, gold, and keys; spend iron/wood at the anvil to repair or upgrade your equipped weapon.
- **Enemy variety** — four races (Orc, Human, Dragon, Dwarf) in both normal and boss variants, each with a distinct weapon/potion loadout and loot drop.
- **Locked doors** — some paths require collecting enough keys to open.
- **Scrolling camera** — the viewport follows the player across a map larger than the screen.
- **Save / load** — game state is serialized to disk and can be reloaded from the main menu.

## Tech stack

- Java 17
- JavaFX 21 (`javafx-base`, `javafx-graphics`, `javafx-controls`, `javafx-media`)
- Maven (with the [`javafx-maven-plugin`](https://github.com/openjfx/javafx-maven-plugin)) — an Eclipse `.project`/`.classpath` is also included for IDE users who prefer it.

## Getting started

### Prerequisites

- JDK 17 or later
- Maven 3.8+

### Build & run

```bash
cd Jeu
mvn javafx:run
```

Or build a runnable jar:

```bash
cd Jeu
mvn clean package
```

### Running from Eclipse

Import `Jeu` as an existing Eclipse project, make sure a JavaFX user library is configured for your JDK, and run `menu.MenuApp`.

> The game loads its images, music, and maps using paths relative to the working directory (e.g. `imagesitems/`, `musiques/`, `cartes/`), so run it with `Jeu/` as the working directory either way.

## Controls

| Key     | Action        |
|---------|---------------|
| `Z`     | Move up       |
| `Q`     | Move left     |
| `S`     | Move down     |
| `D`     | Move right    |
| `Space` | Attack        |
| `Shift` | Sprint        |

(AZERTY-style layout — `Z`/`Q`/`S`/`D` sit where `W`/`A`/`S`/`D` do on a QWERTY keyboard.)

## Project structure

```
Jeu/
├── pom.xml
├── src/
│   ├── game/         # domain model: Entity, Player, Enemy, Weapon, Potion, GameMap, Tile, ...
│   ├── controller/    # input handling, movement, collision, rendering per game object
│   ├── menu/          # JavaFX application entry point and main menu UI
│   ├── display/       # inventory bar widget
│   ├── persistence/    # save/load via Java serialization
│   └── service/       # background threads driving the game loop, AI, and animation timing
├── images/, imagesitems/, imagesmenu/, imagesmonstres/   # sprites and UI art
├── musiques/          # music and sound effects
├── cartes/            # CSV map layouts
└── sauvegardes/       # save files (created at runtime)
```

## Credits

Built by Lilian Naretto, Corentin Brillant, Hassan Yazane, and Ilham Laatarsi.

## License

MIT — see [LICENSE](LICENSE).
