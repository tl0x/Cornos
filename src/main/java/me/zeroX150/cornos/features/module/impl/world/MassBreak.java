package me.zeroX150.cornos.features.module.impl.world;

import java.util.Objects;

import org.apache.logging.log4j.Level;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.etc.config.MConfNum;
import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleType;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class MassBreak extends Module {
	public MassBreak() {
		super("MassBreak", "block breaking but more of it", ModuleType.WORLD);
		this.mconf.add(new MConfNum("radius", 3.0, 10, 0));
	}

	@Override
	public void onExecute() {
		int rad = (int) ((MConfNum) this.mconf.getByName("radius")).getValue();
		BlockPos latest;
		if (Cornos.minecraft.crosshairTarget == null)
			return;
		if (!Cornos.minecraft.crosshairTarget.getType().equals(HitResult.Type.BLOCK))
			return;
		latest = ((BlockHitResult) Cornos.minecraft.crosshairTarget).getBlockPos();
		if (Cornos.minecraft.options.keyAttack.isPressed()) {
			Cornos.log(Level.INFO, "bruh");
			for (int x = 0; x < rad; x++) {
				for (int y = 0; y < rad; y++) {
					for (int z = 0; z < rad; z++) {
						BlockPos c = latest.add(x - (rad / 2), y - (rad / 2), z - (rad / 2));
						if (c.equals(latest))
							continue;
						Objects.requireNonNull(Cornos.minecraft.getNetworkHandler())
								.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK,
										c, Direction.UP));
						Cornos.minecraft.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(
								PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, c, Direction.UP));
					}
				}
			}
			Objects.requireNonNull(Cornos.minecraft.getNetworkHandler()).sendPacket(
					new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, latest, Direction.UP));
			Cornos.minecraft.getNetworkHandler().sendPacket(
					new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, latest, Direction.UP));
		}
		super.onExecute();
	}
}
