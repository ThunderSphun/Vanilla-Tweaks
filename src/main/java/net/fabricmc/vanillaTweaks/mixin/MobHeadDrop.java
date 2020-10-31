package net.fabricmc.vanillaTweaks.mixin;

import net.fabricmc.vanillaTweaks.VanillaTweaks;
import net.fabricmc.vanillaTweaks.mobHeads.MobHead;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Mixin(LivingEntity.class)
public abstract class MobHeadDrop extends Entity {
	private MobHeadDrop(EntityType<?> type, World world) {
		super(type, world);
	}

	@Inject(method = "dropLoot(Lnet/minecraft/entity/damage/DamageSource;Z)V", at = @At("RETURN"))
	protected void dropLoot(DamageSource source, boolean causedByPlayer, CallbackInfo ci) {
		if (VanillaTweaks.CONFIG.MORE_MOB_HEADS.isEnabled() && causedByPlayer && !world.isClient
				&& this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {

			List<MobHead> mobHeads = VanillaTweaks.CONFIG.MORE_MOB_HEADS.getOddsAsSortedList();
			List<MobHead> heads = new ArrayList<>();
			System.out.println(toTag(new CompoundTag()));
			for (MobHead head : mobHeads) {
				if (head.equals(this) && head.matches(toTag(new CompoundTag()))) {
					heads.add(head);
					System.out.println(head);
				}
			}

			System.out.println();


//			System.out.println("nbt: " + toTag(new CompoundTag()));
//			List<MobHead> heads = VanillaTweaks.CONFIG.MORE_MOB_HEADS.getOdds()
//					.stream().filter(e -> e.equals(this)).sorted().collect(Collectors.toList());
//			System.out.println("all of type:");
//			heads.forEach(System.out::println);
//
//			if (heads.size() == 0) {
//				return;
//			}
//
//			Optional<MobHead> head = heads.stream().filter(e -> e.matches(toTag(new CompoundTag()))).sorted().findFirst();
//			System.out.println("filtered of type:");
//			heads.stream().filter(e -> e.matches(toTag(new CompoundTag()))).sorted().forEach(System.out::println);
//
//			if (head.isPresent() && head.get().getOdd(source) >= world.random.nextDouble()) {
//				System.out.println("first: " + head.get());
//				Identifier id = head.get().getId();
//				world.spawnEntity(new ItemEntity(world, getPos().x, getPos().y, getPos().z,
//						new ItemStack(Registry.ITEM.get(new Identifier(id.getNamespace(), id.getPath() + MobHead.GROUND)))));
//				System.out.println("drops: " + head.get().getId());
//			}
//			System.out.println('\n');
		}
	}
}
