/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: DiscordRPCThread
# Created by constantin at 08:32, Mär 02 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.zeroX150.cornos.etc;


import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.etc.helper.STL;
import me.zeroX150.cornos.gui.screen.MainScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;

import java.util.Objects;

public class DiscordRPCMan {
    boolean stopped = false;
    int currentCat = 1;
    int currentCatCounter = 0;
    Thread runner;

    public DiscordRPCMan() {
        DiscordRPC discordRPC = DiscordRPC.INSTANCE;
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.ready = (user) -> {
            if (MinecraftClient.getInstance().player != null)
                STL.notifyUser("Connected to user " + user.username + "#" + user.discriminator + " (" + user.userId + ")");
        };
        discordRPC.Discord_Initialize("819250268117925929", handlers, true, null);
        DiscordRichPresence presence = new DiscordRichPresence();
        presence.startTimestamp = System.currentTimeMillis() / 1000;
        presence.state = getStateAccordingToGame();
        presence.details = "https://cornos.cf/";
        discordRPC.Discord_UpdatePresence(presence);
        runner = new Thread(() -> {
            while (!stopped) {
                currentCatCounter++;
                if (currentCatCounter > 3) {
                    currentCatCounter = 0;
                    currentCat++;
                }
                if (currentCat > 8) currentCat = 1;
                String currentCatS = "c" + currentCat;
                discordRPC.Discord_RunCallbacks();
                presence.largeImageKey = currentCatS;
                presence.largeImageText = "Using the swag";
                presence.state = getStateAccordingToGame();
                discordRPC.Discord_UpdatePresence(presence);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {
                }
            }
            discordRPC.Discord_Shutdown();
        }, "Disgourd RPC");
        runner.start();

    }

    public static String getStateAccordingToGame() {
        try {
            if (MinecraftClient.getInstance().world != null && MinecraftClient.getInstance().getServer() != null)
                return "Playing locally with a client";
            if (MinecraftClient.getInstance().world != null) {
                String addr = Objects.requireNonNull(MinecraftClient.getInstance().getNetworkHandler()).getConnection().getAddress().toString();
                String host = addr.split("/")[0];
                if (host.isEmpty()) host = addr.split("/")[1].split(":")[0];
                String port = addr.split("/")[1].split(":")[1];
                if (port.equals("25565")) port = "";
                else port = ":" + port;
                return "Playing on " + host + "" + port;
            }
            if (MinecraftClient.getInstance().currentScreen instanceof TitleScreen || MinecraftClient.getInstance().currentScreen instanceof MainScreen)
                return "At the home screen";
            if (Cornos.minecraft.currentScreen instanceof InventoryScreen) {
                return "Looking for something in the inv";
            }
        } catch (Exception ignored) {
        }

        return "Doing something";
    }

    public void shutdown() {
        stopped = true;
    }
}
