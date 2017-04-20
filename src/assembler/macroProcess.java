package assembler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class macroProcess {
	macroTab macTab;	
	String txt;
	macroProcess(String title){
			macTab = new macroTab();
			txt = title;
	}
	void macroFirst(){
		//open the file
		// FileReader reads text files in the default encoding.
        try {
			FileReader fileReader = new FileReader(txt);
			// Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
			//go line by line
            Boolean curMac = false;
            String macTitle = "";
            while((line = bufferedReader.readLine()) != null) {
            	String [] retval = line.split("\n");
            	for (int i = 0; i<retval.length; i++) {
               		//split the line by tabs for each field
            		String [] splitter = retval[i].split("\t", -1);
            		//check to see if the line is a comment
            		if(splitter[0].startsWith(".")){
        					//do nothing, just skip it
        				}
            		//otherwise check each field
            		else{
            			//if there are at least 3 entities separated by tab
            			if(3<=splitter.length){
            				if(curMac){
            					if(splitter[1].equals("MEND")){
            						curMac = false;
            						
            					}
            					else{
            						String s="";
            						for(int j = 0; j<splitter.length;j++){
            							s+=splitter[j]+"\t";
            						}
            						macro m = macTab.getMac(macTitle);
            						m.addCode(s);
            					}
            				}
            				else{
            					if(splitter[1].length()!=0){
            						if(splitter[1].contentEquals("MACRO")){
            							//get the title
            							String title;
            							title = splitter[0];
            							//create the macro
            							macro mac = new macro(title);
            							//get the parameters
            							String parameters[] = splitter[2].split(",");
            							for(int a=0; a< parameters.length; a++){
            								mac.addParam(parameters[a].substring(1));
            							}
            							macTitle = title;
            							curMac = true;
            							macTab.addMac(mac);
            						}
            					}
            				}
            			}
            		}
            			
            			
            	}
            }
		//end of first pass
        //close the file
        bufferedReader.close();         
        } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	void macroSecond(){
		// FileReader reads text files in the default encoding.
        try {
			FileReader fileReader = new FileReader(txt);
			// Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            //open the writer
            PrintWriter writer = new PrintWriter("expanded.txt");
            String line;
            boolean isMac = false;
            //go line by line
            while((line = bufferedReader.readLine()) != null) {
            	String [] retval = line.split("\n");
            	for (int i = 0; i<retval.length; i++) {
            		//split the line by tabs for each field
            		String [] splitter = retval[i].split("\t", -1);
            		//check to see if the line is a comment
            		if(splitter[0].startsWith(".")){
        					//do nothing, just skip it
        			}
            		else{
            			if(3<=splitter.length){
            				if(splitter[1].contentEquals("MACRO")){
            					isMac = true;
            				}
            				if(!isMac){	
            					//check to see if it is a macro call
            					if(macTab.isMacro(splitter[1])){
            						//if it is, print out the code to the file
            						writer.print(splitter[0]);
            						ArrayList <String> p = getParams(splitter[2]);
            						macro m = macTab.getMac(splitter[1]);
            						for(int j=0; j<m.code.size();j++){
            							String a = m.code.get(j);
            							a = retParams(a, m.params, p);
            							writer.println(a);
            						}
            					}
            					else{
            						//if not, just put the current code in the file
            						for(int j = 0; j<splitter.length; j++){
            							writer.print(splitter[j]+"\t");
            						}
            					}
            				}
            				if(splitter[1].contentEquals("MEND")){
            					isMac = false;
            				}
            			}
            		}
            		if(!isMac){
            		writer.println("");
            		}
            	}
            }
		//close the file
        bufferedReader.close(); 
        //close the writer
        writer.close();
        } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	ArrayList<String> getParams(String m){
		ArrayList <String>  params = new ArrayList <String>(); 
		String split[] = m.split(",");
		for(int i = 0; i< split.length; i++){
			String p = split[i];
			params.add(p);
			System.out.println(p);
		}
		System.out.println("");
		return params;
	}

	String retParams (String m, ArrayList<String> macP, ArrayList<String> p){
		String a = "";
		if(m.contains("&")){
			for(int i = 0; i < macP.size(); i++){
				if(m.contains(macP.get(i))){
					String x = "&"+macP.get(i);
					m = m.replaceAll(x, p.get(i));
				}
			}
		}
		a = m;
		return a;
	}
}
	