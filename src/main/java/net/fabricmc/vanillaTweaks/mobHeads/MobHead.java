package net.fabricmc.vanillaTweaks.mobHeads;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.vanillaTweaks.VanillaTweaks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.StringJoiner;

public class MobHead implements Comparable<MobHead> {
	public static final String GROUND = "_head";
	public static final String WALL = "_wall_head";

	private final EntityType<?> entity;
	private final CompoundTag nbt;
	private final double droprate;
	private final double looting;
	private final Identifier id;

	public MobHead(EntityType<?> entity, CompoundTag nbt, double droprate, double looting, Identifier id) {
		this.entity = entity;
		this.nbt = nbt;
		this.droprate = droprate;
		this.looting = looting;
		this.id = id.getNamespace().equals("minecraft") ? new Identifier(VanillaTweaks.MOD_ID, id.getPath()) : id;
	}

	public double getLooting() {
		return this.looting;
	}

	public double getDroprate() {
		return this.droprate;
	}

	public CompoundTag getNbt() {
		return this.nbt;
	}

	public EntityType<?> getEntity() {
		return this.entity;
	}

	public Identifier getId() {
		return this.id;
	}

	public double getOddAsPercent(DamageSource source) {
		if (source.getAttacker() instanceof LivingEntity) {
			int level = EnchantmentHelper.getLooting((LivingEntity) source.getAttacker());
			return this.droprate + level * this.looting;
		}

		return -1;
	}

	public double getOdd(DamageSource source) {
		return getOddAsPercent(source) / 100;
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", getClass().getSimpleName() + "[", "]")
				.add("entity=" + this.entity)
				.add("nbt=" + this.nbt)
				.add("droprate=" + this.droprate)
				.add("looting=" + this.looting)
				.add("id=" + this.id)
				.toString();
	}

	@Override
	public int compareTo(MobHead other) {
		if (this.equals(other)) {
			return 0;
		}
		if (this.entity.equals(other.entity)) {
			return this.id.compareTo(other.id);
		}
		return Registry.ENTITY_TYPE.getId(this.entity).compareTo(Registry.ENTITY_TYPE.getId(other.entity));
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MobHead) {
			MobHead other = (MobHead) obj;
			return this.entity.equals(other.entity) && this.nbt.equals(other.nbt) && this.droprate == other.droprate &&
					this.looting == other.looting && this.id.equals(other.id);
		}

		if (obj instanceof Entity) {
			obj = ((Entity) obj).getType();
		}
		if (obj instanceof EntityType<?>) {
			return this.entity.equals(obj);
		}

		if (obj instanceof Identifier) {
			return this.id.equals(obj);
		}

		return false;
	}

	public static class MobHeadBuilder {
		private final EntityType<?> entity;
		private CompoundTag nbt;
		private double droprate;
		private double looting;
		private Identifier id;

		public MobHeadBuilder(EntityType<?> entity) {
			this.entity = entity;
			this.nbt = new CompoundTag();
			this.droprate = 0;
			this.looting = 0;
			this.id = Registry.ENTITY_TYPE.getId(this.entity);
		}

		public MobHeadBuilder(String entity) {
			this(Registry.ENTITY_TYPE.get(new Identifier(entity)));
		}

		public MobHeadBuilder(Identifier entity) {
			this(Registry.ENTITY_TYPE.get(entity));
		}

		public MobHeadBuilder setNbt(CompoundTag nbt, String id) {
			return setNbt(nbt, new Identifier(id));
		}

		public MobHeadBuilder setNbt(String nbt, String id) throws CommandSyntaxException {
			return setNbt(new StringNbtReader(new StringReader(nbt)).parseCompoundTag(), new Identifier(id));
		}

		public MobHeadBuilder setNbt(String nbt, Identifier id) throws CommandSyntaxException {
			return setNbt(new StringNbtReader(new StringReader(nbt)).parseCompoundTag(), id);
		}

		public MobHeadBuilder setNbt(CompoundTag nbt, Identifier id) {
			this.nbt = nbt;
			this.id = id;
			return this;
		}

		public MobHeadBuilder setDroprate(double droprate) {
			this.droprate = droprate;
			return this;
		}

		public MobHeadBuilder setLooting(double looting) {
			this.looting = looting;
			return this;
		}

		public MobHeadBuilder setRates(double droprate, double looting) {
			this.droprate = droprate;
			this.looting = looting;
			return this;
		}

		public MobHead build() {
			return new MobHead(this.entity, this.nbt, this.droprate, this.looting, this.id);
		}
	}
}
