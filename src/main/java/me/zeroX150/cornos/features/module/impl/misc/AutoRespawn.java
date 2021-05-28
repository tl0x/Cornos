package me.zeroX150.cornos.features.module.impl.misc;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleType;
import net.minecraft.network.packet.c2s.play.ClientStatusC2SPacket;

public class AutoRespawn extends Module {
    public AutoRespawn() {
        super("AutoRespawn", "Automatically respawns you when you die", ModuleType.MISC);
    }

    @Override
    public void onExecute() {
        assert Cornos.minecraft.player != null;
        if (Cornos.minecraft.player.isDead()) {
            if (Cornos.minecraft.getNetworkHandler() == null) return;
            Cornos.minecraft.getNetworkHandler().sendPacket(new ClientStatusC2SPacket(ClientStatusC2SPacket.Mode.PERFORM_RESPAWN));
        }
        super.onExecute();
    }
}
