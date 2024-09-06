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
            Player player = ((Player) commandSender).getPlayer();

            // Get the player's location and send it as a message
            var playerlocation = player.getLocation();
            commandSender.sendMessage(playerlocation.toString());

            // Save the player's location in the config
            configManager.setPublicVar("HubCords", playerlocation);
        }

        return false;
    }
}
