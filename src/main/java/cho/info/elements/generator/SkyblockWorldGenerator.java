package cho.info.elements.generator;

import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

public class SkyblockWorldGenerator extends ChunkGenerator {
    @Override
    public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
        // Creates an empty ChunkData with no blocks
        return createChunkData(world);  // No blocks are set, everything is air
    }
}
