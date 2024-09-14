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

public class LibarianVillager implements Listener {

    public ConfigManager configManager;

    private static final String VILLAGER_NAME_PREFIX = ChatColor.GOLD + "Librarian ";
    private static final String VILLAGER_NAME_SUFFIX = ChatColor.GOLD + " / ";

    public LibarianVillager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public void villagerSpawn() {
        Object libarianStageObj = configManager.getPublicVar("LibarianGoal");
        int libarianStage = (libarianStageObj != null) ? (int) libarianStageObj : 0;

        if (libarianStage == 0) {
            Location villagerLocation = new Location(Bukkit.getWorld("world"), -8.5, 69, 3.5);
            if (!isVillagerAtLocation(villagerLocation)) {
                Villager villager = (Villager) Bukkit.getWorld("world").spawnEntity(villagerLocation, EntityType.VILLAGER);
                configureVillager(villager);

                int libarianXp = getConfigValue("LibarianGoalXp");
                int libarianMaxXp = getConfigValue("LibarianGoalMaxXp");

                villager.setCustomName(createVillagerName(libarianXp, libarianMaxXp));
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

            int libarianStage = getConfigValue("LibarianGoal");
            int libarianXp = getConfigValue("LibarianGoalXp");
            int libarianMaxXp = getConfigValue("LibarianGoalMaxXp");

            if (villager.getCustomName() != null && villager.getCustomName().equals(createVillagerName(libarianXp, libarianMaxXp))) {
                if (libarianXp >= libarianMaxXp) {
                    libarianStage = 1;
                    configManager.setPublicVar("LibarianGoal", libarianStage);
                    villager.remove();

                    // Führe den Clone-Befehl aus
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "clone -34 81 69 -40 77 65 -11 69 3");
                }

                if (player.getLevel() >= 10) {
                    player.sendMessage(ChatColor.GOLD + "You have deposited 10 levels!");
                    player.setLevel(player.getLevel() - 10);

                    libarianXp += 10;
                    configManager.setPublicVar("LibarianGoalXp", libarianXp);

                    villager.setCustomName(createVillagerName(libarianXp, libarianMaxXp));
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

    private String createVillagerName(int libarianXp, int libarianMaxXp) {
        return VILLAGER_NAME_PREFIX + ChatColor.GREEN + libarianXp + VILLAGER_NAME_SUFFIX + ChatColor.GREEN + libarianMaxXp;
    }
}
