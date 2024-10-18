# Elements Project by HamburgBigJ

**Note:** Some modifications have been made. This is not a direct recreation but a similar project.

<p align="center">
    <a href="https://www.codefactor.io/repository/github/hamburgbigj/elements/overview/master" alt="CodeFactor Score">
        <img src="https://www.codefactor.io/repository/github/hamburgbigj/elements/badge/master"/>
    </a>
</p>

**Disclaimer:**
- This is a recreation of the original project by **SparkofPhoenix**.

**YouTube:**
- [SparkofPhoenix's Channel](https://www.youtube.com/@SparkofPhoenix)

---

**To-Do List:**
- Implement the mana refill system in `player/mana/ManaRefill.java`.
- Complete the storyline.
- Add tags before player names.
- Implement all villager shops.
- Implement the Gravity Stone.
- Create all 27 Unite Items.
- Implement ElementHoe logic in `player/skills/FarmingSkill.java`.
- Update `MobManager` to function without `BossBar`.
- Implement leaderboards.
- Add a Dungeon Editor.
- Finish the Anvil GUI in `server/villagers/stants/inventory/AnvilGuiFunction.java`.
- Improve the random reward system and other mechanics.
- Create a Boss Bar for the collection barrel's objective.
- Finish the display cases in the hub.
- Add a crate display entity for the goal of reaching level 10,000,000.

**Ideas:**
- Add combat skills.
- Add crafting skills.

**Errors:**
- Fix barrel
- Gravity stone not working.
- Villagers not spawning after upgrading the barrel.
- Villager upgrade gui editable.

---

**Installation Instructions:**
1. Set `spawn-protection` to 0.
2. Enable command blocks (`enable-command-block` to true).
   3. World configuration:
   ```yaml
   worlds:
      world:
        generator: Elements:CustomOverworldGenerator
   ```
4. Drag and drop the plugin into the plugin folder.
5. Add plugin "WorldGard" & "WorldEdit" to the Plugins Folder.