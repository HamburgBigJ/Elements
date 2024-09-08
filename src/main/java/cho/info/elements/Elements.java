/*
Created by: HamburgBihJ
9/6/2024
10:04
Edit by: HamburgBigJ
 */
package cho.info.elements;

import cho.info.elements.commands.*;
import cho.info.elements.generator.CustomOverworldGenerator;
import cho.info.elements.generator.SkyblockWorldGenerator;
import cho.info.elements.managers.ConfigManager;
import cho.info.elements.managers.ItemManager;
import cho.info.elements.managers.VariableManager;
import cho.info.elements.managers.WorldManager;
import cho.info.elements.player.SkillLevelManager;
import cho.info.elements.player.gui.EnderChest;
import cho.info.elements.player.onFirstJoin;
import cho.info.elements.player.skills.FarmingSkill;
import cho.info.elements.player.skills.ForestingSkill;
import cho.info.elements.player.skills.MiningSkill;
import fr.supermax_8.boostedaudio.api.BoostedAudioAPI;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import cho.info.elements.generator.SkyblockWorldGenerator;

public final class Elements extends JavaPlugin {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    private ConfigManager configManager;
    public PluginManager pluginManager = getServer().getPluginManager();
    public VariableManager variableManager;
    public ItemManager itemManager;
    public WorldManager worldManager;

    @Override
    public void onEnable() {

        configManager = new ConfigManager(getDataFolder());
        VariableManager publicVariableManager = new VariableManager(this, getDataFolder(), "ServerVars", "PublicVars.yml", true);
        ItemManager itemManager = new ItemManager();
        worldManager = new WorldManager(this);
        

        getLogger().warning("Plugin: " + getName());
        getLogger().warning("This is an Experimental Alpha version of this plugin.");
        getLogger().warning("Many features aren't implemented yet and may not work!");


        // ANSI Escape Code for Blue Color
        String blue = "\u001B[34m";
        String reset = "\u001B[0m";

        // ASCII-Art
        String logo = blue + "By: HamburgBigJ\n" +
                " /$$$$$$$$\n" +
                " | $$_____/\n" +
                " | $$      \n" +
                " | $$$$$   \n" +
                " | $$__/   \n" +
                " | $$      \n" +
                " | $$$$$$$$\n" +
                " |________/\n" +
                reset;

        // Print to console
        getLogger().info(logo);

        saveDefaultConfig();

        // Register all events
        pluginManager.registerEvents(new onFirstJoin(this), this);
        pluginManager.registerEvents(new MiningSkill(this, configManager), this);
        pluginManager.registerEvents(new SkillLevelManager(this, configManager), this);
        pluginManager.registerEvents(new FarmingSkill(this, configManager), this);
        pluginManager.registerEvents(new ForestingSkill(this, configManager), this);
        pluginManager.registerEvents(new EnderChest(this, configManager, publicVariableManager, itemManager), this);

        // Register all commands
        this.getCommand("gm").setExecutor(new GamemodeCommand());
        this.getCommand("setvar").setExecutor(new SetSkillXpCommand(configManager));


        // All public variables
        // Nothing yet

        //Boosted Audio Soft Depend
        if (pluginManager.isPluginEnabled("BoostedAudio")) {
            BoostedAudioAPI boostedAudioAPI = BoostedAudioAPI.getAPI();
            getLogger().info(ChatColor.BLUE + "BoostedAudio: True");
            boostedAudioAPI.info(ChatColor.BLUE + "Enable Elements");
        }else {
            getLogger().info(ChatColor.RED + "BoostedAudio: False");
        }



        //Create all Worlds
        worldManager.createSkyWorld("world_skyblock");
        getLogger().info("Create: world_skyblock");

        worldManager.createStoneWorld("world_stone");
        getLogger().info("Create: world_stone");

        worldManager.createWaterWorld("world_whater");
        getLogger().info("Create: world_whater");




    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Shutdown");

        // ANSI Escape Code for Blue Color
        String blue = "\u001B[34m";
        String reset = "\u001B[0m";

        // ASCII-Art
        String logo = blue + "By: HamburgBigJ\n" +
                " /$$$$$$$$\n" +
                " | $$_____/\n" +
                " | $$      \n" +
                " | $$$$$   \n" +
                " | $$__/   \n" +
                " | $$      \n" +
                " | $$$$$$$$\n" +
                " |________/\n" +
                reset;

        // Print to console
        getLogger().info(logo);
    }


    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return new CustomOverworldGenerator();
    }
}
