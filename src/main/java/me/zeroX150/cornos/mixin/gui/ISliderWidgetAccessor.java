package me.zeroX150.cornos.mixin.gui;

import net.minecraft.client.gui.widget.SliderWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

// fuck you, minecraft
@Mixin(SliderWidget.class)
public interface ISliderWidgetAccessor {
    @Accessor("value")
    double getValue();
}
