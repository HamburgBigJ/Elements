package cho.info.elements.server.goals;

import cho.info.elements.managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;

public class GoalVillagers{

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
                goalFirst.setCustomNameVisible(false);
                goalFirst.setNoPhysics(true);
                goalFirst.setAI(false);
                goalFirst.setInvulnerable(true);
                goalFirst.setInvisible(false); //do not nok ho its work !!!

                float yaw = 90.0f; // 180 Grad für Norden
                float pitch = 0.0f; // 0 Grad für eine gerade Ausrichtung
                goalFirst.setRotation(yaw, pitch);


                goalFirst.setCustomName(ChatColor.WHITE + "FirstGoal!");



                spawnArmorStand(goalFirst);


            }
        }
    }
    private void spawnArmorStand(Villager villager) {

        removeExistingArmorStands(villager);

        Object firstGoalXpObj = configManager.getPublicVar("FirstGoalXp");
        Object firstGoalMaxXpObj = configManager.getPublicVar("FirstGoalMaxXp");

        int firstGoalXp = (firstGoalXpObj != null) ? (int) firstGoalXpObj : 0;
        int firstGoalMaxXp = (firstGoalMaxXpObj != null) ? (int) firstGoalMaxXpObj : 0;

        // Spawne den Armor Stand knapp über dem Villager
        Location armorStandLocation = villager.getLocation().add(0, 3, 0); // 2 Blöcke über dem Villager

        ArmorStand armorStand = (ArmorStand) villager.getWorld().spawnEntity(armorStandLocation, EntityType.ARMOR_STAND);
        armorStand.setCustomName(ChatColor.GREEN + String.valueOf(firstGoalXp) + ChatColor.WHITE + " / " + ChatColor.GREEN + String.valueOf(firstGoalMaxXp) + ChatColor.WHITE + " Xp");
        armorStand.setCustomNameVisible(true); // Der Name ist immer sichtbar
        armorStand.setInvisible(true); // Macht den Armor Stand unsichtbar
        armorStand.setGravity(false); // Deaktiviert die Schwerkraft, damit er nicht fällt
        armorStand.setMarker(true); // Macht den Armor Stand kleiner und unsichtbar für Kollisionen
    }

    private void removeExistingArmorStands(Villager villager) {
        // Suche nach Armor Stands in der Nähe des Villagers und lösche sie
        Location villagerLocation = villager.getLocation();
        for (Entity entity : villager.getWorld().getNearbyEntities(villagerLocation, 1, 3, 1)) { // Radius 1x3x1, um Armor Stands in der Nähe zu finden
            if (entity instanceof ArmorStand) {
                entity.remove(); // Entfernt den Armor Stand
            }
        }
    }

    private boolean isVillagerAtLocation(Location location) {
        return location.getWorld().getNearbyEntities(location, 1, 1, 1).stream()
                .anyMatch(entity -> entity instanceof Villager);
    }
}
