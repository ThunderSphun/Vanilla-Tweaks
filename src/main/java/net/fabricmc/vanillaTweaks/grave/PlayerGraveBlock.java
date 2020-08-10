package net.fabricmc.vanillaTweaks.grave;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class PlayerGraveBlock extends Block implements BlockEntityProvider {
	private static final VoxelShape GROUND_PLATE;
	private static final VoxelShape NORTH_SHAPE;
	private static final VoxelShape EAST_SHAPE;
	private static final VoxelShape SOUTH_SHAPE;
	private static final VoxelShape WEST_SHAPE;
	public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

	public PlayerGraveBlock() {
		super(Settings.of(Material.SOIL));
		setDefaultState(getStateManager().getDefaultState().with(FACING, Direction.NORTH));
	}

	@SuppressWarnings("deprecation")
	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return GROUND_PLATE;
	}

	@SuppressWarnings("deprecation")
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		switch (state.get(FACING)) {
			case NORTH:
				return NORTH_SHAPE;
			case SOUTH:
				return SOUTH_SHAPE;
			case WEST:
				return WEST_SHAPE;
			case EAST:
				return EAST_SHAPE;
			default:
				return GROUND_PLATE;
		}
	}

	@Override
	public BlockEntity createBlockEntity(BlockView world) {
		return new PlayerGraveEntity();
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
		stateManager.add(FACING);
	}

	static {
		GROUND_PLATE = Block.createCuboidShape(0, 0, 0, 16, 2, 16);
		NORTH_SHAPE = VoxelShapes.union(GROUND_PLATE, Block.createCuboidShape(0, 2, 4, 16, 12, 0));
		EAST_SHAPE = VoxelShapes.union(GROUND_PLATE, Block.createCuboidShape(12, 2, 0, 16, 12, 16));
		SOUTH_SHAPE = VoxelShapes.union(GROUND_PLATE, Block.createCuboidShape(0, 2, 12, 16, 12, 16));
		WEST_SHAPE = VoxelShapes.union(GROUND_PLATE, Block.createCuboidShape(4, 2, 0, 0, 12, 16));
	}
}
