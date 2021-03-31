package me.constantindev.ccl.etc.reg;

import me.constantindev.ccl.etc.TabManager;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.module.*;
import me.constantindev.ccl.module.COMBAT.Confuse;
import me.constantindev.ccl.module.COMBAT.Target;
import me.constantindev.ccl.module.COMBAT.ThunderAura;
import me.constantindev.ccl.module.COMBAT.TriggerBot;
import me.constantindev.ccl.module.EXPLOIT.*;
import me.constantindev.ccl.module.EXPLOIT.CRASH.*;
import me.constantindev.ccl.module.MOVEMENT.*;
import me.constantindev.ccl.module.RENDER.Freecam;
import me.constantindev.ccl.module.RENDER.Tracers;
import me.constantindev.ccl.module.WORLD.*;
import me.constantindev.ccl.module.ext.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ModuleRegistry {
    private static final List<Module> ml = new ArrayList<>();
    private static TabManager tabManager; // Probably not the best place for this but whatever -FreakingChicken

    public static void init() {
        ml.add(new AntiHunger()); // lemme disable this while i test the event bus
        ml.add(new Flight());
        ml.add(new FullBright());
        ml.add(new Suicide());
        ml.add(new ShiftTp());
        ml.add(new Nuker());
        ml.add(new MassBreak());
        ml.add(new MoonGravity());
        ml.add(new Hud());
        ml.add(new AutoRespawn());
        ml.add(new AutoTool());
        ml.add(new Jesus());
        ml.add(new Sprint());
        ml.add(new BuildLimit());
        ml.add(new MidAirPlace());
        ml.add(new XRAY());
        ml.add(new AntiOffhandCrash());
        ml.add(new Freecam()); // freecam broke
        ml.add(new ClickGUI());
        ml.add(new TabGUI());
        ml.add(new ClientConfig());
        ml.add(new ArrowAvoid());
        ml.add(new DiscordRPC());
        ml.add(new QuickMove());
        ml.add(new Tracers());
        ml.add(new ByteSizeViewer());
        ml.add(new ThunderAura());
        ml.add(new NoFall());
        ml.add(new AutoSign());
        ml.add(new OffhandCrash());
        ml.add(new MovementCrash());
        ml.add(new BoatCrash());
        ml.add(new BoatPhase());
        ml.add(new NameTags());
        ml.add(new ResourcePackSpoof());
        ml.add(new Boost());
        ml.add(new Target());
        ml.add(new Confuse());
        ml.add(new Test());
        ml.add(new EntityCrash());
        ml.add(new SignCrash());
        ml.add(new Step());
        ml.add(new TryUseCrash());
        ml.add(new LoginCrash());
        ml.add(new Debug());
        ml.add(new FastUse());
        ml.add(new HologramAura());
        ml.add(new Alts());
        ml.add(new NoRender());
        ml.add(new VelocityCap());
        ml.add(new NoJumpingCooldown());
        ml.add(new MemeSFX());
        ml.add(new ClientProgression());
        ml.add(new TriggerBot());

        tabManager = new TabManager();
    }

    public static List<Module> getAll() {
        return ml;
    }

    public static void reload() {
        ml.forEach(module -> {
            module.setEnabled(false);
        });
        ml.clear();
        init();
    }

    public static TabManager getTabManager() {
        return tabManager;
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
