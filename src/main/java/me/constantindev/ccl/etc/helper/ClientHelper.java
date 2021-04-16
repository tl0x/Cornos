package me.constantindev.ccl.etc.helper;

import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.thealtening.auth.service.AlteningServiceType;
import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.config.ClientConfig;
import me.constantindev.ccl.mixin.SessionAccessor;
import net.minecraft.client.util.Session;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.apache.logging.log4j.Level;

import java.lang.reflect.Field;
import java.net.Proxy;
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

    public static void setField(Object t, String n1, String n2, Object value) {
        Field f;
        try {
            f = t.getClass().getField(n1);
        } catch (Exception exc) {
            try {
                f = t.getClass().getField(n2);
            } catch (Exception ignored) {
                return;
            }
        }
        if (!f.isAccessible()) f.setAccessible(true);
        try {
            f.set(t, value);
        } catch (Exception ignored) {
        }
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
}
