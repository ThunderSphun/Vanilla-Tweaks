package net.fabricmc.vanillaTweaks.config;

import com.google.gson.JsonObject;
import io.github.fablabsmc.fablabs.api.fiber.v1.schema.type.BooleanSerializableType;
import io.github.fablabsmc.fablabs.api.fiber.v1.schema.type.SerializableType;
import io.github.fablabsmc.fablabs.api.fiber.v1.schema.type.derived.ConfigTypes;
import io.github.fablabsmc.fablabs.api.fiber.v1.tree.ConfigBranch;
import io.github.fablabsmc.fablabs.api.fiber.v1.tree.ConfigLeaf;
import io.github.fablabsmc.fablabs.api.fiber.v1.tree.ConfigNode;
import io.github.fablabsmc.fablabs.impl.fiber.tree.ConfigLeafImpl;

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
