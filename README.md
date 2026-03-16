# 📦 TagSort Logistics

A **Quality-of-Life** Minecraft mod for **Fabric 1.21.4** that simplifies chest inventory management using **Item Tags** and **Item Frames**.

> Place an Item Frame on a chest, put an item inside it, and let the mod handle the rest!

---

## ✨ Features

### 🏷️ Smart Tagging
- Place an **Item Frame** on or next to a chest and insert any item.
- The mod reads the item's **tags** (e.g., `#minecraft:logs`, `#minecraft:planks`) and assigns them to the chest.
- Any item sharing those tags can now be automatically routed to that chest.

### 🖱️ GUI Buttons
When you open a chest, two buttons appear on the right side of the chest UI:

| Button | Function |
|--------|----------|
| **Pull** | Pulls all matching items from your inventory into this chest (based on Item Frame tags) |
| **A-Z** | Sorts the chest contents alphabetically and stacks items together |

---

## 📥 Installation

### Requirements
- **Minecraft** 1.21.4
- **Fabric Loader** ≥ 0.16.5
- **Fabric API** ([Download](https://modrinth.com/mod/fabric-api/versions?g=1.21.4))

### Steps
1. Install [Fabric Loader](https://fabricmc.net/use/installer/) for Minecraft 1.21.4.
2. Download **Fabric API** for 1.21.4 and place it in your `mods/` folder.
3. Download `tagsortlogistics-1.0.0.jar` from [Releases](../../releases) and place it in your `mods/` folder.
4. Launch Minecraft with the Fabric profile.

---

## 🎮 How to Use

1. **Tag a Chest:** Place an Item Frame on any side of a chest. Put an item (e.g., Oak Log) into the frame.
2. **Pull Button:** Open the tagged chest and click `Pull` to transfer all matching items from your inventory into the chest.
3. **Sort Button:** Click `A-Z` to sort and stack items inside the chest alphabetically.

---

## 🏗️ Building from Source

### Prerequisites
- **Java 21** (JDK)

### Build
```bash
git clone https://github.com/YOUR_USERNAME/TagSortLogistics.git
cd TagSortLogistics
./gradlew build
```

The compiled JAR will be at `build/libs/tagsortlogistics-1.0.0.jar`.

---

## 📁 Project Structure

```
src/
├── main/java/com/tagsortlogistics/
│   ├── TagSortLogistics.java          # Server-side mod initializer
│   ├── networking/ModPayloads.java    # Custom network packets
│   ├── logic/InventoryLogic.java      # Item transfer & sorting algorithms
│   ├── logic/LastOpenedTracker.java   # Tracks last opened chest position
│   └── util/TagHelper.java           # Item Frame tag reading utilities
├── client/java/com/tagsortlogistics/
│   ├── TagSortLogisticsClient.java    # Client-side mod initializer
│   └── mixin/client/
│       └── GenericContainerScreenMixin.java  # GUI button injection
```

---

## 🔧 Technical Details

- Uses **Fabric Networking API** (Custom Payloads) for client-server communication.
- GUI buttons are injected via **Mixin** into `AbstractContainerScreen`.
- Tag matching uses Minecraft's built-in **Item Tag** system — no custom configs needed.
- All inventory operations run **server-side** for multiplayer compatibility.

---

## 📄 License

This project is licensed under the [CC0-1.0](LICENSE) license.
