/** This code is property of the Corundum project managed under the Software Developers' Association of Arizona State University.
 *
 * Copying and use of this open-source code is permitted provided that the following requirements are met:
 *
 * - This code may not be used or distributed for private enterprise, including but not limited to personal or corporate profit. - Any products resulting from the copying,
 * use, or modification of this code may not claim endorsement by the Corundum project or any of its members or associates without written formal permission from the endorsing
 * party or parties. - This code may not be copied or used under any circumstances without the inclusion of this notice and mention of the contribution of the code by the
 * Corundum project. In source code form, this notice must be included as a comment as it is here; in binary form, proper documentation must be included with the final product
 * that includes this statement verbatim.
 *
 * @author REALDrummer */

package org.corundummc.world;

import net.minecraft.world.WorldServer;

import org.corundummc.biomes.Biome.BiomeType;
import org.corundummc.utils.interfaces.MCEquivalent;
import org.corundummc.utils.myList.myList;

public class Chunk implements MCEquivalent<net.minecraft.world.chunk.Chunk> {
    private static myList<Chunk> loaded_chunks = new myList<>();

    private final net.minecraft.world.chunk.Chunk chunkMC;

    Chunk(net.minecraft.world.chunk.Chunk chunkMC) {
        this.chunkMC = chunkMC;
    }

    /** This method retrieves the map of biomes over this chunk. Each chunk is 16 blocks wide and long and 256 blocks tall; since biomes stretch from the bottom of the world to
     * the top of the world, Minecraft uses a 16x16 map to describe the biome of each 1x1 vertical column of 256 blocks. This function retrives that map in a two-dimensional
     * array of {@link BiomeType}s in which the first dimension is the x-coordinate and the second dimension is the z-coordinate, both relative to the northwest corner of the
     * biome; for example, <tt>getBiomeMap()[1][3]</tt> would get you the biome of any block in the chunk at the x and z coordinates <tt>(chunk x + 1)</tt> and
     * <tt>(chunk z + 3)</tt>.
     *
     * @return the biome map of this chunk. */
    public BiomeType[][] getBiomeMap() {
        BiomeType[][] results = new BiomeType[16][16];  // all chunks are 16x16 (along x and z axes); the biome map reflects that

        for (int x = 0; x < 16; x++)
            for (int z = 0; z < 16; z++)
                /* Minecraft's "biomeArray" is a byte[256] in which each byte represents the I.D. of the biome at that column of blocks (since biomes are the same at any y)
                 * and each index represents (x*16 + z) where x and z are the x- and z-coordinates of the 1x1 vertical column of blocks */
                // TODO TEST: make sure it's x*16 + z and not z*16 + x
                results[x][z] = BiomeType.getByID(chunkMC.getBiomeArray()[x * 16 + z]);

        return results;
    }

    public static Chunk fromMC(net.minecraft.world.chunk.Chunk chunkMC) {
        return new Chunk(chunkMC);
    }

    public net.minecraft.world.chunk.Chunk MC() {
        return chunkMC;
    }

    public Location getLocation() {
        return new Location(chunkMC.xPosition, 0 /* the y is irrelevant */, chunkMC.zPosition, World.fromMC((net.minecraft.world.WorldServer) chunkMC.getWorld()));
    }

    public boolean isLoaded() {
        return chunkMC.isLoaded();
    }

    public void load() {
        ((WorldServer) chunkMC.getWorld()).theChunkProviderServer.loadChunk(chunkMC.xPosition, chunkMC.zPosition);
    }

    public void unload() {
        ((WorldServer) chunkMC.getWorld()).theChunkProviderServer.dropChunk(chunkMC.xPosition, chunkMC.zPosition);
    }
}
