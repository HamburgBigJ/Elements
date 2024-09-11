package cho.info.elements.server.events;

import cho.info.elements.managers.ConfigManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class LoadPlayer implements Listener {

    public ConfigManager configManager;

    public LoadPlayer(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @EventHandler
    public void onLoadPlayer(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Retrieve player health value from config
        Object playerHealthObj = configManager.getPlayerValue(player, "health");

        // Convert the retrieved value to an int, default to 0 if null
        int playerHealth = (playerHealthObj != null) ? (int) playerHealthObj : 0;

        // Convert int to double and set the player's health
        // player.setHealth((double) playerHealth); incorcect
        player.setMaxHealth((double) playerHealth);

    }

}
