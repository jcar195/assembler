package assembler;

import java.util.ArrayList;

public class objcodegen {
	
	String startAddress = "0";
	String indexval = "0";
	String baseval = "0";
	
	//getters and setters for the base, index and starting values
	public void setBaseValue(String baseval){
		this.baseval = baseval;
	}
	
	public String getBaseValue(){
		return baseval;
	}
	
	public void setIndexValue(String indexval){
		this.indexval = indexval;
	}
	
	public String getIndexValue(){
		return indexval;
	}
	
	public void setStartAddress(String startAddress){
		this.startAddress = startAddress;
	}
	
	public String getStartAddress(){
		return startAddress;
	}
	
	//Generate object code for formats 3 and 4
	public String ObjCodeGenerate(Long PC, String operation, String operand, ArrayList<symbol> symTab){
		opTab optab = new opTab();
		
		//initializations for the object code
		String objCode = "";
		String byteOne = "";
		String byteTwo = "";
		
		if(operation.startsWith("+")){
			String format4Addr = "";
			
			//First byte of the opcode
			Integer opcode = optab.getOpcode(operation.substring(1));
			byteOne = Integer.toHexString(opcode + chkAddress(operand)).toUpperCase();
			if(byteOne.length() == 1){	
				byteOne = "0" + byteOne.toUpperCase();
			}
			
			//Second byte of the opcode
			byteTwo = Integer.toHexString(chkIndex(operand, symTab) + 1).toUpperCase();
			
			//Order of operations within an operand
			if(operand.contains("*")){
				Long targetAddress = 0L;
				Long disp = 0L;
				if(operand.contains("-")){
	    			String[] ops = operand.split("\\s*-\\s*");
	    			
	    			targetAddress = PC - Long.parseLong(ops[1].trim());;
	    			disp = targetAddress - PC;
	    		}
	    		
	    		else if(operand.contains("+")){
	    			String[] ops = operand.split("\\s*\\+\\s*");
	    			
	    			targetAddress = PC + Long.parseLong(ops[1].trim());;
	    			disp = targetAddress - PC;
	    		}
				
				String display = String.format("%05X", disp);
				if(display.length() > 3){
					display = display.substring(display.length() - 5);
				}
				
				format4Addr = display.toUpperCase();
			}//end of expression calculation
			
			/*// generate address bytes
			else if(contSects.size() > 1){
				if(!knownControlSectionStartingPlace){
					for(int i = 0; i < currentControlSection.ExtRefTab.size(); i++){
						if(operand.contains(currentControlSection.ExtRefTab.get(i).Label)){
							format4Addr = "00000";
						}
					}
				}
			}*/
			
			//Immediate addressing
			else if(chkAddress(operand).equals(1)){
				PC += 4;
				
				Long targetAddress = 0L;
				boolean symbolUsed = false;
				String operandval = operand.trim().substring(1);
				//Look through the symbol table and find the operand
				for(int i = 0; i < symTab.size(); i++){
		    		if(symTab.get(i).symbolName.equals(operandval)){
		    			targetAddress = Long.valueOf(symTab.get(i).location);
		    			symbolUsed = true;
		    		}
				}
				
				if(symbolUsed){
					//Compute disp (location symbol because of immediate addressing)
					Long disp = targetAddress;
					String display = String.format("%05X", disp);
					if(display.length() > 5){
						display = display.substring(display.length() - 5);
					}
					format4Addr = display;
				}
				
				else{
					Long lon = Long.parseLong(operandval);
					String str = Long.toHexString(lon);
					format4Addr = str.toUpperCase();
					if(str.length() == 4){	
						format4Addr = "0" + str;
					}
					if(str.length() == 3){	
						format4Addr = "00" + str;
					}
					if(str.length() == 2){	
						format4Addr = "000" + str;
					}
					if(str.length() == 1){	
						format4Addr = "0000" + str;
					}
				} 
			}
			
			//Indirect addressing
			else if(chkAddress(operand).equals(2)){
				PC += 4;
				
				Long targetAddress = 0L;
				String operandval = operand.trim().substring(1);
				//Look through the symbol table and find the operand
				for(int i = 0; i < symTab.size(); i++){
		    		if(symTab.get(i).symbolName.equals(operandval)){
		    			targetAddress = Long.valueOf(symTab.get(i).location);
		    		}
				}
				
				//Compute the disp
				Long disp = targetAddress - PC;
				String display = String.format("%05X", disp);
				if(display.length() > 5){
					display = display.substring(display.length() - 5);
				}
				format4Addr = display;
			}

			//Indexed addressing
			else if(chkIndex(operand, symTab).equals(8)){
				String indexval = getIndexValue();
				Long longIndexValue = Long.parseLong(indexval);
				
				PC += 4;
				
				Long targetAddress = 0L;
				String[] ops = operand.split("\\s*,\\s*");
				String symbol = ops[0].trim();
				//Look through the symbol table and find the operand
				for(int i = 0; i < symTab.size(); i++){
		    		if(symTab.get(i).symbolName.equals(symbol)){
		    			targetAddress = Long.valueOf(symTab.get(i).location);
		    		}
				}
				
				Long disp = targetAddress - PC - longIndexValue;
				String display = String.format("%05X", disp);
				format4Addr = display;
			}
			
			//No PC/Base relative 
			else{
				PC += 4;
				
				Long targetAddress = 0L;
				String symbol = operand.trim();
				//Look through the symbol table and find the operand
				for(int i = 0; i < symTab.size(); i++){
		    		if(symTab.get(i).symbolName.equals(symbol)){
		    			targetAddress = Long.valueOf(symTab.get(i).location);
		    		}
				}
				
				Long disp = targetAddress;
				String display = String.format("%05X", disp);
				format4Addr = display;
			}
		
			objCode = byteOne + byteTwo + format4Addr;
		}//end of extended format
		
		
		
		
		//Format 3 (no extended)
		else{
			String format3Addr = "";
			
			//First byte of the op code
			Integer opcode = optab.getOpcode(operation);
			byteOne = Integer.toHexString(opcode + chkAddress(operand)).toUpperCase();
			if(byteOne.length() == 1){	
				byteOne = "0" + byteOne.toUpperCase();
			}
			
			//Second byte of the op code
			byteTwo = Integer.toHexString(chkIndex(operand, symTab) + chkRelative(operand, symTab, 3L, PC)).toUpperCase();
			
			//Order of operations within an expression
			if(operand.contains("*")){
				Long targetAddress = 0L;
				Long disp = 0L;
				if(operand.contains("-")){
	    			String[] ops = operand.split("\\s*-\\s*");
	    			
	    			targetAddress = PC - Long.parseLong(ops[1].trim());;
	    			disp = targetAddress - PC;
	    		}
	    		
	    		else if(operand.contains("+")){
	    			String[] ops = operand.split("\\s*\\+\\s*");
	    			
	    			targetAddress = PC + Long.parseLong(ops[1].trim());;
	    			disp = targetAddress - PC;
	    		}
				
				String display = String.format("%03X", disp);
				if(display.length() > 3){
					display = display.substring(display.length() - 3);
				}
				
				format3Addr = display.toUpperCase();
			}
			
			//RSUB operation
			else if(operation.equals("RSUB")){
				format3Addr = getStartAddress();
				byteTwo = "";
				if(format3Addr.length() == 2){	
					format3Addr = "00" + format3Addr;
				}
				if(format3Addr.length() == 1){	
					format3Addr = "000" + format3Addr;
				}
				if(format3Addr.length() == 0){	
					format3Addr = "0000" + format3Addr;
				}
			}
			
			/*// literal is in operand
			else if(operand.startsWith("=")){
				// location operandval operandval in calculation
				PC += 3;
				
				// (TA) operandval in calculation
				Long targetAddress = 0L;
				
				for(int i = 0; i < litTab.size(); i++){
		    		if(litTab.get(i).Operation.equals(operand)){
		    			targetAddress = litTab.get(i).PC;
		    		}
				}
				
				Long disp = targetAddress - PC;
				String display = String.format("%03X", disp);
				if(display.length() > 3){
					display = display.substring(display.length() - 3);
				}
				format3Addr = display;	
			}*/
			
			//Immediate addressing
			else if(chkAddress(operand).equals(1)){
				PC += 3;
				
				Long targetAddress = 0L;
				
				boolean symbolUsed = false;
				String operandval = operand.trim().substring(1);
				//Look through the symbol table and find the operand
				for(int i = 0; i < symTab.size(); i++){
		    		if(symTab.get(i).symbolName.equals(operandval)){
		    			targetAddress = Long.valueOf(symTab.get(i).location);
		    			symbolUsed = true;
		    		}
				}
				
				if(symbolUsed){
					//Compute disp
					Long disp = targetAddress - PC;
					String display = String.format("%03X", disp);
					if(display.length() > 3){
						display = display.substring(display.length() - 3);
					}
					format3Addr = display;
				}
				
				else{
					Long lon = Long.parseLong(operandval);
					String str = Long.toHexString(lon);
					format3Addr = str.toUpperCase();
					if(str.length() == 2){	
						format3Addr = "0" + str;
					}
					if(str.length() == 1){	
						format3Addr = "00" + str;
					}
				} 
			}
			
			//Indirect addressing
			else if(chkAddress(operand).equals(2)){
				PC += 3;
				
				Long targetAddress = 0L;
				
				String operandval = operand.trim().substring(1);
				//Look through the symbol table and find the operand
				for(int i = 0; i < symTab.size(); i++){
		    		if(symTab.get(i).symbolName.equals(operandval)){
		    			targetAddress = Long.valueOf(symTab.get(i).location);
		    		}
				}
				
				//Compute disp
				Long disp = targetAddress - PC;
				String display = String.format("%03X", disp);
				if(display.length() > 3){
					display = display.substring(display.length() - 3);
				}
				format3Addr = display;
			}
			
			//Indexed addressing
			else if(chkIndex(operand, symTab).equals(8)){
				String indexval = getIndexValue();
				Long longIndexValue = Long.parseLong(indexval);
							
				Long targetAddress = 0L;
				String[] ops = operand.split("\\s*,\\s*");
				String symbol = ops[0].trim();
				//Look through the symbol table and find the operand
				for(int i = 0; i < symTab.size(); i++){
		    		if(symTab.get(i).symbolName.equals(symbol)){
		    			targetAddress = Long.valueOf(symTab.get(i).location);
		    		}
				}
				
				//Compute disp for base relative addressing
				String display = String.format("%03X", 0);
				if(chkRelative(operand, symTab, 3L, PC).equals(4)){
					Long baseRegister = 0L;
					String baseval = getBaseValue().trim();
					
					if(baseval.startsWith("#")){
						baseval = baseval.substring(1);
					}
					else if(baseval.startsWith("@")){
						baseval = baseval.substring(1);
					}
					
					boolean found = false;
					for(int i = 0; i < symTab.size(); i++){
			    		if(symTab.get(i).symbolName.equals(baseval)){
			    			baseRegister = Long.valueOf(symTab.get(i).location);
			    			found = true;
			    			break;
			    		}
					}
					
					if(!found){
		    			baseRegister = Long.parseLong(baseval);
					}
					
					Long disp = targetAddress - baseRegister;
					display = String.format("%03X", disp);
				}

				//Compute disp for pc relative
				else{
					PC += 3;
					Long disp = targetAddress - PC - longIndexValue;
					display = String.format("%03X", disp);
				}
				
				
				format3Addr = display;
				
			}
			
			//PC relative addressing
			else if(chkRelative(operand, symTab, 3L, PC).equals(2)){
				PC += 3;
				
				Long targetAddress = 0L;
				String symbol = operand.trim();
				//Look through the symbol table and find the operand
				for(int i = 0; i < symTab.size(); i++){
		    		if(symTab.get(i).symbolName.equals(symbol)){
		    			targetAddress = Long.valueOf(symTab.get(i).location);
		    		}
				}
				
				//Compute disp
				Long disp = targetAddress - PC;
				String display = String.format("%03X", disp);
				if(display.length() > 3){
					display = display.substring(display.length() - 3);
				}
				format3Addr = display;		
			}
			
			//Base relative addressing scheme
			else if(chkRelative(operand, symTab, 3L, PC).equals(4)){			
				String stringBaseValue = getBaseValue();
				Long baseRegister = 0L;
				String baseval = getBaseValue().trim();
				
				if(stringBaseValue.startsWith("#")){
					baseval = baseval.substring(1);
				}
				
				else if(stringBaseValue.startsWith("@")){
					baseval = baseval.substring(1);
				}
				
				boolean found = false;
				for(int i = 0; i < symTab.size(); i++){
		    		if(symTab.get(i).symbolName.equals(baseval)){
		    			baseRegister = Long.valueOf(symTab.get(i).location);
		    			found = true;
		    			break;
		    		}
				}
				
				if(!found){
	    			baseRegister = Long.parseLong(baseval);
				}
				
				
				Long targetAddress = 0L;
				String[] ops = operand.split("\\s*,\\s*");
				String symbol = ops[0].trim();
				//Look through the symbol table and find the operand
				for(int i = 0; i < symTab.size(); i++){
		    		if(symTab.get(i).symbolName.equals(symbol)){
		    			targetAddress = Long.valueOf(symTab.get(i).location);
		    		}
				}
				
				//Compute disp
				Long disp = targetAddress - baseRegister;
				String display = String.format("%03X", disp);
				format3Addr = display;
			}

			objCode = byteOne + byteTwo+ format3Addr;
		}
		
		return objCode;
	}//end of object code generation
	
