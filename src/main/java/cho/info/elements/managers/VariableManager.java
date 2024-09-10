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
    private boolean loggingEnabled;  // Schalter für Logging

    // Konstruktor mit Logging-Schalter
    public VariableManager(JavaPlugin plugin, File dataFolder, String subFolder, String fileName, boolean loggingEnabled) {
        this.plugin = plugin;
        this.loggingEnabled = loggingEnabled;  // Setze den Logging-Schalter

        // Erstelle den Pfad zu dem Ordner "publicvars"
        File folder = new File(dataFolder, subFolder);
        if (!folder.exists()) {
            folder.mkdirs();  // Erstelle den Ordner, falls er nicht existiert
            logInfo("Ordner erstellt: " + folder.getPath());
        }

        // Pfad zur Datei
        this.configFile = new File(folder, fileName);
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                logInfo("Datei erstellt: " + configFile.getPath());
            } catch (IOException e) {
                logSevere("Fehler beim Erstellen der Datei: " + e.getMessage());
            }
        }

        this.config = YamlConfiguration.loadConfiguration(configFile);
    }

    // Holt eine Location aus der YAML-Datei
    public Location getLocation(@NotNull String path) {
        if (config.contains(path + ".world")) {
            String worldName = config.getString(path + ".world");
            double x = config.getDouble(path + ".x");
            double y = config.getDouble(path + ".y");
            double z = config.getDouble(path + ".z");

            if (worldName != null && Bukkit.getWorld(worldName) != null) {
                return new Location(Bukkit.getWorld(worldName), x, y, z);
            } else {
                logWarning("Welt " + worldName + " nicht gefunden oder null.");
            }
        } else {
            logWarning("Pfad für Location nicht gefunden: " + path);
        }
        return null;
    }

    // Holt einen Boolean-Wert aus der YAML-Datei
    public Boolean getBoolean(@NotNull String path) {
        if (config.contains(path)) {
            Boolean value = config.getBoolean(path);
            logInfo("Boolean-Wert geladen: " + path + " = " + value);
            return value;
        }
        logWarning("Boolean-Wert nicht gefunden: " + path);
        return null;
    }

    // Holt einen String aus der YAML-Datei
    public String getString(@NotNull String path) {
        if (config.contains(path)) {
            String value = config.getString(path);
            logInfo("String-Wert geladen: " + path + " = " + value);
            return value;
        }
        logWarning("String-Wert nicht gefunden: " + path);
        return null;
    }

    // Holt einen Wert eines Spielers (UUID) aus der YAML-Datei
    public Boolean getPlayerStatus(@NotNull UUID uuid) {
        String path = "players." + uuid.toString();
        Boolean status = getBoolean(path);
        if (status != null) {
            logInfo("Spielerstatus für " + uuid + " geladen: " + status);
        } else {
            logWarning("Spielerstatus für " + uuid + " nicht gefunden.");
        }
        return status;
    }

    // Speichert die Config-Datei
    public void saveConfig() {
        try {
            config.save(configFile);
            logInfo("Konfigurationsdatei erfolgreich gespeichert: " + configFile.getPath());
        } catch (IOException e) {
            logSevere("Fehler beim Speichern der Konfigurationsdatei: " + e.getMessage());
        }
    }

    // Methoden für Logging, abhängig vom Schalter
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

    // Schalter für Logging umschalten
    public void setLoggingEnabled(boolean enabled) {
        this.loggingEnabled = enabled;
    }

    public boolean isLoggingEnabled() {
        return loggingEnabled;
    }
}
