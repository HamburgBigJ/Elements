package cho.info.elements.player.gui.collections;

import cho.info.elements.managers.ConfigManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class CollectRegister implements Listener {

    public ConfigManager configManager;

    public CollectRegister(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @EventHandler
    public void onUpdateCollection(BlockBreakEvent event) {

        Player player = event.getPlayer();

        // Listen der Sammlungen und deren Belohnungen
        String[] collections = {"cobblestone", "Oak", "Wheat", "Carrot", "Potato", "Amethyst", "Eco_Shard", "Kelp", "Apple"};

        for (String collection : collections) {
            int value = (int) configManager.getPlayerValue(player, collection);


            if (value == 100) {
                giveRewardFor100(player, collection);
                value = value + 1;
                configManager.setPlayerValue(player, collection, value);
            } else if (value == 200) {
                giveRewardFor200(player, collection);
                value = value + 1;
                configManager.setPlayerValue(player, collection, value);
            } else if (value == 300) {
                giveRewardFor300(player, collection);
                value = value + 1;
                configManager.setPlayerValue(player, collection, value);
            } else if (value == 400) {
                giveRewardFor400(player, collection);
                value = value + 1;
                configManager.setPlayerValue(player, collection, value);
            } else if (value == 500) {
                giveRewardFor500(player, collection);
                value = value + 1;
                configManager.setPlayerValue(player, collection, value);
            } else if (value == 600) {
                giveRewardFor600(player, collection);
                value = value + 1;
                configManager.setPlayerValue(player, collection, value);
            } else if (value == 700) {
                giveRewardFor700(player, collection);
                value = value + 1;
                configManager.setPlayerValue(player, collection, value);
            } else if (value == 800) {
                giveRewardFor800(player, collection);
                value = value + 1;
                configManager.setPlayerValue(player, collection, value);
            } else if (value == 900) {
                giveRewardFor900(player, collection);
                value = value + 1;
                configManager.setPlayerValue(player, collection, value);
            }

        }
    }

    // Methode für Belohnung bei 100
    private void giveRewardFor100(Player player, String collection) {
        // Beispiel: Füge ein bestimmtes Item hinzu
        player.sendMessage("Du hast 100 " + collection + " gesammelt! Hier ist deine Belohnung.");
        // Füge Code hinzu, um Belohnungen zu geben, z. B. Items oder XP
    }

    // Methode für Belohnung bei 200
    private void giveRewardFor200(Player player, String collection) {
        player.sendMessage("Du hast 200 " + collection + " gesammelt! Hier ist eine größere Belohnung.");
        // Füge Code für andere Belohnungen hinzu
    }

    // Methode für Belohnung bei 300
    private void giveRewardFor300(Player player, String collection) {
        player.sendMessage("Du hast 300 " + collection + " gesammelt! Hier ist eine noch größere Belohnung.");
        // Füge Code für die Belohnung bei 300 hinzu
    }

    private void giveRewardFor400(Player player, String collection) {
        player.sendMessage("Du hast 400 " + collection + " gesammelt! Hier ist eine noch größere Belohnung.");
        // Füge Code für die Belohnung bei 400 hinzu
    }

    private void giveRewardFor500(Player player, String collection) {
        player.sendMessage("Du hast 500 " + collection + " gesammelt! Hier ist eine noch größere Belohnung.");
        // Füge Code für die Belohnung bei 500 hinzu
    }

    private void giveRewardFor600(Player player, String collection) {
        player.sendMessage("Du hast 600 " + collection + " gesammelt! Hier ist eine noch größere Belohnung.");
        // Füge Code für die Belohnung bei 600 hinzu
    }

    private void giveRewardFor700(Player player, String collection) {
        player.sendMessage("Du hast 700 " + collection + " gesammelt! Hier ist eine noch größere Belohnung.");
        // Füge Code für die Belohnung bei 700 hinzu
    }

    private void giveRewardFor800(Player player, String collection) {
        player.sendMessage("Du hast 800 " + collection + " gesammelt! Hier ist eine noch größere Belohnung.");
        // Füge Code für die Belohnung bei 800 hinzu
    }

    private void giveRewardFor900(Player player, String collection) {
        player.sendMessage("Du hast 900 " + collection + " gesammelt! Hier ist eine noch größere Belohnung.");
        // Füge Code für die Belohnung bei 900 hinzu
    }
}
