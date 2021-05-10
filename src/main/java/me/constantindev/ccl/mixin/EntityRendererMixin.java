package me.constantindev.ccl.mixin;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.reg.ModuleRegistry;
import me.constantindev.ccl.module.ext.NameTags;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin<T extends Entity> {

    @Shadow
    @Final
    protected EntityRenderDispatcher dispatcher;

    @Inject(at = {@At("HEAD")}, method = "renderLabelIfPresent", cancellable = true)
    private void onRenderLabel(T entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        if (entity.getCustomName() != null && entity.getCustomName().equals(Text.of("DoNotRenderThisUsernameISwearToGod"))) {
            ci.cancel();
            return;
        }
        //GL11.glDisable(GL11.GL_LIGHTING);
        if (entity instanceof PlayerEntity) {
            if (Cornos.friendsManager.getFriends().containsKey(text.asString())) {
                text = Text.of("\u00A79" + Cornos.friendsManager.getFriends().get(text.asString()).getFakeName());
            }
            if (ModuleRegistry.search("Nametags").isEnabled()) {
                ((NameTags) ModuleRegistry.search("Nametags")).renderCustomLabel(entity, text, matrices, vertexConsumers, light, dispatcher);
                ci.cancel();
            }
        }
    }

    @Inject(method = "shouldRender", at = @At("HEAD"), cancellable = true)
    public void sR(T entity, Frustum frustum, double x, double y, double z, CallbackInfoReturnable<Boolean> cir) {
        if (entity.getType().equals(EntityType.AREA_EFFECT_CLOUD) && ModuleRegistry.search("antiblockban").isEnabled())
            cir.setReturnValue(false);
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void render(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        if (entity.getType().equals(EntityType.AREA_EFFECT_CLOUD) && ModuleRegistry.search("antiblockban").isEnabled())
            ci.cancel();
    }
}
