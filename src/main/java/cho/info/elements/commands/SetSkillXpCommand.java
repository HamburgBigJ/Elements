/*
Created by: HamburgBihJ
9/6/2024
10:04
Edit by: HamburgBigJ
 */
package cho.info.elements.commands;

import cho.info.elements.managers.ConfigManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetSkillXpCommand implements CommandExecutor {

    private final ConfigManager configManager;

    public SetSkillXpCommand(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            // Check if the correct number of arguments are provided
            if (args.length == 2) {
                String xptype = args[0];
                int amount;

                try {
                    amount = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    player.sendMessage("Invalid amount. Please enter a valid number.");
                    return false;
                }

                // Set the player value using the ConfigManager
                configManager.setPlayerValue(player, xptype, amount);
                player.sendMessage("Successfully updated " + xptype + " to " + amount + ".");
                return true;
            } else {
                // Inform the player about the correct usage of the command
                player.sendMessage("Usage: /setskillxp <xpType> <amount>");
                player.sendMessage("XP Types: FarmingXp, ForestingXp, MiningXp, etc.");
                return false;
            }
        } else {
            // Inform non-players that the command can only be used by players
            commandSender.sendMessage("This command can only be used by players.");

        }

        return false;
    }
}
