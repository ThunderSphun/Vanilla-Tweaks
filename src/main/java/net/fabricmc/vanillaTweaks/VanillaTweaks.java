package net.fabricmc.vanillaTweaks;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.vanillaTweaks.config.Config;
import net.fabricmc.vanillaTweaks.grave.PlayerGraveEntity;
import net.fabricmc.vanillaTweaks.util.Register;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
		}
	}
}
