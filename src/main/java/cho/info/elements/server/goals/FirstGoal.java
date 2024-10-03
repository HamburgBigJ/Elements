package cho.info.elements.server.goals;

import cho.info.elements.managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class FirstGoal implements Listener {

    public ConfigManager configManager;
    public JavaPlugin plugin;

    public FirstGoal(JavaPlugin plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
    }

    @EventHandler
    public void onVillagerInteract(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity goalEntity = event.getRightClicked();

        // Check if the entity is a Villager
        if (goalEntity.getType() == EntityType.VILLAGER) {
            Villager villager = (Villager) goalEntity;

            // First Goal
            Object firstGoalXpObj = configManager.getPublicVar("FirstGoalXp");
            Object firstGoalMaxXpObj = configManager.getPublicVar("FirstGoalMaxXp");

            int firstGoalXp = (firstGoalXpObj != null) ? (int) firstGoalXpObj : 0;
            int firstGoalMaxXp = (firstGoalMaxXpObj != null) ? (int) firstGoalMaxXpObj : 0;

            if (villager.getCustomName() != null && villager.getCustomName().equals(ChatColor.GOLD + "First Goal: " + ChatColor.GREEN + String.valueOf(firstGoalXp) + ChatColor.WHITE + " / " + ChatColor.GREEN + String.valueOf(firstGoalMaxXp))) {
                if (player.getLevel() >= 10) {
                    player.sendMessage(ChatColor.GOLD + "You have deposited 10 levels!");
                    int playerLevel = player.getLevel();

                    playerLevel = playerLevel - 10;

                    player.setLevel(playerLevel);

                    firstGoalXp = firstGoalXp + 10;

                    configManager.setPublicVar("FirstGoalXp", firstGoalXp);

                    villager.setCustomName(ChatColor.GOLD + "First Goal: " + ChatColor.GREEN + String.valueOf(firstGoalXp) + ChatColor.WHITE + " / " + ChatColor.GREEN + String.valueOf(firstGoalMaxXp));

                } else {
                    player.sendMessage(ChatColor.RED + "You need at least 10 levels to deposit!");
                }

                if (firstGoalXp >= firstGoalMaxXp) {

                    Object firstGoalObj = configManager.getPublicVar("FirstGoal");

                    int firstGoal = (firstGoalObj != null) ? (int) firstGoalObj : 0;

                    firstGoal = 1;

                    configManager.setPublicVar("FirstGoal", firstGoal);

                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "clone 40 67 168 73 77 152 21 66 -7");
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "fill 21 76 -7 21 68 -6 minecraft:spruce_planks");
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "fill 21 76 8 21 68 9 minecraft:spruce_planks");
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "setblock 21 70 -6 minecraft:cobblestone");

                    villager.remove();
                }
            }
        }
    }
}
