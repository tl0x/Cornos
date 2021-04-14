package me.constantindev.ccl.mixin;

import me.constantindev.ccl.etc.reg.ModuleRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public class AbstractBlockStateMixin {
    @Inject(at = {@At("HEAD")}, method = "getLuminance", cancellable = true)
    public void getLuminace(CallbackInfoReturnable<Integer> cir) {
        if (ModuleRegistry.getByName("xray").isOn.isOn()) {
            cir.setReturnValue(15);
        }
    }

    @Inject(at = {@At("TAIL")}, method = "isFullCube", cancellable = true)
    private void isFullCube(BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (ModuleRegistry.getByName("Freecam").isOn.isOn()) {
            cir.setReturnValue(false);
        }
    }
}