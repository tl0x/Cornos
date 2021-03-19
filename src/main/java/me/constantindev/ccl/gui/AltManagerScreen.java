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
import com.mojang.authlib.Environment;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.thealtening.auth.service.AlteningServiceType;
import me.constantindev.ccl.etc.config.ClientConfig;
import me.constantindev.ccl.etc.helper.RenderHelper;
import me.constantindev.ccl.mixin.SessionAccessor;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.util.Session;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.data.client.model.Texture;
import net.minecraft.text.Text;

import java.awt.*;
import java.net.Proxy;

public class AltManagerScreen extends Screen {
    TextFieldWidget email;
    TextFieldWidget passwd;
    String errormsg = "";
    public AltManagerScreen() {
        super(Text.of("Cornos alt manager"));
    }

    @Override
    protected void init() {
        Environment thealtening = new Environment() {
            @Override
            public String getAuthHost() {
                return AlteningServiceType.THEALTENING.getAuthServer();
            }

            @Override
            public String getAccountsHost() {
                return "";
            }

            @Override
            public String getSessionHost() {
                return AlteningServiceType.THEALTENING.getSessionServer();
            }

            @Override
            public String getServicesHost() {
                return "";
            }

            @Override
            public String getName() {
                return "Thealtening";
            }

            @Override
            public String asString() {
                return "null";
            }
        };
        ClientConfig.authentication.updateService(AlteningServiceType.MOJANG);
        super.init();
        email = new TextFieldWidget(textRenderer,width/2-(200/2),height/2-(20/2)-15,200,20,Text.of("Email"));
        passwd = new TextFieldWidget(textRenderer,width/2-(200/2),height/2-(20/2)+15,200,20,Text.of("Password"));
        ButtonWidget w = new ButtonWidget(width-121,1,120,20,Text.of(ClientConfig.authentication.getService().equals(AlteningServiceType.THEALTENING)?"TheAltening":"Mojang"),button -> {
            switch(button.getMessage().asString()) {
                case "Mojang":
                    ClientConfig.authentication.updateService(AlteningServiceType.THEALTENING);
                    button.setMessage(Text.of("TheAltening"));
                    passwd.setEditable(false);
                    break;
                case "TheAltening":
                    passwd.setEditable(true);
                    ClientConfig.authentication.updateService(AlteningServiceType.MOJANG);
                    button.setMessage(Text.of("Mojang"));
                    break;
            }
        });
        ButtonWidget login = new ButtonWidget(width/2-(120/2),height/2-(20/2)+45,120,20,Text.of("Login"),button -> {
            YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) (new YggdrasilAuthenticationService(Proxy.NO_PROXY,"")).createUserAuthentication(Agent.MINECRAFT);
            auth.setUsername(this.email.getText());
            auth.setPassword(ClientConfig.authentication.getService().equals(AlteningServiceType.THEALTENING)?"CornosOnTOP":this.passwd.getText());
            try {
                auth.logIn();
                Session newS = new Session(auth.getSelectedProfile().getName(),auth.getSelectedProfile().getId().toString(),auth.getAuthenticatedToken(),"mojang");
                errormsg = "§aSuccessfully logged in! Username: "+newS.getUsername();
                ((SessionAccessor) this.client).setSession(newS);

            } catch (Exception exc) {
                errormsg = "§cSomething went wrong: "+exc.getMessage();
            }
        });
        this.addButton(login);
        this.addButton(w);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        email.render(matrices, mouseX, mouseY, delta);
        passwd.render(matrices, mouseX, mouseY, delta);
        if (!errormsg.isEmpty()) {
            textRenderer.draw(matrices,errormsg,1,1,0xFFFFFF);
            //DrawableHelper.drawCenteredString(matrices,textRenderer,errormsg,width/2,height/2+30,0xFFFFFF);
        }
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        email.mouseClicked(mouseX,mouseY,button);
        passwd.mouseClicked(mouseX, mouseY, button);
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        email.keyPressed(keyCode, scanCode, modifiers);
        passwd.keyPressed(keyCode, scanCode, modifiers);
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        email.keyReleased(keyCode, scanCode, modifiers);
        passwd.keyReleased(keyCode, scanCode, modifiers);
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char chr, int keyCode) {
        email.charTyped(chr, keyCode);
        passwd.charTyped(chr, keyCode);
        return false;
    }
}
