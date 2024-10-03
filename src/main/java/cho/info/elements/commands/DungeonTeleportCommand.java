package cho.info.elements.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DungeonTeleportCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            Location location = new Location(Bukkit.getWorld("world_dungeon"), 1, 77, 1);
            player.setGameMode(GameMode.CREATIVE);
            player.sendMessage("Teleport");
            player.teleport(location);
        }
        return true;
    }
}
