package cho.info.elements.player.items;

import cho.info.elements.managers.ConfigManager;
import cho.info.elements.managers.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.block.Action;
import org.bukkit.entity.Player;

import java.util.List;

public class HealtFrutItem implements Listener {

    private final ConfigManager configManager;
    private final ItemManager itemManager;

    public HealtFrutItem(ConfigManager configManager, ItemManager itemManager) {
        this.configManager = configManager;
        this.itemManager = itemManager;
    }

    @EventHandler
    public void onHealtFrutItem(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        // Überprüfen, ob der Rechtsklick ausgeführt wurde
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            // Überprüfen, ob das Item das richtige ist
            if (item != null && item.getType() == Material.FERMENTED_SPIDER_EYE && item.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Health Fruit")) {

                // Nachricht senden und 1 Herz hinzufügen
                player.sendMessage(ChatColor.GREEN + "You have used the Health Fruit!");

                // Health-Basiswert
                int healts = (configManager.getPlayerValue(player, "health") != null) ? (int) configManager.getPlayerValue(player, "health") : 0;
                healts += 2; // Füge 1 Herz (2 Health-Punkte) hinzu

                // Setze die neue maximale Gesundheit des Spielers
                player.setMaxHealth((double) healts);
                configManager.setPlayerValue(player, "health", healts);

                // Entferne das Item nach der Benutzung
                if (item.getAmount() > 1) {
                    item.setAmount(item.getAmount() - 1); // Menge reduzieren
                } else {
                    player.getInventory().setItemInMainHand(null); // Entfernen, wenn nur 1 Item
                }

                player.sendMessage(ChatColor.GREEN + "Your maximum health has been increased by 1 heart!");
            }
        }
    }
}
