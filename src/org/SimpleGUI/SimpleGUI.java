package org.SimpleGUI;

import java.io.FileInputStream;
import java.io.InputStream;
import org.SimpleGUI.Components.Button;
import org.SimpleGUI.Components.CheckBox;
import org.SimpleGUI.Components.Component;
import org.SimpleGUI.Components.Panel;
import org.SimpleGUI.Components.Label;
import org.SimpleGUI.Components.Popup;
import org.SimpleGUI.Components.RadioButton;
import org.SimpleGUI.Components.TextField;
import org.SimpleGUI.Util.PerformAction;
import org.SimpleGUI.Util.Standards;
import org.SimpleGUI.Util.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Lucas Barbosa
 */
public class SimpleGUI {
    
    private GameContainer container;
    private List<Component> widgets;
    private AngelCodeFont font;
    
    public SimpleGUI(GameContainer container){
        InputStream inputFont = getClass().getClassLoader().getResourceAsStream("org/SimpleGUI/fonts/arial16.fnt");
        InputStream inputImage = getClass().getClassLoader().getResourceAsStream("org/SimpleGUI/fonts/arial16.png");
        AngelCodeFont angel;
        try {
            angel = new AngelCodeFont("arial16.png", inputFont, inputImage);
            this.font = angel;
        } catch (SlickException ex) {
            Logger.getLogger(SimpleGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.container = container;
        this.widgets = new ArrayList<>();
        container.getInput().enableKeyRepeat();
    }
    
    public SimpleGUI(GameContainer container, AngelCodeFont font){
        this.container = container;
        this.font = font;
        this.widgets = new ArrayList<>();
    }
    
    /**
     * Adds the component to the user interface.
     * 
     * @param c the component that will be added to the user interface
     */
    public void add(Component c){
        //if adding another function, remember to add the treatment for the container too
        if(!widgets.contains(c)){
            widgets.add(c);
            if(c instanceof TextField)
                container.getInput().addKeyListener(((TextField) c).getKeylistener());
            
            if(c instanceof Label)
                ((Label) c).setFont(font);
            
            if(c instanceof Panel){
                Panel con = (Panel) c;
                for(int i=0;i<con.getComponentsCount();i++){
                    if(con.getComponent(i) instanceof TextField)
                        container.getInput().addKeyListener(((TextField) con.getComponent(i)).getKeylistener());
                    
                    if(con.getComponent(i) instanceof Label)
                        ((Label) con.getComponent(i)).setFont(font);
                }
            }
                
        }
    }
    
    /**
     * Removes the component to the user interface.
     * 
     * @param c the component that will be removed to the user interface
     * @return the removed component
     */
    public boolean remove(Component c){
        if(widgets.contains(c)){
            widgets.remove(c);
            
            return true;
        }

        return false;
    }
    
    /**
     * Checks user interface events, such as button clicks and key listeners, for example.
     */
    public void checkEvents(){
        Input input = container.getInput();
        
        for(int i=0;i<widgets.size();i++){
            
            if(widgets.get(i) instanceof RadioButton){
                verifyRadioButton((RadioButton) widgets.get(i), input);
            }else
                
            if(widgets.get(i) instanceof Button){
                verifyButton((Button) widgets.get(i), input);
                
            }else
                
            if(widgets.get(i) instanceof TextField){
                verifyTextField((TextField) widgets.get(i), input);
                
            }else
            
            if(widgets.get(i) instanceof Panel){
                verifyContainer((Panel) widgets.get(i), input);
            }
            
        }
        
        
    }
    
    /**
     * Specifically checks radio button events.
     * 
     * @param rb the RadioButton that will be checked
     * @param input the input from GameContainer
     */
    private void verifyRadioButton(RadioButton rb, Input input){
        if(rb.haveButtonGroup()){
            if(rb.isPressed(input)){
                rb.getButtonGroup().setChosen(rb);
                
            }
            
        }
    }
    
    /**
     * Specifically checks button events.
     * 
     * @param button the Button that will be checked
     * @param input the input from GameContainer
     */
    private void verifyButton(Button button, Input input){
        if(button.isPressed(input))
            if(button.haveAction()){
                PerformAction performAction = new PerformAction(button.getAction());
                performAction.execute();
            }
            
    }
    
    /**
     * Specifically checks Text field events.
     * 
     * @param tf the TextField that will be checked
     * @param input the input from GameContainer
     */
    private void verifyTextField(TextField tf, Input input){
        if(tf.isPressed(input)){
            tf.setFocusOwner(true);
            
            for(int i=0;i<widgets.size();i++){
                if(widgets.get(i) instanceof TextField)
                    if(!widgets.get(i).equals(tf))
                        ((TextField) widgets.get(i)).setFocusOwner(false);
                
                if(widgets.get(i) instanceof Panel){
                    Panel con = ((Panel) widgets.get(i));
                    for(int j=0;j<con.getComponentsCount();j++){
                        if(con.getComponent(j) instanceof TextField)
                            if(!con.getComponent(j).equals(tf))
                                ((TextField) con.getComponent(j)).setFocusOwner(false);
                                
                    }
                }
                    
            }
        }
    }
    
    /**
     * Specifically checks Container children events.
     * 
     * @param con the container that will check your children's events
     * @param input the input from GameContainer
     */
    private void verifyContainer(Panel con, Input input){
        for(int i=0;i<con.getComponentsCount();i++){
            
            if(con.getComponent(i) instanceof RadioButton){
                verifyRadioButton((RadioButton) con.getComponent(i), input);
            }else
                
            if(con.getComponent(i) instanceof Button){
                verifyButton((Button) con.getComponent(i), input);
            }else
                
            if(con.getComponent(i) instanceof TextField){
                verifyTextField((TextField) con.getComponent(i), input);
            }else
            
            if(con.getComponent(i) instanceof Panel){
                verifyContainer((Panel) con.getComponent(i), input);
            }
            
        }
    }
    
    /**
     * This method should be called in render method to be able to render the 
     * graphical user interface.
     * 
     * @param g the Graphics that will render the GUI
     */
    public void renderGUI(Graphics g){
        
        for(int i=0;i<widgets.size();i++){
            
            if(widgets.get(i).isVisible()){
                //must be processed from the most specialized to the most widespread
                
                if(widgets.get(i) instanceof Panel){
                    renderContainer((Panel) widgets.get(i), g);
                }else
                    
                if(widgets.get(i) instanceof CheckBox){
                    renderCheckBox((CheckBox) widgets.get(i), g);
                
                }else

                if(widgets.get(i) instanceof Label){
                    renderLabel((Label) widgets.get(i), g);
                }
                
            }
            
        }
        
    }
    
    /**
     * Specifically renders labels.
     * 
     * @param label the Label that will be rendered
     * @param g the Graphics that will render the Label
     */
    private void renderLabel(Label label, Graphics g){
        
        if(label.getIcon()!=null){
            label.getIcon().draw(label.getX(), label.getY(), label.getWidth(), label.getHeight());
        }else{
            
            if(label instanceof Button)
                if(((Button) label).isHighlight()){
                    g.setColor(Utils.blackOrWhite(label.getBackgroundColor()));
                    g.draw(label);
                }
            if(label instanceof TextField)
                if(((TextField) label).isFocusOwner()){
                    g.setColor(Utils.blackOrWhite(label.getBackgroundColor()));
                    g.draw(label);
                }
            
            if(label.getBackgroundColor()==null)
                g.setColor(Standards.STANDARD_LABEL_BACKGROUND_COLOR);
            else
                g.setColor(label.getBackgroundColor());
            
            //g.draw(label);
            g.fill(label);
            
        }

        if(label.getFont()!=null){
            g.setFont(label.getFont());
        }/*else{
            g.setFont(Label.STANDARD_FONT);
        }*/
        
        if(label.getText()!=null){
            if(label.getForegroundColor()==null)
            g.setColor(Standards.STANDARD_FOREGROUND_COLOR);
        else
            g.setColor(label.getForegroundColor());
            
        renderText(label, g);
        }
        
    }
    
    /**
     * Specifically renders the Panel and all of its children.
     * 
     * @param con the Container that will be rendered
     * @param g the Graphics that will render the Container and all children
     */
    private void renderContainer(Panel panel, Graphics g){
        //if he has a notification of a child, he resizes and removes the notification
        if(panel.isNotification()){
            panel.setSize(panel.getPreferredSize());
            panel.setNotification(false);
        }
        
        g.setColor(panel.getBorderColor());//container border is black
        g.draw(panel);
        
        if(panel.getBackgroundColor()==null){
            g.setColor(Standards.STANDARD_CONTAINER_BACKGROUND_COLOR);
        }
            
        else
            g.setColor(panel.getBackgroundColor());
        
        g.fill(panel);
        
        for(int i=0;i<panel.getComponentsCount();i++){
            //do the same as above, figure out the component type, then render accordingly
            
            if(panel.getComponent(i).isVisible()){
            
                if(panel.getComponent(i) instanceof Panel){
                    renderContainer((Panel) panel.getComponent(i), g);
                }else{

                    if(panel.getComponent(i) instanceof CheckBox){
                        renderCheckBox((CheckBox) panel.getComponent(i), g);
                    }else{

                        if(panel.getComponent(i) instanceof Label){
                            renderLabel((Label) panel.getComponent(i), g);
                        }

                    }

                }
                
            }
            
        }
        
        if(panel instanceof Popup)
            if(((Popup) panel).isWantToClose())
                widgets.remove(panel);
    }
    
    /**
     * Specifically renders check Boxes.
     * 
     * @param cb the CheckBox that will be rendered
     * @param g the Graphics that will render the CheckBox
     */
    private void renderCheckBox(CheckBox cb, Graphics g){
        //renderLabel(cb, g);
        
        //rendering her in herself
        if((cb.getIcon()!=null) || (cb.getIconSelected()!=null)){
            if(cb.isSelected())
                cb.getIcon().draw();
            else
                cb.getIconSelected().draw();
        }else{
            
            if(cb.getBackgroundColor()==null)
                g.setColor(Standards.STANDARD_CHECKBOX_BACKGROUND_COLOR);
            else
                g.setColor(cb.getBackgroundColor());
            
            g.draw(cb);
            g.fill(cb);
            
        }
        
        //rendering the circle and checked
        g.setColor(cb.getCircleColor());
        g.draw(cb.getCircle());
        g.fill(cb.getCircle());

        if(cb.isSelected()){
            g.setColor(cb.getCheckColor());
            g.draw(cb.getCheck());
            g.fill(cb.getCheck());
        }
        
        //rendering the text
        if(cb.getFont()!=null){
            g.setFont(cb.getFont());
        }/*else{
            g.setFont(Label.STANDARD_FONT);
        }*/
        
        if(cb.getText()!=null){
            if(cb.getForegroundColor()==null)
            g.setColor(Standards.STANDARD_FOREGROUND_COLOR);
        else
            g.setColor(cb.getForegroundColor());
            
        renderText(cb, g);
        }
            
            
        
    }
    
    /**
     * Delimits the text of the component so that it does not exceed the limits 
     * of the Component.
     * 
     * @param label the Label (or other Component by inheritance) that the text 
     * will be delimited
     * 
     * @return Component text already delimited
     */
    private String delimiterText(Label label){
        if(label.getFont()!=null){
            if(label.getFont().getWidth(label.getText())>label.getWidth()){

                    String resultingText = "";
                    int i=0;
                    while((label.getFont().getWidth(resultingText)<=label.getWidth()) && (resultingText.length()<=label.getText().length())){
                        resultingText += label.getText().charAt(i);
                        i++;
                    }
                    resultingText = resultingText.substring(0, resultingText.length()-1);//because it runs once more

                    return resultingText;
                }
        }else
            if(font.getWidth(label.getText())>label.getWidth()){//Label.STANDARD_FONT
                
                String resultingText = "";
                int i=0;
                while((font.getWidth(resultingText)<=label.getWidth()) && (resultingText.length()<=label.getText().length())){
                    resultingText += label.getText().charAt(i);
                    i++;
                }
                resultingText = resultingText.substring(0, resultingText.length()-1);//because it runs once more
                
                return resultingText;
            }
            
        return label.getText();
    }
    
    /**
     * Renders text for all components that have.
     * 
     * @param label the component that will be rendered 
     * (can be directly a Label or indirectly, inheriting)
     * @param g the Graphics that will render the Label 
     * (or other Component by inheritance)
     */
    private void renderText(Label label, Graphics g){
        float x = label.getX();
        float y = label.getY();
        
        //for horizontal alignment (I don't need to settle if it's LEFT, because it's already like LEFT)
        if(label.getTextHorizontalAlignment()==Label.CENTER_ALIGNMENT){
            x += label.getHorizontalPadding();
        }else
            if(label.getTextHorizontalAlignment()==Label.RIGHT_ALIGNMENT){
                x += (label.getHorizontalPadding() * 2);
            }
        
        //for vertical alignment (I don't need to settle if it's LEFT, because it's already like LEFT)
        if(label.getTextVerticalAlignment()==Label.CENTER_ALIGNMENT){
            y += label.getVerticalPadding();
        }else
            if(label.getTextVerticalAlignment()==Label.RIGHT_ALIGNMENT){
                y += (label.getVerticalPadding() * 2);
            }
        
        g.drawString(delimiterText(label), x, y);
    }
    
    /**
     * Almost the same as another renderText, 
     * but it considers the checkbox circle to render the text.
     * 
     * @param cb the CheckBox that will render the text
     * @param g the Graphics
     */
    private void renderText(CheckBox cb, Graphics g){
        float x = cb.getX() + ((float) ((cb.getCircle().radius * 2) * CheckBox.distanceCircleEdge));
        float y = cb.getY(); /*+ ((float) ((cb.getCircle().radius * 2) * CheckBox.distanceCircleEdge))*/
        
        //for horizontal alignment (I don't need to settle if it's LEFT, because it's already like LEFT)
        if(cb.getTextHorizontalAlignment()==Label.CENTER_ALIGNMENT){
            x += cb.getHorizontalPadding();
        }else
            if(cb.getTextHorizontalAlignment()==Label.RIGHT_ALIGNMENT){
                x += (cb.getHorizontalPadding() * 2);
            }
        
        //for vertical alignment (I don't need to settle if it's LEFT, because it's already like LEFT)
        if(cb.getTextVerticalAlignment()==Label.CENTER_ALIGNMENT){
            y += cb.getVerticalPadding();
        }else
            if(cb.getTextVerticalAlignment()==Label.RIGHT_ALIGNMENT){
                y += (cb.getVerticalPadding() * 2);
            }
        
        g.drawString(delimiterText(cb), x, y);
    }
    
    /**
     * Returns the number of components, also called widgets that the GUI 
     * (Graphical User Interface) has.
     * 
     * @return he number of components
     */
    public int getWidgetsCount(){
        return widgets.size();
    }
    
}
