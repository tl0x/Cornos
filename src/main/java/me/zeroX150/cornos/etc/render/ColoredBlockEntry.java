/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: ColoredBlockEntry
# Created by constantin at 18:29, Mär 03 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.zeroX150.cornos.etc.render;

import java.awt.*;

import net.minecraft.util.math.Vec3d;

public class ColoredBlockEntry {
	public final Vec3d bp;
	public final Color c;

	public ColoredBlockEntry(Vec3d bp, Color assigned) {
		this.bp = bp;
		this.c = assigned;
	}
}
