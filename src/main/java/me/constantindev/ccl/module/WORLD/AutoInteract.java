package me.constantindev.ccl.module.WORLD;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.MConfNum;
import me.constantindev.ccl.etc.ms.ModuleType;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;

public class AutoInteract extends Module {
    MConfNum delay = new MConfNum("delay", 5, 20, 0);
    int passed = -1;

    public AutoInteract() {
        super("AutoInteract", "Automatically interacts with shit", ModuleType.WORLD);
        mconf.add(delay);
    }

    @Override
    public void onExecute() {
        passed++;
        if (passed >= delay.getValue()) {
            passed = -1;
            if (!(Cornos.minecraft.crosshairTarget instanceof BlockHitResult)) return;
            Cornos.minecraft.interactionManager.interactBlock(Cornos.minecraft.player, Cornos.minecraft.world, Hand.MAIN_HAND, (BlockHitResult) Cornos.minecraft.crosshairTarget);
        } else {
            Cornos.minecraft.player.currentScreenHandler.close(Cornos.minecraft.player);
            CloseHandledScreenC2SPacket p = new CloseHandledScreenC2SPacket(Cornos.minecraft.player.currentScreenHandler.syncId);
            Cornos.minecraft.getNetworkHandler().sendPacket(p);
        }
        super.onExecute();
    }
}
