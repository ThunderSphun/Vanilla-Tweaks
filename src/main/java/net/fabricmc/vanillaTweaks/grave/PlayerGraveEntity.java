package net.fabricmc.vanillaTweaks.grave;

import net.fabricmc.vanillaTweaks.util.ImplementedInventory;
import net.fabricmc.vanillaTweaks.util.Register;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.collection.DefaultedList;

public class PlayerGraveEntity extends BlockEntity implements ImplementedInventory {
	private final DefaultedList<ItemStack> items = DefaultedList.ofSize(41, ItemStack.EMPTY);

	public PlayerGraveEntity() {
		super(Register.PLAYER_GRAVE_ENTITY);
	}

	@Override
	public void fromTag(BlockState state, CompoundTag tag) {
		super.fromTag(state, tag);
		Inventories.fromTag(tag, this.items);
	}

	@Override
	public CompoundTag toTag(CompoundTag tag) {
		Inventories.toTag(tag, this.items);
		return super.toTag(tag);
	}

	@Override
	public DefaultedList<ItemStack> getItems() {
		return this.items;
	}

	@Override
	public boolean canPlayerUse(PlayerEntity player) {
		return false;
	}
}
