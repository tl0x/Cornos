package me.constantindev.ccl.etc.helper;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.thealtening.auth.service.AlteningServiceType;
import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.config.ClientConfig;
import me.constantindev.ccl.mixin.SessionAccessor;
import net.minecraft.client.util.Session;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.Proxy;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.UUID;

public class ClientHelper {
    public static void sendChat(String msg) {
        if (Cornos.minecraft.player == null) return;
        Cornos.minecraft.player.sendMessage(Text.of(Formatting.DARK_AQUA + "[ " + Formatting.AQUA + Cornos.MOD_ID.toUpperCase() + Formatting.DARK_AQUA + " ] " + Formatting.RESET + msg), false);
    }

    public static boolean isIntValid(String intToParse) {
        boolean isValid;
        try {
            Integer.parseInt(intToParse);
            isValid = true;
        } catch (Exception exc) {
            isValid = false;
        }
        return isValid;
    }

    public static boolean isLongValid(String longToParse) {
        boolean isValid;
        try {
            Long.parseLong(longToParse);
            isValid = true;
        } catch (Exception exc) {
            isValid = false;
        }
        return isValid;
    }

    public static boolean login(String username, String password) {
        if (password.isEmpty()) {
            Session crackedSession = new Session(username, UUID.randomUUID().toString(), "CornosOnTOP", "mojang");
            ((SessionAccessor) Cornos.minecraft).setSession(crackedSession);
            return true;
        }
        YggdrasilUserAuthentication auth =
                (YggdrasilUserAuthentication) new YggdrasilAuthenticationService(
                        Proxy.NO_PROXY, "").createUserAuthentication(Agent.MINECRAFT);
        if (username.contains("@alt") && username.contains("-")) {
            ClientConfig.authentication.updateService(AlteningServiceType.THEALTENING);
        } else {
            ClientConfig.authentication.updateService(AlteningServiceType.MOJANG);
        }
        auth.setPassword(password);
        auth.setUsername(username);
        try {
            auth.logIn();
            Session ns = new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
            ((SessionAccessor) Cornos.minecraft).setSession(ns);
            return true;
        } catch (Exception ec) {
            Cornos.log(Level.ERROR, "Failed to log in: ");
            ec.printStackTrace();
            return false;
        }
    }

    public static void sleep(int duration) {
        try {
            Thread.sleep(duration);
        } catch (Exception ignored) {
        }
    }

    @SuppressWarnings("unused")
    public static void checkForUpdates() {
        new Thread(() -> {
            try {
                boolean trash;
                File f = new File(ClientHelper.class.getProtectionDomain().getCodeSource().getLocation().toURI());
                if (f.isDirectory()) {
                    ClientHelper.sendChat("Can't check for updates.");
                    return;
                }
                File parent = new File(f.getParentFile().getParent() + "/tmp");
                if (!parent.exists()) trash = parent.mkdir();
                parent = new File(parent.getPath() + "/latest.jar");
                if (parent.exists()) {
                    trash = parent.delete();
                }
                downloadUsingNIO("https://github.com/AriliusClient/Cornos/raw/master/builds/latest.jar", parent.getAbsolutePath());
                HashCode hc = Files.asByteSource(f).hash(Hashing.crc32());
                HashCode hc1 = Files.asByteSource(parent).hash(Hashing.crc32());
                if (!hc.equals(hc1)) {
                    try {
                        Files.move(parent,f);
                    } catch (Exception exc) {
                        sendChat("Your cornos installation is out of sync with the latest build!");
                        sendChat("Failed to automatically update. Head over to https://github.com/AriliusClient/Cornos/raw/master/builds/latest.jar to get the latest version");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                sendChat("Failed to check for updates!");
            }
        }).start();
    }

    private static void downloadUsingNIO(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }

    public static void dropItem(int index) {
        short actionID = Cornos.minecraft.player.currentScreenHandler.getNextActionId(Cornos.minecraft.player.inventory);
        ItemStack is = Cornos.minecraft.player.inventory.getStack(index);
        ClickSlotC2SPacket p = new ClickSlotC2SPacket(0, index, 1, SlotActionType.THROW, is, actionID);
        Cornos.minecraft.getNetworkHandler().sendPacket(p);
    }
}
