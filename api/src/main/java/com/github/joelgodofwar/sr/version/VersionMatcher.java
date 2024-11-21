package com.github.joelgodofwar.sr.version;

import org.bukkit.Bukkit;

public class VersionMatcher {
	@SuppressWarnings("deprecation")
	public VersionWrapper match() {
	       final String serverVersion = MCVersion(getMCVersion());
	       //Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);
	       try {
	           return (VersionWrapper) Class.forName(getClass().getPackage().getName() + ".Wrapper_" + serverVersion).newInstance();
	       } catch (IllegalAccessException | InstantiationException exception) {
	           throw new IllegalStateException("Failed to instantiate version wrapper for version " + serverVersion, exception);
	       } catch (ClassNotFoundException exception) {
	           throw new IllegalStateException("ShulkerRespawner does not support server version \"" + serverVersion + "\"", exception);
	       }
	}
	
	public static String getMCVersion() {
		String strVersion = Bukkit.getVersion();
		strVersion = strVersion.substring(strVersion.indexOf("MC: "), strVersion.length());
		strVersion = strVersion.replace("MC: ", "").replace(")", "");
		return strVersion;
	}
	
	public String MCVersion(String string) {
			switch (string){
			case "1.13":
				return "1_13_R1";
			case "1.13.1":
				return "1_13_R2";
			case "1.13.2":
				return "1_13_R2";
			case "1.14":
			case "1.14.1":
			case "1.14.2":
			case "1.14.3":
			case "1.14.4":
				return "1_14_R1";
			case "1.15":
			case "1.15.1":
			case "1.15.2":
				return "1_15_R1";
			case "1.16":
				return "1_16_R1";
			case "1.16.1":
				return "1_16_R1";
			case "1.16.2":
				return "1_16_R2";
			case "1.16.3":
				return "1_16_R2";
			case "1.16.4":
				return "1_16_R3";
			case "1.16.5":
				return "1_16_R3";
			case "1.17":
				return "1_17_R1";
			case "1.17.1":
				return "1_17_1_R1";
			case "1.18":
				return "1_18_R1";
			case "1.18.1":
				return "1_18_1_R1";
			case "1.18.2":
				return "1_18_R2";
			case "1.19":
			case "1.19.1":
			case "1.19.2":
				return "1_19_R1";
			case "1.19.3":
				return "1_19_R2";
			case "1.19.4":
				return "1_19_R3";
			case "1.20":
			case "1.20.1":
				return "1_20_R1";
			case "1.20.2":
			case "1.20.3":
				return "1_20_R2";
			case "1.20.4":
				return "1_20_R3";
			case "1.20.5":
			case "1.20.6":
				return "1_20_R4";
			case "1.21":
			case "1.21.1":
			case "1.21.2":
				return "1_21_R1";
			case "1.21.3":
			case "1.21.4":
				return "1_21_R2";
			}
			return string;
		}
	 
}
