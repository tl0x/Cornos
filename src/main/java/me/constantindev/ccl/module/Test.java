/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: Test
# Created by constantin at 21:26, Mär 17 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.module;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.ms.ModuleType;
import me.constantindev.ccl.mixin.GameRendererMixin;
import net.minecraft.client.gl.ShaderEffect;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class Test extends Module {
    public Test() {
        super("TestModule", "Poggê", ModuleType.HIDDEN);
    }

    @Override
    public void onRender(MatrixStack ms, float td) {
        super.onRender(ms, td);
    }

    @Override
    public void onEnable() {
        try {
            ((GameRendererMixin) Cornos.minecraft.gameRenderer).setShader(new ShaderEffect(Cornos.minecraft.getTextureManager(),Cornos.minecraft.getResourceManager(),Cornos.minecraft.getFramebuffer(),new Identifier("shaders/post/green.json")));
        } catch (Exception ig) {
            ig.printStackTrace();
        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        ((GameRendererMixin) Cornos.minecraft.gameRenderer).setShader(null);
        super.onDisable();
    }
}
