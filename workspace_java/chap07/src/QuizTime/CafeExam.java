package QuizTime;

public class CafeExam {
	public static void main(String[] args) {
	
		Compoce compoce = new Compoce();
		Starbucks starbucks = new Starbucks();
		Alba alba = new Alba();
		
		
		compoce.open();
		alba.CompoceHi(compoce);	
		compoce.order();
		compoce.pay();
		compoce.make();
		compoce.serving();
		compoce.wash();
		compoce.close();
		
		starbucks.open();
		alba.StarbucksHi(starbucks);	
		starbucks.order();
		starbucks.pay();
		starbucks.make();
		starbucks.serving();
		starbucks.wash();
		starbucks.close();
		
	}
}
