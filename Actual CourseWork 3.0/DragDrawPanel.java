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
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;




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
    private int ganttY;
    public String name;
    public boolean pertBool;
    public boolean ganttBool;
    public boolean wbtBool;
    private boolean setOverwriting;
    private boolean collide = false;
    private DataNode tempNode;
    private DataNode activeNode;
    private Point mouseStartPos;
    private File saveFile;
    
    
    
    public DragDrawPanel(Dimension screenSize){
        
        nodes = new ArrayList<DataNode>();
        DataNode startNode = new DataNode("Start Node");
        
        
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
        
        startNode.x = screenSize.width/2;
        startNode.y = 100;
        startNode.pertY = screenSize.height/2;
        startNode.pertEarlyStart = 0;
        startNode.pertDuration = 0;
        startNode.pertLateStart = 0;
        startNode.pertSel = true;
        nodes.add(startNode);
        
        
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

         
         //print no wbtParents
        for(DataNode node1:nodes){

              
                g.drawRect(node1.x - 75, node1.y - 25, 150, 50);
                g.drawString(node1.name, node1.x -10, node1.y );
                if(node1.wbtParentNode != null){
                g.drawLine(node1.wbtParentNode.x, node1.wbtParentNode.y +25, node1.x, node1.y - 25);
                }
              
            
          }
      
        

        
        g.dispose();
        // repaint panel with new modified paint
        repaint();
    
        
       }
        if(pertBool){
          calculateLateTimes();
          for(DataNode node:nodes){
           
          if(node.pertSel){
            if(node.pertX + 75 > size.width){node.pertX = size.width - 75;}
            else if(node.pertX - 75 < 0){node.pertX = 75;}
            
            if(node.pertY + 50 > size.height){node.pertY = size.height + 50;}
            else if(node.pertY - 50 < 0){node.pertY = 50;}
            
          g.drawRect(node.pertX-75, node.pertY-50, 150, 100);
          g.drawRect(node.pertX-75, node.pertY-50, 50, 25);
          g.drawRect(node.pertX-25, node.pertY-50, 50, 25);
          g.drawRect(node.pertX+25, node.pertY-50, 50, 25);
          g.drawRect(node.pertX-75, node.pertY+25, 50, 25);
          g.drawRect(node.pertX-25, node.pertY+25, 50, 25);
          g.drawRect(node.pertX+25, node.pertY+25, 50, 25);
          
          g.drawString(node.name, node.pertX - 20, node.pertY);
          g.drawString(new Integer(node.pertEarlyStart).toString(), node.pertX -65, node.pertY-30);
          g.drawString(new Integer(node.pertDuration).toString(), node.pertX -15, node.pertY-30);
          g.drawString(new Integer(node.pertEarlyFinish).toString(), node.pertX +35, node.pertY-30);
          g.drawString(new Integer(node.pertLateStart).toString(), node.pertX -65, node.pertY+40);
          g.drawString(new Integer(node.pertSlack).toString(), node.pertX -15, node.pertY+40);
          g.drawString(new Integer(node.pertLateFinish).toString(), node.pertX +35, node.pertY+40);
          if(node.parents != null){
            for(DataNode parent:node.parents){
              g.drawLine(parent.pertX + 75, parent.pertY, node.pertX- 75, node.pertY );
            }
          }
          }
          }
           
         }
        
        if(ganttBool){
         ganttY = 100;
          ganttDraw(g, nodes.get(0));
          for(DataNode node: nodes){
            if(node.wbtParentNode == nodes.get(0)){
              ganttDraw(g, node);
              drawChildren(g, node);
            }
            
          }
          
        }
          g.dispose();
          repaint();
          
        }
    
    
    
    
    public void clear(){
        Graphics g = paintImage.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, size.width, size.height);
        g.dispose();
        // repaint panel with new modified paint
        
      
        repaint();
        
    }
    

    
    public ArrayList<DataNode> getNodes() { return nodes;}
    
    public void setNodes(ArrayList<DataNode> someNodes){
     nodes = someNodes;
     
    }
    
     
     
     public void newTask(){
      infoFrame.setVisible(true);
      infoFrame.reset();
      infoFrame.getNodes(nodes);
      infoFrame.setModel();
      infoFrame.setPertModel();
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
       if(wbtBool){
       if(point.x >= node.x-75 && point.x <= node.x+75 && point.y-45 >= node.y-25 && point.y-45 <= node.y+25){return true;}
       else{return false;}
       }
       else if(pertBool){
       if(point.x >= node.pertX-75 && point.x <= node.pertX+75 && point.y-45 >= node.pertY-50 && point.y-45 <= node.pertY+50){return true;}
       else{return false;}
       }
       return false;
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
             System.out.println(activeNode.pertLateFinish);
           }
           else{}
         }
         if(collide == false){activeNode = null;}
         if(e.getButton() == 3){
           savePng();
           System.out.println("save");
         }
       }
       
       
       
       public void mouseReleased(MouseEvent e){
         
         if(activeNode != null){
           if(wbtBool){
           activeNode.x = MouseInfo.getPointerInfo().getLocation().x;
           activeNode.y = MouseInfo.getPointerInfo().getLocation().y-45;
           }
           if(pertBool){
           activeNode.pertX = MouseInfo.getPointerInfo().getLocation().x;
           activeNode.pertY = MouseInfo.getPointerInfo().getLocation().y-45;
           }
         }
       }
       public void mouseClicked(MouseEvent e){}
       public void mouseEntered(MouseEvent e){}
       public void mouseExited(MouseEvent e){}
       
       public void calculateLateTimes(){
         
         Collections.reverse(nodes);
         
         for(DataNode node:nodes){
           if(node.pertLateFinish == 0 ){
            node.pertLateFinish = node.pertEarlyFinish;
            node.pertLateStart = node.pertLateFinish - node.pertDuration;
            
           }
           if(node.parents != null){
           for(DataNode parent:node.parents){
           parent.pertLateFinish = node.pertLateStart;
           }
           }
           node.pertSlack = node.pertLateFinish - node.pertEarlyFinish;
         }
         
         Collections.reverse(nodes);
       }
       
       public void drawChildren(Graphics g, DataNode parent){
        
         for(DataNode child:nodes){
           if(child.wbtParentNode == parent){
            ganttDraw(g, child);
            drawChildren(g, child);
           }
         }
         
       }
       
       public void ganttDraw(Graphics g, DataNode node){
        
         g.drawRect(10, ganttY, 500, 25);
         g.drawRect(510, ganttY, 500, 25);
         
         g.drawString(node.name, 260, ganttY + 12);
         g.drawString(new Integer(node.pertDuration).toString(), 760, ganttY + 12);
           
         ganttY = ganttY + 25;
         
       }
       
       public void savePng(){
         FileFilter filter = new FileNameExtensionFilter("Image Files", "png");
         JFileChooser loader = new JFileChooser(){
           
      @Override
    public void approveSelection(){
        File f = getSelectedFile();
        if(f.exists() && getDialogType() == SAVE_DIALOG){
            int result = JOptionPane.showConfirmDialog(this,"The file exists, overwrite?","Existing file",JOptionPane.YES_NO_CANCEL_OPTION);
            switch(result){
                case JOptionPane.YES_OPTION:
                    setOverwriting = true;
                    super.approveSelection();
                    return;
                case JOptionPane.NO_OPTION:
                    return;
                case JOptionPane.CLOSED_OPTION:
                    return;
                case JOptionPane.CANCEL_OPTION:
                    cancelSelection();
                    return;
            }
        }
        super.approveSelection();
    }      
};
        loader.setFileFilter(filter);
        setOverwriting = false;       
        int fileReturnVal = loader.showSaveDialog(this);
        if (fileReturnVal == JFileChooser.APPROVE_OPTION){
            if(setOverwriting == true){
                saveFile = loader.getSelectedFile();
                try {
                     ImageIO.write(paintImage, "PNG", saveFile);
                  
                } catch (IOException ex) {
                      Logger.getLogger(DragDraw.class.getName()).log(Level.SEVERE, null, ex);
                }
                 System.out.println(saveFile);
            }
            else{
                saveFile = new File(loader.getSelectedFile().getAbsolutePath() + ".png");
                try {

                     ImageIO.write(paintImage, "PNG", saveFile);
                  
                } catch (IOException ex) {
                      Logger.getLogger(DragDraw.class.getName()).log(Level.SEVERE, null, ex);
                }
                 System.out.println(saveFile);
             }
        }
       }
}




