package cho.info.elements.generator;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.block.Biome;

import java.util.Random;

public class WaterWorldGenerator extends ChunkGenerator {
    @Override
    public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
        ChunkData chunk = createChunkData(world);

        int minHeight = -60; // Minimum height for Barrier
        int maxHeight = 310; // Maximum height for the Barrier layer

        // 1. Barrier layer at Y = -60
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                chunk.setBlock(i, minHeight, j, Material.BARRIER);
            }
        }

        // 2. Water from Y = -59 to Y = 309
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                for (int y = minHeight + 1; y < maxHeight; y++) {
                    chunk.setBlock(i, y, j, Material.WATER);
                }
            }
        }

        // 3. Barrier layer at Y = 310
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                chunk.setBlock(i, maxHeight, j, Material.BARRIER);
            }
        }

        // Set the biome to Deep Ocean for this chunk
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                biome.setBiome(i, j, Biome.DEEP_OCEAN);
            }
        }

        return chunk;
    }
}
