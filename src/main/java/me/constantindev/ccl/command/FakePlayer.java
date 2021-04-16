package me.constantindev.ccl.command;

import com.mojang.authlib.GameProfile;
import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Command;
import me.constantindev.ccl.etc.event.EventHelper;
import me.constantindev.ccl.etc.event.EventType;
import me.constantindev.ccl.etc.helper.ClientHelper;
import me.constantindev.ccl.etc.helper.RandomHelper;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FakePlayer extends Command {
    final List<OtherClientPlayerEntity> fakePlayers = new ArrayList<>();
    final List<Vec3d> trackedPositions = new ArrayList<>();

    public FakePlayer() {
        super("FakePlayer", "Creates a totally real functional player", new String[]{"cplayer"});
        EventHelper.BUS.registerEvent(EventType.ONTICK, event -> {
            if (!trackedPositions.contains(Cornos.minecraft.player.getPos()))
                trackedPositions.add(Cornos.minecraft.player.getPos());
            if (trackedPositions.size() > 30) {
                trackedPositions.subList(0, 1).clear();
            }
            Vec3d gotoPos = trackedPositions.get(0);
            List<OtherClientPlayerEntity> newPl = new ArrayList<>();
            for (OtherClientPlayerEntity p : fakePlayers) {
                if (!p.removed) newPl.add(p);
            }
            for (OtherClientPlayerEntity p : newPl) {
                p.lookAt(EntityAnchorArgumentType.EntityAnchor.FEET, Cornos.minecraft.player.getPos());
                p.updatePosition(gotoPos.x, gotoPos.y, gotoPos.z);
                //p.teleport(gotoPos.x,gotoPos.y,gotoPos.z);
                p.travel(new Vec3d(1, 0, 0).rotateY(MathHelper.wrapDegrees(p.yaw)));
            }
            fakePlayers.clear();
            fakePlayers.addAll(newPl);
        });
    }

    @Override
    public void onExecute(String[] args) {
        if (args.length < 2) {
            ClientHelper.sendChat("ima need an username and uuid");
            return;
        }
        String u = args[0];
        UUID uuid;
        try {
            uuid = UUID.fromString(args[1]);
        } catch (Exception ignored) {
            ClientHelper.sendChat("not sure if thats a valid uuid bruh");
            return;
        }
        assert Cornos.minecraft.world != null;
        OtherClientPlayerEntity o = new OtherClientPlayerEntity(Cornos.minecraft.world, new GameProfile(uuid, u));
        o.copyPositionAndRotation(Cornos.minecraft.player);
        Cornos.minecraft.world.addPlayer((int) RandomHelper.rndD(65535), o);
        fakePlayers.add(o);
        super.onExecute(args);
    }
}
