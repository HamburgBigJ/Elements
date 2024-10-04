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

public class LotaryVillager implements Listener {


    public ConfigManager configManager;

    private static final String VILLAGER_NAME_PREFIX = ChatColor.GOLD + "Lottery ";
    private static final String VILLAGER_NAME_SUFFIX = ChatColor.GOLD + " / ";

    public LotaryVillager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public void villagerSpawn() {
        Object loteryStageObj = configManager.getPublicVar("LoreryVillagerGoal");
        int loteryStage = (loteryStageObj != null) ? (int) loteryStageObj : 0;

        if (loteryStage == 0) {
            Location villagerLocation = new Location(Bukkit.getWorld("world"), 10.5, 69, 3.5);
            if (!isVillagerAtLocation(villagerLocation)) {
                Villager villager = (Villager) Bukkit.getWorld("world").spawnEntity(villagerLocation, EntityType.VILLAGER);
                configureVillager(villager);

                int loteryXp = getConfigValue("LoreryVillagerGoalXp");
                int loteryMaxXp = getConfigValue("LoreryVillagerGoalMaxXp");

                villager.setCustomName(createVillagerName(loteryXp, loteryMaxXp));
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

        float yaw = 180.0f; // 180 degrees for North
        float pitch = 0.0f; // 0 degrees for a straight orientation
        villager.setRotation(yaw, pitch);
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

            int loteryStage = getConfigValue("LoreryVillagerGoal");
            int loteryXp = getConfigValue("LoreryVillagerGoalXp");
            int loteryMaxXp = getConfigValue("LoreryVillagerGoalMaxXp");

            if (villager.getCustomName() != null && villager.getCustomName().equals(createVillagerName(loteryXp, loteryMaxXp))) {
                if (loteryXp >= loteryMaxXp) {
                    loteryStage = 1;
                    configManager.setPublicVar("LoreryVillagerGoal", loteryStage);
                    villager.remove();

                    // Führe den Clone-Befehl aus
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "clone -10 82 69 -16 77 65 7 69 3");

                    configManager.setPublicVar("LoteryVillager", 1);
                }

                if (player.getLevel() >= 10) {
                    player.sendMessage(ChatColor.GOLD + "You have deposited 10 levels!");
                    player.setLevel(player.getLevel() - 10);

                    loteryXp += 10;
                    configManager.setPublicVar("LoreryVillagerGoalXp", loteryXp);

                    villager.setCustomName(createVillagerName(loteryXp, loteryMaxXp));
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
