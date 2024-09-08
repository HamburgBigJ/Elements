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

        int minHeight = -60; // Mindesthöhe für Bedrock
        int maxHeight = 310; // Maximale Höhe für die Barrier-Schicht

        // 1. Bedrock-Schicht bei Y = -60
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                chunk.setBlock(i, minHeight, j, Material.BARRIER);
            }
        }

        // 2. Wasser von Y = -59 bis Y = 309
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                for (int y = minHeight + 1; y < maxHeight; y++) {
                    chunk.setBlock(i, y, j, Material.WATER);
                }
            }
        }

        // 3. Barrier-Schicht bei Y = 310
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                chunk.setBlock(i, maxHeight, j, Material.BARRIER);
            }
        }

        // Setze das Biome auf Deep Ocean für diesen Chunk
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                biome.setBiome(i, j, Biome.DEEP_OCEAN);
            }
        }

        return chunk;
    }
}
