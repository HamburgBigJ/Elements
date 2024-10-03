package cho.info.elements.player.collections;

import cho.info.elements.managers.ConfigManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class CobblestoneCollection implements Listener {

    public ConfigManager configManager;

    public CobblestoneCollection(ConfigManager configManager) {
        this.configManager = configManager;
    }

    // Metot to handle cobblestone collection
    @EventHandler
    public void onCobblestoneCollect(BlockBreakEvent event) {
        // Check if the block broken is cobblestone
        if (event.getBlock().getType().equals(Material.STONE)) {
            // Get the player who broke the block
            Player player = event.getPlayer();

            int cobblestoneCollected = (configManager.getPlayerValue(player, "cobblestone") != null) ? (int) configManager.getPlayerValue(player, "cobblestone") : 0;

            cobblestoneCollected = cobblestoneCollected + 1;

            // Update the player's cobblestone collection value
            configManager.setPlayerValue(player, "cobblestone", cobblestoneCollected);

        }
    }
}
