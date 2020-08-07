package net.fabricmc.vanillaTweaks.config;

import net.minecraft.block.Block;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WrenchItemConfig extends ItemConfig {
	private List<Block> list;

	public WrenchItemConfig(String name) {
		super(name);

		this.list = new ArrayList<>();
		this.list.add(Registry.BLOCK.get(new Identifier("minecraft:repeater")));
		this.list.add(Registry.BLOCK.get(new Identifier("minecraft:comparator")));
		this.list.add(Registry.BLOCK.get(new Identifier("minecraft:observer")));
		this.list.add(Registry.BLOCK.get(new Identifier("minecraft:dispenser")));
		this.list.add(Registry.BLOCK.get(new Identifier("minecraft:dropper")));
		this.list.add(Registry.BLOCK.get(new Identifier("minecraft:hopper")));
		this.list.add(Registry.BLOCK.get(new Identifier("minecraft:sticky_piston")));
		this.list.add(Registry.BLOCK.get(new Identifier("minecraft:piston")));

		this.list.stream()
				.filter(e -> e.getDefaultState().getProperties().stream()
				.noneMatch(p -> p instanceof DirectionProperty)).forEach(e -> {
					String id = Registry.BLOCK.getId(e).toString();
			LogManager.getLogger().warn(id + " is not rotatable.");
		});
		this.list = this.list.stream().filter(e -> e.getDefaultState().getProperties().stream()
				.anyMatch(p -> p instanceof DirectionProperty)).collect(Collectors.toList());
	}

	public List<Block> getIds() {
		return this.list;
	}
}
