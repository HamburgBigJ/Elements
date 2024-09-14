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

    public ConfigManager configManager;

    public SelectClass(ConfigManager configManager){
        this.configManager = configManager;


    }

    @EventHandler
    public void onSelection(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();

            if (block != null && (block.getType() == Material.OAK_SIGN)) {
                Location location = block.getLocation();
                Location skyblock = new Location(Bukkit.getWorld("world"), -5, 70 , 11);
                Location stoneblock = new Location(Bukkit.getWorld("world"), -5, 70, 10);
                Location whaterblock = new Location(Bukkit.getWorld("world"), -5, 70, 9);
                Player player = event.getPlayer();

                Object homeDimesionObj = configManager.getPlayerValue(player, "HomeDimension");

                int homeDimesion = (homeDimesionObj != null) ? (int) homeDimesionObj : 0;


                if (homeDimesion == 0){

                    if (location == skyblock) {
                        configManager.setPlayerValue(player, "HomeDimension", 1);
                        player.sendMessage("Du hast die Skyblock Dimension gewählt!");

                        Location teleport = new Location(Bukkit.getWorld("world_skyblock"), 1.5, 70, 1.5);

                        player.setGameMode(GameMode.SURVIVAL);


                        player.teleport(teleport);

                    } else if (location == stoneblock) {
                        configManager.setPlayerValue(player, "HomeDimension", 2);
                        player.sendMessage("Du hast die Stoneblock Dimension gewählt!");

                        Location teleport = new Location(Bukkit.getWorld("world_stone"), 1.5, 70, 1.5);

                        player.setGameMode(GameMode.SURVIVAL);


                        player.teleport(teleport);

                    } else if (location == whaterblock) {
                        configManager.setPlayerValue(player, "HomeDimension", 3);
                        player.sendMessage("Du hast die Whaterblock Dimension gewählt!");

                        Location teleport = new Location(Bukkit.getWorld("world_whater"), 1.5, 70, 1.5);

                        player.setGameMode(GameMode.SURVIVAL);


                        player.teleport(teleport);

                    }

                }else {
                    player.sendMessage("Du kannst deine Home dimension nicht Ändern!");
                }

            }
        }
    }
}
