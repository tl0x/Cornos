package me.constantindev.ccl.module.ext;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.EnchantEntry;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.Toggleable;
import me.constantindev.ccl.etc.ms.MType;
import me.constantindev.ccl.gui.TabGUI;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
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
import net.minecraft.text.Text;
import net.minecraft.util.math.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class NameTags extends Module {

    Toggleable health = new Toggleable("Health", true);
    Toggleable items = new Toggleable("Items", true);
    Toggleable renderSelf = new Toggleable("RenderSelf", false);
    private float scale;
    private EntityRenderDispatcher dispatcher;

    public NameTags() {
        super("NameTags", "Shows bigger better nametags that give you more info about the player", MType.MISC);
        this.mconf.add(health);
        this.mconf.add(items);
        this.mconf.add(renderSelf);
    }

    public void renderCustomLabel(Entity entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, EntityRenderDispatcher dispatcher) {
        this.dispatcher = dispatcher;
        double d = dispatcher.getSquaredDistanceToCamera(entity);
        if (d > 4096) {
            return;
        }
        String tag = text.asString();
        float f = entity.getHeight() + 0.5F;
        if (entity instanceof PlayerEntity) {
            matrices.push();
            scale = 3F;
            scale /= 50.0f;
            scale *= (float) 0.55;
            assert Cornos.minecraft.player != null;
            if (Cornos.minecraft.player.distanceTo(entity) > 10)
                scale *= Cornos.minecraft.player.distanceTo(entity) / 10;
            matrices.translate(0.0D, f + (scale * 6), 0.0D);
            matrices.multiply(dispatcher.getRotation());
            matrices.scale(-scale, -scale, scale);
            TextRenderer textRenderer = dispatcher.getTextRenderer();
            if (entity.getUuid() == Cornos.minecraft.player.getUuid() && !renderSelf.isEnabled()) {
                return;
            }
            int health = (int) ((PlayerEntity) entity).getHealth();
            if (health <= ((PlayerEntity) entity).getMaxHealth() * 0.25) { // If health is below 25%
                tag += "§4";
            } else if (health <= ((PlayerEntity) entity).getMaxHealth() * 0.5) { // If health is below 50%
                tag += "§6";
            } else if (health <= ((PlayerEntity) entity).getMaxHealth() * 0.75) { // If health is below 75%
                tag += "§e";
            } else if (health <= ((PlayerEntity) entity).getMaxHealth()) { // If health is below 100% (aka. everything else)
                tag += "§2";
            }
            tag = this.health.isEnabled() ? tag + " " + health : tag;
            int width = textRenderer.getWidth(tag) / 2;
            GL11.glPushMatrix();
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GlStateManager.enableBlend();
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDepthFunc(GL11.GL_ALWAYS);
            me.constantindev.ccl.gui.TabGUI.drawBorderedRect(-width - 4, (textRenderer.fontHeight + 2), width + 4, 1, 1, new Color(0, 0, 0, 255).getRGB(), new Color(2, 20, 50, 150).getRGB(), matrices.peek().getModel());
            Cornos.minecraft.textRenderer.draw(matrices, tag, -Cornos.minecraft.textRenderer.getWidth(tag) / 2F, f, Color.WHITE.getRGB());
            matrices.pop();
            if (items.isEnabled()) {

                getCurrentItemsAndRenderThem((PlayerEntity) entity, 0, -(Cornos.minecraft.textRenderer.fontHeight + 1), matrices, width, vertexConsumers);

            }
            GL11.glDepthFunc(GL11.GL_LEQUAL);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GlStateManager.disableBlend();
            GL11.glEnable(2896);
            GL11.glEnable(2929);
            GL11.glDisable(3042);
            GL11.glPopMatrix();
        }
    }

    private void getCurrentItemsAndRenderThem(PlayerEntity entity, int x, int y, MatrixStack matrices, int w, VertexConsumerProvider vertexConsumers) {
        ItemStack[] items = entity.inventory.armor.toArray(new ItemStack[0]);
        ItemStack inHand = entity.getMainHandStack();
        ItemStack offHand = entity.getOffHandStack();
        ItemStack boots = items[0];
        ItemStack leggings = items[1];
        ItemStack body = items[2];
        ItemStack helm = items[3];
        ItemStack[] stuff;
        stuff = new ItemStack[]{inHand, offHand, helm, body, leggings, boots};
        ArrayList<ItemStack> stacks = new ArrayList<>();
        for (ItemStack i : stuff) {
            if (!i.isEmpty()) {
                stacks.add(i);
            }
        }
        int width = 16 * stacks.size() / 2;
        x -= width;
        for (ItemStack stack : stacks) {
            this.renderItem(stack, x, y, matrices, entity, vertexConsumers);
            x += 16;
        }
    }

    public void renderItem(ItemStack stack, int x, int y, MatrixStack matrices, PlayerEntity entity, VertexConsumerProvider vertexConsumers) {
        RenderSystem.enableDepthTest();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Cornos.minecraft.getItemRenderer().zOffset = -100F;
        GL11.glDisable(GL11.GL_LIGHTING);
        RenderSystem.disableLighting();
        GL11.glDepthFunc(GL11.GL_ALWAYS);
        matrices.push();
        matrices.translate(0D, (entity.getHeight() + 0.5F) + (scale * 6), 0D);
        matrices.multiply(dispatcher.getRotation());
        matrices.scale(-scale, -scale, scale);
        matrices.translate(x + 7F, y - Cornos.minecraft.textRenderer.fontHeight + 8F, 0);
        Cornos.minecraft.getItemRenderer().zOffset += 100;
        drawItem(stack, 0, 0, 16, 16, matrices, vertexConsumers);
        Cornos.minecraft.getItemRenderer().zOffset -= 100;
        if (stack.isDamageable()) {
            matrices.translate(1 - 8.5F, y + Cornos.minecraft.textRenderer.fontHeight + 7.5D, 0);
            if (stack.isDamaged()) {
                drawItemDura(stack, 0, 0, matrices.peek().getModel());
            }
        }
        matrices.pop();
        matrices.push();
        matrices.translate(0D, (entity.getHeight() + 0.5F) + (scale * 6), 0D);
        matrices.multiply(dispatcher.getRotation());
        matrices.scale(-scale, -scale, scale);
        matrices.translate(x + 9F, y - Cornos.minecraft.textRenderer.fontHeight + 12F, 0);
        matrices.scale(0.5F, 0.5F, 1);
        if (stack.getCount() > 1) {
            Cornos.minecraft.textRenderer.drawWithShadow(matrices, "§f" + stack.getCount(), 1.0f, 0.0f, Color.WHITE.getRGB());
        }
        matrices.pop();
        matrices.push();
        matrices.translate(0D, (entity.getHeight() + 0.5F) + (scale * 6), 0D);
        matrices.multiply(dispatcher.getRotation());
        matrices.scale(-scale, -scale, scale);
        EnchantEntry[] enchants = {new EnchantEntry(Enchantments.PROTECTION, "Prot"),
                new EnchantEntry(Enchantments.THORNS, "Th"),
                new EnchantEntry(Enchantments.SHARPNESS, "Sh"),
                new EnchantEntry(Enchantments.FIRE_ASPECT, "Fir"),
                new EnchantEntry(Enchantments.KNOCKBACK, "Kb"),
                new EnchantEntry(Enchantments.UNBREAKING, "Unb"),
                new EnchantEntry(Enchantments.POWER, "Pwr"),
                new EnchantEntry(Enchantments.PUNCH, "Pun"),
                new EnchantEntry(Enchantments.FLAME, "Fla"),
                new EnchantEntry(Enchantments.BINDING_CURSE, "§cBind"),
                new EnchantEntry(Enchantments.VANISHING_CURSE, "§cVan"),
                new EnchantEntry(Enchantments.CHANNELING, "Chan"),
                new EnchantEntry(Enchantments.INFINITY, "Inf"),
                new EnchantEntry(Enchantments.RIPTIDE, "Rip"),
                new EnchantEntry(Enchantments.IMPALING, "Imp"),
                new EnchantEntry(Enchantments.MULTISHOT, "Multi"),
                new EnchantEntry(Enchantments.EFFICIENCY, "Eff"),
                new EnchantEntry(Enchantments.MENDING, "Men")};
        for (EnchantEntry enchant : enchants) {
            int level = EnchantmentHelper.getLevel(enchant.getEnchant(), stack);
            String levelDisplay = "" + level;
            if (level > 10) {
                levelDisplay = "10+";
            }
            if (level > 0) {
                float scale = 0.55f;
                matrices.translate((float) (x + 1), (float) (y + 1), 0.0f);
                matrices.scale(scale, scale, scale);
                Cornos.minecraft.textRenderer.draw(matrices, "§f" + enchant.getName() + " " + levelDisplay, 1.0f, 0.0f, Color.WHITE.getRGB());

                matrices.scale(1.0f / scale, 1.0f / scale, 1.0f / scale);
                matrices.translate((float) (-x - 1), (float) (-y - 1), 0.0f);
                y += (int) ((Cornos.minecraft.textRenderer.fontHeight + 1) * scale);
            }
        }
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        RenderSystem.enableLighting();
        GL11.glEnable(GL11.GL_LIGHTING);
        RenderSystem.disableDepthTest();
        matrices.pop();
    }

    private void drawItem(ItemStack stack, int x, int y, int imageWidth, int imageHeight, MatrixStack matrices, VertexConsumerProvider vertexConsumers) {
        if (stack.getItem() instanceof BlockItem || stack.getItem() instanceof DyeableArmorItem || stack.getItem() == Items.SHIELD || stack.getItem() instanceof SpawnEggItem) {
            GL11.glColor4f(1, 1, 1, 1);
            if (stack.getItem() instanceof BlockItem || stack.getItem() == Items.SHIELD || stack.getItem() instanceof SpawnEggItem || stack.getItem() instanceof DyeableArmorItem) {
                matrices.scale(1, -1, -0.01F);
            } else {
                matrices.scale(1, 1, -0.01F);
            }
            RenderSystem.disableRescaleNormal();
            RenderSystem.normal3f(1, 1, 1);
            RenderSystem.colorMask(true, true, true, true);
            matrices.scale(16, 16, 1);
            ItemStack itemStack = stack.copy();
            itemStack.removeSubTag("Enchantments");
            itemStack.removeSubTag("StoredEnchantments");
            Cornos.minecraft.getTextureManager().bindTexture(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);
            Objects.requireNonNull(Cornos.minecraft.getTextureManager().getTexture(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE)).setFilter(false, false);
            VertexConsumerProvider.Immediate immediate = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
            Cornos.minecraft.getItemRenderer().renderItem(itemStack, ModelTransformation.Mode.GUI, 15728880, OverlayTexture.DEFAULT_UV, matrices, immediate);
            immediate.draw();
            RenderSystem.enableRescaleNormal();
            matrices.scale(0.0625F, 0.0625F, 1);
            if (stack.getItem() instanceof BlockItem || stack.getItem() == Items.SHIELD || stack.getItem() instanceof SpawnEggItem || stack.getItem() instanceof DyeableArmorItem) {
                matrices.scale(1, -1, 0F);
            } else {
                matrices.scale(1, 1, 0F);
            }
            return;
        }
        if (stack.getItem() instanceof PotionItem) {
            drawSprite(Cornos.minecraft.getItemRenderer().getModels().getSprite(new ItemStack(Items.GLASS_BOTTLE)), x + -8, y - 8, imageWidth, imageHeight, matrices, stack.hasGlint());
            Color color = new Color(PotionUtil.getColor(stack));
            drawColorSprite(Cornos.minecraft.getItemRenderer().getModels().getSprite(stack), x + -8, y - 8, imageWidth, imageHeight, matrices, stack.hasGlint(), color);
            return;
        }
        drawSprite(Cornos.minecraft.getItemRenderer().getModels().getSprite(stack), x + -8, y - 8, imageWidth, imageHeight, matrices, stack.hasGlint());
    }

    private void drawItemDura(ItemStack stack, int x, int y, Matrix4f matrix4f) {
        float f = (float) stack.getDamage();
        float g = (float) stack.getMaxDamage();
        float h = Math.max(0.0F, (g - f) / g);
        int i = Math.round(13.0F - f * 13.0F / g);
        Color j = Color.getHSBColor(h / 3.0F, 1.0F, 1.0F);
        me.constantindev.ccl.gui.TabGUI.drawGuiRect(x + 2, y + 1, 13, 2, !stack.isDamaged() ? 0 : 0x80000000, matrix4f);
        TabGUI.drawGuiRect(x + 2, y + 2, i, 1, !stack.isDamaged() ? 0 : j.getRGB(), matrix4f);
    }

    private void drawSprite(Sprite sprite, int x, int y, int imageWidth, int imageHeight, MatrixStack model, boolean glint) {
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(770, 771);
        Cornos.minecraft.getTextureManager().bindTexture(sprite.getAtlas().getId());
        drawSprite(model, x, y, 0, imageWidth, imageHeight, sprite, glint);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    private void drawColorSprite(Sprite sprite, int x, int y, int imageWidth, int imageHeight, MatrixStack model, boolean glint, Color color) {
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(770, 771);
        Cornos.minecraft.getTextureManager().bindTexture(sprite.getAtlas().getId());
        drawColoredTexturedQuad(model.peek().getModel(), x, x + imageWidth, y, y + imageHeight, 0, sprite.getMinU(), sprite.getMaxU(), sprite.getMinV(), sprite.getMaxV(), glint, color);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    private void drawSprite(MatrixStack matrices, int x, int y, int z, int width, int height, Sprite sprite, boolean glint) {
        drawTexturedQuad(matrices.peek().getModel(), x, x + width, y, y + height, z, sprite.getMinU(), sprite.getMaxU(), sprite.getMinV(), sprite.getMaxV(), glint);
    }

    private void drawTexturedQuad(Matrix4f matrices, int x0, int x1, int y0, int y1, int z, float u0, float u1, float v0, float v1, boolean glint) {
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(7, VertexFormats.POSITION_TEXTURE);
        bufferBuilder.vertex(matrices, (float) x0, (float) y1, (float) z).texture(u0, v1).next();
        bufferBuilder.vertex(matrices, (float) x1, (float) y1, (float) z).texture(u1, v1).next();
        bufferBuilder.vertex(matrices, (float) x1, (float) y0, (float) z).texture(u1, v0).next();
        bufferBuilder.vertex(matrices, (float) x0, (float) y0, (float) z).texture(u0, v0).next();
        bufferBuilder.end();
        RenderSystem.enableAlphaTest();
        BufferRenderer.draw(bufferBuilder);
    }

    private void drawColoredTexturedQuad(Matrix4f matrices, int x0, int x1, int y0, int y1, int z, float u0, float u1, float v0, float v1, boolean glint, Color color) {
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(7, VertexFormats.POSITION_COLOR_TEXTURE);
        bufferBuilder.vertex(matrices, (float) x0, (float) y1, (float) z).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).texture(u0, v1).next();
        bufferBuilder.vertex(matrices, (float) x1, (float) y1, (float) z).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).texture(u1, v1).next();
        bufferBuilder.vertex(matrices, (float) x1, (float) y0, (float) z).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).texture(u1, v0).next();
        bufferBuilder.vertex(matrices, (float) x0, (float) y0, (float) z).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).texture(u0, v0).next();
        bufferBuilder.end();
        RenderSystem.enableAlphaTest();
        BufferRenderer.draw(bufferBuilder);
    }
}
