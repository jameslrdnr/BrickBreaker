/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brickbreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ge29779
 */
public class HighScoresScreen extends AbstractScreen implements Comparator<String>{

    //Constants
    //------------------------------------------------------------------
    private final int maxNameSize = 6;
    private final String fileName = "src\\assets\\HighScoresFile";
    
    //Variables
    //------------------------------------------------------------------
    private int currentScore;
    private boolean typing;
    private String playerName;
    private ArrayList<String> scoresList;
    private BufferedImage background;
            
    //Default constructor called from menu and only displays previous scores
    //------------------------------------------------------------------
    public HighScoresScreen(){
        super();
        typing = false;
        scoresList = new ArrayList<String>();
        addScores();
    }
    
    //Constructor with score called when coming out of play, need to enter a name
    //------------------------------------------------------------------
    public HighScoresScreen(int currentScore){
        super();
        typing = true;
        playerName = "";
        this.currentScore = currentScore;
        scoresList = new ArrayList<String>();
        addScores();
    }
    
    //Not used
    //------------------------------------------------------------------
    @Override
    public void runLogic() {}

    @Override
    public void drawGame(Graphics g) {
        
        //Make a black background to draw onto
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        g.setColor(Color.WHITE);
        Font font =  new Font("Garamond", Font.PLAIN, 24);
        g.setFont(font);
        
        
        if(typing){            
            //prompt player to input name
            drawCenteredString(g, "What is your name?", getWidth() / 2, getHeight() / 4, font);
            
            //Draw playerName
            drawCenteredString(g, playerName, getWidth() / 2, getHeight() / 2, font);
        }
        
        else{
            
            //Draw top 10 scores
            for(int i = 0; i < 10 && i < scoresList.size(); i++){
                g.drawString((i + 1) + ". " + scoresList.get(i), getWidth() / 3, (getHeight() / 2) + (i * g.getFont().getSize()));
            }
        }
    }

    
    
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    //Handles name input
    //------------------------------------------------------------------
    @Override
    public void keyReleased(KeyEvent e) {
        //System.out.print("Key Typed");
        int key = e.getKeyCode();
        
        //Except input if typing
        //------------------------------------------------------------------
        if(typing){
            if(key == KeyEvent.VK_BACK_SPACE){
                playerName = playerName.substring(0, playerName.length()-1);
//              if(debug){
//                  System.out.println("BackSpace");
//              }
            }
            else if(key == KeyEvent.VK_ENTER){
                typing = false;
                scoresList.add(currentScore + " " + playerName);
                writeScores();
                
//              if(debug){
//                  System.out.println("Enter");
//              }
            }
            else if(Character.isLetterOrDigit(key) && playerName.length() < maxNameSize){
                playerName += (char)key;

//              if(debug){
//                  System.out.println("" + (char)key);
//              }                
            }
        }
        if(key == KeyEvent.VK_F10){
            //enter playgame with or without saving
            
            
//              if(debug){
//                  System.out.println("" + (char)key);
//              }  
            
        }
        if(key == KeyEvent.VK_ESCAPE){
            //enter mainMenu with or without saving\
            
            
//              if(debug){
//                  System.out.println("" + (char)key);
//              }  
        }
    }
    
    //Sorts arrayList of scores
    //------------------------------------------------------------------
    public void sortScores(){
        scoresList.sort(this);
        Collections.reverse(scoresList);
    }

    //Compares strings of score/name combos and compares by score
    //------------------------------------------------------------------
    @Override
    public int compare(String o1, String o2) {
        if(Integer.parseInt(o1.substring(0, o1.indexOf(' '))) > Integer.parseInt(o2.substring(0, o1.indexOf(' '))) ){
            return 1;
        }
        else if(Integer.parseInt(o1.substring(0, o1.indexOf(' '))) == Integer.parseInt(o2.substring(0, o1.indexOf(' '))) ){
            if(o1.substring(o1.indexOf(' '), o1.length()).compareTo(o2.substring(o2.indexOf(' '), o2.length())) > 0)
                return -1;
            else if(o1.substring(o1.indexOf(' ')).compareTo(o2.substring(o2.indexOf(' '))) < 0)
                return 1;
            else
                return 0;
        }
        else if(Integer.parseInt(o1.substring(0, o1.indexOf(' '))) < Integer.parseInt(o2.substring(0, o1.indexOf(' '))) ){
            return -1;
        }
        else{
            throw new Error("compare has failed with o1 - " + o1 + " and o2 - " + o2);
        }
    }

    //Read scores in and add them to scoresList
    //------------------------------------------------------------------
    private void addScores() {
        
        //Uses declared final name
        
        //Reader will access one line at a time
        String line = null;
        
        try{
            //Generic Reader
            FileReader fileReader = new FileReader(fileName);
            
            //Supposed to always use BufferedReader
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            //Add each line to scoresList
            while((line = bufferedReader.readLine()) != null){
                scoresList.add(line);
            }
            
            //Close the file
            bufferedReader.close();
        }
        catch(FileNotFoundException e){
            System.out.println("'" + fileName + "' " + "not found");
        }
        catch(IOException e){
            System.out.println("Failed to read file : " + fileName);
        }
    }
    
    //Write scoresList to the HighScoresFile
    //------------------------------------------------------------------
    private void writeScores(){
        
        //Sort scores before writing
        sortScores();
        
        //Uses declared file name
        
        try{
            //Generic Writer
            FileWriter fileWriter = new FileWriter(fileName);
            
            //Supposed to use BufferedWriter
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                        
            //Write each string in scoresList to a line
            for(String current : scoresList){
                bufferedWriter.write(current);
                bufferedWriter.newLine();
            }
            
            //Close the file
            bufferedWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(HighScoresScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void handleInput(ArrayList<KeyEvent> inputList) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
