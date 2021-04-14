package me.constantindev.ccl.module.WORLD;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.Notification;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.ClientConfig;
import me.constantindev.ccl.etc.config.MultiOption;
import me.constantindev.ccl.etc.config.Num;
import me.constantindev.ccl.etc.config.Toggleable;
import me.constantindev.ccl.etc.helper.ClientHelper;
import me.constantindev.ccl.etc.helper.RenderHelper;
import me.constantindev.ccl.etc.ms.MType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.Objects;

public class ChunkClearer extends Module {
    BlockPos start = null;
    BlockPos end = null;
    MultiOption mode = new MultiOption("mode", "client", new String[]{"client", "packet", "legit"});
    Toggleable ignoreXray = new Toggleable("ignoreXray", true);
    Toggleable clientTeleport = new Toggleable("clientTP", false);
    Num delay = new Num("delay", 0, 20, 0);
    int delayWaited = 0;
    MultiOption fast = new MultiOption("overdrive", "none", new String[]{"none", "light", "strong"});

    public ChunkClearer() {
        super("ChunkClearer", "uh oh", MType.WORLD);
        this.mconf.add(mode);
        this.mconf.add(ignoreXray);
        this.mconf.add(clientTeleport);
        this.mconf.add(delay);
        this.mconf.add(fast);
    }

    @Override
    public void onEnable() {
        assert Cornos.minecraft.player != null;
        ClientHelper.sendChat("Your client might lag for a while, let it calm down. You are clearing an entire chunk");
        int cx = Cornos.minecraft.player.chunkX;
        int cz = Cornos.minecraft.player.chunkZ;
        int startX = 16 * cx;
        int startZ = 16 * cz;
        int endX = startX + 15;
        int endZ = startZ + 15;
        start = new BlockPos(endX, 255, endZ);
        end = new BlockPos(startX, 0, startZ);
        super.onEnable();
    }

    @Override
    public void onRender(MatrixStack ms, float td) {
        if (start == null || end == null) return;
        RenderHelper.renderBlockOutline(new Vec3d(start.getX() - 15, 0, start.getZ() - 15), new Vec3d(16, 255, 16), 255, 0, 255, 255);
        super.onRender(ms, td);
    }

