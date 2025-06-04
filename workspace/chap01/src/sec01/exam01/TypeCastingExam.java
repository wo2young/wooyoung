package sec01.exam01;

public class TypeCastingExam {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int intValue = 10;
		byte byteValue = (byte) intValue;
		
		intValue = 200;
		byteValue = (byte) intValue;
		System.out.println(byteValue);
		
		double d =3.14;
		int i = (int) d;
		System.out.println(i);
		// int는 truncate같이 소수점 뒤에 수를 버린다.
		
		int i2 = 100;
		long L1 = i2;
		System.out.println(L1);
		
		int i3 = 3;
		long l3 = 4L;
		long i4 = i3 + l3;
//		long i4 = (long)i3 + l3;
		System.out.println(i4);
		
		int i5 = 10;
		double d2 = 5.5;
		double d3 = i5 + d2;
		System.out.println(d3);
		
		double d4 = 10/4;
		System.out.println(d4);
		
		double d5 = (double)10 / 4;
		System.out.println(d5);
		
		String s1 = "123";
//		int i8 = s1;
		int i8 = Integer.parseInt(s1);
		// 문자를 숫자로 바꿔준다.
		
		// 가장 쉽게 숫자를 문자로 바꾸는 방법
		String s2 = ""+i8;
		System.out.println(s2);
		
		double z = 5.6; 
		double	z1 = 4;
		double	z2 = z / z1;
		System.out.println("인당" + (z2*10000) + "원"); //10750원
		int	z3 = (int)z2;
		System.out.println("인당" + (z2*10000) + "원");
		double z4 = (double) z3;
		System.out.println("참석인원은" + (z4*10000) + "원" ); //10000원
		double	z5 = z1-1; 
		double z6 =  (z - (z5 * z4))*10000;
		long rounded_z6 = Math.round(z6);
		System.out.println("나는" + rounded_z6 + "원" ); //13000원
		
				
		
		
		
	}

}
