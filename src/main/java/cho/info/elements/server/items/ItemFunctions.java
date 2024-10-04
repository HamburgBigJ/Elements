package cho.info.elements.server.items;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Collection;

public class ItemFunctions {

    public JavaPlugin plugin;

    public ItemFunctions(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void init() {

        //Graventy Stone
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    // Überprüfen, ob der Spieler das spezifische Item in der Offhand hält
                    if (player.getInventory().getItemInOffHand().getType() == Material.GRAY_DYE && player.getInventory().getItemInOffHand().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Gravaty Stone")) {
                        // Teleportiere alle Items im Radius von 5 Blöcken
                        teleportNearbyItems(player);
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 20); // Alle 20 Ticks (1 Sekunde)


    }

    private void teleportNearbyItems(Player player) {
        // Holen der Position des Spielers
        Vector playerLocation = player.getLocation().toVector();
        // Holen aller Items in der Welt
        Collection<Item> items = player.getWorld().getEntitiesByClass(Item.class);

        for (Item item : items) {
            // Überprüfen, ob das Item im Radius von 5 Blöcken ist
            if (item.getLocation().toVector().isInSphere(playerLocation, 5)) {
                // Teleportiere das Item zur Position des Spielers
                item.teleport(player.getLocation());
            }
        }
    }
}
