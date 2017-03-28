package assembler;

public class location {
	Integer locator;
	
	location(Integer l){
		locator = l;
	}
	
	void update(String operator, opTab operators, String operand){
		if(operators.hasOp(operator)){
			locator =  locator + operators.getFormat(operator);
		}
		else{
			String hold = operator.substring(0,1);
			if(hold.equals("+")){
				locator = locator+4;
			}
			if(operator.equals("WORD")){
				locator = locator+3;
			}
			if(operator.equals("RESW")){
				locator = locator + 3*(Integer.valueOf(operand));
			}
			if(operator.equals("RESB")){
				locator =  locator + Integer.valueOf(operand);
			}
			/*circle back to
			 * if(operator.equals("BYTE")){
			 
				
			}*/
		}
	
	}
	
}
