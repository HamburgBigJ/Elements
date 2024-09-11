package cho.info.elements.player;

import cho.info.elements.managers.ConfigManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SkillLevelManager implements Listener {

    private JavaPlugin plugin;
    public ConfigManager configManager;

    public SkillLevelManager(JavaPlugin plugin, ConfigManager configManager){
        this.plugin = plugin;
        this.configManager = configManager;
    }

    @EventHandler
    public void onMiningSkillManager(BlockBreakEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();

        Object miningXpObj = configManager.getPlayerValue(player, "MiningXp");
        Object miningLvObj = configManager.getPlayerValue(player, "MiningLv");
        Object miningMaxXpObj = configManager.getPlayerValue(player, "MiningMaxXp");

        int miningXp = (miningXpObj != null) ? (int) miningXpObj : 0;
        int miningLv = (miningLvObj != null) ? (int) miningLvObj : 0;
        int miningMaxXp = (miningMaxXpObj != null) ? (int) miningMaxXpObj : 0;

        if (miningXp >= miningMaxXp) {  // Check if player has enough XP to level up
            miningXp = 0;
            miningMaxXp = miningMaxXp + 100;  // Increase max XP for next level
            miningLv = miningLv + 1;

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                    ChatColor.DARK_AQUA + "⛏ Mining XP: " + miningXp + " / " + miningMaxXp
            ));

            configManager.setPlayerValue(player, "MiningXp", miningXp);
            configManager.setPlayerValue(player, "MiningLv", miningLv);
            configManager.setPlayerValue(player, "MiningMaxXp", miningMaxXp);

            event.getPlayer().sendMessage(ChatColor.DARK_AQUA + "Mining Level up to --> " + miningLv);

            var playerLocation = event.getPlayer().getLocation();

            world.spawnParticle(Particle.FIREWORK, playerLocation, 5);  // Spawn fireworks at player location
        }
    }

    @EventHandler
    public void onForestrySkillManager(BlockBreakEvent event) {  // Renamed from "onForestingSkillManeger" to "onForestrySkillManager"
        Player player = event.getPlayer();
        World world = player.getWorld();

        Object forestryXpObj = configManager.getPlayerValue(player, "ForestingXp");  // Corrected "Foresting" to "Forestry"
        Object forestryMaxXpObj = configManager.getPlayerValue(player, "ForestingMaxXp");  // Corrected "Foresting" to "Forestry"
        Object forestryLvObj = configManager.getPlayerValue(player, "ForestingLv");  // Corrected "Foresting" to "Forestry"

        int forestryXp = (forestryXpObj != null) ? (int) forestryXpObj : 0;
        int forestryMaxXp = (forestryMaxXpObj != null) ? (int) forestryMaxXpObj : 0;
        int forestryLv = (forestryLvObj != null) ? (int) forestryLvObj : 0;

        if (forestryXp >= forestryMaxXp) {  // Check if player has enough XP to level up
            forestryXp = 0;
            forestryMaxXp = forestryMaxXp + 100;  // Increase max XP for next level
            forestryLv = forestryLv + 1;

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                    ChatColor.DARK_AQUA + "\uD83E\uDE93 Forestry XP: " + forestryXp  + " / " + forestryMaxXp
            ));

            configManager.setPlayerValue(player, "ForestryXp", forestryXp);
            configManager.setPlayerValue(player, "ForestryMaxXp", forestryMaxXp);
            configManager.setPlayerValue(player, "ForestryLv", forestryLv);

            event.getPlayer().sendMessage(ChatColor.DARK_AQUA + "Forestry Level up to --> " + forestryLv);

            var playerLocation = event.getPlayer().getLocation();

            world.spawnParticle(Particle.FIREWORK, playerLocation, 5);  // Spawn fireworks at player location
        }
    }

    @EventHandler
    public void onFarmingSkillManager(BlockBreakEvent event) {  // Renamed from "onFarmingSkillManeger" to "onFarmingSkillManager"
        Player player = event.getPlayer();
        World world = player.getWorld();

        Object farmingXpObj = configManager.getPlayerValue(player, "FarmingXp");
        Object farmingMaxXpObj = configManager.getPlayerValue(player, "FarmingMaxXp");
        Object farmingLvObj = configManager.getPlayerValue(player, "FarmingLv");  // Corrected "ForestingLv" to "FarmingLv"

        int farmingXp = (farmingXpObj != null) ? (int) farmingXpObj : 0;
        int farmingMaxXp = (farmingMaxXpObj != null) ? (int) farmingMaxXpObj : 0;
        int farmingLv = (farmingLvObj != null) ? (int) farmingLvObj : 0;

        if (farmingXp >= farmingMaxXp) {  // Check if player has enough XP to level up
            farmingXp = 0;
            farmingMaxXp = farmingMaxXp + 100;  // Increase max XP for next level
            farmingLv = farmingLv + 1;

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                    ChatColor.DARK_AQUA + "✂ Farming XP: " + farmingXp  + " / " + farmingMaxXp
            ));

            configManager.setPlayerValue(player, "FarmingXp", farmingXp);
            configManager.setPlayerValue(player, "FarmingMaxXp", farmingMaxXp);
            configManager.setPlayerValue(player, "FarmingLv", farmingLv);  // Corrected "ForestingLv" to "FarmingLv"

            event.getPlayer().sendMessage(ChatColor.DARK_AQUA + "Farming Level up to --> " + farmingLv);

            var playerLocation = event.getPlayer().getLocation();

            world.spawnParticle(Particle.FIREWORK, playerLocation, 5);  // Spawn fireworks at player location
        }
    }
}
