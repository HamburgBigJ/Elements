package cho.info.elements.server.goals;

import cho.info.elements.managers.ConfigManager;
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



        // Überprüfen, ob das Entity ein Villager ist
        if (goalEntity.getType() == EntityType.VILLAGER) {
            Villager villager = (Villager) goalEntity;


            // First Goal
            Object firstGoalXpObj = configManager.getPublicVar("FirstGoalXp");
            Object firstGoalMaxXpObj = configManager.getPublicVar("FirstGoalMaxXp");

            int firstGoalXp = (firstGoalXpObj != null) ? (int) firstGoalXpObj : 0;
            int firstGoalMaxXp = (firstGoalMaxXpObj != null) ? (int) firstGoalMaxXpObj : 0;

            if (villager.getCustomName() != null && villager.getCustomName().equals(ChatColor.GOLD + "First Goal: " + ChatColor.GREEN + String.valueOf(firstGoalXp) + ChatColor.WHITE + " / " + ChatColor.GREEN + String.valueOf(firstGoalMaxXp) + ChatColor.WHITE + " Xp")) {
                if (player.getLevel() >= 10) {
                    player.sendMessage(ChatColor.GOLD + "Du Hast 10 Level Eingezahlt!");
                    int playerLevel = player.getLevel();

                    playerLevel = playerLevel - 10;

                    player.setLevel(playerLevel);

                    firstGoalXp = firstGoalXp + 10;

                    configManager.setPublicVar("FirstGoalXp", firstGoalXp);

                    villager.setCustomName(ChatColor.GOLD + "First Goal: " + ChatColor.GREEN + String.valueOf(firstGoalXp) + ChatColor.WHITE + " / " + ChatColor.GREEN + String.valueOf(firstGoalMaxXp) + ChatColor.WHITE + " Xp");

                } else if (firstGoalXp >= firstGoalMaxXp) {

                    Object firstGoalObj = configManager.getPublicVar("FirstGoal");

                    int firstGoal = (firstGoalObj != null) ? (int) firstGoalObj : 0;

                    firstGoal = 1;

                    configManager.setPublicVar("FirstGoal", firstGoal);


                    villager.remove();
                }
            }
        }
    }
}
