package assembler;

public class op {
	String mnemonic;
	Integer format;
	Integer opcode;
	
	op(String m, Integer f, Integer o){
		mnemonic = m;
		format = f;
		opcode = o;
	}
}
