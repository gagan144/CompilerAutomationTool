
package Test;

import commanLib.ItemProdctnSetNode;
import commanLib.ItemProduction;
import java.util.ArrayList;

public class ItemProductionTEst 
{
    public static void main(String[] args) 
    {
        /*
        ArrayList<String> left= new ArrayList<String>();        
        left.add("T");
        
        
        ArrayList<String> right= new ArrayList<String>();
        right.add("T");
        
        //right.add("F");
        
        System.out.println(left.equals(right));
        
        ItemProduction prod = new ItemProduction(left, right);
        
        prod.incrementDotPositon();
        prod.incrementDotPositon();
        
        System.out.println(prod.toString());
        
        System.out.println("IsPresent : "+prod.isPresent("F"));
        * 
        */
        
        
        ArrayList<String> l1= new ArrayList<String>();        
        l1.add("E");
        ArrayList<String> r1= new ArrayList<String>();
        r1.add("T");
        
        ArrayList<String> l2= new ArrayList<String>();        
        l2.add("T");
        ArrayList<String> r2= new ArrayList<String>();
        r2.add("T");
        r2.add("*");
        r2.add("F");
        
        
        ArrayList<String> l3= new ArrayList<String>();        
        l3.add("X");
        ArrayList<String> r3= new ArrayList<String>();
        r3.add("x");
        r3.add("+");
        r3.add("y");
        
        ItemProduction itProd1 = new ItemProduction(l1, r1);
        ItemProduction itProd2 = new ItemProduction(l2, r2);
        
        ItemProduction itProd3 = new ItemProduction(l3, r3);
        
        ArrayList<ItemProduction> list1 = new ArrayList<ItemProduction>();
        list1.add(itProd1);
        list1.add(itProd2);
        
        ItemProdctnSetNode node1 = new ItemProdctnSetNode(0, list1);               
        
        System.out.println("NODE 1 :\n"+node1.toString());
        
        
        ArrayList<ItemProduction> list2 = new ArrayList<ItemProduction>();
        list2.add(itProd2);
        list2.add(itProd1);
        
        
        ItemProdctnSetNode node2 = new ItemProdctnSetNode(0, list2);               
        
        System.out.println("NODE 2 :\n"+node2.toString());
        
        System.out.println("IS node1 & node2 equal : "+node1.isEqualTo(node2));
        
    }
    
}
