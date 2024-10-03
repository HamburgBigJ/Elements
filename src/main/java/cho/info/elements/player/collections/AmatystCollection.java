package cho.info.elements.player.collections;

import cho.info.elements.managers.ConfigManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class AmatystCollection implements Listener {

    public ConfigManager configManager;

    public AmatystCollection(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @EventHandler
    public void onAmatystCollection(BlockBreakEvent event) {
        for (ItemStack drop : event.getBlock().getDrops()) {
            if (drop.getType() == Material.AMETHYST_SHARD) {
                Player player = event.getPlayer();
                int amatystCollected = (configManager.getPlayerValue(player, "Amethyst") != null) ? (int) configManager.getPlayerValue(player, "Amethyst") : 0;
                amatystCollected = amatystCollected + 1;
                configManager.setPlayerValue(player, "Amethyst", amatystCollected);
                break;
            }
        }
    }
}