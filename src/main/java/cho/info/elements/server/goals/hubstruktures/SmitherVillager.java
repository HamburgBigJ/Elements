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

public class SmitherVillager implements Listener {

    private static final String VILLAGER_NAME_PREFIX = ChatColor.GOLD + "Smith ";
    private static final String VILLAGER_NAME_SUFFIX = ChatColor.GOLD + " / ";

    private final ConfigManager configManager;

    public SmitherVillager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public void villagerSpawn() {
        Object smitherStageObj = configManager.getPublicVar("SmitherGoal");
        int smitherStage = (smitherStageObj != null) ? (int) smitherStageObj : 0;

        if (smitherStage == 0) {
            Location villagerLocation = new Location(Bukkit.getWorld("world"), -7.5, 69, -0.5);
            if (!isVillagerAtLocation(villagerLocation)) {
                Villager villager = (Villager) Bukkit.getWorld("world").spawnEntity(villagerLocation, EntityType.VILLAGER);
                configureVillager(villager);

                int smiterXp = getConfigValue("SmitherGoalXp");
                int smitherMaxXp = getConfigValue("SmitherGoalMaxXp");

                villager.setCustomName(createVillagerName(smiterXp, smitherMaxXp));
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

            int smitherStage = getConfigValue("SmitherGoal");
            int smiterXp = getConfigValue("SmitherGoalXp");
            int smitherMaxXp = getConfigValue("SmitherGoalMaxXp");

            if (villager.getCustomName() != null && villager.getCustomName().equals(createVillagerName(smiterXp, smitherMaxXp))) {
                if (smiterXp >= smitherMaxXp) {
                    smitherStage = 1;
                    configManager.setPublicVar("SmitherGoal", smitherStage);
                    villager.remove();

                    //Clones

                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "clone -32 81 65 -26 77 69 -11 69 -5");

                    configManager.setPublicVar("SmithithVillager", 1);
                }

                if (player.getLevel() >= 10) {
                    player.sendMessage(ChatColor.GOLD + "You have deposited 10 levels!");
                    player.setLevel(player.getLevel() - 10);

                    smiterXp += 10;
                    configManager.setPublicVar("SmitherGoalXp", smiterXp);

                    villager.setCustomName(createVillagerName(smiterXp, smitherMaxXp));
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

    private String createVillagerName(int smiterXp, int smitherMaxXp) {
        return VILLAGER_NAME_PREFIX + ChatColor.GREEN + smiterXp + VILLAGER_NAME_SUFFIX + ChatColor.GREEN + smitherMaxXp;
    }
}
