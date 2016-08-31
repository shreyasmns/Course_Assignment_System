import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * @author ${Shreyas Mahanthappa Nagaraj}
 * 
 */
public class AssignCourses {

	private static final Integer NO_OF_STUDENTS = 12;
	private int COURSE_A = 10, COURSE_B = 10, COURSE_C = 10, COURSE_D = 10;
	private FileReader filereader = null;
	private FileWriter filewriter = null;
	private BufferedReader br = null;
	private Double totalPreference = 0.0;
	private int other_students = 5;

	private List<ArrayList<Integer>> list = null; // To store each students
													// preferences
	private ArrayList<Integer> preferences = null; // Individual students
													// preferences
	private List<ArrayList<String>> studentsCourses = new ArrayList<ArrayList<String>>(12);
	private ArrayList<String> courses = null; // storing assigned courses of
												// individual student
	private Map<Integer, Integer> totalPref = new HashMap<Integer, Integer>(12);
	private Map<Integer, Integer> subjects_count = new HashMap<Integer, Integer>();
	private Map<Integer, String> subjects = new HashMap<Integer, String>() {
		private static final long serialVersionUID = 1L;
		{
			put(0, "A");
			put(1, "B");
			put(2, "C");
			put(3, "D");
		}
	};
	private Map<String, Integer> subjectsValues = new HashMap<String, Integer>() {
		private static final long serialVersionUID = 1L;
		{
			put("A", 0);
			put("B", 1);
			put("C", 2);
			put("D", 3);
		}
	};

	/**
	 * Takes input output file names and creates references for filereader and
	 * filewriter
	 * 
	 * @param inFile
	 * @param outFile
	 */
	public AssignCourses(String inFile, String outFile) {
	
			try {
				filereader = new FileReader(inFile);
				br = new BufferedReader(filereader);
			} catch (FileNotFoundException e) {
					e.printStackTrace();
					System.exit(1);
			}
			
		

			try {
				File file = new File(outFile);
				filewriter = new FileWriter(file);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}

	/**
	 * Reads the input file and validates the number of fields in each line
	 */
	public void readInput() {
		String inputLine = null;
		int studentsCount = 0;
		list = new ArrayList<ArrayList<Integer>>(12);

		while (((inputLine = readline()) != null) && (studentsCount < 12)) {
			inputLine = inputLine.trim();
			String[] tokens = inputLine.split(" +");

			if (tokens.length != 5) {
				System.err.println("Input File Format is Wrong. Please provide valid one.");
				System.exit(1);
			}
			preferences = new ArrayList<Integer>(4);

			for (int i = 0; i < 4; i++) {
				preferences.add(Integer.parseInt(tokens[i + 1]));
			}
			list.add(preferences);
		}
		studentsCount++;
	}

	/**
	 * calls 'assign' helper method to assign courses passing student id and
	 * their preferences
	 * 
	 */
	public void assignCourses() {
		TreeMap<Integer, String> choices = null;
		String subj = null;

		for (int i = 0; i < NO_OF_STUDENTS; i++) {
			choices = new TreeMap<Integer, String>();
			for (int j = 0; j < 4; j++) {
				subj = subjects.get(j);
				choices.put(list.get(i).get(j), subj);
			}
			assign(choices, i);
		}
		checkCourseAssignment();
	}

	/**
	 * This method checks, whether every student has been assigned exactly 3
	 * subjects
	 * 
	 */
	public void checkCourseAssignment() {
		Iterator<Entry<Integer, Integer>> it = subjects_count.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, Integer> pair = (Map.Entry<Integer, Integer>) it.next();
			int student = pair.getKey();
			int subjects = pair.getValue();

			if (subjects < 3) {
				reAssignCourses(student, subjects);
			}
		}
		printResults();
	}

	/**
	 * If any student has bene assigned less than 3 courses, Re-assignment will
	 * be done for such student
	 * 
	 * @param student
	 * @param subjects2
	 *            (The number of subjects student has been allotted currently)
	 */
	public void reAssignCourses(int i, int subjects2) { // i is student with
														// less than 3 courses
		int difference = 3 - subjects2;
		int replace_pref = 1;
		String subjReplace = null;
		String subjReassign = null;

		for (int j = 1; j <= difference; j++) {
			if (replace_pref > 2)
				replace_pref = 1;
			Integer fourth_pref = list.get(other_students).indexOf(4);
			Integer first_pref = list.get(other_students).indexOf(replace_pref);
			subjReplace = subjects.get(fourth_pref);
			subjReassign = subjects.get(first_pref);

			studentsCourses.get(other_students).remove(subjReassign);
			studentsCourses.get(other_students).add(subjReplace);

			// Updating preference scores after re-assigning subjects
			int prev_pref = totalPref.get(other_students);
			prev_pref += (4 - replace_pref);
			totalPref.put(other_students, prev_pref);

			// To assign student a course, who didn't get 3 courses initially
			studentsCourses.get(i).add(subjReassign);
			int index = subjectsValues.get(subjReassign);
			int actualPref = list.get(i).get(index);

			actualPref = actualPref + totalPref.get(i);
			totalPref.put(i, actualPref);
			updateCourseAvailability(subjReplace, subjReassign);

			other_students++;
			replace_pref++;
		}
	}

