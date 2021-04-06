/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: AltManagerScreen
# Created by constantin at 08:03, Mär 19 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.gui;

import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.thealtening.auth.service.AlteningServiceType;
import me.constantindev.ccl.etc.config.ClientConfig;
import me.constantindev.ccl.mixin.SessionAccessor;
import me.constantindev.ccl.module.Alts;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.Session;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AltManagerScreen extends Screen {
    TextFieldWidget email;
    TextFieldWidget passwd;
    TextFieldWidget emailpasswd;
    String errormsg = "";
    boolean hasSavedAlts = false;

    public AltManagerScreen() {
        super(Text.of("Cornos alt manager"));
    }

    @Override
    protected void init() {
        ClientConfig.authentication.updateService(AlteningServiceType.MOJANG);
        ButtonWidget w = new ButtonWidget(width - 161, 1, 160, 20, Text.of(ClientConfig.authentication.getService().equals(AlteningServiceType.THEALTENING) ? "TheAltening" : "Mojang"), button -> {
            switch (button.getMessage().asString()) {
                case "Mojang":
                    ClientConfig.authentication.updateService(AlteningServiceType.MOJANG);
                    button.setMessage(Text.of("Cracked"));
                    passwd.setEditable(false);
                    emailpasswd.setEditable(false);
                    break;
                case "Cracked":
                    ClientConfig.authentication.updateService(AlteningServiceType.THEALTENING);
                    button.setMessage(Text.of("TheAltening"));
                    passwd.setEditable(false);
                    emailpasswd.setEditable(false);
                    break;
                case "TheAltening":
                    ClientConfig.authentication.updateService(AlteningServiceType.MOJANG);
                    button.setMessage(Text.of("Mojang"));
                    passwd.setEditable(true);
                    emailpasswd.setEditable(true);
                    break;
            }
        });
        email = new TextFieldWidget(textRenderer, width / 2 - (200 / 2), height / 2 - (20 / 2) - 35, 200, 20, Text.of("Email"));
        email.setMaxLength(1000);
        passwd = new TextFieldWidget(textRenderer, width / 2 - (200 / 2), height / 2 - (20 / 2), 200, 20, Text.of("Password"));
        passwd.setMaxLength(1000);
        emailpasswd = new TextFieldWidget(textRenderer, width / 2 - (200 / 2), height / 2 - (20 / 2) + 35, 200, 20, Text.of("Username:password"));
        emailpasswd.setMaxLength(2000);
        ButtonWidget login = new ButtonWidget(width / 2 - (120 / 2), height / 2 - (20 / 2) + 60, 120, 20, Text.of("Login"), button -> {
            if (w.getMessage().asString().equalsIgnoreCase("cracked")) {
                Session newS = new Session(this.email.getText(), UUID.randomUUID().toString(), "CornosOnTOP", "mojang");
                assert this.client != null;
                ((SessionAccessor) this.client).setSession(newS);
                errormsg = "§aSet username to " + this.email.getText();
                return;
            }
            YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) (new YggdrasilAuthenticationService(Proxy.NO_PROXY, "")).createUserAuthentication(Agent.MINECRAFT);
            auth.setUsername(this.email.getText());
            auth.setPassword(ClientConfig.authentication.getService().equals(AlteningServiceType.THEALTENING) ? "CornosOnTOP" : this.passwd.getText());
            try {
                auth.logIn();
                Session newS = new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
                errormsg = "§aSuccessfully logged in! Username: " + newS.getUsername();
                assert this.client != null;
                ((SessionAccessor) this.client).setSession(newS);

            } catch (Exception exc) {
                errormsg = "§cSomething went wrong: " + exc.getMessage();
            }
        });
        ButtonWidget saveAlt = new ButtonWidget(width / 2 - (120 / 2), height / 2 - (20 / 2) + 85, 120, 20, Text.of("Save alt"), button -> {
            String email = this.email.getText();
            String passwd = this.passwd.getText();
            if (email.isEmpty()) {
                this.errormsg = "§cEmail cannot be empty";
                return;
            }
            if (passwd.isEmpty() && !w.getMessage().asString().equalsIgnoreCase("cracked")) {
                this.errormsg = "§cPassword cannot be empty";
                return;
            }
            if (w.getMessage().asString().equalsIgnoreCase("cracked")) {
                passwd = "ThisDoesNotMatterSinceTheAccountIsCracked";
            }
            Alts.k.setValue(Alts.k.value + ((char) 999) + encStr(email, 6001) + ((char) 998) + encStr(passwd, 6000));
            assert this.client != null;
            this.client.openScreen(new AltManagerScreen());
        });
        this.addButton(login);
        this.addButton(w);
        this.addButton(saveAlt);

        List<List<String>> goodalts = new ArrayList<>();
        for (String s : Alts.k.value.substring(1).split(((char) 999) + "")) {
            String[] keypair = s.split(((char) 998) + "");
            if (keypair.length > 1) {
                System.out.println(s);
                List<String> bruh = new ArrayList<>();
                bruh.add(encStr(keypair[0], -6001));
                bruh.add(encStr(keypair[1], -6000));
                goodalts.add(bruh);
            }
        }
        List<String> fal = new ArrayList<>();
        int offset = 55;
        for (List<String> good : goodalts) {
            hasSavedAlts = true;
            fal.add(encStr(good.get(0), 6001) + ((char) 998) + encStr(good.get(1), 6000));
            ButtonWidget alt = new ButtonWidget(width - 141, offset, 140, 20, Text.of(good.get(0)), button -> {
                this.email.setText(good.get(0));
                if (!good.get(1).equalsIgnoreCase("ThisDoesNotMatterSinceTheAccountIsCracked"))
                    this.passwd.setText(good.get(1));
            });
            ButtonWidget delete = new ButtonWidget(width - 141 - 21, offset, 20, 20, Text.of("X"), button -> {
                fal.remove(encStr(good.get(0), 6001) + ((char) 998) + encStr(good.get(1), 6000));
                Alts.k.setValue("0" + String.join(((char) 999) + "", fal));
                assert this.client != null;
                this.client.openScreen(new AltManagerScreen());
            });
            offset += 25;
            this.addButton(alt);
            this.addButton(delete);
        }
        Alts.k.setValue("0" + String.join(((char) 999) + "", fal));

        super.init();
    }

    @Override
    public void tick() {
        String s = emailpasswd.getText();
        String[] keypair = s.split(":");
        if (keypair.length < 2) return;
        String email = keypair[0];
        String passwd = keypair[1];
        this.email.setText(email);
        this.passwd.setText(passwd);
        super.tick();
    }

    String encStr(String s2c, int off) {
        StringBuilder ret = new StringBuilder();
        for (char c : s2c.toCharArray()) {
            ret.append((char) ((int) c + off));
        }
        return ret.toString();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        DrawableHelper.drawCenteredString(matrices, textRenderer, "Email", width / 2, height / 2 - (20 / 2) - 45, 0xFFFFFF);
        email.render(matrices, mouseX, mouseY, delta);
        DrawableHelper.drawCenteredString(matrices, textRenderer, "Password", width / 2, height / 2 - (20 / 2) - 10, 0xFFFFFF);
        passwd.render(matrices, mouseX, mouseY, delta);
        DrawableHelper.drawCenteredString(matrices, textRenderer, "Or email:password", width / 2, height / 2 - (20 / 2) + 25, 0xFFFFFF);
        emailpasswd.render(matrices, mouseX, mouseY, delta);
        if (hasSavedAlts)
            DrawableHelper.drawCenteredString(matrices, textRenderer, "Saved alts", width - 80, 40, 0xFFFFFF);
        if (!errormsg.isEmpty()) {
            textRenderer.draw(matrices, errormsg, 1, 1, 0xFFFFFF);
            //DrawableHelper.drawCenteredString(matrices,textRenderer,errormsg,width/2,height/2+30,0xFFFFFF);
        }
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        email.mouseClicked(mouseX, mouseY, button);
        passwd.mouseClicked(mouseX, mouseY, button);
        emailpasswd.mouseClicked(mouseX, mouseY, button);
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        email.keyPressed(keyCode, scanCode, modifiers);
        passwd.keyPressed(keyCode, scanCode, modifiers);
        emailpasswd.keyPressed(keyCode, scanCode, modifiers);
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        email.keyReleased(keyCode, scanCode, modifiers);
        passwd.keyReleased(keyCode, scanCode, modifiers);
        emailpasswd.keyReleased(keyCode, scanCode, modifiers);
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char chr, int keyCode) {
        email.charTyped(chr, keyCode);
        passwd.charTyped(chr, keyCode);
        emailpasswd.charTyped(chr, keyCode);
        return false;
    }
}
