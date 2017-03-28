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
		opTab.add(new op("STA",3,0x0C));
		opTab.add(new op("MULF",3,0x60));
		opTab.add(new op("MULR",2,0x98));
		opTab.add(new op("NORM",1,0xC8));
		opTab.add(new op("OR",3,0x44));
		opTab.add(new op("RD",3,0xD8));
		opTab.add(new op("RMO",2,0xAC));
		opTab.add(new op("RSUB",3,0x4C));
		opTab.add(new op("SHIFTL",2,0xA4));
		opTab.add(new op("SHIFTR",2,0xA8));
		opTab.add(new op("SIO",1,0xF0));
		opTab.add(new op("SSK",3,0xEC));
		opTab.add(new op("STB",3,0x78));
		opTab.add(new op("STCH",3,0x54));
		opTab.add(new op("STF",3,0x80));
		opTab.add(new op("STI",3,0xD4));
		opTab.add(new op("STL",3,0x14));
		opTab.add(new op("STS",3,0x7C));
		opTab.add(new op("STSW",3,0xE8));
		opTab.add(new op("STT",3,0x84));
		opTab.add(new op("STX",3,0x10));
		opTab.add(new op("SUB",3,0x1C));
		opTab.add(new op("SUBF",3,0x5c));
		opTab.add(new op("SUBR",2,0x94));
		opTab.add(new op("SVC",2,0xB0));
		opTab.add(new op("TD",3,0xE0));
		opTab.add(new op("TIO",1,0xF8));
		opTab.add(new op("TIX",3,0x2C));
		opTab.add(new op("TIXR",2,0xB8));
		opTab.add(new op("WD",3,0xDC));
	}
	
	Boolean hasOp(String check){
		for(int i = 0; i<opTab.size(); i++){
			if(opTab.get(i).mnemonic.equals(check)){
				return true;
			}
		}
		return false;
	}
	
	Integer getOpcode(String check){
		for(int i = 0; i<opTab.size(); i++){
			if(opTab.get(i).mnemonic.equals(check)){
				return opTab.get(i).opcode;
			}
		}
		//this should not happen
		return 0;
	}
	
	Integer getFormat(String check){
		for(int i = 0; i<opTab.size(); i++){
			if(opTab.get(i).mnemonic.equals(check)){
				return opTab.get(i).format;
			}
		}
		//this should not happen
		return 0;
	}
}
