package cho.info.elements.player;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerJoin implements Listener {

    public JavaPlugin plugin;

    public PlayerJoin (JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {


        Location location = new Location(Bukkit.getWorld("world"), 1.5, 70, 1.5);

        event.getPlayer().teleport(location);
    }
}
