/*
Author: John Carillo
Xukai Zou
CS 30000
Assembler Final Project
2 May 2017
*/

package assembler;

import java.util.ArrayList;

public class macroTab {
	ArrayList <macro> macTab;
	macroTab(){	
		macTab = new ArrayList <macro>();
	}
	Boolean hasMac(String check){
		for(int i = 0; i<macTab.size(); i++){
			if(macTab.get(i).name.equals(check)){
				return true;
			}
		}
		return false;
	}
	void addMac(macro m){
		macTab.add(m);
	}
	macro getMac(String m){
		for(int i=0; i<macTab.size();i++){
			if(macTab.get(i).name.equals(m)){
				return macTab.get(i);
			}
		}
		//this should never happen
		return new macro("wrong");
		
	}
	boolean isMacro(String s){
		for(int i=0; i<macTab.size(); i++){
			if(macTab.get(i).name.equals(s)){
				return true;
			}
		}
		return false;
	}
}
