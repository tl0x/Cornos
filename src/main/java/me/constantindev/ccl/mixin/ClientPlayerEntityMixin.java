package me.constantindev.ccl.mixin;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.CConf;
import me.constantindev.ccl.etc.event.EventHelper;
import me.constantindev.ccl.etc.event.EventType;
import me.constantindev.ccl.etc.event.arg.Event;
import me.constantindev.ccl.etc.helper.ConfMan;
import me.constantindev.ccl.etc.helper.KeybindMan;
import me.constantindev.ccl.etc.helper.STL;
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
        if (ModuleRegistry.search("Freecam").isEnabled()) {
            ci.cancel();
        }
    }

    @Inject(at = {@At("HEAD")}, method = "isSubmergedInWater", cancellable = true)
    private void isSubmergedInWater(CallbackInfoReturnable<Boolean> cir) {
        if (ModuleRegistry.search("Freecam").isEnabled()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        if (!CConf.checkedForUpdates) {
            STL.update();
            CConf.checkedForUpdates = true;
        }
        if (!ConfMan.enabledMods) ConfMan.enableModsToBeEnabled();
        for (Module m : ModuleRegistry.getAll()) {
            if (Cornos.minecraft.player == null) return; // so this mf will stop complaining
            try {
                if (m.isEnabled()) m.onExecute();
            } catch (Exception ignored) {
            }
        }
        KeybindMan.tick();
        Cornos.notifMan.tick();
        EventHelper.BUS.invokeEventCall(EventType.ONTICK, new Event());
    }

}
