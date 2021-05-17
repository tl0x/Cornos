package me.constantindev.ccl.features.module.impl.combat;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.config.MConfNum;
import me.constantindev.ccl.features.module.Module;
import me.constantindev.ccl.features.module.ModuleType;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.hit.BlockHitResult;

public class ChestStealer extends Module {
    int currentSlot = 0;
    MConfNum delay = new MConfNum("delay", 2.0, 20.0, 0);
    int delayWaited = 0;

    public ChestStealer() {
        super("ChestStealer", "Steals chests", ModuleType.COMBAT);
        this.mconf.add(delay);
    }

    @Override
    public void onExecute() {
        if ((Cornos.minecraft.currentScreen instanceof GenericContainerScreen) &&
                (Cornos.minecraft.crosshairTarget instanceof BlockHitResult) &&
                (Cornos.minecraft.world != null && Cornos.minecraft.world.getBlockState(
                        ((BlockHitResult) Cornos.minecraft.crosshairTarget).getBlockPos()).getBlock().is(Blocks.CHEST))) {
            GenericContainerScreen gc = (GenericContainerScreen) Cornos.minecraft.currentScreen;
            int lastIndex = 0;
            int interacted = 0;

            for (Slot s : gc.getScreenHandler().slots) {
                lastIndex++;
                if (lastIndex > gc.getScreenHandler().getRows() * 9) break;
                if (s.getStack().getItem() == Items.AIR) continue;
                delayWaited++;
                interacted++;
                if (delayWaited > delay.getValue()) delayWaited = 0;
                else break;
                assert Cornos.minecraft.interactionManager != null;
                Cornos.minecraft.interactionManager.clickSlot(gc.getScreenHandler().syncId, s.id, 0, SlotActionType.QUICK_MOVE, Cornos.minecraft.player);
            }
            if (interacted == 0) gc.onClose();
            currentSlot++;
        } else currentSlot = 0;
        super.onExecute();
    }
}
