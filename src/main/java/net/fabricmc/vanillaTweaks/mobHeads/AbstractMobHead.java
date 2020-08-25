package net.fabricmc.vanillaTweaks.mobHeads;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class AbstractMobHead extends Block {
	public AbstractMobHead(Settings settings) {
		super(settings);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
		return false;
	}
}
