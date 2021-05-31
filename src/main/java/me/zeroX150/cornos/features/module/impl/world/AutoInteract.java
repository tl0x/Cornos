package me.zeroX150.cornos.features.module.impl.world;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.etc.config.MConfNum;
import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleType;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;

import java.util.Objects;

public class AutoInteract extends Module {
    MConfNum delay = new MConfNum("delay", 5, 20, 0, "interaction delay");
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
            if (!(Cornos.minecraft.crosshairTarget instanceof BlockHitResult))
                return;
            assert Cornos.minecraft.interactionManager != null;
            Cornos.minecraft.interactionManager.interactBlock(Cornos.minecraft.player, Cornos.minecraft.world,
                    Hand.MAIN_HAND, (BlockHitResult) Cornos.minecraft.crosshairTarget);
        } else {
            assert Cornos.minecraft.player != null;
            Cornos.minecraft.player.currentScreenHandler.close(Cornos.minecraft.player);
            CloseHandledScreenC2SPacket p = new CloseHandledScreenC2SPacket(
                    Cornos.minecraft.player.currentScreenHandler.syncId);
            Objects.requireNonNull(Cornos.minecraft.getNetworkHandler()).sendPacket(p);
        }
        super.onExecute();
    }
}
