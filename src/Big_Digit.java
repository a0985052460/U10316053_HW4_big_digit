//U10316053 µ{¬fºú
import java.util.*;
public class Big_Digit {
	public List<Integer> a;
	
	public Big_Digit(String int1){
		boolean positive=true;//+
		String h;
		if(int1.charAt(0) == '-'){
			h=int1.substring(1);
			positive=false;//-
		}
		else h=int1;//h=a unsign Big Digit
		
		a = new ArrayList<>();
		
		for(int i = h.length() - 4; i > -4; i -= 4) {
			a.add(Integer.parseInt(h.substring(i >= 0 ? i : 0, i + 4)));//if(i> = 0) i,i+4;  else 0,i+4;
		}
		
		int b=(a.size()/8+1)*8;
		for(int i = a.size(); i < b; i++) {
            a.add(0);
        }//if less than 8 digit fill up with 0
		
		if(!positive){//if not positive , a goes to Complement
			List<Integer> a_comp = new ArrayList<>();
	        for(Integer i:a){
	        	a_comp.add(9999 - i);
	        }
	        a_comp.set(0, a_comp.get(0) + 1);
	        a=a_comp;
		}
	}
	
	
	
	
	public String toshow(String A){
		return A;
	}
	public String add(String x,String y){
		return x+y;
	}
	
	public String sub(String x,String y){
		return x+y;
	}
	
	public String mul(String x,String y){
		return x+y;
	}
	
	public String div(String x,String y){
		return x+y;
	}
	
	public static void main(String[] args) {
		//Big_Digit x=new Big_Digit("-10000000000000000000000000000000000000000000000000");
		Big_Digit x=new Big_Digit("-1000");
		//Big_Digit y=new Big_Digit("-10");
		//System.out.println(x.add(x,y));
		//System.out.println(x);
		//System.out.println(x);
		//System.out.println(x);
	}
}
