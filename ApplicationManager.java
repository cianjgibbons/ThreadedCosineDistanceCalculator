package ie.gmit.dip;

import java.util.*;
import java.text.*;

/**
 * The ApplicationManager class implements the methods defined by
 * ApplicationInterface,and Runnable, in order to thread the application. It
 * contains the getters, setters and constructors required for the correct
 * function of the application.
 * 
 * @author Cian Gibbons G00387923
 * @version 1.0
 */

public class ApplicationManager implements ApplicationInterface, Runnable {

	private Scanner scanner;
	private volatile String queryFile;
	private String subjectFile;
	private double result;

	private volatile Map<Integer, Integer> queryMap = new LinkedHashMap<>();
	private Map<Integer, Integer> subjectMap = new LinkedHashMap<>();

	private SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private Date date = new Date();

	private FileConnection fc;
	private FileConnection fc1;
	private Calculator c;

	/**
	 * The start() method initiates the application once it is called by the main
	 * method.
	 * 
	 * @throws InterruptedException if a thread is interrupted.
	 */

	public void start() throws InterruptedException {

		menu();

		txtFileToMap();

		similarityCalculation();

		displayResult();

	}

	/**
	 * This displays the menu and requests user input in order to define the query
	 * and subject text files to be compared.
	 */

	public void menu() {
		
		scanner = new Scanner(System.in);

		System.out.println("************************* Document Comparison System *************************");
		System.out.print("Enter Query File>");
		setQueryFile(scanner.next());
		System.out.print("Enter Subject File>");
		setSubjectFile(scanner.next());
		System.out.println("...Processing...please wait...");

	}

	/**
	 * Processes both text files and converts them to hash maps using the
	 * FileConnection class. Threading is used to parse the query and subject files
	 * concurrently, on their own individual stacks.
	 */

	public void txtFileToMap() throws InterruptedException {
		
		Thread thread = new Thread(this);
		thread.start();

		this.fc = new FileConnection();
		this.setSubjectMap(fc.fileToMap(this.getSubjectFile()));

		thread.join();
	}

	/**
	 * Creates a new instance of the Calculator class, takes in the
	 * query and subject hash maps as parameters, and carries out the cosine
	 * similarity calculation to determine the similarity of the two files.
	 */

	public void similarityCalculation() {
		
		this.c = new Calculator();
		this.setResult(c.cosineSimilarity(this.getQueryMap(), this.getSubjectMap()));
	}

	/**
	 * Displays the result of the cosine similarity calculation as a
	 * percentage, once two valid files have been parsed. It also provides
	 * additional time and date functionality that adds authenticity to the result.
	 */

	public void displayResult() {
		
		System.out.println("------------------------------------------------------------------------------");
		
		// Double wrapper class used to verify that a valid number has been returned from the calculation
		// prior to the result being displayed
		if (Double.isNaN(getResult())) {
			System.out.println("Similarity could not be established");
		} else if (this.getResult() == 1.00) {
			System.out.println("Query file & subject file are identical");
		} else {
			System.out.println("Similarity: " + (this.getResult() * 100) + "%");
		}
		System.out.println("------------------------------------------------------------------------------");
		System.out.println();
		System.out.println("Similarity comparison carried out: " + f.format(date));
		System.out.println();

	}

	/**
	 * Implementation of the run method defined by Runnable using a Thread. This
	 * method creates a new instance of the FileConnection class and parses the
	 * contents of the query file to a hash map, concurrently to the main stack.
	 */

	public void run() {
		
		this.fc1 = new FileConnection();
		this.setQueryMap(fc1.fileToMap(this.getQueryFile()));

	}
	
	/**
	 * 
	 * @return the query text file.
	 */

	public String getQueryFile() {
		return this.queryFile;
	}
	
	/**
	 * @param queryFile sets the name of the query text file.
	 */

	public void setQueryFile(String queryFile) {
		this.queryFile = queryFile;
	}

	/**
	 * @return the subject text file.
	 */

	public String getSubjectFile() {
		return this.subjectFile;
	}
	
	/**
	 * @param subjectFile sets the name of the subject text file.
	 */

	public void setSubjectFile(String subjectFile) {
		this.subjectFile = subjectFile;
	}

	/**
	 * @return the query hash map.
	 */

	public Map<Integer, Integer> getQueryMap() {
		return this.queryMap;
	}
	
	/**
	 * @param queryMap sets the hash map created as a result of parsing the query
	 *                 text file.
	 */

	public void setQueryMap(Map<Integer, Integer> queryMap) {
		this.queryMap = queryMap;
	}

	/**
	 * @return the subject hash map.
	 */

	public Map<Integer, Integer> getSubjectMap() {
		return this.subjectMap;
	}
	
	/**
	 * @param subjectMap sets the hash map created as a result of parsing the
	 *                   subject text file.
	 */

	public void setSubjectMap(Map<Integer, Integer> subjectMap) {
		this.subjectMap = subjectMap;
	}

	/**
	 * @return the result of the cosine similarity calculation.
	 */

	public double getResult() {
		return this.result;
	}
	
	/**
	 * @param result sets the result of the cosine similarity calculation.
	 */

	public void setResult(double result) {
		this.result = result;
	}

}
