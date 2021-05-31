package me.zeroX150.cornos.etc.config;

import me.zeroX150.cornos.features.module.Module;

import java.util.ArrayList;
import java.util.List;

public class MConf {
    public final Module owner;
    public List<ConfigKey> config = new ArrayList<>();

    public MConf(Module owner) {
        this.owner = owner;
    }

    public ConfigKey add(ConfigKey pair) {
        config.add(pair);
        return pair;
    }

    public ConfigKey getByName(String name) {
        ConfigKey ref = null;
        for (ConfigKey configKey : this.config) {
            if (configKey.key.equalsIgnoreCase(name)) {
                ref = configKey;
                break;
            }
        }
        return ref;
    }

    public ConfigKey getOrDefault(String name, ConfigKey defaultVal) {
        ConfigKey r = getByName(name);
        if (r == null) {
            this.add(defaultVal);
        }
        return r == null ? defaultVal : r;
    }

    public static class ConfigKey {
        public String key;
        public String value;
        public String description;

        public ConfigKey(String k, String v, String d) {
            this.key = k;
            this.value = v;
            this.description = d;
        }

        public void setValue(String newV) {
            this.value = newV;
        }
    }
}