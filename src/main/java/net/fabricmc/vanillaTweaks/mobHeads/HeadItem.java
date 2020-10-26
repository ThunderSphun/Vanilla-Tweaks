package net.fabricmc.vanillaTweaks.mobHeads;

import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WallStandingBlockItem;
import net.minecraft.item.Wearable;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class HeadItem extends WallStandingBlockItem implements Wearable {
	public HeadItem(Block standingBlock, Block wallBlock, Settings settings) {
		super(standingBlock, wallBlock, settings);

		DispenserBlock.registerBehavior(this, HeadItem::dispense);
	}

	private static ItemStack dispense(BlockPointer blockPointer, ItemStack itemStack) {
		ArmorItem.dispenseArmor(blockPointer, itemStack);
		return itemStack;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		EquipmentSlot equipmentSlot = EquipmentSlot.HEAD;
		if (user.getEquippedStack(equipmentSlot).isEmpty()) {
			user.equipStack(equipmentSlot, itemStack.copy());
			itemStack.decrement(1);
			return TypedActionResult.success(itemStack, world.isClient());
		} else {
			return TypedActionResult.fail(itemStack);
		}
	}
}
