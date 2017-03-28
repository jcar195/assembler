package assembler;

import java.util.ArrayList;

public class opTab {
	ArrayList<op> opTab;
	opTab(){
		//declare the symbol table
		opTab = new ArrayList<op>();
		opTab.add(new op("ADD",3,0x18));
		opTab.add(new op("ADDF",3,0x58));
		opTab.add(new op("ADDR",2,0x90));
		opTab.add(new op("AND",3,0x40));
		opTab.add(new op("CLEAR",2,0xB4));
		opTab.add(new op("COMP",3, 0x28));
		opTab.add(new op("COMPF",3, 0x88));
		opTab.add(new op("COMPR",2, 0xA0));
		opTab.add(new op("DIV",3, 0x24));
		opTab.add(new op("DIVF",3, 0x64));
		opTab.add(new op("DIVR",3,0x9C));
		opTab.add(new op("FIX",1, 0xC4));
		opTab.add(new op("FLOAT",1, 0xC0));
		opTab.add(new op("HIO",1,0xF4));
		opTab.add(new op("J",3,0x3C));
		opTab.add(new op("JEQ",3,0x30));
		opTab.add(new op("JGT",3,0x34));
		opTab.add(new op("JLT",3,0x38));
		opTab.add(new op("JSUB",3,0x48));
		opTab.add(new op("LDA",3,0x00));
		opTab.add(new op("LDB",3,0x68));
		opTab.add(new op("LDCH",3,0x50));
		opTab.add(new op("LDF",3,0x70));
		opTab.add(new op("LDL",3,0x08));
		opTab.add(new op("LDS",3,0x6C));
		opTab.add(new op("LDT",3,0x74));
		opTab.add(new op("LDX",3,0x04));
		opTab.add(new op("LPS",3,0xD0));
		opTab.add(new op("MUL",3,0x20));
		/*
		opTab.add(new op("",,));
		opTab.add(new op("",,));
		opTab.add(new op("",,));
		opTab.add(new op("",,));
		opTab.add(new op("",,));
		opTab.add(new op("",,));
		opTab.add(new op("",,));
		opTab.add(new op("",,));
		opTab.add(new op("",,));
		opTab.add(new op("",,));
		opTab.add(new op("",,));
		opTab.add(new op("",,));
		opTab.add(new op("",,));
		opTab.add(new op("",,));
		opTab.add(new op("",,));
		opTab.add(new op("",,));
		opTab.add(new op("",,));
		opTab.add(new op("",,));
		opTab.add(new op("",,));
		opTab.add(new op("",,));
		opTab.add(new op("",,));
		opTab.add(new op("",,));
		opTab.add(new op("",,));
		opTab.add(new op("",,));
		opTab.add(new op("",,));
		opTab.add(new op("",,));
		opTab.add(new op("",,));
		opTab.add(new op("",,));
		opTab.add(new op("",,));
		opTab.add(new op("",,));*/
	}
}
