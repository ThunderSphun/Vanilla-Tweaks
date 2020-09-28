package net.fabricmc.vanillaTweaks.util;

import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.fabricmc.vanillaTweaks.mixin.DebugRegisterFix;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;

import java.util.List;

/**
 * Debug fix by 2xsaico
 * Found on https://paste.2x.ax/~saiko/799ae0ce3c3d5bc4ac6f3f042c6c9d356fdcec43
 */
public class DebugRegister {
	public static void fix() {
		DebugRegisterFix.getStates().clear();
		Registry.BLOCK.forEach(DebugRegister::addToDebugGen);
		RegistryEntryAddedCallback.event(Registry.BLOCK)
				.register((i, identifier, block) -> addToDebugGen(block));
	}

	private static void addToDebugGen(Block block) {
		List<BlockState> states = DebugRegisterFix.getStates();
		states.addAll(block.getStateManager().getStates());
		int xSize = MathHelper.ceil(MathHelper.sqrt(states.size()));
		DebugRegisterFix.setXSideLength(xSize);
		DebugRegisterFix.setZSideLength(MathHelper.ceil(states.size() / (float) xSize));
	}
}
