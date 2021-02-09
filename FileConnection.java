package ie.gmit.dip;

import java.io.*;
import java.util.*;

/**
 * The FileReader class is a concrete class that contains the functionality to
 * parse text files.
 * 
 * @author Cian Gibbons G00387923
 * @version 1.0
 */

public class FileConnection {

	private Map<Integer, Integer> fileMap = new LinkedHashMap<Integer, Integer>();

	/**
	 * Takes in a text file as a string using a BufferedReader/InputStreamReader/FileInputStream
	 * and splits its contents into a string array. It then gets the hash code of each
	 * string in the array, and stores them in a hash map alongside their
	 * corresponding frequency.
	 * 
	 * @param file the text file to be converted to hash map
	 * @return the hash map of the parsed text file
	 */

	public Map<Integer, Integer> fileToMap(String file) {

		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {

			String line = null;

			while ((line = br.readLine()) != null) {

				// A regex expression that splits the line into 10 character segments and
				// stores them in a string array.
				String[] words = line.split("(?<=\\G.{9})");

				// for-each loop used to traverse the array, retrieve the hashCode of each
				// string and add it to the hash map with the corresponding frequency.
				for (String word : words) {
					int hash = word.hashCode();
					int frequency = 1;
					if (this.fileMap.containsKey(hash)) {
						frequency += fileMap.get(hash);
					}
					fileMap.put(hash, frequency);
				}
			}
			br.close();
		} catch (Exception e) {
			System.out.println();
			System.out.println("[ERROR] Invalid File: " + file);
		}
		return fileMap;
	}

}