/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

/**
 *
 * @author Gagandeep
 */
public class MatrixTest 
{
    public static void main(String[] args) 
    {
        int[][] array = new int[5][10];

        for (int i = 0 ; i < array.length ; i++) 
        {
            for (int j = 0 ; j < array[i].length; j++) 
            {
                System.out.print(array[i][j]+ " ");
            }
            System.out.println("");
        }
       

        
    }
    
}
