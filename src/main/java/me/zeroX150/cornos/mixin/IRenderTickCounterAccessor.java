package me.zeroX150.cornos.mixin;

import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RenderTickCounter.class)
public interface IRenderTickCounterAccessor {
    @Accessor("tickTime")
    float getTickTime();

    @Accessor("tickTime")
    void setTickTime(float newTickTime);
}
