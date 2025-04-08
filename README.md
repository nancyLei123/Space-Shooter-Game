# 🚀 LeiGame - LANDING ON MARS

## 🌌 Overview
**LeiGame** plunges players into a thrilling scrolling space adventure, where they navigate a spaceship on a daring journey to Mars. The game has two main stages:

- **Session 1: Space Journey** – Players dodge asteroids and collect valuable resources like ammo and shields.
- **Session 2: Boss Encounter** – Players confront an alien boss using the resources gathered during the space journey.

Set against the backdrop of an alien invasion, with Earth devastated, the player's mission is to secure humanity's future by reaching and landing on Mars.

---

## ✨ Features

- **Scrolling Environment:** A dynamic space-scrolling world filled with obstacles (e.g., asteroids) and collectibles (ammo, shields).
- **Entity Interactions:** Entities are classified as:
    - `Avoid`: Obstacles like asteroids.
    - `Get` / `SpecialGet`: Collectibles like ammo and shields.
- **Boss Encounter:** A challenging boss battle is triggered after 30 entities have spawned.
- **Game States:** Includes pregame, active gameplay, boss encounter, and postgame phases with distinct splash screens.
- **Player & Boss Mechanics:**
    - Player: Can shoot bullets and has a health point (HP) system.
    - Boss: Has its own movement patterns and HP bar.
- **Scoring & Bullets:** Players collect bullets, score points, and use bullets strategically in the boss fight.

---

## 🧩 Key Classes & Methods

- `Boss`, `Bullet`, `MyBullet` classes.
- New methods include:
    - `startBossEncounter()`
    - `handleBossCollision()`
- Major revisions were made to support boss dynamics and multi-stage gameplay.

---

## 🎮 Game Flow

1. **Start**: Welcome splash screen ➝ Information splash screen.
2. **Gameplay**: Navigate space, dodge obstacles, collect ammo/shields.
3. **Boss Fight**: Triggered after enough entities have spawned.
4. **End**: Based on win or lose conditions.

---

## 🕹 Controls

- **Arrow Keys** – Move spaceship
- **Spacebar** – Shoot (boss stage only)
- **Pause Key** – Pause the game

---

## 🏆 Win & Lose Conditions

- **Win**:
    - Defeat the alien boss (reduce boss HP to 0).
- **Lose**:
    - Player HP reaches 0 (due to obstacles or boss attacks).
    - Player runs out of bullets during the boss fight.

---

## 📦 Assets & Resources

### 🎨 Visual Assets
- **Icons:**  
  [CraftPix Space Icons](https://craftpix.net/?s=space)
- **Characters:**  
  [Space Adventures 2D Game Kit](https://craftpix.net/product/space-adventures-2d-game-kit/?num=1&count=355&sq=space%20adventures&pos=0)
- **Background & Asteroids:**  
  [Asteroids Crusher 2D Game Kit](https://craftpix.net/product/asteroids-crusher-2d-game-kit/?num=1&count=11&sq=asteroids&pos=0)

### 🎨 Splash Page Designs (Made with Canva)
- [Splash Design 1](https://www.canva.com/design/DAF13SuFQmw/Ur8uSgu29NUsGTA-kU4NuQ/edit)
- [Splash Design 2](https://www.canva.com/design/DAF15XBuYCg/iXBovtKmApix4Zoi6NfTNA/edit)
- [Splash Design 3](https://www.canva.com/design/DAF13YQ-0Cw/xFqiP5JfmPg_1_Y32w6cKg/edit)

### 🔊 Sound Effects
- [Pixabay Game Sound Effects](https://pixabay.com/sound-effects/search/game%20bonus/)

---

## 🧰 Tools Used

- Convert PNG to GIF: [ezgif.com](https://ezgif.com/maker/)
- Convert MP3 to WAV: [CloudConvert](https://cloudconvert.com/mp3-to-wav)

---

> Designed and developed by **Jianan Lei (Nancy)** 🎮🪐
