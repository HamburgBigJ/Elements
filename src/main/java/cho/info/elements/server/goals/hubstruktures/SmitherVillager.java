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

    public ConfigManager configManager;

    public SmitherVillager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public void villagerSpawn() {
        Location villagerLocation = new Location(Bukkit.getWorld("world"), -8, 69, -1);
        if (isVillagerAtLocation(villagerLocation)) {
            Villager villager = (Villager) Bukkit.getWorld("world").spawnEntity(villagerLocation, EntityType.VILLAGER);
            villager.setNoPhysics(true);
            villager.setAI(false);
            villager.setSilent(true);
            villager.setInvisible(true);
            villager.setCollidable(false);
            villager.setCustomNameVisible(true);

            Object smitherStageObj = configManager.getPublicVar("SmitherGoal");
            Object smitherXpObj = configManager.getPublicVar("SmitherGoalXp");
            Object smitherMaxXpObj = configManager.getPublicVar("SmitherGoalMaxXp");

            int smitherStage = (smitherStageObj != null) ? (int) smitherStageObj : 0;
            int smiterXp = (smitherXpObj != null) ? (int) smitherXpObj : 0;
            int smitherMaxXp = (smitherMaxXpObj != null) ? (int) smitherMaxXpObj : 0;

            villager.setCustomName(ChatColor.GOLD + "Smith " + ChatColor.GREEN + smiterXp + ChatColor.GOLD + " / " + smitherMaxXp);



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

    @EventHandler
    public void onVilalgerInteract(PlayerInteractEntityEvent event) {

        if (event.getRightClicked() instanceof Villager) {
            Villager villager = (Villager) event.getRightClicked();
            Player player = event.getPlayer();

            Object smitherStageObj = configManager.getPublicVar("SmitherGoal");
            Object smitherXpObj = configManager.getPublicVar("SmitherGoalXp");
            Object smitherMaxXpObj = configManager.getPublicVar("SmitherGoalMaxXp");

            int smitherStage = (smitherStageObj != null) ? (int) smitherStageObj : 0;
            int smiterXp = (smitherXpObj != null) ? (int) smitherXpObj : 0;
            int smitherMaxXp = (smitherMaxXpObj != null) ? (int) smitherMaxXpObj : 0;

            if (villager.getCustomName() != null && villager.getCustomName().equals(ChatColor.GOLD + "Smith " + ChatColor.GREEN + smiterXp + ChatColor.GOLD + " / " + smitherMaxXp) ) {

                if (smiterXp >= smitherMaxXp) {
                    smitherStage = 1;
                    configManager.setPublicVar("SmitherGoal", smitherStage);
                }


                int playerLevel = player.getLevel();

                if (playerLevel >= 10) {

                    player.sendMessage(ChatColor.GOLD + "You have deposited 10 levels!");
                    playerLevel = playerLevel - 10;

                    player.setLevel(playerLevel);

                    smiterXp =  smiterXp + 10;

                    configManager.setPublicVar("SmitherGoalXp", smiterXp);

                    villager.setCustomName(ChatColor.GOLD + "Smith " + ChatColor.GREEN + smiterXp + ChatColor.GOLD + " / " + smitherMaxXp);
                }else {
                    player.sendMessage("Du hast nicht Genug Level!");

                }


            }

        }
    }

}
