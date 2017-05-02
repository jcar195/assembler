/*
Author: Myson Burch and John Carillo
Xukai Zou
CS 30000
Assembler Final Project
2 May 2017
*/

package assembler;

import java.io.*;
import java.util.*;

public class main {
	public static void main(String[] args){
		
		System.out.println("Please enter the assembler file:");
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		macroProcess m = new macroProcess(s);
		m.macroFirst();
		m.macroSecond();
		opTab operators = new opTab();
		String fileName = "expanded.txt";
		symTab symbols = new symTab();
		parse j = new parse(fileName, operators);
		j.parser(symbols);
		location L = j.getLocation();
		System.out.println("Symbols: ");
		symbols.printTable();
		System.out.println("");
			
		secondparse sp = new secondparse();
		//use the text file with the LOC to generate the object code
		sp.assemble("intermediate.txt", symbols, L);
		//DONE!
		System.out.println("End of Assembler!");
				
	}
}