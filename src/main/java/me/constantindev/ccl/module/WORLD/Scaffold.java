package me.constantindev.ccl.module.WORLD;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.Toggleable;
import me.constantindev.ccl.etc.ms.MType;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class Scaffold extends Module {
    public static Toggleable preventFalling = new Toggleable("preventfall", true);
    Toggleable lookForBlocks = new Toggleable("lookforblocks", true);
    Toggleable placeMidAir = new Toggleable("placemidair", true);

    public Scaffold() {
        super("Scaffold", "POGGERS", MType.WORLD);
        this.mconf.add(lookForBlocks);
        this.mconf.add(placeMidAir);
        this.mconf.add(preventFalling);
    }

    @Override
    public void onExecute() {
        BlockPos current = Cornos.minecraft.player.getBlockPos().down();
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
                        if (currStack.getItem() instanceof BlockItem) {
                            isIndex = i;
                            break;
                        }
                    }
                }
                if (isIndex == -1) return;
                Cornos.minecraft.player.inventory.selectedSlot = isIndex;
                BlockHitResult bhr = new BlockHitResult(new Vec3d(.5, .5, .5), Direction.DOWN, current, false);
                Cornos.minecraft.interactionManager.interactBlock(Cornos.minecraft.player, Cornos.minecraft.world, Hand.MAIN_HAND, bhr);
            }
        }
        super.onExecute();
    }
}
