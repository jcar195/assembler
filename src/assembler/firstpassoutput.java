package assembler;

public class firstpassoutput {
	String label;
	String operation;
	String operand;
	String comment;
	Long location;
	String objCode;

	public String toString(){
		
		if(null == location){
			String output = String.format("%s\t%s\t%s\t%s\t%s\t%s", "", label, operation, operand, objCode, comment);
			return output;
		}
		
		//parsing the components of the intermediate test file separated by tabs
		String output = String.format("%04X\t%s\t%s\t%s\t%s\t%s", location, label, operation, operand, objCode, comment);
		return output;
	}
	
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

