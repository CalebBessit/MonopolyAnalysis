import java.util.ArrayList;

public class TreeTest{

   public static void main(String[] args){
      ArrayList<String> children = new ArrayList<String>();
      children.add("6");
      children.add("7");
      Tree tree = new Tree();
      System.out.println(tree.getTreeHeight());
      tree.constructPerfectTree(3, children);
      System.out.println(tree.getTreeHeight());
   }

}