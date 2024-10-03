package cho.info.elements.player.gui.collections;

import cho.info.elements.managers.ConfigManager;
import cho.info.elements.managers.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CollectRegister implements Listener {

    public ConfigManager configManager;
    public ItemManager itemManager;

    public CollectRegister(ConfigManager configManager, ItemManager itemManager) {
        this.configManager = configManager;
        this.itemManager = itemManager;
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
        player.sendMessage(ChatColor.GREEN + "Du hast 100 " + collection + " gesammelt! Hier ist deine Belohnung.");
        int playerxp = (int) player.getLevel();
        playerxp = playerxp + 20;
        player.setLevel(playerxp);
        // Füge Code hinzu, um Belohnungen zu geben, z. B. Items oder XP


    }

    // Methode für Belohnung bei 200
    private void giveRewardFor200(Player player, String collection) {
        player.sendMessage(ChatColor.GREEN + "Du hast 200 " + collection + " gesammelt! Hier ist eine größere Belohnung.");
        // Füge Code für andere Belohnungen hinzu
        int playerxp = (int) player.getLevel();
        playerxp = playerxp + 50;
        player.setLevel(playerxp);

        giveHelthFute(player);

    }

    // Methode für Belohnung bei 300
    private void giveRewardFor300(Player player, String collection) {
        player.sendMessage(ChatColor.GREEN + "Du hast 300 " + collection + " gesammelt! Hier ist eine noch größere Belohnung.");
        // Füge Code für die Belohnung bei 300 hinzu
        int playerxp = (int) player.getLevel();
        playerxp = playerxp + 80;
        player.setLevel(playerxp);
    }

    private void giveRewardFor400(Player player, String collection) {
        player.sendMessage(ChatColor.GREEN + "Du hast 400 " + collection + " gesammelt! Hier ist eine noch größere Belohnung.");
        // Füge Code für die Belohnung bei 400 hinzu
        int playerxp = (int) player.getLevel();
        playerxp = playerxp + 100;
        player.setLevel(playerxp);

        giveHelthFute(player);
    }

    private void giveRewardFor500(Player player, String collection) {
        player.sendMessage(ChatColor.GREEN + "Du hast 500 " + collection + " gesammelt! Hier ist eine noch größere Belohnung.");
        // Füge Code für die Belohnung bei 500 hinzu
        int playerxp = (int) player.getLevel();
        playerxp = playerxp + 150;
        player.setLevel(playerxp);
    }

    private void giveRewardFor600(Player player, String collection) {
        player.sendMessage(ChatColor.GREEN + "Du hast 600 " + collection + " gesammelt! Hier ist eine noch größere Belohnung.");
        // Füge Code für die Belohnung bei 600 hinzu
        int playerxp = (int) player.getLevel();
        playerxp = playerxp + 180;
        player.setLevel(playerxp);

        giveHelthFute(player);
    }

    private void giveRewardFor700(Player player, String collection) {
        player.sendMessage(ChatColor.GREEN + "Du hast 700 " + collection + " gesammelt! Hier ist eine noch größere Belohnung.");
        // Füge Code für die Belohnung bei 700 hinzu
        int playerxp = (int) player.getLevel();
        playerxp = playerxp + 200;
        player.setLevel(playerxp);
    }

    private void giveRewardFor800(Player player, String collection) {
        player.sendMessage(ChatColor.GREEN + "Du hast 800 " + collection + " gesammelt! Hier ist eine noch größere Belohnung.");
        // Füge Code für die Belohnung bei 800 hinzu
        int playerxp = (int) player.getLevel();
        playerxp = playerxp + 250;
        player.setLevel(playerxp);

        giveHelthFute(player);
    }

    private void giveRewardFor900(Player player, String collection) {
        player.sendMessage(ChatColor.GREEN + "Du hast 900 " + collection + " gesammelt! Hier ist eine noch größere Belohnung.");
        // Füge Code für die Belohnung bei 900 hinzu
        int playerxp = (int) player.getLevel();
        playerxp = playerxp + 280;
        player.setLevel(playerxp);

        giveHelthFute(player);
    }

    private void giveHelthFute(Player player) {
        List<String> healtLore = itemManager.createLore(ChatColor.GRAY + "Click to use!");
        ItemStack healtFruit = itemManager.createItem(Material.FERMENTED_SPIDER_EYE, 1, ChatColor.GOLD + "Health Fruit", healtLore);

        player.getInventory().addItem(healtFruit);
    }
}
