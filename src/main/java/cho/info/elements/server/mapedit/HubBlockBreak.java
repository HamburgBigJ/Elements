package cho.info.elements.server.mapedit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class HubBlockBreak implements Listener {

    @EventHandler
    public void onBlockBreakInHub(BlockBreakEvent event) {
        if (event.getBlock().getLocation().getWorld() == Bukkit.getWorld("world")) {
            if (!event.getPlayer().hasPermission("elements.edit")) {
                event.getPlayer().sendMessage(ChatColor.RED + "Du kannst in Hub keine Blöcke abbauen!");
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockPlaceInHub(BlockPlaceEvent event) {
        if (event.getBlock().getLocation().getWorld() == Bukkit.getWorld("world")) {
            if (!event.getPlayer().hasPermission("elements.edit")) {
                event.getPlayer().sendMessage(ChatColor.RED + "Du kannst in Hub keine Blöcke platzieren!");
                event.setCancelled(true);
            }
        }
    }
}
