package me.constantindev.ccl.module;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.ms.MType;
import net.minecraft.network.packet.c2s.play.ClientStatusC2SPacket;

public class AutoRespawn extends Module {
    public AutoRespawn() {
        super("AutoRespawn", "Automatically respawns you upon death", MType.MISC);
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
