package assembler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//import java.util.ArrayList;

public class secondparse {
	//private Long totalOffset = 0L;
	private Long currentOffset = 0L;
	//private Boolean knownControlSectionStartingPlace = false;
	//private ControlSection currentControlSection;
	
	//Case switch statements for general purpose registers
	private String GetRegisterValue(String operand){
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
		
		BufferedWriter bw = null;
		BufferedReader reader = null;
		
		try {
		    reader = new BufferedReader(new FileReader(firstPassOutputFileName));
		    String text = null;
		    
		    
		    // New file with object code extension
		    File file = new File(firstPassOutputFileName + ".objprog");
		    
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
            
		    while ((text = reader.readLine()) != null) {
		    	//empty input
		    	if(text.length() == 0){
		    		continue;
		    	}
		    	
		    	//comment input
		    	//if the input starts with a tab then a period or just a period
		    	if(text.startsWith("\t.") || text.startsWith(".")){
		    		if(text.startsWith("\t.") && !text.substring(2).startsWith("\t") && !(text.length() == 2)){
		    			String line[] = text.split("\t");
		    			line[1] = line[1] + "\t";
		    			line[2] = line[2] + "\t";
		    			line[3] = line[3] + "\t" + "\t";
		    			StringBuilder builder = new StringBuilder();
		    			for(String s : line) {
		    			    builder.append(s);
		    			}
		    			text = "\t" + builder.toString();
		    		}
		    		bw.write(text);
		    		bw.newLine();
		    		continue;
		    	}
		    	
		    	//parse the line into the components (location, operation etc.)
		    	firstpassoutput line = new firstpassoutput(text);
		    	
		    	//last line of the file
		    	if(line.operation.equals("")){
		    		line.operand = "";
		    		line.label = "";
		    		line.comment = "";
		    		line.location = null;
		    		bw.write(line.toString());
		    		continue;
		    	}
		    	
		    	//end of file
		    	if(line.operation.equals(null)){
		    		continue;
		    	}
		    	
		    	//look for the START in the file at the beginning
		    	if(line.operation.equals("START") && firstLine){
		    		try{
		    			String value = line.operand;
			    		objcodegenerator.setBaseValue(value);
			    		bw.write(line.toString());
			    		bw.newLine();
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
		    	
		    	
		    	//LDX operation
		    	if(line.operation.equals("LDX") || line.operation.equals("+LDX")){
		    		String value = line.operand.substring(1);
		    		objcodegenerator.setIndexValue(value);
		    	}
		    	
		    	//LDB operation
		    	if(line.operation.equals("LDB") || line.operation.equals("+LDB")){
		    		String value = line.operand.substring(1);
		    		objcodegenerator.setBaseValue(value);
		    	}
		    	
		    	//END operation
		    	if(line.operation.equals("END")){
		    		bw.write(line.toString());
		    		bw.newLine();
		    		continue;
		    	}
		    	
		    	//checking if the operation is in the op table
		    	opTab currOpTable = new opTab();
		    	if(currOpTable.hasOp(line.operation)){
		    		//grab the opcode for that operation
		    		Integer opCode = currOpTable.getOpcode(line.operation);
		    				    		
	    			//Format 1 (just the opcode as the object code, 1 byte, no memory reference)
	    			if(currOpTable.getFormat(line.operation).equals(1)){
	    				String hexval = String.format("%02X", opCode);
	    				line.objCode = hexval;
		    		}//end of format 1 instructions
	    			
		    		//Format 2 (2 bytes, no memory reference)
		    		if(currOpTable.getFormat(line.operation).equals(2)){
		    			String hexval = String.format("%02X", opCode);
		    			
		    			
		    			if(line.operation.equals("CLEAR") || line.operation.equals("TIXR")){
		    				hexval += GetRegisterValue(line.operand) + "0"; 
		    				line.objCode = hexval;
		    			}
		    			
		    			if(line.operation.equals("ADDR") ||line.operation.equals("DIVR")|| line.operation.equals("COMPR")  ||line.operation.equals("MULR") ||line.operation.equals("SUBR")||line.operation.equals("RMO") ){
		    				String[] ops = line.operand.split("\\s*,\\s*");
		    				hexval += GetRegisterValue(ops[0].trim()) + GetRegisterValue(ops[1].trim());
		    				line.objCode = hexval;
		    			}
		    					    			
		    			if(line.operation.equals("SVC")){
		    				String[] ops = line.operand.split(",");
		    				Long r1 = Long.parseLong(GetRegisterValue(ops[1].trim()));
		    				hexval += r1 + "0";
		    				line.objCode = hexval;
		    			}
		    			
		    			if(line.operation.equals("SHIFTL") || line.operation.equals("SHIFTR")){
		    				String[] ops = line.operation.split(",");
		    				Long r2 = (Long.parseLong(GetRegisterValue(ops[1].trim())) - 1);
		    				hexval += GetRegisterValue(ops[0].trim()) + r2; 
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
		    			byte[] bytes = literal.getBytes("US-ASCII");
		    			for(int i = 0; i < bytes.length; i++){
		    				buffer.append(Character.forDigit((bytes[i] >> 4) & 0xF, 16));
		    				buffer.append(Character.forDigit((bytes[i] & 0xF), 16));
		    			}
		    			line.objCode = buffer.toString().toUpperCase();
		    		}
		    		//hexadecimal literal
		    		else if(line.operation.substring(1).toUpperCase().startsWith("X")){
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
		    	
		    	
		    	
		    	//error handling
		    	else{
		    		System.err.println("Invalid opeartion " + line.operation);
		    		return false;
		    	}
		    	
		    	//use this line to begin generating object program
	    		bw.write(line.objCode);
	    		bw.newLine();
	    		
		    }//end of reading the file
		}
		
		//catches for exceptions
		catch (FileNotFoundException e) {
		    e.printStackTrace();
		} 
		
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
		return true;
	}
	
}//end of second pass
