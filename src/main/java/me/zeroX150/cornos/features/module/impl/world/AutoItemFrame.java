package me.zeroX150.cornos.features.module.impl.world;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.etc.config.MConfNum;
import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;

public class AutoItemFrame extends Module {
    MConfNum delay = new MConfNum("delay", 0, 20, 0, "delay to interact after");
    MConfNum range = new MConfNum("range", 4, 7, 1, "range of interacting");
    MConfNum itemsPerTick = new MConfNum("itemsPerTick", 2, 20, 1, "how many item frames to click each time");
    int delayPassed = -1;

    public AutoItemFrame() {
        super("AutoItemFrame", "Automatically interacts with nearby item frames", ModuleType.WORLD);
        mconf.add(delay);
        mconf.add(range);
        mconf.add(itemsPerTick);
    }

    @Override
    public void onExecute() {
        delayPassed++;
        if (delayPassed >= delay.getValue())
            delayPassed = 0;
        else
            return;
        Vec3d p = Cornos.minecraft.player.getPos();
        int interacted = 0;
        for (Entity entity : Cornos.minecraft.world.getEntities()) {
            if (entity.getType() == EntityType.ITEM_FRAME && entity.getPos().distanceTo(p) < range.getValue()) {
                ItemFrameEntity ief = (ItemFrameEntity) entity;
                if (ief.getHeldItemStack().getItem() == Items.AIR) {
                    Cornos.minecraft.interactionManager.interactEntity(Cornos.minecraft.player, entity, Hand.MAIN_HAND);
                    interacted++;
                    if (interacted > itemsPerTick.getValue())
                        break;
                }
            }
        }
    }
}
