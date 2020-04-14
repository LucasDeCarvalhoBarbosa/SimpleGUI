package org.SimpleGUI.Components;

import org.SimpleGUI.Util.Dimension;
import org.SimpleGUI.Util.Standards;
import org.SimpleGUI.Util.Utils;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Vector2f;

/**
 *
 * @author Lucas Barbosa
 */
public class CheckBox extends Button {
    
    protected boolean selected;
    
    protected Circle circle;
    protected Color circleColor;
    
    protected Circle check;
    protected Color checkColor;
    
    protected Image iconSelected;
    
    public static final float distanceCircleEdge = 1.3f;//30% of rightmost and lower radius size
    
    public CheckBox(float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize(x, y, width, height);
        setBackgroundColor(Standards.STANDARD_CHECKBOX_BACKGROUND_COLOR);
    }
    
    public CheckBox(float x, float y, float width, float height, String text){
        super(x, y, width, height);
        initialize(x, y, width, height);
        this.text = replaceAccents(text);
        
        setSize(getPreferredSize());
        setBackgroundColor(Standards.STANDARD_CHECKBOX_BACKGROUND_COLOR);
    }
    
    private void initialize(float x, float y, float width, float height){
        float radius = (float) (smallerSide(width, height) * 0.8) / 2;
        float centerX = (float) (x+(radius * distanceCircleEdge));
        float centerY = (float) (y+(radius * distanceCircleEdge));
        
        circle = new Circle(centerX, centerY, radius);
        check = new Circle(centerX, centerY, (float) (radius  * 0.6));//0.75
    }
    
    private float smallerSide(float side1, float side2){
        if(side1<side2)
            return side1;
        else return side2;
    }
    
    @Override
    public void setX(float x){
        float radius = (float) (smallerSide(width, height) * 0.8) / 2;
        float centerX = (float) (x+(radius * distanceCircleEdge));
        
        circle.setCenterX(centerX);
        check.setCenterX(centerX);
        super.setX(x);
    }
    
    @Override
    public void setY(float y){
        float radius = (float) (smallerSide(width, height) * 0.8) / 2;
        float centerY = (float) (y+(radius * distanceCircleEdge));
        
        circle.setCenterY(centerY);
        check.setCenterY(centerY);
        super.setY(y);
    }
    
    @Override
    public void setLocation(float x, float y){
        float radius = (float) (smallerSide(width, height) * 0.8) / 2;
        float centerX = (float) (x+(radius * distanceCircleEdge));
        float centerY = (float) (y+(radius * distanceCircleEdge));
        
        circle.setCenterX(centerX);
        circle.setCenterY(centerY);
        check.setCenterX(centerX);
        check.setCenterY(centerY);
        super.setLocation(x, y);
    }
    
    @Override
    public void setLocation(Vector2f loc){
        float radius = (float) (smallerSide(width, height) * 0.8) / 2;
        float centerX = (float) (loc.x+(radius * distanceCircleEdge));
        float centerY = (float) (loc.y+(radius * distanceCircleEdge));
        
        circle.setCenterX(centerX);
        circle.setCenterY(centerY);
        check.setCenterX(centerX);
        check.setCenterY(centerY);
        super.setLocation(loc);
    }
    
    @Override
    public boolean isPressed(Input input){
        boolean pressed = super.isPressed(input);
        if(pressed)
            selected = !selected;
        
        return pressed;
    }
    
    @Override
    public void setBackgroundColor(Color color){
        this.backgroundColor = color;
        //The circle color will be 10% lighter by default
        circleColor = new Color((float) (color.r+0.1), (float) (color.g+0.1), (float) (color.b+0.1));
        //The check color will be the inverse color of the circle by default
        checkColor = new Color(Utils.reverseColor(circleColor));
    }
    
    @Override
    public String toString(){
        return "[CheckBox: "+text+" : "+selected+"]";
    }
    
    @Override
    public Dimension getPreferredSize(){
        Dimension newSize = super.getPreferredSize();
//        System.out.println("circle: "+circle);//+" circle.radius: "+circle.radius
        newSize.width = (float) ((newSize.width + (circle.radius * 2)) * 1.1);//plus the diameter and 10% bigger of all
        newSize.height = (float) ((circle.radius * 2) * CheckBox.distanceCircleEdge);
        
        return newSize;
    }
    
    //gets and sets
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public Circle getCheck() {
        return check;
    }

    public void setCheck(Circle check) {
        this.check = check;
    }

    public Color getCircleColor() {
        return circleColor;
    }

    public void setCircleColor(Color circleColor) {
        this.circleColor = circleColor;
    }

    public Color getCheckColor() {
        return checkColor;
    }

    public void setCheckColor(Color checkColor) {
        this.checkColor = checkColor;
    }

    public Image getIconSelected() {
        return iconSelected;
    }

    public void setIconSelected(Image iconSelected) {
        this.iconSelected = iconSelected;
    }
    
}
