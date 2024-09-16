package cho.info.elements.server.goals.second;

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
import org.bukkit.plugin.Plugin;

public class SecondGoal implements Listener {

    public ConfigManager configManager;

    public SecondGoal(ConfigManager configManager) {
        this.configManager = configManager;

    }

    @EventHandler
    public void onVillagerInteact(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if (entity.getType() == EntityType.VILLAGER) {
            Villager villager = (Villager) entity;

            Object secondGoalXpObj = configManager.getPublicVar("SecondGoalXp");
            Object secondGoalMaxXpObj = configManager.getPublicVar("SecondGoalMaxXp");

            int secondGoalXp = (secondGoalXpObj != null) ? (int) secondGoalXpObj : 0;
            int secondGoalMaxXp = (secondGoalMaxXpObj != null) ? (int) secondGoalMaxXpObj : 0;

            if (villager.getCustomName() != null && villager.getCustomName().equals(ChatColor.GOLD + "Second Hall: " + ChatColor.GREEN + String.valueOf(secondGoalXp) + ChatColor.WHITE + " / " + ChatColor.GREEN + String.valueOf(secondGoalMaxXp))) {
                if (player.getLevel() >= 10) {
                    player.sendMessage(ChatColor.GOLD + "You have deposited 10 levels!");

                    int playerLevel = player.getLevel();

                    playerLevel = playerLevel - 10;

                    player.setLevel(playerLevel);

                    secondGoalXp = secondGoalXp + 10;

                    configManager.setPublicVar("SecondGoalXp", secondGoalXp);
                    villager.setCustomName(ChatColor.GOLD + "Second Hall: " + ChatColor.GREEN + String.valueOf(secondGoalXp) + ChatColor.WHITE + " / " + ChatColor.GREEN + String.valueOf(secondGoalMaxXp));

                }
                if (secondGoalXp >= secondGoalMaxXp) {
                    Object secondGoalObj =  configManager.getPublicVar("SecondGoal");
                    int secondGoal = (secondGoalObj != null) ? (int) secondGoalObj : 0;

                    secondGoal = 1;

                    configManager.setPublicVar("SecondGoal", secondGoal);

                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "clone 43 70 135 28 81 102 -7 67 -52");
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "fill -7 78 -19 -6 68 -19 minecraft:spruce_planks");
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "fill 8 78 -19 8 68 -19 minecraft:spruce_planks");

                    villager.remove();
                }
            }
        }
    }
}
