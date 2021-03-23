package me.constantindev.ccl;

import com.thealtening.auth.TheAlteningAuthentication;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.ClientConfig;
import me.constantindev.ccl.etc.event.EventHelper;
import me.constantindev.ccl.etc.helper.ConfigHelper;
import me.constantindev.ccl.etc.helper.KeyBindManager;
import me.constantindev.ccl.etc.reg.CommandRegistry;
import me.constantindev.ccl.etc.reg.ModuleRegistry;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Cornos implements ModInitializer {

    public static final String MOD_ID = "ccl";
    public static final String MOD_NAME = "Cornos";
    public static Logger LOGGER = LogManager.getLogger();
    public static MinecraftClient minecraft = MinecraftClient.getInstance();
    public static Thread fastUpdater;

    public static void log(Level level, String message) {
        LOGGER.log(level, "[" + MOD_NAME + "] " + message);
    }

    @Override
    public void onInitialize() {
        Runtime.getRuntime().addShutdownHook(new Thread(ConfigHelper::saveConfig));
        log(Level.INFO, "Initializing main client");

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
        ClientConfig.authentication = TheAlteningAuthentication.mojang(yggdrasilEnvironment -> {});
        fastUpdater.start();

    }

}