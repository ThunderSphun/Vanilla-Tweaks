package net.fabricmc.vanillaTweaks;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.vanillaTweaks.util.Register;
import net.minecraft.client.render.RenderLayer;

public class Client implements ClientModInitializer {
	public void onInitializeClient() {
		Register.GROUND_HEADS.forEach(e -> BlockRenderLayerMap.INSTANCE.putBlock(e, RenderLayer.getCutout()));
		Register.WALL_HEADS.forEach(e -> BlockRenderLayerMap.INSTANCE.putBlock(e, RenderLayer.getCutout()));
	}
}
