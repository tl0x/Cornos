package me.constantindev.ccl.mixin;

import me.constantindev.ccl.etc.config.CConf;
import me.constantindev.ccl.features.module.ModuleRegistry;
import me.constantindev.ccl.features.module.impl.external.XRAY;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class BlockMixin {

    @Inject(method = "shouldDrawSide", cancellable = true, at = @At("HEAD"))
    private static void checkBlock(BlockState state, BlockView world, BlockPos pos, Direction facing, CallbackInfoReturnable<Boolean> cir) {
        if (ModuleRegistry.search(XRAY.class).isEnabled()) {
            boolean isIncluded = false;
            for (Block b : CConf.xrayBlocks) {
                if (state.getBlock().is(b)) isIncluded = true;
            }
            cir.setReturnValue(isIncluded);
        }
    }

    @Inject(method = "isTranslucent", cancellable = true, at = @At("HEAD"))
    public void checkTranslucent(BlockState state, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (ModuleRegistry.search(XRAY.class).isEnabled()) {
            boolean isIncluded = false;
            for (Block b : CConf.xrayBlocks) {
                if (state.getBlock().is(b)) isIncluded = true;
            }
            cir.setReturnValue(!isIncluded);
        }
    }


}
