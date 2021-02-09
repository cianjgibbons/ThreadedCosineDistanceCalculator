package ie.gmit.dip;

import java.util.*;

/**
 * The Calculator class is a concrete class that implements its own method to
 * calculate the cosine similarity of two given vectors.
 * 
 * @author Cian Gibbons G00387923
 * 
 * @version 1.0
 */

public class Calculator {

	private Map<Integer, Integer> queryMap = new LinkedHashMap<>();
	private Map<Integer, Integer> subjectMap = new LinkedHashMap<>();
	private Set<Integer> intersection = new HashSet<>();

	/**
	 * Takes in the subject and query hash maps and uses their key sets and
	 * corresponding frequency value to calculate the cosine distance between the
	 * two files.
	 * 
	 * @param query defines the query map vector.
	 * @param subject defines the subject map vector.
	 * @return the cosine similarity of the two parameters as a value between zero
	 *         and one.
	 */

	public double cosineSimilarity(Map<Integer, Integer> query, Map<Integer, Integer> subject) {

		this.queryMap = query;
		this.subjectMap = subject;

		// Creates a hash set of the keys in the query map vector and retains those that
		// are also contained in
		// the subject map vector.
		intersection = new HashSet<>(queryMap.keySet());
		intersection.retainAll(subjectMap.keySet());

		double dotProduct = 0, magnitudeOfQuery = 0, magnitudeOfSubject = 0;

		// For-each loop traverses each map vector to get frequency (value) of each hash
		// code (key) using .get().
		// Calculates the dot product - the sum of the frequencies common to both
		// vectors multiplied by each other
		for (Integer value : intersection) {
			dotProduct += queryMap.get(value) * subjectMap.get(value);
		}

		// Calculates the magnitude of the query and subject key sets respectively by
		// traversing across the key set using a for-each loop and using .get() 
		// to retrieve the corresponding value for each key
		for (Integer i : queryMap.keySet()) {
			magnitudeOfQuery += Math.pow(queryMap.get(i), 2);
		}

		for (Integer i : subjectMap.keySet()) {
			magnitudeOfSubject += Math.pow(subjectMap.get(i), 2);
		}

		// Returns the cosine distance
		return dotProduct / Math.sqrt(magnitudeOfQuery * magnitudeOfSubject);

	}
}
