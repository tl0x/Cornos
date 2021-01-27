package me.constantindev.ccl;

import me.constantindev.ccl.etc.config.ClientConfig;
import me.constantindev.ccl.etc.config.ConfigHelper;
import me.constantindev.ccl.etc.reg.CommandRegistry;
import me.constantindev.ccl.etc.reg.ModuleRegistry;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CornClient implements ModInitializer {

    public static final String MOD_ID = "ccl";
    public static final String MOD_NAME = "CornClient";
    public static Logger LOGGER = LogManager.getLogger();

    public static void log(Level level, String message) {
        LOGGER.log(level, "[" + MOD_NAME + "] " + message);
    }

    @Override
    public void onInitialize() {
        Runtime.getRuntime().addShutdownHook(new Thread(ConfigHelper::saveConfig));
        log(Level.INFO, "Initializing main client");
        log(Level.INFO, "Initializing configuration");
        ClientConfig.init();

        log(Level.INFO, "Initializing command registry");
        CommandRegistry.init();
        log(Level.INFO, "Initializing module registry");
        ModuleRegistry.init();
        log(Level.INFO, "All features registered. Ready to load game");

        ConfigHelper.loadConfig();
    }

}