package org.SimpleGUI.Components;

import org.SimpleGUI.Util.ButtonGroup;

/**
 *
 * @author Lucas Barbosa
 */
public class RadioButton extends CheckBox {
    
    protected ButtonGroup buttonGroup;
    
    public RadioButton(float x, float y, float width, float height) {
        super(x, y, width, height);
    }
    
    public RadioButton(float x, float y, float width, float height, String text) {
        super(x, y, width, height, text);
    }
    
    public boolean haveButtonGroup(){
        return (buttonGroup!=null);
    }
    
    @Override
    public String toString(){
        return "[RadioButton: "+text+" : "+selected+"]";
    }
    
    //gets and sets
    public ButtonGroup getButtonGroup() {
        return buttonGroup;
    }

    public void setButtonGroup(ButtonGroup buttonGroup) {
        this.buttonGroup = buttonGroup;
    }
    
}
