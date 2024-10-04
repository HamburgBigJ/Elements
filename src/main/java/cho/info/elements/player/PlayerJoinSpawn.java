package cho.info.elements.player;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinSpawn implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Set the player's spawn location to the world's spawn location
        Location location = new Location(Bukkit.getWorld("world"), 1.5, 70, 1.5);
        event.getPlayer().teleport(location);
    }

}
