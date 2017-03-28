package assembler;

import java.util.ArrayList;

public class symTab {
	ArrayList<symbol> SymTab;
	
	symTab(){
		//declare the symbol table
		SymTab = new ArrayList<symbol>();
	}
	
	void apend(String sym, int loc){
		SymTab.add(new symbol(sym, loc));
	}
	
	void printTable(){
		for (int i = 0; i < SymTab.size(); i++) {
			System.out.println(SymTab.get(i).symbolName+"\t"+Integer.toHexString(SymTab.get(i).location));
		}
	}
	
	Boolean hasSymbol(String check){
		for(int i = 0; i<SymTab.size(); i++){
			if(SymTab.get(i).symbolName.equals(check)){
				return true;
			}
		}
		return false;
	}
	
	Integer getLocation(String check){
		for(int i = 0; i<SymTab.size(); i++){
			if(SymTab.get(i).symbolName.equals(check)){
				return SymTab.get(i).location;
			}
		}
		//this should not happen
		return 0;
	}
}
