package assembler;

import java.io.*;
import java.util.*;

public class main {
	public static void main(String[] args){
		opTab operators = new opTab();
		ArrayList<symbol> symTab1 = new ArrayList<>();
		String fileName = "basic.txt";
		symTab symbols = new symTab();
		parse j = new parse(fileName, operators);
		j.parser(symbols,symTab1);
		
//		macroProcess m = new macroProcess("macros.txt");
//		m.macroFirst();
		
		System.out.println("Symbols: ");
		symbols.printTable();
		System.out.println("");
			
		secondparse sp = new secondparse();
		//use the text file with the LOC to generate the object code
		sp.assemble("intermediate.txt", symTab1);
		//DONE!
		System.out.println("End of Assembler!");
				
	}
}
