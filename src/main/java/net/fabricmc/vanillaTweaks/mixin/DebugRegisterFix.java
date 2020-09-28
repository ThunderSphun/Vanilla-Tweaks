package net.fabricmc.vanillaTweaks.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.chunk.DebugChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

/**
 * Debug fix by 2xsaico
 * Found on https://paste.2x.ax/~saiko/799ae0ce3c3d5bc4ac6f3f042c6c9d356fdcec43
 */
@Mixin(DebugChunkGenerator.class)
public interface DebugRegisterFix {
	@Accessor("BLOCK_STATES")
	static List<BlockState> getStates() {
		throw new IllegalStateException("not implemented");
	}

	@Accessor("X_SIDE_LENGTH")
	static void setXSideLength(int i) {
		throw new IllegalStateException("not implemented");
	}

	@Accessor("Z_SIDE_LENGTH")
	static void setZSideLength(int i) {
		throw new IllegalStateException("not implemented");
	}
}