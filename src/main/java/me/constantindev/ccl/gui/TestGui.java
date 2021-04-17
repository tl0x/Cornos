package me.constantindev.ccl.gui;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.gui.widget.RoundedButtonWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class TestGui extends Screen {
    public TestGui() {
        super(Text.of("Bruh"));
    }

    @Override
    protected void init() {
        this.addButton(new RoundedButtonWidget(5, 5, 100, 100, 30, Text.of("ham"), Cornos::openCongratsScreen));
        super.init();
    }
}
