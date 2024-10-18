package cho.info.elements;

import cho.info.elements.commands.*;
import cho.info.elements.generator.CustomOverworldGenerator;
import cho.info.elements.managers.*;
import cho.info.elements.player.PlayerJoinSpawn;
import cho.info.elements.player.SelectClass;
import cho.info.elements.player.SkillLevelManager;
import cho.info.elements.player.blocks.CompresstCobbleDrop;
import cho.info.elements.player.collections.*;
import cho.info.elements.player.gui.CollectionInv;
import cho.info.elements.player.gui.EnderChest;
import cho.info.elements.player.gui.collections.ClickEvent;
import cho.info.elements.player.gui.collections.CollectRegister;
import cho.info.elements.player.gui.collections.CollectionListInv;
import cho.info.elements.player.items.HealtFrutItem;
import cho.info.elements.player.mana.ManaRefill;
import cho.info.elements.player.onFirstJoin;
import cho.info.elements.player.skills.FarmingSkill;
import cho.info.elements.player.skills.ForestingSkill;
import cho.info.elements.player.skills.MiningSkill;
import cho.info.elements.server.VillagerTechniker;
import cho.info.elements.server.VillagersInHub;
import cho.info.elements.server.events.*;
import cho.info.elements.server.goals.FirstGoal;
import cho.info.elements.server.goals.GoalVillagers;
import cho.info.elements.server.goals.hubstruktures.EnderVillager;
import cho.info.elements.server.goals.hubstruktures.LibarianVillager;
import cho.info.elements.server.goals.hubstruktures.LotaryVillager;
import cho.info.elements.server.goals.hubstruktures.SmitherVillager;
import cho.info.elements.server.goals.second.SecondGoal;
import cho.info.elements.server.goals.second.SecondGoalVillager;
import cho.info.elements.server.items.Items;
import cho.info.elements.server.mapedit.HubBlockBreak;
import cho.info.elements.server.recepies.EcoShardRecepie;
import cho.info.elements.server.servergoals.CheckBarrel;
import cho.info.elements.server.serverhealt.TpsMonitor;
import cho.info.elements.server.villagers.VillagerInHubTirTwo;
import cho.info.elements.server.villagers.stants.LoteryInteractStand;
import cho.info.elements.server.villagers.stants.LoteryVillagerStand;
import cho.info.elements.server.villagers.stants.SmithInteractStand;
import cho.info.elements.server.villagers.stants.SmithVillagerStand;
import cho.info.elements.server.villagers.stants.inventory.AnvilGuiFunction;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

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
    public SecondGoalVillager secondGoalVillager;
    public VillagerInHubTirTwo villagerInHubTirTwo;
    public EcoShardRecepie ecoShardRecepie;
    public CollectionListInv collectionListInv;
    public Items items;
    public LoteryVillagerStand loteryVillagerStand;
    public ManaRefill manaRefill;

    @Override
    public void onEnable() {

        configManager = new ConfigManager(getDataFolder());
        VariableManager publicVariableManager = new VariableManager(this, getDataFolder(), "ServerVars", "PublicVars.yml", true);
        ItemManager itemManager = new ItemManager();
        worldManager = new WorldManager(this);
        this.mobManager = new MobManager(this);
        ecoShardRecepie = new EcoShardRecepie(this);
        collectionListInv = new CollectionListInv(this);
        items = new Items(itemManager);
        manaRefill = new ManaRefill(this, configManager, itemManager);

        serverLogo();

        //Init Mana
        manaRefill.initManaRefill();


        // Initialisiere VillagersInHub
        villagersInHub = new VillagersInHub();
        villagersInHub.configManager = configManager;
        villagersInHub.itemManager = itemManager;

        villagerInHubTirTwo = new VillagerInHubTirTwo();
        villagerInHubTirTwo.configManager = configManager;
        villagerInHubTirTwo.itemManager = itemManager;


        getLogger().warning("Plugin: " + getName());
        getLogger().warning("This is an Experimental Alpha version of this plugin.");
        getLogger().warning("Many features aren't implemented yet and may not work!");


        saveDefaultConfig();

        // Register all events
        pluginManager.registerEvents(new onFirstJoin(this), this);
        pluginManager.registerEvents(new MobManager(this), this);
        pluginManager.registerEvents(new MiningSkill(this, configManager), this);
        pluginManager.registerEvents(new SkillLevelManager(this, configManager), this);
        pluginManager.registerEvents(new FarmingSkill(this, configManager), this);
        pluginManager.registerEvents(new ForestingSkill(this, configManager), this);
        pluginManager.registerEvents(new EnderChest(this, configManager, publicVariableManager, itemManager), this);
        pluginManager.registerEvents(new SteinSpalterHit(itemManager, mobManager), this);
        pluginManager.registerEvents(new HubWeather(), this);
        pluginManager.registerEvents(new CompresstCobbleDrop(itemManager), this);
        pluginManager.registerEvents(new LoadPlayer(configManager), this);
        pluginManager.registerEvents(new FirstGoal(configManager), this);
        pluginManager.registerEvents(new SelectClass(configManager), this);
        pluginManager.registerEvents(new VillagerTechniker(configManager), this);
        pluginManager.registerEvents(new SmitherVillager(configManager, this), this);
        pluginManager.registerEvents(new HubBlockBreak(), this);
        pluginManager.registerEvents(new LibarianVillager(configManager, this), this);
        pluginManager.registerEvents(new EnderVillager(configManager, this), this);
        pluginManager.registerEvents(new LotaryVillager(configManager, this), this);
        pluginManager.registerEvents(new PlayerRespawn(this, configManager), this);
        pluginManager.registerEvents(new CollectionInv(this, configManager, itemManager, collectionListInv), this);
        pluginManager.registerEvents(new TpsMonitor(this), this);
        pluginManager.registerEvents(new SecondGoal(configManager), this);
        pluginManager.registerEvents(new AmatystCollection(configManager), this);
        pluginManager.registerEvents(new AppleCollection(configManager), this);
        pluginManager.registerEvents(new CarrotCollection(configManager), this);
        pluginManager.registerEvents(new CobblestoneCollection(configManager), this);
        pluginManager.registerEvents(new EcoShardCollection(configManager), this);
        pluginManager.registerEvents(new KelpCollection(configManager), this);
        pluginManager.registerEvents(new OakCollection(configManager), this);
        pluginManager.registerEvents(new PotatoCollection(configManager), this);
        pluginManager.registerEvents(new WheatCollection(configManager), this);
        pluginManager.registerEvents(new ServerFinishLoad(this, this), this);
        pluginManager.registerEvents(new ClickEvent(configManager) , this);
        pluginManager.registerEvents(new CollectRegister(configManager, itemManager), this);
        pluginManager.registerEvents(new HealtFrutItem(configManager, itemManager), this);
        pluginManager.registerEvents(new CheckBarrel(this, configManager), this);
        pluginManager.registerEvents(new LoteryInteractStand(configManager, itemManager, items), this);
        pluginManager.registerEvents(new SmithInteractStand(configManager), this);
        pluginManager.registerEvents(new AnvilGuiFunction(itemManager), this); // Register the AnvilGuiFunction
        pluginManager.registerEvents(new PlayerJoinSpawn(), this);


        // Register all commands
        this.getCommand("gm").setExecutor(new GamemodeCommand());
        this.getCommand("setvar").setExecutor(new SetSkillXpCommand(configManager));
        this.getCommand("setskillvar").setExecutor(new SetSkillXpCommand(configManager));
        this.getCommand("respawnallvillagers").setExecutor(new RespanAllVillager(this));
        this.getCommand("setpublicvar").setExecutor(new SetPublicVarCommand(configManager));
        this.getCommand("resetallstruktures").setExecutor(new ResteAllStruktures());
        this.getCommand("tpdungon").setExecutor(new DungeonTeleportCommand());
        this.getCommand("tphub").setExecutor(new HubTeleportCommand());
        this.getCommand("setmaxflying").setExecutor(new SetMaxFlyingSpeed());
        this.getCommand("elementsinv").setExecutor(new ElementsInv(items));




        //Public Vars
        configManager.addPublicVar("Stage", 1);

        //
        configManager.addPublicVar("TotalPlayer", 0);

        //First Goal
        configManager.addPublicVar("FirstGoal", 0);
        configManager.addPublicVar("FirstGoalMaxXp", 10000);
        configManager.addPublicVar("FirstGoalXp", 0);

        //Second Goal
        configManager.addPublicVar("SecondGoal", 0);
        configManager.addPublicVar("SecondGoalMaxXp", 800000);
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


        //Villagers in Hub
        configManager.addPublicVar("VillagerInHubTir", 0);

        //Hub Upgrades
        configManager.setPublicVar("HubTir", 0);

        //Goal
        configManager.setPublicVar("Goal", 0);
        configManager.setPublicVar("GoalMax", 1000);

        //Stände
        configManager.addPublicVar("LoteryVillager", 0);
        configManager.addPublicVar("SmithithVillager", 0);
        configManager.addPublicVar("LibarianVillager", 0);
        configManager.addPublicVar("EnderVillager", 0);

        // Leader Bord

        configManager.addPublicVar("FarmingLv1", "No Player");
        configManager.addPublicVar("FarmingLv2", "No Player");
        configManager.addPublicVar("FarmingLv3", "No Player");
        configManager.addPublicVar("FarmingLv4", "No Player");

        configManager.addPublicVar("ForestingLv1", "No Player");
        configManager.addPublicVar("ForestingLv2", "No Player");
        configManager.addPublicVar("ForestingLv3", "No Player");
        configManager.addPublicVar("ForestingLv4", "No Player");

        configManager.addPublicVar("MiningLv1", "No Player");
        configManager.addPublicVar("MiningLv2", "No Player");
        configManager.addPublicVar("MiningLv3", "No Player");
        configManager.addPublicVar("MiningLv4", "No Player");

        configManager.addPublicVar("PlayerLv1", "No Player");
        configManager.addPublicVar("PlayerLv2", "No Player");
        configManager.addPublicVar("PlayerLv3", "No Player");
        configManager.addPublicVar("PlayerLv4", "No Player");


        getLogger().info("Register all Variables");

        // Note: Villagers after startup !!!

        //Recepis
        ecoShardRecepie.createEcoShardRecepie();

        // Serrver gamerules


        // Tab List
        new BukkitRunnable() {
            @Override
            public void run() {
                updateTabListNames();
            }
        }.runTaskTimer(this, 0L, 1L); // Alle 1 Tick (20 Ticks pro Sekunde) aktualisieren




    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Shutdown");

        serverLogo();


    }


    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return new CustomOverworldGenerator();
    }


    public void serverLogo() {
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



    public void runAfterServerLoad() {



        getLogger().info("Loading Worlds!");

        //Create all Worlds
        worldManager.createSkyWorld("world_skyblock");
        getLogger().info("Create: world_skyblock");

        worldManager.createStoneWorld("world_stone");
        getLogger().info("Create: world_stone");

        worldManager.createWaterWorld("world_whater");
        getLogger().info("Create: world_whater");

        worldManager.createSkyWorld("world_dungeon");
        getLogger().info("Create: world_dungeon");

        enableKeepInventory();

        spawnvillager();


        // Set the world spawn location
        getLogger().info("Set World Spawn");
        setWorldSpawn("world", 1, 70, 1);



    }

    public void spawnvillager() {

        GoalVillagers goalVillagers = new GoalVillagers(configManager);

        VillagerTechniker villagerTechniker = new VillagerTechniker(configManager);

        SmitherVillager smitherVillager = new SmitherVillager(configManager, this);

        LibarianVillager libarianVillager = new LibarianVillager(configManager, this);

        EnderVillager enderVillager = new EnderVillager(configManager, this);

        LotaryVillager lotaryVillager = new LotaryVillager(configManager, this);

        SecondGoalVillager secondGoalVillager = new SecondGoalVillager(configManager);

        LoteryVillagerStand loteryVillagerStand = new LoteryVillagerStand();

        SmithVillagerStand smithVillagerStand = new SmithVillagerStand();





        killAllVillagersInOverworld();


        //Spawn Villager

        int stage = (configManager.getPublicVar("Stage") != null) ? (int) configManager.getPublicVar("Stage") : 0;
        int loteryVillagerstage = (configManager.getPublicVar("LoteryVillager") != null) ? (int) configManager.getPublicVar("LoteryVillager") : 0;
        int smitherVillagerstage = (configManager.getPublicVar("SmithithVillager") != null) ? (int) configManager.getPublicVar("SmithithVillager") : 0;
        int libarianVillagerstage = (configManager.getPublicVar("LibarianVillager") != null) ? (int) configManager.getPublicVar("LibarianVillager") : 0;
        int enderVillagerstage = (configManager.getPublicVar("EnderVillager") != null) ? (int) configManager.getPublicVar("EnderVillager") : 0;

        if (loteryVillagerstage == 1) {

            loteryVillagerStand.spawnVillagerLotery();


        }

        if (smitherVillagerstage == 1) {
            smithVillagerStand.spawnVillagerSmith();

        }

        if (libarianVillagerstage == 1) {
            //Fix

        }

        if (enderVillagerstage == 1) {

            //Fix

        }



        if (stage >= 1) {

            villagersInHub.spawnVillagerStone();

            smitherVillager.villagerSpawn();

            villagerTechniker.villagerSpawn();

            libarianVillager.villagerSpawn();

            enderVillager.villagerSpawn();

            lotaryVillager.villagerSpawn();

            secondGoalVillager.spawnVillager();

            int villager = (configManager.getPublicVar("VillagerInHubTir") != null) ? (int) configManager.getPublicVar("VillagerInHubTir") : 0;

            if (villager >= 1) {
                villagerInHubTirTwo.spawnVillagerWeat();


            }


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

    private void updateTabListNames() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            // Erhalte den aktuellen Namen und das Level des Spielers
            String playerName = player.getName();
            int playerLevel = player.getLevel();

            // Setze den neuen Namen in der Tab-Liste
            player.setPlayerListName(playerName + ChatColor.BLUE + " " + playerLevel);
        }
    }

}