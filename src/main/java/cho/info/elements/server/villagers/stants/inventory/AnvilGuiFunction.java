package cho.info.elements.server.villagers.stants.inventory;

import cho.info.elements.Elements;
import cho.info.elements.managers.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class AnvilGuiFunction implements Listener {

    public ItemManager itemManager;
    public Elements elements;

    public AnvilGuiFunction(ItemManager itemManager, Elements elements) {
        this.itemManager = itemManager;
        this.elements = elements;
    }

    @EventHandler
    public void onAnvilGuiFunction(InventoryClickEvent event) {
        InventoryView view = event.getView();
        Inventory inventory = event.getInventory();

        // Check if the clicked inventory is the anvil and its title
        if (inventory != null && view.getTitle().equals("Upgrade your items!")) {
            int slot = event.getSlot();

            // Allow interaction only for slots 11, 14, and 17
            if (slot != 11 && slot != 14 && slot != 17) {
                event.setCancelled(true); // Cancel the event to prevent interaction

            }

            // Handle actions based on the specific slot clicked
            Player player = (Player) event.getWhoClicked();
            ItemStack item = event.getCurrentItem();

            if (slot == 11) {
                // Handle slot 11
                if (item != null && item.getType() == Material.DIAMOND_SWORD ) {
                    // Upgrade the item
                    if (item.getItemMeta().getDisplayName().toString().startsWith(ChatColor.GREEN + "Element")) {

                        //Function to upgrade the item
                        elements.getLogger().info("Elements Test");

                    }


                }
            } else if (slot == 14) {
                // Handle slot 14
                elements.getLogger().info("Elements Test");
                

            } else if (slot == 17) {
                // Handle slot 17
                elements.getLogger().info("Elements Test");

            }
        }
    }
}
