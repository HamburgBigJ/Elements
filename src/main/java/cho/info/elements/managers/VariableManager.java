package cho.info.elements.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class VariableManager {

    ///Do not use !!!!  out Dated

    private final FileConfiguration config;
    private final File configFile;
    private final JavaPlugin plugin;
    private boolean loggingEnabled;  // Switch for logging

    // Constructor with logging switch
    public VariableManager(JavaPlugin plugin, File dataFolder, String subFolder, String fileName, boolean loggingEnabled) {
        this.plugin = plugin;
        this.loggingEnabled = loggingEnabled;  // Set the logging switch

        // Create the path to the "publicvars" folder
        File folder = new File(dataFolder, subFolder);
        if (!folder.exists()) {
            folder.mkdirs();  // Create the folder if it does not exist
            logInfo("Folder created: " + folder.getPath());
        }

        // Path to the file
        this.configFile = new File(folder, fileName);
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                logInfo("File created: " + configFile.getPath());
            } catch (IOException e) {
                logSevere("Error creating file: " + e.getMessage());
            }
        }

        this.config = YamlConfiguration.loadConfiguration(configFile);
    }

    // Retrieves a Location from the YAML file
    public Location getLocation(@NotNull String path) {
        if (config.contains(path + ".world")) {
            String worldName = config.getString(path + ".world");
            double x = config.getDouble(path + ".x");
            double y = config.getDouble(path + ".y");
            double z = config.getDouble(path + ".z");

            if (worldName != null && Bukkit.getWorld(worldName) != null) {
                return new Location(Bukkit.getWorld(worldName), x, y, z);
            } else {
                logWarning("World " + worldName + " not found or null.");
            }
        } else {
            logWarning("Path for location not found: " + path);
        }
        return null;
    }

    // Retrieves a Boolean value from the YAML file
    public Boolean getBoolean(@NotNull String path) {
        if (config.contains(path)) {
            Boolean value = config.getBoolean(path);
            logInfo("Boolean value loaded: " + path + " = " + value);
            return value;
        }
        logWarning("Boolean value not found: " + path);
        return null;
    }

    // Retrieves a String from the YAML file
    public String getString(@NotNull String path) {
        if (config.contains(path)) {
            String value = config.getString(path);
            logInfo("String value loaded: " + path + " = " + value);
            return value;
        }
        logWarning("String value not found: " + path);
        return null;
    }

    // Retrieves a player's status (UUID) from the YAML file
    public Boolean getPlayerStatus(@NotNull UUID uuid) {
        String path = "players." + uuid.toString();
        Boolean status = getBoolean(path);
        if (status != null) {
            logInfo("Player status for " + uuid + " loaded: " + status);
        } else {
            logWarning("Player status for " + uuid + " not found.");
        }
        return status;
    }

    // Saves the config file
    public void saveConfig() {
        try {
            config.save(configFile);
            logInfo("Configuration file successfully saved: " + configFile.getPath());
        } catch (IOException e) {
            logSevere("Error saving configuration file: " + e.getMessage());
        }
    }

    // Logging methods, depending on the switch
    private void logInfo(String message) {
        if (loggingEnabled) {
            plugin.getLogger().info(message);
        }
    }

    private void logWarning(String message) {
        if (loggingEnabled) {
            plugin.getLogger().warning(message);
        }
    }

    private void logSevere(String message) {
        if (loggingEnabled) {
            plugin.getLogger().severe(message);
        }
    }

    // Toggle logging switch
    public void setLoggingEnabled(boolean enabled) {
        this.loggingEnabled = enabled;
    }

    public boolean isLoggingEnabled() {
        return loggingEnabled;
    }
}
