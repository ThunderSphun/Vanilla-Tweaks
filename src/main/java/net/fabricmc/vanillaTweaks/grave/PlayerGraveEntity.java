package net.fabricmc.vanillaTweaks.grave;

import net.fabricmc.vanillaTweaks.util.ExperienceStorage;
import net.fabricmc.vanillaTweaks.util.ImplementedInventory;
import net.fabricmc.vanillaTweaks.util.Register;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.collection.DefaultedList;

import java.util.UUID;

public class PlayerGraveEntity extends BlockEntity implements ImplementedInventory, ExperienceStorage {
	private static final String XP_KEY = "xp";
	private final DefaultedList<ItemStack> items = DefaultedList.ofSize(41, ItemStack.EMPTY);
	private int xp;
	private UUID uuid;

	public PlayerGraveEntity() {
		super(Register.PLAYER_GRAVE_ENTITY);
		this.xp = 0;
		this.uuid = null;
	}

	@Override
	public void fromTag(BlockState state, CompoundTag tag) {
		super.fromTag(state, tag);
		Inventories.fromTag(tag, this.items);
		tag.putInt(XP_KEY, this.xp);
	}

	@Override
	public CompoundTag toTag(CompoundTag tag) {
		Inventories.toTag(tag, this.items);
		this.xp = tag.getInt(XP_KEY);
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

	@Override
	public int getExperience() {
		return this.xp;
	}

	@Override
	public void setExperience(int experience) {
		this.xp = experience;
	}

	@Override
	public boolean hasExperience() {
		return this.xp > 0;
	}

	public boolean hasNothing() {
		return this.items.isEmpty() && this.xp == 0;
	}

	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}

	public boolean isGraveFrom(PlayerEntity player) {
		return this.uuid == null || player.getUuid().equals(this.uuid);
	}
}
