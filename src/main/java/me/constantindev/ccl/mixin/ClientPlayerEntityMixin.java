package me.constantindev.ccl.mixin;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.event.EventHelper;
import me.constantindev.ccl.etc.event.EventType;
import me.constantindev.ccl.etc.event.arg.Event;
import me.constantindev.ccl.etc.helper.KeyBindManager;
import me.constantindev.ccl.etc.reg.ModuleRegistry;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {

    @Inject(at = @At("INVOKE"), method = "pushOutOfBlocks", cancellable = true)
    private void pushOutOfBlocks(double x, double d, CallbackInfo ci) {
        if (ModuleRegistry.getByName("Freecam").isOn.isOn()) {
            ci.cancel();
        }
    }

    @Inject(at = {@At("HEAD")}, method = "isSubmergedInWater", cancellable = true)
    private void isSubmergedInWater(CallbackInfoReturnable<Boolean> cir) {
        if (ModuleRegistry.getByName("Freecam").isOn.isOn()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        if (System.currentTimeMillis() % 120000 == 0) System.gc();
        ModuleRegistry.getAll().forEach(m -> {
            m.updateVitals();
            if (Cornos.minecraft.player == null) return; // so this mf will stop complaining
            try {
                if (m.isOn.isOn()) m.onExecute();
            } catch (Exception ignored) {
            }
        });
        KeyBindManager.tick();
        Cornos.notifMan.tick();
        EventHelper.BUS.invokeEventCall(EventType.ONTICK, new Event());
    }


}
