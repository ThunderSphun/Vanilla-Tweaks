package net.fabricmc.vanillaTweaks.config;

import com.google.gson.JsonObject;
import net.fabricmc.vanillaTweaks.mobHeads.MobHead;
import net.minecraft.nbt.CompoundTag;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MobHeadConfig extends EnabledConfig {
	public static final String MOB_HEADS = "more_mob_heads";
	public static final String ODDS = "odds";
	private static final Set<MobHead> DEFAULT_HEADS = new HashSet<>();

	static {
		// TODO: 10/26/2020 update shulker stats
		String[] entities = 			{"minecraft:bat", "minecraft:blaze"	, "minecraft:cave_spider"	, "minecraft:chicken"	, "minecraft:cod"	, "minecraft:cow"	, "minecraft:dolphin"	, "minecraft:donkey", "minecraft:drowned"	, "minecraft:elder_guardian", "minecraft:enderman"	, "minecraft:endermite"	, "minecraft:evoker", "minecraft:ghast"	, "minecraft:guardian"	, "minecraft:hoglin", "minecraft:husk"	, "minecraft:illusioner", "minecraft:iron_golem", "minecraft:magma_cube", "minecraft:mule"	, "minecraft:ocelot", "minecraft:piglin", "minecraft:piglin_brute"	, "minecraft:phantom"	, "minecraft:pig"	, "minecraft:pillager"	, "minecraft:polar_bear", "minecraft:pufferfish", "minecraft:ravager"	, "minecraft:salmon", "minecraft:shulker"	, "minecraft:silverfish", "minecraft:skeleton_horse", "minecraft:slime"	, "minecraft:snow_golem", "minecraft:spider", "minecraft:squid"	, "minecraft:stray"	, "minecraft:tropical_fish"	, "minecraft:turtle", "minecraft:vex"	, "minecraft:vindicator", "minecraft:wandering_trader"	, "minecraft:witch"	, "minecraft:zoglin", "minecraft:zombified_piglin"	, "minecraft:zombie_horse"	, "minecraft:zombie_villager"	};
		double[] dropRates = 			{10				, 0.5				, 0.5						, 1						, 10				, 1					, 33					, 20				, 5						, 100						, 0.5					, 10					, 25				, 6.25				, 0.5					, 3					, 6					, 25					, 5						, 0.5					, 20				, 20				, 4					, 10						, 10					, 1					, 2.5					, 20					, 15					, 25					, 10				, 2						, 5						, 20						, 0.5				, 5						, 0.5				, 5					, 6					, 10						, 10				, 10				, 5						, 100							, 0.5				, 20				, 0.5							, 100						, 50							};
		double[] lootingModifiers = 	{2				, 0.05				, 1							, 0.1					, 1					, 0.1				, 2						, 9					, 2						, 0							, 0.01					, 1						, 2					, 1.25				, 0.1					, 2					, 1					, 2						, 1.5					, 0.1					, 5					, 2					, 1					, 1							, 1						, 0.1				, 0.5					, 5						, 1						, 2						, 1					, 1						, 1						, 5							, 0.1				, 1						, 0.1				, 1					, 5					, 1							, 1					, 1					, 1.5					, 0								, 0.1				, 5					, 0.1							, 0							, 2								};

		for (int i = 0; i < entities.length; i++) {
			DEFAULT_HEADS.add(new MobHead.MobHeadBuilder(entities[i]).setRates(dropRates[i], lootingModifiers[i]).build());
		}

		createVariants("minecraft:bee", 20, 2, "", "pollinated", "angry", "angry_pollinated");
		createVariants("minecraft:cat", 33, 2, "tabby", "tuxedo", "ginger", "siamese", "british_shorthair", "calico", "persian", "ragdoll", "white", "jellie", "black");
		createVariants("minecraft:creeper", 100, 0, "charged");
		createVariants("minecraft:fox", 100, 0, "", "snow");
		createVariants("minecraft:horse", 27, 1, "white", "creamy", "chestnut", "brown", "black", "gray", "dark_brown");
		createVariants("minecraft:llama", 24, 2, "creamy", "white", "brown", "gray");
		createVariants("minecraft:mooshroom", 100, 0, "", "brown");
		createVariants("minecraft:panda", 27, 0.4, "aggressive", "lazy", "playful", "worried", "brown", "weak", "");
		createVariants("minecraft:parrot", 25, 2, "red", "blue", "green", "yellow_blue", "gray");
		createVariants("minecraft:rabbit", 100, 0, "toast", "brown", "white", "black", "white_splotched", "gold", "salt", "killer_rabbit");
		createVariants("minecraft:sheep", 1.75, 0.25, "black", "blue", "brown", "cyan", "gray", "green", "jeb_", "light_blue", "light_gray", "lime", "magenta", "orange", "pink", "purple", "red", "white", "yellow");
		createVariants("minecraft:strider", 10, 5, "", "freezing");
		createVariants("minecraft:trader_llama", 24, 7, "creamy", "white", "brown", "gray");
		createVariants("minecraft:villager", 100, 0, "armorer", "butcher", "cartographer", "cleric", "farmer", "fisherman", "fletcher", "leatherworker", "librarian", "mason", "nitwit", "shepherd", "toolsmith", "weaponsmith");
		createVariants("minecraft:wither", 100, 0, "", "invulnerable", "armored", "invulnerable_armored");
		createVariants("minecraft:wolf", 20, 1, "", "angry");
	}

	private final Set<MobHead> odds;

	public MobHeadConfig(String name, JsonObject json) {
		super(name, json);

		this.odds = DEFAULT_HEADS;
	}

	private static void createVariants(String mob, double dropRate, double lootingModifier, String... variants) {
		for (String variant : variants) {
			String path = mob;
			if (!variant.isEmpty()) {
				path += "_" + variant;
			}
			DEFAULT_HEADS.add(new MobHead.MobHeadBuilder(mob).setRates(dropRate, lootingModifier).setNbt(new CompoundTag(), path).build());
		}
	}

	public Set<MobHead> getOdds() {
		return this.odds;
	}

	public List<MobHead> getOddsAsSortedList() {
		return this.odds.stream().sorted().collect(Collectors.toList());
	}
}
