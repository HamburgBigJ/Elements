package cho.info.elements.managers;

import cho.info.elements.generator.SkyblockWorldGenerator;
import cho.info.elements.generator.StoneWorldGenerator;
import cho.info.elements.generator.WaterWorldGenerator;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.World.Environment;

import java.io.File;

public class WorldManager {

    private final JavaPlugin plugin;

    public WorldManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Creates a world with a custom chunk generator.
     *
     * @param worldName     Name of the world
     * @param environment   The world environment (e.g., NORMAL, NETHER, END)
     * @param worldType     Type of the world (e.g., FLAT, NORMAL)
     * @param generator     The chunk generator for custom world generation
     */
    public void createCustomWorld(String worldName, Environment environment, WorldType worldType, ChunkGenerator generator) {
        WorldCreator creator = new WorldCreator(worldName);
        creator.environment(environment);
        creator.type(worldType);
        creator.generator(generator);
        Bukkit.createWorld(creator);
    }

    /**
     * Creates a water world with a custom generator.
     *
     * @param worldName Name of the water world
     */
    public void createWaterWorld(String worldName) {
        ChunkGenerator waterWorldGenerator = new WaterWorldGenerator();
        createCustomWorld(worldName, Environment.NORMAL, WorldType.FLAT, waterWorldGenerator);
    }

    public void createSkyWorld(String worldName) {
        ChunkGenerator skyWorldGenerator = new SkyblockWorldGenerator();
        createCustomWorld(worldName, Environment.NORMAL, WorldType.FLAT, skyWorldGenerator);
    }

    public void createStoneWorld(String worldName) {
        ChunkGenerator stoneWorldGenerator = new StoneWorldGenerator();
        createCustomWorld(worldName, Environment.NORMAL, WorldType.FLAT, stoneWorldGenerator);
    }

    /**
     * Changes the generator of an existing world.
     *
     * @param worldName Name of the world
     * @param generator The new chunk generator
     */
    public void setWorldGenerator(String worldName, ChunkGenerator generator) {
        World world = Bukkit.getWorld(worldName);

        if (world != null) {
            // Unload the world
            Bukkit.unloadWorld(world, false);
        }

        // Reload the world with the new generator
        WorldCreator creator = new WorldCreator(worldName);
        creator.generator(generator);
        Bukkit.createWorld(creator);

        plugin.getLogger().info("World " + worldName + " has been reloaded with a new generator.");
    }

    /**
     * Example: Changes the generator of the water world
     *
     * @param worldName Name of the water world
     */
    public void changeToWaterWorldGenerator(String worldName) {
        setWorldGenerator(worldName, new WaterWorldGenerator());
    }

    public void setOverworldGenerator(ChunkGenerator generator) {
        World overworld = Bukkit.getWorld("world"); // "world" is the default name of the Overworld

        if (overworld != null) {
            // Unload the world
            Bukkit.unloadWorld(overworld, false);
        }

        // Reload the world with the custom generator
        WorldCreator creator = new WorldCreator("world");
        creator.generator(generator);
        Bukkit.createWorld(creator);

        plugin.getLogger().info("Overworld has been reloaded with a new generator.");
    }

    // Additional methods (such as deleting or teleporting) could be added here.
}
