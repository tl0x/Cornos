package me.zeroX150.cornos.features.module;

import java.util.ArrayList;
import java.util.List;

import me.zeroX150.cornos.etc.config.MConf;
import me.zeroX150.cornos.etc.config.MConfKeyBind;
import me.zeroX150.cornos.etc.config.MConfToggleable;
import me.zeroX150.cornos.etc.config.Toggleable$1;
import me.zeroX150.cornos.etc.helper.Renderer;
import me.zeroX150.cornos.etc.render.Notification;
import me.zeroX150.cornos.etc.render.RenderableBlock;
import me.zeroX150.cornos.etc.render.RenderableLine;
import net.minecraft.client.util.math.MatrixStack;

public class Module {
	public final String name;
	public final String description;
	public final MConf mconf;
	public final ModuleType type;
	private final Toggleable$1 isOn = new Toggleable$1(false);
	public boolean showNotifications = true;
	public List<RenderableBlock> rbq = new ArrayList<>();
	public List<RenderableLine> rlq = new ArrayList<>();

	public Module(String n, String d) {
		this(n, d, ModuleType.MISC);
	}

	public Module(String n, String d, ModuleType type) {
		this.name = n;
		this.description = d;
		this.type = type;
		this.mconf = new MConf(this);
		this.mconf.add(new MConfKeyBind("keybind", -1));
		this.mconf.add(new MConfToggleable("visible", true));
	}

	public final void onExecuteIntern() {
		if (!this.isEnabled())
			return;
		onExecute();
	}

	public void onExecute() {
	}

	public void onFastUpdate() {
	}

	public void onEnable() {

	}

	public void onDisable() {

	}

	public void onHudRender(MatrixStack ms, float td) {

	}

	public void onRender(MatrixStack ms, float td) {
		for (RenderableLine rl : rlq.toArray(new RenderableLine[0])) {
			Renderer.renderLine(rl.bp1, rl.bp2, rl.c, rl.width);
		}
		for (RenderableBlock rl : rbq.toArray(new RenderableBlock[0])) {
			Renderer.renderBlockOutline(rl.bp, rl.dimensions, rl.r, rl.g, rl.b, rl.a);
		}
		rlq.clear();
		rbq.clear();
	}

	public final boolean isEnabled() {
		return isOn.isOn();
	}

	public final void setEnabled(boolean isEnabled) {
		this.isOn.setState(isEnabled);
		if (isEnabled) {
			if (this.showNotifications)
				Notification.create("Module toggle", new String[]{"§aEnabled§r " + this.name}, 1000);
			this.onEnable();
		} else {
			if (this.showNotifications)
				Notification.create("Module toggle", new String[]{"§cDisabled§r " + this.name}, 1000);
			this.onDisable();
		}
	}

	protected void setEnabledWithoutUpdate(boolean nv) {
		this.isOn.setState(nv);
	}
}
