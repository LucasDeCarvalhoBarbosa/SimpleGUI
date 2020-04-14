package org.SimpleGUI.Components;

import org.SimpleGUI.Util.Dimension;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

/**
 *
 * @author Lucas Barbosa
 */
public abstract class Component extends Rectangle {
    
    protected Panel parent;
    
    protected boolean visible = true;//not to render (listeners will be disabled too)
    protected boolean enabled = true;//not to enable listeners
    
    //protected boolean dynamicallyChanges = true;//to change size when changing font or when a child is moved or resized
    protected boolean mutable = true;//if true, can move and change size
    protected boolean notifyingChanges = true;//to notify the parent that they have made a change
    
    protected Color backgroundColor = Color.lightGray;
    
    protected float horizontalPadding = 10;
    protected float verticalPadding = 10;
    
    public Component(float x, float y, float width, float height) {
        super(x, y, width, height);
    }
    
    public Component(Dimension size){
        super(0, 0, size.width, size.height);
    }
    
    /**
     * Returns true if mouse is over this component.
     * 
     * @param input the input
     * @return true if mouse is over this component
     */
    public boolean isMouseOver(Input input){
        int posX = input.getMouseX();
        int posY = input.getMouseY();
        
        boolean dentroAreaX = (posX>x) && (posX<x+width);
        boolean dentroAreaY = (posY>y) && (posY<y+height);
        
        return (dentroAreaX && dentroAreaY);
    }
    
