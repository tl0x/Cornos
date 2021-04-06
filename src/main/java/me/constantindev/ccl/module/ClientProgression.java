/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: ClientProgression
# Created by constantin at 17:56, Mär 31 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.module;

import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.Toggleable;
import me.constantindev.ccl.etc.ms.MType;

public class ClientProgression extends Module {
    public static Toggleable hasFinishedTut = new Toggleable("finishedTutorial", false);

    public ClientProgression() {
        super("clientprogression", "how much you progressed with using the client", MType.HIDDEN);
        this.mconf.add(hasFinishedTut);
    }
}
