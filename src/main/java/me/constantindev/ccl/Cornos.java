package me.constantindev.ccl;

import com.thealtening.auth.TheAlteningAuthentication;
import me.constantindev.ccl.etc.NotificationManager;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.ClientConfig;
import me.constantindev.ccl.etc.event.EventHelper;
import me.constantindev.ccl.etc.helper.ConfigHelper;
import me.constantindev.ccl.etc.helper.KeyBindManager;
import me.constantindev.ccl.etc.reg.CommandRegistry;
import me.constantindev.ccl.etc.reg.ModuleRegistry;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;

public class Cornos implements ModInitializer {

    public static final String MOD_ID = "ccl";
    public static final String MOD_NAME = "Cornos";
    public static SoundEvent BONG_SOUND = new SoundEvent(new Identifier("ccl", "bong"));
    public static SoundEvent HITMARKER_SOUND = new SoundEvent(new Identifier("ccl", "hitmarker"));
    public static Logger LOGGER = LogManager.getLogger();
    public static MinecraftClient minecraft = MinecraftClient.getInstance();
    public static Thread fastUpdater;
    public static NotificationManager notifMan;

    public static void log(Level level, String message) {
        LOGGER.log(level, "[" + MOD_NAME + "] " + message);
    }

    public static void onMinecraftCreate() {
        if (ModuleRegistry.getByName("ClientConfig").mconf.getByName("customProcessIcon").value.equals("on")) {
            InputStream inputStream = Cornos.class.getClassLoader().getResourceAsStream("assets/ccl/icon1.png");
            Cornos.minecraft.getWindow().setIcon(inputStream, inputStream);
        }
    }

    public static void openCongratsScreen() {
        ConfirmScreen cs1 = new ConfirmScreen(t -> {
            if (t) {
                Util.getOperatingSystem().open("https://github.com/AriliusClient/Cornos/issues/new?title=Broke%20the%20client");
            } else Cornos.minecraft.openScreen(null);
        }, Text.of("Congrats!"), Text.of("You broke the client! Dare to tell me how you did it?"));
        Cornos.minecraft.openScreen(cs1);
    }

    @Override
    public void onInitialize() {
        Runtime.getRuntime().addShutdownHook(new Thread(ConfigHelper::saveConfig));
        log(Level.INFO, "Initializing main client");
        Registry.register(Registry.SOUND_EVENT, BONG_SOUND.getId(), BONG_SOUND);
        Registry.register(Registry.SOUND_EVENT, HITMARKER_SOUND.getId(), HITMARKER_SOUND);
        notifMan = new NotificationManager();

        log(Level.INFO, "Initializing configuration");
        ClientConfig.init();
        log(Level.INFO, "Registering event bus");
        EventHelper.BUS.init();
        log(Level.INFO, "Initializing command registry");
        CommandRegistry.init();
        log(Level.INFO, "Initializing module registry");
        ModuleRegistry.init();
        log(Level.INFO, "Loading the configuration file");
        ConfigHelper.loadConfig();
        log(Level.INFO, "Registering all keybinds");
        KeyBindManager.init();
        log(Level.INFO, "All features registered. Ready to load game");

        fastUpdater = new Thread(() -> {
            while (true) {
                try {
                    if (Cornos.minecraft.player != null) {
                        for (Module m : ModuleRegistry.getAll()) {
                            if (m.isOn.isOn()) m.onFastUpdate();
                        }
                    }
                    Thread.sleep(10);
                } catch (Exception ignored) {
                }
            }
        });
        ClientConfig.authentication = TheAlteningAuthentication.mojang(yggdrasilEnvironment -> {
        });
        fastUpdater.start();
    }

}