package cho.info.elements.server.events;

import cho.info.elements.managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerRespawn implements Listener {

    public ConfigManager configManager;
    public JavaPlugin plugin;

    public PlayerRespawn(JavaPlugin plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        Object homedimesionobj = configManager.getPlayerValue(player, "HomeDimension");

        int homedimension = (homedimesionobj != null) ? (int) homedimesionobj : 0;// 1 = Skyblock 2 = StoneBlock 3 = WhaterBlock

        if (homedimension == 1) {
            Location location = new Location(Bukkit.getWorld("world_skyblock"), 1.5, 70, 1.5);

            event.setRespawnLocation(location);
            player.teleport(location);
            player.setGameMode(GameMode.SURVIVAL);
        } else if (homedimension == 2) {
            Location location = new Location(Bukkit.getWorld("world_stone"), 1.5, 70, 1.5);

            event.setRespawnLocation(location);
            player.teleport(location);
            player.setGameMode(GameMode.SURVIVAL);
        } else if (homedimension == 3) {
            Location location = new Location(Bukkit.getWorld("world_whater"), 1.5, 70, 1.5);

            event.setRespawnLocation(location);
            player.teleport(location);
            player.setGameMode(GameMode.SURVIVAL);
        } else if (homedimension == 0) {
            Location location = new Location(Bukkit.getWorld("world"), 1.5, 70, 1.5);

            event.setRespawnLocation(location);
            player.teleport(location);
            player.setGameMode(GameMode.ADVENTURE);
        }

        player.setTotalExperience(player.getTotalExperience() / 3);
        //

    }


}
