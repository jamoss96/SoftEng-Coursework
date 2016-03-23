 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Dimension;
import java.util.ArrayList;


/**
 *
 * @author up728335
 */
public class DragDrawPanel extends JPanel implements MouseListener, ActionListener, MouseMotionListener, KeyListener{
    
    private boolean go = false;
    private int xpos, ypos, oldx, oldy;
    private MouseEvent dave;
    private BufferedImage paintImage;
    private Color david = Color.black;
    public Dimension size;
    public ArrayList<DataNode> nodes;
    private DataNode ken;
    private DataNode ken2;
    public NewJFrame infoFrame;
    private int created;
    private int wbtParentnum;
    private int tempwbtParent;
    
    public DragDrawPanel(Dimension screenSize){
        Timer time = new Timer(5, this);
        time.start();
        nodes = new ArrayList<DataNode>();
        ken = new DataNode(560, 300, "Dave", false);
        ken2 = new DataNode(560, 300, "Ken", true);
        nodes.add(ken);
        nodes.add(ken2);
        System.out.println(nodes);
        paintImage = new BufferedImage(screenSize.width, screenSize.height, BufferedImage.TYPE_3BYTE_BGR);
        size = screenSize;
        clear();
        infoFrame = new NewJFrame(nodes);
        
        
    }
    
    @Override
    public void paintComponent(Graphics g) {
            g.drawImage(paintImage, 0, 0, null);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
            g.drawString("Press left mouse Button to paint, right to clear and middle to save to file", 0, 12);
    }
    
    public void updatePaint(){
        Graphics g = paintImage.createGraphics();
        
        g.setColor(david);
        
        oldx = xpos;
        oldy = ypos;
        
        for(DataNode node:nodes){
          g.drawRect(node.x, node.y, 100, 25);
          g.drawString(node.name, node.x+25, node.y+25);
      }
       getwbtParent();
        System.out.println(wbtParentnum);
        
       
        tempwbtParent = wbtParentnum;
        
         for(DataNode node:nodes){
           if (node.wbtParent) {
           System.out.println("wbtPrint");
          g.drawRect((size.width/(wbtParentnum+1))*tempwbtParent-100, 250, 150, 50);
          g.drawString(node.name, (size.width/(wbtParentnum+1))*tempwbtParent-35, 275);
          tempwbtParent--;
           }
         } 
         
         
        System.out.println("sdfas3");
        for(DataNode node:nodes){
          System.out.println("Noode print");
          g.drawRect(node.x, node.y, 150, 50);
          g.drawString(node.name, node.x+15, node.y+25);
      }
        g.dispose();
        // repaint panel with new modified paint
        repaint();
    
        System.out.println(infoFrame.created);
        System.out.println(created);
        if(created != infoFrame.created){
          System.out.println(infoFrame.going);
          if(infoFrame.going == false){
            System.out.println("going");
            gettingInfo();
            created = infoFrame.created;
          }
        }
    }
    
    public void newNode(){}
    
    public void clear(){
        Graphics g = paintImage.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, size.width, size.height);
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
                Logger.getLogger(DragDrawPanel.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public ArrayList<DataNode> getNodes() {System.out.println(nodes); return nodes;}

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
    
    public void setNodes(ArrayList<DataNode> someNodes){
     nodes = someNodes;
     System.out.println(nodes);
    }
    
     private void gettingInfo(){
      System.out.println("going");
      nodes.add(new DataNode(300, 300, infoFrame.getName(), infoFrame.getwbtParent()));
    }
     
     public void newTask(){
      infoFrame.setVisible(true);
      infoFrame.reset(); 
     }
     
     public void getwbtParent() {
      wbtParentnum = 0;
      System.out.println("sdfas4");
      for(DataNode node : nodes){
        System.out.println("sdfas7");
        if (node.wbtParent) {
          wbtParentnum++;
      }
    }
    }
    
}


