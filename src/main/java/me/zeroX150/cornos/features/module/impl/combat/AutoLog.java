package me.zeroX150.cornos.features.module.impl.combat;

import java.util.Objects;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.etc.config.MConfNum;
import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleType;
import net.minecraft.text.Text;

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
		if (hper < perHealth.getValue() && h != 0) {
			Objects.requireNonNull(Cornos.minecraft.getNetworkHandler()).getConnection()
					.disconnect(Text.of("AutoLog reached " + hper + "% health. Autolog is disabled."));
			this.setEnabled(false);
		}
		super.onExecute();
	}
}
