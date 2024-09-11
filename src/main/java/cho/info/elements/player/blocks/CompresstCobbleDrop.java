package cho.info.elements.player.blocks;

import cho.info.elements.managers.ItemManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import java.util.List;

public class CompresstCobbleDrop implements Listener {

    public ItemManager itemManager;

    public CompresstCobbleDrop(ItemManager itemManager) {
        this.itemManager = itemManager;
    }

    @EventHandler
    public void onComprestCobbleStoneDrop(BlockBreakEvent event) {
        // Check if the broken block is DEEPSLATE
        if (event.getBlock().getType().equals(Material.DEEPSLATE)) {

            // Prevent the normal DEEPSLATE drop
            event.setDropItems(false);

            // Create custom lore for the compressed stone
            List<String> compressedStoneLore = itemManager.createLore(
                    ChatColor.GRAY + "Komprimierter Stein",
                    ChatColor.GRAY + " ",
                    ChatColor.BLUE + "" + ChatColor.BOLD + "Rare"
            );

            // Create the custom compressed cobblestone item
            ItemStack compressedStone = itemManager.createItem(
                    Material.DEEPSLATE, 1, ChatColor.BLUE + "Compressed Cobblestone", compressedStoneLore
            );

            // Get the block location
            Location blockLocation = event.getBlock().getLocation();

            // Drop the custom item at the block's location
            event.getBlock().getWorld().dropItemNaturally(blockLocation, compressedStone);
        }
    }
}
