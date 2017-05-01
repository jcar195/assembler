/*
Author: Myson Burch
Xukai Zou
CS 30000
Assembler Final Project
2 May 2017
*/
package assembler;


import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//import java.io.Writer;
import java.io.BufferedReader;
import java.io.BufferedWriter;

//second pass of the assembler
public class secondparse {
	private Long currentOffset = 0L;
	
	//Case switch statements for general purpose registers
	private String getRegVals(String operand){
		switch (operand) {
		case "A" :
			return "0";
		case "X" :
			return "1";
		case "L" :
			return "2";
		case "B" :
			return "3";
		case "S" :
			return "4";
		case "T" :
			return "5";
		case "F" :
			return "6";
		case "PC" :
			return "8";
		case "SW" :
			return "9";
		default :
			return "";
		}
	}
		
	
	public Boolean assemble(String firstPassOutputFileName, symTab symtab){	
		//elements to read and write to the text files
		BufferedWriter bw = null;
		BufferedReader reader = null;
		
		//header string
		StringBuilder headerRecord = new StringBuilder();
		//text record string
		StringBuilder textRecord = new StringBuilder();
		//end record string
		StringBuilder endRecord = new StringBuilder();
		
		//maximum in half bits
		Integer maxrecord = 31;
		//length of the text record
		Integer currentLen = 0;
		//temporary length
		Integer tempcurrLen = 0;
		//length of the program
		Integer totalLen = 0;
		//temporary object code string
		StringBuilder tempobjCode = new StringBuilder();
		
		//variables to keep track of record components 
		Integer headerCtr = 0;
		Long tempLocation = 0L;
		Long startLoc = 0L;
		
		try {
			//read the immediate file
		    reader = new BufferedReader(new FileReader(firstPassOutputFileName));
		    String text = null;
		    
		    
		    // New file with object code extension
		    File file = new File(firstPassOutputFileName + ".objprog");
		    //if file DNE, create one
            if (!file.exists()) {
                file.createNewFile();
            }
            
            //elements to write to the new file
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            bw = new BufferedWriter(fw);
            
            //create a new object code generator instance
    		objcodegen objcodegenerator = new objcodegen();

            //boolean to detect the START in the AL
            Boolean firstLine = true;            
            
            //while not at the end of the file
		    while ((text = reader.readLine()) != null) {
		    	//empty input
		    	if(text.length() == 0){
		    		continue;
		    	}
		    		    	
		    	//parse the line into the components (location, operation etc.)
		    	firstpassoutput line = new firstpassoutput(text);
		    		    		    	
		    	//look for the START in the file at the beginning
		    	if(line.operation.equals("START") && firstLine){
		    		try{
		    			//grab the operand to set the base value
		    			String value = line.operand;
			    		objcodegenerator.setBaseValue(value);
			    		
			    		//Write the header record of zeros and length of the file
			    		//Length of the file will be written later
			    		//bw.write("H^" + line.label + '\t' + "^" + line.location + "^");
			    		headerRecord.append("H^" + line.label + '\t' + "^" + String.format("%06X", line.location) + "^");
			    		//bw.newLine();
			    		headerCtr = 1;
			    		startLoc = line.location;
			    		continue;
		    		}
		    		
		    		catch(Exception e){
		    			System.err.println("Invalid START address");
		    			return false;
		    		}
		    		
		    		finally{		    		
		    			firstLine = false;
		    		}
		    	}
		    	
		    	//else write a empty title Header record
		    	else{
		    		if (headerCtr == 0){
		    			//bw.write("H^" + "DEFAULT" + '\t' + "^" + String.format("%06X",line.location) + "^");
		    			headerRecord.append("H^" + "DEFAULT" + '\t' + "^" + String.format("%06X",line.location) + "^");
		    			//bw.newLine();
		    			headerCtr = 1;
		    			startLoc = line.location;
		    		}
		    	}
		    	
		    	//catch for the JST typo error in the macros.txt file
		    	if(line.operation.equals("JST")){
		    		line.operation = "JLT";
		    	}
		    	
		    	//LDX operation
		    	if(line.operation.equals("LDX") || line.operation.equals("+LDX")){
		    		//initialize with the index register value
		    		String value = line.operand.substring(1);
		    		objcodegenerator.setIndexValue(value);
		    	}
		    	
		    	//LDB operation
		    	if(line.operation.equals("LDB") || line.operation.equals("+LDB")){
		    		//initialize with the base register value
		    		String value = line.operand.substring(1);
		    		objcodegenerator.setBaseValue(value);
		    	}
		    	
		    	//END operation
		    	if(line.operation.equals("END")){
		    		continue;
		    	}
		    	
		    	//checking if the operation is in the op table
		    	opTab currOpTable = new opTab();
		    	if(currOpTable.hasOp(line.operation)){
		    		//grab the opcode for that operation
		    		Integer opCode = currOpTable.getOpcode(line.operation);
		    				    		
	    			//Format 1 (just the opcode as the object code, 1 byte, no memory reference)
	    			if(currOpTable.getFormat(line.operation).equals(1)){
	    				//grab hexadecimal value of the opcode
	    				String hexval = String.format("%02X", opCode);
	    				line.objCode = hexval;
		    		}//end of format 1 instructions
	    			
		    		//Format 2 (2 bytes, no memory reference)
		    		if(currOpTable.getFormat(line.operation).equals(2)){
	    				//grab hexadecimal value of the opcode
		    			String hexval = String.format("%02X", opCode);		    			
		    			
		    			//Go through all the format 2 operations
		    			//Format 2 operations with one argument
		    			if(line.operation.equals("CLEAR") || line.operation.equals("TIXR")){
		    				hexval += getRegVals(line.operand) + "0"; 
		    				line.objCode = hexval;
		    			}
		    			//Format 2 operations with two arguments
		    			if(line.operation.equals("ADDR") ||line.operation.equals("DIVR")|| line.operation.equals("COMPR")  ||line.operation.equals("MULR") ||line.operation.equals("SUBR")||line.operation.equals("RMO") ){
		    				String[] ops = line.operand.split("\\s*,\\s*");
		    				hexval += getRegVals(ops[0].trim()) + getRegVals(ops[1].trim());
		    				line.objCode = hexval;
		    			}
		    			
		    		}//end of format 2 instructions
		    		
			    	//Format 3 (3 bytes, default SIC/XE format)
			    	else{
			    		line.objCode = objcodegenerator.ObjCodeGenerate(line.location + currentOffset, line.operation, line.operand, symtab);
			    	}
		    		
		    	}
		   
			    //Format 4 (4 bytes, extended SIC/XE for more space)
			    else if(line.operation.startsWith("+")){
			    	line.objCode = objcodegenerator.ObjCodeGenerate(line.location + currentOffset, line.operation, line.operand, symtab);
			    }
		    	
		    	//Looking for literals
		    	else if(line.operation.startsWith("=")){
		    		String literal = line.operation.trim().toUpperCase().substring(3, line.operation.length() - 1);
		    		//character string literal
		    		if(line.operation.substring(1).startsWith("C")){
		    			StringBuffer buffer = new StringBuffer();
		    			//encode the string into array of bytes
		    			byte[] bytes = literal.getBytes("US-ASCII");
		    			for(int i = 0; i < bytes.length; i++){
		    				//determines the character representation for a specific digit in the specified radix
		    				buffer.append(Character.forDigit((bytes[i] >> 4) & 0xF, 16));
		    				buffer.append(Character.forDigit((bytes[i] & 0xF), 16));
		    			}
		    			//store that object code
		    			line.objCode = buffer.toString().toUpperCase();
		    		}
		    		//hexadecimal literal
		    		else if(line.operation.substring(1).toUpperCase().startsWith("X")){
		    			//store that object code
		    			line.objCode = literal.toUpperCase();
		    		}
		    	}
		    	
		    	//other operations that do not generate object code
		    	else if(line.operation.equals("RESB")){
		    		line.objCode = "";
		    	}
		    	
		    	else if(line.operation.equals("RESW")){
		    		line.objCode = "";
		    	}
		    	
		    	//BYTE operation
		    	else if(line.operation.equals("BYTE")){
		    		//if we are storing a character value
		    		if(line.operand.startsWith("C")){
		    			//buffer for the object code
		    			StringBuffer objCodebuffer = new StringBuffer();
		    			String charSubstring = line.operand.substring(2, line.operation.length() + 1);
		    			//encode the string into a sequence of bytes
		    			byte[] bytes = charSubstring.getBytes("US-ASCII");
		    			//for each byte, append for the object code 
		    			for(int i = 0; i < bytes.length; i++){
		    				//determines the character representation for a specific digit in the specified radix
		    				objCodebuffer.append(Character.forDigit((bytes[i] >> 4) & 0xF, 16));
		    				objCodebuffer.append(Character.forDigit((bytes[i] & 0xF), 16));
		    			}
		    			//final object code
		    			line.objCode = objCodebuffer.toString().toUpperCase();
		    		}
		    		//hexadecimal byte
		    		else if(line.operand.startsWith("X")){
		    			String charSubstring = line.operand.substring(2, line.operation.length());
		    			line.objCode = charSubstring;
		    		}
		    	}
		    	
		    	//error handling
		    	else{
		    		//System.err.println("Invalid operation: " + line.operation);
		    		//set that object code to blank so the assembler completes(even with incorrect object code)
		    		line.objCode = "";
		    		//return false;
		    	}
		    	
		    	//use this line to begin generating object program
		    	//if there is a line that doesn't generate object code or we are at the maximum record length
		    	if(line.objCode.equals("")|| (currentLen + line.objCode.length() >= 2*maxrecord)){
		    		//bw.newLine();
		    		
		    		//if this line didn't generate object code (multiple times)
		    		if (line.objCode.equals("")&&currentLen ==0){
		    			currentLen = 0;
		    		}
		    		//this is the first line that didn't generate object code
		    		else if (line.objCode.equals("")){
		    			/*bw.write("T^" + String.format("%06X", tempLocation) + "^" 
		    					+ String.format("%02X",currentLen) + tempobjCode);
		    			*/
		    			textRecord.append("T^" + String.format("%06X", tempLocation) + "^" 
		    					+ String.format("%02X",currentLen) + tempobjCode.toString());
		    			
		    			tempobjCode.setLength(0);
		    			currentLen = 0;
		    		}
		    		//otherwise there is overflow and 
		    		//write what we had to the record and move to the next line
		    		else {
		    			/*bw.write("T^" + String.format("%06X", tempLocation) + "^" 
		    					+ String.format("%02X",currentLen) + tempobjCode);
		    			*/
		    			textRecord.append("T^" + String.format("%06X", tempLocation) + "^" 
		    					+ String.format("%02X",currentLen) + tempobjCode.toString() + '\n');
		    			
		    			//bw.newLine();
		    			
		    			//store stuff for the portion of code that went over the bound for the next line
		    			tempLocation = line.location;
		    			tempobjCode.setLength(0);
		    			//append new object code
		    			tempobjCode.append("^"+line.objCode.toString());
		    			currentLen = 0;
		    			//increment the length for the current record
		    			currentLen += line.objCode.length();
		    		}
		    		
		    		//System.out.println(tempobjCode);
		    		//currentLen = 0;
		    	}
		    	else{
		    		//at the start of a new text record
		    		if(currentLen == 0){
		    			//bw.newLine();
		    			textRecord.append('\n');
		    			tempobjCode.setLength(0);
		    			//write the text record
		    			//bw.write("T^" + String.format("%06X", line.location) + "^");
		    			//grab a temporary location
		    			tempLocation = line.location;
		    			//append new object code
		    			//bw.write("^"+line.objCode);
		    			tempobjCode.append("^"+line.objCode.toString());
		    			//increment the length for the current record
		    			currentLen += line.objCode.length();
		    			tempcurrLen = currentLen;
		    			//increment the total length
		    			totalLen += line.objCode.length();
		    		}
		    		else{
		    			if(currentLen + line.objCode.length() < 2*maxrecord){
			    			//append new object code as long as we are within the record length
			    			//bw.write("^"+line.objCode);
			    			tempobjCode.append("^"+line.objCode.toString());
			    			//increment the length for the current record
			    			currentLen += line.objCode.length();
			    			tempcurrLen = currentLen;
			    			//increment the total length
			    			totalLen += line.objCode.length();
		    			}	
		    		}
		    		
		    		//bw.write(line.objCode);
		    		//bw.newLine();
		    		
		    	}//end of writing text record
	    			    		
		    }//end of reading the file (end of while)
		    
		    //just in case we ended the file and did not reach a bound on the length of the record and had object code still to write
		    if (tempobjCode.length()!=0){
		    	/*bw.write("T^" + String.format("%06X", tempLocation) + "^" 
    					+ String.format("%02X",tempcurrLen) + tempobjCode);
		    	*/
		    	textRecord.append("T^" + String.format("%06X", tempLocation) + "^" 
    					+ String.format("%02X",tempcurrLen) + tempobjCode.toString() + '\n');
		    	
    			tempobjCode.setLength(0);
    			currentLen = 0;
		    }
		    
		    headerRecord.append(String.format("%06X", totalLen));
		    endRecord.append("E^"+String.format("%06X", startLoc));
		    
		    /*bw.newLine();
		    //insert program length and end of record
		    bw.write("E^"+String.format("%06X", startLoc));*/
		    
		    bw.write(headerRecord.toString()+textRecord.toString()+'\n'+endRecord.toString());
            
		}
		
		//catches for exceptions
		catch (IOException e) {
		    e.printStackTrace();
		}
		
		//close connections
		finally {
			try {
		        if (reader != null) {
		            reader.close();
		            bw.close();
		        }
		    }
		    catch (IOException e) {}
		}
		
		/*Writer output = null;
		try {
			output = new BufferedWriter(new FileWriter(firstPassOutputFileName + ".objprog", true));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			output.append("New Line!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		return true;
	}
	
}//end of second pass
