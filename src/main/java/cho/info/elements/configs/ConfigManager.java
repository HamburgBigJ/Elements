package cho.info.elements.configs;

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
    private final Map<UUID, FileConfiguration> configCache = new HashMap<>();

    public ConfigManager(File pluginFolder) {
        // Erstelle einen Ordner für Spielerdateien, falls er nicht existiert
        playerDataFolder = new File(pluginFolder, "playerdata");
        if (!playerDataFolder.exists()) {
            playerDataFolder.mkdirs();
        }
    }

    // Lädt oder holt die Config-Datei für den Spieler basierend auf der UUID
    private FileConfiguration getPlayerConfig(UUID playerUUID) {
        File playerFile = new File(playerDataFolder, playerUUID.toString() + ".yml");
        FileConfiguration config = configCache.get(playerUUID);

        if (config == null || configFileNeedsReload(playerFile)) {
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

    private boolean configFileNeedsReload(File playerFile) {
        // Hier kannst du überprüfen, ob die Datei neu geladen werden muss (z.B. durch Zeitstempel vergleichen)
        return !configCache.containsKey(playerFile);
    }

    // Speichert die Config-Datei des Spielers
    public void savePlayerConfig(UUID playerUUID, FileConfiguration config) {
        File playerFile = new File(playerDataFolder, playerUUID.toString() + ".yml");
        try {
            config.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Funktion zum Setzen eines Wertes (z.B. FarmingXP) für den Spieler
    public void setPlayerValue(Player player, String path, Object value) {
        FileConfiguration config = getPlayerConfig(player.getUniqueId());
        config.set(path, value);
        savePlayerConfig(player.getUniqueId(), config);
    }

    // Funktion zum Abrufen eines Wertes (z.B. FarmingXP) für den Spieler
    public Object getPlayerValue(Player player, String path) {
        FileConfiguration config = getPlayerConfig(player.getUniqueId());
        return config.get(path);
    }

    // Funktion zum Hinzufügen einer neuen Variable mit einem Standardwert
    public void addValue(Player player, String variableName, Object defaultValue) {
        FileConfiguration config = getPlayerConfig(player.getUniqueId());

        // Überprüfe, ob die Variable bereits existiert
        if (!config.contains(variableName)) {
            // Setze die Variable mit dem Standardwert
            config.set(variableName, defaultValue);
            // Speichere die Config
            savePlayerConfig(player.getUniqueId(), config);
        }
    }

    // Funktion zum Überprüfen, ob eine Variable existiert
    public boolean contains(Player player, String path) {
        FileConfiguration config = getPlayerConfig(player.getUniqueId());
        return config.contains(path);
    }
}
