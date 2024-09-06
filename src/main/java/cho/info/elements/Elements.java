package cho.info.elements;

import cho.info.elements.configs.ConfigManager;
import cho.info.elements.player.onFirstJoin;
import cho.info.elements.player.skills.MiningSkill;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public final class Elements extends JavaPlugin {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    private ConfigManager configManager;
    public PluginManager pluginManager = getServer().getPluginManager();

    @Override
    public void onEnable() {
        // ANSI-Escape-Code für Blaue Farbe
        String blue = "\u001B[34m";
        String reset = "\u001B[0m";

        // ASCII-Art
        String logo = blue + "\n" +
                " /$$$$$$$$\n" +
                " | $$_____/\n" +
                " | $$      \n" +
                " | $$$$$   \n" +
                " | $$__/   \n" +
                " | $$      \n" +
                " | $$$$$$$$\n" +
                " |________/\n" +
                reset;

        // Ausgabe in der Konsole
        getLogger().info(logo);

        saveDefaultConfig();

        //Register All Events
        pluginManager.registerEvents(new onFirstJoin(this), this);
        pluginManager.registerEvents(new MiningSkill(this), this);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
