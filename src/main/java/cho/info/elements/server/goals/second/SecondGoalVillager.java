package cho.info.elements.server.goals.second;

import cho.info.elements.managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;

public class SecondGoalVillager {

    public ConfigManager configManager;

    public SecondGoalVillager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public void spawnVillager() {
        Object secondGoalObj = configManager.getPublicVar("SecondGoal");
        int secondGoal = (secondGoalObj != null) ? (int) secondGoalObj : 0;

        if (secondGoal == 0) {
            Location secondGoalLocation = new Location(Bukkit.getWorld("world"), 1.5, 69, -17.5);

            if (!isVillagerAtLocation(secondGoalLocation)) {
                Villager villager = (Villager) Bukkit.getWorld("world").spawnEntity(secondGoalLocation, EntityType.VILLAGER);
                villager.setCustomNameVisible(true);
                villager.setAI(false);
                villager.setNoPhysics(true);
                villager.setInvulnerable(true);
                villager.setSilent(true);

                Object secondGoalXpObj = configManager.getPublicVar("SecondGoalXp");
                Object secondGoalMaxXpObj = configManager.getPublicVar("SecondGoalMaxXp");

                int secondGoalXp = (secondGoalXpObj != null) ? (int) secondGoalXpObj : 0;
                int secondGoalMaxXp = (secondGoalMaxXpObj != null) ? (int) secondGoalMaxXpObj : 0;

                villager.setCustomName(ChatColor.GOLD + "Second Hall: " + ChatColor.GREEN + String.valueOf(secondGoalXp) + ChatColor.WHITE + " / " + ChatColor.GREEN + String.valueOf(secondGoalMaxXp));

            }
        }
    }

    private boolean isVillagerAtLocation(Location location) {
        return location.getWorld().getNearbyEntities(location, 1, 1, 1).stream()
                .anyMatch(entity -> entity instanceof Villager);
    }
}
