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
     * Erstellt eine Welt mit benutzerdefiniertem Chunk-Generator.
     *
     * @param worldName     Name der Welt
     * @param environment   Die Weltumgebung (z. B. NORMAL, NETHER, END)
     * @param worldType     Typ der Welt (z. B. FLAT, NORMAL)
     * @param generator     Der Chunk-Generator für benutzerdefinierte Weltgeneration
     */
    public void createCustomWorld(String worldName, Environment environment, WorldType worldType, ChunkGenerator generator) {
        WorldCreator creator = new WorldCreator(worldName);
        creator.environment(environment);
        creator.type(worldType);
        creator.generator(generator);
        Bukkit.createWorld(creator);
    }

    /**
     * Erstellt eine Wasserwelt mit benutzerdefiniertem Generator.
     *
     * @param worldName Name der Wasserwelt
     */
    public void createWaterWorld(String worldName) {
        ChunkGenerator waterWorldGenerator = new WaterWorldGenerator();
        createCustomWorld(worldName, Environment.NORMAL, WorldType.FLAT, waterWorldGenerator);
    }

    public void createSkyWorld(String worldName) {
        ChunkGenerator waterWorldGenerator = new SkyblockWorldGenerator();
        createCustomWorld(worldName, Environment.NORMAL, WorldType.FLAT, waterWorldGenerator);
    }

    public void createStoneWorld(String worldName) {
        ChunkGenerator waterWorldGenerator = new StoneWorldGenerator();
        createCustomWorld(worldName, Environment.NORMAL, WorldType.FLAT, waterWorldGenerator);
    }

    /**
     * Ändert den Generator einer existierenden Welt.
     *
     * @param worldName Name der Welt
     * @param generator Der neue Chunk-Generator
     */
    public void setWorldGenerator(String worldName, ChunkGenerator generator) {
        World world = Bukkit.getWorld(worldName);

        if (world != null) {
            // Welt entladen
            Bukkit.unloadWorld(world, false);
        }

        // Welt neu laden mit neuem Generator
        WorldCreator creator = new WorldCreator(worldName);
        creator.generator(generator);
        Bukkit.createWorld(creator);

        plugin.getLogger().info("Welt " + worldName + " wurde mit einem neuen Generator neu geladen.");
    }

    /**
     * Beispiel: Ändert den Generator der Wasserwelt
     *
     * @param worldName Name der Wasserwelt
     */
    public void changeToWaterWorldGenerator(String worldName) {
        setWorldGenerator(worldName, new WaterWorldGenerator());
    }

    public void setOverworldGenerator(ChunkGenerator generator) {
        World overworld = Bukkit.getWorld("world"); // "world" ist der Standardname der Overworld

        if (overworld != null) {
            // Welt entladen
            Bukkit.unloadWorld(overworld, false);
        }

        // Welt neu laden mit benutzerdefiniertem Generator
        WorldCreator creator = new WorldCreator("world");
        creator.generator(generator);
        Bukkit.createWorld(creator);

        plugin.getLogger().info("Overworld wurde mit einem neuen Generator neu geladen.");
    }

    // Weitere Methoden (wie Löschen oder Teleportieren) könnten hier hinzugefügt werden.
}
