package net.fabricmc.vanillaTweaks.config;

import com.google.gson.JsonObject;

import java.util.*;
import java.util.stream.Collectors;

public class MobHeadConfig extends EnabledConfig {
	public static final String MOB_HEADS = "more_mob_heads";
	public static final String ODDS = "odds";
	private static final MobHeadMap DEFAULT_MAP = new MobHeadMap();
	private MobHeadMap odds;

	static {
		/*
		creeper/charged creeper
		piglin/piglin brute
		 */
		String[] entities = {"minecraft:bat", "minecraft:bee", "minecraft:sheep", "minecraft:blaze", "minecraft:cat", "minecraft:cave_spider", "minecraft:chicken", "minecraft:cod", "minecraft:cow", "minecraft:dolphin", "minecraft:donkey", "minecraft:drowned", "minecraft:elder_guardian", "minecraft:enderman", "minecraft:endermite", "minecraft:evoker", "minecraft:fox", "minecraft:ghast", "minecraft:guardian", "minecraft:hoglin", "minecraft:horse", "minecraft:husk", "minecraft:illusioner", "minecraft:iron_golem", "minecraft:llama", "minecraft:magma_cube", "minecraft:mooshroom", "minecraft:mule", "minecraft:ocelot", "minecraft:panda", "minecraft:parrot", "minecraft:phantom", "minecraft:pig", "minecraft:piglin", "minecraft:pillager", "minecraft:polar_bear", "minecraft:pufferfish", "minecraft:rabbit", "minecraft:ravager", "minecraft:salmon", "minecraft:silverfish", "minecraft:skeleton_horse", "minecraft:slime", "minecraft:snow_golem", "minecraft:spider", "minecraft:squid", "minecraft:stray", "minecraft:strider", "minecraft:trader_llama", "minecraft:tropical_fish", "minecraft:turtle", "minecraft:vex", "minecraft:villager", "minecraft:vindicator", "minecraft:wandering_trader", "minecraft:withch", "minecraft:wither", "minecraft:wolf", "minecraft:zoglin", "minecraft:zombie_horse", "minecraft:zombie_villager", "minecraft:zombified_piglin"};
		double[] dropRates = {10, 20, 1.75, 0.5, 33, 0.5, 1, 10, 1, 33, 20, 5, 100, 0.5, 10, 25, 100, 6.25, 0.5, 3, 27, 6, 25, 5, 24, 0.5, 100, 20, 20, 27, 25, 10, 1, 4, 2.5, 20, 15, 100, 25, 10, 5, 20, 0.5, 5, 0.5, 5, 6, 10, 24, 10, 10, 10, 100, 5, 100, 0.5, 100, 20, 20, 100, 50, 0.5};
		double[] lootingModifiers = {2, 2, 0.25, 0.05, 2, 1, 0.1, 1, 0.1, 2, 9, 2, 0, 0.01, 1, 2, 0, 1.25, 0.1, 2, 1, 1, 2, 1.5, 2, 0.1, 0, 5, 2, 0.4, 2, 1, 0.1, 1, 0.5, 5, 1, 0, 2, 1, 1, 5, 0.1, 1, 0.1, 1, 5, 5, 7, 1, 1, 1, 0, 1.5, 0, 0.1, 0, 1, 5, 0, 2, 0.1};

		for (int i = 0; i < entities.length; i++) {
			DEFAULT_MAP.add(entities[i], dropRates[i], lootingModifiers[i]);
		}
	}

	public MobHeadConfig(String name, JsonObject json) {
		super(name, json);

		this.odds = DEFAULT_MAP;
	}

	public MobHeadMap getOdds() {
		return this.odds;
	}

	public static class MobHeadMap {
		private final Map<String, Double> dropRate;
		private final Map<String, Double> lootingMod;
		private final Set<String> key;

		public MobHeadMap() {
			this.dropRate = new HashMap<>();
			this.lootingMod = new HashMap<>();
			this.key = new HashSet<>();
		}

		public void add(String key, Double dropRate, Double lootingModifier) {
			this.dropRate.put(key, dropRate);
			this.lootingMod.put(key, lootingModifier);
			this.key.add(key);
		}

		public Double getDropRate(String key) {
			return this.dropRate.getOrDefault(key, 0D);
		}

		public Double getLootingModifier(String key) {
			return this.lootingMod.getOrDefault(key, 0D);
		}

		public Map<value, Double> getValues(String key) {
			Map<value, Double> map = new EnumMap<>(value.class);
			map.put(value.DROP_RATE, getDropRate(key));
			map.put(value.LOOTING_MODIFIER, getLootingModifier(key));
			return map;
		}

		public List<String> getKeys() {
			return this.key.stream().sorted().collect(Collectors.toList());
		}

		public List<Map<value, Double>> asList() {
			List<Map<value, Double>> list = new ArrayList<>(this.key.size());
			this.key.forEach(e -> list.add(this.getValues(e)));
			return list;
		}

		enum value {
			DROP_RATE,
			LOOTING_MODIFIER
		}
	}
}
