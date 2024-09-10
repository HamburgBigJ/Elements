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
        // Überprüfe, ob der Spieler mit der rechten Maustaste auf einen Block geklickt hat
        if (event.getAction().toString().contains("RIGHT_CLICK_BLOCK")) {
            Player player = event.getPlayer();
            ItemStack itemInHand = player.getInventory().getItemInMainHand();
            Block clickedBlock = event.getClickedBlock();

            if (clickedBlock != null) {
                // Überprüfe, ob das Item in der Hand ein Stick ist und den richtigen Namen hat
                if (itemInHand.getType() == Material.STICK && itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasDisplayName()) {
                    String displayName = itemInHand.getItemMeta().getDisplayName();

                    if (displayName.equals(steinSpalterName) && clickedBlock.getType() == deepslateMaterial) {
                        // Entferne den Block und das Item aus dem Inventar des Spielers
                        clickedBlock.setType(Material.AIR);

                        // Reduziere die Menge des Items „Stone Splitter“ im Inventar des Spielers um 1
                        ItemStack stoneSplitter = new ItemStack(Material.STICK, 1);
                        stoneSplitter.getItemMeta().setDisplayName(steinSpalterName);
                        player.getInventory().removeItem(stoneSplitter);

                        // Update die Menge des Items in der Hand des Spielers
                        ItemStack updatedItemInHand = itemInHand.clone();
                        updatedItemInHand.setAmount(updatedItemInHand.getAmount() - 1);
                        player.getInventory().setItemInMainHand(updatedItemInHand);

                        // Code für den stinspalter mini boss


                    }
                }
            }
        }
    }
}
