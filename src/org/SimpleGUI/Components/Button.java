package org.SimpleGUI.Components;

import org.newdawn.slick.Image;
import org.SimpleGUI.Util.Action;
import org.SimpleGUI.Util.Standards;
import org.newdawn.slick.Input;

/**
 *
 * @author Lucas Barbosa
 */
public class Button extends Label {
    
    protected Action action;
    protected boolean highlight = false;
    
    
    public Button(float x, float y, float width, float height) {
        super(x, y, width, height);
        setBackgroundColor(Standards.STANDARD_BUTTON_BACKGROUND_COLOR);
    }
    
    public Button(){
        super();
        setBackgroundColor(Standards.STANDARD_BUTTON_BACKGROUND_COLOR);
    }
    
    public Button(String text){
        super(text);
        setBackgroundColor(Standards.STANDARD_BUTTON_BACKGROUND_COLOR);
    }
    
    public Button(Image icon){
        super(icon);
        setBackgroundColor(Standards.STANDARD_BUTTON_BACKGROUND_COLOR);
    }
    
    /**
     * Returns true if it has a designated action, and otherwise returns false.
     * 
     * @return true if it has a designated action, and otherwise returns false
     */
    public boolean haveAction(){
        return (action!=null);
    }
    
    /**
     * add a listener to use when the button is clicked.
     * 
     * @param action the listener.
     */
    public void addAction(Action action){
        this.action = action;
    }
    
    @Override
    public boolean isMouseOver(Input input){
        this.highlight = super.isMouseOver(input);
        
        return highlight;
    }
    
    @Override
    public String toString(){
        return "[Button: "+text+"]";
    }

    //gets and sets
    public Action getAction() {
        return action;
    }
    
    /**
     * If the mouse is over it is highlighted.
     * 
     * @return the highlighted attribute
     */
    public boolean isHighlight() {
        return highlight;
    }
    
}
