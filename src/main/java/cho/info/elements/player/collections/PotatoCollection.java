package cho.info.elements.player.collections;

import cho.info.elements.managers.ConfigManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class PotatoCollection implements Listener {

    public ConfigManager configManager;

    public PotatoCollection(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @EventHandler
    public void onPotatoCollection(BlockBreakEvent event) {
        for (ItemStack drop : event.getBlock().getDrops()) {
            if (drop.getType() == Material.POTATO) {
                Player player = event.getPlayer();
                int potatoCollected = (configManager.getPlayerValue(player, "Potato") != null) ? (int) configManager.getPlayerValue(player, "Potato") : 0;
                potatoCollected = potatoCollected + 1;
                configManager.setPlayerValue(player, "Potato", potatoCollected);
                break;
            }
        }
    }
}