/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursework2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author up728335
 */
public class GraphPanel extends JPanel implements MouseListener, ActionListener, MouseMotionListener, KeyListener, Runnable{
    
    
     private boolean go = false;
    private int xpos, ypos, oldx, oldy;
    private MouseEvent dave;
    private BufferedImage paintImage = new BufferedImage(10000, 10000, BufferedImage.TYPE_3BYTE_BGR);
    private Color david = Color.black;
    private Thread thread;
    
    public GraphPanel(){
        Timer time = new Timer(5, this);
        time.start();
        clear();
        setFocusable(true);
        requestFocus();
    }
    
    @Override
    public void paintComponent(Graphics g) {
            g.drawImage(paintImage, 0, 0, null);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
            
    }
    
    public void updatePaint(){
        Graphics g = paintImage.createGraphics();
        
        g.setColor(david);
        g.fillOval(xpos, ypos, 5, 5);
        oldx = xpos;
        oldy = ypos;
        

        g.dispose();
        // repaint panel with new modified paint
        repaint();
    }
    
    public void clear(){
        Graphics g = paintImage.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, 700, 700);
        g.dispose();
        // repaint panel with new modified paint
        repaint();
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == 3 ){clear();}
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == 1){
        go = true;
        dave = e;
        }
         if(e.getButton() == 3 ){clear();}
         if(e.getButton() == 2 ){
             try {
             save();
            } catch (IOException ex) {
                Logger.getLogger(GraphPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        go = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(go){
        try{
            
        xpos = MouseInfo.getPointerInfo().getLocation().x - 10;
        ypos = MouseInfo.getPointerInfo().getLocation().y - 30;
        updatePaint();
            
        }
        catch(NullPointerException en){
        }
        
        
    }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        xpos = MouseInfo.getPointerInfo().getLocation().x - 10;
        ypos = MouseInfo.getPointerInfo().getLocation().y - 30;
        updatePaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        xpos = MouseInfo.getPointerInfo().getLocation().x - 10;
        ypos = MouseInfo.getPointerInfo().getLocation().y - 30;
        updatePaint();
    }
    
    public void save() throws IOException{
        File aFile = new File("images/image.png");
        aFile = getFile(aFile, 0);
        ImageIO.write(paintImage, "PNG", aFile);
    }
    
    private File getFile(File file,int num){
        
        if(file.exists()){return getFile(new File("images/image" + num + ".png"), num+1);}
        else{return file;}
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_R){david = Color.RED;}
        else if(e.getKeyCode() == KeyEvent.VK_B){david = Color.BLACK;}
        else if(e.getKeyCode() == KeyEvent.VK_G){david = Color.GREEN;}
        else if(e.getKeyCode() == KeyEvent.VK_P){david = Color.PINK;}
        else if(e.getKeyCode() == KeyEvent.VK_O){david = Color.ORANGE;}
        else if(e.getKeyCode() == KeyEvent.VK_M){david = Color.MAGENTA;}
        else if(e.getKeyCode() == KeyEvent.VK_L){david = Color.BLUE;}
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
        
    }

    @Override
    public void run() {
        
    }
    
     @Override
    public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}
}
