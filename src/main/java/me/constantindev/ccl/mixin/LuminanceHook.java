package me.constantindev.ccl.mixin;

import me.constantindev.ccl.etc.reg.ModuleRegistry;
import net.minecraft.block.AbstractBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public class LuminanceHook {

    @Inject(at = {@At("HEAD")}, method = "getLuminance", cancellable = true)
    public void getLuminace(CallbackInfoReturnable<Integer> cir) {
        if (ModuleRegistry.getByName("xray").isOn.isOn()) {
            cir.setReturnValue(15);
        }
    }
}