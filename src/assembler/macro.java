package assembler;

import java.util.ArrayList;

public class macro {
	String name;
	ArrayList <String> params;
	ArrayList <String> code;
	macro(String n){
		name = n;
		params = new ArrayList<String>();
		code = new ArrayList<String>();
	}
	void addParam(String p){
		params.add(p);
	}
	void addCode(String c){
		code.add(c);
	}
	void printParam(){
		for(int i=0; i<params.size();i++){
			System.out.println(params.get(i));
		}
	}
	void printCode(){
		for(int i=0; i<code.size();i++){
			System.out.println(code.get(i));
		}
	}
}
