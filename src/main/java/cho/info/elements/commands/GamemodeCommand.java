/*
Created by: HamburgBihJ
9/6/2024
10:04
Edit by: HamburgBigJ
*/
package cho.info.elements.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GamemodeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        // Check if the command sender is a player
        if (commandSender instanceof Player) {
            Player player = ((Player) commandSender).getPlayer();

            // Toggle the player's game mode
            if (player.getGameMode().equals(GameMode.CREATIVE)){
                player.setGameMode(GameMode.SURVIVAL);
            } else if (player.getGameMode().equals(GameMode.ADVENTURE)) {
                player.setGameMode(GameMode.SURVIVAL);

            } else {
                player.setGameMode(GameMode.CREATIVE);
            }
        }

        // Indicate that the command was executed
        return false;
    }
}
