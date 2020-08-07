package net.fabricmc.vanillaTweaks;

import net.fabricmc.vanillaTweaks.config.Config;
import net.minecraft.block.BlockState;
import net.minecraft.block.GlazedTerracottaBlock;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RayTraceContext;
import net.minecraft.world.World;

public class WrenchItem extends Item {
	public WrenchItem(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		BlockHitResult result = rayTrace(world, user, RayTraceContext.FluidHandling.NONE);
		if (result.getType() == HitResult.Type.BLOCK) {
			BlockState state = world.getBlockState(result.getBlockPos());
			if (Config.TERRACOTTA_WRENCH.isEnabled() && state.getBlock() instanceof GlazedTerracottaBlock) {
				switch (state.get(HorizontalFacingBlock.FACING)) {
					case SOUTH:
						world.setBlockState(result.getBlockPos(), state.with(HorizontalFacingBlock.FACING,
								user.isSneaking() ? Direction.EAST : Direction.WEST));
						break;
					case NORTH:
						world.setBlockState(result.getBlockPos(), state.with(HorizontalFacingBlock.FACING,
								user.isSneaking() ? Direction.WEST : Direction.EAST));
						break;
					case EAST:
						world.setBlockState(result.getBlockPos(), state.with(HorizontalFacingBlock.FACING,
								user.isSneaking() ? Direction.NORTH : Direction.SOUTH));
						break;
					case WEST:
						world.setBlockState(result.getBlockPos(), state.with(HorizontalFacingBlock.FACING,
								user.isSneaking() ? Direction.SOUTH : Direction.NORTH));
						break;
				}
				return TypedActionResult.success(user.getStackInHand(hand));
			}
		}
		return TypedActionResult.pass(user.getStackInHand(hand));
	}
}
