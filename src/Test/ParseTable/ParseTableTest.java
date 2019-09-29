package Test.ParseTable;

import commanLib.ParsingTableBtmUp;

public class ParseTableTest 
{
    public static void main(String[] args) 
    {
        /*
        ParsingTableBtmUp slrTable = new ParsingTableBtmUp(2, 2);
        System.out.println(slrTable.toString());
        * 
        */
        
        StudentTable table = new StudentTable(2, 2);
        System.out.println(table.toString());
                
    }
}

class Student
{
    int rno;
    int marks;

    public Student()
    { rno=0; marks=0; }
    
    public Student(int rno, int marks) {
        this.rno = rno;
        this.marks = marks;
    }
    
    public String toString()
    {
        return "Rno : "+rno+", Marks : "+marks+"\n";
    }
        
}

class StudentTable
{
    Student tableData[][]=null;
        
    public StudentTable(int r,int c) 
    {
        tableData= new Student[r][c]; 
        for(int i=0;i<tableData.length;i++)
        {
            for(int j=0;j<tableData[i].length;j++)
            { tableData[i][j]=new Student(); }
        }
    }
    
    public String toString()
    {
        String ret="";
        
        for(int i=0;i<tableData.length;i++)
        {
            for(int j=0;j<tableData[i].length;j++)
            {  System.out.println(tableData[i][j].toString()); }
            System.out.println("");
        }
        return ret;
    }
    
}
