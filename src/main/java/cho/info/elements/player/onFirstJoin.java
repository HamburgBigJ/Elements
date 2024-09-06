package cho.info.elements.player;

import cho.info.elements.configs.ConfigManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class onFirstJoin implements Listener {

    private final JavaPlugin plugin;
    private ConfigManager configManager;


    // Konstruktor um das Plugin-Objekt zu übergeben
    public onFirstJoin(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        FileConfiguration config = plugin.getConfig(); // Zugriff auf die Konfiguration des Plugins
        String playerUUID = event.getPlayer().getUniqueId().toString();

        if (config.contains("players." + playerUUID)) {
            // Der Spieler ist bereits gejoint
            event.getPlayer().sendMessage("Willkommen zurück, " + event.getPlayer().getName() + "!");
        } else {
            // Der Spieler join zum ersten Mal
            event.getPlayer().sendMessage("Willkommen zu deinem ersten Besuch, " + event.getPlayer().getName() + "!");
            // Füge den Spieler zur Konfiguration hinzu
            config.set("players." + playerUUID, true);
            plugin.saveConfig(); // Speichere die Konfiguration

            // Erstelle eine Instanz des ConfigManagers
            ConfigManager configManager = new ConfigManager(plugin.getDataFolder());

            //All Xp Forms For a player
            configManager.addValue(event.getPlayer(), "FarmingXp", 0);
            configManager.addValue(event.getPlayer(), "ForestingXp", 0);
            configManager.addValue(event.getPlayer(), "MiningXp", 0);

            //All Skills
            configManager.addValue(event.getPlayer(), "FarmingLv", 0);
            configManager.addValue(event.getPlayer(), "ForestingLv", 0);
            configManager.addValue(event.getPlayer(), "MiningLv", 0);

            //Skill Max Xp
            configManager.addValue(event.getPlayer(), "FarmingMaxXp", 100);
            configManager.addValue(event.getPlayer(), "ForestingMaxXp", 100);
            configManager.addValue(event.getPlayer(), "MiningMaxXp", 100);

            //Recurses
            configManager.addValue(event.getPlayer(), "Mana", 10);
        }
    }
}
