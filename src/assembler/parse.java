package assembler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class parse{
	String fileName;
	String line;
	location LOC;
	opTab operators;
	
	public  parse(String name, opTab opers){
		this.fileName = name;
		this.line = null;
		LOC = new location(0);
		operators = opers;
	}
	
	public void parser(symTab symbols){
		 try {
	            // FileReader reads text files in the default encoding.
	            FileReader fileReader = new FileReader(fileName);
	           PrintWriter writer = new PrintWriter("intermediate.txt");
	            
	            // Always wrap FileReader in BufferedReader.
	            BufferedReader bufferedReader = 
	                new BufferedReader(fileReader);
	            while((line = bufferedReader.readLine()) != null) {
	            	//take each line in the txt file line by line
	            	for (String retval: line.split("\n")) {
	            		//split the line by tabs for each field
	            		String [] splitter = retval.split("\t", -1);
	            		//check to see if the line is a comment
	            		if(splitter[0].startsWith(".")){
            				}
	            		//otherwise check each field
	            		else{
	            			//check to see if there are any tabs to separate anything
	            			if(1==splitter.length){
	            				//otherwise print the blank line
	            				writer.print(splitter[0]);
	            			}
	            			//if there are at least 3 entities separated by tab
	            			if(3<=splitter.length){
	            				writer.print(Integer.toHexString(LOC.locator)+"\t");
	            				//check if there is something in the label field, if there is identify it, if not print out a tab
	            				if(splitter[0].length()!=0){
		            			//add the label to the symbol tables
		            			symbols.apend(splitter[0], LOC.locator);
	            				}
		            			writer.print(splitter[0]+"\t");
	            				//check if there is anything in the operator field, if there is identify it, if not print out a tab
	            				writer.print(splitter[1]+"\t");
	            				//check if there is anything in the operand field, if there is identify it, if not print out a tab
	            				if(splitter[2].length()!=0){
	            					LOC.update(splitter[1], operators, splitter[2]);
	            				}
            					writer.print(splitter[2]+"\t");
	            				
	            			}
	            			//if there are four entities separated by tab then there is a comment
	            			if(4<=splitter.length){
	            				//check if there is anything in the comment field, if there is identify it, if not print out a tab
	            				if(splitter[3].length()!=0){
	            					writer.print(splitter[3]+"\t");
	            				}
	            			}
	            		}
		           			writer.println("");
	            		}
	            	
	            }
	            // Always close files.
	            bufferedReader.close();         
	            writer.close();
		 }
	        catch(FileNotFoundException ex) {
	            System.out.println(
	                "Unable to open file '" + 
	                fileName + "'");                
	        }
	        catch(IOException ex) {
	            System.out.println(
	                "Error reading file '" 
	                + fileName + "'");                  
	            // Or we could just do this: 
	            // ex.printStackTrace();
	        }
	}
	
	public location getLocation(){
		return LOC;
	}
}