package cho.info.elements.player.gui;

import cho.info.elements.managers.ConfigManager;
import cho.info.elements.managers.ItemManager;
import cho.info.elements.managers.VariableManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class EnderChest implements Listener {

    private final JavaPlugin plugin;
    private final VariableManager variableManager;
    private final ItemManager itemManager;
    private final ConfigManager configManager;

    public EnderChest(JavaPlugin plugin, ConfigManager configManager, VariableManager variableManager, ItemManager itemManager) {
        this.configManager = configManager;
        this.variableManager = variableManager;
        this.plugin = plugin;
        this.itemManager = itemManager;

        if (this.itemManager == null) {
            plugin.getLogger().warning("ItemManager is not initialized.");
        }
    }

    @EventHandler
    public void onEnderGui(PlayerInteractEvent event) {
        if (event.getAction().isRightClick() && event.hasBlock()) {
            Block block = event.getClickedBlock();
            Player player = event.getPlayer();
            Inventory enderchest = player.getEnderChest();

            // Sicherstellen, dass "EnderGui" korrekt referenziert wird
            Object endertierObj = configManager.getPlayerValue(player, "EnderGui");
            int endertier = (endertierObj instanceof Integer) ? (int) endertierObj : 0;

            if (block != null && block.getType() == Material.ENDER_CHEST) {
                if (endertier >= 0) {
                    List<String> teleportLore = itemManager.createLore(ChatColor.WHITE + "Click to Teleport to destination");
                    ItemStack teleport = itemManager.createItem(Material.ENDER_EYE, 1, ChatColor.GOLD + "Teleport", teleportLore);
                    enderchest.setItem(23, teleport);
                }
            }
        }
    }

    @EventHandler
    public void onEnderInteract(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        ItemStack clickedItem = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();

        // Überprüfen, ob das Inventar das Enderchest des Spielers ist
        if (inventory.equals(player.getEnderChest())) {
            if (clickedItem != null && clickedItem.hasItemMeta()) {
                ItemMeta meta = clickedItem.getItemMeta();
                if (meta != null && meta.hasDisplayName()) {
                    String displayName = meta.getDisplayName();

                    // Überprüfen, ob der angeklickte Gegenstand ein Teleport-Gegenstand ist
                    if (displayName.equals(ChatColor.GOLD + "Teleport")) {
                        Location hubLocation = variableManager.getLocation("HubWorld");
                        player.teleport(hubLocation);
                        /*if (hubLocation != null) { // Temp For Test Reasons
                            player.teleport(hubLocation);
                            player.sendMessage(ChatColor.GREEN + "Teleport!");
                        } else {
                            player.sendMessage(ChatColor.RED + "Teleport location not found.");
                            plugin.getLogger().warning("Hub location not found in the configuration.");
                        }
                        */
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
