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
    private boolean pertBool;
    private boolean ganttBool;
    private boolean wbtBool;
    private ArrayList<DataNode> nodes;
    
    
    public DragDraw(){
        
        
        setTitle("Draw");
        setBackground(Color.WHITE);
        setResizable(true);
        setVisible(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0,0,screenSize.width, screenSize.height);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
   
        panel = new DragDrawPanel(screenSize);
        panel.addMouseListener(panel);
        panel.addKeyListener(panel);
        
        pertBut = new JButton("Pert");
        ganttBut = new JButton("Gantt");
        wbtBut = new JButton("WBT");
        addNewTaskBut = new JButton("Add New Task");
        
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
        
        addNewTaskBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewTaskActionPerformed(evt);
                System.out.println("addnew");
            } 
        });
        
        
        panel.setFocusable(true);
        add(panel);
        panel.add(pertBut);
        panel.add(ganttBut);
        panel.add(wbtBut);
        panel.add(addNewTaskBut);
        panel.setLayout(null);
        requestFocus();
        
        pertBut.setBounds(new Rectangle(screenSize.width -600, 50, 95, 25));
        ganttBut.setBounds(new Rectangle(screenSize.width -500, 50, 95, 25));
        wbtBut.setBounds(new Rectangle(screenSize.width -400, 50, 95, 25));
        addNewTaskBut.setBounds(new Rectangle(50, 50, 150, 25));
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new DragDraw();
    }
    
    private void pertActionPerformed(java.awt.event.ActionEvent evt) {                                         
        pertBool = true;
        ganttBool = false;
        wbtBool = false;
    } 
    
    private void ganttActionPerformed(java.awt.event.ActionEvent evt) {                                         
        pertBool = false;
        ganttBool = true;
        wbtBool = false;
    } 
    
    private void wbtActionPerformed(java.awt.event.ActionEvent evt) {                                         
        pertBool = false;
        ganttBool = false;
        wbtBool = true;
    } 
    
    private void addNewTaskActionPerformed(java.awt.event.ActionEvent evt) {                                         
        new NewJFrame(panel.getNodes());
    } 
    
}
