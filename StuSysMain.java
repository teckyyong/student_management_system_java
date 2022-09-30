import java.util.Scanner;

public class StuSysMain {
      private static StuSys stuSys;

      public static void main(String[] args) {
            stuSys = new StuSys();
            // stuSys.ImportAcct("AcctInfo.txt");
            MainScreen();
      }

      private static void MainScreen() {
            Scanner input = new Scanner(System.in);
            int option;

            do {  // Keep prompting users until a valid input is given
                  // * MAIN SCREEN
                  System.out.println("\n==================================================");
                  System.out.println("|        WELCOME TO CIMP STUDENT SYSTEM          |");
                  System.out.println("==================================================");
                  System.out.println("1. New Student");
                  System.out.println("2. Returning Student");
                  System.out.println("3. Exit System");
                  System.out.print("Pick an option: ");
                  option = input.nextInt();

                  if (option > 3 || option < 1) {
                        System.out.println("Invalid option. Please enter a valid option.\n");
                  } else if (stuSys.currNewId == 10000 && option == 2) {      // No students in database. Cant login
                        option = -1;
                        System.out.println("There are no students in the database yet. Please create account.\n");
                  }
            } while (option > 3 || option < 1);

            if (option == 1) {
                  CreateAccount();
            } else if (option == 2) {
                  Login();
            } else if (option == 3) {
                  // * Option 3: Exit
                  System.out.println("System is shutting down.");
            }
            input.close();
      }

      private static void CreateAccount() {
            // * Option1: New Student Screen
            // Print create account form and prompt user line by line
            Scanner input = new Scanner(System.in);
            String id;
            String name;
            String pw1;
            String pw2;

            System.out.println("\n-------------------");
            System.out.println("| CREATE ACCOUNT  |");
            System.out.println("-------------------");

            boolean valid_id;
            do {  // Error Handling: Ensure valid student ID
                  System.out.print("Student ID     : ");
                  id = input.nextLine();
                  if (id.length() != 5) {
                        System.out.println("Invalid Student ID. Please enter a 5 digit student ID.\n");
                        valid_id = false;
                  } else { // ID is valid (has 5 digits)
                        valid_id = true;
                  }
            } while (valid_id == false);

            System.out.print("Full Name      : ");
            name = input.nextLine();

            int status; // keep track of the status 
            do {  // Keep prompting users until valid inputs are given
                  System.out.print("Password       : ");
                  pw1 = input.nextLine();

                  System.out.print("Retype Password: ");
                  pw2 = input.nextLine();

                  status = stuSys.CreateNewAcct(name, pw1, pw2);

                  if (status == 1) { // if account is created successfully
                        System.out.println("Succesfully created account '" + id + "'");
                        MainScreen(); // Redirect student back to main screen
                  } else if (status == -3) { // if password and retype password don't match
                        System.out.println("\nPasswords don't match. Please retype. ");
                  } else if (status == -4) { // if number of accounts have reached its limit
                        System.out.println("\nFailed to create account. Number of accounts have reached its limit.");
                  } else if (status == -7) { // if password is not 5 characters.
                        System.out.println(
                                    "\nInvalid Password. Please make sure your password contains exactly 5 characters.");
                  }

            } while (status == -3 || status == -7);

            input.close();
      }

