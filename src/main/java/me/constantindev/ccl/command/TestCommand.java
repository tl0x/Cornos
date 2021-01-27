package me.constantindev.ccl.command;

import me.constantindev.ccl.etc.base.Command;
import me.constantindev.ccl.etc.mod.IndefiniteChatMessagePacket;
import net.minecraft.client.MinecraftClient;

import java.util.Objects;

public class TestCommand extends Command {
    boolean isRunning = false;

    public TestCommand() {
        super("Test", "bruh", new String[]{"test"});
    }

    @Override
    public void onExecute(String[] args) {
        //MinecraftClient.getInstance().player.setPos(Double.NaN,Double.NaN,Double.NaN);
        StringBuilder msg = new StringBuilder();
        for (int i = 0; i < 0xFFFF; i++) {
            msg.append((char) (Math.floor(Math.random() * 57) + 65));
        }
        Objects.requireNonNull(MinecraftClient.getInstance().getNetworkHandler()).sendPacket(new IndefiniteChatMessagePacket(msg.toString()));
        super.onExecute(args);
    }
}
