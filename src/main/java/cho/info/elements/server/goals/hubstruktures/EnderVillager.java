package cho.info.elements.server.goals.hubstruktures;

import cho.info.elements.managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class EnderVillager implements Listener {

    public ConfigManager configManager;

    private static final String VILLAGER_NAME_PREFIX = ChatColor.GOLD + "EnderVillager ";
    private static final String VILLAGER_NAME_SUFFIX = ChatColor.GOLD + " / ";

    public EnderVillager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public void villagerSpawn() {
        Object enderStageObj = configManager.getPublicVar("EnderVillagerGoal");
        int enderStage = (enderStageObj != null) ? (int) enderStageObj : 0;

        if (enderStage == 0) {
            Location villagerLocation = new Location(Bukkit.getWorld("world"), -8.5, 69, 3.5);
            if (!isVillagerAtLocation(villagerLocation)) {
                Villager villager = (Villager) Bukkit.getWorld("world").spawnEntity(villagerLocation, EntityType.VILLAGER);
                configureVillager(villager);

                int enderXp = getConfigValue("EnderVillagerGoalXp");
                int enderMaxXp = getConfigValue("EnderVillagerGoalMaxXp");

                villager.setCustomName(createVillagerName(enderXp, enderMaxXp));
            }
        }
    }

    private void configureVillager(Villager villager) {
        villager.setNoPhysics(true);
        villager.setAI(false);
        villager.setSilent(true);
        villager.setInvisible(false);
        villager.setCollidable(false);
        villager.setCustomNameVisible(true);
    }

    private boolean isVillagerAtLocation(Location location) {
        for (Entity entity : location.getWorld().getNearbyEntities(location, 1, 1, 1)) {
            if (entity instanceof Villager) {
                return true;
            }
        }
        return false;
    }

    @EventHandler
    public void onVillagerInteract(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Villager) {
            Villager villager = (Villager) event.getRightClicked();
            Player player = event.getPlayer();

            int enderStage = getConfigValue("EnderVillagerGoal");
            int enderXp = getConfigValue("EnderVillagerGoalXp");
            int enderMaxXp = getConfigValue("EnderVillagerGoalMaxXp");

            if (villager.getCustomName() != null && villager.getCustomName().equals(createVillagerName(enderXp, enderMaxXp))) {
                if (enderXp >= enderMaxXp) {
                    enderStage = 1;
                    configManager.setPublicVar("EnderVillagerGoal", enderStage);
                    villager.remove();

                    // Führe den Clone-Befehl aus
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "clone -24 81 65 -18 77 69 7 69 -5");
                }

                if (player.getLevel() >= 10) {
                    player.sendMessage(ChatColor.GOLD + "You have deposited 10 levels!");
                    player.setLevel(player.getLevel() - 10);

                    enderXp += 10;
                    configManager.setPublicVar("EnderVillagerGoalXp", enderXp);

                    villager.setCustomName(createVillagerName(enderXp, enderMaxXp));
                } else {
                    player.sendMessage("Du hast nicht Genug Level!");
                }
            }
        }
    }

    private int getConfigValue(String key) {
        Object value = configManager.getPublicVar(key);
        return (value != null) ? (int) value : 0;
    }

    private String createVillagerName(int enderXp, int enderMaxXp) {
        return VILLAGER_NAME_PREFIX + ChatColor.GREEN + enderXp + VILLAGER_NAME_SUFFIX + ChatColor.GREEN + enderMaxXp;
    }
}