      private static void Login() {
            // * Option 2: Returning Screen
            Scanner input = new Scanner(System.in);
            String id;
            String pw;

            System.out.println("\n---------------------");
            System.out.println("|   LOGIN SCREEN    |");
            System.out.println("---------------------");

            int status = 0;   
            while (status != 1) {   // Error Handdling: keep prompting users until valid inputs are given
                  System.out.print("Student ID : ");
                  id = input.nextLine();

                  System.out.print("Password : ");
                  pw = input.nextLine();

                  status = stuSys.Login(id, pw);

                  if (status == -1) { // if account ID not found in the database.
                        System.out.println("Account ID not found in the database.");
                  } else if (status == -2) { // if password associated with the ID is not correct or null
                        System.out.println("Password associated with the ID is not correct.");
                  } else if (status == 1) { // login sucessfulyy
                        System.out.println("Logged in successfully.");
                  }
            }

            System.out.println("Login Success");
            int option = -1;
            while (option != 6) {   // Display screen unless logged out
                  do {

                        System.out.println("\n====================================================================");
                        System.out.println("|                     CIMP STUDENT SYSTEM                          |");
                        System.out.println("|                         OPTION SCREEN                            |");
                        System.out.println("|==================================================================|");
                        System.out.println("|         1. STUDENT DETAIL     |      4. EDIT COURSE GRADE        |");
                        System.out.println("|------------------------------------------------------------------|");
                        System.out.println("|         2. ADD COURSES        |      5. CHANGE PASSWORD          |");
                        System.out.println("|------------------------------------------------------------------|");
                        System.out.println("|         3. DROP COURSES       |      6. LOGOUT                   |");
                        System.out.println("|===================================================================");
                        System.out.println("Pick an option: ");
                        option = input.nextInt();

                        if (option > 6 || option < 1) {
                              System.out.println("Invalid option. Please enter a valid option.");
                        }
                  } while (option > 6 || option < 1);

                  id = stuSys.loginUserId;

                  if (option == 1) {
                        StudentDetail(id);
                  } else if (option == 2) {
                        AddCourses(input, id);
                        stuSys.DisplayDatabase();
                  } else if (option == 3) {
                        DropCourses(input, id);
                  } else if (option == 4) {
                        EditCourses(input, id);
                  } else if (option == 5) {
                        ChangePassword(input, id);
                  } else if (option == 6) {
                        option = 6;
                        Logout();
                        MainScreen();
                  }

            }
            input.close();

      }

      private static void StudentDetail(String id) {
            String student_name = stuSys.GetStudentName(id);

            int num_of_course = stuSys.NumCourse(id);
            Double gpa = stuSys.GetGPA(id);

            System.out.println("\n----------------------------------------------------------------");
            System.out.println("|                       STUDENT DETAIL                         |");
            System.out.println("----------------------------------------------------------------");

            System.out.printf("|" + "Student ID       :%45s", id + "   |");
            System.out.printf("\n|" + "Student name     :%45s", student_name + "   |");
            if (num_of_course == -1 || gpa == -1) {
                  System.out.printf("\n|" + "GPA              :%45s", "N/A" + "   |");
            } else {
                  System.out.printf("\n|" + "GPA              :%45s", gpa + " % |");
            }

            System.out.println("\n----------------------------------------------------------------");
            System.out.println("\n\n|--------------------------------------------------------------|");
            System.out.println("|           COURSES            |           Grades              |");
            System.out.println("|--------------------------------------------------------------|");

            DisplayCourseGrades(id, num_of_course);

      }

      /**
       * A reusable method to display student's courses and their grades
       * 
       * @param id: student's ID
       * @param num_of_course: the number of courses taken by students
       */
      private static void DisplayCourseGrades(String id, int num_of_course) {
            if (num_of_course == -1) {
                  System.out.println("No course at the moment!");
            }

            int current_index = 1;
            for (int i = 0; i < num_of_course; i++) {
                  String course_name = stuSys.GetCourseNameAt(id, i);
                  int course_grade = stuSys.GetCourseGradeAt(id, i);
                  // int grade = course_grade;

                  if (course_grade >= 0) {
                        System.out.printf("| " + current_index + " " + course_name + "%21s" +
                                    "|  %30s ", " ", String.valueOf(course_grade) + " |");
                        current_index++;
                        System.out.println("\n----------------------------------------------------------------");
                  } else if (course_grade == -1) {
                        System.out.printf("| " + current_index + " " + course_name + "%21s" +
                                    " | %31s ", " ", "TBD |");
                        current_index++;
                        System.out.println("\n----------------------------------------------------------------");
                  } else if (course_grade == -2) {
                        System.out.printf("| " + current_index + " " + course_name + "%21s" +
                                    " | %31s ", " ", "DROPPED |");
                        current_index++;
                        System.out.println("\n----------------------------------------------------------------");
                  }

            }

      }

