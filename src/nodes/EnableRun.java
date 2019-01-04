package nodes;

import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.ui.Log;
import res.Settings;

public class EnableRun extends Node  {

    @Override
    public boolean isValid() {
        return !Movement.isRunEnabled() && Movement.getRunEnergy() > Random.high(0,100);
    }

    @Override
    public void execute() {
        Settings.state = "enabling run";
        Movement.toggleRun(true);
    }

}
