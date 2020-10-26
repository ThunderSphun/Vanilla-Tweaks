package net.fabricmc.vanillaTweaks;

import net.fabricmc.vanillaTweaks.config.Config;
import net.minecraft.block.BlockState;
import net.minecraft.block.GlazedTerracottaBlock;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class WrenchItem extends Item {
	private static final List<Direction> HEADINGS = new ArrayList<>(Properties.FACING.getValues());

	public WrenchItem(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if (!world.isClient()) {
			BlockHitResult result = raycast(world, user, RaycastContext.FluidHandling.NONE);
			if (result.getType() == HitResult.Type.BLOCK) {
				BlockState state = world.getBlockState(result.getBlockPos());

				//checks if glazed terracotta is being targeted
				if (VanillaTweaks.CONFIG.TERRACOTTA_WRENCH.isEnabled() && state.getBlock() instanceof GlazedTerracottaBlock) {
					this.swapState(world, state, result.getBlockPos(), HorizontalFacingBlock.FACING, user.isSneaking());
					return TypedActionResult.success(user.getStackInHand(hand));

					//checks if a configured redstone blockType is being targeted
				} else if (VanillaTweaks.CONFIG.REDSTONE_WRENCH.isEnabled() && VanillaTweaks.CONFIG.REDSTONE_WRENCH.getIds()
						.stream().anyMatch(e -> e.equals(state.getBlock()))) {
					DirectionProperty property = (DirectionProperty) state.getProperties().stream()
							.filter(p -> p instanceof DirectionProperty).findFirst().get();

					this.swapState(world, state, result.getBlockPos(), property, user.isSneaking());
					return TypedActionResult.success(user.getStackInHand(hand));
				}
			}
		}
		return TypedActionResult.pass(user.getStackInHand(hand));
	}

	private void swapState(World world, BlockState state, BlockPos pos, DirectionProperty property, boolean sneaking) {
		List<Direction> dirList = new ArrayList<>(property.getValues());

		int i = HEADINGS.indexOf(state.get(property)) + (sneaking ? -1 : 1);
		if (i < 0) {
			i = HEADINGS.size() - 1;
		} else if (i >= HEADINGS.size()) {
			i = 0;
		}

		while (!dirList.contains(HEADINGS.get(i))) {
			i += sneaking ? -1 : 1;
			if (i < 0) {
				i = HEADINGS.size() - 1;
			} else if (i >= HEADINGS.size()) {
				i %= HEADINGS.size();
			}
		}

		world.setBlockState(pos, state.with(property, HEADINGS.get(i)));
	}
}
