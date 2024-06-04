package com.github.joelgodofwar.sr.version;

import java.util.Map;
import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.StructureType;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_18_R2.CraftChunk;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R2.util.CraftNamespacedKey;
import org.bukkit.entity.Entity;

import com.mojang.datafixers.util.Pair;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.HolderSet.Direct;
import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;

public class Wrapper_1_18_R2 implements VersionWrapper {

	@Override
	public boolean playerInsideStructure(Entity entity, String version, boolean debug) {
		CraftWorld world = (CraftWorld) entity.getWorld();
        
		boolean insidestructure = insideStructure(world, entity.getLocation(), StructureType.END_CITY, 16, 0, debug);
		
        if (!insidestructure) {
        	//if(debug){	log("failed to find End City.");	};
            return false;
        }
        return true;
	}
	
	@SuppressWarnings("unused")
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
		 Pair<BlockPos, Holder<ConfiguredStructureFeature<?, ?>>> a = locateStructure((CraftWorld) world, location, bukkitStructureType.getName().replace("endcity", "end_city"), searchRadius, false, debug);
		 if(a == null) {
			 //if(debug){	log("a=null");	};
	            return false;
		 }
		 BlockPos bp = a.getFirst();
		 //if(debug){	log("x=" + bp.getX());	};
		 //if(debug){	log("y=" + bp.getY());	};
		 //if(debug){	log("z=" + bp.getZ());	};
		 structureLocation = new Location(world, bp.getX(), bp.getY(), bp.getZ());
		 if (structureLocation == null) {
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
	        // Suppress warnings are again needed due to generic cast and type erasure
	        Map<ConfiguredStructureFeature<?, ?>, StructureStart> structureStartMap = craftChunk.getHandle().getAllStarts();
	        structureStartMap = craftChunk.getHandle().getAllStarts();
	        structureStartMap = craftChunk.getHandle().getAllStarts();
	        structureStartMap = craftChunk.getHandle().getAllStarts();
	        structureStartMap = craftChunk.getHandle().getAllStarts();
	
	        if (structureStartMap == null||structureStartMap.isEmpty()) {
	        	//if(debug){	log("structureStartMap=null");	};
	            return false;
	        }
	        StructureStart structureStart = null;
	        //if(debug){	log("structureStartMap.size()=" + structureStartMap.size());	};
	        for (StructureStart structure : structureStartMap.values()) {
	            // Check type of structure feature
	        	//if(debug){	log("structure=" + structure.toString());	};
	        	//if(debug){	log("structureStart=" + structureStartMap.get(structure).toString());	};
	                structureStart = structure;//StartMap.get(structure);
	                break;
	            
	        }
	     // Check if the structure was actually found
	        if (structureStart == null) {
	        	//if(debug){	log("structureStart=null");	};
	            return false;
	        }
	        int i = 0;
	        // Finally, get the bounding box of the structure
	        for (StructurePiece piece : chunk.getStartForFeature(structureStart.getFeature()).getPieces()) {
	        	//if(debug){	log("i=" + piece.getBoundingBox().maxX() + "," + piece.getBoundingBox().minX());	};
	        	//if(debug){	log("i=" + piece.getBoundingBox().maxZ() + "," + piece.getBoundingBox().minZ());	};
	        	//if(debug){	log("i=" + piece.getType().toString());	};
	        	if(piece.getBoundingBox().isInside(new Vec3i( location.getX(), location.getY(), location.getZ()))) {
	        		return true;
	        	}
	        	i++;
	        }
	        //if(debug){	log("i=" + i);	};
	        boolean inside = false;
	        
	        BoundingBox sboundingBox =  structureStart.getBoundingBox();
	        BoundingBox boundingBox;
	        int MinX = sboundingBox.minX();
	        int MinY = sboundingBox.minY();
	        int MinZ = sboundingBox.minZ();
	        int MaxX = sboundingBox.maxX();
	        int MaxY = sboundingBox.maxY();
	        int MaxZ = sboundingBox.maxZ();
	        //if(debug){	log("MinX=" + MinX);	};
	        //if(debug){	log("MinY=" + MinY);	};
	        //if(debug){	log("MinZ=" + MinZ);	};
	        //if(debug){	log("MaxX=" + MaxX);	};
	        //if(debug){	log("MaxY=" + MaxY);	};
	        //if(debug){	log("MaxZ=" + MaxZ);	};
	        Vec3i vec = new Vec3i(location.getBlockX(), location.getBlockY(), location.getBlockZ());
	        return inside; //sboundingBox.isInside(vec);
	        
	        
	 }
	
	@SuppressWarnings({ "unused" })
	private static @Nullable Pair<BlockPos, Holder<ConfiguredStructureFeature<?, ?>>> locateStructure(CraftWorld craftWorld, Location origin, String key, int i, boolean flag, boolean debug) {
        ServerLevel worldServer = craftWorld.getHandle();
        if(worldServer == null) {
        	//if(debug){	log("worldServer == null");	};
        }
        //if(debug){	log("StructureType.END_CITY.getKey()=" + StructureType.END_CITY.getKey());	};
        NamespacedKey nsk = NamespacedKey.fromString(key);
        //if(debug){	log("nsk=" + nsk);	};
        Registry<ConfiguredStructureFeature<?, ?>> registry = worldServer.registryAccess().registryOrThrow(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY);
        ResourceLocation resourceLocation = CraftNamespacedKey.toMinecraft(nsk);
        ResourceKey<ConfiguredStructureFeature<?, ?>> resourceKey = ResourceKey.create( Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, resourceLocation );
        Holder<ConfiguredStructureFeature<?, ?>> resourceKeyHolder = registry.getHolder(resourceKey).get();
        Direct<ConfiguredStructureFeature<?, ?>> holderSet = HolderSet.direct(resourceKeyHolder);
        BlockPos originPos = new BlockPos(origin.getBlockX(), origin.getBlockY(), origin.getBlockZ() );
        BlockPos location = worldServer.getChunkSource().getGenerator().findNearestMapFeature(worldServer, holderSet, originPos, i, flag).getFirst();
        return worldServer.getChunkSource().getGenerator().findNearestMapFeature(worldServer, holderSet, originPos, i, flag);
	}
	
}
