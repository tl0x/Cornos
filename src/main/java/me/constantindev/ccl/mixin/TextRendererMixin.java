package me.constantindev.ccl.mixin;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.features.module.Module;
import me.constantindev.ccl.features.module.ModuleRegistry;
import me.constantindev.ccl.features.module.impl.external.NameProtect;
import net.minecraft.client.font.TextHandler;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.text.CharacterVisitor;
import net.minecraft.text.OrderedText;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TextRenderer.class)
public abstract class TextRendererMixin {
    Module fmod;
    @Shadow
    @Final
    private TextHandler handler;

    @Shadow
    protected abstract float drawLayer(String text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumerProvider, boolean seeThrough, int underlineColor, int light);

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;drawLayer(Ljava/lang/String;FFIZLnet/minecraft/util/math/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;ZII)F"), method = "drawInternal(Ljava/lang/String;FFIZLnet/minecraft/util/math/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;ZIIZ)I")
    private float drawInternal(TextRenderer textRenderer, String text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumerProvider, boolean seeThrough, int underlineColor, int light) {
        return this.drawLayer(Cornos.friendsManager.filterString(text), x, y, color, shadow, matrix, vertexConsumerProvider, seeThrough, underlineColor, light);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/text/OrderedText;accept(Lnet/minecraft/text/CharacterVisitor;)Z"), method = "drawLayer(Lnet/minecraft/text/OrderedText;FFIZLnet/minecraft/util/math/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;ZII)F")
    private boolean drawLayer(OrderedText orderedText, CharacterVisitor visitor) {
        return Cornos.friendsManager.filterOrderedText(orderedText, visitor);
    }

    @Inject(method = "getWidth(Ljava/lang/String;)I", cancellable = true, at = @At("HEAD"))
    public void getW(String text, CallbackInfoReturnable<Integer> cir) {
        if (fmod == null) fmod = ModuleRegistry.search(NameProtect.class);
        if (!fmod.isEnabled()) return;
        String nt = Cornos.friendsManager.filterString(text);
        cir.setReturnValue(MathHelper.ceil(this.handler.getWidth(nt)));
    }

    @Inject(method = "getWidth(Lnet/minecraft/text/OrderedText;)I", cancellable = true, at = @At("HEAD"))
    public void getW(OrderedText text, CallbackInfoReturnable<Integer> cir) {
        if (fmod == null) fmod = ModuleRegistry.search(NameProtect.class);
        if (!fmod.isEnabled()) return;

    }
}
