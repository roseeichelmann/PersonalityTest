/**
 * CS312 Assignment 8.
 * 
 * On my honor, Rose Eichelmann, this programming assignment is my own work and I have
 * not shared my solution with any other student in the class.
 *
 * A program to read a file with raw data from a Keirsey personality test
 * and print out the results.
 *
 *  email address: rose.eichelmann@gmail.com
 *  UTEID: ree585
 *  Unique 5 digit course ID: 52205
 *  Grader name: Tejna
 *  Number of slip days used on this assignment: 2
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Personality {
    
	// Class constants
	public static int NUM_GROUPS = 10;
	public static int NUM_QUESTIONS_PER_GROUP = 7;
	public static int NUM_DIMENSIONS = 4;
	public static int E_I_Dimension = 0;
	public static int S_N_DIMENSION = 1;
	public static int T_F_DIMENSION = 2;
	public static int J_P_DIMENSION = 3;
	public static int INDEX_QUESTION_E_I = 0;
	public static int INDEX_QUESTION_S_N_1 = 1;
	public static int INDEX_QUESTION_S_N_2 = 2;
	public static int INDEX_QUESTION_T_F_1 = 3;
	public static int INDEX_QUESTION_T_F_2 = 4;
	public static int INDEX_QUESTION_J_P_1 = 5;
	public static int INDEX_QUESTION_J_P_2 = 6;
	
    /* 
     * The main method to process the data from the personality tests
     * and return results for each person's personality.
     */
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        Scanner fileScanner = getFileScanner(keyboard);
        // Process data from the file
        readFile(fileScanner);
        fileScanner.close();
        keyboard.close();
    }

    /* 
     * Method to choose a file.
     * Asks user for name of file. 
     * If file not found creates a Scanner hooked up to a dummy set of data.
    */
    public static Scanner getFileScanner(Scanner keyboard) {
        Scanner result = null;
        try {
            System.out.print("Enter the name of the file with the personality data: ");
            String fileName = keyboard.nextLine().trim();
            System.out.println();
            result = new Scanner(new File(fileName));
        }
        catch(FileNotFoundException e) {
            System.out.println("Problem creating Scanner: " + e);
            System.out.println("Creating Scanner hooked up to default data " + e);
            String defaultData = "1\nDEFAULT DATA\n"
                + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
                + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
            result = new Scanner(defaultData);
        }
        return result;
    }
    
    /* 
     * Reads file
     */
    public static void readFile(Scanner fileScanner) {
    	int numResults = fileScanner.nextInt();
    	fileScanner.nextLine();
    	// Iterates through each result in file
    	for(int result = 0; result < numResults; result++) {
    		// Saves name as variable
    		String name = fileScanner.nextLine();
    		// Saves person's answers as variable
    		String questionAnswers = fileScanner.nextLine();
    		questionAnswers = questionAnswers.toLowerCase();
    		// Calls method to do result calculations
    		resultCalculation(questionAnswers, name);
    	}
    }
    
    /*
     * Does calculations on result in order to find the percentage of B answers
     * for each personality dimension.
     */
    public static void resultCalculation (String questionAnswers, String name) {
    	int[] answersA = new int[NUM_DIMENSIONS];
    	int[] answersB = new int[NUM_DIMENSIONS];
    	String[] answer = new String[NUM_GROUPS];
    	int beginIndex = 0;
    	int endIndex = NUM_QUESTIONS_PER_GROUP;
    	// Splits answer result into 10 groups with 7 questions each and stores group in array
    	for(int index = 0; index < NUM_GROUPS; index++) {
    		// Splits the string into a substring of 7 characters
    		answer[index] = questionAnswers.substring(beginIndex, endIndex);
    		beginIndex += NUM_QUESTIONS_PER_GROUP;
    		endIndex += NUM_QUESTIONS_PER_GROUP;
    	}
    	// Calls method to create array with # 'a' responses for each dimension
    	resultA(answer, answersA);
    	// Calls method to create array with # 'b' responses for each dimension
    	resultB(answer, answersB);
    	// Calls method that returns array with rounded percentage of 'b's for each dimension
    	int[] roundedPercentB = calcPercentage(answersA, answersB);
    	// Calls method to print the results of the personality tests
    	printResults(name, roundedPercentB, answersA);
    	System.out.println();
    }	
    
    /* 
     * Creates array which holds the number of 'a' 
     * responses for each dimension.
     */
    public static void resultA(String[] answer, int[] answersA) {
    	// Iterates through each group of 7 questions
    	for(int group = 0; group < NUM_GROUPS; group++) {
    		String answers = answer[group];
    		String [] letters = answers.split("");
    		// Iterates through each letter and adds to array holding # a's for each dimension
    		for(int index = 0; index < NUM_QUESTIONS_PER_GROUP; index++) {
    			if((letters[index]).equals("a")) {
    				if(index == INDEX_QUESTION_E_I) {
    					answersA[E_I_Dimension]++;
    				}
    				else if((index == INDEX_QUESTION_S_N_1) || (index == INDEX_QUESTION_S_N_2)) {
    					answersA[S_N_DIMENSION]++;
    				}
    				else if((index == INDEX_QUESTION_T_F_1) || (index == INDEX_QUESTION_T_F_2)) {
    					answersA[T_F_DIMENSION]++;
    				}
    				else if((index == INDEX_QUESTION_J_P_1) || (index == INDEX_QUESTION_J_P_2)) {
    					answersA[J_P_DIMENSION]++;
    				}
    			}
    		}
    	}
    }
    
    /* 
     * Creates array which holds the number of 'a' 
     * responses for each dimension.
     */
    public static void resultB(String[] answer, int[] answersB) {

    	// Iterates through each group of 7 questions
    	for(int group = 0; group < NUM_GROUPS; group++) {
    		String answers = answer[group];
    		String [] letters = answers.split("");
    		// Iterates through each letter and adds to array holding # b's for each dimension
    		for(int index = 0; index < NUM_QUESTIONS_PER_GROUP; index++) {
    			if((letters[index]).equals("b")) {
    				if(index == INDEX_QUESTION_E_I) {
    					answersB[E_I_Dimension]++;
    				}
    				else if((index == INDEX_QUESTION_S_N_1) || (index == INDEX_QUESTION_S_N_2)) {
    					answersB[S_N_DIMENSION]++;
    				}
    				else if((index == INDEX_QUESTION_T_F_1) || (index == INDEX_QUESTION_T_F_2)) {
    					answersB[T_F_DIMENSION]++;
    				}
    				else if((index == INDEX_QUESTION_J_P_1) || (index == INDEX_QUESTION_J_P_2)) {
    					answersB[J_P_DIMENSION]++;
    				}
    			}
    		}
    	}
    }
    
    /*
     * Returns the percentage of 'b's for each dimension
     */
    public static int[] calcPercentage(int[] answersA, int[] answersB) {
    	double rounder = 0.5;
    	double[] percentB = new double[NUM_DIMENSIONS];
    	int[] roundedPercentB = new int[NUM_DIMENSIONS];
    	int decimalToPercent = 100;
    	// Iterates through each dimension and calculates percentage of 'b's
    	for(int index = 0; index < NUM_DIMENSIONS; index++) {
    		int sumAnswers = (answersA[index] + answersB[index]);
    		percentB[index] = ((double)(answersB[index])/(sumAnswers) * decimalToPercent);
    		roundedPercentB[index] = ((int) (percentB[index] + rounder));
    	}
    	return roundedPercentB;
    }

    /*
     * Prints the results of the personality tests
     */
    public static void printResults(String name, int[] roundedPercentB, int[] answersA) {
    	int maxNumCharacters = 30;
    	int numCharactersWide = 31;
    	// Prints name according to formatting rules
    	if(name.length() > maxNumCharacters) {
    		System.out.print(name + ":");
    	} else {
    		System.out.printf("%" + numCharactersWide+ "s", name + ":");
    	}
    	// Calls method printing the percentages of b's for each dimension
    	printPercentages(roundedPercentB, answersA);
    	System.out.print(" = ");
    	// Calls method determining personality type of each person and prints it
    	determinePersonality(roundedPercentB, answersA);
    }
    
    /*
     * Determines the personality type of the person and prints it
     */
    public static void determinePersonality(int[] roundedPercentB, int[] answersA) {
    	int tie = 50;
    	// Iterates through # of dimensions and prints personality trait for that dimension
    	for(int index = 0; index < NUM_DIMENSIONS; index++) {
    		if((roundedPercentB[index] == 0) & (answersA[index] == 0)) {
    			System.out.print("-");
    		} else if(roundedPercentB[index] > tie) {
    			if(index == E_I_Dimension) {
    				System.out.print("I");
    			}
    			else if(index == S_N_DIMENSION) {
    				System.out.print("N");
    			} else if(index == T_F_DIMENSION) {
    				System.out.print("F");
    			} else if(index == J_P_DIMENSION) {
    				System.out.print("P");
    			}
    		} else if(roundedPercentB[index] < tie) {
    			if(index == E_I_Dimension) {
    				System.out.print("E");
    			}
    			else if(index == S_N_DIMENSION) {
    				System.out.print("S");
    			} else if(index == T_F_DIMENSION) {
    				System.out.print("T");
    			} else if(index == J_P_DIMENSION) {
    				System.out.print("J");
    			}
    		} else if(roundedPercentB[index] == tie) {
    			System.out.print("X");
    		}
    	}
    }
    
    /*
     * Prints the percentage of b answers per dimension
     */
    public static void printPercentages(int[] roundedPercentB, int[] answersA) {
    	int numCharactersWide = 11;
    	// Iterates through each dimension and prints percent b answers for that dimension
    	for(int index = 0; index < NUM_DIMENSIONS; index++) {
    		if((roundedPercentB[index] == 0) & (answersA[index] == 0)) {
    			System.out.print(" NO ANSWERS");
    		} else {
    			System.out.printf("%" + numCharactersWide + "d", roundedPercentB[index]);
    		}
    	}
    }
}
