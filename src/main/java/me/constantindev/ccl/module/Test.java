/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: Test
# Created by constantin at 21:26, Mär 17 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.module;

import me.constantindev.ccl.etc.base.Module;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.minecraft.client.util.math.Vector3f;

import java.util.Objects;

public class Test extends Module {
    public Test() {
        super("TestModule", "Poggê");
    }

    @Override
    public void onExecute() {
        QuadEmitter qe = Objects.requireNonNull(RendererAccess.INSTANCE.getRenderer()).meshBuilder().getEmitter();
        qe.pos(0, new Vector3f(0, 0, 0))
                .pos(0, new Vector3f(1, 1, 1)).emit();

        super.onExecute();
    }
}
