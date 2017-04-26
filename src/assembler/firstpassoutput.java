/*
Author: Myson Burch
Xukai Zou
CS 30000
Assembler Final Project
2 May 2017
*/

package assembler;

//class to parse the intermediate file from the first parse
public class firstpassoutput {
	//label of the instruction
	String label;
	//operation of the instruction
	String operation;
	//operand(s) of the instruction
	String operand;
	//comments for the instruction
	String comment;
	//the LOC value for the instruction
	Long location;
	//object code for the instruction
	String objCode;

	//method to parse the line of the instruction
	public String toString(){
		//if there is no LOC then just parse and place values into the other parts of the line
		if(null == location){
			String output = String.format("%s\t%s\t%s\t%s\t%s\t%s", "", label, operation, operand, objCode, comment);
			return output;
		}
		//parsing the components of the intermediate test file separated by tabs
		String output = String.format("%04X\t%s\t%s\t%s\t%s\t%s", location, label, operation, operand, objCode, comment);
		return output;
	}
	
	//constructor
	firstpassoutput(String text){
		location = 0L;
		label = "";
		operation = "";
		operand = "";
		comment = "";

		//parsing the components(lines) of the intermediate test file with an addition of an object code section
		String[] split = text.split("\t", -1);
		if(1 <= split.length){
			if(!split[0].equals("NULL")){
				location = Long.parseLong(split[0], 16);
			}
			else{
				location = null;
			}
		}
		if(2 <= split.length){
			label = split[1];
		}
		if(3 <= split.length){
			operation = split[2];
		}
		if(4 <= split.length){
			operand = split[3];
		}
		if(5 <= split.length){
			comment = split[4];
		}
		if(6 <= split.length){
			objCode = split[5];
		}
	}
}	

