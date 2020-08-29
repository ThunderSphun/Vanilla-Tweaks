package net.fabricmc.vanillaTweaks.util;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.vanillaTweaks.VanillaTweaks;
import net.fabricmc.vanillaTweaks.WrenchItem;
import net.fabricmc.vanillaTweaks.grave.PlayerGraveBlock;
import net.fabricmc.vanillaTweaks.grave.PlayerGraveEntity;
import net.fabricmc.vanillaTweaks.mobHeads.AbstractMobHead;
import net.fabricmc.vanillaTweaks.mobHeads.MobHeadBlock;
import net.fabricmc.vanillaTweaks.mobHeads.WallMobHeadBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Register {
	//wrench item
	public static final Item WRENCH = new WrenchItem(new Item.Settings().group(ItemGroup.TOOLS).maxCount(1));
	//player grave block, item, block entity, grave key
	public static final Block GRAVE_BLOCK = new PlayerGraveBlock();
	public static final Item GRAVE_ITEM = new BlockItem(GRAVE_BLOCK, new Item.Settings().group(ItemGroup.SEARCH));
	public static BlockEntityType<PlayerGraveEntity> PLAYER_GRAVE_ENTITY;
	public static final Item GRAVE_KEY = new Item(new Item.Settings().group(ItemGroup.TOOLS).maxCount(1));
	//more mob heads tab, and blocks/blockItem lists
	public static final ItemGroup MOB_HEAD_GROUP = FabricItemGroupBuilder.build(
			new Identifier(VanillaTweaks.MOD_ID, "mob_heads"), () -> new ItemStack(Items.WITHER_SKELETON_SKULL));
	public static final List<Block> GROUND_HEADS = VanillaTweaks.CONFIG.MORE_MOB_HEADS.getOdds().getKeysAsId().stream().map(e -> new MobHeadBlock()).collect(Collectors.toList());
	public static final List<Block> WALL_HEADS = GROUND_HEADS.stream().map(WallMobHeadBlock::new).collect(Collectors.toList());
	public static final List<Item> HEAD_ITEMS = IntStream.range(0, GROUND_HEADS.size()).mapToObj(e -> new WallStandingBlockItem(
			GROUND_HEADS.get(e), WALL_HEADS.get(e), new Item.Settings().maxCount(64).group(MOB_HEAD_GROUP).rarity(Rarity.UNCOMMON))).collect(Collectors.toList());
}
