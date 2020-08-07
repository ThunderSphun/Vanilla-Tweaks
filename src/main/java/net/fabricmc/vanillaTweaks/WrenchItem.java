package net.fabricmc.vanillaTweaks;

import net.fabricmc.vanillaTweaks.config.Config;
import net.minecraft.block.GlazedTerracottaBlock;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
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
			System.out.println(result.getBlockPos());
			if (Config.TERRACOTTA_WRENCH.isEnabled() &&
					world.getBlockState(result.getBlockPos()).getBlock() instanceof GlazedTerracottaBlock) {
				System.out.println("it is a terracotta");
				world.getBlockState(result.getBlockPos()).cycle(HorizontalFacingBlock.FACING);
			}
		}
		return super.use(world, user, hand);
	}
}