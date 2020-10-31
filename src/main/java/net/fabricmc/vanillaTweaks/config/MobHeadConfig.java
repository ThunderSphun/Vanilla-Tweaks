package net.fabricmc.vanillaTweaks.config;

import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.vanillaTweaks.mobHeads.MobHead;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringNbtReader;

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

		//createVariants("minecraft:bee", 20, 2, "", "pollinated", "angry", "angry_pollinated");
		try {
//			DEFAULT_HEADS.add(new MobHead.MobHeadBuilder("minecraft:bee").setRates(20, 2).setNbt("{HasNectar:1b}", "bee_angry_pollinated").build());
//			DEFAULT_HEADS.add(new MobHead.MobHeadBuilder("minecraft:bee").setRates(20, 2).setNbt("{HasNectar:0b}", "bee_angry").build());
//			DEFAULT_HEADS.add(new MobHead.MobHeadBuilder("minecraft:bee").setRates(20, 2).setNbt("{AngerTime:0,HasNectar:1b}", "bee_pollinated").build());
//			DEFAULT_HEADS.add(new MobHead.MobHeadBuilder("minecraft:bee").setRates(20, 2).setNbt("{AngerTime:0}", "bee").build());
			MobHead head1 = new MobHead.MobHeadBuilder("minecraft:bee").setRates(20, 2).setNbt("!{AngerTime:0,HasNectar:1b}", "bee_angry").build();
			MobHead head2 = new MobHead.MobHeadBuilder("minecraft:bee").setRates(20, 2).setNbt("!{AngerTime:0,HasNectar:0b}", "bee_angry_pollinated").build();
			MobHead head3 = new MobHead.MobHeadBuilder("minecraft:bee").setRates(20, 2).setNbt("{AngerTime:0,HasNectar:1b}", "bee_pollinated").build();
			MobHead head4 = new MobHead.MobHeadBuilder("minecraft:bee").setRates(20, 2).setNbt("{AngerTime:0,HasNectar:0b}", "bee").build();

			String simpleNbt1 = "{AngerTime:1,HasNectar:1b}";
			String simpleNbt2 = "{AngerTime:1,HasNectar:0b}";
			String simpleNbt3 = "{AngerTime:0,HasNectar:1b}";
			String simpleNbt4 = "{AngerTime:0,HasNectar:0b}";

			String nbt1 = "{Brain:{memories:{}},HurtByTimestamp:0,HasStung:0b,Attributes:[{Base:1.0d,Name:\"minecraft:generic.max_health\"},{Base:1.0d,Name:\"minecraft:generic.movement_speed\"}],Invulnerable:0b,FallFlying:0b,ForcedAge:0,PortalCooldown:0,AbsorptionAmount:0.0f,FallDistance:0.0f,InLove:0,DeathTime:0s,HandDropChances:[0.0f,0.0f],CannotEnterHiveTicks:0,PersistenceRequired:0b,UUID:[I;0,0,0,0],Age:0,TicksSincePollination:0,AngerTime:1,Motion:[0.0d,0.0d,0.0d],Health:0.0f,HasNectar:1b,LeftHanded:0b,Air:1s,OnGround:0b,NoAI:1b,Rotation:[0.0f,0.0f],HandItems:[{},{}],ArmorDropChances:[1.0f,1.0f,1.0f,1.0f],Pos:[1.0d,1.0d,1.0d],Fire:0s,ArmorItems:[{},{},{},{}],CropsGrownSincePollination:0,CanPickUpLoot:0b,HurtTime:0s}";
			String nbt2 = "{Brain:{memories:{}},HurtByTimestamp:0,HasStung:0b,Attributes:[{Base:1.0d,Name:\"minecraft:generic.max_health\"},{Base:1.0d,Name:\"minecraft:generic.movement_speed\"}],Invulnerable:0b,FallFlying:0b,ForcedAge:0,PortalCooldown:0,AbsorptionAmount:0.0f,FallDistance:0.0f,InLove:0,DeathTime:0s,HandDropChances:[0.0f,0.0f],CannotEnterHiveTicks:0,PersistenceRequired:0b,UUID:[I;0,0,0,0],Age:0,TicksSincePollination:0,AngerTime:1,Motion:[0.0d,0.0d,0.0d],Health:0.0f,HasNectar:0b,LeftHanded:0b,Air:1s,OnGround:0b,NoAI:1b,Rotation:[0.0f,0.0f],HandItems:[{},{}],ArmorDropChances:[1.0f,1.0f,1.0f,1.0f],Pos:[1.0d,1.0d,1.0d],Fire:0s,ArmorItems:[{},{},{},{}],CropsGrownSincePollination:0,CanPickUpLoot:0b,HurtTime:0s}";
			String nbt3 = "{Brain:{memories:{}},HurtByTimestamp:0,HasStung:0b,Attributes:[{Base:1.0d,Name:\"minecraft:generic.max_health\"},{Base:1.0d,Name:\"minecraft:generic.movement_speed\"}],Invulnerable:0b,FallFlying:0b,ForcedAge:0,PortalCooldown:0,AbsorptionAmount:0.0f,FallDistance:0.0f,InLove:0,DeathTime:0s,HandDropChances:[0.0f,0.0f],CannotEnterHiveTicks:0,PersistenceRequired:0b,UUID:[I;0,0,0,0],Age:0,TicksSincePollination:0,AngerTime:0,Motion:[0.0d,0.0d,0.0d],Health:0.0f,HasNectar:1b,LeftHanded:0b,Air:1s,OnGround:0b,NoAI:1b,Rotation:[0.0f,0.0f],HandItems:[{},{}],ArmorDropChances:[1.0f,1.0f,1.0f,1.0f],Pos:[1.0d,1.0d,1.0d],Fire:0s,ArmorItems:[{},{},{},{}],CropsGrownSincePollination:0,CanPickUpLoot:0b,HurtTime:0s}";
			String nbt4 = "{Brain:{memories:{}},HurtByTimestamp:0,HasStung:0b,Attributes:[{Base:1.0d,Name:\"minecraft:generic.max_health\"},{Base:1.0d,Name:\"minecraft:generic.movement_speed\"}],Invulnerable:0b,FallFlying:0b,ForcedAge:0,PortalCooldown:0,AbsorptionAmount:0.0f,FallDistance:0.0f,InLove:0,DeathTime:0s,HandDropChances:[0.0f,0.0f],CannotEnterHiveTicks:0,PersistenceRequired:0b,UUID:[I;0,0,0,0],Age:0,TicksSincePollination:0,AngerTime:0,Motion:[0.0d,0.0d,0.0d],Health:0.0f,HasNectar:0b,LeftHanded:0b,Air:1s,OnGround:0b,NoAI:1b,Rotation:[0.0f,0.0f],HandItems:[{},{}],ArmorDropChances:[1.0f,1.0f,1.0f,1.0f],Pos:[1.0d,1.0d,1.0d],Fire:0s,ArmorItems:[{},{},{},{}],CropsGrownSincePollination:0,CanPickUpLoot:0b,HurtTime:0s}";

			System.out.print("test 1");
			System.out.print(" " + head1.matches(new StringNbtReader(new StringReader(simpleNbt1)).parseTag()));
			System.out.print(" " + head2.matches(new StringNbtReader(new StringReader(simpleNbt1)).parseTag()));
			System.out.print(" " + head3.matches(new StringNbtReader(new StringReader(simpleNbt1)).parseTag()));
			System.out.print(" " + head4.matches(new StringNbtReader(new StringReader(simpleNbt1)).parseTag()));
			System.out.print(" " + head1.matches(new StringNbtReader(new StringReader(nbt1)).parseTag()));
			System.out.print(" " + head2.matches(new StringNbtReader(new StringReader(nbt1)).parseTag()));
			System.out.print(" " + head3.matches(new StringNbtReader(new StringReader(nbt1)).parseTag()));
			System.out.print(" " + head4.matches(new StringNbtReader(new StringReader(nbt1)).parseTag()));
			System.out.println();

			System.out.print("test 2");
			System.out.print(" " + head1.matches(new StringNbtReader(new StringReader(simpleNbt2)).parseTag()));
			System.out.print(" " + head2.matches(new StringNbtReader(new StringReader(simpleNbt2)).parseTag()));
			System.out.print(" " + head3.matches(new StringNbtReader(new StringReader(simpleNbt2)).parseTag()));
			System.out.print(" " + head4.matches(new StringNbtReader(new StringReader(simpleNbt2)).parseTag()));
			System.out.print(" " + head1.matches(new StringNbtReader(new StringReader(nbt2)).parseTag()));
			System.out.print(" " + head2.matches(new StringNbtReader(new StringReader(nbt2)).parseTag()));
			System.out.print(" " + head3.matches(new StringNbtReader(new StringReader(nbt2)).parseTag()));
			System.out.print(" " + head4.matches(new StringNbtReader(new StringReader(nbt2)).parseTag()));
			System.out.println();

			System.out.print("test 3");
			System.out.print(" " + head1.matches(new StringNbtReader(new StringReader(simpleNbt3)).parseTag()));
			System.out.print(" " + head2.matches(new StringNbtReader(new StringReader(simpleNbt3)).parseTag()));
			System.out.print(" " + head3.matches(new StringNbtReader(new StringReader(simpleNbt3)).parseTag()));
			System.out.print(" " + head4.matches(new StringNbtReader(new StringReader(simpleNbt3)).parseTag()));
			System.out.print(" " + head1.matches(new StringNbtReader(new StringReader(nbt3)).parseTag()));
			System.out.print(" " + head2.matches(new StringNbtReader(new StringReader(nbt3)).parseTag()));
			System.out.print(" " + head3.matches(new StringNbtReader(new StringReader(nbt3)).parseTag()));
			System.out.print(" " + head4.matches(new StringNbtReader(new StringReader(nbt3)).parseTag()));
			System.out.println();

			System.out.print("test 4");
			System.out.print(" " + head1.matches(new StringNbtReader(new StringReader(simpleNbt4)).parseTag()));
			System.out.print(" " + head2.matches(new StringNbtReader(new StringReader(simpleNbt4)).parseTag()));
			System.out.print(" " + head3.matches(new StringNbtReader(new StringReader(simpleNbt4)).parseTag()));
			System.out.print(" " + head4.matches(new StringNbtReader(new StringReader(simpleNbt4)).parseTag()));
			System.out.print(" " + head1.matches(new StringNbtReader(new StringReader(nbt4)).parseTag()));
			System.out.print(" " + head2.matches(new StringNbtReader(new StringReader(nbt4)).parseTag()));
			System.out.print(" " + head3.matches(new StringNbtReader(new StringReader(nbt4)).parseTag()));
			System.out.print(" " + head4.matches(new StringNbtReader(new StringReader(nbt4)).parseTag()));
			System.out.println();

			System.exit(0);






			DEFAULT_HEADS.add(new MobHead.MobHeadBuilder("minecraft:bee").setRates(20, 2).setNbt("!{AngerTime:0,HasNectar:0b}", "bee_angry_pollinated").build());
			DEFAULT_HEADS.add(new MobHead.MobHeadBuilder("minecraft:bee").setRates(20, 2).setNbt("!{AngerTime:0,HasNectar:1b}", "bee_angry").build());
			DEFAULT_HEADS.add(new MobHead.MobHeadBuilder("minecraft:bee").setRates(20, 2).setNbt("{AngerTime:0,HasNectar:0b}", "bee").build());
			DEFAULT_HEADS.add(new MobHead.MobHeadBuilder("minecraft:bee").setRates(20, 2).setNbt("{AngerTime:0,HasNectar:1b}", "bee_pollinated").build());
		} catch (CommandSyntaxException e) {
			e.printStackTrace();
		}

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
