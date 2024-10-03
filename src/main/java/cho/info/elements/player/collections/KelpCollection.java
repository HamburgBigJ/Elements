package cho.info.elements.player.collections;

import cho.info.elements.managers.ConfigManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class KelpCollection implements Listener {

    public ConfigManager configManager;

    public KelpCollection(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @EventHandler
    public void onKelpCollection(BlockBreakEvent event) {

        if (event.getBlock().getType().equals(Material.KELP)) {
            Player player = event.getPlayer();

            int kelpCollected = (configManager.getPlayerValue(player, "Kelp") != null) ? (int) configManager.getPlayerValue(player, "Kelp") : 0;
            kelpCollected = kelpCollected + 1;

            configManager.setPlayerValue(player, "Kelp", kelpCollected);

        }

    }

}
