import java.io.File;  
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TreeTest{

   public static void main(String[] args){
      ArrayList<String> children = new ArrayList<String>();
      children.add("6");
      children.add("7");
      children.add("8");
      
      Tree tree = new Tree();
      System.out.println(tree.getTreeHeight());
      
      int height = 3;
      tree.constructPerfectTree(height, children);
      System.out.println(tree.getTreeHeight());
      

      ArrayList<String> lineages = tree.getLineages();
      
      try {
         FileWriter fileWriter = new FileWriter("tree_paths.txt");
         
         for (String lineage: lineages){
            fileWriter.write( lineage + "\n" );
         }
         
         
         fileWriter.close();
         System.out.println( String.format("Successfully wrote %d**%d = %d paths to file.", children.size(), height, lineages.size()) );
         
       } catch (IOException e) {
         System.out.println("An error occurred when trying to write to file.");
         e.printStackTrace();
       }
      }

}