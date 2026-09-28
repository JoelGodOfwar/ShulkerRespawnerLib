package com.github.joelgodofwar.sr;

import java.util.logging.Logger;

import lib.github.joelgodofwar.coreutils.CoreUtils;
import lib.github.joelgodofwar.coreutils.util.Validate;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.joelgodofwar.sr.util.Ansi;
import com.github.joelgodofwar.sr.version.VersionWrapper;
import com.github.joelgodofwar.sr.version.VersionMatcher;

public class ShulkerRespawnerLib {
	private static VersionWrapper WRAPPER;
	static ShulkerRespawnerLib instance;
	private final CoreUtils coreUtils;
	public final JavaPlugin plugin;
	public final static Logger logger = Logger.getLogger("Minecraft");

	/**
	 * Constructor - mirrors CoreUtils style
	 */
	public ShulkerRespawnerLib(JavaPlugin plugin, CoreUtils coreUtils) {
		Validate.notNull(plugin, "Main plugin cannot be null");
		Validate.notNull(coreUtils, "CoreUtils cannot be null");

		this.plugin = plugin;
		this.coreUtils = coreUtils;

		WRAPPER = new VersionMatcher().match();

		instance = this;

		log("ShulkerRespawnerLib initialized → ServerType=" + coreUtils.getServerTypeName());
	}

	public  void log(String dalog){
		logger.info(Ansi.YELLOW + "[SRLib]" + Ansi.RESET + " " + dalog + Ansi.RESET);
	}
	public  void logDebug(String dalog){
		log( " " + Ansi.RED + Ansi.BOLD + " [DEBUG] " + Ansi.RESET + dalog);
	}
	public void logWarn(String dalog){
		log(" " + Ansi.RED + Ansi.BOLD + " [WARNING] " + Ansi.RESET + dalog);
	}

	public static boolean playerInsideStructure(Entity entity, String version, boolean debug) {
		if (WRAPPER == null) {
			throw new IllegalStateException("ShulkerRespawnerLib has not been initialized yet!");
		}
		return WRAPPER.playerInsideStructure(entity, version, debug);
	}

	public CoreUtils getCoreUtils() {
		return coreUtils;
	}

	public static ShulkerRespawnerLib getInstance() {
		if (instance == null) {
			throw new IllegalStateException("ShulkerRespawnerLib has not been initialized yet!");
		}
		return instance;
	}
	
}