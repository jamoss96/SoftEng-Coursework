import java.util.ArrayList;

public class DataNode{
  
  public int pertEarlyStart;
  public int pertDuration;
  public int pertEarlyFinish;
  public int pertLateStart;
  public int pertLateFinish;
  public int pertSlack;
  public ArrayList<DataNode> parents;
  public ArrayList<DataNode> children;
  public DataNode wbtParentNode;
  private int monthStart;
  private int monthFinish;
  public int x;
  public int y;
  public int pertX;
  public int pertY;
  public String name;
  public Boolean wbtParent;
  public Boolean pertSel;
  

  public DataNode(String name){
    this.name = name;
    this.pertEarlyStart = 0;
    this.pertDuration = 0;
    this.pertEarlyFinish = 0;
    this.pertX = 500;
    this.pertY = 500;
    pertInit();
  
  
  }
  
  public void init(){
    if(wbtParentNode == null){
      x = 500;
      y = 200;
    }
    else{
      x = wbtParentNode.x;
      y = wbtParentNode.y + 100;
    }
  }
  
  
 
  
  public void pertInit(){
    if(parents != null){
      for(DataNode node:parents){
        if(node.pertEarlyFinish > pertEarlyStart){
          pertEarlyStart = node.pertEarlyFinish;
        }
     }
      pertEarlyFinish = pertEarlyStart + pertDuration;
    }
    
  }
  
  
}