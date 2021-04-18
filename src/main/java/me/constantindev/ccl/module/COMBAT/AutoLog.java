package me.constantindev.ccl.module.COMBAT;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.Num;
import me.constantindev.ccl.etc.ms.MType;
import net.minecraft.text.Text;

public class AutoLog extends Module {
    Num perHealth = new Num("health%",5,100,1);
    public AutoLog() {
        super("AutoLog", "automatically pussies out after a certain health %", MType.COMBAT);
        this.mconf.add(perHealth);
    }

    @Override
    public void onExecute() {
        float h = Cornos.minecraft.player.getHealth();
        float mh = Cornos.minecraft.player.getMaxHealth();
        float hper = h/mh*100;
        if (hper < perHealth.getValue()) {
            Cornos.minecraft.getNetworkHandler().getConnection().disconnect(Text.of("AutoLog reached "+hper+"% health"));
        }
        super.onExecute();
    }
}
