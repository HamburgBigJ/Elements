package cho.info.elements.server.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class SteinSpalterHit implements Listener {

    private final String steinSpalterName = ChatColor.DARK_PURPLE + "Stone Splitter";
    private final Material deepslateMaterial = Material.DEEPSLATE;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        // Check if the player right-clicked on a block
        if (event.getAction().toString().contains("RIGHT_CLICK_BLOCK")) {
            Player player = event.getPlayer();
            ItemStack itemInHand = player.getInventory().getItemInMainHand();
            Block clickedBlock = event.getClickedBlock();

            if (clickedBlock != null) {
                // Check if the item in hand is a stick and has the correct name
                if (itemInHand.getType() == Material.STICK && itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasDisplayName()) {
                    String displayName = itemInHand.getItemMeta().getDisplayName();

                    if (displayName.equals(steinSpalterName) && clickedBlock.getType() == deepslateMaterial) {
                        // Remove the block and the item from the player's inventory
                        clickedBlock.setType(Material.AIR);

                        // Decrease the amount of "Stone Splitter" in the player's inventory by 1
                        ItemStack stoneSplitter = new ItemStack(Material.STICK, 1);
                        stoneSplitter.getItemMeta().setDisplayName(steinSpalterName);
                        player.getInventory().removeItem(stoneSplitter);

                        // Update the amount of the item in the player's hand
                        ItemStack updatedItemInHand = itemInHand.clone();
                        updatedItemInHand.setAmount(updatedItemInHand.getAmount() - 1);
                        player.getInventory().setItemInMainHand(updatedItemInHand);

                        // Code for the "Steinspalter" mini boss

                    }
                }
            }
        }
    }
}
