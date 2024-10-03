package cho.info.elements.player.collections;

import cho.info.elements.managers.ConfigManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class WheatCollection implements Listener {

    public ConfigManager configManager;

    public WheatCollection(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @EventHandler
    public void onWheatCollection(BlockBreakEvent event) {
        for (ItemStack drop : event.getBlock().getDrops()) {
            if (drop.getType() == Material.WHEAT) {
                Player player = event.getPlayer();
                int wheatCollected = (configManager.getPlayerValue(player, "Wheat") != null) ? (int) configManager.getPlayerValue(player, "Wheat") : 0;
                wheatCollected = wheatCollected + 1;
                configManager.setPlayerValue(player, "Wheat", wheatCollected);
                break;
            }
        }
    }
}