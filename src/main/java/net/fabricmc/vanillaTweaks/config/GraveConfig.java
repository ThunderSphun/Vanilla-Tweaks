package net.fabricmc.vanillaTweaks.config;

import com.google.gson.JsonObject;

public class GraveConfig extends EnabledConfig {
	public static final String ROBBING = "robbing";
	public static final String COLLECTING_XP = "collect xp";
	public static final String LOCATING = "locate grave";
	private boolean robbing;
	private boolean collectingXp;
	private boolean locating;

	public GraveConfig(String name, JsonObject json) {
		super(name, json);

		this.robbing = ConfigUtils.getBoolean(name + "." + ROBBING, json, false);
		this.collectingXp = ConfigUtils.getBoolean(name + "." + COLLECTING_XP, json, true);
		this.locating = ConfigUtils.getBoolean(name + "." + LOCATING, json, true);
	}

	void setRobbing(boolean robbing) {
		this.robbing = robbing;
	}

	void setCollectingXp(boolean collectingXp) {
		this.collectingXp = collectingXp;
	}

	void setLocating(boolean locating) {
		this.locating = locating;
	}

	public boolean isRobbing() {
		return this.robbing;
	}

	public boolean isCollectingXp() {
		return this.collectingXp;
	}

	public boolean isLocating() {
		return this.locating;
	}
}
