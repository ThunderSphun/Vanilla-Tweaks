package net.fabricmc.vanillaTweaks.util;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.stream.Collectors;

public class MobVariantMap {
	private final Map<String, Double> dropRate;
	private final Map<String, Double> lootingMod;
	private final Set<String> keys;
	private final Map<String, Item> heads;

	public MobVariantMap() {
		this.dropRate = new HashMap<>();
		this.lootingMod = new HashMap<>();
		this.keys = new HashSet<>();
		this.heads = new HashMap<>();
	}

	public void add(String variant, double dropRate, double lootingModifier) {
		this.dropRate.put(variant, dropRate);
		this.lootingMod.put(variant, lootingModifier);
		this.keys.add(variant);
	}

	public Double getDropRate(String key) {
		return this.dropRate.getOrDefault(key, -1D);
	}

	public Double getLootingModifier(String key) {
		return this.lootingMod.getOrDefault(key, -1D);
	}

	public List<String> getKeys() {
		return this.keys.stream().sorted().collect(Collectors.toList());
	}

	public void addHead(String key, Item item) {
		if (this.keys.contains(key)) {
			this.heads.put(key, item);
		} else {
			System.err.println("something went wrong for variant with " + key);
		}
	}

	public void print(String parent) {
		List<String> printable = this.keys.stream().map(key -> parent + "/" + key + "=" + this.heads.get(key)).collect(Collectors.toList());
		printable.forEach(System.out::println);
	}

	public Collection<Identifier> getKeys(Identifier id) {
		return this.getKeys().stream().map(e -> new Identifier(id.getNamespace(), id.getPath() + (e.isEmpty() ? "" : "_") + e)).collect(Collectors.toList());
	}
}

