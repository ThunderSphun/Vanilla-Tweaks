package net.fabricmc.vanillaTweaks.util;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.*;

public class MobHeadMap {
	private final Map<String, Double> dropRate;
	private final Map<String, Double> lootingMod;
	private final Map<String, Item> heads;
	private final Map<String, MobVariantMap> variants;
	private final Set<String> keys;

	public MobHeadMap() {
		this.dropRate = new HashMap<>();
		this.lootingMod = new HashMap<>();
		this.heads = new HashMap<>();
		this.variants = new HashMap<>();
		this.keys = new HashSet<>();
	}

	public static EntityType<?> entityFromKey(String key) {
		return Registry.ENTITY_TYPE.get(new Identifier(key));
	}

	public static String keyFromEntity(EntityType<?> key) {
		return Registry.ENTITY_TYPE.getId(key).toString();
	}

	public void add(String key, double dropRate, double lootingModifier) {
		this.dropRate.put(key, dropRate);
		this.lootingMod.put(key, lootingModifier);
		this.keys.add(key);
	}

	public void add(String key, MobVariantMap variantMap) {
		this.variants.put(key, variantMap);
		this.keys.add(key);
	}

	public void add(String key, String variant, double dropRate, double lootingModifier) {
		if (this.variants.containsKey(key)) {
			this.variants.get(key).add(variant, dropRate, lootingModifier);
		} else {
			MobVariantMap variantMap = new MobVariantMap();
			variantMap.add(variant, dropRate, lootingModifier);
			this.add(key, variantMap);
		}
	}

	public Double getDropRate(String key) {
		return this.dropRate.getOrDefault(key, -1D);
	}

	public Double getLootingModifier(String key) {
		return this.lootingMod.getOrDefault(key, -1D);
	}

	public MobVariantMap getVariant(String key) {
		return this.variants.getOrDefault(key, null);
	}

	public Map<Value, Double> getValues(String key) {
		if (!this.keys.contains(key)) {
			return null;
		}

		Map<Value, Double> map = new EnumMap<>(Value.class);
		map.put(Value.DROP_RATE, getDropRate(key));
		map.put(Value.LOOTING_MODIFIER, getLootingModifier(key));
		return map;
	}

	public Map<Value, Double> getValues(EntityType<?> type) {
		return this.getValues(keyFromEntity(type));
	}

	public List<Identifier> getKeysAsId() {
		List<Identifier> list = new ArrayList<>();
		List<String> keys = new ArrayList<>(this.keys);
		keys.sort(String::compareTo);
		for (String type : keys) {
			Identifier id = new Identifier(type);
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

	public double getOdd(EntityType<?> type, DamageSource source) {
		String id = keyFromEntity(type);

		if (this.keys.contains(id) && source.getAttacker() instanceof LivingEntity) {
			int level = EnchantmentHelper.getLooting((LivingEntity) source.getAttacker());
			return this.getDropRate(id) + level * this.getLootingModifier(id);
		}

		return -1;
	}

	public void addHead(String key, Item item) {
		if (this.keys.contains(key)) {
			if (this.variants.containsKey(key)) {
				this.variants.get(key).addHead(key, item);
			} else {
				this.heads.put(key, item);
			}
		} else {
			System.err.println("something went wrong for " + key);
		}
	}

	public Item getHead(EntityType<?> type) {
		return this.heads.getOrDefault(keyFromEntity(type), null);
	}

	public void print() {
		for (String key : this.keys) {
			if (this.variants.containsKey(key)) {
				this.variants.get(key).print(key);
			} else {
				System.out.println(key + "=" + this.heads.get(key));
			}
		}
	}

	enum Value {
		DROP_RATE,
		LOOTING_MODIFIER
	}
}