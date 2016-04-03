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
		
		for(int i=h.length()-1;i>0;i--){
			a.add(Integer.parseInt(h.substring(i)));
			
		}
		//for(int i=0;i<10;i++){
			System.out.println(a);
		//}
		
		
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
		Big_Digit x=new Big_Digit("-10000");
		//Big_Digit y=new Big_Digit("-10");
		//System.out.println(x.add(x,y));
		//System.out.println(x);
		//System.out.println(x);
		//System.out.println(x);
	}
}
