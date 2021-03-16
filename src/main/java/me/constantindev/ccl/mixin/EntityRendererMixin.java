package me.constantindev.ccl.mixin;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.EnchantEntry;
import me.constantindev.ccl.etc.FakePlayerEntity;
import me.constantindev.ccl.etc.config.Toggleable;
import me.constantindev.ccl.etc.helper.RenderHelper;
import me.constantindev.ccl.etc.reg.ModuleRegistry;
import me.constantindev.ccl.gui.TabGUI;
import me.constantindev.ccl.module.ext.NameTags;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import org.apache.commons.lang3.SerializationUtils;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.util.ArrayList;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin<T extends Entity> {

    private float scale;

    @Shadow
    @Final
    protected EntityRenderDispatcher dispatcher;

    @Shadow public abstract TextRenderer getFontRenderer();

    @Inject(at = {@At("INVOKE")}, method = "renderLabelIfPresent", cancellable = true)
    private void onRenderLabel(T entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        if (entity instanceof FakePlayerEntity) {
            FakePlayerEntity fake = (FakePlayerEntity) entity;
            if (!fake.shouldShowName()) {
                ci.cancel();
                return;
            }
        }

        if (ModuleRegistry.getByName("Nametags").isOn.isOn()) {
            if (entity instanceof PlayerEntity) {
                ((NameTags) ModuleRegistry.getByName("Nametags")).renderCustomLabel(entity, text, matrices, vertexConsumers, light, dispatcher);
                ci.cancel();
            }
        }
    }
}
