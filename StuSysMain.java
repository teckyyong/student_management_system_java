import java.util.Scanner;

public class StuSysMain {
   public static void main(String[] args) {
      // StuSys stuSys = new StuSys();
      // stuSys.ImportAcct( "AcctInfo.txt" );

      boolean exit = false; // Sentinel value to control loop
      Scanner input = new Scanner(System.in);
      int option = -1;
      int id;
      String name;
      int pw1;
      int pw2;


      // * MAIN SCREEN
      // TODO: Print and prompt (either new, returning or exit)
      // HINT: if .. else
      System.out.println("==================================================");
      System.out.println("|        WELCOME TO CIMP STUDENT SYSTEM          |");
      System.out.println("==================================================");
      System.out.println("1. New Student");
      System.out.println("2. Returning Student");
      System.out.println("3. Exit System");
      System.out.println("Pick an option");

    
      if (option == 1){
         // * Option1: New Student Screen
         // TODO:  Print create account form and prompt user line by line
        
        
         System.out.println("-------------------");


         
         System.out.println("| CREATE ACCOUNT  |");
         System.out.println("-------------------");
         
         System.out.print("Student ID     : " + id);
         id = input.nextInt();
        
         System.out.print("Full Name      : " + name);
         name = input.nextLine();
         
         while( pw1 != pw2) {
         System.out.print("Password       : " + pw1);
         pw1 = input.nextInt();
        
         System.out.print("Retype Password: " + pw2);
         pw2 = input.nextInt();
         
         if ( pw1 == pw2 ) {
            System.out.printf("\nSuccesfully created account '%5d' ", id);
         }
         else {   // ! Users may type the wrong password more than once. Add a while loop. Alternatively, allow users to retype both password (pw1 and pw2)
            System.out.println("Retype password: "+ pw2);
            pw2 = input.nextInt();  
         }
      }

      } else if (option == 2) {
         // * Option 2: Returning Screen
         // TODO: Print Login Option and prompt user line by line. 
         int id;
         int pw;


         System.out.println("---------------------");
         System.out.println("|   LOGIN SCREEN    |");
         System.out.println("---------------------");
        
         
         System.out.print("Student ID   : "+ id);
         id= input.nextInt();
        
         System.out.print("Passowrd     : "+ pw);  // ! Variable not initialised. 
         pw= input.nextInt();
         
         System.out.println("Login Success");

         System.out.println("====================================================================");
         System.out.println("|                   CIMP STUDENT SYSTEM                            |");
         System.out.println("|                      OPTION SCREEN                               |");
         System.out.println("|==================================================================|");
         System.out.println("|   1. STUDENT DETAIL            |   4. EDIT COURSE GRADE          |");
         System.out.println("|------------------------------------------------------------------|");
         System.out.println("|   2. ADD COURSES               |   5. CHANGE PASSWORD            |");
         System.out.println("|------------------------------------------------------------------|");
         System.out.println("|   3. DROP COURSES              |   6. LOGOUT                     |");
         System.out.println("|===================================================================");
         System.out.println("Pick an option: ");


      } else if (option == 3) { 
         // * Option 3: Exit
         // TODO: Change exit to true
         exit = true ;

         System.out.println("System is shutting down.") ; // ! WHERES YOUR SEMICOLON!!!!!!!

      } else{  // if enter wrong number
         // * ERROR HANDLING FOR INVALID INPUT
         // TODO: Print error message (eg. Invalid input. Pls enter a valid option) 
         System.out.println("Invalid input, please enter a valid option");


      // }
      

    


   }
      while (exit == false); {



      }







      

   
   }
}