    /**
     * Returns true if the mouse is over this component and is pressed.
     * 
     * @param input the input
     * @return true if the mouse is over this component and is pressed
     */
    public boolean isPressed(Input input){
        if(enabled){
            
            if(isMouseOver(input)){
                if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
                    return true;
                }
            }
            
        }
        
        
        return false;
    }
    
    /**
     * returns true if the mouse is over this component and is pressed.
     * 
     * @param input the input
     * @param mouseButton the mouse button code
     * @return true if the mouse is over this component and is pressed
     */
    public boolean isPressed(Input input, int mouseButton){
        if(enabled){
            
            if(isMouseOver(input)){
                if(input.isMousePressed(mouseButton)){
                    return true;
                }
            }
            
        }
        
        return false;
    }
    
    /**
     * Return the minimun size of this component.
     * 
     * @return the minimun size of this component
     */
    public Dimension getPreferredSize(){
        return new Dimension(100, 30);//10, 10
    }
    
    /**
     * This method applies the position and size of the component 
     * relatively (in%).
     * If this component has no parent, its position and size are 
     * relative to the window(global).
     * Otherwise, if this component has a parent, its position and size are 
     * relative to it (local).
     * note: all parameters must be between 0 and 1.
     * 
     * @param XPercent the position of the X coordinate relative to the window or its parent (if any)
     * @param YPercent the position of the Y coordinate relative to the window or its parent (if any)
     * @param widthPercent the WIDTH of the component relative to the window or its parent.
     * @param heightPercent the HEIGHT of the component relative to the window or its parent.
     * @param gc the game container (the window)
     */
    public void setRelativeLocationAndSize(float XPercent, float YPercent, float widthPercent, float heightPercent, GameContainer gc){
        XPercent = normalizeParameter(XPercent);
        YPercent = normalizeParameter(YPercent);
        widthPercent = normalizeParameter(widthPercent);
        heightPercent = normalizeParameter(heightPercent);
       
        if(haveParent()){//if you have a parent then the values will be relative to him
            
            float parentRelativeX = parent.getX() + (parent.getWidth() * XPercent);
            float parentRelativeY = parent.getY() + (parent.getHeight() * YPercent);
            float parentRelativeWidth = parent.getWidth() * widthPercent;
            float parentRelativeHeight = parent.getHeight() * heightPercent;
            
            setX(parentRelativeX);
            setY(parentRelativeY);
            setWidth(parentRelativeWidth);
            setHeight(parentRelativeHeight);
            
        }else{//if you have no parent then the values will be relative to the window
            
            float windowRelativeX = 0 + (gc.getWidth() * XPercent);//because the window starts at 0 of x
            float windowRelativeY = 0 + (gc.getHeight() * YPercent);//because the window starts at 0 of y
            float windowRelativeWidth = gc.getWidth() * widthPercent;
            float windowRelativeHeight = gc.getHeight() * heightPercent;
            
            setX(windowRelativeX);
            setY(windowRelativeY);
            setWidth(windowRelativeWidth);
            setHeight(windowRelativeHeight);
        }
    }
    
    /**
     * This method applies the position of the component 
     * relatively (in %).
     * If this component has no parent, its position and size are 
     * relative to the window(global).
     * Otherwise, if this component has a parent, its position and size are 
     * relative to it (local).
     * note: all parameters must be between 0 and 100.
     * 
     * @param XPercent he position of the X coordinate relative to the window or its parent (if any)
     * @param YPercent the position of the Y coordinate relative to the window or its parent (if any)
     * @param gc the game container (the window)
     */
    public void setLocationRelative(float XPercent, float YPercent, GameContainer gc){
        XPercent = normalizeParameter(XPercent);
        YPercent = normalizeParameter(YPercent);
        
        if(haveParent()){//if you have a parent then the values will be relative to him
            
            float parentRelativeX = parent.getX() + (parent.getWidth() * XPercent);
            float parentRelativeY = parent.getY() + (parent.getHeight() * YPercent);
            
            setX(parentRelativeX);
            setY(parentRelativeY);
            
        }else{//if you have no parent then the values will be relative to the window
            
            float windowRelativeX = 0 + (gc.getWidth() * XPercent);//because the window starts at 0 of x
            float windowRelativeY = 0 + (gc.getHeight() * YPercent);//because the window starts at 0 of y
            
            setX(windowRelativeX);
            setY(windowRelativeY);
        }
    }
    
    /**
     * Centers this component horizontally.
     * 
     * @param gc the game container
     */
    public void centerHorizontally(GameContainer gc){
        if(parent!=null){
            setX((gc.getWidth() / 2) - (this.width / 2) - parent.getX());
        }else{
            setX((gc.getWidth() / 2) - (this.width / 2));
        }
    }
    
    /**
     * Centers this component vertically.
     * 
     * @param gc the game container
     */
    public void centerVertically(GameContainer gc){
        if(parent!=null){
            setY(parent.getY() + (gc.getHeight() / 2) - (this.height / 2));
        }else{
            setY((gc.getHeight() / 2) - (this.height /  2));
        }
    }
    
    /**
     * Center this component in the center of your parent container, and if not, 
     * in the center of the screen.
     * 
     * @param gc the game container
     */
    public void centralize(GameContainer gc){
        centerHorizontally(gc);
        centerVertically(gc);
    }
    
    /**
     * This method applies the size of the component 
     * relatively (in%).
     * If this component has no parent, its position and size are 
     * relative to the window(global).
     * Otherwise, if this component has a parent, its position and size are 
     * relative to it (local).
     * note: all parameters must be between 0 and 1, or between 1 and 100.
     * 
     * @param widthPercent the WIDTH of the component relative to the window or its parent.
     * @param heightPercent the HEIGHT of the component relative to the window or its parent.
     * @param gc the game container (the window)
     */
    public void setSizeRelative(float widthPercent, float heightPercent, GameContainer gc){
        widthPercent = normalizeParameter(widthPercent);
        heightPercent = normalizeParameter(heightPercent);
        
        if(haveParent()){//if you have a parent then the values will be relative to him
            
            float parentRelativeWidth = parent.getWidth() * widthPercent;
            float parentRelativeHeight = parent.getHeight() * heightPercent;
            
            setWidth(parentRelativeWidth);
            setHeight(parentRelativeHeight);
            
        }else{//if you have no parent then the values will be relative to the window
            
            float windowRelativeWidth = gc.getWidth() * widthPercent;
            float windowRelativeHeight = gc.getHeight() * heightPercent;
            
            setWidth(windowRelativeWidth);
            setHeight(windowRelativeHeight);
        }
    }
    
    /**
     * normalizes the parameter leaving it between 0 and 1
     * 
     * @param f the parameter that will be normalized
     * @return the normalized parameter
     */
    private float normalizeParameter(float f){
        if(f>=0 && f<=100)//between 1 and 100
            return f / 100;
        if(f<0)
            return 0;
        if(f>1)
            return 1;
        
        return f;
    }
    
    /**
     * Return the X coordinate relative to your parent.
     * 
     * @return the X coordinate relative to your parent
     */
    private float xRelativeToParent(){
        return x - parent.getX();
    }
    
    /**
     * Return the Y coordinate relative to your parent.
     * 
     * @return the Y coordinate relative to your parent
     */
    private float yRelativeToParent(){
        return y- parent.getY();
    }
    
    /**
     * return true if it has a parent, and otherwise returns false.
     * 
     * @return true if it has a parent, and otherwise returns false
     */
    public boolean haveParent(){
        return parent!=null;
    }
    
    /**
     * Notify your father about some of your actions. Ex: move or change size.
     * 
     */
    private void notifyParent(){
        if((notifyingChanges) && (haveParent()))
            if(parent.isMutable())//only sends notification if parent dynamically changes
                parent.setNotification(true);
    }
    
    /**
     * Returns the representation of this Object in the form of a String 
     * (usually used for testing)
     * 
     * @return the representation of this Object in the form of a String
     */
    @Override
    public String toString(){
        return getClass().getName()+"@"+Integer.toHexString(hashCode());
    }
    
    /**
     * Assigns the X position of the Component 
     * (the X axis is the axis that changes the Component to the left and right).
     * 
     * @param x the X position 
     */
    @Override
    public void setX(float x){
        if(mutable){
            super.setX(x);
            notifyParent();
        }
    }
    
    /**
     * Assigns the Y position of the Component 
     * (the Y axis is the axis that changes the Component further up and down).
     * 
     * @param y the Y position
     */
    @Override
    public void setY(float y){
        if(mutable){
            super.setY(y);
            notifyParent();
        }
    }
    
    /**
     * Assigns the point (X and Y coordinates in the form of a Vector2f) 
     * that the Component must be
     * 
     * @param loc the Vector2f that represents the point
     */
    @Override
    public void setLocation(Vector2f loc){
        if(mutable){
            super.setLocation(loc);
            notifyParent();
        }
    }
    
    /**
     * Assigns the point (X and Y coordinates) that the Component should be.
     * 
     * @param x the X position
     * @param y the Y position
     */
    @Override
    public void setLocation(float x, float y){
        if(mutable){
            super.setLocation(x, y);
            notifyParent();
        }
    }
    
    /**
     * Returns the Vector2f location of the Component.
     * 
     * @return the Vector2f location of the Component
     */
    @Override
    public Vector2f getLocation(){
        return new Vector2f(x, y);
    }
    
    /**
     * Assign Component size (first width, then height).
     * 
     * @param width the width assigned to the Component
     * @param height the height assigned to the Component
     */
    @Override
    public void setSize(float width, float height){
        if(mutable){
            super.setSize(width, height);
            notifyParent();
        }
    }
    
    /**
     * Assign the size of the Component by passing a Dimension.
     * 
     * @param size size of the Component in the form of a Dimension
     */
    public void setSize(Dimension size){
        if(mutable){
            super.setSize(size.width, size.height);
            notifyParent();
        }
    }
    
    /**
     * Returns the size of the Component in the form of a Dimension.
     * 
     * @return the size of the Component
     */
    public Dimension getSize(){
        return new Dimension(width, height);
    }
    
    //gets and sets
    public Panel getParent() {
        return parent;
    }

    public void setParent(Panel parent) {
        this.parent = parent;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        this.enabled = visible;//because if it becomes invisible it will also be disabled
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isMutable() {
        return mutable;
    }

    public void setMutable(boolean mutable) {
        this.mutable = mutable;
    }

    public boolean isNotifyingChanges() {
        return notifyingChanges;
    }

    public void setNotifyingChanges(boolean notifyingChanges) {
        this.notifyingChanges = notifyingChanges;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public float getVerticalPadding() {
        return verticalPadding;
    }

    public void setVerticalPadding(float verticalPadding) {
        this.verticalPadding = verticalPadding;
    }

    public float getHorizontalPadding() {
        return horizontalPadding;
    }

    public void setHorizontalPadding(float horizontalPadding) {
        this.horizontalPadding = horizontalPadding;
    }
    
}
