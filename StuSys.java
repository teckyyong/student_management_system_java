public class StuSys {
   // Instance Variables (DO NOT MODIFY)
   private Database db;
   String loginUserId; /*
                        * Keeps track of the currently logged User ID. It will be null whe
                        * no one is logged in.
                        */
   int currNewId; // The next new user's ID.

   // ========== CONSTRUCTOR ==========//
   public StuSys() {
      db = new Database();
      currNewId = 10000;
   }

   // ========= PRIVATE METHOD =========//
   // (Write your private methods here)

   // ========= PUBLIC METHOD =========//
   /*
    * PROVIDED METHOD
    * Used to import dummy data into the database, so there will
    * be pre-existing accounts in the database to be used for testing.
    * 
    * @param filename - The filename of the textfile that contains all
    * dummy data.
    */
   public void ImportAcct(String filename) {
      currNewId += db.ImportAcct(filename, currNewId);
      System.out.println(currNewId);
   }

   /*
    * Create a new account with given info. It will also check if
    * password and retype password matches
    * 
    * @param id - Student ID
    *
    * @param name - The name for the account
    * 
    * @param pass - The password for the account
    * 
    * @param retypePass - The retype password to be used to check with the password
    * 
    * @return - The status of the account creation process
    * Returns 1, if account is created successfully
    * Returns -3, if password and retype password don't match
    * Returns -4, if number of accounts have reached its limit
    * Returns -7, if password is not 5 characters.
    */
   public int CreateNewAcct(String name, String pass, String retypePass) {
      if (pass.equals(retypePass) == false) { // if password and retype password don't match
         return -3;
      } else if (pass.length() != 5) { // if password is not 5 characters.
         return -7;
      }

      boolean is_successful = db.AddAcct(Integer.toString(currNewId), name, pass); // true or false
      if (is_successful == true) { // if the account is created successful
         currNewId++;
         return 1;
      } else if (is_successful == false) { // the database has reached max account
         return -4;
      }

      return 0;
   }

   /*
    * Login a user with the given student ID and password
    * 
    * @param id - The account ID
    * 
    * @param pass - The password for the account
    * 
    * @return - Returns 1, if the ID and password both matches the info in the
    * database.
    * Returns -1, if account ID not found in the database.
    * Returns -2, if password associated with the ID is not correct.
    */
   public int Login(String id, String pass) {
      if (db.IsAcctExist(id) == false) {
         return -1;
      } else if (db.GetAcctPass(id) == null || db.GetAcctPass(id).equals(pass) == false) {
         return -2;
      } else { // succesfully login
         loginUserId = id;
         return 1;
      }
   }

   /* Logout the currently logged in student from the system. */
   public void Logout() {
      loginUserId = null;
   }

   /*
    * Get the student's name with the given user id from
    * the database.
    * 
    * @id - The user id of the account
    * 
    * @return - Returns The student name of the account if the ID is found.
    * Returns null if the ID is not found.
    */
   public String GetStudentName(String id) {
      String name = db.GetAcctName(id);
      if (name == null) { // account name / id not found
         return null;
      } else { // Returns the account name, if it is found.
         return name;
      }
   }

   /*
    * Get the number of courses the specified account has
    * 
    * @param id - The account ID
    * 
    * @return - Returns the number of courses the account has.
    * Returns -1, if account ID not found in the database.
    */
   public int NumCourse(String id) {
      if (db.IsAcctExist(id) == false) { // if account ID not found in the database.
         return -1;
      }
      String courses = db.GetAllCourseInfo(id); // ICS4U_-1:IT4F_80
      String[] courses_array = courses.split(":"); // ['ICS4U_-1', 'IT4F_80']

      return courses_array.length;
   }

   /*
    * Get the course's name stored at the specified position in the database
    * 
    * @param id - The account ID
    * 
    * @param pos - The pos where the course is stored in.
    * First course is at pos 0, second is at pos 1, and etc.
    * 
    * @return - Returns the name of the course at pos.
    * Returns null if account ID not found or course pos is out of range.
    */
   public String GetCourseNameAt(String id, int pos) {
      if (db.IsAcctExist(id) == false) {
         return null;
      }
      String courses = db.GetAllCourseInfo(id); // ICS4U_-1:IT4F_80
      String[] courses_array = courses.split(":"); // ['ICS4U_-1', 'IT4F_80']
      if (pos >= courses_array.length) { // if pos specified is beyond the range of number of courses
         return null;
      }

      String[] course = courses_array[pos].split("_"); // ['ICS4u', -1]
      String course_name = course[Database.ARRAY_COURSE_NAME_POS];

      return course_name;

   }

   /*
    * Get the course's grade stored at the specified position in the database
    * 
    * @param id - The account ID
    * 
    * @param pos - The pos where the course is stored in.
    * First course is at pos 0, second is at pos 1, etc.
    * 
    * @return - Returns the grade of the course at pos.
    * Returns -1, if account ID not found in the database.
    * Returns -5, if pos specified is beyond the range of number of courses
    */
   public int GetCourseGradeAt(String id, int pos) {
      if (db.IsAcctExist(id) == false) {
         return -1;
      }
      String courses = db.GetAllCourseInfo(id); // ICS4U_-1:IT4F_80
      String[] courses_array = courses.split(":"); // ['ICS4U_-1', 'IT4F_80']
      if (pos >= courses_array.length) { // if pos specified is beyond the range of number of courses
         return -5;
      }

      String[] course = courses_array[pos].split("_"); // ['ICS4u', -1]
    
      int grade = Integer.parseInt(course[Database.ARRAY_COURSE_GRADE_POS]);
  
      return grade;
   }

   /*
    * Get the student's GPA (overall average)
    * 
    * @param id - The account ID
    * 
    * @return - Returns the GPA of the student.
    * Returns -1, if account ID not found in the database.
    */
   public double GetGPA(String id) {
      String courses = db.GetAllCourseInfo(id); // ICS4U_-1:IT4F_80
      String[] courses_array = courses.split(":"); // ['ICS4U_-1', 'IT4F_80']

      int[] grade_array = new int[courses_array.length];
      int total_mark = 0;
      int num_of_subject = 0;
      for (int i = 0; i < courses_array.length; i++) {
         grade_array[i] = this.GetCourseGradeAt(id, i);

         if (grade_array[i] >= 0) {

            total_mark += grade_array[i];
            num_of_subject++;
         }

      }

      double gpa = (total_mark / num_of_subject);

      return gpa;
   }

   /*
    * Add a course to the account
    * 
    * @param id - The account that the course will be added to
    * 
    * @param courseName - The String that contains the cours name.
    * 
    * @return - Returns TRUE if successful added the course
    * Returns FALSE if ID is not found.
    */
   public boolean AddCourse(String id, String courseName) {
      return true; // Dummy return value. Needs to be changed.
   }

   /*
    * Delete a course in an account
    * 
    * @param id - The account ID
    * 
    * @param pos - The pos where the course is stored in.
    * First course is at pos 0, second is at pos 1, etc.
    * 
    * @return - Returns 1, if successfully deleted the course.
    * Returns -1, if account ID not found in the database
    * Returns -5, if pos specified is beyond the range of number of courses.
    */
   public int DropCourse(String id, int pos) {
      return 0; // Dummy return value. Needs to be changed.
   }

   /*
    * Edit a course's grade in an account
    * 
    * @param id - The account ID
    * 
    * @param pos - The position of the course that is in the database.
    * First course is at pos 0, second is at pos 1, etc.
    * 
    * @param grade - The new grade for the course
    * 
    * @return - Returns 1, if successfully edited the course
    * Returns -1, if account ID not found in the database
    * Returns -5, if pos specified is beyond the range of number of courses
    * Returns -6, if grade is not valid (not between 1-100)
    */
   public int EditCourse(String id, int pos, int grade) {
      return 0; // Dummy return value. Needs to be changed.
   }

   /*
    * Change the account's password
    * 
    * @param id - The account ID
    * 
    * @param pass - The new password for the account
    * 
    * @return - Returns 1, if successfully changes the password.
    * Returns -1, if account ID not found in the database.
    * Returns -2, if password is incorrect.
    * Returns -3, if password and retype password don't match
    * Returns -7, if new password is not 5 characters.
    */
   public int ChangePassword(String id, String oldPass, String newPass, String retypePass) {
      return 0; // Dummy return value. Needs to be changed.
   }

   /*
    * For testing purposes. You may use this method to
    * see what is inside the database.
    */
   public void DisplayDatabase() {
      db.DisplayDatabase();
   }
}