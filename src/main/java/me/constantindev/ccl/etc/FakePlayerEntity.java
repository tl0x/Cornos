package me.constantindev.ccl.etc;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

// I have no idea why this shows an error but you can just ignore it. It works fine
public class FakePlayerEntity extends OtherClientPlayerEntity {
    private PlayerEntity player;
    private ClientWorld world;
    private boolean shouldShowName;

    public FakePlayerEntity(World world, GameProfile gameProfile, boolean shouldShowName) {
        super((ClientWorld) world, gameProfile);
        this.shouldShowName = shouldShowName;

    }

    public void setProfile(PlayerEntity player) {
        this.player = player;
        this.world = MinecraftClient.getInstance().world;
        copyPositionAndRotation(player);

        copyInventory();
        copyPlayerModel(player, this);
        copyRotation();
        copyAnimation();
        resetCapeMovement();
    }

    public boolean shouldShowName() {
        return this.shouldShowName;
    }

    public void setShouldShowName(boolean val) {
        this.shouldShowName = val;
    }

    private void copyInventory() {
        inventory.clone(player.inventory);
    }

    public void copyPlayerModel(Entity from, Entity to) {
        DataTracker fromTracker = from.getDataTracker();
        DataTracker toTracker = to.getDataTracker();
        Byte playerModel = fromTracker.get(PlayerEntity.PLAYER_MODEL_PARTS);
        toTracker.set(PlayerEntity.PLAYER_MODEL_PARTS, playerModel);
    }

    private void copyRotation() {
        headYaw = player.headYaw;
        bodyYaw = player.bodyYaw;
    }

    private void copyAnimation() {
        setMainArm(player.getMainArm());
        setMovementSpeed(player.getMovementSpeed());
        setSwimming(player.isSwimming());
        setSneaking(player.isSneaking());
        setSprinting(player.isSprinting());
        preferredHand = player.preferredHand;
        strideDistance = player.strideDistance;
        lastLimbDistance = player.lastLimbDistance;
        limbDistance = player.limbDistance;
        limbAngle = player.limbAngle;
        hurtTime = player.hurtTime;
        setPose(player.getPose());
        handSwinging = player.handSwinging;
        handSwingProgress = player.handSwingProgress;
        handSwingTicks = player.handSwingTicks;
        isSubmergedInWater = player.isSubmergedInWater();
    }

    public void resetCapeMovement() {
        capeX = player.capeX;
        capeY = player.capeY;
        capeZ = player.capeZ;
    }

    public void spawn() {
        world.addEntity(getEntityId(), this);
    }

    public void despawn() {
        removed = true;
    }

    public void resetPlayerPosition() {
        player.refreshPositionAndAngles(getX(), getY(), getZ(), yaw, pitch);
    }
}
