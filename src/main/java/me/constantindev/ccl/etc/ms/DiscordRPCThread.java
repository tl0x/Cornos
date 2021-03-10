/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: DiscordRPCThread
# Created by constantin at 08:32, Mär 02 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.etc.ms;


import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import me.constantindev.ccl.etc.helper.ClientHelper;
import net.minecraft.client.MinecraftClient;

import java.util.Objects;

public class DiscordRPCThread {
    boolean stopped = false;
    int currentCat = 1;
    int currentCatCounter = 0;
    Thread runner;

    public DiscordRPCThread() {
        DiscordRPC discordRPC = DiscordRPC.INSTANCE;
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.ready = (user) -> {
            if (MinecraftClient.getInstance().player != null)
                ClientHelper.sendChat("Ready @ U" + user.userId + " (" + user.username + "#" + user.discriminator + ")");
        };
        discordRPC.Discord_Initialize("816211917713571841", handlers, true, null);
        DiscordRichPresence presence = new DiscordRichPresence();
        presence.startTimestamp = System.currentTimeMillis() / 1000;
        presence.state = getStateAccordingToGame();
        presence.details = "Using Cornos";
        //presence.writeField("buttons", "[{\"label\":\"Get Cornos\", \"url\": \"https://github.com/AriliusClient/Cornos\"}]");
        presence.buttons = "[{\"label\":\"Get Cornos\", \"url\": \"https://github.com/AriliusClient/Cornos\"}]";
        discordRPC.Discord_UpdatePresence(presence);
        runner = new Thread(() -> {
            while (!stopped) {
                currentCatCounter++;
                if (currentCatCounter > 3) {
                    currentCatCounter = 0;
                    currentCat++;
                }
                if (currentCat > 10) currentCat = 1;
                String currentCatS = "c" + currentCat;
                discordRPC.Discord_RunCallbacks();
                presence.largeImageKey = currentCatS;
                presence.largeImageText = "cat #" + currentCat;

                discordRPC.Discord_UpdatePresence(presence);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {
                }
            }
            discordRPC.Discord_Shutdown();
        }, "RPC-Callback-Handler");
        runner.start();


    }

    public static String getStateAccordingToGame() {
        if (MinecraftClient.getInstance().world != null && MinecraftClient.getInstance().world.isClient)
            return "Playing on a local world with a client..?";
        if (MinecraftClient.getInstance().world != null)
            return "Playing on " + Objects.requireNonNull(MinecraftClient.getInstance().getNetworkHandler()).getConnection().getAddress();

        return "Doing something";
    }

    public void shutdown() {
        stopped = true;
    }
}
