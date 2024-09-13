package cho.info.elements.generator;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

public class StoneWorldGenerator extends ChunkGenerator {
    @Override
    public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
        ChunkData chunk = createChunkData(world);

        int minHeight = -60; // Minimum height for Bedrock
        int maxHeight = 310; // Maximum height for the Barrier layer

        // 1. Bedrock layer at Y = -60
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                chunk.setBlock(i, minHeight, j, Material.BEDROCK);
            }
        }

        // 2. Water from Y = -59 to Y = 309
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                for (int y = minHeight + 1; y < maxHeight; y++) {
                    chunk.setBlock(i, y, j, Material.STONE);
                }
            }
        }

        // 3. Barrier layer at Y = 310
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                chunk.setBlock(i, maxHeight, j, Material.BEDROCK);
            }
        }

        // Set the biome to Deep Ocean for this chunk
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                biome.setBiome(i, j, Biome.PLAINS);
            }
        }

        return chunk;
    }
}
