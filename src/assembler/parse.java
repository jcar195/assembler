package assembler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class parse{
	String fileName;
	String line;
	int label;
	int operand;
	int operator;
	int comment; 
	
	public  parse(String name){
		this.fileName = name;
		this.line = null;
		this.label = 0;
		this.operand = 0;
		this.operator = 0;
		this.comment = 0;
	}
	
	public void parser(symTab symbols){
		Integer counter = 0x0;
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
            					comment++;
            					String comt = "comment";
            					comt += comment;
            					writer.print(comt+"\t");
            				}
	            		//otherwise check each field
	            		else{
	            			//check to see if there are any tabs to separate anything
	            			if(1==splitter.length){
	            				//otherwise print the blank line
	            				writer.print(splitter[0]);
	            			}
	            			//if there are 2 entities separated by tab
	            			if(2<=splitter.length){
	            				//check if there is something in the label field, if there is identify it, if not print out a tab
	            				if(splitter[0].length()!=0){
		            			label++;
		            			String labs = "label";
		            			labs = labs+label;
		            			writer.print(labs+"\t");
		            			//add the label to the symbol tables
		            			symbols.apend(splitter[0], counter);
		            			
	            				}
	            				else{
		            			writer.print(splitter[0]+"\t");
	            				}
	            				//check if there is anything in the operator field, if there is identify it, if not print out a tab
	            				if(splitter[1].length()!=0){
	            					operator++;
	            					String oprt = "operator";
	            					oprt = oprt+operator;
	            					writer.print(oprt+"\t");
	            					counter += 0x3;
	            				}
	            				else{
	            					writer.print("\t");
	            				}
	            			}
	            			//if there are three entities separated by tab
	            			if(3<=splitter.length ){
	            				//check if there is anything in the operand field, if there is identify it, if not print out a tab
	            				if(splitter[2].length()!=0){
	            					operand++;
	            					String oprd = "operand";
	            					oprd += operand;
	            					writer.print(oprd+"\t");
	            				}
	            				else{
	            					writer.print("\t");
	            				}
	            			}
	            			//if there are four entities separated by tab
	            			if(4<=splitter.length){
	            				//check if there is anything in the comment field, if there is identify it, if not print out a tab
	            				if(splitter[3].length()!=0){
	            					comment++;
	            					String comt = "comment";
	            					comt += comment;
	            					writer.print(comt+"\t");
	            				}
	            				else{
	            					writer.print("\t");
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
}