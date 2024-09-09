package cho.info.elements.player;

import cho.info.elements.managers.ConfigManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class onFirstJoin implements Listener {

    private final JavaPlugin plugin;
    private ConfigManager configManager;

    // Constructor to pass the plugin object
    public onFirstJoin(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        FileConfiguration config = plugin.getConfig(); // Access the plugin's configuration
        String playerUUID = event.getPlayer().getUniqueId().toString();

        if (config.contains("players." + playerUUID)) {
            // The player has already joined
            event.getPlayer().sendMessage("Welcome back, " + event.getPlayer().getName() + "!");
        } else {
            // The player is joining for the first time
            event.getPlayer().sendMessage("Welcome to your first visit, " + event.getPlayer().getName() + "!");
            // Add the player to the configuration
            config.set("players." + playerUUID, true);
            plugin.saveConfig(); // Save the configuration

            // Create an instance of ConfigManager
            ConfigManager configManager = new ConfigManager(plugin.getDataFolder());

            // All XP Forms for a player
            configManager.addValue(event.getPlayer(), "FarmingXp", 0);
            configManager.addValue(event.getPlayer(), "ForestingXp", 0);
            configManager.addValue(event.getPlayer(), "MiningXp", 0);

            // All Skills
            configManager.addValue(event.getPlayer(), "FarmingLv", 0);
            configManager.addValue(event.getPlayer(), "ForestingLv", 0);
            configManager.addValue(event.getPlayer(), "MiningLv", 0);

            // Skill Max XP
            configManager.addValue(event.getPlayer(), "FarmingMaxXp", 100);
            configManager.addValue(event.getPlayer(), "ForestingMaxXp", 100);
            configManager.addValue(event.getPlayer(), "MiningMaxXp", 100);

            // Resources
            configManager.addValue(event.getPlayer(), "Mana", 10);
            configManager.addValue(event.getPlayer(), "MaxMana", 20);

            //Reward
            configManager.addValue(event.getPlayer(), "BaseXp", 1);
            configManager.addValue(event.getPlayer(), "XpMultiplier", 1);
            configManager.addValue(event.getPlayer(), "HomeDimension", 0); // 1 = Skyblock 2 = StoneBlock 3 = WhaterBlock

            //Gui
            configManager.addValue(event.getPlayer(), "EdderGui", 0);
            configManager.addValue(event.getPlayer(), "Selector", 1);

        }
    }
}
