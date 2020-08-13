package net.fabricmc.vanillaTweaks.config;

import com.google.gson.JsonObject;

public class GraveKeyConfig extends EnabledConfig {
	private static final String SINGLE_USE = "single use";
	private boolean singleUse;

	public GraveKeyConfig(String name, JsonObject json) {
		super(name, json);

		this.singleUse = ConfigUtils.getBoolean(name + "." + SINGLE_USE, json, true);
	}

	public boolean isSingleUse() {
		return this.singleUse;
	}

	void setSingleUse(boolean singleUse) {
		this.singleUse = singleUse;
	}
}
