package fsm.GUIStates;

import fsm.State;
import gamelauncher.Game;

/**
 * Created by Christopher on 24/04/2017.
 */
public class ExitState extends State {

    @Override
    public String onUpdate() {
        finalState = true;
        isFinalState();
        Game.halt();
        return "exit";
    }

    @Override
    public String getStateName() {
        return "exit";
    }
}
