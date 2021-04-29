package me.constantindev.ccl.mixin;

import me.constantindev.ccl.etc.reg.ModuleRegistry;
import me.constantindev.ccl.module.ext.AutoFemboy;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayerEntity.class)
public class AbstractClientPlayerEntityMixin {
    @Inject(method = "getSkinTexture", at = @At("HEAD"), cancellable = true)
    public void bruh(CallbackInfoReturnable<Identifier> cir) {
        if (ModuleRegistry.getByName("autofemboy").isOn.isOn()) {
            AbstractClientPlayerEntity pp = (AbstractClientPlayerEntity) ((Object) this);
            int index;
            if (AutoFemboy.repository.containsKey(pp.getUuid())) {
                index = AutoFemboy.repository.get(pp.getUuid());
            } else {
                index = (int) Math.floor(Math.random() * 6) + 1;
                AutoFemboy.repository.put(pp.getUuid(), index);
            }
            cir.setReturnValue(new Identifier("ccl", "femboy/f" + index + ".png"));
        }
    }
}
