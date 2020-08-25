package net.fabricmc.vanillaTweaks.mobHeads;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class WallMobHeadBlock extends AbstractMobHead {
	public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
	private static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(4, 4, 8, 12, 12, 16);
	private static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(4, 4, 0, 12, 12, 8);
	private static final VoxelShape EAST_SHAPE = Block.createCuboidShape(0, 4, 4, 8, 12, 12);
	private static final VoxelShape WEST_SHAPE = Block.createCuboidShape(8, 4, 4, 16, 12, 12);

	public WallMobHeadBlock(Block head) {
		super(Settings.copy(head).dropsLike(head));
		this.setDefaultState((this.stateManager.getDefaultState()).with(FACING, Direction.NORTH));
	}

	@Override
	public String getTranslationKey() {
		return this.asItem().getTranslationKey();
	}

	@SuppressWarnings("deprecation")
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		switch (state.get(FACING)) {
			case SOUTH:
				return SOUTH_SHAPE;
			case WEST:
				return WEST_SHAPE;
			case EAST:
				return EAST_SHAPE;
			default:
			case NORTH:
				return NORTH_SHAPE;
		}
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		for (Direction direction : ctx.getPlacementDirections()) {
			if (direction.getAxis().isHorizontal()) {
				if (!ctx.getWorld().getBlockState(ctx.getBlockPos().offset(direction)).canReplace(ctx)) {
					return this.getDefaultState().with(FACING, direction.getOpposite());
				}
			}
		}

		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(FACING)));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
}
