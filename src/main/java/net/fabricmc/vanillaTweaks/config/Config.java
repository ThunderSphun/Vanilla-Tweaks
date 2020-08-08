package net.fabricmc.vanillaTweaks.config;

import io.github.fablabsmc.fablabs.api.fiber.v1.schema.type.derived.ConfigTypes;
import io.github.fablabsmc.fablabs.api.fiber.v1.tree.ConfigBranch;
import io.github.fablabsmc.fablabs.api.fiber.v1.tree.ConfigTree;

public class Config {
	private static final ConfigBranch CONFIG = ConfigTree.builder()
			.fork("terracotta_rotation_wrench")
				.withValue(EnabledConfig.ENABLED, ConfigTypes.BOOLEAN, true)
				.finishBranch()
			.fork("redstone_rotation_wrench")
				.withValue(WrenchItemConfig.ENABLED, ConfigTypes.BOOLEAN, true)
				.withValue(WrenchItemConfig.BLOCKS, ConfigTypes.makeArray(ConfigTypes.STRING), new String[]{
						"minecraft:repeater",
						"minecraft:comparator",
						"minecraft:observer",
						"minecraft:dispenser",
						"minecraft:dropper",
						"minecraft:hopper",
						"minecraft:sticky_piston",
						"minecraft:piston"
				})
				.finishBranch()
			.build();
	public static final WrenchItemConfig REDSTONE_WRENCH = new WrenchItemConfig("redstone_rotation_wrench", CONFIG);
	public static final EnabledConfig TERRACOTTA_WRENCH = new EnabledConfig("terracotta_rotation_wrench", CONFIG);
}
