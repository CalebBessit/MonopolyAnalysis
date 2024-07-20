//Implementation of general tree
//16 July 2024
//Caleb Bessit

import java.util.ArrayList;

public class Tree
{

   TreeNode root;
   
  
   /**
      Construct a tree with only a root node.
   **/
   public Tree(){
      root = new TreeNode("",-1,"");
   }
   
   /**
   
      Construct a tree with a root node and the given children.
      
      @parameter theChildren An ArrayList of strings containing the data the root's children should have.
   **/
   public Tree(ArrayList<String> theChildren){
   
      root = new TreeNode("",-1,"");
      
   
      for (String childData: theChildren){
         root.addChild(childData);  
      }
           
   }
   
   /**
   
    Method that gets the total height of the tree.
    
    @returns int The height of the tree.
   **/
   public int getTreeHeight ()
   {
      return getTreeHeight (root);
   }   
   
   /**
      Method that returns the height of the tree at this node, counted from the root to this node.
      
      @parameter node The node we want to check the height of the tree at.
      @returns int The height of the tree measured from the root to this node.
   **/
   public int getTreeHeight ( TreeNode node )
   {
      if (node == null)
         return -1;
      else if (node.getChildren()==null){
         return node.getHeight();
      }
      else {
         ArrayList<TreeNode> children = (ArrayList<TreeNode>)node.getChildren().clone();
         int height = -1;
  
         for (TreeNode child: children){
            height = Math.max(height, getTreeHeight(child));
         }
         return height;
         }
   }
   
   /**
      Helper method that calls the constructPerfectTree method at the root with the given children.
    
      @parameter height The required height of the tree.
      @parameter children An ArrayList of strings containing the data that each child should have.
   **/  
   public void constructPerfectTree(int height, ArrayList<String> children){
      constructPerfectTree(height, children, this.root);
   }

   /**
      Recursively constructs a perfect tree with a given height where each node has the children with the same data items.
      
      @parameter height The required height of the tree.
      @parameter children An ArrayList of strings containing the data that each child should have.
      @parameter node The current node we are constructing the node at.
   
   **/
   public void constructPerfectTree(int height, ArrayList<String> children, TreeNode node){
      
      //If the node we are at is of the required height, return.
      if (node.getHeight()==height){
         return;
      }
      else{
      
         //At this node, create child nodes for each given data item
         for (String child: children){
            node.addChild(child);
         }
         
         //Recursively create the perfect tree at each node
         ArrayList<TreeNode> nodeChildren = node.getChildren();
         for (TreeNode child: nodeChildren){
            constructPerfectTree(height, children, child);
         }
         
         
      }
      
   }


}