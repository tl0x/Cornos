package me.zeroX150.cornos.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;

@Mixin(Scoreboard.class)
public class ScoreboardMixin {
	@Inject(method = "addTeam", at = @At("HEAD"), cancellable = true)
	public void a(String string, CallbackInfoReturnable<Team> cir) {
		// minecraft didnt do it themselves so we do it.
		if (string == null)
			cir.setReturnValue(null);
	}
}
