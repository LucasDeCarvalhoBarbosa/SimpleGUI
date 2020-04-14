package org.SimpleGUI.Util;

import org.SimpleGUI.Components.RadioButton;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lucas Barbosa
 */
public class ButtonGroup {
    
    protected List<RadioButton> radioButtons;
    
    protected RadioButton chosen;
    
    public ButtonGroup(){
        this.radioButtons = new ArrayList<>();
    }
    
    public void addRadioButton(RadioButton rb){
        radioButtons.add(rb);
        rb.setButtonGroup(this);
    }
    
    //gets and sets
    public RadioButton getChosen() {
        return chosen;
    }

    public void setChosen(RadioButton chosen) {
        this.chosen = chosen;
        
        for(int i=0;i<radioButtons.size();i++){
            if(!radioButtons.get(i).equals(chosen)){
                radioButtons.get(i).setSelected(false);
            }
        }
    }
    
}
