/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: Boost
# Created by constantin at 16:29, Mär 15 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.zeroX150.cornos.features.module.impl.movement;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.etc.config.MConfNum;
import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleType;
import net.minecraft.util.math.Vec3d;

public class Boost extends Module {
	MConfNum strength = new MConfNum("strength", 4.0, 10, 1);

	public Boost() {
		super("Boost", "Boosts you upwards", ModuleType.MOVEMENT);
		this.mconf.add(strength);
		this.showNotifications = false;
	}

	@Override
	public void onExecute() {
		this.setEnabled(false);
		double mtp = strength.getValue();
		assert Cornos.minecraft.player != null;
		Vec3d mtpV = Cornos.minecraft.player.getRotationVector().multiply(mtp);
		Cornos.minecraft.player.addVelocity(mtpV.x, mtpV.y, mtpV.z);
		super.onExecute();
	}
}
