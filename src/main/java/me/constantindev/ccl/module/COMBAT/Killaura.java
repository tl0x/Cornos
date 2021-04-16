package me.constantindev.ccl.module.COMBAT;

import com.google.common.base.CharMatcher;
import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.MultiOption;
import me.constantindev.ccl.etc.config.Num;
import me.constantindev.ccl.etc.config.Toggleable;
import me.constantindev.ccl.etc.ms.MType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class Killaura extends Module {
    MultiOption mode = new MultiOption("mode", "single", new String[]{"single", "multi"});
    Toggleable entities = new Toggleable("entities", false);
    Toggleable players = new Toggleable("players", true);
    Toggleable mobs = new Toggleable("mobs", true);
    Toggleable swing = new Toggleable("swing", true);
    Num range = new Num("range", 3.0, 10.0, 1.0);
    Num delay = new Num("delay", 2.0, 20.0, 0);
    Toggleable abNoname = new Toggleable("ab:noname", true);
    Toggleable abColorName = new Toggleable("ab:colorname", true);
    Toggleable abInvalidName = new Toggleable("ab:invalidName", true);
    int delayWaited = 0;
    List<LivingEntity> attacks = new ArrayList<>();

    public Killaura() {
        super("Killaura", "bruh (ab = antibot)", MType.COMBAT);
        this.mconf.add(mode);
        this.mconf.add(delay);
        this.mconf.add(players);
        this.mconf.add(mobs);
        this.mconf.add(entities);
        this.mconf.add(swing);
        this.mconf.add(range);
        this.mconf.add(abColorName);
        this.mconf.add(abNoname);
        this.mconf.add(abInvalidName);
    }

    @Override
    public void onExecute() {
        delayWaited++;
        if (delayWaited > delay.getValue()) {
            delayWaited = 0;
        } else return;
        attacks.clear();
        Vec3d loc = Cornos.minecraft.player.getPos();
        Box selector = new Box(loc.add(-1, -1, -1), loc.add(1, 1, 1));
        selector = selector.expand(range.getValue());
        List<Entity> entities1 = Cornos.minecraft.world.getOtherEntities(Cornos.minecraft.player, selector);
        if (entities1.size() < 1) return;
        List<Entity> entities = new ArrayList<>();
        for (Entity e : entities1) {
            if ((e instanceof PlayerEntity) && !players.isEnabled()) continue;
            if ((e instanceof HostileEntity) && !mobs.isEnabled()) continue;
            if (!(e instanceof PlayerEntity) && !(e instanceof HostileEntity) && !this.entities.isEnabled()) continue;
            String n = e.getEntityName();
            List<Boolean> abThesis = new ArrayList<>();
            if (abColorName.isEnabled()) abThesis.add(n.contains("§"));
            if (abNoname.isEnabled()) abThesis.add(n.trim().isEmpty());
            if (abInvalidName.isEnabled())
                abThesis.add(!CharMatcher.anyOf("abcdefghijklmnopqrstuvwyxzABCDEFGHIJKLMNOPQRSTUVWYXZ0123456789_").matchesAllOf(n));
            boolean isBot = false;
            for (Boolean thesis : abThesis) {
                isBot = isBot || thesis;
            }
            if (isBot) continue;
            entities.add(e);
        }
        switch (mode.value) {
            case "single":
                for (Entity e : entities) {
                    if (!(e instanceof LivingEntity)) continue;
                    LivingEntity le1 = (LivingEntity) e;
                    if (le1.isDead() || !le1.isAttackable()) continue;
                    attacks.add(le1);
                    break;
                }
                break;
            case "multi":
                entities.forEach(entity -> {
                    if (!(entity instanceof LivingEntity)) return;
                    LivingEntity le1 = (LivingEntity) entity;
                    if (le1.isDead() || !le1.isAttackable()) return;
                    attacks.add(le1);
                });
                break;
        }
        for (Entity e : attacks) {
            Cornos.minecraft.interactionManager.attackEntity(Cornos.minecraft.player, e);
            if (swing.isEnabled()) {
                HandSwingC2SPacket p = new HandSwingC2SPacket(Hand.MAIN_HAND);
                Cornos.minecraft.getNetworkHandler().sendPacket(p);
            }
        }
        super.onExecute();

    }
}
