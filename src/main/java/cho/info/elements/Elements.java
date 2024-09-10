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
import cho.info.elements.player.mana.ManaRefill;
import cho.info.elements.player.onFirstJoin;
import cho.info.elements.player.skills.FarmingSkill;
import cho.info.elements.player.skills.ForestingSkill;
import cho.info.elements.player.skills.MiningSkill;
import cho.info.elements.server.VillagersInHub;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import cho.info.elements.generator.SkyblockWorldGenerator;

public final class Elements extends JavaPlugin implements Listener {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    public ConfigManager configManager;
    public PluginManager pluginManager = getServer().getPluginManager();
    public VariableManager variableManager;
    public ItemManager itemManager;
    public WorldManager worldManager;
    public VillagersInHub villagersInHub;




    @Override
    public void onEnable() {

        configManager = new ConfigManager(getDataFolder());
        VariableManager publicVariableManager = new VariableManager(this, getDataFolder(), "ServerVars", "PublicVars.yml", true);
        ItemManager itemManager = new ItemManager();
        worldManager = new WorldManager(this);

        // Initialisiere VillagersInHub
        villagersInHub = new VillagersInHub();
        villagersInHub.configManager = configManager;
        villagersInHub.itemManager = itemManager;

        

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
        pluginManager.registerEvents(new ManaRefill(this, configManager, itemManager), this);
        // Only Event In der Main !!!!
        pluginManager.registerEvents(this, this);

        // Register all commands
        this.getCommand("gm").setExecutor(new GamemodeCommand());
        this.getCommand("setvar").setExecutor(new SetSkillXpCommand(configManager));
        this.getCommand("setskillvar").setExecutor(new SetSkillXpCommand(configManager));


        //Public Vars
        configManager.addPublicVar("Stage", 1);


        // Note: Villagers after startup !!!


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

    @EventHandler
    public void onServerLoad(ServerLoadEvent event) {
        getLogger().info("Server full load  Finish");
        runAfterServerLoad();

    }


    public void runAfterServerLoad() {



        // Deine Funktion, die erst nach dem Laden des Servers ausgeführt werden soll
        getLogger().info("Load!");

        //Create all Worlds
        worldManager.createSkyWorld("world_skyblock");
        getLogger().info("Create: world_skyblock");

        worldManager.createStoneWorld("world_stone");
        getLogger().info("Create: world_stone");

        worldManager.createWaterWorld("world_whater");
        getLogger().info("Create: world_whater");


        //Spawn Villager
        Object stageobj = configManager.getPublicVar("Stage");

        int stage = (stageobj != null) ? (int) stageobj : 0;

        if (stage >= 1) {

            villagersInHub.spawnVillagerStone();


        }
    }
}
