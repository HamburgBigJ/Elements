package cho.info.elements.server.mapedit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class HubBlockBreak implements Listener {

    @EventHandler
    public void onBlockBreakInHub(BlockBreakEvent event) {
        if (event.getBlock().getLocation().getWorld() == Bukkit.getWorld("world")) {
            if (!event.getPlayer().hasPermission("elements.edit")) {
                event.getPlayer().sendMessage(ChatColor.RED + "Du kannst in Hub keine Blöcke abbauen !");
            }
        }
    }
}
