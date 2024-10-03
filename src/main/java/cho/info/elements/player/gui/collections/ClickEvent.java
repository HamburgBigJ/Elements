package cho.info.elements.player.gui.collections;

import cho.info.elements.managers.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ClickEvent implements Listener {

    public ConfigManager configManager;

    public ClickEvent(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        ItemStack itemStack = event.getCurrentItem();

        String displayName = itemStack.getItemMeta().getDisplayName();

        if (displayName.equals(ChatColor.GRAY + " ")) {
            event.setCancelled(true);

        } else if (displayName.startsWith(ChatColor.RED + "Locked")) {
            event.setCancelled(true);
        } else if (displayName.startsWith(ChatColor.GREEN + "Unlocked")) {
            event.setCancelled(true);
        }
    }

}
