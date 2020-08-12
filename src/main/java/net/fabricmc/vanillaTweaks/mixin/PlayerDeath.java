package net.fabricmc.vanillaTweaks.mixin;

import net.fabricmc.vanillaTweaks.VanillaTweaks;
import net.fabricmc.vanillaTweaks.grave.PlayerGraveBlock;
import net.fabricmc.vanillaTweaks.grave.PlayerGraveEntity;
import net.fabricmc.vanillaTweaks.util.Register;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
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

	@Shadow public abstract void sendMessage(Text message, boolean actionBar);

	@Inject(method = "dropInventory()V", at = @At("HEAD"), cancellable = true)
	public void dropInventory(CallbackInfo info) {
		if (!isAlive()) {
			if (!this.world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY) && !this.inventory.isEmpty()) {
				this.vanishCursedItems();
				PlayerGraveEntity grave = this.generateGrave();
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

	private PlayerGraveEntity generateGrave() {
		if (this.world.getBlockState(this.getBlockPos()).getBlock() != Register.GRAVE_BLOCK &&
				VanillaTweaks.CONFIG.GRAVES.isEnabled()) {

			if (VanillaTweaks.CONFIG.GRAVES.isLocating()) {
				this.sendMessage(new TranslatableText("message." + VanillaTweaks.MOD_ID + ".grave.location",
						this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ(), this.world
						.getDimensionRegistryKey().getValue()).formatted(Formatting.YELLOW), false);
			}
			this.world.setBlockState(this.getBlockPos(),
					Register.GRAVE_BLOCK.getDefaultState().with(PlayerGraveBlock.FACING,
							this.getHorizontalFacing().getOpposite()));
			PlayerGraveEntity grave = (PlayerGraveEntity) this.world.getBlockEntity(this.getBlockPos());
			if (grave != null) {
				grave.setUUID(this.uuid);
			}
		}
		return (PlayerGraveEntity) this.world.getBlockEntity(this.getBlockPos());
	}

	public void dropXp() {
		if (!isAlive() && VanillaTweaks.CONFIG.GRAVES.isCollectingXp()) {
			if (!this.world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY)
					&& this.getCurrentExperience((PlayerEntity) (Object) this) > 0) {
				PlayerGraveEntity grave = this.generateGrave();
				if (grave != null) {
					grave.setExperience(this.getCurrentExperience((PlayerEntity) (Object) this));
				}
			}
		} else {
			super.dropXp();
		}
	}
}
