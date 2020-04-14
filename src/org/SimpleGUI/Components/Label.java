package org.SimpleGUI.Components;

import org.SimpleGUI.Util.Dimension;
import org.SimpleGUI.Util.Standards;
import java.awt.Font;
import java.text.Normalizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author Lucas Barbosa
 */
public class Label extends Component {
    
    protected String text;
    protected AngelCodeFont font;
    protected Image icon;
    protected Color foregroundColor;
    protected Dimension preferredSize;
    
    protected int textVerticalAlignment = CENTER_ALIGNMENT;
    protected int textHorizontalAlignment = CENTER_ALIGNMENT;
    
    public static int LEFT_ALIGNMENT = 0;
    public static int CENTER_ALIGNMENT = 1;
    public static int RIGHT_ALIGNMENT = 2;
    
//    public static TrueTypeFont STANDARD_FONT = new TrueTypeFont(new Font("Arial", Font.PLAIN, 12), false);
    
    
    public Label(float x, float y, float width, float height) {
        super(x, y, width, height);
        
        setBackgroundColor(Standards.STANDARD_LABEL_BACKGROUND_COLOR);
    }
    
    public Label(Dimension size){
        super(size);
        
        setBackgroundColor(Standards.STANDARD_LABEL_BACKGROUND_COLOR);
    }
    
    public Label(){
        super(0, 0, 0, 0);
        
        this.setSize(getPreferredSize().width, getPreferredSize().height);
        setBackgroundColor(Standards.STANDARD_LABEL_BACKGROUND_COLOR);
    }
    
    public Label(String text){
        super(0, 0, 0, 0);
        
        this.text = replaceAccents(text);
        this.setSize(getPreferredSize().width, getPreferredSize().height);
        setBackgroundColor(Standards.STANDARD_LABEL_BACKGROUND_COLOR);
    }
    
    public Label(Image icon){
        super(0, 0, 0, 0);
        
        this.setSize(getPreferredSize().width, getPreferredSize().height);
        this.icon = icon;
        setBackgroundColor(Standards.STANDARD_LABEL_BACKGROUND_COLOR);
    }
    
    protected String replaceAccents(String str){
        return  Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }
    
    /**
     * return the minimum size your text written in your font if appropriate
     * 
     * @return the preferred size
     */
    @Override
    public Dimension getPreferredSize(){
        if((text!=null) && (font!=null)){
            
            return new Dimension(font.getWidth(text) + (horizontalPadding * 2),
                                font.getHeight(text) + (verticalPadding * 2));
        }
        
        return super.getPreferredSize();
    }
    
    @Override
    public String toString(){
        return "[Label: "+text+"]";
    }
    
    //gets and sets
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = replaceAccents(text);
        this.setSize(getPreferredSize());
    }

    public AngelCodeFont getFont() {
        return font;
    }

    public void setFont(AngelCodeFont font) {
        this.font = font;
        this.setSize(getPreferredSize());
    }

    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
//        if((icon.getWidth()>getWidth()) || (icon.getHeight()>getHeight()))
//            resizeIcon();
    }

    public Color getForegroundColor() {
        return foregroundColor;
    }

    public void setForegroundColor(Color foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    public int getTextVerticalAlignment() {
        return textVerticalAlignment;
    }

    public void setTextVerticalAlignment(int textVerticalAlignment) {
        this.textVerticalAlignment = textVerticalAlignment;
    }

    public int getTextHorizontalAlignment() {
        return textHorizontalAlignment;
    }

    public void setTextHorizontalAlignment(int textHorizontalAlignment) {
        this.textHorizontalAlignment = textHorizontalAlignment;
    }
    
}
