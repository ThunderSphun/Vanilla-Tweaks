package net.fabricmc.vanillaTweaks;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.vanillaTweaks.config.Config;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class VanillaTweaks implements ModInitializer {
	public static final String MOD_ID = "vt";

	@Override
	public void onInitialize() {
		if (Config.REDSTONE_WRENCH.isEnabled() || Config.TERRACOTTA_WRENCH.isEnabled()) {
			Registry.register(Registry.ITEM, new Identifier(MOD_ID, "wrench"), new WrenchItem(new Item.Settings().group(ItemGroup.TOOLS).maxCount(1)));
		}
	}
}
