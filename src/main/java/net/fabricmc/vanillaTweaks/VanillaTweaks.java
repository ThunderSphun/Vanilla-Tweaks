package net.fabricmc.vanillaTweaks;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.vanillaTweaks.config.Config;
import net.fabricmc.vanillaTweaks.config.MobHeadConfig;
import net.fabricmc.vanillaTweaks.grave.PlayerGraveEntity;
import net.fabricmc.vanillaTweaks.util.MobHeadMap;
import net.fabricmc.vanillaTweaks.util.Register;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class VanillaTweaks implements ModInitializer {
	public static final String MOD_ID = "vt";
	public static final Logger LOGGER = LogManager.getLogger();
	public static final Config CONFIG = new Config("vanilla_tweaks.json");

	@Override
	public void onInitialize() {
		if (CONFIG.REDSTONE_WRENCH.isEnabled() || CONFIG.TERRACOTTA_WRENCH.isEnabled()) {
			Registry.register(Registry.ITEM, new Identifier(MOD_ID, "wrench"), Register.WRENCH);
		}
		if (CONFIG.GRAVES.isEnabled()) {
			Identifier id = new Identifier(MOD_ID, "grave");
			Registry.register(Registry.BLOCK, id, Register.GRAVE_BLOCK);
			Registry.register(Registry.ITEM, id, Register.GRAVE_ITEM);
			Register.PLAYER_GRAVE_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, id,
					BlockEntityType.Builder.create(PlayerGraveEntity::new, Register.GRAVE_BLOCK).build(null));
			if (CONFIG.GRAVES.getKeyConfig().isEnabled()) {
				Registry.register(Registry.ITEM, new Identifier(MOD_ID, "grave_key"), Register.GRAVE_KEY);
			}
		}
		if (CONFIG.MORE_MOB_HEADS.isEnabled()) {
			List<Identifier> keys = CONFIG.MORE_MOB_HEADS.getOdds().getKeysAsId();
			for (int i = 0; i < keys.size(); i++) {
				Registry.register(Registry.BLOCK, new Identifier(MOD_ID, keys.get(i).getPath() + "_head"), Register.GROUND_HEADS.get(i));
				Registry.register(Registry.BLOCK, new Identifier(MOD_ID, keys.get(i).getPath() + "_wall_head"), Register.WALL_HEADS.get(i));
				Registry.register(Registry.ITEM, Registry.BLOCK.getId(Register.GROUND_HEADS.get(i)), Register.HEAD_ITEMS.get(i));
			}
		}
	}
}
