package me.constantindev.ccl.etc.config;

import me.constantindev.ccl.etc.base.Module;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ModuleConfig {
    public final Module owner;
    public List<ConfigKey> config = new ArrayList<>();

    public ModuleConfig(Module owner) {
        this.owner = owner;
    }

    public ConfigKey add(ConfigKey pair) {
        config.add(pair);
        return pair;
    }

    public ConfigKey getByName(String name) {
        AtomicReference<ConfigKey> ref = new AtomicReference<>(null);
        this.config.forEach(configKey -> {
            if (configKey.key.equalsIgnoreCase(name)) ref.set(configKey);
        });
        return ref.get();
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

        public ConfigKey(String k, String v) {
            this.key = k;
            this.value = v;
        }

        public void setValue(String newV) {
            this.value = newV;
        }
    }
}