/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screenObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 *
 * @author JamesLaptop
 */
public class MenuSelectorIcon extends AbstractScreenObject {
    
    private boolean blinking;
    private int blinkTimer;
    private int blinkTime = 15;
    
    public MenuSelectorIcon(int x, int y, int width, int height, int maxPosition, Color color, boolean blinking, boolean acceptingInput){
        
        super(x, y, width, height, false, false);
        
        this.blinking = blinking;
        
        setAcceptingInput(acceptingInput);
        
        setColor(color);
        
        setyMovementMultiplier(20);
        setxMovementMultiplier(20);
        
        setPosition(0);
        setMaxPosition(maxPosition);
        
        
    }
    
    //logic method
    @Override
    public void runLogic(){
        
        //blinks the selector if blinking == true
        if(blinking){
            if(blinkTimer >= blinkTime){
                blinkTimer = 0;
                setIsVisible(!getIsVisible());
            }
            else
                blinkTimer ++;
        }
        
    }

    //move method
    @Override
    public void move() {
        
        setX(getX() + getDeltaX());
        setY(getY() + getDeltaY());
        
    }

    //draw method
    @Override
    public void drawObject(Graphics g) {
        
        g.fillRect((int)getX(), (int)getY(), getWidth(), getHeight());
        
    }
    
    //handle input method
    @Override
    public void handleInput(String inputMethod, KeyEvent ke){
        
        switch(inputMethod){
            
            //default input handling
            case "default" :
                //handle arrowkey input
                switch(ke.getKeyCode()){
                    
                    case KeyEvent.VK_DOWN :
                        //makes sure it will be in bounds
                        if(getPosition() < getMaxPosition()){
                            moveY(+1);
                            setPosition(getPosition() + 1);
                            setIsVisible(true);
                            blinkTimer = 0;
                        }
                        break;
                    case KeyEvent.VK_UP:
                        //makes sure it will be in bounds
                        if(getPosition() > 0){
                            moveY(-1);
                            setPosition(getPosition() - 1);
                            setIsVisible(true);
                            blinkTimer = 0;
                        }
                        break;
                    
                }
            
        }
        
    }
    
    //getter/setter methods
    public boolean getBlinking(){
        return blinking;
    }
    
    public int getBlinkTime(){
        return blinkTime;
    }
    
    public void setBlinking(boolean blink){
        blinking = blink;
    }
    
    public void setBlinkTime(int t){
        blinkTime = t;
    }
    
}
