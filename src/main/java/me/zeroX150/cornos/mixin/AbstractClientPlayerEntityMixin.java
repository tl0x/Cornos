package me.zeroX150.cornos.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.zeroX150.cornos.features.module.ModuleRegistry;
import me.zeroX150.cornos.features.module.impl.external.AutoFemboy;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.util.Identifier;

@Mixin(AbstractClientPlayerEntity.class)
public class AbstractClientPlayerEntityMixin {
	@Inject(method = "getSkinTexture", at = @At("HEAD"), cancellable = true)
	public void bruh(CallbackInfoReturnable<Identifier> cir) {
		if (ModuleRegistry.search(AutoFemboy.class).isEnabled()) {
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
