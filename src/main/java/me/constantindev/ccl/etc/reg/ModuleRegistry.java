package me.constantindev.ccl.etc.reg;

import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.module.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ModuleRegistry {
    private static final List<Module> ml = new ArrayList<>();

    public static void init() {
        ml.add(new TestModule());
        ml.add(new FlyModule());
        ml.add(new NoFallModule());
        ml.add(new Suicide());
        ml.add(new ShiftTp());
        ml.add(new NukerModule());
        ml.add(new MassBreak());
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
