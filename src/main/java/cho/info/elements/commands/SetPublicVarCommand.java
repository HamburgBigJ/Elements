package cho.info.elements.commands;

import cho.info.elements.managers.ConfigManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class SetPublicVarCommand implements CommandExecutor {

    public ConfigManager configManager;

    public SetPublicVarCommand(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (strings.length == 2) {
            configManager.setPublicVar(strings[0], Integer.parseInt(strings[1]));

            commandSender.sendMessage("Set " + strings[0] + " to " + Integer.parseInt(strings[1]));

        }else {
            commandSender.sendMessage("Use /<Command> <Var> <Int>");
        }

        return false;
    }
}
