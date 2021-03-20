package me.constantindev.ccl.mixin;

import me.constantindev.ccl.etc.helper.KeyBindManager;
import me.constantindev.ccl.etc.helper.RenderHelper;
import me.constantindev.ccl.etc.reg.ModuleRegistry;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class TickHook {
    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        ModuleRegistry.getAll().forEach(m -> {
            m.updateVitals();
            if (m.isOn.isOn()) m.onExecute();
        });
        KeyBindManager.tick();

    }
}
