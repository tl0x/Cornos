package me.constantindev.ccl.etc.reg;

import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.module.*;
import me.constantindev.ccl.module.ext.BlockHighlighter;
import me.constantindev.ccl.module.ext.Hud;
import me.constantindev.ccl.module.ext.NoFireOverlay;
import me.constantindev.ccl.module.ext.NoPumpkin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ModuleRegistry {
    private static final List<Module> ml = new ArrayList<>();

    public static void init() {
        ml.add(new Test());
        ml.add(new Flight());
        ml.add(new NoFall());
        ml.add(new Suicide());
        ml.add(new ShiftTp());
        ml.add(new Nuker());
        ml.add(new MassBreak());
        ml.add(new MoonGravity());
        ml.add(new Hud());
        ml.add(new AutoRespawn());
        ml.add(new NoPumpkin());
        ml.add(new BlockHighlighter());
        ml.add(new NoFireOverlay());
    }

    public static List<Module> getAll() {
        return ml;
    }

    public static Module getByName(String name) {
        AtomicReference<Module> mr = new AtomicReference<>(null);
        ModuleRegistry.getAll().forEach(module -> {
            if (module.name.equalsIgnoreCase(name)) {
                mr.set(module);
            }
        });
        return mr.get();
    }
}
