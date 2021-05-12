package me.constantindev.ccl.module.WORLD;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.MConfToggleable;
import me.constantindev.ccl.etc.ms.ModuleType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class Scaffold extends Module {
    public static MConfToggleable preventFalling = new MConfToggleable("preventfall", true);
    MConfToggleable lookForBlocks = new MConfToggleable("lookforblocks", true);
    MConfToggleable placeMidAir = new MConfToggleable("placemidair", true);

    public Scaffold() {
        super("Scaffold", "Tired of falling but dont wanna use safewalk?", ModuleType.WORLD);
        this.mconf.add(lookForBlocks);
        this.mconf.add(placeMidAir);
        this.mconf.add(preventFalling);
    }

    @Override
    public void onExecute() {
        assert Cornos.minecraft.player != null;
        BlockPos current = Cornos.minecraft.player.getBlockPos().down();
        assert Cornos.minecraft.world != null;
        BlockState bs = Cornos.minecraft.world.getBlockState(current);
        if (bs.getMaterial().isReplaceable()) {
            boolean shouldPlace = false;
            if (placeMidAir.isEnabled()) shouldPlace = true;
            else {
                int[][] bruh = new int[][]{
                        new int[]{1, 0, 0},
                        new int[]{0, 1, 0},
                        new int[]{0, 0, 1},
                        new int[]{-1, 0, 0},
                        new int[]{0, -1, 0},
                        new int[]{0, 0, -1},
                        new int[]{1, 0, 1},
                        new int[]{-1, 0, 1},
                        new int[]{1, 0, -1},
                        new int[]{-1, 0, -1}
                };
                for (int[] c : bruh) {
                    if (!Cornos.minecraft.world.getBlockState(current.add(c[0], c[1], c[2])).isAir()) {
                        shouldPlace = true;
                        break;
                    }
                }
            }
            if (shouldPlace) {
                int prevIndex = Cornos.minecraft.player.inventory.selectedSlot;
                int isIndex = -1;
                if (!lookForBlocks.isEnabled()) isIndex = prevIndex;
                else {
                    for (int i = 0; i < 9; i++) {
                        ItemStack currStack = Cornos.minecraft.player.inventory.getStack(i);
                        if (Block.getBlockFromItem(currStack.getItem()) != Blocks.AIR) {
                            isIndex = i;
                            break;
                        }
                    }
                }
                if (isIndex == -1) return;
                Cornos.minecraft.player.inventory.selectedSlot = isIndex;
                BlockHitResult bhr = new BlockHitResult(new Vec3d(.5, .5, .5), Direction.DOWN, current, false);
                assert Cornos.minecraft.interactionManager != null;
                Cornos.minecraft.interactionManager.interactBlock(Cornos.minecraft.player, Cornos.minecraft.world, Hand.MAIN_HAND, bhr);
                Cornos.minecraft.getNetworkHandler().sendPacket(new HandSwingC2SPacket(Hand.MAIN_HAND));
            }
        }
        super.onExecute();
    }
}
