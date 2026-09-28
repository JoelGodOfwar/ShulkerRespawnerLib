package com.github.joelgodofwar.sr.version;

import lib.github.joelgodofwar.coreutils.util.Version;
import org.bukkit.Bukkit;

public class VersionMatcher {

	/** Last Wrapper_* class you actually ship. Bump when you add a new one. */
	public static final String LAST_SUPPORTED_WRAPPER = "26_3_R1";
	public static final Version LAST_SUPPORTED_MC = new Version("26.3");

	public VersionWrapper match() {
		String raw = getMCVersion();
		String tag = toWrapperTag(raw);

		try {
			return instantiate(tag);
		} catch (ClassNotFoundException missing) {
			if (!LAST_SUPPORTED_WRAPPER.equals(tag)) {
				Bukkit.getLogger().warning("[ShulkerRespawner] No Wrapper_" + tag
						+ " for MC " + raw + "; trying " + LAST_SUPPORTED_WRAPPER);
				try {
					return instantiate(LAST_SUPPORTED_WRAPPER);
				} catch (ClassNotFoundException stillMissing) {
					throw unsupported(raw, stillMissing);
				} catch (ReflectiveOperationException e) {
					throw instantiateFailed(LAST_SUPPORTED_WRAPPER, e);
				}
			}
			throw unsupported(raw, missing);
		} catch (ReflectiveOperationException e) {
			throw instantiateFailed(tag, e);
		}
	}

	public static String getMCVersion() {
		return Version.extractVersion(Bukkit.getVersion());
	}

	/**
	 * Maps a Bukkit MC string (1.21.8, 26.3.1, …) to the Wrapper_* suffix.
	 * Unknown versions newer than LAST_SUPPORTED_MC also get LAST_SUPPORTED_WRAPPER.
	 */
	public String toWrapperTag(String mc) {
		Version v = new Version(mc); // "1.17" and "26.1" are fine; avoid fromString()

		if (v.isAtLeast(LAST_SUPPORTED_MC)) {
			return LAST_SUPPORTED_WRAPPER;
		}

		// 26.x
		if (v.isBetween("26.3", "26.3.99")) return "26_3_R1";
		if (v.isBetween("26.2", "26.2.99")) return "26_2_R1";
		if (v.isBetween("26.1", "26.1.99")) return "26_1_R1";

		// 1.21.x — one range per NMS revision, not one case per patch
		if (v.isBetween("1.21.11", "1.21.99")) return "1_21_R7";
		if (v.equalsString("1.21.10"))         return "1_21_R6";
		if (v.isBetween("1.21.6", "1.21.9"))   return "1_21_R5";
		if (v.equalsString("1.21.5"))          return "1_21_R4";
		if (v.equalsString("1.21.4"))          return "1_21_R3";
		if (v.equalsString("1.21.3"))          return "1_21_R2";
		if (v.isBetween("1.21", "1.21.2"))     return "1_21_R1";

		if (v.isBetween("1.20.5", "1.20.6"))   return "1_20_R4";
		if (v.equalsString("1.20.4"))          return "1_20_R3";
		if (v.isBetween("1.20.2", "1.20.3"))   return "1_20_R2";
		if (v.isBetween("1.20", "1.20.1"))     return "1_20_R1";

		if (v.equalsString("1.19.4"))          return "1_19_R3";
		if (v.equalsString("1.19.3"))          return "1_19_R2";
		if (v.isBetween("1.19", "1.19.2"))     return "1_19_R1";

		if (v.equalsString("1.18.2"))          return "1_18_R2";
		if (v.equalsString("1.18.1"))          return "1_18_1_R1";
		if (v.equalsString("1.18"))            return "1_18_R1";

		if (v.equalsString("1.17.1"))          return "1_17_1_R1";
		if (v.equalsString("1.17"))            return "1_17_R1";

		if (v.isBetween("1.16.4", "1.16.5"))   return "1_16_R3";
		if (v.isBetween("1.16.2", "1.16.3"))   return "1_16_R2";
		if (v.isBetween("1.16", "1.16.1"))     return "1_16_R1";

		if (v.isVersion(new String[]{"1.15", "1.15.2"})) return "1_15_R1";
		if (v.isVersion(new String[]{"1.14", "1.14.4"})) return "1_14_R1";

		if (v.isBetween("1.13.1", "1.13.2"))   return "1_13_R2";
		if (v.equalsString("1.13"))            return "1_13_R1";

		// Older / unmapped: last wrapper is the optimistic try
		return LAST_SUPPORTED_WRAPPER;
	}

	private VersionWrapper instantiate(String tag) throws ReflectiveOperationException {
		return (VersionWrapper) Class.forName(
				getClass().getPackage().getName() + ".Wrapper_" + tag
		).getDeclaredConstructor().newInstance();
	}

	private static IllegalStateException unsupported(String raw, Exception cause) {
		return new IllegalStateException(
				"ShulkerRespawner does not support server version \"" + raw + "\"", cause);
	}

	private static IllegalStateException instantiateFailed(String tag, Exception cause) {
		return new IllegalStateException("Failed to instantiate version wrapper " + tag, cause);
	}
}