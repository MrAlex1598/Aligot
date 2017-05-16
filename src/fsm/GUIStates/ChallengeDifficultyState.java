package fsm.GUIStates;

import core.GraphicsEngine;
import fsm.State;
import graphics.gui.Button;
import graphics.gui.GUI;
import graphics.gui.GUIButtonListener;

/**
 * Created by Christopher on 18/04/2017.
 */
public class ChallengeDifficultyState extends State{
    private GUI selectChallenge;
    private GraphicsEngine graphicsEngine;
    private GUIButtonListener easyChallengeButtonListener = new GUIButtonListener();
    private GUIButtonListener mediumChallengeButtonListener = new GUIButtonListener();
    private GUIButtonListener hardChallengeButtonListener = new GUIButtonListener();
    private GUIButtonListener backButtonListener = new GUIButtonListener();

    public ChallengeDifficultyState(GUI selectChallenge, GraphicsEngine graphicsEngine){
        this.selectChallenge = selectChallenge;
        this.graphicsEngine = graphicsEngine;
    }

    public void onEnter(){
        Button easy = selectChallenge.getButtonById("easy");
        easy.addListener(easyChallengeButtonListener);
        Button medium = selectChallenge.getButtonById("medium");
        medium.addListener(mediumChallengeButtonListener);
        Button hard = selectChallenge.getButtonById("hard");
        hard.addListener(hardChallengeButtonListener);
        Button back = selectChallenge.getButtonById("back");
        back.addListener(backButtonListener);
        easyChallengeButtonListener.setNotClicked();
        mediumChallengeButtonListener.setNotClicked();
        hardChallengeButtonListener.setNotClicked();
        backButtonListener.setNotClicked();
        graphicsEngine.setGUI(selectChallenge);
        System.out.println("Switching to Select Challenge Screen");
    }

    @Override
    public String onUpdate() {
        if (easyChallengeButtonListener.isClicked()){
            return "easyChallenges";
        }
        else if (mediumChallengeButtonListener.isClicked()){
            return "mediumChallenges";
        }
        else if (hardChallengeButtonListener.isClicked()) {
            return "hardChallenges";
        }
        else if (backButtonListener.isClicked()) {
            return "gameMods";
        }
        return "challengeDifficulty";
    }

    @Override
    public String getStateName() {
        return "challengeDifficulty";
    }
}