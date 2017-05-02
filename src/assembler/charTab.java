package assembler;

import java.util.ArrayList;

public class charTab {
	ArrayList <character> chars;
	
	charTab(){
		chars = new ArrayList <character>();
		chars.add(new character(48,"0"));
		chars.add(new character(49,"1"));
		chars.add(new character(50,"2"));
		chars.add(new character(51,"3"));
		chars.add(new character(52,"4"));
		chars.add(new character(53,"5"));
		chars.add(new character(54,"6"));
		chars.add(new character(55,"7"));
		chars.add(new character(56,"8"));
		chars.add(new character(57,"9"));
		chars.add(new character(58,":"));
		chars.add(new character(59,";"));
		chars.add(new character(60,"<"));
		chars.add(new character(61,"="));
		chars.add(new character(62,">"));
		chars.add(new character(63,"?"));
		chars.add(new character(64,"@"));
		chars.add(new character(65,"A"));
		chars.add(new character(66,"B"));
		chars.add(new character(67,"C"));
		chars.add(new character(68,"D"));
		chars.add(new character(69,"E"));
		chars.add(new character(70,"F"));
		chars.add(new character(71,"G"));
		chars.add(new character(72,"H"));
		chars.add(new character(73,"I"));
		chars.add(new character(74,"J"));
		chars.add(new character(75,"K"));
		chars.add(new character(76,"L"));
		chars.add(new character(77,"M"));
		chars.add(new character(78,"N"));
		chars.add(new character(79,"O"));
		chars.add(new character(80,"P"));
		chars.add(new character(81,"Q"));
		chars.add(new character(82,"R"));
		chars.add(new character(83,"S"));
		chars.add(new character(84,"T"));
		chars.add(new character(85,"U"));
		chars.add(new character(86,"V"));
		chars.add(new character(87,"W"));
		chars.add(new character(88,"X"));
		chars.add(new character(89,"Y"));
		chars.add(new character(90,"Z"));
		chars.add(new character(91,"["));
		chars.add(new character(92,"\\"));
		chars.add(new character(93,"]"));
		chars.add(new character(94,"^"));
		chars.add(new character(95,"_"));
		chars.add(new character(96,"`"));
		chars.add(new character(97,"a"));
		chars.add(new character(98,"b"));
		chars.add(new character(99,"c"));
		chars.add(new character(100,"d"));
		chars.add(new character(101,"e"));
		chars.add(new character(102,"f"));
		chars.add(new character(103,"g"));
		chars.add(new character(104,"h"));
		chars.add(new character(105,"i"));
		chars.add(new character(106,"j"));
		chars.add(new character(107,"k"));
		chars.add(new character(108,"l"));
		chars.add(new character(109,"m"));
		chars.add(new character(110,"n"));
		chars.add(new character(111,"o"));
		chars.add(new character(112,"p"));
		chars.add(new character(113,"q"));
		chars.add(new character(114,"r"));
		chars.add(new character(115,"s"));
		chars.add(new character(116,"t"));
		chars.add(new character(117,"u"));
		chars.add(new character(118,"v"));
		chars.add(new character(119,"w"));
		chars.add(new character(120,"x"));
		chars.add(new character(121,"y"));
		chars.add(new character(122,"z"));
		chars.add(new character(123,"{"));
		chars.add(new character(124,"|"));
		chars.add(new character(125,"}"));
		chars.add(new character(126,"~"));
	}
	
	public int getIndex(String c){
		int b = 0;
		for(int i = 0; i<chars.size(); i++){
			character a = chars.get(i);
			if(c.contentEquals(a.name)){
				b = i;
			}
		}
		return b;
	}
}
