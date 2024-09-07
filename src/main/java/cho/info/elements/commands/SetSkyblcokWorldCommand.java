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

public class SetSkyblcokWorldCommand implements CommandExecutor {

    // Instance of ConfigManager used for managing configuration
    public ConfigManager configManager;

    // Constructor initializing ConfigManager
    public SetSkyblcokWorldCommand(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        // Check if the command sender is a player
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            Location playerLocation = player.getLocation();

            // Save the player's location in the config with proper structure
            configManager.setPublicVar("SkyblockWorld.world", playerLocation.getWorld().getName());
            configManager.setPublicVar("SkyblockWorld.x", playerLocation.getX());
            configManager.setPublicVar("SkyblockWorld.y", playerLocation.getY());
            configManager.setPublicVar("SkyblockWorld.z", playerLocation.getZ());
            configManager.setPublicVar("SkyblockWorld.pitch", playerLocation.getPitch());
            configManager.setPublicVar("SkyblockWorld.yaw", playerLocation.getYaw());



            player.sendMessage(ChatColor.GREEN + "SkyblockWorld location set!");
            return true;
        }
        return false;
    }
}

