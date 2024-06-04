package com.github.joelgodofwar.sr.version;

import org.bukkit.Location;
import org.bukkit.StructureType;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;
import org.bukkit.entity.Entity;

import net.minecraft.server.v1_16_R2.BaseBlockPosition;
import net.minecraft.server.v1_16_R2.Chunk;
import net.minecraft.server.v1_16_R2.StructureGenerator;
import net.minecraft.server.v1_16_R2.StructurePiece;
import net.minecraft.server.v1_16_R2.WorldServer;

public class Wrapper_1_16_R2 implements VersionWrapper {

	@Override
	public boolean playerInsideStructure(Entity entity, String version, boolean debug) {
		Location loc = entity.getWorld().locateNearestStructure(entity.getLocation(), StructureType.END_CITY, 100, false); // (location, radius, findUnexplored)

        if (loc == null) {
            return false;
        }
     
        WorldServer world = ((CraftWorld) entity.getWorld()).getHandle();
        Chunk chunk = world.getChunkAt(loc.getChunk().getX(), loc.getChunk().getZ());
        if (chunk.a(StructureGenerator.ENDCITY) != null) { //Checking if this chunk is the starting point of the structure
            for (StructurePiece piece : chunk.a(StructureGenerator.ENDCITY).d()) { //Iterating through every piece of the structure
                if (piece.g().b(new BaseBlockPosition(entity.getLocation().getBlockX(), entity.getLocation().getY(), entity.getLocation().getBlockZ()))) { //Getting the piece's bounding box and then checking if the player is inside
                    return true; //If all this is true, then the player is standing inside the structure
                }
            }
        }
        return false;
	}

}
