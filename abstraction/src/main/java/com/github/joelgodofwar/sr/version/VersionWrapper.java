package com.github.joelgodofwar.sr.version;

import org.bukkit.entity.Entity;

public interface VersionWrapper {
	
	boolean playerInsideStructure(Entity entity, String version, boolean debug);

}
