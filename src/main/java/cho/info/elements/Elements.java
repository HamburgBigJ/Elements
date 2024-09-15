package cho.info.elements;

import cho.info.elements.commands.*;
import cho.info.elements.generator.CustomOverworldGenerator;
import cho.info.elements.generator.SkyblockWorldGenerator;
import cho.info.elements.managers.*;
import cho.info.elements.player.SelectClass;
import cho.info.elements.player.SkillLevelManager;
import cho.info.elements.player.blocks.CompresstCobbleDrop;
import cho.info.elements.player.gui.EnderChest;
import cho.info.elements.player.mana.ManaRefill;
import cho.info.elements.player.onFirstJoin;
import cho.info.elements.player.skills.FarmingSkill;
import cho.info.elements.player.skills.ForestingSkill;
import cho.info.elements.player.skills.MiningSkill;
import cho.info.elements.server.VillagerTechniker;
import cho.info.elements.server.VillagersInHub;
import cho.info.elements.server.events.HubWeather;
import cho.info.elements.server.events.LoadPlayer;
import cho.info.elements.server.events.SteinSpalterHit;
import cho.info.elements.server.fix.EnderChestFix;
import cho.info.elements.server.goals.FirstGoal;
import cho.info.elements.server.goals.GoalVillagers;
import cho.info.elements.server.goals.hubstruktures.EnderVillager;
import cho.info.elements.server.goals.hubstruktures.LibarianVillager;
import cho.info.elements.server.goals.hubstruktures.LotaryVillager;
import cho.info.elements.server.goals.hubstruktures.SmitherVillager;
import cho.info.elements.server.mapedit.HubBlockBreak;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import cho.info.elements.generator.SkyblockWorldGenerator;

import java.util.Set;

public final class Elements extends JavaPlugin implements Listener {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    public ConfigManager configManager;
    public PluginManager pluginManager = getServer().getPluginManager();
    public VariableManager variableManager;
    public ItemManager itemManager;
    public WorldManager worldManager;
    public VillagersInHub villagersInHub;
    public VillagerTechniker villagerTechniker;
    public SmitherVillager smitherVillager;
    public LibarianVillager libarianVillager;
    public LotaryVillager lotaryVillager;
    public EnderVillager enderVillager;
    public MobManager mobManager;

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

        getLogger().info("Mystic Mobs Api Enabled!");


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
        pluginManager.registerEvents(new MobManager(this), this);
        pluginManager.registerEvents(new MiningSkill(this, configManager), this);
        pluginManager.registerEvents(new SkillLevelManager(this, configManager), this);
        pluginManager.registerEvents(new FarmingSkill(this, configManager), this);
        pluginManager.registerEvents(new ForestingSkill(this, configManager), this);
        pluginManager.registerEvents(new EnderChest(this, configManager, publicVariableManager, itemManager), this);
        pluginManager.registerEvents(new ManaRefill(this, configManager, itemManager), this);
        pluginManager.registerEvents(new SteinSpalterHit(itemManager, mobManager), this);
        pluginManager.registerEvents(new HubWeather(), this);
        pluginManager.registerEvents(new CompresstCobbleDrop(itemManager), this);
        pluginManager.registerEvents(new LoadPlayer(configManager), this);
        pluginManager.registerEvents(new FirstGoal(this, configManager), this);
        pluginManager.registerEvents(new SelectClass(configManager), this);
        pluginManager.registerEvents(new VillagerTechniker(configManager), this);
        pluginManager.registerEvents(new SmitherVillager(configManager), this);
        pluginManager.registerEvents(new HubBlockBreak(), this);
        pluginManager.registerEvents(new LibarianVillager(configManager), this);
        pluginManager.registerEvents(new EnderVillager(configManager), this);
        pluginManager.registerEvents(new LotaryVillager(configManager), this);
        pluginManager.registerEvents(new EnderChestFix(), this);
        // Only Event In der Main !!!!
        pluginManager.registerEvents(this, this);


        // Register all commands
        this.getCommand("gm").setExecutor(new GamemodeCommand());
        this.getCommand("setvar").setExecutor(new SetSkillXpCommand(configManager));
        this.getCommand("setskillvar").setExecutor(new SetSkillXpCommand(configManager));
        this.getCommand("respawnallvillagers").setExecutor(new RespanAllVillager(this));
        this.getCommand("setpublicvar").setExecutor(new SetPublicVarCommand(configManager));
        this.getCommand("resetallstruktures").setExecutor(new ResteAllStruktures());


        //Public Vars
        configManager.addPublicVar("Stage", 1);

        //First Goal
        configManager.addPublicVar("FirstGoal", 0);
        configManager.addPublicVar("FirstGoalMaxXp", 10000);
        configManager.addPublicVar("FirstGoalXp", 0);

        //Second Goal
        configManager.addPublicVar("SecondGoal", 0);
        configManager.addPublicVar("SecondGoalMaxXp", 100000);
        configManager.addPublicVar("SecondGoalXp", 0);

        //Smithin goal
        configManager.addPublicVar("SmitherGoal", 0);
        configManager.addPublicVar("SmitherGoalMaxXp", 5000);
        configManager.addPublicVar("SmitherGoalXp", 0);

        //Bibiotekar
        configManager.addPublicVar("LibarianGoal", 0);
        configManager.addPublicVar("LibarianGoalMaxXp", 30000);
        configManager.addPublicVar("LibarianGoalXp", 0);

