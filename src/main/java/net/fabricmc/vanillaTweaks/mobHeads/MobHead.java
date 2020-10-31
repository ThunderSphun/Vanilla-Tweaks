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
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.nbt.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.StringJoiner;

public class MobHead implements Comparable<MobHead> {
	public static final String GROUND = "_head";
	public static final String WALL = "_wall_head";

	private final EntityType<?> entity;
	private final Tag nbt;
	private final Identifier id;
	private final double dropRate;
	private final double looting;
	private final boolean inverted;

	public MobHead(EntityType<?> entity, Tag nbt, double dropRate, double looting, Identifier id, boolean inverted) {
		this.entity = entity;
		this.nbt = nbt;
		this.dropRate = dropRate;
		this.looting = looting;
		this.id = id.getNamespace().equals("minecraft") ? new Identifier(VanillaTweaks.MOD_ID, id.getPath()) : id;
		this.inverted = inverted;
	}

	public double getLooting() {
		return this.looting;
	}

	public double getDropRate() {
		return this.dropRate;
	}

	public Tag getNbt() {
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
			return this.dropRate + level * this.looting;
		}

		return -1;
	}

	public double getOdd(DamageSource source) {
		return getOddAsPercent(source) / 100;
	}

	public boolean matches(Tag tag) {
		return NbtHelper.matches(tag, this.nbt, true) != this.inverted;
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", getClass().getSimpleName() + "[", "]")
				.add("entity=" + this.entity)
				.add("nbt=" + (this.inverted ? "!" : "") + this.nbt)
				.add("dropRate=" + this.dropRate + "%")
				.add("looting=" + this.looting + "%")
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
			return this.entity.equals(other.entity) && this.nbt.equals(other.nbt) && this.dropRate == other.dropRate &&
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
		private Tag nbt;
		private Identifier id;
		private double dropRate;
		private double looting;
		private boolean inverted;

		public MobHeadBuilder(EntityType<?> entity) {
			this.entity = entity;
			this.nbt = new CompoundTag();
			this.id = Registry.ENTITY_TYPE.getId(this.entity);
			this.dropRate = 0;
			this.looting = 0;
			this.inverted = false;
		}

		public MobHeadBuilder(String entity) {
			this(Registry.ENTITY_TYPE.get(new Identifier(entity)));
		}

		public MobHeadBuilder(Identifier entity) {
			this(Registry.ENTITY_TYPE.get(entity));
		}

		public MobHeadBuilder(Entity entity) {
			this(entity.getType());
		}

		public MobHeadBuilder setNbt(Tag nbt, String id) {
			return setNbt(nbt, new Identifier(id));
		}

		public MobHeadBuilder setNbt(String nbt, String id) throws CommandSyntaxException {
			return setNbt(nbt, new Identifier(id));
		}

		public MobHeadBuilder setNbt(String nbt, Identifier id) throws CommandSyntaxException {
			StringReader reader = new StringReader(nbt);
			setInverted(isNegated(reader));
			return setNbt(new StringNbtReader(reader).parseCompoundTag(), id);
		}

		public MobHeadBuilder setNbt(Tag nbt, Identifier id) {
			this.nbt = nbt;
			this.id = id;
			return this;
		}

		private boolean isNegated(StringReader reader) {
			reader.skipWhitespace();
			if (reader.canRead() && reader.peek() == '!') {
				reader.skip();
				reader.skipWhitespace();
				return true;
			}
			return false;
		}

		public MobHeadBuilder setInverted(boolean inverted) {
			this.inverted = inverted;
			return this;
		}

		public MobHeadBuilder setDropRate(double dropRate) {
			this.dropRate = dropRate;
			return this;
		}

		public MobHeadBuilder setLooting(double looting) {
			this.looting = looting;
			return this;
		}

		public MobHeadBuilder setRates(double dropRate, double looting) {
			this.dropRate = dropRate;
			this.looting = looting;
			return this;
		}

		public MobHead build() {
			return new MobHead(this.entity, this.nbt, this.dropRate, this.looting, this.id, this.inverted);
		}
	}
}
