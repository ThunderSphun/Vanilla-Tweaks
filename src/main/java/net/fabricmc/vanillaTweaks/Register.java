package net.fabricmc.vanillaTweaks;

import net.fabricmc.vanillaTweaks.grave.PlayerGraveBlock;
import net.fabricmc.vanillaTweaks.grave.PlayerGraveEntity;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class Register {
	//wrench item
	public static final Item WRENCH = new WrenchItem(new Item.Settings().group(ItemGroup.TOOLS).maxCount(1));
	//player grave block, item, block entity
	public static final Block GRAVE_BLOCK = new PlayerGraveBlock();
	public static final Item GRAVE_ITEM = new BlockItem(GRAVE_BLOCK, new Item.Settings().group(ItemGroup.SEARCH));
	public static BlockEntityType<PlayerGraveEntity> PLAYER_GRAVE_ENTITY;
}
