package cho.info.elements.player.collections;

import cho.info.elements.managers.ConfigManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

public class EcoShardCollection implements Listener {

    public ConfigManager configManager;

    public EcoShardCollection(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @EventHandler
    public void onEcoShardCollection(CraftItemEvent event) {
        ItemStack craftedItem = event.getCurrentItem();
        if (craftedItem != null && craftedItem.getType() == Material.ECHO_SHARD) {
            Player player = (Player) event.getWhoClicked();
            int ecoShardCollected = (configManager.getPlayerValue(player, "Eco_Shard") != null) ? (int) configManager.getPlayerValue(player, "Eco_Shard") : 0;
            ecoShardCollected = ecoShardCollected + 1;
            configManager.setPlayerValue(player, "Eco_Shard", ecoShardCollected);
        }
    }
}