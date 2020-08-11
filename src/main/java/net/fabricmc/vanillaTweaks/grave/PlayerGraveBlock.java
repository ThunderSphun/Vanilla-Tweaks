package net.fabricmc.vanillaTweaks.grave;

import net.fabricmc.vanillaTweaks.util.ImplementedInventory;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class PlayerGraveBlock extends Block implements BlockEntityProvider {
	public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
	private static final VoxelShape GROUND_PLATE = Block.createCuboidShape(0, 0, 0, 16, 2, 16);
	private static final VoxelShape NORTH_SHAPE = VoxelShapes.union(GROUND_PLATE, Block.createCuboidShape(0, 2, 5, 16, 12, 0));
	private static final VoxelShape EAST_SHAPE = VoxelShapes.union(GROUND_PLATE, Block.createCuboidShape(11, 2, 0, 16, 12, 16));
	private static final VoxelShape SOUTH_SHAPE = VoxelShapes.union(GROUND_PLATE, Block.createCuboidShape(0, 2, 11, 16, 12, 16));
	private static final VoxelShape WEST_SHAPE = VoxelShapes.union(GROUND_PLATE, Block.createCuboidShape(5, 2, 0, 0, 12, 16));

	public PlayerGraveBlock() {
		super(Settings.of(Material.SOIL, MaterialColor.DIRT).dropsNothing().sounds(BlockSoundGroup.GRAVEL));
		setDefaultState(getStateManager().getDefaultState().with(FACING, Direction.NORTH));
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		System.out.println("GetPlacementState");
		return getDefaultState().with(FACING, ctx.getPlayer() == null ? Direction.NORTH : ctx.getPlayer().getHorizontalFacing());
	}

	@Override
	public BlockEntity createBlockEntity(BlockView world) {
		System.out.println("CreateBlockEntity");
		PlayerGraveEntity graveEntity = new PlayerGraveEntity();
		return graveEntity;
	}

	@SuppressWarnings("deprecation")
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (world.isClient) {
			return ActionResult.PASS;
		}

		ImplementedInventory blockEntity = (PlayerGraveEntity) world.getBlockEntity(pos);
		if (blockEntity == null) {
			return ActionResult.PASS;
		}

		for (int i = 0; i < blockEntity.getItems().size(); i++) {
			if (!blockEntity.getStack(i).isEmpty()) {
				if (!player.inventory.getStack(i).isEmpty()) {
					ItemEntity item = new ItemEntity(world, player.getX(), player.getY(), player.getZ(), player.inventory.getStack(i));
					item.setOwner(player.getUuid());
					item.setPickupDelay(5);
					world.spawnEntity(item);
				}
				player.inventory.setStack(i, blockEntity.getStack(i));
			}
		}
		world.setBlockState(pos, Blocks.AIR.getDefaultState());
		return ActionResult.SUCCESS;
		/*if (player.getStackInHand(hand).isEmpty()) {
			int slot = blockEntity.getSlot(blockEntity.getFirst());
			if (slot == -1) {
				return ActionResult.PASS;
			}
			player.inventory.offerOrDrop(world, blockEntity.getStack(slot));
			blockEntity.removeStack(slot);
			return ActionResult.SUCCESS;
		} else {
			int slot = blockEntity.getFirstEmptySlot();
			if (slot == -1) {
				return ActionResult.PASS;
			}
			blockEntity.setStack(slot, player.getStackInHand(hand).copy());
			player.getStackInHand(hand).setCount(0);
			return ActionResult.SUCCESS;
		}*/
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
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
		stateManager.add(FACING);
	}
}