      private static void AddCourses(Scanner input, String id) {
            String newCourse;

            System.out.println("\n-----------------------------------");
            System.out.println("|            ADD COURSES          |");
            System.out.println("-----------------------------------");
            System.out.print("Course to add: ");
            newCourse = input.next();

            boolean status = stuSys.AddCourse(id, newCourse); // for adding a new course

            if (status)
                  System.out.println("Successfully added " + "'" + newCourse + "'");
            else
                  System.out.println("ID is not found");
      }

      private static void DropCourses(Scanner input, String id) {
            int num_of_course = stuSys.NumCourse(id);

            System.out.println("\n----------------------------------------------------------------");
            System.out.println("                           DROP COURSES                         ");
            System.out.println("----------------------------------------------------------------");

            DisplayCourseGrades(id, num_of_course);

            int status = 0;
            int course_num = -1;
            while (status != 1) {   // Keep prompting users unitl valid input is given
                  System.out.print("Select course number to drop: ");
                  course_num = input.nextInt();

                  if (course_num < 1 || course_num > num_of_course) {
                        System.out.println("Invalid Course Number.");
                  } else {
                        status = stuSys.DropCourse(id, course_num - 1);
                        if (status == 1) {
                              System.out.println("Successfully dropped the course.");
                        } else if (status == -5 || status == -1) {
                              System.out.println("Unable to drop course.");
                              System.out.print("Select course number to drop: ");
                        }
                  }
            }

      }

      private static void EditCourses(Scanner input, String id) {

            System.out.println("\n----------------------------------------------------------------");
            System.out.println("|                        EDIT COURSE GRADE                       |");
            System.out.println("----------------------------------------------------------------");
            System.out.printf("| YOUR CURRENT COURSES: %38s |\n", " ");
            int num_of_course = stuSys.NumCourse(id);
            DisplayCourseGrades(id, num_of_course);
            // System.out.printf(course_name + " | %20s" + course_grade);

            int status = 0;
            int course_num = -1;
            while (status != 1) { // Keep prompting users unitl valid inputs are given
                  System.out.print("Select a course number to edit: ");
                  course_num = input.nextInt();

                  if (course_num < 1 || course_num > num_of_course) {
                        System.out.println("Invalid Course Number.");
                  } else {
                        int grade;
                        System.out.println("Enter the new grade: ");
                        grade = input.nextInt();
                        status = stuSys.EditCourse(id, course_num - 1, grade);
                        if (status == 1) {
                              System.out.println("Successfully edited the course.\n");
                        } else if (status == -1) {
                              System.out.println("Account ID not found in the database.\n");
                        } else if (status == -5) {
                              System.out.println("Invalid course number.\n");
                        } else if (status == -6) { // if grade is not valid (not between 1-100)
                              System.out.println("Grade is not valid (not between 1-100)\n");
                        }
                  }
            }

      }

      private static void ChangePassword(Scanner input, String id) {
            String old_pass;
            String new_pass;
            String retyped_pass;

            int status = 0;
            while (status != 1) {   // Keep prompting users unitl valid inputs are given
                  System.out.println("\n--------------------------");
                  System.out.println("|     CHANGE PASSWORD    |");
                  System.out.println("--------------------------");
                  System.out.print("Enter old password: ");
                  old_pass = input.next();

                  System.out.print("Enter new password: ");
                  new_pass = input.next();

                  System.out.print("Retype password: ");
                  retyped_pass = input.next();

                  status = stuSys.ChangePassword(id, old_pass, new_pass, retyped_pass);
                  if (status == 1) { // if successfully changes the password.
                        System.out.println("Successfully changed the account password.");
                  } else if (status == -2) { // if password is incorrect.
                        System.out.println("Incorrect password.");
                  } else if (status == -3) { // if password and retype password don't match
                        System.out.println("New password and retype password don't match.");
                  } else if (status == -7) { // if new password is not 5 characters.
                        System.out.println("New password is not 5 characters.");
                  }
            }

      }

      private static void Logout() {
            stuSys.Logout();
      }
}