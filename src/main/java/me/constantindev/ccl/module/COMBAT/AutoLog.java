package me.constantindev.ccl.module.COMBAT;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.MConfNum;
import me.constantindev.ccl.etc.ms.ModuleType;
import net.minecraft.text.Text;

import java.util.Objects;

public class AutoLog extends Module {
    MConfNum perHealth = new MConfNum("health%", 5, 100, 1);

    public AutoLog() {
        super("AutoLog", "automatically pussies out after a certain health %", ModuleType.COMBAT);
        this.mconf.add(perHealth);
    }

    @Override
    public void onExecute() {
        assert Cornos.minecraft.player != null;
        float h = Cornos.minecraft.player.getHealth();
        float mh = Cornos.minecraft.player.getMaxHealth();
        float hper = h / mh * 100;
        if (hper < perHealth.getValue()) {
            Objects.requireNonNull(Cornos.minecraft.getNetworkHandler()).getConnection().disconnect(Text.of("AutoLog reached " + hper + "% health"));
        }
        super.onExecute();
    }
}
