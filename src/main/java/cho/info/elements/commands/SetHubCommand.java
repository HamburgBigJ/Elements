/*
Created by: HamburgBihJ
9/6/2024
10:04
Edit by: HamburgBigJ
 */
package cho.info.elements.commands;

import cho.info.elements.managers.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetHubCommand implements CommandExecutor {

    public ConfigManager configManager;

    // Constructor to initialize ConfigManager
    public SetHubCommand(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        // Check if the command sender is a player
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            Location playerLocation = player.getLocation();

            // Save the player's location in the config with proper structure
            configManager.setPublicVar("HubWorld.world", playerLocation.getWorld().getName());
            configManager.setPublicVar("HubWorld.x", playerLocation.getX());
            configManager.setPublicVar("HubWorld.y", playerLocation.getY());
            configManager.setPublicVar("HubWorld.z", playerLocation.getZ());
            configManager.setPublicVar("HubWorld.pitch", playerLocation.getPitch());
            configManager.setPublicVar("HubWorld.yaw", playerLocation.getYaw());



            player.sendMessage(ChatColor.GREEN + "Hub location set!");
            return true;
        }
        return false;
    }
}
