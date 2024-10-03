package cho.info.elements.player.collections;

import cho.info.elements.managers.ConfigManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.List;

public class OakCollection implements Listener {

    public ConfigManager configManager;
    public List<Material> materialList;

    public OakCollection(ConfigManager configManager) {
        this.configManager = configManager;

        this.materialList = new ArrayList<>();

        this.materialList.add(Material.OAK_LOG);
        this.materialList.add(Material.DARK_OAK_LOG);
        this.materialList.add(Material.JUNGLE_LOG);
        this.materialList.add(Material.ACACIA_LOG);
        this.materialList.add(Material.BIRCH_LOG);
        this.materialList.add(Material.SPRUCE_LOG);

    }

    // Method to handle oak collection
    @EventHandler
    public void onOakCollect(BlockBreakEvent event) {
        // Handle oak collection
        if (materialList.contains(event.getBlock().getType())) {
            // Get the player who broke the block
            Player player = event.getPlayer();

            int oakCollected = (configManager.getPlayerValue(player, "Oak") != null) ? (int) configManager.getPlayerValue(player, "Oak") : 0;

            oakCollected = oakCollected + 1;

            // Update the player's oak collection value
            configManager.setPlayerValue(player, "Oak", oakCollected);
        }
    }
}
