//Node for a general tree
//Caleb Bessit
//16 July 2024

import java.util.ArrayList;

public class TreeNode<dataType>
{

   dataType data;
   ArrayList<dataType> children;
   
   public TreeNode(dataType d, ArrayList<dataType> theChildren){
      this.data = d;
      
      for (dataType child: theChildren){
         this.children.add(child);
      }
   }
   
   ArrayList<dataType> getChildren(){
      return this.children;
   }


}