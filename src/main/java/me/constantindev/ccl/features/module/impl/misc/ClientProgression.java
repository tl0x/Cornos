/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: ClientProgression
# Created by constantin at 17:56, Mär 31 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.features.module.impl.misc;

import me.constantindev.ccl.etc.config.MConfToggleable;
import me.constantindev.ccl.features.module.Module;
import me.constantindev.ccl.features.module.ModuleType;

public class ClientProgression extends Module {
    public static MConfToggleable hasFinishedTut = new MConfToggleable("finishedTutorial", false);

    public ClientProgression() {
        super("clientprogression", "how much you progressed with using the client", ModuleType.HIDDEN);
        this.mconf.add(hasFinishedTut);
    }
}
