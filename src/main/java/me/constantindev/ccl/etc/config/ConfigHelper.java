package me.constantindev.ccl.etc.config;

import me.constantindev.ccl.CornClient;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.reg.ModuleRegistry;
import net.minecraft.client.MinecraftClient;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConfigHelper {
    private static String xor(char key, String content) {
        StringBuilder outputString = new StringBuilder();
        int len = content.length();
        for (int i = 0; i < len; i++) {
            outputString.append((char) (content.charAt(i) ^ key));
        }
        return outputString.toString();
    }


    public static void saveConfig() {
        List<String> confFinal = new ArrayList<>();
        ModuleRegistry.getAll().forEach(module -> {
            List<String> configKeys = new ArrayList<>();
            module.mconf.config.forEach(configKey -> configKeys.add(configKey.key + ":" + configKey.value));
            confFinal.add(module.name + "=" + String.join(",", configKeys));
        });
        String finalC = String.join(";", confFinal);
        try {
            File f = new File(MinecraftClient.getInstance().runDirectory + "/ccl_moduleconfig.bin");
            if (f.exists()) f.delete();
            f.createNewFile();
            FileWriter fw = new FileWriter(f);
            fw.write(xor((char) 42069, finalC));
            fw.write("\n");
            fw.flush();
            fw.close();
        } catch (Exception ignored) {
        }
    }

    public static void loadConfig() {
        try {
            File f = new File(MinecraftClient.getInstance().runDirectory + "/ccl_moduleconfig.bin");
            Scanner s = new Scanner(f);
            StringBuilder sb = new StringBuilder();
            while (s.hasNextLine()) {
                String data = s.nextLine();
                sb.append(xor((char) 42069, data));
            }
            //System.out.println(sb.toString());
            for (String ck : sb.toString().split(";")) {
                //System.out.println(ck);
                String[] datapair = ck.split("=");
                if (datapair.length != 2) continue;
                String mname = datapair[0];
                String config = datapair[1];
                Module m = ModuleRegistry.getByName(mname);
                //System.out.println(m);
                if (m == null) continue;
                CornClient.log(Level.INFO, "Loading config for module " + m.name);
                if (config.isEmpty()) continue;
                for (String v : config.split(",")) {
                    String k = v.split(":")[0];
                    String v1 = v.split(":")[1];
                    m.mconf.getOrDefault(k, new ModuleConfig.ConfigKey(k, v1)).setValue(v1);
                    CornClient.log(Level.INFO, "  " + k + " = " + v1);
                }
            }
        } catch (Exception ignored) {
        }

    }
}
