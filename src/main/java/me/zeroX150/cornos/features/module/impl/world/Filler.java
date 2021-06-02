package me.zeroX150.cornos.features.module.impl.world;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.etc.config.MConfMultiOption;
import me.zeroX150.cornos.etc.config.MConfNum;
import me.zeroX150.cornos.etc.config.MConfToggleable;
import me.zeroX150.cornos.etc.helper.Renderer;
import me.zeroX150.cornos.etc.helper.STL;
import me.zeroX150.cornos.etc.render.Notification;
import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleType;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class Filler extends Module {
    public static BlockPos start;
    public static BlockPos end;

    MConfToggleable autoPlace = new MConfToggleable("autoPlace", true, "Automatically places blocks when in range");
    MConfToggleable aimBlock = new MConfToggleable("aimBlock", true, "Aims at the next block to place");
    MConfMultiOption mode = new MConfMultiOption("mode", "y-", new String[]{"y-", "near", "order"}, "The mode to place blocks with");
    MConfNum bps = new MConfNum("ipt", 2, 20, 0, "Interactions per tick");
    BlockPos toPlaceNext = null;

    public Filler() {
        super("Filler", "Fills a selected area", ModuleType.WORLD);
        mconf.add(autoPlace);
        mconf.add(aimBlock);
        mconf.add(mode);
        mconf.add(bps);
    }

    @Override
    public void onEnable() {
        if (start == null || end == null) {
            Notification.create("Filler", new String[]{"Please set start and end", "positions first.", "Use the .filler command"}, 6000);
            setEnabled(false);
        }
    }

    @Override
    public void onRender(MatrixStack ms, float td) {
        if (start != null && end != null) {
            Vec3d s = new Vec3d(start.getX(), start.getY(), start.getZ());
            Vec3d e = new Vec3d(end.getX(), end.getY(), end.getZ());
            // start pos
            Renderer.renderBlockOutline(s, new Vec3d(1, 1, 1), 50, 255, 50, 255);
            // end pos
            Renderer.renderBlockOutline(e, new Vec3d(1, 1, 1), 50, 50, 255, 255);
            // entire outline
            Vec3d start1 = s;
            Vec3d end1 = e.subtract(s);
            // what
            if (start1.getX() > e.getX()) {
                start1 = start1.add(1, 0, 0);
                end1 = end1.add(-1, 0, 0);
            } else end1 = end1.add(1, 0, 0);
            if (start1.getZ() > e.getZ()) {
                start1 = start1.add(0, 0, 1);
                end1 = end1.add(0, 0, -1);
            } else end1 = end1.add(0, 0, 1);
            if (start1.getY() > e.getY()) {
                start1 = start1.add(0, 1, 0);
                end1 = end1.add(0, -1, 0);
            } else end1 = end1.add(0, 1, 0);

            Renderer.renderBlockOutline(start1, end1, 255, 50, 50, 255);

            if (toPlaceNext != null) {
                Renderer.renderBlockOutline(new Vec3d(toPlaceNext.getX(), toPlaceNext.getY(), toPlaceNext.getZ()), new Vec3d(1, 1, 1), 255, 255, 255, 255);
            }
        }
        super.onRender(ms, td);
    }

    @Override
    public void onExecute() {
        if (start == null || end == null) return;
        boolean changed = false;
        for (int i = 0; i < bps.getValue(); i++) {
            boolean a = false;
            if (mode.value.equalsIgnoreCase("order")) {
                for (double y = Math.min(start.getY(), end.getY()); y < Math.max(start.getY(), end.getY()) + 1; y++) {
                    for (double x = Math.min(start.getX(), end.getX()); x < Math.max(start.getX(), end.getX()) + 1; x++) {
                        for (double z = Math.min(start.getZ(), end.getZ()); z < Math.max(start.getZ(), end.getZ()) + 1; z++) {
                            if (a) break;
                            BlockPos current = new BlockPos(x, y, z);
                            BlockState s = Cornos.minecraft.world.getBlockState(current);
                            if (s.getMaterial().isReplaceable()) {
                                toPlaceNext = current;
                                changed = true;
                                a = true;
                                if (autoPlace.isEnabled()) {
                                    if (new Vec3d(current.getX(), current.getY(), current.getZ()).distanceTo(Cornos.minecraft.player.getPos()) < 6) {
                                        STL.placeBlock(toPlaceNext);
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
            } else {
                changed = true;
                int xStart = Math.min(start.getX(), end.getX());
                int xEnd = Math.max(start.getX(), end.getX()) + 1;
                int yStart = Math.min(start.getY(), end.getY());
                int yEnd = Math.max(start.getY(), end.getY()) + 1;
                int zStart = Math.min(start.getZ(), end.getZ());
                int zEnd = Math.max(start.getZ(), end.getZ()) + 1;
                for (int x = -5; x < 6; x++) {
                    for (int y = -5; y < (mode.value.equalsIgnoreCase("y-") ? 0 : 6); y++) {
                        for (int z = -5; z < 6; z++) {
                            if (a) break;
                            if (x == 0 && (y == 0 || y == 1) && z == 0) continue;
                            Vec3d offset = new Vec3d(x, y, z);
                            if (offset.distanceTo(Vec3d.ZERO) > 5) continue;
                            Vec3d np = Cornos.minecraft.player.getPos().add(offset);
                            BlockState bs = Cornos.minecraft.world.getBlockState(new BlockPos(np.x, np.y, np.z));
                            // jesus fuck
                            if (np.getX() > xStart && (np.getX()) < (xEnd)
                                    && (np.getY()) > (yStart) && (np.getY()) < (yEnd)
                                    && (np.getZ()) > (zStart) && (np.getZ()) < (zEnd)
                                    && bs.getMaterial().isReplaceable()) {
                                toPlaceNext = new BlockPos(np.x, np.y, np.z);
                                a = true;
                                if (autoPlace.isEnabled()) {
                                    STL.placeBlock(toPlaceNext);
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (!changed) {
            Notification.create("Filler", new String[]{"Job done"}, 3000);
            setEnabled(false);
            toPlaceNext = null;
            return;
        }

        if (toPlaceNext != null) {
            Vec3d d = new Vec3d(toPlaceNext.getX(), toPlaceNext.getY(), toPlaceNext.getZ());
            if (aimBlock.isEnabled()) {
                Cornos.minecraft.player.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, d.add(.5, .5, .5));
            }

        }
    }
}
