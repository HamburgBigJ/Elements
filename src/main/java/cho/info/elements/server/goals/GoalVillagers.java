package cho.info.elements.server.goals;

import cho.info.elements.managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;

public class GoalVillagers {

    public ConfigManager configManager;

    public GoalVillagers(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public void spawnVillagers() {
        Object firstGoalStageObj = configManager.getPublicVar("FirstGoal");

        int firstGoalStage = (firstGoalStageObj != null) ? (int) firstGoalStageObj : 0;

        // First Goal
        if (firstGoalStage == 0) {
            Location firstgoalLocation = new Location(Bukkit.getWorld("world"), 20.5, 69, 1.5);

            if (!isVillagerAtLocation(firstgoalLocation)) {
                Villager goalFirst = (Villager) Bukkit.getWorld("world").spawnEntity(firstgoalLocation, EntityType.VILLAGER);
                goalFirst.setCustomNameVisible(true);
                goalFirst.setNoPhysics(true);
                goalFirst.setAI(false);
                goalFirst.setInvulnerable(true);
                goalFirst.setInvisible(false); // Ensure it is visible; setting this to false makes sure the Villager is visible
                goalFirst.setSilent(true); // Very important

                float yaw = 90.0f; // 180 degrees for north
                float pitch = 0.0f; // 0 degrees for a flat orientation
                goalFirst.setRotation(yaw, pitch);

                Object firstGoalXpObj = configManager.getPublicVar("FirstGoalXp");
                Object firstGoalMaxXpObj = configManager.getPublicVar("FirstGoalMaxXp");

                int firstGoalXp = (firstGoalXpObj != null) ? (int) firstGoalXpObj : 0;
                int firstGoalMaxXp = (firstGoalMaxXpObj != null) ? (int) firstGoalMaxXpObj : 0;

                goalFirst.setCustomName(ChatColor.GOLD + "First Hall: " + ChatColor.GREEN + String.valueOf(firstGoalXp) + ChatColor.WHITE + " / " + ChatColor.GREEN + String.valueOf(firstGoalMaxXp));
            }
        }
    }

    private boolean isVillagerAtLocation(Location location) {
        return location.getWorld().getNearbyEntities(location, 1, 1, 1).stream()
                .anyMatch(entity -> entity instanceof Villager);
    }
}
