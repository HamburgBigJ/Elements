package cho.info.elements.player;

import cho.info.elements.managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SelectClass implements Listener {

    private static final Location SKYBLOCK_LOCATION = new Location(Bukkit.getWorld("world"), -5, 70, 11);
    private static final Location STONEBLOCK_LOCATION = new Location(Bukkit.getWorld("world"), -5, 70, 10);
    private static final Location WHATERBLOCK_LOCATION = new Location(Bukkit.getWorld("world"), -5, 70, 9);

    private static final Location SKYBLOCK_TELEPORT_LOCATION = new Location(Bukkit.getWorld("world_skyblock"), 1.5, 70, 1.5);
    private static final Location STONEBLOCK_TELEPORT_LOCATION = new Location(Bukkit.getWorld("world_stone"), 1.5, 70, 1.5);
    private static final Location WHATERBLOCK_TELEPORT_LOCATION = new Location(Bukkit.getWorld("world_whater"), 1.5, 70, 1.5);

    private final ConfigManager configManager;

    public SelectClass(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @EventHandler
    public void onSelection(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();

            if (block != null && block.getType() == Material.OAK_SIGN) {
                Location location = block.getLocation();
                Player player = event.getPlayer();

                Object homeDimensionObj = configManager.getPlayerValue(player, "HomeDimension");
                int homeDimension = (homeDimensionObj != null) ? (int) homeDimensionObj : 0;

                if (homeDimension == 0) {
                    if (location.equals(SKYBLOCK_LOCATION)) {
                        configManager.setPlayerValue(player, "HomeDimension", 1);
                        player.sendMessage("Du hast die Skyblock Dimension gewählt!");
                        player.setGameMode(GameMode.SURVIVAL);
                        player.teleport(SKYBLOCK_TELEPORT_LOCATION);

                    } else if (location.equals(STONEBLOCK_LOCATION)) {
                        configManager.setPlayerValue(player, "HomeDimension", 2);
                        player.sendMessage("Du hast die Stoneblock Dimension gewählt!");
                        player.setGameMode(GameMode.SURVIVAL);
                        player.teleport(STONEBLOCK_TELEPORT_LOCATION);

                    } else if (location.equals(WHATERBLOCK_LOCATION)) {
                        configManager.setPlayerValue(player, "HomeDimension", 3);
                        player.sendMessage("Du hast die Whaterblock Dimension gewählt!");
                        player.setGameMode(GameMode.SURVIVAL);
                        player.teleport(WHATERBLOCK_TELEPORT_LOCATION);

                    } else {
                        player.sendMessage("Unbekannte Dimension!");
                    }

                } else {
                    player.sendMessage("Du kannst deine Home-Dimension nicht ändern!");
                }
            }
        }
    }
}
