package cho.info.elements.player.gui.collections;

import cho.info.elements.managers.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ClickEvent implements Listener {

    public ConfigManager configManager;

    public ClickEvent(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        ItemStack itemStack = event.getCurrentItem();

        // Überprüfe, ob itemStack nicht null ist und ob es Metadaten hat
        if (itemStack != null && itemStack.hasItemMeta()) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta != null && itemMeta.hasDisplayName()) {
                String displayName = itemMeta.getDisplayName();

                if (displayName.equals(ChatColor.GRAY + " ")) {
                    event.setCancelled(true);
                } else if (displayName.startsWith(ChatColor.RED + "Locked")) {
                    event.setCancelled(true);
                } else if (displayName.startsWith(ChatColor.GREEN + "Unlocked")) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
