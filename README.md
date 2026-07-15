# Barley Porridge

A tiny [NeoForge](https://neoforged.net/) mod for **Minecraft 1.21.1** (built against `neoforge-21.1.235`) that adds a single, hearty food item: **Barley Porridge**.

## The item

- **Barley Porridge** — restores 6 hunger (3 drumsticks) with 0.6 saturation, eaten with the vanilla eating animation. Like mushroom stew, it stacks to 1 and hands the **empty bowl back** when you finish eating. Found in the *Food & Drinks* creative tab.

## Recipe

Shapeless crafting:

| Ingredient | |
|---|---|
| `minecraft:bowl` | ×1 |
| `biomesoplenty:barley` | ×1 |
| `minecraft:milk_bucket` | ×1 |

The **empty bucket stays in the crafting grid** after crafting (vanilla milk-bucket crafting remainder).

The recipe is loaded conditionally via `neoforge:mod_loaded`, so the mod is safe to install without [Biomes O' Plenty](https://modrinth.com/mod/biomes-o-plenty) — the recipe simply won't be available until BOP is present.

## Farmer's Delight compatibility

With [Farmer's Delight](https://modrinth.com/mod/farmers-delight) installed, Barley Porridge can also be simmered in the **Cooking Pot** (shown in its recipe book UI under *Meals*):

| Cooking Pot | |
|---|---|
| `biomesoplenty:barley` | ×1 |
| `minecraft:milk_bucket` | ×1 |
| Serve with | `minecraft:bowl` |

Porridge cooked this way is extra hearty: eating it grants **Nourishment for 1 minute** (Farmer's Delight's status effect). Porridge from the regular crafting grid has no effect — only the slow-simmered kind. Both integrations are data-driven and conditional, so any combination of BOP / Farmer's Delight being installed or absent works fine.

## Building

```bash
./gradlew build
```

The mod jar ends up in `build/libs/`.

## Development

```bash
./gradlew runClient
```
