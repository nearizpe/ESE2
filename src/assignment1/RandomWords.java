package assignment1;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class RandomWords {
	private final List<String> sourceWords = new ArrayList<String>();
	private static final String WORD_REGEXP = "[- .:,]+";

	
	/**
	 * Constructor
	 * 
	 * @throws IOException
	 *             If the source words file cannot be read
	 */
	//@SuppressWarnings("resource")
	public RandomWords() throws IOException {
		//String path = "C:\Users\neari\Desktop\Enterprise\Assiginment 2\ESE2\src\assignment1\words.txt";
		/*File file = new File("C:\Users\neari\Desktop\Enterprise\Assiginment 2\ESE2\src\assignment1\words.txt");
		BufferedReader reader = null;
		try{
		reader = new BufferedReader(new FileReader(file));
		String text = null;
		while((text = reader.readLine()) != null){
			sourceWords.add(text);
			
		}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		} finally {
			try {
				if (reader != null){
					reader.close();
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}*/
		try{
		Path filePath = Paths.get("words.txt");
		Scanner scanner = new Scanner(filePath);
		while (scanner.hasNext()) {
		    if (scanner.hasNextInt()) {
		        sourceWords.add(scanner.next());
		    } else {
		        scanner.next();
		    }
		}
		}catch(FileNotFoundException e){
			e.printStackTrace();
			
		}
	}
	
	public String getWord(int i){
		return sourceWords.get(i);
	}
}