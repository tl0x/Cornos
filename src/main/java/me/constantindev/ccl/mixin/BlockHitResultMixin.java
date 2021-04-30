package me.constantindev.ccl.mixin;

import me.constantindev.ccl.etc.reg.ModuleRegistry;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockHitResult.class)
public abstract class BlockHitResultMixin {
    @Shadow
    public abstract BlockPos getBlockPos();

    @Inject(method = "getSide", at = @At("HEAD"), cancellable = true)
    public void constructor(CallbackInfoReturnable<Direction> cir) {
        if (ModuleRegistry.getByName("buildlimit").isEnabled()) {
            cir.setReturnValue(Direction.DOWN);
        }
    }
}
