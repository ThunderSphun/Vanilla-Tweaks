package net.fabricmc.vanillaTweaks.mixin;

import net.fabricmc.vanillaTweaks.VanillaTweaks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class MobHeadDrop extends Entity {
	private MobHeadDrop(EntityType<?> type, World world) {
		super(type, world);
	}

	@Inject(method = "dropLoot(Lnet/minecraft/entity/damage/DamageSource;Z)V", at = @At("RETURN"))
	protected void dropLoot(DamageSource source, boolean causedByPlayer, CallbackInfo ci) {
		if (causedByPlayer && this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT) && !world.isClient) {

			if (VanillaTweaks.CONFIG.MORE_MOB_HEADS.getOdds().getValues(getType()) != null) {
				double rate = VanillaTweaks.CONFIG.MORE_MOB_HEADS.getOdds().getOdd(getType(), source) / 100;
				System.out.println(rate);

				if (VanillaTweaks.CONFIG.MORE_MOB_HEADS.isEnabled() && rate > world.random.nextDouble()) {
					world.spawnEntity(new ItemEntity(world, getPos().x, getPos().y, getPos().z,
							new ItemStack(VanillaTweaks.CONFIG.MORE_MOB_HEADS.getOdds().getHead(getType()))));
				}
			}
		}
	}
}
