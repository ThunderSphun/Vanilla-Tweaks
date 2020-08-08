package net.fabricmc.vanillaTweaks.config;

import com.google.gson.JsonObject;
import io.github.fablabsmc.fablabs.api.fiber.v1.schema.type.ListSerializableType;
import io.github.fablabsmc.fablabs.api.fiber.v1.schema.type.SerializableType;
import io.github.fablabsmc.fablabs.api.fiber.v1.tree.ConfigBranch;
import net.fabricmc.vanillaTweaks.VanillaTweaks;
import net.minecraft.block.Block;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WrenchItemConfig extends EnabledConfig {
	public static final String BLOCKS = "blocks";
	private List<Block> list;

	public WrenchItemConfig(String name, ConfigBranch branch) {
		super(name, branch);
		this.list = new ArrayList<>();

		branch.lookupBranch(name).lookupLeaf(BLOCKS, new ListSerializableType<String>());
		this.list.add(Registry.BLOCK.get(new Identifier("minecraft:repeater")));
		this.list.add(Registry.BLOCK.get(new Identifier("minecraft:comparator")));
		this.list.add(Registry.BLOCK.get(new Identifier("minecraft:observer")));
		this.list.add(Registry.BLOCK.get(new Identifier("minecraft:dispenser")));
		this.list.add(Registry.BLOCK.get(new Identifier("minecraft:dropper")));
		this.list.add(Registry.BLOCK.get(new Identifier("minecraft:hopper")));
		this.list.add(Registry.BLOCK.get(new Identifier("minecraft:sticky_piston")));
		this.list.add(Registry.BLOCK.get(new Identifier("minecraft:piston")));

		List<Block> removableList = this.list.stream().filter(e -> e.getDefaultState().getProperties().stream()
						.noneMatch(p -> p instanceof DirectionProperty)).collect(Collectors.toList());
		removableList.forEach(e -> {
					String id = Registry.BLOCK.getId(e).toString();
					VanillaTweaks.LOGGER.warn(id + " is not rotatable.");
					this.list.remove(e);
				});
	}

	public List<Block> getIds() {
		return this.list;
	}
}
