package net.fabricmc.vanillaTweaks.config;

import com.google.gson.JsonObject;

public class EnabledConfig {
	public static final String ENABLED = "enabled";
	private boolean enabled;

	public EnabledConfig(String name, JsonObject json) {
		this.enabled = ConfigUtils.getBoolean(name + "." + ENABLED, json, true);
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
