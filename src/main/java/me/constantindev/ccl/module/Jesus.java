package me.constantindev.ccl.module;

import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.MultiOption;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class Jesus extends Module {
    public Jesus() {
        super("Jesus", "Yisus.");
        this.mconf.add(new MultiOption("mode", "jump", new String[]{"jump", "velocity", "vanilla", "dontfall"}));
    }

    @Override
    public void onExecute() {
        assert MinecraftClient.getInstance().player != null;
        switch (this.mconf.getByName("mode").value) {
            case "jump":
                if (MinecraftClient.getInstance().player.isWet()) {
                    MinecraftClient.getInstance().player.jump();
                }
                break;
            case "velocity":
                if (MinecraftClient.getInstance().player.isWet()) {
                    Vec3d vel = MinecraftClient.getInstance().player.getVelocity();
                    MinecraftClient.getInstance().player.setVelocity(vel.x, 0.1, vel.z);
                }
                break;
            case "vanilla":
                if (MinecraftClient.getInstance().player.isWet()) {
                    MinecraftClient.getInstance().player.addVelocity(0, 0.05, 0);
                }

                break;
            case "dontfall":
                BlockPos bpos = MinecraftClient.getInstance().player.getBlockPos();

                if (MinecraftClient.getInstance().player.world.getBlockState(bpos.down()).getFluidState().getLevel() != 0) {
                    Vec3d v = MinecraftClient.getInstance().player.getVelocity();
                    MinecraftClient.getInstance().player.setVelocity(v.x, 0, v.z);
                }
                break;
        }
        super.onExecute();
    }
}