        // Ender Villager
        configManager.addPublicVar("EnderVillagerGoal", 0);
        configManager.addPublicVar("EnderVillagerGoalMaxXp", 50000);
        configManager.addPublicVar("EnderVillagerGoalXp", 0);

        // Lorery Vilalger
        configManager.addPublicVar("LoreryVillagerGoal", 0);
        configManager.addPublicVar("LoreryVillagerGoalMaxXp", 1000);
        configManager.addPublicVar("LoreryVillagerGoalXp", 0);

        // Note: Villagers after startup !!!

        // Serrver gamerules




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



        getLogger().info("Load!");

        //Create all Worlds
        worldManager.createSkyWorld("world_skyblock");
        getLogger().info("Create: world_skyblock");

        worldManager.createStoneWorld("world_stone");
        getLogger().info("Create: world_stone");

        worldManager.createWaterWorld("world_whater");
        getLogger().info("Create: world_whater");

        enableKeepInventory();

        spawnvillager();


        // Set the world spawn location
        getLogger().info("Set World Spawn");
        setWorldSpawn("world", 1, 70, 1);



    }

    public void spawnvillager() {

        GoalVillagers goalVillagers = new GoalVillagers(configManager);

        VillagerTechniker villagerTechniker = new VillagerTechniker(configManager);

        SmitherVillager smitherVillager = new SmitherVillager(configManager);

        LibarianVillager libarianVillager = new LibarianVillager(configManager);

        EnderVillager enderVillager = new EnderVillager(configManager);

        LotaryVillager lotaryVillager = new LotaryVillager(configManager);

        killAllVillagersInOverworld();


        //Spawn Villager
        Object stageobj = configManager.getPublicVar("Stage");

        int stage = (stageobj != null) ? (int) stageobj : 0;

        if (stage >= 1) {

            villagersInHub.spawnVillagerStone();

            smitherVillager.villagerSpawn();

            villagerTechniker.villagerSpawn();

            libarianVillager.villagerSpawn();

            enderVillager.villagerSpawn();

            lotaryVillager.villagerSpawn();


        }

        goalVillagers.spawnVillagers();
        getLogger().info("Spawn All villagers");


    }

    public void killAllVillagersInOverworld() {
        // Get the Overworld (commonly named "world")
        World world = Bukkit.getWorld("world");

        if (world != null) {
            // Loop through all entities in the world
            for (Entity entity : world.getEntities()) {
                // Check if the entity is a villager
                if (entity.getType() == EntityType.VILLAGER) {
                    // Kill the villager
                    entity.remove();
                }
            }

            World syblcok = Bukkit.getWorld("world_skyblock");

            for (Entity entity : syblcok.getEntities()) {
                if (entity.getType() == EntityType.VILLAGER) {
                    entity.remove();
                }
            }

            World steonblock = Bukkit.getWorld("world_stone");
            for (Entity entity : steonblock.getEntities()) {
                if (entity.getType() == EntityType.VILLAGER) {
                    entity.remove();
                }
            }

            World whaterblco = Bukkit.getWorld("world_whater");
            for (Entity entity : whaterblco.getEntities()) {
                if (entity.getType() == EntityType.VILLAGER) {
                    entity.remove();
                }
            }

        } else {
            getLogger().info("World 'world' not found!");


        }
    }


    public void enableKeepInventory() {
        // Get the world (in this case, the overworld named "world")
        World world = Bukkit.getWorld("world");

        if (world != null) {
            // Set the keepInventory game rule to true
            world.setGameRule(GameRule.KEEP_INVENTORY, true);
            getLogger().info("KeepInventory has been enabled.");
        } else {
            getLogger().info("World 'world' not found!");
        }

        // Get the world (in this case, the overworld named "world_skyblock")
        World world_skyblock = Bukkit.getWorld("world_skyblock");

        if (world_skyblock != null) {
            // Set the keepInventory game rule to true
            world_skyblock.setGameRule(GameRule.KEEP_INVENTORY, true);
            getLogger().info("KeepInventory has been enabled.");
        } else {
            getLogger().info("World 'world' not found!");
        }

        // Get the world (in this case, the overworld named "world_stone")
        World world_stone = Bukkit.getWorld("world_stone");

        if (world_stone != null) {
            // Set the keepInventory game rule to true
            world_stone.setGameRule(GameRule.KEEP_INVENTORY, true);
            getLogger().info("KeepInventory has been enabled.");
        } else {
            getLogger().info("World 'world' not found!");
        }

        // Get the world (in this case, the overworld named "world_whater")
        World world_whater = Bukkit.getWorld("world_whater");

        if (world_whater != null) {
            // Set the keepInventory game rule to true
            world_whater.setGameRule(GameRule.KEEP_INVENTORY, true);
            getLogger().info("KeepInventory has been enabled.");
        } else {
            getLogger().info("World 'world' not found!");
        }
    }

    private void setWorldSpawn(String worldName, int x, int y, int z) {
        // Get the world by name
        World world = Bukkit.getWorld(worldName);

        if (world != null) {
            // Create a Location object with the desired coordinates
            Location spawnLocation = new Location(world, x, y, z);

            // Set the world spawn location
            world.setSpawnLocation(spawnLocation);

            getLogger().info("Spawn location set to: " + spawnLocation.toVector());
        } else {
            getLogger().warning("World '" + worldName + "' not found!");
        }
    }

}