package cho.info.elements.commands;

import cho.info.elements.server.items.Items;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ElementsInv implements CommandExecutor {

    public Items items;

    public ElementsInv(Items items) {
        this.items = items;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSender instanceof Player) {

            Player player = (Player) commandSender;
            Inventory inventory = player.getServer().createInventory(null, 27, "Elements Inventory");

            inventory.setItem(0, items.lebensFrucht(1));
            inventory.setItem(1, items.gravatyStone(1));
            inventory.setItem(2, items.upgradeItem(1));

            // Add More Items

        }

        return false;
    }
}