    @Override
    public void onExecute() {
        delayWaited++;
        if (delayWaited > delay.getValue()) {
            delayWaited = 0;
        } else return;
        Vec3d block = null;
        boolean showParticles = mode.value.equals("client");
        boolean interacted = false;
        if (start == null || end == null) return;
        for (int y = 0; y < 256; y++) {

            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    BlockPos bp1 = start.add(-x, -y, -z);
                    assert Cornos.minecraft.world != null;
                    BlockState bs = Cornos.minecraft.world.getBlockState(bp1);
                    boolean shouldSkip = bs.isOf(Blocks.AIR) || bs.isOf(Blocks.CAVE_AIR) || bs.isOf(Blocks.WATER) || bs.isOf(Blocks.LAVA);
                    boolean shift = false;
                    if (ignoreXray.isEnabled() && !shouldSkip) {
                        for (Block xrayBlock : ClientConfig.xrayBlocks) {
                            if (bs.getBlock().is(xrayBlock)) {
                                shouldSkip = true;
                                break;
                            }
                        }
                    }
                    if (!shouldSkip) {
                        BlockPos tpTo = bp1.up(2);
                        BlockPos check = tpTo.up();
                        BlockState b1 = Cornos.minecraft.world.getBlockState(tpTo);
                        BlockState b2 = Cornos.minecraft.world.getBlockState(check);
                        if (!b1.getBlock().is(Blocks.AIR) && !b1.getBlock().is(Blocks.CAVE_AIR)) shift = true;
                        if (!b2.getBlock().is(Blocks.AIR) && !b2.getBlock().is(Blocks.CAVE_AIR)) shift = true;
                    }
                    if (shouldSkip) continue;
                    BlockPos tpGoal = null;
                    if (shift) {
                        for (int[] i : new int[][]{
                                new int[]{1, 0, 0},
                                new int[]{1, 1, 0},
                                new int[]{0, 0, 1},
                                new int[]{0, 1, 1},
                                new int[]{-1, 0, 0},
                                new int[]{-1, 1, 0},
                                new int[]{0, 0, -1},
                                new int[]{0, 1, -1},
                                new int[]{1, 0, 1},
                                new int[]{-1, 0, -1}
                        }) {
                            BlockPos to = bp1.add(i[0], i[1], i[2]);
                            BlockState b1 = Cornos.minecraft.world.getBlockState(to);
                            BlockState b2 = Cornos.minecraft.world.getBlockState(to.up());
                            boolean downGood = b1.getBlock().is(Blocks.AIR) || b1.getBlock().is(Blocks.CAVE_AIR) || b1.getBlock().is(Blocks.WATER) || b1.getBlock().is(Blocks.LAVA);
                            boolean upGood = b2.getBlock().is(Blocks.AIR) || b2.getBlock().is(Blocks.CAVE_AIR) || b2.getBlock().is(Blocks.WATER) || b2.getBlock().is(Blocks.LAVA);
                            if (downGood && upGood) {
                                tpGoal = to;
                                break;
                            }
                        }
                    } else tpGoal = bp1.up(2);
                    if (tpGoal == null) continue;
                    if (!mode.value.equals("legit")) {
                        PlayerMoveC2SPacket.PositionOnly p = new PlayerMoveC2SPacket.PositionOnly(tpGoal.getX() + .5, tpGoal.getY(), tpGoal.getZ() + .5, true);
                        Objects.requireNonNull(Cornos.minecraft.getNetworkHandler()).sendPacket(p);
                        if (clientTeleport.isEnabled())
                            Cornos.minecraft.player.updatePosition(tpGoal.getX() + .5, tpGoal.getY(), tpGoal.getZ() + .5);
                        if (showParticles) {
                            assert Cornos.minecraft.interactionManager != null;
                            Cornos.minecraft.interactionManager.attackBlock(bp1, Direction.DOWN);
                        } else {
                            PlayerActionC2SPacket p1 = new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, bp1, Direction.DOWN);
                            PlayerActionC2SPacket p2 = new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK, bp1, Direction.DOWN);
                            Cornos.minecraft.getNetworkHandler().sendPacket(p1);
                            Cornos.minecraft.getNetworkHandler().sendPacket(p2);
                        }
                    } else {
                        block = new Vec3d(bp1.getX(), bp1.getY(), bp1.getZ());
                        interacted = true;
                        break;
                    }
                    interacted = true;
                    if (fast.value.equals("none")) break;
                }
                if (interacted && !fast.value.equals("strong")) break;
            }
            if (interacted) break;
        }
        if (block != null) {
            assert Cornos.minecraft.player != null;
            Cornos.minecraft.player.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, block.add(.5, .5, .5));
            int upDown = (block.y + 2) < Cornos.minecraft.player.getPos().y ? -4 : 4;
            Cornos.minecraft.player.travel(new Vec3d(0, upDown, 0));
            Cornos.minecraft.options.keyForward.setPressed(true);
            Cornos.minecraft.player.abilities.flying = true;
            if (Cornos.minecraft.crosshairTarget instanceof BlockHitResult) {
                BlockHitResult bhr = (BlockHitResult) Cornos.minecraft.crosshairTarget;
                if (bhr.getBlockPos().getX() == block.x && bhr.getBlockPos().getY() == block.y && bhr.getBlockPos().getZ() == block.z) {
                    assert Cornos.minecraft.interactionManager != null;
                    Cornos.minecraft.interactionManager.attackBlock(bhr.getBlockPos(), Direction.DOWN);
                }
            }
        }
        if (!interacted) {
            Notification.create("ChunkClearer", new String[]{"done"}, 5000);
            this.setEnabled(false);
        }
        super.onExecute();
    }
}
