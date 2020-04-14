package org.SimpleGUI.Util;

/**
 *
 * @author Lucas Barbosa
 */
public class PerformAction {
    
    private Action action;
    
    public PerformAction(Action action){
        this.action = action;
    }
    
    public void execute(){
        action.action();
    }
    
}
