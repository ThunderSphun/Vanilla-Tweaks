package net.fabricmc.vanillaTweaks.util;

import net.minecraft.util.Identifier;

import java.util.*;
import java.util.stream.Collectors;

public class MobVariantMap {
	private final Map<String, Double> dropRate;
	private final Map<String, Double> lootingMod;
	private final Set<String> keys;

	public MobVariantMap() {
		this.dropRate = new HashMap<>();
		this.lootingMod = new HashMap<>();
		this.keys = new HashSet<>();
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

	public Collection<Identifier> getKeys(Identifier id) {
		return this.getKeys().stream().map(e -> new Identifier(id.getNamespace(), id.getPath() + (e.isEmpty() ? "" : "_") + e)).collect(Collectors.toList());
	}
}

