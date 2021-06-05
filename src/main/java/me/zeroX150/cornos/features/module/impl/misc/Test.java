/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: Test
# Created by constantin at 21:26, Mär 17 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.zeroX150.cornos.features.module.impl.misc;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.etc.helper.Renderer;
import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleType;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.packet.c2s.play.CreativeInventoryActionC2SPacket;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class Test extends Module {
    public Test() {
        super("TestModule", "j", ModuleType.HIDDEN);
    }

    @Override
    public void onRender(MatrixStack ms, float td) {
        bruhMoment(Cornos.minecraft.player.getPos());
    }

    void bruhMoment(Vec3d p) {
        Camera c = BlockEntityRenderDispatcher.INSTANCE.camera;
        Vec3d s = p.subtract(c.getPos());
        double r = Math.toRadians(-c.getYaw()+90);
        double sin = Math.sin(r);
        double cos = Math.cos(r);
        double x = s.x;
        double y = s.y;
        double z = s.z;
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glLineWidth(2);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glRotated(MathHelper.wrapDegrees(c.getPitch()), 1, 0, 0);
        GL11.glRotated(MathHelper.wrapDegrees(c.getYaw() + 180.0), 0, 1, 0);
        GL11.glColor4f(1,1,1,1);
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex3d(x+0+sin,y+0,z+cos);
        GL11.glVertex3d(x+1-sin,y+0,z-cos);
        GL11.glVertex3d(x+1-sin,y+0,z-cos);
        GL11.glVertex3d(x+1-sin,y+2,z-cos);
        GL11.glVertex3d(x+1-sin,y+2,z-cos);
        GL11.glVertex3d(x+0+sin,y+2,z+cos);
        GL11.glVertex3d(x+0+sin,y+2,z+cos);
        GL11.glVertex3d(x+0+sin,y+0,z+cos);
        GL11.glEnd();
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glPopMatrix();
    }
}
