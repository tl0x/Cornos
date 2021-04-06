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

@Mixin(ClientPlayerEntity.class)
public class TickHook {
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
