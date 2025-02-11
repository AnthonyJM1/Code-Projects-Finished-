
/*Anthony Molina
 * CS1450
 * February 12, 2025
 * Assignment 3
 * In this assignment we are asked to access information from a file and import it into a polymorphic array for task one. The second task is meant to use a method that passes the 
 * animal objects within the array and finds which ones have the interface climbers associated with them for a new concept ArrayLists and display them. The last task is a method 
 * that will run through each of the animals array and see which one of them has the highest skill level in regards to a total amount of each of the activities recorded at mph, and 
 * display the most skilled animal.
 */
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MolinaAnthonyAssignment3 {

	public static void main(String[] args) throws IOException {
		// Task 1: Reading and storing file into a polymorphic array
		final String FILE_NAME = "Animals.txt";

		File inputFileName = new File(FILE_NAME);
		Scanner inputFile = new Scanner(inputFileName);

		int numAnimals = inputFile.nextInt();
		Animal[] animals = new Animal[numAnimals];
		// Reading through each line appropriately
		for (int i = 0; i < animals.length; i++) {
			String name = inputFile.next();
			String species = inputFile.next();
			int swimSpeed = inputFile.nextInt();
			int runSpeed = inputFile.nextInt();
			int climbSpeed = inputFile.nextInt();
			// Switch to designate what kind of animal object will be put into the array
			// spot.
			switch (species) {
			case "alligator":
				animals[i] = new Alligator(name, swimSpeed, runSpeed);
				break;
			case "bear":
				animals[i] = new Bear(name, swimSpeed, climbSpeed, runSpeed);
				break;
			case "giraffe":
				animals[i] = new Giraffe(name, runSpeed);
				break;
			case "monkey":
				animals[i] = new Monkey(name, climbSpeed, runSpeed);
				break;
			case "sloth":
				animals[i] = new Sloth(name, swimSpeed, climbSpeed);
				break;
			}
		}
		inputFile.close();
		// Display Method for all objects.
		System.out.println("---------------------------------------------------------");
		System.out.println("ALL ANIMALS IN ARRAY");
		System.out.println("---------------------------------------------------------");
		displayAnimal(animals);

		// Task 2: Finding Climbers.
		System.out.printf("\n");
		System.out.println("---------------------------------------------------------");
		System.out.println("ANIMALS THAT CAN CLIMB");
		System.out.println("---------------------------------------------------------");
		System.out.printf("%-10s %-10s %-10s\n", "Name", "Species", "Climbing Speed");
		System.out.println("---------------------------------------------------------");
		findClimbers(animals);

		// Task 3: Finding the most skilled animal
		System.out.printf("\n");
		System.out.println("---------------------------------------------------------");
		System.out.println("MOST SKILLED ANIMAL");
		System.out.println("---------------------------------------------------------");
		Animal mostSkilled = findMostSkilled(animals);
		// With the return of what is the mostSkilled animal object and whatever animal
		// it may be display the appropriate information.
		if (mostSkilled != null) {
			System.out.println(
					mostSkilled.getName() + " the " + mostSkilled.getSpecies() + " says " + mostSkilled.makeNoise());
			switch (mostSkilled.getSpecies()) {
			case "Alligator":
				// Casting the interface from the animal object being referenced and displaying
				// the appropriate number recorded for that particular animal.
				System.out.println("Swimming Speed: " + ((Swimmer) mostSkilled).swim());
				System.out.println("Running Speed: " + ((Runner) mostSkilled).run());
				break;
			case "Bear":
				System.out.println("Swimming Speed: " + ((Swimmer) mostSkilled).swim());
				System.out.println("Running Speed: " + ((Runner) mostSkilled).run());
				System.out.println("Climbing Speed: " + ((Climber) mostSkilled).climb());
				break;
			case "Giraffe":
				System.out.println("Running Speed: " + ((Runner) mostSkilled).run());
				break;
			case "Monkey":
				System.out.println("Running Speed: " + ((Runner) mostSkilled).run());
				System.out.println("Climbing Speed: " + ((Climber) mostSkilled).climb());
				break;
			case "Sloth":
				System.out.println("Swimming Speed: " + ((Swimmer) mostSkilled).swim());
				System.out.println("Climbing Speed: " + ((Climber) mostSkilled).climb());
				break;
			}
		} else {
			System.out.println("No animals found.");
		}
	}

	// Display Animals method passing the animals objects and displaying them.
	public static void displayAnimal(Animal[] animals) {
		for (int i = 0; i < animals.length; i++) {
			System.out.println(
					animals[i].getName() + " the " + animals[i].getSpecies() + " says: " + animals[i].makeNoise());

			if (animals[i] instanceof Swimmer) {
				System.out.println("Swimming speed: " + ((Swimmer) animals[i]).swim());
			}
			if (animals[i] instanceof Runner) {
				System.out.println("Running speed: " + ((Runner) animals[i]).run());
			}
			if (animals[i] instanceof Climber) {
				System.out.println("Climbing speed: " + ((Climber) animals[i]).climb());
			}
			System.out.println();
		}
	}

	// Finding Climbers method that passes the animals object array and converting
	// them into a Array List to then be displayed.
	public static ArrayList<Animal> findClimbers(Animal[] animals) {
		ArrayList<Animal> climbers = new ArrayList<>();

		for (Animal animal : animals) {
			if (animal instanceof Climber) {
				climbers.add(animal); // Add climbers to the list
				System.out.printf("%-10s %-10s %-10d\n", animal.getName(), animal.getSpecies(),
						((Climber) animal).climb()); // Display climber info
			}
		}

		return climbers;
	}

	// Finding most skilled animal, running through the animals array and returning
	// an animal under mostSkilled which will later run through
	public static Animal findMostSkilled(Animal[] animals) {
		Animal mostSkilled = null;
		int maxSkills = 0;

		for (int i = 0; i < animals.length; i++) {
			int skillCount = 0;
			if (animals[i] instanceof Swimmer) {
				skillCount = skillCount + ((Swimmer) animals[i]).swim();
			}
			if (animals[i] instanceof Runner) {
				skillCount = skillCount + ((Runner) animals[i]).run();
			}
			if (animals[i] instanceof Climber) {
				skillCount = skillCount + ((Climber) animals[i]).climb();
			}
			if (skillCount > maxSkills) {
				maxSkills = skillCount;
				mostSkilled = animals[i];
			}
		}
		return mostSkilled;
	}
}

