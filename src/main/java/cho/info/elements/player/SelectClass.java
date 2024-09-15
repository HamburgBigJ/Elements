package cho.info.elements.player;

import cho.info.elements.managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SelectClass implements Listener {

    private static final Location SKYBLOCK_TELEPORT_LOCATION = new Location(Bukkit.getWorld("world_skyblock"), 1.5, 70, 1.5);
    private static final Location STONEBLOCK_TELEPORT_LOCATION = new Location(Bukkit.getWorld("world_stone"), 1.5, 70, 1.5);
    private static final Location WATERBLOCK_TELEPORT_LOCATION = new Location(Bukkit.getWorld("world_water"), 1.5, 70, 1.5);

    private final ConfigManager configManager;

    public SelectClass(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @EventHandler
    public void onSelection(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();

            if (block != null && block.getType() == Material.OAK_WALL_SIGN) {
                Sign sign = (Sign) block.getState();
                String header = ChatColor.stripColor(sign.getLine(0));  // Removes any color formatting
                String secondLine = ChatColor.stripColor(sign.getLine(1));

                Player player = event.getPlayer();

                // Check if the first line contains "[Elements]"
                if ("§b[Elements]".equalsIgnoreCase(header)) {

                    Object homeDimensionObj = configManager.getPlayerValue(player, "HomeDimension");
                    int homeDimension = (homeDimensionObj != null) ? (int) homeDimensionObj : 0;

                    if (homeDimension == 0) {
                        // Process based on the second line
                        if ("§1Skyblock".equalsIgnoreCase(secondLine)) {
                            configManager.setPlayerValue(player, "HomeDimension", 1);
                            player.sendMessage("Du hast die Skyblock Dimension gewählt!");
                            player.setGameMode(GameMode.SURVIVAL);
                            player.teleport(SKYBLOCK_TELEPORT_LOCATION);

                        } else if ("§7Stoneblock".equalsIgnoreCase(secondLine)) {
                            configManager.setPlayerValue(player, "HomeDimension", 2);
                            player.sendMessage("Du hast die Stoneblock Dimension gewählt!");
                            player.setGameMode(GameMode.SURVIVAL);
                            player.teleport(STONEBLOCK_TELEPORT_LOCATION);

                        } else if ("§3Waterblock".equalsIgnoreCase(secondLine)) {
                            configManager.setPlayerValue(player, "HomeDimension", 3);
                            player.sendMessage("Du hast die Waterblock Dimension gewählt!");
                            player.setGameMode(GameMode.SURVIVAL);
                            player.teleport(WATERBLOCK_TELEPORT_LOCATION);

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
}
