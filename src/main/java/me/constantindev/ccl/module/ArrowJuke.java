/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: ArrowJuke
# Created by constantin at 07:04, Mär 01 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

credits to bleachhack for providing most of the code here
*/
package me.constantindev.ccl.module;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.MType;
import me.constantindev.ccl.etc.RenderableBlock;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.MultiOption;
import me.constantindev.ccl.etc.config.Num;
import me.constantindev.ccl.etc.helper.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class ArrowJuke extends Module {
    public ArrowJuke() {
        super("ArrowAvoid", "Avoids arrows, if possible", MType.MOVEMENT);
        this.mconf.add(new MultiOption("Type", "Packet", new String[]{"Client", "Packet"}));
        this.mconf.add(new Num("Speed", 1, 2, 0.1));
    }

    @Override
    public void onExecute() {
        assert Cornos.minecraft.world != null;
        for (Entity e : Cornos.minecraft.world.getEntities()) {
            if (!(e instanceof ArrowEntity) || e.age > 50) continue;
            assert Cornos.minecraft.player != null;
            Box playerHB = Cornos.minecraft.player.getBoundingBox().expand(0.555);
            List<Box> bl = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                Vec3d nextPos = e.getPos().add(e.getVelocity().multiply(i / 5d));
                Box current = new Box(
                        nextPos.subtract(e.getBoundingBox().getXLength() / 2, 0, e.getBoundingBox().getZLength() / 2),
                        nextPos.add(e.getBoundingBox().getXLength() / 2, e.getBoundingBox().getYLength(), e.getBoundingBox().getZLength() / 2));
                bl.add(current);
                boolean intc = playerHB.intersects(current);
                RenderHelper.addToQueue(new RenderableBlock(new BlockPos(nextPos), intc ? 255 : 100, intc ? 50 : 255, 50, 255, new Vec3d(e.getBoundingBox().getXLength(), e.getBoundingBox().getYLength(), e.getBoundingBox().getZLength())));

            }
            String mode = this.mconf.getByName("Type").value;
            double speed = ((Num) this.mconf.getByName("Speed")).getValue();
            for (int i = 0; i < 75; i++) {
                Vec3d nextPos = e.getPos().add(e.getVelocity().multiply(i / 5d));
                Box nextBox = new Box(
                        nextPos.subtract(e.getBoundingBox().getXLength() / 2, 0, e.getBoundingBox().getZLength() / 2),
                        nextPos.add(e.getBoundingBox().getXLength() / 2, e.getBoundingBox().getYLength(), e.getBoundingBox().getZLength() / 2));

                if (playerHB.intersects(nextBox)) {
                    for (Vec3d vel : new Vec3d[]{new Vec3d(1, 0, 0), new Vec3d(-1, 0, 0), new Vec3d(0, 0, 1), new Vec3d(0, 0, -1)}) {
                        boolean contains = false;
                        for (Box b : bl)
                            if (b.intersects(playerHB.offset(vel.x, vel.y, vel.z)))
                                contains = true;
                        if (!contains) {
                            if (mode.equalsIgnoreCase("client")) {
                                Vec3d vel2 = vel.multiply(speed);
                                Cornos.minecraft.player.setVelocity(vel2.x, vel2.y, vel2.z);
                            } else if (mode.equalsIgnoreCase("packet")) {
                                Vec3d vel2 = Cornos.minecraft.player.getPos().add(vel.multiply(speed));
                                Cornos.minecraft.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionOnly(vel2.x, vel2.y, vel2.z, false));
                                Cornos.minecraft.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionOnly(vel2.x, vel2.y - 0.01, vel2.z, true));
                            }
                            return;
                        }
                    }

                    if (mode.equalsIgnoreCase("client")) {
                        Cornos.minecraft.player.setVelocity(0, 0, -speed);
                    } else if (mode.equalsIgnoreCase("packet")) {
                        Vec3d vel2 = Cornos.minecraft.player.getPos().add(new Vec3d(0, 0, -speed));
                        Cornos.minecraft.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionOnly(vel2.x, vel2.y, vel2.z, false));
                        Cornos.minecraft.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionOnly(vel2.x, vel2.y - 0.01, vel2.z, true));
                    }
                }
            }
        }
        super.onExecute();
    }
}
