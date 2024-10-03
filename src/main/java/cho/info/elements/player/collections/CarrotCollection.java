package cho.info.elements.player.collections;

import cho.info.elements.managers.ConfigManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class CarrotCollection implements Listener {

    public ConfigManager configManager;

    public CarrotCollection(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @EventHandler
    public void onAppleCollection(BlockBreakEvent event) {
        for (ItemStack drop : event.getBlock().getDrops()) {
            if (drop.getType() == Material.APPLE) {
                Player player = event.getPlayer();
                int carrotCollection = (configManager.getPlayerValue(player, "Carrot") != null) ? (int) configManager.getPlayerValue(player, "Carrot") : 0;
                carrotCollection = carrotCollection + 1;
                configManager.setPlayerValue(player, "Carrot", carrotCollection);
                break;
            }
        }
    }
}
