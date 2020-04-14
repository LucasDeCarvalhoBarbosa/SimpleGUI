package org.SimpleGUI.Util;

import org.newdawn.slick.Color;

/**
 *
 * @author Lucas Barbosa
 */
public class Utils {
    
    public static Color reverseColor(Color color){
        return new Color(1-color.r, 1-color.g, 1-color.b);
    }
    
    public static Color blackOrWhite(Color color){
        float rgb = color.r + color.g + color.b;
        
        float differenceToBlack = Math.abs(0 - rgb);//because the sum of rgb to black on scale 0-1 would be 0
        float differenceToWhite = Math.abs(3 - rgb);//because the sum of rgb to white on scale 0-1 would be 3
        
        if(differenceToBlack < differenceToWhite){
            return Color.white;
        }else{
            return Color.black;
        }      
            
    }
    
}
