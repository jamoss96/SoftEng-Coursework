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
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JFrame;
import java.awt.event.*;



/**
 *
 * @author up728335
 */
public class DragDrawPanel extends JPanel implements  ActionListener, MouseListener{
    
    private boolean go = false;
    private int xpos, ypos, oldx, oldy;
    private MouseEvent dave;
    private BufferedImage paintImage;
    public Dimension size;
    public ArrayList<DataNode> nodes;
    private ArrayList<DataNode> revNodes;
    public NewJFrame infoFrame;
    private int created;
    private int wbtParentnum;
    private int tempwbtParent;
    private int wbtChildrennum;
    private int tempwbtChildren;
    public String name;
    public boolean pertBool;
    public boolean ganttBool;
    public boolean wbtBool;
    private boolean collide = false;
    private DataNode tempNode;
    private DataNode activeNode;
    private Point mouseStartPos;
    
    
    
    public DragDrawPanel(Dimension screenSize){
        
        nodes = new ArrayList<DataNode>();
        System.out.println(nodes);
        paintImage = new BufferedImage(screenSize.width, screenSize.height, BufferedImage.TYPE_3BYTE_BGR);
        size = screenSize;
        addMouseListener(this);
        clear();
        infoFrame = new NewJFrame(nodes);
        infoFrame.setVisible(false);
        infoFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        infoFrame.addWindowListener( new WindowAdapter()
                                      {
          public void windowClosing(WindowEvent e)
          {
            infoFrame.setVisible(false);
          }
        });
        
        name = "unnamed";
        Timer time = new Timer(50, this);
        time.start();
        repaint();
        
        
    }
    
    @Override
    public void paintComponent(Graphics g) {
            g.drawImage(paintImage, 0, 0, null);
    }
    
    public void updatePaint(){
        Graphics g = paintImage.createGraphics();
        clear();
        g.setColor(Color.black);
        
        if(wbtBool){
          // draw start node
          g.drawRect(size.width/2-100, 100, 150, 50);
          g.drawString(name, size.width/2-25, 125);
          
          Collections.reverse(nodes);
        
          //print all other nodes
         for(DataNode node:nodes){
           if (node.wbtParent) {
           
          
          g.drawRect(node.x-75, node.y-25, 150, 50);
          g.drawString(node.name, node.x -10, node.y);
          g.drawLine(size.width/2-25, 150, node.x, node.y -25);
          
          tempwbtParent--;
           }
         } 
         
         Collections.reverse(nodes);
         
         //print no wbtParents
        for(DataNode node1:nodes){
          if(node1.wbtParent){
            for(DataNode node2:nodes){
              if(node2.wbtParentNode == node1){
                g.drawRect(node2.x - 75, node2.y - 25, 150, 50);
                g.drawString(node2.name, node2.x -10, node2.y );
                g.drawLine(node1.x, node1.y +25, node2.x, node2.y);
              }
            }
          }
      }
        

        
        g.dispose();
        // repaint panel with new modified paint
        repaint();
    
        
       }
    
    
    }
    
    public void clear(){
        Graphics g = paintImage.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, size.width, size.height);
        g.dispose();
        // repaint panel with new modified paint
        
      
        repaint();
        
    }
    

    
    public ArrayList<DataNode> getNodes() {System.out.println(nodes); return nodes;}
    
    public void setNodes(ArrayList<DataNode> someNodes){
     nodes = someNodes;
     System.out.println(nodes);
    }
    
     
     
     public void newTask(){
      infoFrame.setVisible(true);
      infoFrame.reset();
      infoFrame.getNodes(nodes);
      infoFrame.setModel();
     }
     
     public void getwbtParent() {
      wbtParentnum = 0;
      for(DataNode node : nodes){
        if (node.wbtParent) {
          wbtParentnum++;
        }
      }
    }
     
     public void getwbtChildren(DataNode node1) {
      wbtChildrennum = 0;
        for(DataNode node2 : nodes){
        if (node2.wbtParentNode == node1) {
          wbtChildrennum++;
        }
        }
      
    }
     
     public boolean checkCollision(Point point, DataNode node){
       if(point.x >= node.x-75 && point.x <= node.x+75 && point.y-45 >= node.y-25 && point.y-45 <= node.x+25){return true;}
       else{return false;}
     }
     
       @Override
    public void actionPerformed(ActionEvent e) {
      updatePaint();
      if(!infoFrame.getNewNodes().isEmpty()){
       
        for(DataNode newNode : infoFrame.getNewNodes()){
        nodes.add(newNode);
      }
        infoFrame.resetNodes();
        
      }
      
    }
       
       @Override
       public void mousePressed(MouseEvent e){
         collide = false;
         mouseStartPos = MouseInfo.getPointerInfo().getLocation();
         for(DataNode node : nodes){
           if(checkCollision(mouseStartPos, node)){
             activeNode = node;
             collide = true;
             System.out.println("_");
             System.out.println("collide");
           }
         }
         if(collide == false){activeNode = null;}
       }
       
       
       
       public void mouseReleased(MouseEvent e){
         if(activeNode != null){
           activeNode.x = MouseInfo.getPointerInfo().getLocation().x;
           activeNode.y = MouseInfo.getPointerInfo().getLocation().y-45;
         }
       }
       public void mouseClicked(MouseEvent e){}
       public void mouseEntered(MouseEvent e){}
       public void mouseExited(MouseEvent e){}
    
}


