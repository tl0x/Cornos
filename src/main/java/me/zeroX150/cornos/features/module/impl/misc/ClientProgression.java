/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: ClientProgression
# Created by constantin at 17:56, Mär 31 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.zeroX150.cornos.features.module.impl.misc;

import me.zeroX150.cornos.etc.config.MConfToggleable;
import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleType;

public class ClientProgression extends Module {
    public static MConfToggleable hasFinishedTut = new MConfToggleable("finishedTutorial", false);

    public ClientProgression() {
        super("clientprogression", "how much you progressed with using the client", ModuleType.HIDDEN);
        this.mconf.add(hasFinishedTut);
    }
}
