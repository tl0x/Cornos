package me.constantindev.ccl.etc.helper;

import me.constantindev.ccl.Cornos;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.render.Camera;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;

public class SilentRotations {

    public static float camPitch;
    public static float camYaw;
    public static float prevCamPitch;
    public static float prevCamYaw;

    public static boolean nextRotationSilent;

    public static void doSilentRotation(EntityAnchorArgumentType.EntityAnchor anchorPoint, Vec3d target) {
        assert Cornos.minecraft.player != null;
        float pitch = Cornos.minecraft.player.pitch;
        float yaw = Cornos.minecraft.player.yaw;
        Cornos.minecraft.player.lookAt(anchorPoint, target);
        Entity cameraEntity = Cornos.minecraft.cameraEntity;
        assert cameraEntity != null;
        camPitch = cameraEntity.pitch;
        camYaw = cameraEntity.yaw;
        prevCamPitch = cameraEntity.prevPitch;
        prevCamYaw = cameraEntity.prevYaw;
        cameraEntity.pitch = pitch;
        cameraEntity.yaw = yaw;
        nextRotationSilent = true;
    }

}
