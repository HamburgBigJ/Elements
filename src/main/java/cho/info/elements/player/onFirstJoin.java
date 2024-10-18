package cho.info.elements.player;

import cho.info.elements.managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
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
            event.getPlayer().sendMessage(ChatColor.BLUE + "This server is Running Elements:" +  plugin.getDescription().getVersion());
            // Add the player to the configuration
            config.set("players." + playerUUID, true);
            plugin.saveConfig(); // Save the configuration

            // Create an instance of ConfigManager
            ConfigManager configManager = new ConfigManager(plugin.getDataFolder());

            // All XP forms for a player
            configManager.addValue(event.getPlayer(), "FarmingXp", 0);
            configManager.addValue(event.getPlayer(), "ForestingXp", 0);
            configManager.addValue(event.getPlayer(), "MiningXp", 0);

            // All skills
            configManager.addValue(event.getPlayer(), "FarmingLv", 0);
            configManager.addValue(event.getPlayer(), "ForestingLv", 0);
            configManager.addValue(event.getPlayer(), "MiningLv", 0);

            // Skill Max XP
            configManager.addValue(event.getPlayer(), "FarmingMaxXp", 100);
            configManager.addValue(event.getPlayer(), "ForestingMaxXp", 100);
            configManager.addValue(event.getPlayer(), "MiningMaxXp", 100);

            // Resources
            configManager.addValue(event.getPlayer(), "Mana", 0);
            configManager.addValue(event.getPlayer(), "MaxMana", 20);

            // Reward
            configManager.addValue(event.getPlayer(), "BaseXp", 1);
            configManager.addValue(event.getPlayer(), "XpMultiplier", 1);
            configManager.addValue(event.getPlayer(), "HomeDimension", 0); // 1 = Skyblock, 2 = StoneBlock, 3 = WaterBlock

            // GUI
            configManager.addValue(event.getPlayer(), "EdderGui", 0);
            configManager.addValue(event.getPlayer(), "Selector", 1);

            // Health
            configManager.addValue(event.getPlayer(), "health", 6);

            // Collection to do
            configManager.addValue(event.getPlayer(), "cobblestone", 0);
            configManager.addValue(event.getPlayer(), "Oak", 0);
            configManager.addValue(event.getPlayer(), "Wheat", 0);
            configManager.addValue(event.getPlayer(), "Amethyst", 0);
            configManager.addValue(event.getPlayer(), "Eco_Shard", 0);
            configManager.addValue(event.getPlayer(), "Kelp", 0);
            configManager.addValue(event.getPlayer(), "Potato", 0);
            configManager.addValue(event.getPlayer(), "Carrot", 0);
            configManager.addValue(event.getPlayer(), "Apple", 0);

            // Techniker Stage
            configManager.addValue(event.getPlayer(), "TechnikerStage", 0);

            //Villagers
            configManager.addValue(event.getPlayer(), "SmithStage", 0);
            configManager.addValue(event.getPlayer(), "LoteryStage", 0);
            configManager.addValue(event.getPlayer(), "LibarianStage", 0);
            configManager.addValue(event.getPlayer(), "EnderStage", 0);

            int playerCount = (configManager.getPublicVar("TotalPlayer") != null) ? (int) configManager.getPublicVar("TotalPlayer") : 0;

            playerCount = playerCount + 1;

            configManager.setPublicVar("TotalPlayer", playerCount);


            // Gamemod Change
            event.getPlayer().setGameMode(GameMode.ADVENTURE);

            //Chage palyer count
            Object playerCoutObj = configManager.getPublicVar("TotalPlayer");
            int plyerCount = (playerCoutObj != null) ? (int) playerCoutObj : 0;

            plyerCount = plyerCount + 1;

            configManager.setPublicVar("palyerCount", plyerCount);

        }

    }
}
