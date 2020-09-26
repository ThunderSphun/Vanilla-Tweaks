package net.fabricmc.vanillaTweaks.mobHeads;

import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WallStandingBlockItem;
import net.minecraft.item.Wearable;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class HeadItem extends WallStandingBlockItem implements Wearable {
	public HeadItem(Block standingBlock, Block wallBlock, Settings settings) {
		super(standingBlock, wallBlock, settings);

		DispenserBlock.registerBehavior(this, (pointer, stack) -> {
			List<LivingEntity> list = pointer.getWorld().getEntities(LivingEntity.class, new Box(pointer.getBlockPos().offset(
					pointer.getBlockState().get(DispenserBlock.FACING))), EntityPredicates.EXCEPT_SPECTATOR.and(new EntityPredicates.CanPickup(stack)));
			if (!list.isEmpty()) {
				LivingEntity entity = list.get(0);
				entity.equipStack(EquipmentSlot.HEAD, stack.split(1));
				if (entity instanceof MobEntity) {
					((MobEntity) entity).setEquipmentDropChance(EquipmentSlot.HEAD, 2.0F);
					((MobEntity) entity).setPersistent();
				}
			}
			return stack;
		});
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		EquipmentSlot equipmentSlot = EquipmentSlot.HEAD;
		if (user.getEquippedStack(equipmentSlot).isEmpty()) {
			user.equipStack(equipmentSlot, itemStack.copy());
			itemStack.decrement(1);
			return TypedActionResult.method_29237(itemStack, world.isClient());
		} else {
			return TypedActionResult.fail(itemStack);
		}
	}
}
