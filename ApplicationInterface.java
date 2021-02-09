package ie.gmit.dip;

/**
 * The ApplicationInterface interface defines the methods that are required to
 * be implemented by the ApplicationManager.
 * 
 * @author Cian Gibbons G00387923
 * @version 1.0
 */

public interface ApplicationInterface {
	
	public void menu();

	public void txtFileToMap() throws InterruptedException;

	public void similarityCalculation();

	public void displayResult();

}
