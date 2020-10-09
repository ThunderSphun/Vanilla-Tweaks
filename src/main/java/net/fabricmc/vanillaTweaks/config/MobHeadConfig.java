package net.fabricmc.vanillaTweaks.config;

import com.google.gson.JsonObject;
import net.fabricmc.vanillaTweaks.util.MobHeadMap;

public class MobHeadConfig extends EnabledConfig {
	public static final String MOB_HEADS = "more_mob_heads";
	public static final String ODDS = "odds";
	private static final MobHeadMap DEFAULT_MAP = new MobHeadMap();

	static {
		String[] entities = {"minecraft:bat", "minecraft:blaze", "minecraft:cave_spider", "minecraft:chicken", "minecraft:cod", "minecraft:cow", "minecraft:dolphin", "minecraft:drowned", "minecraft:elder_guardian", "minecraft:enderman", "minecraft:endermite", "minecraft:evoker", "minecraft:ghast", "minecraft:guardian", "minecraft:hoglin", "minecraft:husk", "minecraft:illusioner", "minecraft:iron_golem", "minecraft:magma_cube", "minecraft:ocelot", "minecraft:phantom", "minecraft:pig", "minecraft:piglin", "minecraft:pillager", "minecraft:polar_bear", "minecraft:pufferfish", "minecraft:ravager", "minecraft:salmon", "minecraft:silverfish", "minecraft:slime", "minecraft:snow_golem", "minecraft:spider", "minecraft:squid", "minecraft:stray", "minecraft:strider", "minecraft:tropical_fish", "minecraft:turtle", "minecraft:vex", "minecraft:vindicator", "minecraft:wandering_trader", "minecraft:witch", "minecraft:zoglin", "minecraft:zombified_piglin", "minecraft:zombie_villager"};
		double[] dropRates = {10, 0.5, 0.5, 1, 10, 1, 33, 5, 100, 0.5, 10, 25, 6.25, 0.5, 3, 6, 25, 5, 0.5, 20, 10, 1, 4, 2.5, 20, 15, 25, 10, 5, 0.5, 5, 0.5, 5, 6, 10, 10, 10, 10, 5, 100, 0.5, 20, 0.5, 50};
		double[] lootingModifiers = {2, 0.05, 1, 0.1, 1, 0.1, 2, 2, 0, 0.01, 1, 2, 1.25, 0.1, 2, 1, 2, 1.5, 0.1, 2, 1, 0.1, 1, 0.5, 5, 1, 2, 1, 1, 0.1, 1, 0.1, 1, 5, 5, 1, 1, 1, 1.5, 0, 0.1, 5, 0.1, 2};

		for (int i = 0; i < entities.length; i++) {
			DEFAULT_MAP.add(entities[i], dropRates[i], lootingModifiers[i]);
		}

		createVariants("minecraft:creeper", 100, 0, "charged");
		createVariants("minecraft:piglin", 4, 1, "");
		createVariants("minecraft:piglin", 10, 1, "brute");

		createVariants("minecraft:horse", 20, 9, "donkey");
		createVariants("minecraft:horse", 20, 5, "mule");
		createVariants("minecraft:horse", 20, 5, "skeleton");
		createVariants("minecraft:horse", 100, 0, "zombie");

		createVariants("minecraft:bee", 20, 2, "", "pollinated", "angry", "angry_pollinated");
		createVariants("minecraft:sheep", 1.75, 0.25, "black", "blue", "brown", "cyan", "gray", "green", "jeb_", "light_blue", "light_gray", "lime", "magenta", "orange", "pink", "purple", "red", "white", "yellow");
		createVariants("minecraft:cat", 33, 2, "tabby", "tuxedo", "ginger", "siamese", "british_shorthair", "calico", "persian", "ragdoll", "white", "jellie", "black");
		createVariants("minecraft:fox", 100, 0, "", "snow");
		createVariants("minecraft:horse", 27, 1, "white", "creamy", "chestnut", "brown", "black", "gray", "dark_brown");
		createVariants("minecraft:llama", 24, 2, "creamy", "white", "brown", "gray");
		createVariants("minecraft:mooshroom", 100, 0, "", "brown");
		createVariants("minecraft:panda", 27, 0.4, "aggressive", "lazy", "playful", "worried", "brown", "weak", "");
		createVariants("minecraft:parrot", 25, 2, "red", "blue", "green", "yellow_blue", "gray");
		createVariants("minecraft:rabbit", 100, 0, "toast", "brown", "white", "black", "white_splotched", "gold", "salt", "killer_rabbit");
		createVariants("minecraft:strider", 10, 5, "", "freezing");
		createVariants("minecraft:trader_llama", 24, 7, "creamy", "white", "brown", "gray");
		createVariants("minecraft:villager", 100, 0, "armorer", "butcher", "cartographer", "cleric", "farmer", "fisherman", "fletcher", "leatherworker", "librarian", "mason", "nitwit", "shepherd", "toolsmith", "weaponsmith");
		createVariants("minecraft:wither", 100, 0, "", "invulnerable", "armored", "invulnerable_armored");
		createVariants("minecraft:wolf", 20, 1, "", "angry");
	}

	private final MobHeadMap odds;

	public MobHeadConfig(String name, JsonObject json) {
		super(name, json);

		this.odds = DEFAULT_MAP;

		DEFAULT_MAP.print();
		DEFAULT_MAP.print();
	}

	private static void createVariants(String mob, double dropRate, double lootingModifier, String... variants) {
		for (String variant : variants) {
			DEFAULT_MAP.add(mob, variant, dropRate, lootingModifier);
		}
	}

	public MobHeadMap getOdds() {
		return this.odds;
	}
}
