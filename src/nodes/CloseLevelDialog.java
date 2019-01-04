package nodes;

import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Dialog;
import res.Settings;

public class CloseLevelDialog extends Node  {

    @Override
    public boolean isValid() {
        return Dialog.isOpen() && Dialog.canContinue();
    }

    @Override
    public void execute() {
        Settings.state = "skipping dialog";
        Dialog.processContinue();
        Time.sleepUntil(()-> !isValid(), Random.low(2000,3000));
    }

}
