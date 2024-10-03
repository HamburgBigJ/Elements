package cho.info.elements.player.gui;

import cho.info.elements.managers.ConfigManager;
import cho.info.elements.managers.ItemManager;
import cho.info.elements.player.gui.collections.CollectionListInv;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class CollectionInv implements Listener {

    public ConfigManager configManager;
    public ItemManager itemManager;
    public JavaPlugin plugin;
    public CollectionListInv collectionListInv;

    public CollectionInv(JavaPlugin plugin, ConfigManager configManager, ItemManager itemManager, CollectionListInv collectionListInv) {
        this.configManager = configManager;
        this.plugin = plugin;
        this.itemManager = itemManager;
        this.collectionListInv = collectionListInv;
    }

    @EventHandler
    public void onCollectionSelect(InventoryClickEvent event) {
        InventoryView view = event.getView();
        Inventory inventory = event.getClickedInventory();
        Player player = (Player) event.getWhoClicked();

        // Check if the clicked inventory is not null and has the correct title
        if (inventory != null && view.getTitle().equals(ChatColor.DARK_PURPLE + "Collection")) {
            event.setCancelled(true); // Cancel the default click behavior

            // Get the clicked item
            ItemStack clickedItem = event.getCurrentItem();

            // Handle specific item clicks by checking display name
            if (clickedItem != null && clickedItem.hasItemMeta() && clickedItem.getItemMeta().hasDisplayName()) {
                String displayName = clickedItem.getItemMeta().getDisplayName();

                if (displayName.equals(ChatColor.GOLD + "Cobblestone")) {
                    // Action when "Cobblestone" is clicked
                    event.setCancelled(true);

                    int cobblestoneCollected = (configManager.getPlayerValue(player, "cobblestone") != null) ? (int) configManager.getPlayerValue(player, "cobblestone") : 0;

                    collectionListInv.buildInv("Cobblestone", cobblestoneCollected, player);

                } else if (displayName.equals(ChatColor.GOLD + "Oak")) {
                    // Action when "Oak" is clicked
                    event.setCancelled(true);

                    int oakCollected = (configManager.getPlayerValue(player, "Oak") != null) ? (int) configManager.getPlayerValue(player, "Oak") : 0;

                    collectionListInv.buildInv("Oak", oakCollected, player);

                } else if (displayName.equals(ChatColor.GOLD + "Wheat")) {
                    // Action when "Wheat" is clicked
                    event.setCancelled(true);

                    int wheatCollected = (configManager.getPlayerValue(player, "Wheat") != null) ? (int) configManager.getPlayerValue(player, "Wheat") : 0;

                    collectionListInv.buildInv("Wheat", wheatCollected, player);


                } else if (displayName.equals(ChatColor.GOLD + "Amethyst")) {
                    // Action when "Amethyst" is clicked
                    event.setCancelled(true);

                    int amethystCollected = (configManager.getPlayerValue(player, "Amethyst") != null) ? (int) configManager.getPlayerValue(player, "Amethyst") : 0;

                    collectionListInv.buildInv("Amethyst", amethystCollected, player);


                } else if (displayName.equals(ChatColor.GOLD + "Apple")) {
                    // Action when "Apple" is clicked
                    event.setCancelled(true);

                    int appleCollected = (configManager.getPlayerValue(player, "Apple") != null) ? (int) configManager.getPlayerValue(player, "Apple") : 0;

                    collectionListInv.buildInv("Apple", appleCollected, player);

                } else if (displayName.equals(ChatColor.GOLD + "Potato")) {
                    // Action when "Potato" is clicked
                    event.setCancelled(true);

                    int potatoCollected = (configManager.getPlayerValue(player, "Potato") != null) ? (int) configManager.getPlayerValue(player, "Potato") : 0;

                    collectionListInv.buildInv("Potato", potatoCollected, player);

                } else if (displayName.equals(ChatColor.GOLD + "Eco Shard")) {
                    // Action when "Eco Shard" is clicked
                    event.setCancelled(true);

                    int ecoShardCollected = (configManager.getPlayerValue(player, "Eco_Shard") != null) ? (int) configManager.getPlayerValue(player, "Eco_Shard") : 0;

                    collectionListInv.buildInv("Eco Shard", ecoShardCollected, player);

                } else if (displayName.equals(ChatColor.GOLD + "Carrot")) {
                    // Action when "Carrot" is clicked
                    event.setCancelled(true);

                    int carrotCollected = (configManager.getPlayerValue(player, "Carrot") != null) ? (int) configManager.getPlayerValue(player, "Carrot") : 0;

                    collectionListInv.buildInv("Carrot", carrotCollected, player);

                } else if (displayName.equals(ChatColor.GOLD + "Kelp")) {
                    // Action when "Kelp" is clicked
                    event.setCancelled(true);

                    int kelpCollected = (configManager.getPlayerValue(player, "Kelp") != null) ? (int) configManager.getPlayerValue(player, "Kelp") : 0;

                    collectionListInv.buildInv("Kelp", kelpCollected, player);

                }
            }
        }
    }
}
