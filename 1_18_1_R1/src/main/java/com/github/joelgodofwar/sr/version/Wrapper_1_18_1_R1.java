package com.github.joelgodofwar.sr.version;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.StructureType;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_18_R1.CraftChunk;
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;
import org.bukkit.entity.Entity;

import net.minecraft.core.Vec3i;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.feature.EndCityFeature;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;

public class Wrapper_1_18_1_R1 implements VersionWrapper {

	@Override
	public boolean playerInsideStructure(Entity entity, String version, boolean debug) {
		CraftWorld world = (CraftWorld) entity.getWorld();
        
		boolean insidestructure = insideStructure(world, entity.getLocation(), StructureType.END_CITY, 16, 0, debug);
		
        if (!insidestructure) {
        	//if(debug){	log("failed to find End City.");	};
        }
        return true;
	}
	
	@SuppressWarnings({ "unused", "rawtypes" })
	private static boolean insideStructure(
            World world,
            Location location,
            StructureType bukkitStructureType,
            int searchRadius,
            int size, 
            boolean debug
    ) {
		 //if(debug){	log("is - location=" + location);	};
		 Location structureLocation;
	
		 
		 structureLocation = world.locateNearestStructure(location, bukkitStructureType, searchRadius, false);
		 //if(debug){	log("is - structureLocation=" + structureLocation);	};
		 if (structureLocation == null) {
			 //if(debug){	log("is - structureLocation == null");	};
			 structureLocation = location;
	     }
	     // Get the chunk at the structure location and cast to CraftChunk
	     CraftChunk craftChunk = (CraftChunk) world.getChunkAt(structureLocation);
	     
	     // Get the chunk from the reference
		 LevelChunk chunk = craftChunk.getHandle();
		       
		 if (chunk == null) {
	        	//if(debug){	log("chunk=null");	};
	            return false;
	        }
	    // Get the structure start map from the NMS Chunk
	    @Nullable StructureStart<?> structureStartMap = craftChunk.getHandle().getStartForFeature(EndCityFeature.END_CITY);
	    
		
	    if (structureStartMap == null) {
	        	//if(debug){	log("structureStartMap=null");	};
	            return false;
	    }
	    
	    StructureStart structureStart = null;
	    
	    //if(debug){	log("structureStartMap=" + structureStartMap.toString());	};
	
	    structureStart = structureStartMap;
	
	    
		
	    // Finally, get the bounding box of the structure
	    for (StructurePiece piece : chunk.getStartForFeature(EndCityFeature.END_CITY).getPieces()) {
	    	if(piece.getBoundingBox().isInside(new Vec3i( location.getX(), location.getY(), location.getZ()))) {
	    		return true;
	    	}
	    }
	    boolean inside = false;
	
	    return inside;
	    
	}
	
}