	public Integer chkIndex(String operand, ArrayList<symbol> symTab){
		if(chkAddress(operand).equals(1)){
			return 0;
		}
		
		else if(chkAddress(operand).equals(2)){
			return 0;
		}
		
		else if((operand.trim().toUpperCase()).endsWith("X")){
			return 8;
		}
		
		return 0;
	}
	
	//Determines addressing type based on n/i bits
	public Integer chkAddress(String operand){
		Integer addressingval = 0;
		if((operand.trim().toUpperCase()).startsWith("#")){
			addressingval = 1 + addressingval;
		}
		
		else if((operand.trim().toUpperCase()).startsWith("@")){
			addressingval = 2 + addressingval;
		}
		
		else{
			addressingval = 3 + addressingval;
		}
		
		return addressingval;
	}
	
	//Checks base/PC relative mode
	public Integer chkRelative(String operand, ArrayList<symbol> symTab, Long format, Long PC){
		if(chkAddress(operand).equals(1)){
			String operandval = operand.trim().substring(1);
			//operand is #symbol
			for(int i = 0; i < symTab.size(); i++){
	    		if(symTab.get(i).symbolName.equals(operandval)){
	    			return 2;
	    		}
			}
			return 0;
		}
		
		Long targetAddress = 0L;
		String[] ops = operand.split("\\s*,\\s*");
		String symbol = ops[0].trim();
		//Look through the symbol table and find the operand
		for(int i = 0; i < symTab.size(); i++){
    		if(symTab.get(i).symbolName.equals(symbol)){
    			targetAddress = Long.valueOf(symTab.get(i).location);
    		}
		}
		
		/*for(int i = 0; i < litTab.size(); i++){
    		if(litTab.get(i).Operation.equals(operand)){
    			targetAddress = litTab.get(i).location;
    		}
		}*/
		
		Long dispPCRange = 0L;
		dispPCRange = targetAddress - PC + format;
		
		Long baseRegister = 0L;
		String baseval = getBaseValue().trim();
		
		if(baseval.startsWith("#")){
			baseval = baseval.substring(1);
		}
		else if(baseval.startsWith("@")){
			baseval = baseval.substring(1);
		}
		
		boolean found = false;
		for(int i = 0; i < symTab.size(); i++){
    		if(symTab.get(i).symbolName.equals(baseval)){
    			baseRegister = Long.valueOf(symTab.get(i).location);
    			found = true;
    			break;
    		}
		}
		
		if(!found){
    			baseRegister = Long.parseLong(baseval);
		}
		
		Long dispbaseRange = 0L;
		dispbaseRange = targetAddress - PC + format + baseRegister;
		
		if(dispPCRange < 2048 && dispPCRange > -2048){
			return 2;
		}
		
		else if(dispbaseRange < 4095 && dispbaseRange > -4095){
			return 4;
		}
		
		return 0;
	}
}
