package cho.info.elements.commands;

import cho.info.elements.Elements;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class RespanAllVillager implements CommandExecutor {

    private final Elements elements;

    // Constructor that accepts the plugin instance
    public RespanAllVillager(Elements elements) {
        this.elements = elements;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        // Call the spawnvillager method from Elements class
        elements.spawnvillager();

        // Send confirmation message to the sender
        commandSender.sendMessage("Respawn all Villagers");

        return true;
    }
}
