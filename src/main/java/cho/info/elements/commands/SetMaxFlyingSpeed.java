package cho.info.elements.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetMaxFlyingSpeed implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (strings.length == 1) {
                try {
                    float speed = Float.parseFloat(strings[0]);
                    player.setFlySpeed(speed);
                    player.sendMessage("Flying speed set to " + speed);
                    return true;
                } catch (NumberFormatException e) {
                    player.sendMessage("Invalid number format");
                    return false;
                }
            } else {
                player.sendMessage("Invalid number of arguments");
                return false;
            }
        }

        return false;
    }
}
