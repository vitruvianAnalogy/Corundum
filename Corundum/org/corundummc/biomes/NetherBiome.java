package org.corundummc.biomes;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenHell;

import org.corundummc.biomes.Biome.BiomeType;
import org.corundummc.world.Location;

public class NetherBiome extends Biome<NetherBiome, BiomeGenHell, NetherBiome.NetherBiomeType> {
    protected NetherBiome(Location location) {
        super(location);
    }

    static class NetherBiomeType extends BiomeType<NetherBiomeType, BiomeGenHell, NetherBiome> {
        public static final NetherBiomeType TYPE = new NetherBiomeType();

        private NetherBiomeType() {
            super((BiomeGenHell) BiomeGenBase.hell);
        }

        @Override
        public NetherBiome fromLocation(Location location) {
            return new NetherBiome(location);
        }
    }

    @Override
    public NetherBiomeType getType() {
        // TODO Auto-generated method stub
        return null;
    }
}
