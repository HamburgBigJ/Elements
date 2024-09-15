package cho.info.elements.player;

import cho.info.elements.managers.ConfigManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SelectClass implements Listener {

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
                if ("[Elements]".equalsIgnoreCase(header)) {

                    Object homeDimensionObj = configManager.getPlayerValue(player, "HomeDimension");
                    int homeDimension = (homeDimensionObj != null) ? (int) homeDimensionObj : 0;

                    if (homeDimension == 0) {
                        Location teleportLocation = null;
                        World targetWorld = null;

                        // Determine the target world and location based on the second line
                        switch (secondLine.toLowerCase()) {
                            case "skyblock":
                                targetWorld = Bukkit.getWorld("world_skyblock");
                                teleportLocation = new Location(targetWorld, 1.5, 70, 1.5);
                                break;
                            case "stoneblock":
                                targetWorld = Bukkit.getWorld("world_stone");
                                teleportLocation = new Location(targetWorld, 1.5, 70, 1.5);
                                break;
                            case "whaterblock":
                                targetWorld = Bukkit.getWorld("world_whater");
                                teleportLocation = new Location(targetWorld, 1.5, 70, 1.5);
                                break;
                            default:
                                player.sendMessage("Unbekannte Dimension!");
                                return; // Exit the method if the dimension is unknown
                        }

                        // Check if the target world is not null
                        if (targetWorld == null) {
                            player.sendMessage("Die Zielwelt konnte nicht gefunden werden!");
                            return; // Exit the method if the world is not found
                        }

                        // Set player dimension and teleport
                        configManager.setPlayerValue(player, "HomeDimension", getDimensionFromLine(secondLine));
                        player.sendMessage("Du hast die " + secondLine + " Dimension gewählt!");
                        player.setGameMode(GameMode.SURVIVAL);
                        player.teleport(teleportLocation);
                    } else {
                        player.sendMessage("Du kannst deine Home-Dimension nicht ändern!");
                    }
                }
            }
        }
    }

    private int getDimensionFromLine(String line) {
        switch (line.toLowerCase()) {
            case "skyblock":
                return 1;
            case "stoneblock":
                return 2;
            case "whaterblock":
                return 3;
            default:
                return 0;
        }
    }
}
