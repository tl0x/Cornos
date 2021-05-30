package me.zeroX150.cornos.features.module.impl.world;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.etc.config.MConfMultiOption;
import me.zeroX150.cornos.etc.config.MConfNum;
import me.zeroX150.cornos.etc.helper.Renderer;
import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleType;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class AutoBuild extends Module {
	MConfMultiOption mode = new MConfMultiOption("mode", "bunker", new String[]{"bunker", "cock"});
	Vec3d[] cock = new Vec3d[]{new Vec3d(0, 2, 0), new Vec3d(0, 1, 0), new Vec3d(1, 0, 0), new Vec3d(0, 0, 0),
			new Vec3d(-1, 0, 0)};
	MConfNum delay = new MConfNum("delay", 100, 1000, 1);
	Vec3d[] bunker;
	double timer = 100.0;

	public AutoBuild() {
		super("AutoBuild", "makes structures fuckin idk", ModuleType.WORLD);
		this.mconf.add(mode);
		this.mconf.add(delay);
		ArrayUtils.reverse(cock);
		List<Vec3d> bunkerShit = new ArrayList<>();
		for (int y = 0; y < 3; y++) {
			for (int x = -1; x < 3; x++) {
				for (int z = -1; z < 3; z++) {
					if (y != 2 && (x == 0 && z == 0 || x == 1 && z == 0 || x == 0 && z == 1 || x == 1 && z == 1))
						continue; // carve out middle
					bunkerShit.add(new Vec3d(x - .5, y, z - .5));
				}
			}
		}
		bunker = bunkerShit.toArray(new Vec3d[]{});

	}

	@Override
	public void onEnable() {
		timer = delay.getValue();
		super.onEnable();
	}

	@Override
	public void onRender(MatrixStack ms, float td) {
		if (Cornos.minecraft.crosshairTarget == null)
			return;
		Vec3d bp1 = Cornos.minecraft.crosshairTarget.getPos();
		Vec3d bp = new Vec3d(Math.floor(bp1.x), Math.floor(bp1.y), Math.floor(bp1.z));
		Vec3d[] points;
		switch (mode.value) {
			case "cock" :
				points = cock;
				break;
			case "bunker" :
				points = bunker;
				break;
			default :
				return;
		}
		double perDone = timer / delay.getValue();
		double colVal = perDone * 255;
		double red = Math.abs(colVal - 255);
		for (Vec3d bruh : points) {
			Renderer.renderBlockOutline(bp.add(bruh), new Vec3d(1, 1, 1), (int) Math.floor(red), 50,
					(int) Math.floor(colVal), 255);
		}
		super.onRender(ms, td);
	}

	@Override
	public void onExecute() {
		timer--;
		if (timer > 0)
			return;
		timer = delay.getValue();
		this.setEnabled(false);
		if (Cornos.minecraft.crosshairTarget == null)
			return;
		Vec3d bp = Cornos.minecraft.crosshairTarget.getPos();
		Vec3d[] points;
		switch (mode.value) {
			case "cock" :
				points = cock;
				break;
			case "bunker" :
				points = bunker;
				break;
			default :
				return;
		}
		for (Vec3d point : points) {
			BlockPos current = new BlockPos(bp.x + point.x, bp.y + point.y, bp.z + point.z);
			assert Cornos.minecraft.world != null;
			BlockState bs = Cornos.minecraft.world.getBlockState(current);
			if (!bs.getMaterial().isReplaceable())
				continue;
			BlockHitResult bhr = new BlockHitResult(Vec3d.ZERO, Direction.DOWN, current, false);
			assert Cornos.minecraft.interactionManager != null;
			Cornos.minecraft.interactionManager.interactBlock(Cornos.minecraft.player, Cornos.minecraft.world,
					Hand.MAIN_HAND, bhr);
		}
		super.onExecute();
	}
}
