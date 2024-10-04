package cho.info.elements.server.villagers.stants;

import cho.info.elements.managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;

public class LoteryVillagerStand {


    public void spawnVillagerLotery() {
        Location location = new Location(Bukkit.getWorld("world"), 10.5, 69, 5.5);

        if (!isVillagerAtLocation(location)){
            Villager villager = (Villager) Bukkit.getWorld("world").spawnEntity(location, EntityType.VILLAGER);
            villager.setCustomNameVisible(true);
            villager.setInvulnerable(true);
            villager.setCustomName(ChatColor.DARK_AQUA + "Lotery Villager");
            villager.setAI(false);
            villager.setSilent(true);
            villager.setNoPhysics(true);
            villager.setVillagerLevel(5);

            // Set the villager's facing direction to North
            float yaw = 180.0f; // 180 degrees for North
            float pitch = 0.0f; // 0 degrees for a straight orientation
            villager.setRotation(yaw, pitch);


        }

    }

    private boolean isVillagerAtLocation(Location location) {
        for (Entity entity : location.getWorld().getNearbyEntities(location, 1, 1, 1)) {
            if (entity instanceof Villager) {
                return true;
            }
        }
        return false;

    }
}
