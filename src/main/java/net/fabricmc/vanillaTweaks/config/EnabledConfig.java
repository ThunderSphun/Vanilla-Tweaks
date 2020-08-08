package net.fabricmc.vanillaTweaks.config;

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

	public EnabledConfig(String name, ConfigBranch branch) {
		ConfigBranch subBranch = branch.lookupBranch(name);
		if (subBranch != null) {
			ConfigLeaf<Boolean> node = subBranch.lookupLeaf(ENABLED, BooleanSerializableType.BOOLEAN);
			if (node != null) {
				this.enabled = node.getValue();
			}
		}
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
