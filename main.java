package assembler;

import java.io.*;
import java.util.*;

public class main {
	public static void main(String[] args){
		String fileName = "control_section.txt";
		parse j = new parse(fileName);
		j.parser();
	}
}
