package cho.info.elements.server.serverhealt;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TpsMonitor implements Listener {

    private final JavaPlugin plugin;
    private final int TPS_THRESHOLD = 18;
    private final int COUNTDOWN_TIME = 20;

    public TpsMonitor(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onTpsCheck(ServerTickEndEvent event) {
        // TPS-Wert ermitteln
        double tps = Bukkit.getTPS()[0]; // 0 = 1 Minute Durchschnitt

        if (tps < TPS_THRESHOLD) {
            // Nachricht an alle Spieler senden
            sendActionBarMessage(tps);

            // Countdown für Items auf dem Boden starten
            new BukkitRunnable() {
                private int remainingTime = COUNTDOWN_TIME;

                @Override
                public void run() {
                    if (remainingTime > 0) {
                        // Countdown-Nachricht im Chat senden
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            player.sendMessage(String.format("[EmLag]: %d seconds until items are removed", remainingTime));
                        }
                        remainingTime--;
                    } else {
                        // Items entfernen, wenn der Countdown abgelaufen ist
                        for (Entity entity : Bukkit.getWorlds().get(0).getEntities()) {
                            if (entity instanceof Item) {
                                Item item = (Item) entity;
                                item.remove();
                            }
                        }
                        cancel();
                    }
                }
            }.runTaskTimer(plugin, 0L, 20L); // Alle 20 Ticks (1 Sekunde) wiederholen
        }
    }

    private void sendActionBarMessage(double tps) {
        String message = String.format("[EmLag]: %.1f", tps);

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendActionBar(message);
        }
    }
}
