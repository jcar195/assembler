package assembler;

import java.io.*;
import java.util.*;

public class main {
	public static void main(String[] args){
		opTab operators = new opTab();
		String fileName = "basic.txt";
		symTab symbols = new symTab();
		parse j = new parse(fileName, operators);
		j.parser(symbols);
		symbols.printTable();
	}
}
