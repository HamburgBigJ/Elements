package cho.info.elements.server.villagers.stants;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;

public class SmithVillagerStand {

    public void spawnVillagerSmith() {
        Location location = new Location(Bukkit.getWorld("world"), -8.5, 69, -3.5);

        if (!isVillagerAtLocation(location)){
            Villager villager = (Villager) Bukkit.getWorld("world").spawnEntity(location, org.bukkit.entity.EntityType.VILLAGER);
            villager.setCustomNameVisible(true);
            villager.setInvulnerable(true);
            villager.setCustomName(ChatColor.DARK_AQUA + "Smith Villager");
            villager.setAI(false);
            villager.setSilent(true);
            villager.setNoPhysics(true);
            villager.setVillagerLevel(5);

            // Set the villager's facing direction to North
            float yaw = 0.0f;
            float pitch = 0.0f;
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
