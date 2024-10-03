package cho.info.elements.player.items;

import cho.info.elements.managers.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.block.Action;
import org.bukkit.entity.Player;

public class HealtFrutItem implements Listener {

    public ConfigManager configManager;

    public HealtFrutItem(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @EventHandler
    public void onHealtFrutItem(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        // Überprüfen, ob der Rechtsklick ausgeführt wurde
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            // Überprüfen, ob das Item, das der Spieler hält, das richtige ist (z.B. eine Frucht)
            if (item != null && item.getType() == Material.FERMENTED_SPIDER_EYE && item.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Health Fruit")) {
                // Nachricht in den Chat senden
                player.sendMessage(ChatColor.GREEN + "You have used the Health Fruit!");


                int healts = (configManager.getPlayerValue(player, "health") != null) ? (int) configManager.getPlayerValue(player, "health") : 0;

                healts = healts + 2;

                configManager.setPlayerValue(player, "health", healts);

                player.setMaxHealth((double) healts);
            }
        }
    }
}
