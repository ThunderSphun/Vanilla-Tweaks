package net.fabricmc.vanillaTweaks.grave;

import net.fabricmc.vanillaTweaks.ImplementedInventory;
import net.fabricmc.vanillaTweaks.Register;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

public class PlayerGraveEntity extends BlockEntity implements ImplementedInventory {
	public PlayerGraveEntity() {
		super(Register.PLAYER_GRAVE_ENTITY);
	}

	@Override
	public void fromTag(BlockState state, CompoundTag tag) {
		super.fromTag(state, tag);

	}

	@Override
	public CompoundTag toTag(CompoundTag tag) {
		return super.toTag(tag);
	}
}
