package cho.info.elements.server.villagers.stants;

import cho.info.elements.managers.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SmithInteractStand implements Listener {

    public ConfigManager configManager;

    public SmithInteractStand(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @EventHandler
    public void onSmithInteract(PlayerInteractEntityEvent event) {

        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if (event instanceof Villager) {
            Villager villager = (Villager) entity;
            if (villager.getCustomName().equals(ChatColor.DARK_AQUA + "Smith Villager")) {
                Inventory anvilInventory = player.getServer().createInventory(null, 27, ChatColor.DARK_AQUA + "Upgrade your items!");

                ItemStack fillItem = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
                ItemStack air = new ItemStack(Material.AIR);

                if (fillItem != null) {
                    ItemMeta fillItemMeta = fillItem.getItemMeta();

                    fillItemMeta.setDisplayName(ChatColor.GREEN + " ");
                    fillItem.setItemMeta(fillItemMeta);
                }

                for (int i = 0; i < 27; i++) {
                    anvilInventory.setItem(i, fillItem);
                }

                anvilInventory.setItem(11, air);
                anvilInventory.setItem(14, air);
                anvilInventory.setItem(17, air);



            }
        }

    }
}
