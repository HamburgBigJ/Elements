package cho.info.elements.server.fix;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class EnderChestFix implements Listener {

    @EventHandler
    public void enderFix(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        // Check if the action is right-clicking a block
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Block block = event.getClickedBlock();

            // Ensure the block is not null and is an Ender Chest in the correct world
            if (block != null && block.getType().equals(Material.ENDER_CHEST) &&
                    block.getLocation().getWorld().equals(Bukkit.getWorld("world"))) {

                // Open the player's Ender Chest inventory
                player.openInventory(player.getEnderChest());
            }
        }
    }
}
