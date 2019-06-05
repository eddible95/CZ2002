import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * Represents the Main function for running the MoblimaApp. 
 * @author: CZ2002 Team 3
 * @version: Final version
 * @since 2017-11-16
 */
public class Moblima {
	/**
	 * Main Function to run the MoblimaApp
	 * @param args A String object to run the main method.
	 *
	 */
	public static void main (String [] args)
	{
		UI ui = UI.getInstance();
		/*-------------------------------------------------------------*
		 *|                      Application to Start                 |*
	     *-------------------------------------------------------------*/
		/**
		 * A method to Start the Application
		 */
		boolean end = false;
		int option;
		Scanner sc = new Scanner(System.in);
		do{
			System.out.println("------------------------------------------------");
			System.out.println("|            Welcome to MoblimaApp!            |");
			System.out.println("------------------------------------------------");
			System.out.println("(1) Admin Page");
			System.out.println("(2) Movie-Goer Page");
			System.out.println("(3) Quit");
			System.out.println("Enter your choice:");
			/** 
			 * Handles Exception Cases when the user enters String instead of Number
			 */
			try
			{
				option = sc.nextInt();
				sc.nextLine();
				switch(option){
					case 1:{
						/**
						 *  Authenticate the rights of the Admin User
						 */
						if (ui.adminLogin()== true){
							ui.adminModule();
							break;
						}
						else
							break;
					}
					case 2:{
							ui.customerModule();
							break;
					}
					case 3:{
						end = true;
						/**
						 *  Before the Application ends, writes back all the updated Data to the Data base
						 *  so that the information can be retrieved when the Application is restarted. 
						 */
						if ( ui.userManager.save()&& ui.movieManager.save() && ui.hallManager.save() && ui.bookingManager.save() && ui.showtimeManager.save()){
							System.out.println("Saving existing data to .dat files...");
							break;
						}
					}
				}
			}catch(InputMismatchException e){
				System.out.println("Invalid input type");
				System.out.println("Enter only the number stated");
				sc.nextLine();
			}
		}while(end!=true);
	}
}