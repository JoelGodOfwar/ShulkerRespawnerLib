package com.github.joelgodofwar.sr.version;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_13_R1.CraftWorld;
import org.bukkit.entity.Entity;

import net.minecraft.server.v1_13_R1.BaseBlockPosition;
import net.minecraft.server.v1_13_R1.BlockPosition;
import net.minecraft.server.v1_13_R1.Chunk;
import net.minecraft.server.v1_13_R1.StructureBoundingBox;
import net.minecraft.server.v1_13_R1.StructurePiece;
import net.minecraft.server.v1_13_R1.WorldServer;

public class Wrapper_1_13_R1 implements VersionWrapper {

	@SuppressWarnings("unused")
	@Override
	public boolean playerInsideStructure(Entity entity, String version, boolean debug) {
		WorldServer world = ((CraftWorld) entity.getWorld()).getHandle();
		if(debug) {System.out.println("Location = " + entity.getLocation());}
		BlockPosition bp = new BlockPosition(entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ());
		BlockPosition endcity = world.a("EndCity", bp, 100);
        Location loc = new Location(entity.getWorld(), endcity.getX(), endcity.getY(), endcity.getZ()); // (location, radius, findUnexplored)

        if (endcity == null) {
        	if(debug) {System.out.println("endcity == null");}
            return false;
        }
     
        Chunk chunk = world.getChunkAt(loc.getChunk().getX(), loc.getChunk().getZ());
        if (chunk.a("EndCity") != null) { //Checking if this chunk is the starting point of the structure
            for (StructurePiece piece : chunk.a("EndCity").d()) { //Iterating through every piece of the structure
            	StructureBoundingBox sbb = piece.d();
                if (sbb.b(new BaseBlockPosition(entity.getLocation().getBlockX(), entity.getLocation().getY(), entity.getLocation().getBlockZ()))) { //Getting the piece's bounding box and then checking if the player is inside
                    return true; //If all this is true, then the player is standing inside the structure
                }
                if(debug) {System.out.println("sbb = " + sbb.toString());}
                if(debug) {System.out.println("piece = " + piece.toString());}
            }
        }else {
        	if(debug) {System.out.println("chunk != StructureStart");}
        }

        return false;
    }

}
