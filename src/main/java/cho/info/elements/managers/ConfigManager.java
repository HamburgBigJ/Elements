/*
Created by: HamburgBihJ
9/6/2024
10:04
Edit by: HamburgBigJ, Codellama
 */
package cho.info.elements.managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ConfigManager {

    private final File playerDataFolder;
    private final File publicVarsFile;
    private final Map<UUID, FileConfiguration> configCache = new HashMap<>();
    private FileConfiguration publicVarsConfig;

    public ConfigManager(File pluginFolder) {
        // Create a folder for player files if it doesn't exist
        playerDataFolder = new File(pluginFolder, "playerdata");
        if (!playerDataFolder.exists()) {
            playerDataFolder.mkdirs();
        }

        // Initialize the public variables file
        File serverVarsFolder = new File(pluginFolder, "ServerVars");
        if (!serverVarsFolder.exists()) {
            serverVarsFolder.mkdirs();
        }
        publicVarsFile = new File(serverVarsFolder, "PublicVars.yml");

        // Load public variables
        loadPublicVars();
    }

    // Loads or retrieves the config file for the player based on UUID
    private FileConfiguration getPlayerConfig(UUID playerUUID) {
        File playerFile = new File(playerDataFolder, playerUUID.toString() + ".yml");
        FileConfiguration config = configCache.get(playerUUID);

        if (config == null || !playerFile.exists()) {
            if (!playerFile.exists()) {
                try {
                    playerFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            config = YamlConfiguration.loadConfiguration(playerFile);
            configCache.put(playerUUID, config);
        }

        return config;
    }

    // Saves the player's config file
    public void savePlayerConfig(UUID playerUUID, FileConfiguration config) {
        File playerFile = new File(playerDataFolder, playerUUID.toString() + ".yml");
        try {
            config.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Sets a value (e.g., FarmingXP) for the player
    public void setPlayerValue(Player player, String path, Object value) {
        FileConfiguration config = getPlayerConfig(player.getUniqueId());
        config.set(path, value);
        savePlayerConfig(player.getUniqueId(), config);
    }

    // Retrieves a value (e.g., FarmingXP) for the player
    public Object getPlayerValue(Player player, String path) {
        FileConfiguration config = getPlayerConfig(player.getUniqueId());
        return config.get(path);
    }

    // Adds a new variable with a default value
    public void addValue(Player player, String variableName, Object defaultValue) {
        FileConfiguration config = getPlayerConfig(player.getUniqueId());

        if (!config.contains(variableName)) {
            config.set(variableName, defaultValue);
            savePlayerConfig(player.getUniqueId(), config);
        }
    }

    // Checks if a variable exists
    public boolean contains(Player player, String path) {
        FileConfiguration config = getPlayerConfig(player.getUniqueId());
        return config.contains(path);
    }

    // Loads the public variables from the PublicVars file
    private void loadPublicVars() {
        if (!publicVarsFile.exists()) {
            try {
                publicVarsFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        publicVarsConfig = YamlConfiguration.loadConfiguration(publicVarsFile);
    }

    // Retrieves a public variable value
    public Object getPublicVar(String path) {
        return publicVarsConfig.get(path);
    }

    // Sets a public variable value
    public void setPublicVar(String path, Object value) {
        publicVarsConfig.set(path, value);
        savePublicVars();
    }

    // Saves the public variables to the PublicVars file
    private void savePublicVars() {
        try {
            publicVarsConfig.save(publicVarsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Adds a new public variable with a default value
    public void addPublicVar(String variableName, Object defaultValue) {
        if (!publicVarsConfig.contains(variableName)) {
            publicVarsConfig.set(variableName, defaultValue);
            savePublicVars();
        }
    }
}
