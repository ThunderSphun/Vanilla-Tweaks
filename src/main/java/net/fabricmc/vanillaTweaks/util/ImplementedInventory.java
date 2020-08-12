package net.fabricmc.vanillaTweaks.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public interface ImplementedInventory extends Inventory {
	DefaultedList<ItemStack> getItems();

	@Override
	default int size() {
		return this.getItems().size();
	}

	@Override
	default boolean isEmpty() {
		return this.getItems().stream().anyMatch(itemStack -> !itemStack.isEmpty());
	}

	@Override
	default ItemStack getStack(int slot) {
		return this.getItems().get(slot);
	}

	@Override
	default ItemStack removeStack(int slot, int count) {
		ItemStack result = Inventories.splitStack(this.getItems(), slot, count);
		if (!result.isEmpty()) {
			this.markDirty();
		}
		return result;
	}

	default ItemStack getFirst() {
		if (this.getItems().stream().anyMatch(e -> !e.isEmpty())) {
			return this.getItems().stream().filter(e -> !e.isEmpty()).findFirst().get();
		}
		return ItemStack.EMPTY;
	}

	default int getSlot(ItemStack stack) {
		return this.getItems().indexOf(stack);
	}

	default ItemStack removeStack(int slot) {
		return Inventories.removeStack(this.getItems(), slot);
	}

	@Override
	default void setStack(int slot, ItemStack stack) {
		this.getItems().set(slot, stack);
		if (stack.getCount() > getMaxCountPerStack()) {
			stack.setCount(getMaxCountPerStack());
		}
	}

	@Override
	default void clear() {
		this.getItems().clear();
	}

	@Override
	default void markDirty() {
	}

	@Override
	default boolean canPlayerUse(PlayerEntity player) {
		return true;
	}

	default int getFirstEmptySlot() {
		return this.getSlot(ItemStack.EMPTY);
	}
}
