package Test;

import java.util.ArrayList;

public class TestArrayList 
{
    public static void main(String[] args) 
    {
        /*
        String str="gagan";
    
        ArrayList<String> list1 = new ArrayList<String>();
        ArrayList<String> list2 = new ArrayList<String>();
        
        list1.add(str);
        //list2.add(str);
        list2.add(list1.get(0));
        
        System.out.println((list1.get(0)==list2.get(0)));
        */
        /*
        ArrayList<String> list = new ArrayList<String>();
        
        String str="A";
        
        list.add("A");
        if(!list.contains("A"))
        { list.add("Ax"); }
        list.add("A");
                
        
        System.out.println(list);
        */
        
        ArrayList<String> list1 = new ArrayList<String>();
        list1.add("1");
        list1.add("2");
        list1.add("3");
        list1.add("4");
        
        ArrayList<String> list2=(ArrayList<String>) list1.clone();
        
        System.out.println(list2);
        list2.remove(0);
        System.out.println(list2);
        System.out.println(list1);
    }
    
    
    
}
