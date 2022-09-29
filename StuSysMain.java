import java.util.Scanner;

public class StuSysMain {
      private static StuSys stuSys;

      public static void main(String[] args) {
            stuSys = new StuSys();
            stuSys.ImportAcct("AcctInfo.txt");

            Scanner input = new Scanner(System.in);
            int option = -1;

            do {
                  // * MAIN SCREEN
                  // TODO: Print and prompt (either new, returning or exit)
                  System.out.println("==================================================");
                  System.out.println("|        WELCOME TO CIMP STUDENT SYSTEM          |");
                  System.out.println("==================================================");
                  System.out.println("1. New Student");
                  System.out.println("2. Returning Student");
                  System.out.println("3. Exit System");
                  System.out.print("Pick an option: ");
                  option = input.nextInt();

                  if (option > 3 || option < 1) {
                        System.out.println("Invalid option. Please enter a valid option.\n");
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
            do {
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

            int status;
            do {
                  System.out.print("Password       : ");
                  pw1 = input.nextLine();

                  System.out.print("Retype Password: ");
                  pw2 = input.nextLine();

                  status = stuSys.CreateNewAcct(name, pw1, pw2);

                  if (status == 1) { // if account is created successfully
                        System.out.println("Succesfully created account '" + id + "'");
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

            System.out.println("---------------------");
            System.out.println("|   LOGIN SCREEN    |");
            System.out.println("---------------------");

            int status = 0;
            while (status != 1) {
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

            int option;
            System.out.println("====================================================================");
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

            if (option == 1) {
                  // stuSys.DisplayDatabase();
                  StudentDetail();
            } else if (option == 2) {

            } else if (option == 3) {

            } else if (option == 4) {

            } else if (option == 5) {

            } else if (option == 6) {

            } else {

            }

            input.close();
      }

      private static void StudentDetail() {
            String id = stuSys.loginUserId;
            String student_name = stuSys.GetStudentName(id);
            Double gpa = stuSys.GetGPA(id);


            System.out.println(String.valueOf(gpa));

            System.out.println("|==============================================================|");
            System.out.println("|           COURSES            |           Grades              |");
            System.out.println("|--------------------------------------------------------------|");

            int num_of_course = stuSys.NumCourse(id);
            int current_index = 1;
            for (int i = 0; i < num_of_course; i++) {
                  String course_name = stuSys.GetCourseNameAt(id, i);
                  int course_grade = stuSys.GetCourseGradeAt(id, i);

                  if (course_grade >= 0) {
                        System.out.println("| " + current_index + " " + course_name  + "   |    "+ String.valueOf(course_grade)  +" |");
                        current_index++;
                  }
                
            }

      }
}