package me.constantindev.ccl.etc.helper;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.MConf;
import me.constantindev.ccl.etc.reg.ModuleRegistry;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class ConfMan {
    public static boolean enabledMods = false;
    private static JsonArray modReg;

    public static void sconf() {
        if (!enabledMods) {
            Cornos.log(Level.INFO, "Not saving config due to modules not being enabled from config.");
            return;
        }
        JsonObject configContainer = new JsonObject();
        JsonArray configuration = new JsonArray();
        JsonArray enabled = new JsonArray();
        for (Module module : ModuleRegistry.getAll()) {
            if (module.isEnabled()) {
                enabled.add(module.name);
            }
            JsonObject modConfig = new JsonObject();
            modConfig.addProperty("module", module.name);
            JsonArray actualConfig = new JsonArray();
            for (MConf.ConfigKey configKey : module.mconf.config) {
                JsonObject lol = new JsonObject();
                lol.addProperty("key", configKey.key);
                lol.addProperty("value", configKey.value);
                actualConfig.add(lol);
            }
            modConfig.add("conf", actualConfig);
            configuration.add(modConfig);
        }
        configContainer.add("config", configuration);
        configContainer.add("enabledMods", enabled);
        try {
            boolean garbage;
            boolean garbage1 = true;
            File f = new File(Cornos.minecraft.runDirectory + "/ccl_moduleconfig.json");
            if (f.exists()) garbage1 = f.delete();
            garbage = f.createNewFile();
            FileWriter fw = new FileWriter(f);
            fw.write(configContainer.toString());
            fw.flush();
            fw.close();
            if (garbage && garbage1) {
                Cornos.log(Level.INFO, "Successfully saved config");
            }
        } catch (Exception ignored) {
        }
    }

    public static void lconf() {
        try {
            File f = new File(Cornos.minecraft.runDirectory + "/ccl_moduleconfig.json");
            if (!f.exists()) return;
            Scanner s = new Scanner(f);
            StringBuilder fileData = new StringBuilder();
            while (s.hasNextLine()) {
                String data = s.nextLine();
                fileData.append(data).append("\n");
            }
            String fileDataS = fileData.toString();
            Gson g = new Gson();
            JsonObject jobj = g.fromJson(fileDataS, JsonObject.class);
            if (jobj.has("config")) {
                JsonArray configBody = jobj.getAsJsonArray("config");
                for (JsonElement configInner : configBody) {
                    JsonObject configInnerJob = configInner.getAsJsonObject();
                    String t = configInnerJob.get("module").getAsString();
                    Module m = ModuleRegistry.search(t);
                    if (m != null) {
                        JsonArray confInner = configInnerJob.getAsJsonArray("conf");
                        for (JsonElement jsonElement : confInner) {
                            JsonObject configElementBody = jsonElement.getAsJsonObject();
                            String k = configElementBody.get("key").getAsString();
                            String v = configElementBody.get("value").getAsString();
                            m.mconf.getOrDefault(k, new MConf.ConfigKey(k, v)).setValue(v);
                        }
                    }
                }
            }
            if (jobj.has("enabledMods")) {
                modReg = jobj.getAsJsonArray("enabledMods");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void enableModsToBeEnabled() {
        if (enabledMods) return;
        enabledMods = true;
        JsonArray cum = modReg;
        for (JsonElement jsonElement : cum) {
            String mname = jsonElement.getAsString();
            Module m = ModuleRegistry.search(mname);
            if (m != null) {
                try {
                    m.showNotifications = false;
                    m.setEnabled(true);
                    m.showNotifications = true;
                } catch (Exception ignored) {

                }
            }
        }
    }
}