// Interfaces for Swimmer, Climber, and Runner.
interface Swimmer {
	int swim();
}

interface Climber {
	int climb();
}

interface Runner {
	int run();
}

// Abstract Animal Class (NO Constructor)
abstract class Animal {
	private String name;
	private String species;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	public abstract String makeNoise();
}

// Animal Subclasses, the Animal SuperClass, is creating a Alligator Subclass that implements the appropriate interfaces.
class Alligator extends Animal implements Swimmer, Runner {
	private int swimSpeed;
	private int runSpeed;

//Using the set methods from superclass to pass the name and species into this specific object passing through
	public Alligator(String name, int swimSpeed, int runSpeed) {
		setName(name);
		setSpecies("Alligator");
		this.swimSpeed = swimSpeed;
		this.runSpeed = runSpeed;
	}

	public String makeNoise() {
		return "Crunch Crunch Crunch";
	}

	// Making the action methods that are called from the interface to complete the
	// animal object with the activities it uses through implementation and finding
	// that specific animals mph ratings.
	public int swim() {
		return swimSpeed;
	}

	public int run() {
		return runSpeed;
	}
}

class Bear extends Animal implements Swimmer, Climber, Runner {
	private int swimSpeed;
	private int climbSpeed;
	private int runSpeed;

	public Bear(String name, int swimSpeed, int climbSpeed, int runSpeed) {
		setName(name);
		setSpecies("Bear");
		this.swimSpeed = swimSpeed;
		this.climbSpeed = climbSpeed;
		this.runSpeed = runSpeed;
	}

	public String makeNoise() {
		return "Growl Growl Growl!!!";
	}

	public int swim() {
		return swimSpeed;
	}

	public int climb() {
		return climbSpeed;
	}

	public int run() {
		return runSpeed;
	}
}

class Giraffe extends Animal implements Runner {
	private int runSpeed;

	public Giraffe(String name, int runSpeed) {
		setName(name);
		setSpecies("Giraffe");
		this.runSpeed = runSpeed;
	}

	public String makeNoise() {
		return "Bleat Bleat Bleat!!!";
	}

	public int run() {
		return runSpeed;
	}
}

class Monkey extends Animal implements Climber, Runner {
	private int climbSpeed;
	private int runSpeed;

	public Monkey(String name, int climbSpeed, int runSpeed) {
		setName(name);
		setSpecies("Monkey");
		this.climbSpeed = climbSpeed;
		this.runSpeed = runSpeed;
	}

	public String makeNoise() {
		return "Screech Screech Screech!!!";
	}

	public int climb() {
		return climbSpeed;
	}

	public int run() {
		return runSpeed;
	}
}

class Sloth extends Animal implements Swimmer, Climber {
	private int swimSpeed;
	private int climbSpeed;

	public Sloth(String name, int swimSpeed, int climbSpeed) {
		setName(name);
		setSpecies("Sloth");
		this.swimSpeed = swimSpeed;
		this.climbSpeed = climbSpeed;
	}

	public String makeNoise() {
		return "Squeak Squeak Squeak";
	}

	public int swim() {
		return swimSpeed;
	}

	public int climb() {
		return climbSpeed;
	}
}
