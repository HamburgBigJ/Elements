package cho.info.elements.player.mana;

import cho.info.elements.managers.ConfigManager;
import cho.info.elements.managers.ItemManager;
import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class ManaRefill implements Listener {
    public ConfigManager configManager;
    public ItemManager itemManager;
    private JavaPlugin plugin;

    public ManaRefill(JavaPlugin plugin, ConfigManager configManager, ItemManager itemManager) {
        this.plugin = plugin;
        this.configManager = configManager;
        this.itemManager = itemManager;


    }

    @EventHandler
    public void onTcik(ServerTickStartEvent event) {
        int timer = 0;

        if (event.getTickNumber() == 20) {
            timer = timer + 1;
        }

        if (timer >= 20) {
            timer = 0;

            Bukkit.getOnlinePlayers();

            // Impimetation of all player becom 1 mana back
            // Work in porgress
        }
    }
}
