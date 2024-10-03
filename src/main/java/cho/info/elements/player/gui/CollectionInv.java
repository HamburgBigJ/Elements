package cho.info.elements.player.gui;

import cho.info.elements.managers.ConfigManager;
import cho.info.elements.managers.ItemManager;
import org.bukkit.ChatColor;
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

    public CollectionInv(JavaPlugin plugin, ConfigManager configManager, ItemManager itemManager) {
        this.configManager = configManager;
        this.plugin = plugin;
        this.itemManager = itemManager;
    }

    @EventHandler
    public void onCollectionSelect(InventoryClickEvent event) {
        InventoryView view = event.getView();
        Inventory inventory = event.getClickedInventory();

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

                } else if (displayName.equals(ChatColor.GOLD + "Oak")) {
                    // Action when "Oak" is clicked
                    event.setCancelled(true);

                } else if (displayName.equals(ChatColor.GOLD + "Wheat")) {
                    // Action when "Wheat" is clicked
                    event.setCancelled(true);

                } else if (displayName.equals(ChatColor.GOLD + "Amethyst")) {
                    // Action when "Amethyst" is clicked
                    event.setCancelled(true);

                }
            }
        }
    }
}
