/*
Created by: HamburgBihJ
9/7/2024
11:04
Edit by: HamburgBigJ
 */
package cho.info.elements.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class VariableManager {

    private File configFile;
    private FileConfiguration config;

    public VariableManager(File dataFolder, String subFolder, String fileName) {
        // Erstelle den Pfad zu dem Ordner "publicvars"
        File folder = new File(dataFolder, subFolder);
        if (!folder.exists()) {
            folder.mkdirs();  // Erstelle den Ordner, falls er nicht existiert
        }

        // Pfad zur Datei
        this.configFile = new File(folder, fileName);
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.config = YamlConfiguration.loadConfiguration(configFile);
    }

    // Holt eine Location aus der YAML-Datei
    public Location getLocation(String path) {
        if (config.contains(path)) {
            String worldName = config.getString(path + ".world");
            double x = config.getDouble(path + ".x");
            double y = config.getDouble(path + ".y");
            double z = config.getDouble(path + ".z");
            float pitch = (float) config.getDouble(path + ".pitch");
            float yaw = (float) config.getDouble(path + ".yaw");

            return new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
        }
        return null;  // Falls der Pfad nicht existiert
    }

    // Holt einen Boolean-Wert aus der YAML-Datei
    public Boolean getBoolean(String path) {
        if (config.contains(path)) {
            return config.getBoolean(path);
        }
        return null;
    }

    // Holt einen String aus der YAML-Datei
    public String getString(String path) {
        if (config.contains(path)) {
            return config.getString(path);
        }
        return null;
    }

    // Holt einen Wert eines Spielers (UUID) aus der YAML-Datei
    public Boolean getPlayerStatus(UUID uuid) {
        String path = "players." + uuid.toString();
        return getBoolean(path);
    }

    // Speichert die Config-Datei
    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

