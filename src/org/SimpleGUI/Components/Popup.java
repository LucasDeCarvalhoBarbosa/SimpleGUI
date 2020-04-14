package org.SimpleGUI.Components;

import org.SimpleGUI.Util.Action;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;

/**
 *
 * @author Lucas Barbosa
 */
public class Popup extends Panel {
    
    protected boolean wantToClose = false;
    
    protected String input;
    
    /**
     * To create a Popup with just one message
     * 
     * @param gc the GameContainer
     * @param message the message that appear in Popup
     */
    public Popup(GameContainer gc, Object message){
        super(0, 0, 0, 0);
        createMessage(message);
        setSize(getPreferredSize());
        centralize(gc);
    }
    
    /**
     * To create a popup with the buttons for paste options.
     * 
     * @param gc the GameContainer
     * @param message the message that appear in Popup
     * @param options the options that will turn buttons
     */
    public Popup(GameContainer gc, Object message, String[] options){
        super(0, 0, 0, 0);
        createMessage(message);
        createOptions(options);
        setSize(getPreferredSize());
        centralize(gc);
    }
    
    /**
     * Create Popup message as an image or other object 
     * (if an object, toString () will be used).
     * 
     * @param message he message that appear in Popup
     */
    private void createMessage(Object message){
        if(message instanceof Image)
            components.add(new Label((Image) message));
        else
            writeText(message.toString());
    }
    
    private void writeText(String text){
        Label lb = new Label(text);
        lb.setBackgroundColor(this.backgroundColor);
        add(lb);//for a while
    }
    
    /**
     * Create buttons for options passed by parameter.
     * 
     * @param options the options that will be the buttons
     */
    private void createOptions(String[] options){
        Panel con = new Panel(0, 0, 0, 0);
        con.setAlignment(Panel.HORIZONTAL_ALIGNMENT);
        con.setBackgroundColor(this.backgroundColor);
        con.setBorderColor(this.backgroundColor);
        add(con);
        for(int i=0;i<options.length;i++){
            Button bt = new Button(options[i]);
            bt.addAction(new Action(){
                @Override
                public void action(){
                    input = bt.getText();
                    wantToClose = true;
                }
            });
            con.add(bt);
        }
    }
    
    //gets and sets
    public boolean isWantToClose() {
        return wantToClose;
    }

    public void setWantToClose(boolean wantToClose) {
        this.wantToClose = wantToClose;
    }

    /**
     * <b>This method must be executed in a separate 
     *  THREAD from the Slick loop thread.</b><br>
     *  Returns the String of the button option that was pressed.
     * 
     * @return the String of the button option that was pressed
     * @throws InterruptedException if there is any problem waiting for the button press
     */
    public synchronized String getInput() throws InterruptedException {
        Thread t = new Thread(){
          @Override
          public void run(){
              while(!wantToClose){
                  try {Thread.sleep(200);}catch (InterruptedException ex) {}
              }
          }
        };
        t.start();
        t.join();

        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }
    
}
