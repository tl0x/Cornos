package me.constantindev.ccl.module.RENDER;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.MultiOption;
import me.constantindev.ccl.etc.ms.MType;
import me.constantindev.ccl.etc.reg.ModuleRegistry;
import net.minecraft.client.options.Perspective;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Quaternion;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class Animations extends Module {
    private static final MultiOption type = new MultiOption("type", "laidBack", new String[]{"laidBack", "small", "smaller"});

    public Animations() {
        super("Animations", "makes items go brrr", MType.RENDER);
        this.mconf.add(type);
    }

    public static void render(LivingEntity entity, ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci, ItemRenderer itemRenderer) {
        assert Cornos.minecraft.player != null;
        if (entity.getUuid() == Cornos.minecraft.player.getUuid() && Cornos.minecraft.options.getPerspective() == Perspective.FIRST_PERSON && ModuleRegistry.getByName("Animations").isEnabled()) {
            switch (type.value) {
                case "laidBack":
                    matrices.multiply(new Quaternion(0.3f, 0f, 0f, 1f));
                    break;
                case "small":
                    matrices.scale(.2f, .2f, .2f);
                    matrices.translate(0, 1, 0);
                    break;
                case "smaller":
                    matrices.scale(.05f, .05f, .05f);
                    matrices.translate(0, 3, 0);
                    break;
                default:
                    return;
            }

        }
        if (!stack.isEmpty()) {
            itemRenderer.renderItem(entity, stack, renderMode, leftHanded, matrices, vertexConsumers, entity.world, light, OverlayTexture.DEFAULT_UV);
        }
        ci.cancel();
    }
}
