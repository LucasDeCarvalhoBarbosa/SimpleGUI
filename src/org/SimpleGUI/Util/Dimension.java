package org.SimpleGUI.Util;

/**
 *
 * @author Lucas Barbosa
 */
public class Dimension {
    
    public float width;
    public float height;
    
    public Dimension(float width, float height){
        this.width = width;
        this.height = height;
    }
    
    @Override
    public String toString(){
        return "Dimension: (width: "+width+", height: "+height+")";
    }
    
}
