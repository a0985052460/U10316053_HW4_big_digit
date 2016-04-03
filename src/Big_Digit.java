//U10316053 祘琭胡
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
	
	private Big_Digit(List<Integer> value) {
    	a = value;
	}
	
	
	//the following reference from: http://openhome.cc/Gossip/AlgorithmGossip/BigNumber.htm
	
	
	public Big_Digit add(Big_Digit that){
		
		if(isNegative(that.a)) {
            return sub(new Big_Digit(toComplement(that.a)));
        }
        // 癸霍计
        int length = Math.max(a.size(), that.a.size());
        List<Integer> op1 = copyOf(a, length);
        List<Integer> op2 = copyOf(that.a, length);
        List<Integer> result = new ArrayList<>();
        
        int carry = 0;
        for(int i = 0; i < length - 1; i++) {
            int c = op1.get(i) + op2.get(i) + carry;
            if(c < 10000) {
                carry = 0;
            } else {
                c -= 10000;
                carry = 1;
            }
            result.add(c);
        }
        
        if(carry == 1) { // 犯矪瞶
            if(isPositive(op1)) { result.add(1); } 
            else { result.clear(); } // 璽计猭笲衡犯碞琌 0
            for(int i = 0; i < 8; i++) { result.add(0); } // 笆糤 8 计
        } else { // 干タ计干 0璽计干 9999
            result.add(isPositive(op1) ? 0 : 9999);
        }
        return new Big_Digit(result);
	}
	
	public Big_Digit sub(Big_Digit that){
		if(isNegative(that.a)) {
            return add(new Big_Digit(toComplement(that.a)));
        }
        // 癸霍计
        int length = Math.max(a.size(), that.a.size());
        List<Integer> op1 = copyOf(a, length);
        List<Integer> op2 = copyOf(that.a, length);
        List<Integer> result = new ArrayList<>();
        
        int borrow = 0;
        for(int i = 0; i < length - 1; i++) { 
            int c = op1.get(i) - op2.get(i) - borrow; 
            if(c > -1) {
                borrow = 0;
            } else { //  
                c += 10000; 
                borrow = 1; 
            }
            result.add(c);
        }
        
        if(borrow == 1) { // 犯矪瞶
            if(isNegative(op1)) { result.add(9998); } 
            else { result.clear(); } // タ计搭猭笲衡犯碞琌 0
            for(int i = 0; i < 8; i++) { result.add(9999); } // 笆糤 8 计
        } else {  // 干璽计干 9999タ计干 0
            result.add(isNegative(op1) ? 9999 : 0);
        }
        
        return new Big_Digit(result);
	}
	
	
	
	private Big_Digit mul(int val, int shift){
		List<Integer> result = new ArrayList<>();
        for(int i = 0; i < shift; i++) { result.add(0); } // 簿干 0
        int carry = 0;
        for(int i = 0; i < a.size() - 1; i++) {
            int tmp = a.get(i) * val + carry;
            result.add(tmp % 10000);
            carry = tmp / 10000;
        }
        if(carry != 0) {
            result.add(carry);
            for(int i = 0; i < 8; i++) { result.add(0); }
        } else { result.add(0); }

        return new Big_Digit(result);
	}
	
	public Big_Digit mul(Big_Digit that){
		Big_Digit op1 = isNegative(a) ? new Big_Digit(toComplement(a)) : this;
		List<Integer> op2 = isNegative(that.a) ? toComplement(that.a) : that.a;
		// 硋笲衡
		List<Big_Digit> rs = new ArrayList<>();
		for(int i = 0; i < op2.size() - 1; i++) {
		 rs.add(op1.mul(op2.get(i), i));
		}
		// 癸硋笲衡挡狦羆
		Big_Digit result = rs.get(0);
		for(int i = 1; i < rs.size(); i++) {
		 result = result.add(rs.get(i));
		}
		// 耞タ璽计
		return getLast(a) + getLast(that.a) == 9999 ? new Big_Digit(toComplement(result.a)) : result;
	}
	
	
	
	
	private Big_Digit div(int that){
		List<Integer> result = new ArrayList<>();
        int remain = 0;
        for(int i = a.size() - 1; i > -1; i--) {
            int tmp = a.get(i) + remain;
            result.add(tmp / that);
            remain = (tmp % that) * 10000;
        }
        Collections.reverse(result);
        for(int i = 0; i < 8 - (result.size() % 8); i++) {
            result.add(0);
        }
        return new Big_Digit(result);
	}
	public Big_Digit div(Big_Digit that){
		Big_Digit op1 = isNegative(a) ? new Big_Digit(toComplement(a)) : this;
		Big_Digit op2 = isNegative(that.a) ? new Big_Digit(toComplement(that.a)) : that;

		Big_Digit one = new Big_Digit("1");
		Big_Digit left = new Big_Digit("0");
		Big_Digit right = op1;

		// だ猭穓碝 x.islessOrEqualsToQuotient(op1, op2)  true 程 x 
		while(right.greaterOrEquals(left)) {
			Big_Digit x = left.add(right).div(2);
		if(x.islessOrEqualsToQuotient(op1, op2)) {
		   left = x.add(one);
		} else {
		   right = x.sub(one);
		}
		}
		Big_Digit result = left.sub(one);

		// 耞タ璽计
		return getLast(a) + getLast(that.a) == 9999 ? new Big_Digit(toComplement(result.a)) : result;
	}
	
	
	
	
	
	
	public boolean greaterOrEquals(Big_Digit that) {
        return isNegative(sub(that).a) ? false : true;
    }
    
    private boolean islessOrEqualsToQuotient(Big_Digit op1, Big_Digit op2) {
        return op1.greaterOrEquals(mul(op2)) ? true : false;
    }
	
	
	
	public String toString() {
        // タ计ボ
        List<Integer> v = isNegative(a) ? toComplement(a) : a;
        StringBuilder builder = new StringBuilder();
        for(int i = v.size() - 1; i > -1; i--) {
            builder.append(String.format("%04d", v.get(i)));
        }
        // 簿玡狠 0璽计干璽腹
        while(builder.length() > 0 && builder.charAt(0) == '0') {
            builder.deleteCharAt(0);
        }
        return builder.length() == 0 ? "0" : isNegative(a) ? builder.insert(0, '-').toString() : builder.toString();
    }
        
    private static List<Integer> toComplement(List<Integer> v) {
        List<Integer> comp = new ArrayList<>();
        for(Integer i : v) { comp.add(9999 - i); }
        comp.set(0, comp.get(0) + 1);
        return comp;
    }
    
    private static List<Integer> copyOf(
                List<Integer> original, int newLength) {
        List<Integer> v = new ArrayList<>(original);
        for(int i = v.size(); i < newLength; i++) {
            v.add(isPositive(original) ? 0 : 9999);
        }
        return v;
    }
    
    private static Integer getLast(List<Integer> list) {
        return list.get(list.size() - 1);
    }
    
    private static boolean isNegative(List<Integer> list) {
        return getLast(list) == 9999;
    }
    
    private static boolean isPositive(List<Integer> list) {
        return getLast(list) == 0;
    }
    
    private static boolean isZero(List<Integer> list) {
        for(Integer i : list) if(i != 0) {
            return false;
        }
        return true;
    }
	
	public static void main(String[] args) {
		Big_Digit x=new Big_Digit("10000000000000000000000000000000000000000000000000");
		Big_Digit y=new Big_Digit("-500000");
		System.out.println(x+"+"+y+"="+x.add(y));
		System.out.println(x+"-"+y+"="+x.sub(y));
		System.out.println(x+"*"+y+"="+x.mul(y));
		System.out.println(x+"/"+y+"="+x.div(y));
	}
}
