package me.constantindev.ccl.mixin;

import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Scoreboard.class)
public class ScoreboardMixin {
    @Inject(method = "addTeam", at = @At("HEAD"), cancellable = true)
    public void a(String string, CallbackInfoReturnable<Team> cir) {
        // minecraft didnt do it themselves so we do it.
        if (string == null) cir.setReturnValue(null);
    }
}
