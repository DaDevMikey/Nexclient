# NexClient

A feature-rich Minecraft 1.8.8 PvP client built on MCP (Mod Coder Pack) 9.18.

## Features

NexClient ships with **35 built-in mods** across six categories, a modern mod selector GUI, a custom main menu, and a drag-and-drop HUD editor.

### Mod Categories

#### Display
| Mod | Description |
|-----|-------------|
| BW Stats Overlay | Bedwars statistics overlay |
| Keystrokes | Shows WASD key presses on screen |
| Armor HUD | Displays equipped armor on the HUD |
| Coordinates | Displays your XYZ coordinates |
| Memory Usage | Shows current RAM usage |
| Potion HUD | Shows active potion effects |
| Reach Info | Displays current reach distance |
| Speedometer | Shows movement speed |
| Scoreboard | Customizes scoreboard display |
| Tab List | Customizes the player tab list |

#### Player
| Mod | Description |
|-----|-------------|
| ToggleSprint | Keeps you sprinting automatically |
| Sprint | Automatically sprints when moving forward |
| Hit Mod | Hit effects and modifications |
| Item Physics | Makes dropped items have physics |
| Minim Viewbob | Minimizes view bobbing |

#### Render
| Mod | Description |
|-----|-------------|
| FullBright | Removes all darkness from the game |
| Block Overlay | Custom block selection overlay |
| Crosshair | Custom crosshair |
| 1.7 Animations | 1.7-style combat animations |
| FreeLook | Free camera look (disabled on Hypixel) |
| Time Changer | Changes the visual time of day |
| Motion Blur | Adds a motion blur effect |
| Glint Color | Custom enchantment glint color |
| 3D Skin | 3D skin layer rendering |

#### Chat
| Mod | Description |
|-----|-------------|
| Nick Hider | Hides your player name |
| Auto GG | Automatically sends "gg" at game end |
| Auto GLHF | Automatically sends "glhf" at game start |
| Auto Text | Automatic text responses |
| Chat | Chat customization |

#### Hypixel
| Mod | Description |
|-----|-------------|
| Height Limit | Shows warning when approaching the height limit |
| Quick Play | Quick game joining |
| Auto Add | Auto-add friend requests |
| Auto Friend | Auto-accept friend requests |

#### Optimization
| Mod | Description |
|-----|-------------|
| MultiCore Chunks | Multi-threaded chunk loading |
| Vulkan Rendering | Experimental Vulkan rendering pipeline |

### GUI

- **Custom Main Menu** — Replaces the vanilla main menu with NexClient branding, animated logo, and quick access to Mods and HUD Editor.
- **Mod Selector** — Modern sidebar-based mod browser with category filtering, toggle switches, scrollable card layout, and color-coded category accents.
- **HUD Editor** — Drag-and-drop interface for repositioning any draggable HUD mod on screen.

## Project Structure

```
src/minecraft/
├── com/nexclient/client/          # NexClient source
│   ├── Client.java                # Main client singleton
│   ├── ModManager.java            # Mod registry and lifecycle
│   ├── gui/
│   │   ├── NexMainMenu.java       # Custom main menu
│   │   ├── GuiModSelector.java    # Mod selector GUI
│   │   └── GuiHudEditor.java      # HUD drag editor
│   └── mods/
│       ├── Mod.java               # Base mod class
│       ├── display/               # Display mods
│       ├── player/                # Player mods
│       ├── render/                # Render mods
│       ├── chat/                  # Chat mods
│       ├── hypixel/               # Hypixel-specific mods
│       └── optimization/          # Performance mods
├── net/minecraft/                 # Decompiled Minecraft 1.8.8 source
└── Start.java                     # MCP entry point
```

## Building

NexClient uses the standard MCP 9.18 toolchain. You need a valid Minecraft 1.8.8 installation and Java 8.

```bash
# 1. Decompile Minecraft (first time only)
./decompile.sh        # Linux/macOS
decompile.bat         # Windows

# 2. Recompile with NexClient changes
./recompile.sh        # Linux/macOS
recompile.bat         # Windows

# 3. Test the client
./startclient.sh      # Linux/macOS
startclient.bat       # Windows

# 4. Reobfuscate for distribution
./reobfuscate.sh      # Linux/macOS
reobfuscate.bat       # Windows
```

## Adding a New Mod

1. Create a class in the appropriate sub-package of `com.nexclient.client.mods`.
2. Extend `Mod` and pass a name, description, and `Category` to the super constructor.
3. Override any lifecycle methods you need: `onEnable()`, `onDisable()`, `onUpdate()`, `onRender()`.
4. Register the mod in `ModManager`'s constructor.

```java
package com.nexclient.client.mods.render;

import com.nexclient.client.mods.Mod;

public class ExampleMod extends Mod {
    public ExampleMod() {
        super("Example", "An example mod", Category.RENDER);
    }

    @Override
    public void onEnable() {
        // called when toggled on
    }

    @Override
    public void onUpdate() {
        // called every game tick while enabled
    }
}
```

For HUD mods that should be repositionable in the HUD Editor, call `this.setDraggable(true)` in the constructor and implement `onRender()`.

## License

See [LICENSE](LICENSE) for details.
