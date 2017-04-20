package assembler;

import java.io.*;
import java.util.*;

public class main {
	public static void main(String[] args){
		
		macroProcess m = new macroProcess("basic.txt");
		m.macroFirst();
		m.macroSecond();
		opTab operators = new opTab();
		String fileName = "expanded.txt";
		symTab symbols = new symTab();
		parse j = new parse(fileName, operators);
		j.parser(symbols);
		symbols.printTable();
	}
}
