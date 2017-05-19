package fsm.GUIStates;

import core.GraphicsEngine;
import core.LevelGen;
import fsm.State;
import gamelauncher.Game;
import graphics.Window;
import graphics.gui.*;

import java.util.Random;

public class MultiState extends State {
    private GUI multi;
    private GraphicsEngine graphicsEngine;
    private int i = 2;
    private Label count;
    private GUIButtonListener rightButtonListener = new GUIButtonListener();
    private GUIButtonListener leftButtonListener = new GUIButtonListener();
    private GUIButtonListener fightButtonListener = new GUIButtonListener();
    private GUIButtonListener backButtonListener = new GUIButtonListener();

    public MultiState(GraphicsEngine graphicsEngine){
        this.graphicsEngine = graphicsEngine;
    }

    public void initialize(){
        multi = new GUI();
        int buttonWidth = (int)(Window.getWidth()*0.08), buttonHeight = (int)(Window.getHeight()*0.14);
        Image menu = new Image("menu_bg.png");
        menu.setZ(-1);
        multi.addComponent(menu);
        Label description = new Label("Select how many celestial bodies to generate", Window.getWidth()/2 - (int)(Window.getWidth()*0.33), Window.getHeight()/7, (int)(Window.getWidth()*0.7),(int)(Window.getHeight()*0.16), "description");
        multi.addComponent(description);
        Label text = new Label("Number of celestial bodies", 4*(Window.getWidth()/10) - (int)(Window.getWidth()*0.17), Window.getHeight()/2 - (int)(Window.getHeight()*0.07), (int)(Window.getWidth()*0.35), (int)(Window.getHeight()*0.14), "text");
        multi.addComponent(text);
        count = new Label ("---", 7*(Window.getWidth()/10) -5, Window.getHeight() /2 - (int)(Window.getHeight()*0.08), (int)(Window.getWidth()*0.039), (int)(Window.getHeight()*0.27), "count");
        multi.addComponent(count);
        Button right = new Button("button_right.png", "", 8*(Window.getWidth()/10) - buttonWidth / 2, Window.getHeight() /2 - buttonHeight /2 + (int)(Window.getHeight()*0.06), buttonWidth - 15, buttonHeight - 20, "right");
        right.addListener(rightButtonListener);
        multi.addComponent(right);
        Button left = new Button("button_left.png", "", 6*(Window.getWidth()/10) - buttonWidth / 2, Window.getHeight() /2 - buttonHeight/2 + (int)(Window.getHeight()*0.06), buttonWidth - 15, buttonHeight - 20,"left");
        left.addListener(leftButtonListener);
        multi.addComponent(left);
        Button fight = new Button("button_fight.png", "",Window.getWidth() - (int)(Window.getWidth()*0.15), Window.getHeight() - (int)(Window.getHeight()*0.17), (int)(Window.getWidth()*0.11), (int)(Window.getHeight()*0.1), "fight");
        fight.addListener(fightButtonListener);
        multi.addComponent(fight);
        Button back = new Button("button_back.png","", (int)(Window.getWidth()*0.03), Window.getHeight() - (int)(Window.getHeight()*0.12), (int)(Window.getWidth()*0.08),(int)(Window.getHeight()*0.07), "back");
        back.addListener(backButtonListener);
        multi.addComponent(back);
        count = multi.getLabelById("count");
        count.setText(String.valueOf(i));
    }

    public void onEnter() {
        graphicsEngine.setGUI(multi);
    }

    @Override
    public String onUpdate() {
        if (rightButtonListener.isClicked()){
            rightButtonListener.setNotClicked();
            if (i<9){
                i += 1;
                count.setText(Integer.toString(i));
            }
        }
        else if (leftButtonListener.isClicked()){
            leftButtonListener.setNotClicked();
            if(i > 2) {
                i -= 1;
                count.setText(Integer.toString(i));
            }
        }
        else if (fightButtonListener.isClicked()){
            fightButtonListener.setNotClicked();
            LevelGen levelGen = new LevelGen(new Random().nextLong(), LevelGen.SMALL);
            levelGen.setPlanetNumber(i);
            Game.setLevel(levelGen.create());
            return "multiPlay";
        }
        else if (backButtonListener.isClicked()){
            backButtonListener.setNotClicked();
            return "gameMods";
        }
        return "multi";
    }

    @Override
    public String getStateName() {
        return "multi";
    }
}
