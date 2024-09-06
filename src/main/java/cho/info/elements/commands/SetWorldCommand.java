/*
Created by: HamburgBihJ
9/6/2024
10:04
Edit by: HamburgBigJ
 */
package cho.info.elements.commands;

import cho.info.elements.configs.ConfigManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetWorldCommand implements CommandExecutor {

    // Instance of ConfigManager used for managing configuration
    public ConfigManager configManager;

    // Constructor initializing ConfigManager
    public SetWorldCommand(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        // Check if the sender of the command is a player
        if (commandSender instanceof Player) {
            Player player = ((Player) commandSender).getPlayer();

            // Get the player's current location
            var playerlocation = player.getLocation();

            // Set the location in the configuration manager under the key "WorldCords"
            configManager.setPublicVar("WorldCords", playerlocation);
        }

        // Return false to indicate the command did not execute successfully
        return false;
    }
}
