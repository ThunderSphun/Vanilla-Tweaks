package net.fabricmc.vanillaTweaks.util;

import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.*;

public class MobHeadMap {
	private final Map<EntityType<?>, Double> dropRate;
	private final Map<EntityType<?>, Double> lootingMod;
	private final Map<EntityType<?>, MobVariantMap> variants;
	private final Set<EntityType<?>> keys;

	public MobHeadMap() {
		this.dropRate = new HashMap<>();
		this.lootingMod = new HashMap<>();
		this.variants = new HashMap<>();
		this.keys = new HashSet<>();
	}

	public static EntityType<?> entityFromKey(String key) {
		return Registry.ENTITY_TYPE.get(new Identifier(key));
	}

	public static Identifier keyFromEntity(EntityType<?> key) {
		return Registry.ENTITY_TYPE.getId(key);
	}

	public void add(EntityType<?> key, double dropRate, double lootingModifier) {
		this.dropRate.put(key, dropRate);
		this.lootingMod.put(key, lootingModifier);
		this.keys.add(key);
	}

	public void add(String key, double dropRate, double lootingModifier) {
		this.add(entityFromKey(key), dropRate, lootingModifier);
	}

	public void add(EntityType<?> key, MobVariantMap variantMap) {
		this.variants.put(key, variantMap);
		this.keys.add(key);
	}

	public void add(String key, MobVariantMap variantMap) {
		this.add(entityFromKey(key), variantMap);
	}

	public void add(EntityType<?> key, String variant, double dropRate, double lootingModifier) {
		if (this.variants.containsKey(key)) {
			this.variants.get(key).add(variant, dropRate, lootingModifier);
		} else {
			MobVariantMap variantMap = new MobVariantMap();
			variantMap.add(variant, dropRate, lootingModifier);
			this.add(key, variantMap);
		}
	}

	public void add(String key, String variant, double dropRate, double lootingModifier) {
		this.add(entityFromKey(key), variant, dropRate, lootingModifier);
	}

	public Double getDropRate(EntityType<?> key) {
		return this.dropRate.getOrDefault(key, -1D);
	}

	public Double getLootingModifier(EntityType<?> key) {
		return this.lootingMod.getOrDefault(key, -1D);
	}

	public MobVariantMap getVariant(EntityType<?> key) {
		return this.variants.getOrDefault(key, new MobVariantMap());
	}

	public Map<Value, Double> getValues(String key) {
		return this.getValues(entityFromKey(key));
	}

	public Map<Value, Double> getValues(EntityType<?> key) {
		Map<Value, Double> map = new EnumMap<>(Value.class);
		map.put(Value.DROP_RATE, getDropRate(key));
		map.put(Value.LOOTING_MODIFIER, getLootingModifier(key));
		return map;
	}

	public List<Identifier> getKeysAsId() {
		List<Identifier> list = new ArrayList<>();
		List<EntityType<?>> keys = new ArrayList<>(this.keys);
		keys.sort(Comparator.comparing(MobHeadMap::keyFromEntity));
		for (EntityType<?> type : keys) {
			Identifier id = keyFromEntity(type);
			if (this.variants.containsKey(type)) {
				list.addAll(this.variants.get(type).getKeys(id));
			} else {
				list.add(id);
			}
		}
		return list;
	}

	public List<Map<Value, Double>> asList() {
		List<Map<Value, Double>> list = new ArrayList<>(this.keys.size());
		this.keys.forEach(e -> list.add(this.getValues(e)));
		return list;
	}

	enum Value {
		DROP_RATE,
		LOOTING_MODIFIER
	}
}