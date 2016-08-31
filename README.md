# Course_Assign_System
Java Application which automatically assigns course for students based on their preferences

There are 4 courses (A, B, C, and D) being offered in the summer session. The capacity for each course is 10. The total number of students is 12. Each student is required to register for 3 courses. The student is asked to provide a preference for each of the courses. Top preference is specified as "1", while the lowest preference is specified as "4".
In the file input.txt, students give thier preferences as shown below
 
Student_1 preference_for_A prefrence_for_B preference_for_C preference_for_D
Student_2 preference_for_A prefrence_for_B preference_for_C preference_for_D
...
Student_12 preference_for_A prefrence_for_B preference_for_C preference_for_D

so that the output.txt file looks like the following:
 
Student_1 assigned_course_name assigned_course_name assigned_course_name total_preference_score
Student_2 assigned_course_name assigned_course_name assigned_course_name total_preference_score
...
Student_3 assigned_course_name assigned_course_name assigned_course_name total_preference_score

Average preference_score is: X.Y
