package net.fabricmc.vanillaTweaks.config;

import com.google.gson.JsonObject;
import net.fabricmc.vanillaTweaks.VanillaTweaks;
import net.minecraft.block.Block;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WrenchItemConfig extends EnabledConfig {
	public static final String BLOCKS = "blocks";
	private static final List<Object> DEFAULT_BLOCKS = Arrays.asList(
			"minecraft:repeater",
			"minecraft:comparator",
			"minecraft:observer",
			"minecraft:dispenser",
			"minecraft:dropper",
			"minecraft:hopper",
			"minecraft:sticky_piston",
			"minecraft:piston");
	private List<Block> list;

	public WrenchItemConfig(String name, JsonObject json) {
		super(name, json);
		this.list = new ArrayList<>();

		ConfigUtils.getList(name + "." + BLOCKS, json, DEFAULT_BLOCKS).stream()
				.filter(e -> e instanceof String).map(e -> Registry.BLOCK.get(new Identifier((String) e)))
				.forEach(e -> {
					if (e.getDefaultState().getProperties().stream().noneMatch(p -> p instanceof DirectionProperty)) {
						VanillaTweaks.LOGGER.warn(Registry.BLOCK.getId(e).toString() + " is not rotatable.");
					} else {
						this.list.add(e);
					}
				});
	}

	public List<Block> getIds() {
		return this.list;
	}

	void setIds(List<Block> blocks) {
		this.list = blocks;
	}
}
