/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.*;



/**
 *
 * @author up728335
 */
public class DragDraw extends JFrame{
    
    public  DragDrawPanel panel;
    public  static ArrayList<ArrayList<String>> dave;
    public String[] options = new String[2];
    public JButton pertBut;
    public JButton ganttBut;
    public JButton wbtBut;
    public JButton addNewTaskBut;
    public JButton exitBut;
    
    private File loadFile;
    private File saveFile;
    private boolean setOverwriting;
 
    private ArrayList<DataNode> nodes;
    private JFileChooser saver = new JFileChooser();
    private JMenuBar mainMenuBar ;
    private JMenu fileMenu;
    private JMenuItem saveMenuItem;
    private JMenuItem loadMenuItem;
    
  
    
    
    public DragDraw(){
        setTitle("Draw");
        setBackground(Color.WHITE);
        setResizable(true);
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0,0,screenSize.width, screenSize.height);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        
   
        panel = new DragDrawPanel(screenSize);
        panel.repaint();
        panel.wbtBool = true;
        
        pertBut = new JButton("Pert");
        ganttBut = new JButton("Gantt");
        wbtBut = new JButton("WBT");
        addNewTaskBut = new JButton("Add New Task");
        exitBut = new JButton("Exit");

        
        pertBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pertActionPerformed(evt);
            } 
        });
        
        ganttBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ganttActionPerformed(evt);
            } 
        });
        
        wbtBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wbtActionPerformed(evt);
            } 
        });
        
        exitBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            } 
        });
        
        addNewTaskBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewTaskActionPerformed(evt);
                
                panel.infoFrame.going = true;
            } 
        });
        
        
        panel.setFocusable(true);
        add(panel);
        panel.add(pertBut);
        panel.add(ganttBut);
        panel.add(wbtBut);
        panel.add(addNewTaskBut);
        panel.add(exitBut);
        panel.setLayout(null);
        requestFocus();
        panel.repaint();
        
        mainMenuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        saveMenuItem = new javax.swing.JMenuItem();
        loadMenuItem = new javax.swing.JMenuItem();
        
        fileMenu.setText("File");

        saveMenuItem.setText("Save");
        saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveMenuItem);

        loadMenuItem.setText("Load");
        loadMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(loadMenuItem);

        mainMenuBar.add(fileMenu);

        setJMenuBar(mainMenuBar);

        
        pertBut.setBounds(new Rectangle(screenSize.width -600, 50, 95, 25));
        ganttBut.setBounds(new Rectangle(screenSize.width -500, 50, 95, 25));
        wbtBut.setBounds(new Rectangle(screenSize.width -400, 50, 95, 25));
        addNewTaskBut.setBounds(new Rectangle(50, 50, 150, 25));
        exitBut.setBounds(new Rectangle(screenSize.width - 200, 50, 150, 50));
        setVisible(true);
        panel.repaint();
        
        
        
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new DragDraw();
    }
    
    private void pertActionPerformed(java.awt.event.ActionEvent evt) {                                         
        panel.pertBool = true;
        panel.ganttBool = false;
        panel.wbtBool = false;
    } 
    
    private void ganttActionPerformed(java.awt.event.ActionEvent evt) {                                         
        panel.pertBool = false;
        panel.ganttBool = true;
        panel.wbtBool = false;
    } 
    
    private void wbtActionPerformed(java.awt.event.ActionEvent evt) {                                         
        panel.pertBool = false;
        panel.ganttBool = false;
        panel.wbtBool = true;
    } 
    
    private void addNewTaskActionPerformed(java.awt.event.ActionEvent evt) {                                         
        panel.newTask();
        
    } 
    
    private void exitActionPerformed(java.awt.event.ActionEvent evt) {                                         
        System.exit(0);
        
    }
    
     private void saveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMenuItemActionPerformed
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
        setOverwriting = false;       
        int fileReturnVal = loader.showSaveDialog(this);
        if (fileReturnVal == JFileChooser.APPROVE_OPTION){
            if(setOverwriting == true){
                saveFile = loader.getSelectedFile();
                try {
                     saveFile.createNewFile();
                } catch (IOException ex) {
                      Logger.getLogger(DragDraw.class.getName()).log(Level.SEVERE, null, ex);
                }
                 System.out.println(saveFile);
            }
            else{
                saveFile = new File(loader.getSelectedFile().getAbsolutePath() + ".txt");
                try {
                     saveFile.createNewFile();
                } catch (IOException ex) {
                      Logger.getLogger(DragDraw.class.getName()).log(Level.SEVERE, null, ex);
                }
                 System.out.println(saveFile);
             }
        }
        
        panel.name = saveFile.getName();
        System.out.println(panel.name);
    }

    private void loadMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
         JFileChooser loader = new JFileChooser();
    
        
        int fileReturnVal = loader.showOpenDialog(this);
        if (fileReturnVal == JFileChooser.APPROVE_OPTION){
           loadFile = loader.getSelectedFile();
           System.out.println(loadFile);
        } 
        
    }
    
  
    
   
    
}
