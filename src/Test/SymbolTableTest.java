package Test;

import Compiler.SymbolTable;

public class SymbolTableTest 
{
    public static void main(String[] args) 
    {
        SymbolTable symTable = new SymbolTable(null);
        
        symTable.insert("amount", 2,1);
        symTable.insert("principle", 2,1);
        symTable.insert("interest", 2,2);
        
        System.out.println(symTable.lookup("id3"));
        
    }
    
}
