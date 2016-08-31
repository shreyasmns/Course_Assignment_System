
CS542 Design Patterns
Fall 2016
Assignment 1

README FILE

Due Date: Tuesday, August 30, 2016.
Submission Date: Tuesday, August 30, 2016.
Author: Shreyas Mahanthappa Nagaraj
e-mail: smn1@binghamton.edu

PURPOSE: To implement course assignment system for students. 
PERCENT COMPLETE: I believe I have completed 100% of this project.

FILES:

1) Driver.java, The class which contains main, takes input arguments and does validations
2) AssignCourses.java, The class which does course assignment, calculating total preference scores and printing results
3) README.txt, REDAME file


SAMPLE OUTPUT:
=================================================
Student_1 C B A 6
Student_2 C B D 6
Student_3 B D A 6
Student_4 D C B 6
Student_5 A B D 6
Student_6 B C A 6
Student_7 A C D 6
Student_8 A D B 6
Student_9 D A B 6
Student_10 B A D 6
Student_11 C B D 6
Student_12 A C D 8

Average preference_score is: 6.17
==================================================


TO COMPILE: javac Driver.java

TO RUN: java Driver input_file_name output_file_name

Data Structure Used:

1) ArrayList : I have used ArrayList to store the students id and respective preferences as get, set, size,
			   iterator operations run in constant time i.e, O(1) time complexity making, storing and retrieving 
			   preferences, iterating over preferences can be done in O(1) Time Complexity.
			   
			   Here, internally arrays wont get resized, as size can be maximum 5 including student id
			   and 4 preferences. So Array resiszing overhead will not be there in this case.
			   
2) HashMap :  Have used HashMap to store the student id and the sum of preferences for the subjects they have been assigned.
			 
			  Also in times of reshuffling of subjects in some cases, preferences have to be updated accordingly and 
			  HashMap does good job in performing put, get operations as our hashmap size atmost, can be 12.
			  
			  So Time Compelxity will be ~O(1).
			
			