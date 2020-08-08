package net.fabricmc.vanillaTweaks;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.vanillaTweaks.config.Config;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VanillaTweaks implements ModInitializer {
	public static final String MOD_ID = "vt";
	public static final Logger LOGGER = LogManager.getLogger();

	@Override
	public void onInitialize() {
		System.out.println();
		System.out.println("mod is loading");
		System.out.println("I REPEAT");
		System.out.println("mod is loading");
		System.out.println();
		if (Config.REDSTONE_WRENCH.isEnabled() || Config.TERRACOTTA_WRENCH.isEnabled()) {
			System.out.println("wrench is enabled");
			Registry.register(Registry.ITEM, new Identifier(MOD_ID, "wrench"), new WrenchItem(new Item.Settings().group(ItemGroup.TOOLS).maxCount(1)));
		}
	}
}
