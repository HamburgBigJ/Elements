package cho.info.elements.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ResteAllStruktures implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "clone -10 82 82 -16 77 78 7 69 3"); // Lotory
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "clone -24 81 78 -18 77 82 7 69 -5"); // Alcemais
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "clone -32 81 78 -26 77 82 -11 69 -5"); //  smith
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "clone -34 81 82 -40 77 78 -11 69 3"); // Libarian

        return false;
    }
}
