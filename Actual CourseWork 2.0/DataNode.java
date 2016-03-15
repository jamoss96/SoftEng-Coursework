import java.util.ArrayList;

public class DataNode{
  
  private int pertEarlyStart;
  private int pertDuration;
  private int pertEarlyFinish;
  private int pertLateStart;
  private int pertLateFinish;
  private int pertSlack;
  private ArrayList<DataNode> parents;
  private ArrayList<DataNode> children;
  private int monthStart;
  private int monthFinish;
  public int x;
  public int y;
  public String name;
  public Boolean wbtParent;

  public DataNode(int x, int y, String name, Boolean wbtP){
    this.x = x;
    this.y = y;
    this.name = name;
    this.wbtParent = wbtP;
    
  
  
  }
  
  
}