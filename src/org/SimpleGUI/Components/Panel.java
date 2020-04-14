package org.SimpleGUI.Components;

import java.util.ArrayList;
import java.util.List;
import org.SimpleGUI.Util.Dimension;
import org.SimpleGUI.Util.Standards;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;

/**
 *
 * @author Lucas Barbosa
 */
public class Panel extends Component {//is not dealing well with not visible children (ex: preferredSize)
    
    protected List<Component> components = components = new ArrayList<>();;
    protected int alignment = VERTICAL_ALIGNMENT;
    protected float componentSpacing = 10;
    protected boolean notification = false;
    protected Color borderColor;
    
    public static final int HORIZONTAL_ALIGNMENT = 0;
    public static final int VERTICAL_ALIGNMENT = 1;
    
    public Panel(float x, float y, float width, float height) {
        super(x, y, width, height);
        setBackgroundColor(Standards.STANDARD_CONTAINER_BACKGROUND_COLOR);
    }
    
    public Panel(Dimension size){
        super(size);
        setBackgroundColor(Standards.STANDARD_CONTAINER_BACKGROUND_COLOR);
    }
    
    /**
     * reposition the component according to alignment
     * 
     * @param c the component that will be repositioned
     */
    private void rearrangeComponent(Component c){
        if(alignment==HORIZONTAL_ALIGNMENT)
            rearrangeHorizontal(c);
        else
            rearrangeVertical(c);
    }
    
    /**
     * reposition the component vertically.
     * 
     * @param c the component that will be repositioned
     */
    private void rearrangeHorizontal(Component c){
        //if(isOutside(c)){
            
            if(isThefirst(c)){//if this is the first component
                c.setLocation(x+horizontalPadding, y+verticalPadding);
            }else{//if he is not the first
                
                Component previous = getPenultimate();
                c.setLocation(previous.getX() + previous.getWidth() + componentSpacing, previous.getY());
                
            }
            
        //}
    }
    
    /**
     * reposition the component horizontally.
     * 
     * @param c the component that will be repositioned
     */
    private void rearrangeVertical(Component c){
        //if(isOutside(c)){
            
            if(isThefirst(c)){//if this is the first component
                c.setLocation(x+horizontalPadding, y+verticalPadding);
            }else{//if he is not the first
                
                Component previous = getPenultimate();
                c.setLocation(previous.getX(), previous.getY() + previous.getHeight() + componentSpacing);
                
            }
            
        //}
    }
    
    /**
     * Returns true if the passed component is the first one added.
     * 
     * @param c the component that will be checked
     * @return true if the passed component is the first one added
     */
    private boolean isThefirst(Component c){
        return c.equals(components.get(0));
    }
    
    /**
     * returns true if the parameter component is outside this container, otherwise false.
     * 
     * @param c
     * @return 
     */
    public boolean isOutside(Component c){
        return (isUp(c) || (isDown(c) || (isLeft(c) || (isRight(c)))));
    }
    
    private boolean isUp(Component c){
        return (c.getY()<y);
    }
    
    private boolean isDown(Component c){
        return (c.getY()>y+height);
    }
    
    private boolean isLeft(Component c){
        return (c.getX()<x);
    }
    
    private boolean isRight(Component c){
        return (c.getX()>x+width);
    }
    
    /**
     * add component to container.
     * 
     * @param c the component that will be added
     */
    public void add(Component c){
        components.add(c);
        c.setParent(this);
        setSize(getPreferredSize());
        
        rearrangeComponent(c);
    }
    
    /**
     * Removes the specified component.
     * 
     * @param c the specified component that will be removed
     * @return the removed component
     */
    public Component remove(Component c){
        if(components.contains(c)){
            components.remove(c);
            c.setParent(null);
            setSize(getPreferredSize());
            
            return c;
        }
        
        return null;
    }
    
    /**
     * Removes the component with the indicated index.
     * 
     * @param i the index of the component to be deleted
     * @return the removed component
     */
    public Component remove(int i){
        if(components.isEmpty()){
            return null;
        }
        
        Component c = components.remove(i);
        c.setParent(null);
        setSize(getPreferredSize());
        
        return c;
    }
    
    /**
     * returns the number of components this container has.
     * 
     * @return number of components this container has
     */
    public int getComponentsCount(){
        return components.size();
    }
    
    /**
     * returns true if you have the child, and otherwise, returns false.
     * 
     * @param c the Child
     * @return true if you have the child, and otherwise, returns false.
     */
    public boolean haveChild(Component c){
        return components.contains(c);
    }
    
