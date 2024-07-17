/**

Node for a general tree
@author Caleb Bessit
@version 16 July 2024

**/

import java.util.ArrayList;

public class TreeNode
{

   private String item;
   private String lineage;
   private int height;
   private ArrayList<TreeNode> children;
   
   public TreeNode(String data, int parentHeight, String parentLineage){
      this.item = data;
      this.children = null;
      this.height = parentHeight +1;
      this.lineage = String.format("%s$%s", parentLineage, data);
   }
   
   public void addChild(String data){
      if (this.children==null){
         children = new ArrayList<TreeNode>();
         children.add(new TreeNode(data, this.height, this.lineage) );
      }
      else{
         children.add(new TreeNode(data, this.height, this.lineage) );
      }
   }
   
   /**
      Constructor that creates a tree node with the given data type and list of children.
      @parameter d The data type of this tree node.
      @parameter theChildren An ArrayList containing references to what should be this node's children.
   
   **/
   public TreeNode(String data, ArrayList<TreeNode> theChildren){
      this.item = data;
      
      children = new ArrayList<TreeNode>();
      
      for (TreeNode child: theChildren){
         this.children.add(child);
      }
   }
   
   
   /**
      Method that returns a list of this node's children.
      
      @returns ArrayList<dataType> An ArrayList of references to this node's children.
   **/
   public ArrayList<TreeNode> getChildren(){
      return this.children;
   }
   
   /**
      Method that returns the height of the tree counting from the root to this node.
      
      @returns int The height of the tree counting from the root to this node.
   **/
   public int getHeight(){
      return this.height;
   }
   
   /**
      Method that returns the "lineage" of this node.
      This is a string containing the data items of all of this node's ancestors from the root.
      
      @returns String This node's lineage.
   **/
   public String getLineage(){
   
      if ( this.lineage.length()>2 ){
         return this.lineage.substring(2,this.lineage.length());
      }
      return "";
   }


}