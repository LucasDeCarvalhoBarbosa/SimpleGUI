package org.SimpleGUI.Components;

import org.SimpleGUI.Util.Action;
import org.SimpleGUI.Util.PerformAction;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

/**
 *
 * @author Lucas Barbosa
 */
public class TextField extends Label {
    
    protected boolean focusOwner;
    protected KeyListener keylistener;
    protected Action action;
    protected int characterLimit = Integer.MAX_VALUE;
    
    public TextField(){
        super();
        text = "";
        initKeylistener();
    }
    
    public TextField(String text){
        super(text);
        initKeylistener();
    }
    
    private void initKeylistener(){//missing caps lock (with a boolean) and shift (can be keyReleased to stop shift)
        keylistener = new KeyListener() {
            
            @Override
            public void keyPressed(int key, char c) {
                boolean change = false;
                
                if(text.length() < characterLimit){//Characters
                    text += replaceAccents(c+"");
                    change = true;
                }

                if(key == Input.KEY_BACK){//Backspace
                    text = removeLastCharacter(text);
                    change = true;
                }
                
                if(key == Input.KEY_ENTER){//Enter
                    if(action != null)
                        new PerformAction(action).execute();
                }
                
                if(change)
                    setSize(getPreferredSize());
            }

            @Override
            public void keyReleased(int key, char c) {}

            @Override
            public void setInput(Input input) {}

            @Override
            public boolean isAcceptingInput() {
                return focusOwner;
            }

            @Override
            public void inputEnded() {}

            @Override
            public void inputStarted() {}
        };
    }
    
    private String removeLastCharacter(String text){
        if(text.length()>1){
            //System.out.println("text.substring(): "+text.substring(0, text.length()-2));
            return text.substring(0, text.length()-2);//-1
        }
            
        
        return "";
    }
    
    @Override
    public String toString(){
        return "[TextField: "+text+" : "+focusOwner+"]";
    }
    
    //gets and sets
    public boolean isFocusOwner() {
        return focusOwner;
    }

    public void setFocusOwner(boolean focusOwner) {
        this.focusOwner = focusOwner;
    }

    public KeyListener getKeylistener() {
        return keylistener;
    }

    public Action getAction() {
        return action;
    }

    public void addAction(Action action) {
        this.action = action;
    }

    public int getCharacterLimit() {
        return characterLimit;
    }

    public void setCharacterLimit(int characterLimit) {
        this.characterLimit = characterLimit;
    }

    
}