    /**
     * Returns the minimum size that fits all of its components according to their alignment.
     * 
     * @return the preferred size
     */
    @Override
    public Dimension getPreferredSize(){
        if(components.isEmpty())
            return getSize();
        
        float preferredWidth = this.horizontalPadding * 2;
        float preferredHeight = this.verticalPadding * 2;
        
        if(alignment==HORIZONTAL_ALIGNMENT){
            /*if it is horizontal alignment, the preferred width would be
            the padding plus the sum of the width of all components plus 
            the total space between them.
            And the preferred height would be the highest component plus the padding times two.*/
            
            for(int i=0;i<components.size();i++){
                preferredWidth += components.get(i).getWidth();
            }
            preferredWidth += totalComponentSpacing();
            
            preferredHeight = highestComponent().getHeight() + (verticalPadding * 2);
            
            return new Dimension(preferredWidth, preferredHeight);
        }else {
            /*if it is vertical alignment, the preferred height would be
            padding plus the sum of the height of all components plus
            the total space between them.
            And the preferred width would be the wider component plus the padding times two.*/
            
            for(int i=0;i<components.size();i++){
                preferredHeight += components.get(i).getHeight();
            }
            preferredHeight += totalComponentSpacing();
            
            preferredWidth = widerComponent().getWidth() + (horizontalPadding * 2);
            
            return new Dimension(preferredWidth, preferredHeight);
        }
    }
    
    /**
     * returns the widest component of the container.
     * 
     * @return the widest component of the container
     */
    private Component widerComponent(){//the widest component
        if(components.isEmpty())
            return null;
        
        Component widerComponent = components.get(0);
        
        for(int i=0;i<components.size();i++){
            if(components.get(i).getWidth()>widerComponent.getWidth())
                widerComponent = components.get(i);
        }
        
        return widerComponent;
    }
    
    /**
     * returns the highest component of the container.
     * 
     * @return the highest component of the container
     */
    private Component highestComponent(){//the component with the highest height
        if(components.isEmpty())
            return null;
        
        Component highestComponent = components.get(0);
        
        for(int i=0;i<components.size();i++){
            if(components.get(i).getHeight()>highestComponent.getHeight())
                highestComponent = components.get(i);
        }
        
        return highestComponent;
    }
    
    /**
     * returns the sum of the spacings of all components of this container.
     * 
     * @return the sum of the spacings
     */
    private float totalComponentSpacing(){
        return (componentSpacing * components.size()) - 1;//the total size of all spaces between components
    }
    
    /**
     * Put x and y to change position.
     * If you have children, they should follow you.
     * 
     * @param x the x coordinate
     * @param y the y coordinate
     */
    @Override
    public void setLocation(float x, float y){
        float oldX = this.x;
        float oldY = this.y;

        super.setLocation(x, y);

        float differenceX = x - oldX;
        float differenceY = y - oldY;

        if(!components.isEmpty()){//if you have children, they should follow
            for(int i=0;i<components.size();i++){
                components.get(i).setLocation(components.get(i).getX()+differenceX, components.get(i).getY()+differenceY);
            }
        }
    }
    
    @Override
    public void centerHorizontally(GameContainer gc){
        super.centerHorizontally(gc);
        for(int i=0;i<components.size();i++){
            rearrangeComponent(components.get(i));
        }
    }
    
    @Override
    public void centerVertically(GameContainer gc){
        super.centerVertically(gc);
        for(int i=0;i<components.size();i++){
            rearrangeComponent(components.get(i));
        }
    }
    
    @Override
    public void centralize(GameContainer gc){
        super.centralize(gc);
        for(int i=0;i<components.size();i++){
            rearrangeComponent(components.get(i));//not exactly but it works well
        }
    }
    
    /**
     * Returns the penultimate component of this container.
     * 
     * @return the penultimate component of this container
     */
    public Component getPenultimate(){
        return components.get(components.size()-2);
    }
    
    /**
     * When the container is invisible, all of its components should be invisible as well, and of course, disabled. 
     * 
     * @param visible if you want the container to be visible or not
     */
    @Override
    public void setVisible(boolean visible){
        super.setVisible(visible);
        for(int i=0;i<components.size();i++){
            components.get(i).setVisible(visible);
        }
    }
    
    @Override
    public String toString(){
        String s = "[Conatiner ("+components.size()+"): {";
        for(int i=0;i<components.size();i++){
            s += components.get(i)+", ";
        }
        s += "}]";
        
        return s;
    }
    
    //gets and sets
    public Component getComponent(int i){
        return components.get(i);
    }

    public int getAlignment() {
        return alignment;
    }

    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }

    public float getComponentSpacing() {
        return componentSpacing;
    }

    public void setComponentSpacing(float componentSpacing) {
        this.componentSpacing = componentSpacing;
    }

    public boolean isNotification() {
        return notification;
    }

    public void setNotification(boolean notification) {
        this.notification = notification;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }
    
}
