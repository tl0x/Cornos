package me.zeroX150.cornos.gui.widget;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class PasswordField extends TextFieldWidget {

    private boolean showPassword;

    public PasswordField(TextRenderer textRenderer, int x, int y, int width, int height, Text text) {
        super(textRenderer, x, y, width, height, text);
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        String text = this.getText();
        if (!showPassword) {
            this.setText(this.getText().replaceAll("(?s).", "x"));
        }
        super.renderButton(matrices, mouseX, mouseY, delta);
        if (!showPassword) {
            this.setText(text);
        }
    }

    public boolean isShowPassword() {
        return showPassword;
    }

    public void setShowPassword(boolean showPassword) {
        this.showPassword = showPassword;
    }
}
