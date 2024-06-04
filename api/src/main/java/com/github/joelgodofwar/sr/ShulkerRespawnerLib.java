package com.github.joelgodofwar.sr;

import java.util.logging.Logger;

import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.joelgodofwar.sr.util.Ansi;
import com.github.joelgodofwar.sr.version.VersionWrapper;
import com.github.joelgodofwar.sr.version.VersionMatcher;

public class ShulkerRespawnerLib  extends JavaPlugin{
	private static VersionWrapper WRAPPER = new VersionMatcher().match();
	static ShulkerRespawnerLib instance;
	@Override
	public void onEnable(){
		instance = this;
		log("ShulkerRespawnerLib has been loaded.");
	}
	
	public final static Logger logger = Logger.getLogger("Minecraft");
	
	public  void log(String dalog){
		logger.info(Ansi.YELLOW + "" + this.getName() + Ansi.RESET + " " + dalog + Ansi.RESET);
	}
	public  void logDebug(String dalog){
		log(" " + this.getDescription().getVersion() + Ansi.RED + Ansi.BOLD + " [DEBUG] " + Ansi.RESET + dalog);
	}
	public void logWarn(String dalog){
		log(" " + this.getDescription().getVersion() + Ansi.RED + Ansi.BOLD + " [WARNING] " + Ansi.RESET + dalog);
	}
	
	/**
	 * 
	 * @param entity
	 * @return
	 */
	public static boolean playerInsideStructure(Entity entity, String version, boolean debug) {
		return WRAPPER.playerInsideStructure(entity, version, debug);
	}
	

	public static Plugin getInstance() {
		// TODO Auto-generated method stub
		return instance;
	}
	
}
