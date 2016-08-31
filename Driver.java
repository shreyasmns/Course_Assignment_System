import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author ${Shreyas Mahanthappa Nagaraj}
 * 
 */
public class Driver {
	
	public static FileReader reader;
	public static BufferedReader br;
	public static FileWriter fw;
	public static final Integer IN_FILE = 0;
	public static final Integer OUT_FILE = 1;
	
	public static void main(String args[]) {
		Driver dr = new Driver();
		boolean valid = dr.validateInputArguements(args);
		if (valid) {
			boolean validFile = dr.validateInputFile(args);
			if (validFile) {
				String inFile = args[0];
				String outFile   = args[1];
				
				AssignCourses ac = new AssignCourses(inFile, outFile);
				ac.readInput();
				ac.assignCourses();
				ac.closeFile();
			}
		}
	}

	/**
	 * @param inFile
	 * @param outFile
	 */
	
	/*
	 * This method is to verify whether the path provided is actually a file or
	 * not and if yes, it can be read or not
	 * 
	 * @param args
	 * 
	 * @return whether file is valid
	 */
	private boolean validateInputFile(String[] args) {
		File file = new File(args[0]);
		if (file.isFile() && file.canRead() && (file.length() !=0 )) {
			return true;
		} 
		else {
			System.err.println("Input File provided is Invalid one");
			System.exit(1);
		}
		return true;
	}

	/*
	 * This Method vaidates the number of input arguements to the program.
	 * 
	 * @param args
	 * 
	 * @return whether, number of input arguemnents is one or not
	 */
	private boolean validateInputArguements(String[] args) {
		if (args.length != 2) {
			System.err.println("Invalid number of Arguements. Hence Exiting.!!");
			System.exit(1);
		}
		return true;
	}

}