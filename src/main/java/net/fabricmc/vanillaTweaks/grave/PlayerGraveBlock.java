package net.fabricmc.vanillaTweaks.grave;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.vanillaTweaks.VanillaTweaks;
import net.fabricmc.vanillaTweaks.util.Register;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static net.fabricmc.vanillaTweaks.VanillaTweaks.MOD_ID;

public class PlayerGraveBlock extends Block implements BlockEntityProvider {
	public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
	private static final VoxelShape GROUND_PLATE = Block.createCuboidShape(0, 0, 0, 16, 2, 16);
	private static final VoxelShape NORTH_SHAPE = VoxelShapes.union(GROUND_PLATE, Block.createCuboidShape(0, 2, 5, 16, 12, 0));
	private static final VoxelShape EAST_SHAPE = VoxelShapes.union(GROUND_PLATE, Block.createCuboidShape(11, 2, 0, 16, 12, 16));
	private static final VoxelShape SOUTH_SHAPE = VoxelShapes.union(GROUND_PLATE, Block.createCuboidShape(0, 2, 11, 16, 12, 16));
	private static final VoxelShape WEST_SHAPE = VoxelShapes.union(GROUND_PLATE, Block.createCuboidShape(5, 2, 0, 0, 12, 16));

	public PlayerGraveBlock() {
		super(FabricBlockSettings.of(Material.SOIL, MaterialColor.DIRT).strength(-1, 5).dropsNothing()
				.sounds(BlockSoundGroup.GRAVEL));
		setDefaultState(getStateManager().getDefaultState().with(FACING, Direction.NORTH));
	}

	@SuppressWarnings("deprecation")
	@Override
	public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
		if (world.getBlockState(pos.down()).getBlock() == Blocks.GRASS_BLOCK) {
			world.setBlockState(pos.down(), Blocks.DIRT.getDefaultState());
		}
	}

	@Override
	public void buildTooltip(ItemStack stack, BlockView world, List<Text> tooltip, TooltipContext options) {
		List<TranslatableText> tooltips = new ArrayList<>();
		tooltips.add(new TranslatableText("block." + MOD_ID + ".grave.tooltip_info"));
		tooltips.add(new TranslatableText("block." + MOD_ID + ".grave.tooltip_placing"));
		tooltips.add(new TranslatableText("block." + MOD_ID + ".grave.tooltip_inventory"));

		if (VanillaTweaks.CONFIG.GRAVES.isCollectingXp()) {
			tooltips.add(new TranslatableText("block." + MOD_ID + ".grave.tooltip_xp"));
		}

		tooltip.addAll(tooltips.stream().map(e -> e.formatted(Formatting.GRAY)).collect(Collectors.toList()));
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		World world = ctx.getWorld();
		BlockPos pos = ctx.getBlockPos();
		if (world.getBlockState(pos.down()).getBlock() == Blocks.GRASS_BLOCK) {
			world.setBlockState(pos.down(), Blocks.DIRT.getDefaultState());
		}
		return getDefaultState().with(FACING, ctx.getPlayer() == null ? Direction.NORTH : ctx.getPlayer().getHorizontalFacing());
	}

	@Override
	public BlockEntity createBlockEntity(BlockView world) {
		return new PlayerGraveEntity();
	}

	@SuppressWarnings("deprecation")
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (world.isClient) {
			return ActionResult.PASS;
		}

		PlayerGraveEntity blockEntity = (PlayerGraveEntity) world.getBlockEntity(pos);
		if (blockEntity == null) {
			return ActionResult.PASS;
		}
		if ((VanillaTweaks.CONFIG.GRAVES.getKeyConfig().isEnabled() && player.getStackInHand(hand).getItem() == Register.GRAVE_KEY) ||
				(VanillaTweaks.CONFIG.GRAVES.isRobbing() || blockEntity.isGraveFrom(player))) {
			if (player.getStackInHand(hand).getItem() == Register.GRAVE_KEY &&
					VanillaTweaks.CONFIG.GRAVES.getKeyConfig().isSingleUse()) {
				player.getStackInHand(hand).decrement(1);
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
			if (blockEntity.hasExperience()) {
				player.addExperience(blockEntity.getExperience());
			}

			if (world.getRandom().nextInt(5) < 1) {
				ItemEntity item = new ItemEntity(world, player.getX(), player.getY(), player.getZ(),
						new ItemStack(Items.DIRT, world.getRandom().nextInt(3)));
				item.setPickupDelay(5);
				world.spawnEntity(item);
			}
			world.setBlockState(pos, Blocks.AIR.getDefaultState());
			return ActionResult.SUCCESS;
		}
		return ActionResult.PASS;
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
