package assembler;

import java.io.*;
import java.util.*;

public class main {
	public static void main(String[] args){
		/*opTab operators = new opTab();
		String fileName = "prog_blocks.txt";
		symTab symbols = new symTab();
		parse j = new parse(fileName, operators);
		j.parser(symbols);
		symbols.printTable();*/
		macroProcess m = new macroProcess("macros.txt");
		m.macroFirst();
	}
}
