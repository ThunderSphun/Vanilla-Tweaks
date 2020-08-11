package net.fabricmc.vanillaTweaks.mixin;

import net.fabricmc.vanillaTweaks.grave.PlayerGraveBlock;
import net.fabricmc.vanillaTweaks.grave.PlayerGraveEntity;
import net.fabricmc.vanillaTweaks.util.Register;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerDeath extends LivingEntity {
	@Shadow
	@Final
	public PlayerInventory inventory;

	private PlayerDeath(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Shadow
	protected abstract void vanishCursedItems();

	@Inject(method = "dropInventory()V", at = @At("HEAD"), cancellable = true)
	public void dropInventory(CallbackInfo info) {
		System.out.println("died");
		if (!isAlive()) {
			if (!this.world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) {
				this.world.setBlockState(this.getBlockPos(),
						Register.GRAVE_BLOCK.getDefaultState().with(PlayerGraveBlock.FACING,
								this.getHorizontalFacing().getOpposite()));
				this.vanishCursedItems();
				PlayerGraveEntity grave = (PlayerGraveEntity) this.world.getBlockEntity(this.getBlockPos());
				if (grave != null) {
					for (int i = 0; i < grave.getItems().size(); i++) {
						grave.setStack(i, this.inventory.getStack(i));
						this.inventory.setStack(i, ItemStack.EMPTY);
					}
				}
			}
			info.cancel();
		}
	}

	public void dropXp() {
		if (this.world.getBlockState(this.getBlockPos()).getBlock() == Register.GRAVE_BLOCK) {
			PlayerGraveEntity grave = (PlayerGraveEntity) this.world.getBlockEntity(this.getBlockPos());
			if (grave != null) {
				grave.setExperience(this.getCurrentExperience((PlayerEntity) (Object) this));
			}
		} else {
			super.dropXp();
		}
	}
}