	/**
	 * Updates the each course available count
	 * 
	 * @param subjReplace
	 * @param subjReassign
	 */
	public void updateCourseAvailability(String subjReplace, String subjReassign) {

		if (subjReplace != null) {
			if (subjReplace.equals("A"))
				--COURSE_A;
			else if (subjReplace.equals("B"))
				--COURSE_B;
			else if (subjReplace.equals("C"))
				--COURSE_C;
			else
				--COURSE_D;
		}

		if (subjReassign != null) {
			if (subjReassign.equals("A"))
				++COURSE_A;
			else if (subjReassign.equals("B"))
				++COURSE_B;
			else if (subjReassign.equals("C"))
				++COURSE_C;
			else
				++COURSE_D;
		}
	}

	/**
	 * Method try assigning courses to students according to preferences
	 * 
	 * @param choices
	 * @param i(student
	 *            id)
	 */
	public void assign(TreeMap<Integer, String> map, int i) {
		courses = new ArrayList<String>(4);
		int totalpref = 0;
		int no_of_subjects = 0;

		for (Map.Entry<Integer, String> entry : map.entrySet()) {
			int pref = entry.getKey();
			String sub = entry.getValue();

			if (no_of_subjects < 3) {
				boolean available = isCourseSAvailable(sub);
				if (available) {
					courses.add(sub);
					totalpref = totalpref + pref;
					++no_of_subjects;
					subjects_count.put(i, no_of_subjects);
				}
			}
		}
		totalPref.put(i, totalpref);
		studentsCourses.add(courses);
	}

	/**
	 * Method checks if are slots available in a particluar course before course
	 * assignment
	 * 
	 * @param sub
	 * @return whether particular course is available or not
	 */
	public boolean isCourseSAvailable(String sub) {

		boolean answer = false;
		if (sub != null) {
			if (sub.equals("A")) {
				if (COURSE_A > 0) {
					--COURSE_A;
					answer = true;
				} else {
					answer = false;
				}
			}
			if (sub.equals("B")) {
				if (COURSE_B > 0) {
					--COURSE_B;
					answer = true;
				} else {
					answer = false;
				}
			}
			if (sub.equals("C")) {
				if (COURSE_C > 0) {
					--COURSE_C;
					answer = true;
				} else {
					answer = false;
				}
			}
			if (sub.equals("D")) {
				if (COURSE_D > 0) {
					--COURSE_D;
					answer = true;
				} else {
					answer = false;
				}
			}
		}
		return answer;
	}

	/**
	 * Reads a Single line from the Input File
	 * 
	 * @return read line
	 */
	public String readline() {
		try {
			return br.readLine();
		} catch (IOException e) {
			System.err.println("Error while Reading Input file");
			System.exit(1);
		}
		return null;
	}

	/**
	 * Writes the Results to the Output File
	 */
	public void printResults() {

		ArrayList<String> subjects = null;
		for (int i = 0; i < NO_OF_STUDENTS; i++) {
			totalPreference += totalPref.get(i);
			subjects = studentsCourses.get(i);

			writeToOutFile("Student_" + (i + 1) + " ");
			for (int j = 0; j < subjects.size(); j++) {
				writeToOutFile(subjects.get(j) + " ");
			}
			writeToOutFile(totalPref.get(i).toString());
			writeToOutFile("\n");
		}
		writeToOutFile("\nAverage preference_score is: " + String.format("%.2f", totalPreference / NO_OF_STUDENTS));
	}

	/**
	 * Helper method to write to the output file
	 * 
	 * @param string
	 */
	public void writeToOutFile(String string) {
		try {
			filewriter.write(string);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Releases the Resources to the System
	 */
	public void closeFile() {
		try {
			filereader.close();
			br.close();
			filewriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/*
	 * @return TotalPreference
	 */
	public Double getTotalPreference() {
		return totalPreference;
	}

	/*
	 * @return StudentsCourses
	 */
	public List<ArrayList<String>> getStudentsCourses() {
		return studentsCourses;
	}

	/*
	 * @return TotalPref
	 */
	public Map<Integer, Integer> getTotalPref() {
		return totalPref;
	}

	/*
	 * @return String
	 */
	@Override
	public String toString() {
		return "studentsCourses " + studentsCourses + ", TotalPreference " + totalPreference + ", totalPref "
				+ totalPref;
	}
}
