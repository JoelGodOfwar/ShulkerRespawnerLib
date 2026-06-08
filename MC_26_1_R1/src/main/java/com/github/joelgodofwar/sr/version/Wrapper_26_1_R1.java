package com.github.joelgodofwar.sr.version;

import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.generator.structure.GeneratedStructure;
import org.bukkit.generator.structure.Structure;
import org.bukkit.util.BoundingBox;

public class Wrapper_26_1_R1 implements VersionWrapper {

	@Override
	public boolean playerInsideStructure(Entity entity, String version, boolean debug) {
		if (entity == null || entity.getWorld() == null) {
			return false;
		}

		return insideEndCity(entity.getWorld(), entity.getLocation());
	}

	private static boolean insideEndCity(World world, Location location) {
		int chunkX = location.getBlockX() >> 4;
		int chunkZ = location.getBlockZ() >> 4;

		Collection<GeneratedStructure> structures = world.getStructures(chunkX, chunkZ, Structure.END_CITY);
		if (structures == null || structures.isEmpty()) {
			return false;
		}

		for (GeneratedStructure generatedStructure : structures) {
			BoundingBox boundingBox = generatedStructure.getBoundingBox();
			if (boundingBox != null && boundingBox.contains(
					location.getBlockX(),
					location.getBlockY(),
					location.getBlockZ()
			)) {
				return true;
			}
		}

		return false;
	}
}