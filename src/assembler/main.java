package assembler;

import java.io.*;
import java.util.*;

public class main {
	public static void main(String[] args){
		
		macroProcess m = new macroProcess("macros.txt");
		m.macroFirst();
		m.macroSecond();
		opTab operators = new opTab();
		String fileName = "expanded.txt";
		symTab symbols = new symTab();
		parse j = new parse(fileName, operators);
		j.parser(symbols);
		
		System.out.println("Symbols: ");
		symbols.printTable();
		System.out.println("");
			
		secondparse sp = new secondparse();
		//use the text file with the LOC to generate the object code
		sp.assemble("intermediate.txt", symbols);
		//DONE!
		System.out.println("End of Assembler!");
				
	}
}
