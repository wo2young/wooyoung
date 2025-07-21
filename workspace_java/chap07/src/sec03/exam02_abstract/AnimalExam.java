package sec03.exam02_abstract;

public class AnimalExam {
	
	public static void main(String[] args) {
		
		Animal a = new Dog();
		a.sound();
		Dog dog = (Dog)a;
		
		System.out.println(dog.kind);
		testSound(dog);
		System.out.println(dog.kind);
	}
	static void testSound(Animal animal) {
		animal.sound();
		animal.kind = "개";
	}
}
