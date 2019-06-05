import java.util.*;
import Manager.BookingManager;
import Manager.HallManager;
import Manager.MovieManager;
import Manager.ShowtimeManager;
import Manager.UserManager;
/**
 * Represents the User Interface of MoblimaApp that receives inputs and return outputs to Users.
 * @author: CZ2002 Team 3
 * @version: Final version
 * @since 2017-11-16
 */
public class UI 
{	
	/**
	* The instance of movieManager to allow UI to have access to Movie entity through the movieManager.
	* {@link MovieManager}
	*/
	MovieManager movieManager;
	/**
	* The instance of userManager to allow UI to have access to User entity through the userManager.
	*  {@link UserManager}
	*/
	UserManager userManager;
	/**
	* The instance of hallManager to allow UI to have access to Hall entity through the hallManager.
	* {@link HallManager}
	*/
	HallManager hallManager;
	/**
	* The instance of showtimeManager to allow UI to have access to Showtime entity through the showtimeManager.
	* {@link ShowtimeManager}
	*/
	ShowtimeManager showtimeManager;
	/**
	* The instance of bookingManager to allow UI to pass inputs and access outputs for
	* booking of the tickets through MoblimaApp.
	* {@link BookingManager}
	*/
	BookingManager bookingManager;
	/**
	 * Ensure that throughout the program, only one instance of UI will be created.
	 */
	private static UI instance = null;
	/**
	 * To create one instance of the UI if it does not exists else it will return
	 * the instance of UI that already exists.
	 * @return instance The instance of UI that is already created.
	 */
	public static UI getInstance() 
	{	
		if(instance == null){
			instance = new UI();
		}
		return instance;
	}
	/**
	* Create a new instance of UI.
	* It will instantiate and get the instance of each of the various Managers 
	*/
	public UI()
	{
		this.movieManager = MovieManager.getInstance();
		this.userManager = UserManager.getInstance();
		this.hallManager = HallManager.getInstance();
		this.showtimeManager = ShowtimeManager.getInstance();
		this.bookingManager = BookingManager.getInstance();
	}
	
	/*---------------------------------------------------------------------------------              
	 *| 						    Movie-goer Module Methods                          |
	 *---------------------------------------------------------------------------------*/
	
	/**
	 *  Method to validate the authenticity of existing Customer User using their email and password.
	 *  If it is new Customer User, it will allow the creation of a new User object. 
	 *  @return false If the customer login fails or when creation of new user fails.
	 *          <p>
	 *          true If the customer login is successful or when creation of new user is successful.
	 */
	public boolean customerLogin()
	{
		int option;
		String name, password, mobileNumber, email, ageGroup = null;
		Scanner sc = new Scanner(System.in);
		System.out.println("------------------------------------------------");
		System.out.println("|            Validation of Information         |");
		System.out.println("------------------------------------------------");
		System.out.println("(1) Existing User");
		System.out.println("(2) New User");
		System.out.println("(3) Back");
		System.out.println("Enter your choice: ");
		/**
		 *  Catch for Exception when user enters a string instead of integer.
		 */
		try {
			option = sc.nextInt();
			sc.nextLine();
			switch(option){
				/**
				 *  For Existing Customer User to verify their email and password.
				 */
				case 1:
				{
					System.out.println("<Verification of Customer>");
					System.out.println("Enter your email:");
					email = sc.nextLine();
					
					System.out.println("Enter your password:");
					password = sc.nextLine();
			
					if(userManager.validateCustomer(email, password) == true){
						System.out.println("Verfication Success!");
						return true;
					}
					else{ 
						System.out.println("Verfication Failed!");
						return false;
					}
				}
				/**
				 *  For new Customer User to create new User
				 */
				case 2:
				{
					System.out.println("<Creation of new Customer User>");
					System.out.println("Key in your name: ");
					name = sc.nextLine();
					
					System.out.println("Key in your password: ");
					password = sc.nextLine();
					
					System.out.println("Key in your mobileNumber: ");
					mobileNumber = sc.nextLine();
					
					System.out.println("Key in your email: ");
					email = sc.nextLine();
					
					System.out.println("Select Age Group: (1) Child (2) Adult (3) Senior Citizen");
					do {
						option = sc.nextInt();
						sc.nextLine();
						switch(option){
							case 1:{
								ageGroup = "Child";
								break;
							}
							case 2:{
								ageGroup = "Adult";
								break;
							}
							case 3:{
								ageGroup = "Senior Citizen";
								break;
							}
							default:
								break;
						}
					}while(option <1 | option >4);
					/**
					 *  Creates the new User object
					 */
					userManager.createCustomer(name, password, mobileNumber, email, ageGroup);
					/**
					 *  Double check to ensure the customer is created in the database
					 */
					if (userManager.validateCustomer(email, password) == true){
						System.out.println("Customer Created!");
						userManager.save();
						return true;
					}
					else{
						System.out.println("Creating of Customer User unsuccessful");
						return false;
					}
				}
				case 3:
					break;
			}
		}catch(InputMismatchException e){
			/**
			 * Brings the interface back to previous menu
			 */
			System.out.println("Enter only numbers");
			System.out.println("Back to Menu...");
			sc.nextLine();
		}
		catch(IndexOutOfBoundsException e){
			/**
			 *  Brings the interface back to previous menu
			 */
			System.out.println("User do not exists");
			System.out.println("Back to Menu...");
			sc.nextLine();
		}
		
		return false;	
	}
	/**
	 *  This method will list down all the Movies Listing available for Customer to select from
	 *  for more information. 
	 */
	public void listMovies()
	{
		int i = 0;
		// Traverse down the List of Movie Objects and print all available Movies 
		System.out.println("------------------------------------------------");
		System.out.println("|                List of Movies                |");
		System.out.println("------------------------------------------------");
		while(i<movieManager.getMovieList().size()){
			System.out.printf("(%d) %s\n", i+1, movieManager.getMovieList().get(i).getTitle());
			i++;
		}
	}
	/**
	 * Takes in the Customer Choice of Movie and prints out all the information regarding the 
	 * movie title selected. 
	 * @param title The title of the Movie the Customer wish to search for more information.
	 */
	public void searchMovies(String title)
	{
		System.out.println("------------------------------------------------");
		System.out.println("|                Movie Details                 |");
		System.out.println("------------------------------------------------");
		System.out.printf("Information for Movie: %s\n", title);
		System.out.printf("Movie Title: %s\n",movieManager.getMovieObject(title).getTitle());
		System.out.printf("Showing Status: %s\n",movieManager.getMovieObject(title).getShowingStatus());
		System.out.printf("SYNOPSIS: %s\n",movieManager.getMovieObject(title).getSynopsis());
		System.out.printf("Director: %s\n",movieManager.getMovieObject(title).getDirector());
		System.out.printf("Cast: %s\n",movieManager.getMovieObject(title).getCast());
		if (movieManager.getMovieObject(title).getRatingList().size() >1){
			movieManager.setOverallRating(movieManager.getMovieObject(title));
			System.out.printf("Overall Reviewer Rating: %.1f\n",movieManager.getMovieObject(title).getOverallRating());
		}
		else{ 
			System.out.println("Overall Reviewer Rating: NA");
			}
		System.out.println("------------Past Reviewer Rating----------------");
		/**
		 * Since it's a List of Rating, need to traverse down the List and print out each individual
		 * past Ratings of the Movie .
		 */
		for (int i =0; i< movieManager.getMovieObject(title).getRatingList().size(); i++){
			System.out.printf("(%d) %.1f\n",i+1,movieManager.getMovieObject(title).getRatingList().get(i));
		}
		/**
		 * Since it's a List of Review, need to traverse down the List and print out each individual
		 * past Reviews of the Movie 
		 */
		System.out.println("------------Past Reviewer Review----------------");
		for (int i =0; i< movieManager.getMovieObject(title).getReviewList().size(); i++){
			System.out.printf("(%d) %s\n",i+1,movieManager.getMovieObject(title).getReviewList().get(i));
		}
		System.out.println("------------------------------------------------");
	}
	/**
	 * Method to allow Customer to book a particular movie from the movie listing.
	 * Customer has to first login before they can book any movie
	 * Only movies that are of status "PREVIEW" and "NOWSHOWING" is allowed for booking.
	 * <p>
	 * Upon selection of the title, cineplex, day and show times, the layout seats is print out
	 * showing the available seats for booking.
	 * If seat selected is already occupied, booking will fail and customer will be prompt for 
	 * another seat selection.
	 * <p>
	 * Upon successful booking and purchase of tickets, the price will be generated and printed out and
	 * the transaction is created and viewed from booking history.
	 */
	public void bookMovies()
	{
		int index = 0, option, maxColumn, row, column, movieTypeOption, numOfKeys = 0, dayOption;
		double price = 0;
		String hallCode, title, showtime, email, transaction, selectedDay, cineplex = null;
		HashMap <String, Double> movieTypeHash = new HashMap <String, Double>();
		List <String> movieTypeList = new ArrayList <String>();
		List <String> showtimesOfSelectedDay = new ArrayList <String>();
		List <String> tempMovieList = new ArrayList <String>();
		HashMap <String, List <String>> hallShowtimesHash= new HashMap <String, List <String>>();
		Scanner sc = new Scanner(System.in);
		/*
		 *  Handles both non-integer input and invalid input
		 */
		try 
		{
			System.out.println("------------------------------------------------");
			System.out.println("|              Booking of Movies               |");
			System.out.println("------------------------------------------------");
			while(index<movieManager.getMovieList().size()){
				if (movieManager.getMovieList().get(index).getShowingStatus().equals("PREVIEW") ||movieManager.getMovieList().get(index).getShowingStatus().equals("NOWSHOWING")){
					tempMovieList.add(movieManager.getMovieList().get(index).getTitle());
				}
				index++;
			}
			for (index= 0 ; index < tempMovieList.size(); index++){
				System.out.printf("(%d) %s\n", index+1, tempMovieList.get(index));
			}
			System.out.println("Enter the number beside the movie title to book: ");
			option = sc.nextInt();
			title = tempMovieList.get(option-1);	
			do {
				System.out.println("Select the cineplax (1)Cineplex.AMK (2)Cineplex.SK (3)Cineplex.YCK:");
				System.out.println("AMK = Ang Mo Kio, SK = Seng Kang, YCK = Yio Chu Kang");
				option = sc.nextInt();
				sc.nextLine();
				if (option == 1)
					cineplex = "Cineplex.AMK";
				else if (option == 2)
					cineplex = "Cineplex.SK";
				else if (option == 3)
					cineplex = "Cineplex.YCK";
			} while(option<1 || option >3);
			System.out.println("Select a movie type:");
			movieTypeHash = movieManager.getMovieTypeRateHash();
			for (String key : movieTypeHash.keySet()){
				System.out.println("("+ (numOfKeys+1) +") " + key);
				movieTypeList.add(key);
				numOfKeys++;
			}
			movieTypeOption = sc.nextInt();
			while (movieTypeOption <=0 || movieTypeOption > movieTypeList.size()){
				System.out.println("Invalid input!");
				System.out.println("Select a movie type:");
				for (int i = 0 ; i < movieTypeList.size(); i++ ){
					System.out.println("("+ (i+1) +") " + movieTypeList.get(i));
				}
				movieTypeOption = sc.nextInt();
			}
			
			hallShowtimesHash= showtimeManager.getHallShowtimeHash(hallManager.getHallList().get(option-1).getHallCode(), title);
			if (hallShowtimesHash.size()== 0){ //empty hashMap
				System.out.println("No showtimes available!");
				return;
			}
			else{
				System.out.printf("Select a day:\n");
				List <String> movieShowday = new ArrayList <String>();
				index =0;
				for (String key : hallShowtimesHash.keySet()){
					index++;
					movieShowday.add(key);
					System.out.printf("(%d) %s\n",index,key);
				}
				dayOption = sc.nextInt()-1;
				selectedDay = movieShowday.get(dayOption);
			}
			System.out.println("Showtimes available:");
			if (showtimeManager.getMovieShowtimes(title, selectedDay).size() == 0){
				System.out.println("No showtimes available today!");
			}
			else {
				showtimesOfSelectedDay = hallShowtimesHash.get(selectedDay);
				for (int i = 0 ; i <showtimesOfSelectedDay.size(); i++){
					System.out.printf("(%d) %s\n",(i+1),showtimesOfSelectedDay.get(i));
					}
				}
			System.out.printf("Enter number beside showtime to select\n");
			index = sc.nextInt()-1;
			sc.nextLine();
			showtime = showtimesOfSelectedDay.get(index);
			System.out.println("Seats available");
			hallCode = showtimeManager.getShowtimeObject(title, showtime,selectedDay).getHallCode();
			maxColumn = hallManager.getHallObject(hallCode).getColumns();
			printLayout(hallCode, title, showtime,selectedDay);
			System.out.println("Enter choice of row: ");
			row = sc.nextInt();
			System.out.println("Enter choice of column:");
			column = sc.nextInt();
			sc.nextLine();
			System.out.println("Enter your email again to confirm booking: ");
			email = sc.nextLine();
			if (bookingManager.validateSeatChoice(showtimeManager.getShowtimeObject(title, showtime, selectedDay), hallCode, row, column) == true){
				showtimeManager.updateLayout(showtimeManager.getShowtimeObject(title, showtime, selectedDay), maxColumn, row, column);		
				price = bookingManager.generatePrice(email, title,hallManager.getHallObject(hallCode).getHallClass(), movieTypeList.get(movieTypeOption-1), selectedDay);
				transaction = bookingManager.generateTransaction(email,showtimeManager.getShowtimeObject(title, showtime,selectedDay),price);
				System.out.printf("Movie will be screened at Hall: %s \n",showtimeManager.getMovieShowtimes(title,selectedDay).get(index).getHallCode());
				System.out.println(transaction);
				/**
				 * Save the transaction and layout to database so that it can be updated.
				 */
				bookingManager.save(); 	
				showtimeManager.save(); 
			}
			else {
				System.out.println("Seats occupied or unknown Seats Selection");
			}
		}catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid option");
        }
		catch(InputMismatchException e){
			/**
			 * Brings the interface back to previous menu.
			 */
			System.out.println("Enter only numbers");
			System.out.println("Back to Menu...");
			sc.nextLine();
		}
	}
	/**
	 * This method will print out the layout of the selected Hall in a format where the screen, exit
	 * main stairways and aisle are included when given the hallCode, title, showtime, selectedDay.
	 * It also shows the available and occupied seats.
	 * @param hallCode Hall Code to identify which Hall the selected show time is being screened at.
	 * @param title The title of the Movie that is being screened.
	 * @param showtime The show time that is selected.
	 * @param selectedDay The day that is selected.
	 */
	public void printLayout(String hallCode, String title, String showtime, String selectedDay)
	{
		int row = hallManager.getHallObject(hallCode).getRows();
		int col = hallManager.getHallObject(hallCode).getColumns();
		int count = 0;
		for (int i = 0; i<col/2 ; i++){
			System.out.printf(" ");
		}
		System.out.println("|            Screen             |");
		System.out.printf("\n\n\n");
		for(int i =0; i<row;i++){
			System.out.printf("|  ");
			for(int j =0; j<=col;j++){
				if (j == col/2){
					System.out.printf("      ");
				}	
				else if(showtimeManager.getShowtimeObject(title, showtime, selectedDay).getLayout()[count] == 0){
					System.out.printf("|0|");
					count++;
				}
				else if (showtimeManager.getShowtimeObject(title, showtime, selectedDay).getLayout()[count] == 1){
					System.out.printf("|X|");
					count++;
				}
			}
			System.out.printf("  |");
			System.out.println(" ");

		}
		System.out.println("\n");
		for (int i = 0; i<col; i++){
			System.out.printf(" ");
		}
		System.out.println("|            Entrance             |");
		System.out.println("\n");
		System.out.println("------------------");
		System.out.println("|Legend:         |");
		System.out.println("|0: Available    |");
		System.out.println("|X: Taken        |");
		System.out.println("------------------");
	}
	/**
	 * This method will prompt the customer for their email and password for verification.
	 * Upon successful verification, it will print out all the booking history of a selected customer.
	 * If a invalid email and password combination are given, no transactions will be printed.
	 */
	public void viewBookingHistory()
	{
		String email, password;
		int index;
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter email:");
		email = sc.nextLine();
		System.out.println("Enter password:");
		password = sc.nextLine();
		if ( userManager.validateCustomer(email, password) == true){
			for (index = 0; index < userManager.getUserObject(email).getBookingHistory().size();index++){
				System.out.printf("(%d) %s",index+1,userManager.getUserObject(email).getBookingHistory().get(index));
			}
		}
		else 
		{
			System.out.println("User does not exists or wrong password/email entered");
		}
	}
	/**
	 * A menu will be printed to prompt the movie-goer to either select the option of giving a selected 
	 * movie rating or write a review for the selected movie from the movie list.
	 * Rating can only be in the range of 1-5. (5 being the best)
	 * Movie-Goer are able to write Reviews and give Ratings for all movies on the list.
	 */
	public void writeReviewAndRating()
	{
		int option,index=0;
		String title = null, review = null;
		double rating =0;
		Scanner sc = new Scanner(System.in);
		System.out.println("------------------------------------------------");
		System.out.println("|             Reviews and Ratings              |");
		System.out.println("------------------------------------------------");
		System.out.println("(1) Write Reviews");
		System.out.println("(2) Give Ratings");
		System.out.println("(3) Back to Main Menu");
		/**
		 *  Catches Exception when user enters a string instead of integer.
		 */
		try 
		{
			option = sc.nextInt();
			sc.nextLine();
			switch(option){
				case 1:
				{
					listMovies();
					do {
						System.out.println("Enter the movie to give review");
						index = sc.nextInt()-1;
						sc.nextLine();
						title = movieManager.getMovieList().get(index).getTitle();
					} while(index > movieManager.getMovieList().size() | index < 0);
					System.out.println("Enter the review:");
					review = sc.nextLine();
					movieManager.updateReview(movieManager.getMovieList().get(index), review);
					System.out.println("Reviews Added");
					movieManager.save();
					break;
				}
				case 2:
				{
					listMovies();
					do {
						System.out.println("Enter the movie to give Rating");
						index = sc.nextInt()-1;
						sc.nextLine();
						title = movieManager.getMovieList().get(index).getTitle();
					} while(index > movieManager.getMovieList().size() | index < 0);
					do {
						System.out.println("Select the Ratings to give: [1 to 5, 5 being the best]");
						rating = sc.nextDouble();
						sc.nextLine();
					} while (rating <1 | rating >5);
					movieManager.updateRating(movieManager.getMovieList().get(index), rating);
					System.out.println("Ratings Added");	
					movieManager.save();
					break;
				}
				case 3:
					break;
				default:
					break;
			}
		}catch(InputMismatchException e){
			/**
			 *  Brings the interface back to previous menu.
			 */
			System.out.println("Enter only numbers");
			System.out.println("Back to Menu...");
			sc.nextLine();
		}
	}
	/**
	 * A menu will be printed to prompt the movie-goer to select to see the top 5 movies ranked by
	 * overall sales or overall ratings.
	 * The overall rating and overall sales will be printed alongside the ranking.
	 */
	public void printTop5()
	{
		int option, index=0;
		List <String> rankedMovieRatingList = new ArrayList <String> ();
		HashMap <String, Double> rankedMovieSalesHash = new HashMap <String, Double>();
		List <String> rankedMovieSalesMovies = new ArrayList <String>();
		List <Double> overallRatingsList = new ArrayList <Double>();
		Scanner sc = new Scanner(System.in);
		System.out.println("------------------------------------------------");
		System.out.println("|              Top 5 Movies                    |");
		System.out.println("------------------------------------------------");
		System.out.println("(1) By Ticket Sales");
		System.out.println("(2) By Overall Reviewers' Rating");
		System.out.println("(3) Back to Main Menu");
		/**
		 *  Catches Exception when user enters a string instead of integer
		 */
		try {
			option = sc.nextInt();
			sc.nextLine();
			switch (option){
				case 1:
				{
					System.out.println("<Ranking by Ticket Sales>");
					rankedMovieSalesHash = movieManager.generateTotalSales();
					for (String key:rankedMovieSalesHash.keySet()){
						rankedMovieSalesMovies.add(key);
					}
					for (index =0 ; index< rankedMovieSalesHash.size() && index <5 ;index++){
						System.out.printf("(%d) %s (Ticket Sales: S$ %.1f) \n",index+1,rankedMovieSalesMovies.get(index),(rankedMovieSalesHash.get(rankedMovieSalesMovies.get(index))));
					}
					break;
				}
				case 2:
				{
					System.out.println("<Ranked by Overall Reviewers' Rating>");
					overallRatingsList = movieManager.setOverallRating();
					rankedMovieRatingList = movieManager.rankByRatings(overallRatingsList);
					for (index = 0 ;index< rankedMovieRatingList.size() && index <5; index++){
						System.out.printf("(%d) %s (Overall Ratings: %.1f) \n",index+1,rankedMovieRatingList.get(index), overallRatingsList.get(index));
					}
					break;
				}
				case 3:
					break;
				default:
					break;
			}
		}catch(InputMismatchException e){
			/**
			 * Brings the interface back to previous menu.
			 */
			System.out.println("Enter only numbers");
			System.out.println("Back to Menu...");
			sc.nextLine();
			}
	}
	/**
	 * Implements all the different functions for movie-goers ranging from listing and searching movie details,
	 * booking of movie tickets, viewing of booking history, listing top 5 ranking of mvoies and 
	 * writing of rewiew and ratings for movies.
	 * A menu will be printed to prompt the movie-goer to choose the various functions.
	 * This method will then call the respective sub-methods to perform the various functions.
	 */
	public void customerModule()
	{
		int option = 0;
		Scanner sc = new Scanner(System.in);
		do{
			System.out.println("------------------------------------------------");
			System.out.println("|                 Customer Menu                |");
			System.out.println("------------------------------------------------");
			System.out.println("(1) List of Movies");	
			System.out.println("(2) View Movie Details");
			System.out.println("(3) Booking of Movies Tickets");
			System.out.println("(4) View Booking History");
			System.out.println("(5) List top 5 rankings of Movies");
			System.out.println("(6) Write Review and Rating");
			System.out.println("(7) Back to Main Menu");
			System.out.println("Enter your choice:");
			/**
			 *  Handles Exception when User enters string instead of number
			 */
			try
			{				
				option = sc.nextInt();
				sc.nextLine();
				switch(option){
					case 1:
					{
						listMovies();
						break;
					}
					case 2:
					{
						listMovies();
						System.out.println("Enter the number beside the Movie Title to view more information");
						option = sc.nextInt();
						sc.nextLine();
						/**
						 *  Searches the movie information by title via the index
						 */
						searchMovies(movieManager.getMovieList().get(option-1).getTitle());
						break;
					}
					case 3:
					{
						if (customerLogin()==true){
							bookMovies();
						}
						break;
					}
					case 4:
					{
						viewBookingHistory();
						break;
					}
					case 5:
					{
						printTop5();
						break;
					}
					case 6:
					{
						writeReviewAndRating();
						break;
					}
					case 7:
					{
						System.out.println("Back to Main Menu....");
						System.out.println(" ");
						break;
					}
				}
			}catch(InputMismatchException e){
				System.out.println("Invalid input type");
				System.out.println("Enter only the number stated");
				sc.nextLine();
			}
		} while (option < 7);
	}
	
	
	/*---------------------------------------------------------------------------------              
	 *| 						    Admin Module Methods                              |
	 *---------------------------------------------------------------------------------*/
	
	/**
	 *  Method to validate the authenticity of existing Admin User using their email and password.
	 *  If it is new Admin User, it will allow the creation of a new User object given that a
	 *  default General Admin Password is validated.
	 *  A total of 3 tries are given for logging in before it returns back to main meny.
	 *  @return false If the admin login fails or when creation of new admin user fails.
	 *          <p>
	 *          true If the admin login is successful or when creation of new user is successful.
	 */
	public boolean adminLogin()
	{
		int option;
		String name, password, mobileNumber, email, ageGroup;
		Scanner sc = new Scanner(System.in);
		System.out.println("------------------------------------------------");
		System.out.println("|               Admin Login Page               |");
		System.out.println("------------------------------------------------");
		System.out.println("(1) Existing User");
		System.out.println("(2) New User");
		System.out.println("(3) Back");
		System.out.println("Enter your choice: ");
		/**
		 *  Handles Exception when User enters string instead of number
		 */
		try 
		{
			option = sc.nextInt();
			sc.nextLine();
			switch(option){
				/**
				 *  For Existing Admin User to verify their email and password
				 */
				case 1:
				{
					System.out.println("<Verification of Admin>");
					System.out.println("Enter your email:");
					email = sc.nextLine();
					
					System.out.println("Enter your password:");
					password = sc.nextLine();
					if(userManager.validateAdmin(email, password) == true){
						System.out.println("Verfication Success!");
						return true;
					}
					else{ 
						System.out.println("Verfication Failed!");
						return false;
					}
				}
				/**
				 *  For new Admin User to create new User
				 */
				case 2:
				{
					String adminGeneralPassword = "moblimaAdminPass";
					/**
					 *  To track number of tries for the admin verification
					 */
					int count = 1; 
					System.out.println("<Verification of Admin>");
					System.out.println("Key in the admin password: ");
					while (sc.nextLine().equals(adminGeneralPassword) == false && count < 3){
						System.out.println("Wrong admin password!");
						System.out.println("Number of tries left: " + (3-count));
						System.out.println("Key in the admin password: ");
						count++;
					}
					/**
					 *  reached the maximum number of tries
					 */
					if (count >= 3){
						System.out.println("Maximum number of tries have reached!");
						System.out.println("Failed to create a new admin user");
						return false;
					}
					System.out.println("Successful Verification!");
					System.out.println("<Creation of new Admin User>");
					System.out.println("Key in your name: ");
					name = sc.nextLine();
					
					System.out.println("Key in your password: ");
					password = sc.nextLine();
					
					System.out.println("Key in your mobileNumber: ");
					mobileNumber = sc.nextLine();
					
					System.out.println("Key in your email: ");
					email = sc.nextLine();
					ageGroup = "Adult";
					
					userManager.createAdmin(name, password, mobileNumber, email, ageGroup);
					if (userManager.validateAdmin(email, password) == true){
						System.out.println("Admin Created!");
						userManager.save();
						return true;
					}
					else{
						System.out.println("Creating of Admin User unsuccessful");
						return false;
					}
				}
				case 3:
					break;
				}
			}catch(InputMismatchException e){
				System.out.println("Invalid input type");
				System.out.println("Enter only the number stated");
				sc.nextLine();
			}catch(IndexOutOfBoundsException e){
				/**
				 *  Brings the interface back to previous menu
				 */
				System.out.println("User do not exists");
				System.out.println("Back to Menu...");
			}
		return false;
	}
	/**
	 * Method for Admin User to create new movie listing.
	 * Admin has to fill up the title, synopsis, director, casts, showing status.
	 */
	public void createMovieListing()
	{
		String title, synopsis, director, cast, showingStatus = null;
		String [] castArray;
		Scanner sc = new Scanner(System.in); 
		int option;
		System.out.printf("Enter the Movie Title:\n");
		title = sc.nextLine();
		/**
		 * Checks if the movie already exists in the movie listing.
		 */
		while (title.equals("exit") == false && movieManager.checkMovieExistence(title)== true){
			System.out.println("This Movie is already in the Movie List!");
			System.out.println("Key " + "\""+"exit"+"\"" + " to exit");
			System.out.printf("Enter the Movie Title:\n");
			title = sc.nextLine();
		}
		/**
		 * Returns back to Admin main menu.
		 */
		if (title.equals("exit")){
			return;
		}
		/**
		 *  Choices will be provided for the Admin.
		 */
		System.out.printf("Enter the Showing Status:\n");
		System.out.printf("(1) COMINGSOON\n");
		System.out.printf("(2) PREVIEW\n");
		System.out.printf("(3) NOWSHOWING\n");
		do {	
			option = sc.nextInt();
			sc.nextLine();
			switch(option){
				case 1: {showingStatus = "COMINGSOON";break;}
				case 2: {showingStatus = "PREVIEW";break;}
				case 3: {showingStatus = "NOWSHOWING";break;}
				default:{
					System.out.printf("Invalid Choice!");
					System.out.printf("Enter the Showing Status\n");
					break;
				}
			}
		} while(option > 3 || option < 1);
		System.out.printf("Enter the Movie Synopsis:\n");
		synopsis = sc.nextLine();
		
		System.out.printf("Enter the Movie Director:\n");
		director = sc.nextLine();
		/**
		 *  Ensures that at least 2 Casts are added.
		 */
		do {
			System.out.println("Enter at least 2 Movie Casts (In the format of Cast1, Cast2,...)\n");
			cast = sc.nextLine();
			castArray = cast.split(",");
		} while (castArray.length <2);
		
		movieManager.addMovie(title, showingStatus, synopsis, director, cast);
		movieManager.save();
		/**
		 *  Prints out the new Movie Listing.
		 */
		System.out.printf("Movie Listing Created:\n");
		System.out.printf("Movie Title: %s\n",title);
		System.out.printf("Showing Status: %s\n", showingStatus);
		System.out.printf("SYNOPSIS: %s\n",synopsis);
		System.out.printf("Director: %s\n",director);
		System.out.printf("Cast: %s\n",cast);
	}
	/**
	 * Method for Admin User to update existing movie listing.
	 * Admin first selects the title from the list and then the respective part of the movie listing
	 * to update
	 * <p>
	 * This method also allows Admin User to change a movie showing status to "ENDOFSHOW" to remove
	 * it from the movie listing that is visible to movie-goers when making a booking.
	 */
	public void updateMovieListing()
	{
		int option, index;
		String title; // Variable to store the movie title to update the information
		String newInformation; // Variable to store the new information to be update
		String showingStatus = null; // Enum variable to store Showing Status
		String[] castArray;
		Scanner sc = new Scanner(System.in);
		listMovies();
		System.out.println("Enter the title of Movie Listing to update: ");
		System.out.println("Enter '0' to return to back");
		index = sc.nextInt()-1;
		sc.hasNextLine();
		if(index == -1)
			return;
		title = movieManager.getMovieList().get(index).getTitle();
		System.out.println("Please indicate the information to update:");
		System.out.println("(1)Title");
		System.out.println("(2)Showing Status");
		System.out.println("(3)SYNOPSIS");
		System.out.println("(4)Director");
		System.out.println("(5)Casts");
		/**
		 * Handles Exception when User enters string instead of number
		 */
		try 
		{
			option = sc.nextInt();
			sc.nextLine();
			switch(option){
				case 1:
				{
					System.out.println("Enter the new title");
					newInformation = sc.nextLine();
					showtimeManager.updateTitle(newInformation, movieManager.getMovieList().get(index).getTitle());
					movieManager.updateTitle(index, newInformation);
					System.out.println("Title Updated!");
					break;
				}
				case 2:
				{
					System.out.println("Enter the new Showing Status");
					System.out.printf("(1) COMINGSOON\n");
					System.out.printf("(2) PREVIEW\n");
					System.out.printf("(3) NOWSHOWING\n");
					System.out.printf("(4) ENDOFSHOWING\n");
					do {
						option = sc.nextInt();
						sc.nextLine();
						switch(option){
							case 1: {showingStatus = "COMINGSOON";break;}
							case 2: {showingStatus = "PREVIEW";break;}
							case 3: {showingStatus = "NOWSHOWING";break;}
							case 4: 
							{
								showingStatus = "ENDOFSHOWING";
								showtimeManager.removeShowtimeByTitle(movieManager.getMovieList().get(index).getTitle());
								break;
							}
							default:{
								System.out.printf("Invalid Choice!");
								System.out.printf("Enter the Showing Status\n");
								break;
							}
						}
					} while( option >4 || option < 1);
					movieManager.updateShowingStatus(index, showingStatus);
					System.out.println("Showing Status Updated!");
					break;
				}
				case 3:
				{
					System.out.println("Enter the new Synopsis");
					newInformation = sc.nextLine();
					movieManager.updateSynopsis(index, newInformation);
					System.out.println("Synopsis Updated!");
					break;
				}
				case 4:
				{
					System.out.println("Enter the new Director");
					newInformation = sc.nextLine();;
					movieManager.updateDirector(index, newInformation);
					System.out.println("Director Updated!");
					break;
				}
				case 5:
				{
					/**
					 * Ensure that at least 2 Casts are added
					 */
					do {
						System.out.println("Enter the new Casts");
						newInformation = sc.nextLine();
						castArray = newInformation.split(",");
					} while (castArray.length <2);
					movieManager.updateCast(index, newInformation);
					System.out.println("Casts Updated!");
					break;
				}
			}
		}catch(InputMismatchException e){
			System.out.println("Invalid input type");
			System.out.println("Enter only the number stated");
			sc.nextLine();
		}
		movieManager.save(); // Ensure that the movie information is updated.
		showtimeManager.save(); // Ensure that movie information in showtimeManager is updated.
	}
	/**
	 * Method for Admin User to delete existing movie listing.
	 * Admin first selects the title from the list.
	 */
	public void deleteMovieListing()
	{
		int index;
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the movie title to delete the listing: ");
		System.out.println("Enter '0' to exit");
		listMovies();
		index = sc.nextInt()-1;
		sc.hasNextLine();
		if(index == -1)
			return;
		if( movieManager.removeMovie(movieManager.getMovieList().get(index)) == true){
			System.out.println("Movie Listing Successfully Removed!");
		}
		/**
		 *  To take care of cases where the Movie Listing does not exists
		 */
		else if (movieManager.getMovieList().get(index) == null){
			System.out.println("No such Movie Listing");
		}
		else{
			System.out.println("Removal of Movie Listing Unsuccessful!");
		}
		movieManager.save();
		showtimeManager.save();
	}
	/**
	 * Method for Admin User to create new showtimes for existing movies.
	 * Admin needs to select the movie title, hall to screen, the day and timeslot to screen based
	 * on real-time calendar.
	 * It does not allows the creation of timeslot that already has show being screened at the
	 * selected hall.
	 * Show times can only be created minimally 1 day away and there is no maximum date Admin can create
	 * a show time.
	 */
	public void createShowtimes()
	{
		/**
		 *  Catches NullPointerException when index input by Admin User is invalid
		 */
		try {
			int index =0,option, maxCapacity, numOfDays, minInterval,time;
			String title,hallCode;
			boolean uniqueShowtime;
			List <String> showDay_List = new ArrayList <String>();
			List <String> showtime_List = new ArrayList <String>();
			List <String> hallShowtimeList = new ArrayList <String>();
			List <String> uniqueShowtimeList = new ArrayList <String>(); // does not clash with same hall showtimes
			Scanner sc =  new Scanner(System.in);
			listMovies();
			/**
			 *  Find the Movie to create showtimes
			 */
			System.out.println("Enter the number of the movie to add showtimes");
			System.out.println("Enter '0' to return to back");
			index = sc.nextInt()-1;
			sc.nextLine();
			if(index == -1)
				return;
			title = movieManager.getMovieList().get(index).getTitle();
			
			/**
			 *  Choose the Hall to Assign the showtime
			 */
			System.out.println("Enter the choice of Hall to screen");
			for(int i=0; i<hallManager.getHallList().size();i++){
				System.out.printf("(%d) %s\n",i+1,hallManager.getHallList().get(i).getHallCode());
			}
			index = sc.nextInt()-1;
			sc.nextLine();
			hallCode = hallManager.getHallList().get(index).getHallCode();
			maxCapacity = hallManager.getHallList().get(index).getHallCapacity();
			
			/**
			 *  Find the day and timeslot to create showtime listing
			 */
			System.out.println("Enter the number of days from today to display for selection of show day (i.e. 1 represents tomorrow's date): ");
			numOfDays = sc.nextInt();
			while (numOfDays <=0){
				System.out.println("Invalid input!");
				System.out.println("Enter the number of days from today to display for selection of show day (i.e. 1 represents tomorrow's date): ");
			}
			if(numOfDays >0){
				showDay_List = (showtimeManager.displayDayOption(numOfDays));
				for (index = 1 ; index <= showDay_List.size(); index++){
					System.out.println("("+index+") "+ showDay_List.get(index-1));
				}
			}
			
			/**
			 *  Selection of the day to screen movie
			 */
			System.out.println("Enter the number beside the date for selection of show day:");
			option = sc.nextInt();	
			while (option <=0 || option > showDay_List.size()){
				System.out.println("Invalid Input");
				for (index = 1 ; index <= showDay_List.size(); index++){
					System.out.println("("+index+") "+ showDay_List.get(index-1));
				}
				System.out.println("Enter the number beside the date for selection of show day:");
				option = sc.nextInt();	
			}
			/**
			 *  Selection of the time slot
			 */			
			hallShowtimeList = showtimeManager.getHallShowtimeList(hallCode,showDay_List.get(option-1));
			System.out.println("Enter the minute interval (0 to 780) to show the range of showtime from 11:00 (i.e 120 will give 11:00, 13:00, ...)");
			minInterval = sc.nextInt();
			/**
			 * checks if the user keys in a minute interval that is between 0 to 780 (i.e. 11am to 12am)
			 */
			while (minInterval <0 || minInterval > 780){
				System.out.println("Invalid input!");
				System.out.println("Enter the minute interval (0 to 780) to show the range of showtime from 11:00 (i.e 120 will give 11:00, 13:00, ...)");
				minInterval = sc.nextInt();
			}
			
			/**
			 *  Only display show times that are still available to assign
			 */
			showtime_List = showtimeManager.displayTimeOption(minInterval, option);	
			System.out.println("Available showtime to assign to movie");
			System.out.println("Enter the number beside the showtime: ");
			if (hallShowtimeList.size() >= 1){
				for (index = 0; index < showtime_List.size(); index++){
					uniqueShowtime = true;
					for (int i = 0 ; i < hallShowtimeList.size(); i++){
						if (showtime_List.get(index).equals(hallShowtimeList.get(i)) == true){
							uniqueShowtime = false;
							break;
						}
					}
					if (uniqueShowtime == true){
						uniqueShowtimeList.add(showtime_List.get(index));
					}
				}
				for (index = 0; index < uniqueShowtimeList.size(); index++){
					System.out.println("("+(index+1)+") "+ uniqueShowtimeList.get(index));

				}
				time = sc.nextInt();
				while (time <= 0 || time > uniqueShowtimeList.size()) {
					System.out.println("Invalid Input");
					for (index = 1; index <= uniqueShowtimeList.size(); index++) {
						System.out.println("(" + index + ") " + uniqueShowtimeList.get(index - 1));
					}
					System.out.println("Enter the number beside the showtime: ");
					time = sc.nextInt();
				}
				showtimeManager.addShowtime(title,  uniqueShowtimeList.get(time-1),showDay_List.get(option-1), hallCode, maxCapacity);
			} 
			
			else {
				for (index = 0; index < showtime_List.size(); index++) {
					System.out.println("(" + (index + 1) + ") " + showtime_List.get(index));
				}
				time = sc.nextInt();
				while (time <= 0 || time > showtime_List.size()) {
					System.out.println("Invalid Input");
					for (index = 1; index <= showtime_List.size(); index++) {
						System.out.println("(" + index + ") " + showtime_List.get(index - 1));
					}
					System.out.println("Enter the number beside the showtime: ");
					time = sc.nextInt();
				}		
				showtimeManager.addShowtime(title,  showtime_List.get(time-1),showDay_List.get(option-1), hallCode, maxCapacity);
			}
				
			showtimeManager.save();
			System.out.println("Showtime Added!");
		}catch(NullPointerException e){
			System.out.println("Invalid option input during the process");
			System.out.println("Showtime no added");
			System.out.println("Back to Menu...");
		}
	}
	/**
	 * Method for Admin User to update existing showtimes.
	 */
	public void updateShowtimes()
	{
		int index,showtimeObjOption,option = 0,numOfDays,minInterval,time;
		String hallCode;
		boolean uniqueShowtime;
		List <String> hallShowtimeList = new ArrayList <String>();
		List <String> uniqueShowtimeList = new ArrayList <String>();
		List <String> showDayList = new ArrayList <String>();
		List <String> showtimeList = new ArrayList <String>();
		Scanner sc =  new Scanner(System.in);
		System.out.println("Enter the existing showtimes to update");
		for(index =0; index < showtimeManager.getShowtimeList().size(); index++){
			System.out.printf("(%d)Title: %s | Day: %s | Showtime: %s | HallCode: %s\n",index+1,showtimeManager.getShowtimeList().get(index).getTitle(),showtimeManager.getShowtimeList().get(index).getDay(),showtimeManager.getShowtimeList().get(index).getShowtime(), showtimeManager.getShowtimeList().get(index).getHallCode());
		}
		/**
		 *  Store the selected showtime object's index.
		 */
		showtimeObjOption = sc.nextInt()-1; 
		sc.nextLine();
		while (showtimeObjOption > showtimeManager.getShowtimeList().size()|| showtimeObjOption <0){
			System.out.println("Invalid input!");
			for(index =0; index < showtimeManager.getShowtimeList().size(); index++){
				System.out.printf("(%d)Title: %s | Day: %s | Showtime: %s | HallCode: %s\n",index+1,showtimeManager.getShowtimeList().get(index).getTitle(),showtimeManager.getShowtimeList().get(index).getDay(),showtimeManager.getShowtimeList().get(index).getShowtime(), showtimeManager.getShowtimeList().get(index).getHallCode());
			}
			showtimeObjOption = sc.nextInt()-1;
			sc.nextLine();
		}
		/**
		 * To handle Exception that arises from invalid input such as String typed input.
		 */
		try 
		{
			System.out.println("Enter the number of days from today to display for selection of show day (i.e. 1 represents tomorrow's date): ");
			numOfDays = sc.nextInt();
			while (numOfDays <=0){
				System.out.println("Invalid input!");
				System.out.println("Enter the number of days from today to display for selection of show day (i.e. 1 represents tomorrow's date): ");
			}
			if(numOfDays >0){
				showDayList = (showtimeManager.displayDayOption(numOfDays));
				for (index = 1 ; index <= showDayList.size(); index++){
					System.out.println("("+index+") "+ showDayList.get(index-1));
				}
			}
			
			/**
			 *  Selection of the day to screen movie
			 */
			System.out.println("Enter the number beside the date for selection of show day:");
			option = sc.nextInt();	
			while (option <=0 || option > showDayList.size()){
				System.out.println("Invalid Input");
				for (index = 1 ; index <= showDayList.size(); index++){
					System.out.println("("+index+") "+ showDayList.get(index-1));
				}
				System.out.println("Enter the number beside the date for selection of show day:");
				option = sc.nextInt();	
			}
			/**
			 *  Selection of the time slot
			 */			
			hallCode = showtimeManager.getShowtimeObjectByIndex(showtimeObjOption).getHallCode();
			hallShowtimeList = showtimeManager.getHallShowtimeList(hallCode,showDayList.get(option-1));
			System.out.println("Enter the minute interval (0 to 780) to show the range of showtime from 11:00 (i.e 120 will give 11:00, 13:00, ...)");
			minInterval = sc.nextInt();
			/**
			 * checks if the user keys in a minute interval that is between 0 to 780 (i.e. 11am to 12am)
			 */
			while (minInterval <0 || minInterval > 780){
				System.out.println("Invalid input!");
				System.out.println("Enter the minute interval (0 to 780) to show the range of showtime from 11:00 (i.e 120 will give 11:00, 13:00, ...)");
				minInterval = sc.nextInt();
			}
			
			/**
			 *  Only display show times that are still available to assign
			 */
			showtimeList = showtimeManager.displayTimeOption(minInterval, option);	
			System.out.println("Available showtime to assign to movie");
			System.out.println("Enter the number beside the showtime: ");
			if (hallShowtimeList.size() >= 1){
				for (index = 0; index < showtimeList.size(); index++){
					uniqueShowtime = true;
					for (int i = 0 ; i < hallShowtimeList.size(); i++){
						if (showtimeList.get(index).equals(hallShowtimeList.get(i)) == true){
							uniqueShowtime = false;
							break;
						}
					}
					if (uniqueShowtime == true){
						uniqueShowtimeList.add(showtimeList.get(index));
					}
				}
				for (index = 0; index < uniqueShowtimeList.size(); index++){
					System.out.println("("+(index+1)+") "+ uniqueShowtimeList.get(index));

				}	
				time = sc.nextInt();
				while (time <=0 || time > uniqueShowtimeList.size() ){
					System.out.println("Invalid Input");
					for (index = 1; index <= uniqueShowtimeList.size(); index++){
						System.out.println("("+index+") "+ uniqueShowtimeList.get(index-1));
					}
					System.out.println("Enter the number beside the showtime: ");
					time = sc.nextInt();	
				}			
				showtimeManager.updateShowtime(showtimeObjOption, uniqueShowtimeList.get(time-1));
			}
			else {
				for (index = 0; index < showtimeList.size(); index++){
					System.out.println("("+(index+1)+") "+ showtimeList.get(index));
				}
				time = sc.nextInt();
				while (time <=0 || time > showtimeList.size() ){
					System.out.println("Invalid Input");
					for (index = 1; index <= showtimeList.size(); index++){
						System.out.println("("+index+") "+ showtimeList.get(index-1));
					}
					System.out.println("Enter the number beside the showtime: ");
					time = sc.nextInt();	
				}	
				showtimeManager.updateShowtime(showtimeObjOption, showtimeList.get(time-1));

			}
			showtimeManager.updateDay(showtimeObjOption, showDayList.get(option-1));
			showtimeManager.save();
			System.out.println("Showtime Added!");
			
		}catch(InputMismatchException e){
				System.out.println("Invalid option input during the process");
				System.out.println("Showtime not updated");
				System.out.println("Back to Menu...");
		}
		showtimeManager.save();
	}
	/**
	 * Method for Admin User to delete existing showtimes.
	 * Admin needs to select the existing showtimes to delete.
	 */
	public void deleteShowtimes()
	{
		/**
		 *  Catches NullPointerException when the input entered by the Admin User does not
		 *  exists.
		 */
		try 
		{
			int index,option;
			String title,showtime, day;
			Scanner sc =  new Scanner(System.in);
			System.out.println("Enter the existing showtimes to delete");
			for(index =0; index < showtimeManager.getShowtimeList().size(); index++){
				System.out.printf("(%d)Title: %s | Day: %s | Showtime: %s | HallCode: %s\n",index+1,showtimeManager.getShowtimeList().get(index).getTitle(),showtimeManager.getShowtimeList().get(index).getDay(),showtimeManager.getShowtimeList().get(index).getShowtime(), showtimeManager.getShowtimeList().get(index).getHallCode());
			}
			option = sc.nextInt()-1;
			sc.nextLine();
			title = showtimeManager.getShowtimeList().get(option).getTitle();
			showtime = showtimeManager.getShowtimeList().get(option).getShowtime();
			day = showtimeManager.getShowtimeList().get(option).getDay();
			if (showtimeManager.removeShowtime(showtimeManager.getShowtimeObject(title, showtime, day))==true){
				System.out.println("Showtime Removed! Successfully");
			}
			else{
				System.out.println("Showtime not found!");
			}
			showtimeManager.save();
		}catch(NullPointerException e){
			System.out.println("Invalid option input during the process");
			System.out.println("Showtime no added");
			System.out.println("Back to Menu...");
		}
	}
	/**
	 * This methods implements all the sub-methods to handle the configuration of different 
	 * system settings of the MoblimaApp.
	 * It prints out a menu to which prompts the Admin User to select between configuring of
	 * rates, adding new halls or removing of existing halls.
	 */
	public void configureSystem()
	{
		int option = 0;
		do {
			Scanner sc = new Scanner(System.in);
			System.out.println("------------------------------------------------");
			System.out.println("|          Configure System Settings           |");
			System.out.println("------------------------------------------------");
			System.out.println("(1) Configure Rates");
			System.out.println("(2) Add New Halls");
			System.out.println("(3) Remove Existing Halls");
			System.out.println("(4) Delete Existing Admin User");
			System.out.println("(5) Back to Main Menu");
			System.out.println("Enter your choice:");
			/**
			 *  Handles Exception when User enters string instead of number
			 */
			try 
			{
				option=sc.nextInt();
				sc.nextLine();
				switch(option){
					case 1:{
						modifyRates();
						break;
					}
					case 2:{
						createHall();
						break;
					}
					case 3:{
						removeHall();
						break;
					}
					case 4:{
						if (deleteAdminUser() == true)
							System.out.println("Admin User Deleted Successfully!");
						else
							System.out.println("Admin User Not Deleted");
						break;
					}
					case 5:
						break;
					default:
						break;
				}
			}catch(InputMismatchException e){
				System.out.println("Invalid input type");
				System.out.println("Enter only the number stated");
			}
		} while(option !=5);
	}
	/**
	 * Method for Admin User to delete another existing Admin User upon validating with 
	 * the Admin General Password.
	 * @return true if the Admin User is deleted successfully.
	 *         <p>
	 *         false if the Admin USer is not deleted successfully.
	 */
	public boolean deleteAdminUser(){
		String adminGeneralPassword = "moblimaAdminPass", email;
		Scanner sc = new Scanner(System.in);
		/**
		 *  To track number of tries for the admin verification
		 */
		try 
		{
			int count = 1; 
			System.out.println("<Verification of Admin>");
			System.out.println("Key in the admin password: ");
			while (sc.nextLine().equals(adminGeneralPassword) == false && count < 3){
				System.out.println("Wrong admin password!");
				System.out.println("Number of tries left: " + (3-count));
				System.out.println("Key in the admin password: ");
				count++;
			}
			if (count >= 3){
				System.out.println("Maximum number of tries have reached!");
				return false;
			}
			System.out.println("Successful Verification!");
			System.out.println("Key in the email of the User to delete: ");
			email = sc.nextLine();
			if (userManager.deleteAdmin(email) == true)
				return true;
			else 
				return false;
		}catch(NullPointerException e){
			System.out.println("Admin Doe Not Exists!");
		}
		catch (IndexOutOfBoundsException e) {
            System.out.println("Admin Doe Not Exists!");
        }
		return false;
	}
	/**
	 * The methods that allow Admin User to set rates for different age groups, days, type of movies
	 * and the hall class type or modify any of these existing rates.
	 * It also allows Admin User to set a certain day as Public Holiday so a different rate is applied.
	 * A menu will be printed to prompt the Admin User to select which rates to modify.
	 */
	public void modifyRates()
	{
		int option = 0, numOfDays,index, dayType, typeOption;
		boolean classExistence;
		String ageGroup = null; // Variable to store the Age Group to adjust rates
		String movieType = null; // Variable to store the movie Type to adjust rates
		String hallClassType = null; // Variable to store the Hall Class Type to adjust rates
		HashMap <String, Double> movieTypeHash = new HashMap <String, Double>(); // To access the movie types
		List <String> PHDay_List = new ArrayList <String>(); // To store all the list of days for admin to choose for the selection of PH day
		List <String> movieTypeList;// To store all the existing movie types
		List <String> hallClassTypeList;
		Double rate;
		Scanner sc = new Scanner(System.in);
		do 
		{
			System.out.println("------------------------------------------------");
			System.out.println("|                Configure Rates               |");
			System.out.println("------------------------------------------------");
			System.out.println("Enter the Rates to change:");
			System.out.println("(1) Age Group Rate");
			System.out.println("(2) Movie Type Rate");
			System.out.println("(3) Hall Class Type Rate");
			System.out.println("(4) Daily Rates");
			System.out.println("(5) Assign Public Holiday");
			System.out.println("(6) Back");
			System.out.println("Enter your choice:");
			/**
			 *  Handles Exception when User enters string instead of number
			 */
			try
			{
				option = sc.nextInt();
				sc.nextLine();
				switch(option){
					case 1:
					{
						do {
							System.out.println("Enter the AgeGroup to change");
							System.out.println("(1) Child");
							System.out.println("(2) Adult");
							System.out.println("(3) Senior Citizen");
							option = sc.nextInt();
							sc.nextLine();
							if(option ==1)
								ageGroup = "Child";
							else if(option ==2)
								ageGroup = "Adult";
							else if(option ==3)
								ageGroup = "Senior Citizen";
							else{
								System.out.println("Wrong Option");
								System.out.println("Enter the AgeGroup again");
							}
						} while(option <1 |option>3);
						System.out.println("Enter the new rate: ");
						rate = sc.nextDouble();
						sc.nextLine();
						userManager.updateAgeRate(ageGroup, rate);
						System.out.println("Age Group Rates Adjusted!");
						userManager.save();
						break;
					}
					case 2:
					{
						movieTypeList = new ArrayList <String>(); 
						movieTypeHash = movieManager.getMovieTypeRateHash();
						for (String key : movieTypeHash.keySet()){
							movieTypeList.add(key);
						}
						System.out.println("(1): Create a new Movie Type");
						System.out.println("(2): Update an existing Movie Type");
						typeOption = sc.nextInt();
						sc.nextLine();
						switch(typeOption){
						case 1:
							System.out.println("Enter new Movie Type: ");
							movieType = sc.nextLine();
							for (int i = 0; i< movieTypeList.size(); i++){
								if (movieTypeList.get(i).equals(movieType)){
									System.out.println("Movie type has already existed!");
									System.out.println("Enter new Movie Type: ");
									movieType = sc.nextLine();
									break;
									}
							}
							break;
						case 2:
							if (movieTypeList.size() >=1){				
								System.out.println("Enter the number of the Movie Type to change: ");
								for (int i = 0 ; i < movieTypeList.size(); i++ ){
									System.out.println("("+ (i+1) +") " + movieTypeList.get(i));
								}
								movieType = movieTypeList.get(sc.nextInt()-1);
							}
							else {
								System.out.println("No existing movie type!");
								System.out.println("Enter the Movie Type to change: ");
								movieType = sc.nextLine();
							}
							break;
						default:
							break;
						}
						System.out.println("Enter the new rate: ");
						rate = sc.nextDouble();
						sc.nextLine();
						movieManager.updateTypeRate(movieType, rate);
						System.out.println("Movie Type Rates Adjusted!");
						movieManager.save();
						break;
					}
					case 3:
					{
						do {
							hallClassTypeList = new ArrayList <String>();
							System.out.println("Enter the Hall Class Type to change");
							for (String key : hallManager.getClassRateHash().keySet()){
								classExistence = false;
								for (int i = 0 ; i < hallClassTypeList.size(); i++){
									if (hallClassTypeList.get(i).equals(key) == true){
										classExistence = true;
										break;
									}
								}
								if (classExistence == false){
									hallClassTypeList.add(key);
								}
							}
							for (int i = 0; i < hallClassTypeList.size(); i++){
								System.out.println("(" + (i+1) + ") " + hallClassTypeList.get(i));
							}
							option = sc.nextInt();
							sc.nextLine();
							if (option <= hallClassTypeList.size() && option >0){
								hallClassType = hallClassTypeList.get(option-1);
							}
							else {
								System.out.println("Wrong Option");
								System.out.println("Enter the Hall Class Type again");
							}
						} while(option < 1 |option > 3);
						System.out.println("Enter the new rate:");
						rate = sc.nextDouble();
						sc.nextLine();
						hallManager.updateClassRate(hallClassType, rate);
						System.out.println("Hall Class Rates Adjusted!");
						hallManager.save();
						break;
					}
					case 4:
					{
						System.out.println("Enter the Daily Rates to change (weekday/weekend/PHDay):");
						System.out.println("(1) Weekday");
						System.out.println("(2) Weekend");
						System.out.println("(3) PH Day");
						dayType = sc.nextInt();		
						System.out.println("Enter the new rate:");
						rate = sc.nextDouble();
						sc.nextLine();
						switch (dayType){
							case 1:
								showtimeManager.updateDayRate("weekday", rate);
								break;
							case 2:
								showtimeManager.updateDayRate("weekend", rate);
								break;
							case 3:
								showtimeManager.updateDayRate("PH", rate);
								break;
							default:
								break;
						}
						System.out.println("Daily Rates Adjusted!");
						showtimeManager.save();
						break;
					}
					case 5:
					{
						System.out.println("Enter the number of days from today to display for selection of PH day (i.e. 1 represents tomorrow's date): ");
						numOfDays = sc.nextInt();
						while (numOfDays <=0){
							System.out.println("Invalid input!");
							System.out.println("Enter the number of days from today to display for selection of PH day (i.e. 1 represents tomorrow's date): ");
						}
						if(numOfDays >0){
							PHDay_List = (showtimeManager.displayDayOption(numOfDays));
							for (index = 1 ; index <= PHDay_List.size(); index++){
								System.out.println("("+index+") "+ PHDay_List.get(index-1));
							}
						}
					
						System.out.println("Enter the number beside the date for selection of PH day:");
						option = sc.nextInt();	
						while (option <=0 || option > PHDay_List.size()){
							System.out.println("Invalid Input");
							for (index = 1 ; index <= PHDay_List.size(); index++){
								System.out.println("("+index+") "+ PHDay_List.get(index-1));
							}
							System.out.println("Enter the number beside the date for selection of PH day:");
							option = sc.nextInt();	
						}
						showtimeManager.setPHDay(PHDay_List.get(option-1));
						System.out.println(PHDay_List.get(option-1) + " is set to PH day");
						showtimeManager.save();
					}			
					case 6:
						break;
				}
			}catch(InputMismatchException e){
				System.out.println("Invalid input type");
				System.out.println("Enter only the number stated");
				sc.nextLine();
			}
		} while(option != 6);
	}
	/**
	 * Allows Admin Users to add new Halls to any of the Cineplex owned by Moblima.
	 * Admin Users has to input the hall code, hall class type, cineplex to assign the hall to and the capacity.
	 * Only 3 different configuration is allowed for Admin User to choose for the capacity and layout of the hall.
	 * Admin will only input the second portion of hall code as the first hall code will be assigned
	 * accordingly to the cineplex it is assigned to.
	 * <p>
	 * It checks for existing Hall created based on Hall Codes to prevent Halls with the same
	 * Hall Code from created. 
	 * It also checks to ensure that the Hall Code is 3-letters long only.
	 */
	public void createHall()
	{
		int option, capacity = 0, rows = 0, columns =0;
		String hallClass = null, hallCode = null, cineplex = null, hallName = null; // hallName to store the second portion of hallCode
		boolean valid; //Variable to check if hallCode is a String of 3 Letters only
		Scanner sc = new Scanner(System.in);
		System.out.println("<Adding new Hall>");
		System.out.println("Choose the Hall Class: (1)Silver (2)Gold (3)Platinum");
		/**
		 * Handles Exception when users enter sting instead of number
		 */
		try 
		{
			do {
				option = sc.nextInt();
				sc.nextLine();
				if (option == 1)
					hallClass = "Silver";
				else if(option ==2)
					hallClass = "Gold";
				else if(option ==3)
					hallClass ="Platinum";
				else{
					System.out.println("Wrong Choice");
					System.out.println("Enter the option again");
				}
			} while(option <1 | option >3);	
			/**
			 *  Assigning the Hall to one of the Cineplex
			 */
			do {
				System.out.println("Choose the Cineplex the Hall belongs to: (1)Cineplex.AMK (2)Cineplex.SK (3)Cineplex.YCK");
				System.out.println("AMK = Ang Mo Kio, SK = Seng Kang, YCK = Yio Chu Kang");
				option = sc.nextInt();
				sc.nextLine();
				if (option == 1){
					cineplex = "Cineplex.AMK";
					hallCode = "A";
				}
				else if(option ==2){
					cineplex = "Cineplex.SK";
					hallCode = "S";
				}
				else if(option ==3){
					cineplex = "Cineplex.YCK";
					hallCode = "Y";
				}
				else{
					System.out.println("Wrong Choice");
					System.out.println("Enter the option again");
				}
			} while(option < 1 | option > 3);
			/**
			 *  Entering of HallCode
			 */
			do {
				System.out.println("Enter the hallCode (2 Letters Format e.g HG)");
				hallName = "";
				hallName = hallCode+sc.nextLine();
				valid = true;
				/**
				 *  Checking if hallCode contains only 2 Letters
				 */
				char[] chars = hallName.toCharArray();
				for (char c:chars){
					if(!Character.isLetter(c)){
						valid = false;
						break;
					}
				}
			} while( hallName.length()!=3 || valid == false || hallManager.checkHallExistence(hallName)==true); // Check if the length is 3 and is unique
			
			System.out.println("Layout of Hall available");
			System.out.println("(1) Max Capacity: 100, 10 Rows by 10 Columns");
			System.out.println("(2) Max Capacity: 200, 20 Rows by 10 Columns");
			System.out.println("(3) Max Capacity: 250, 10 Rows by 25 Columns");
			System.out.println("Enter choice");
			do {
				option = sc.nextInt();
				sc.nextLine();
				if (option == 1){
					capacity = 100;
					rows = 10;
					columns = 10;
				}
				else if(option == 2){
					capacity = 200;
					rows = 20;
					columns = 10;
				}
				else if(option == 3){
					capacity = 250;
					rows = 10;
					columns = 25;
				}
				else{
					System.out.println("Wrong Choice");
					System.out.println("Enter the option again");
				}
			} while(option < 1 | option > 3);
			hallManager.createHall(hallClass, hallName, cineplex, capacity, rows, columns);
			hallManager.save();
			System.out.println("Hall Successfully Created!");
		}catch(InputMismatchException e){
			System.out.println("Invalid input type");
			System.out.println("Enter only the number stated");
			sc.nextLine();
		}
	}
	/**
	 * Allows Admin Users to delete existing Halls.
	 * It checks for the existence of Hall to be deleted;
	 */
	// Allow deletion of existing CineplexHall
	public void removeHall()
	{
		/**
		 *  Catches NullPointerException if index is invalid
		 */
		try 
		{
			int index =0;
			String hallCode;
			Scanner sc = new Scanner(System.in);
			System.out.println("<Removing of Existing Halls>");
			System.out.println("Select Existing Halls to remove");
			for (index =0; index < hallManager.getHallList().size(); index++){
				System.out.printf("(%d) %s\n",index+1, hallManager.getHallList().get(index).getHallCode());
			}
			System.out.println("Enter your choice");
			index = sc.nextInt()-1;
			hallCode = hallManager.getHallList().get(index).getHallCode();
			if (hallManager.removeHall(hallCode) == true){
				System.out.println("Hall Remove successfully!");
				hallManager.save();
				showtimeManager.save();
			}
			else{
				System.out.println("Hall Not Remove or Hall does not exists!");
			}
		}catch(NullPointerException e){
			System.out.println("Invalid option input during the process");
			System.out.println("Showtime no added");
			System.out.println("Back to Menu...");
		}
	}
	/**
	 * Implements all the different functions for the Admin User ranging from creating/updating/deleting of movie listing,
	 * creating/updating/deleting of showtimes, configuration of system settings and saving all updated data to the database.
	 * A menu will be printed to prompt the Admin User to choose the various functions.
	 * This method will then call the respective sub-methods to perform the various functions.
	 */
	// A methods that implements the Admin Module Interface
	public void adminModule()
	{
		/**
		 *  Handles Exception when User enters string instead of number
		 */
		try {
			int option;
			boolean login = true;
			Scanner sc = new Scanner(System.in);
			do {
				System.out.println("------------------------------------------------");
				System.out.println("|                 Admin Module                 |");
				System.out.println("------------------------------------------------");
				System.out.println("(1) Create Movie Listing");
				System.out.println("(2) Update Movie Listing");
				System.out.println("(3) Delete Movie Listing");
				System.out.println("(4) Create Showtime");
				System.out.println("(5) Update Existing Showtimes");
				System.out.println("(6) Delete Showtime");
				System.out.println("(7) Configure System Settings");
				System.out.println("(8) Save Changes");
				System.out.println("(9) Back");
				System.out.println("Enter your choice:");
				option = sc.nextInt();
				sc.nextLine();
				switch(option){
					case 1:{
						createMovieListing();
						break;
					}
					case 2:{
						updateMovieListing();
						break;
					}
					case 3:{
						deleteMovieListing();
						break;
					}
					case 4: {
						if (hallManager.getHallList().size()==0){
							System.out.println("There is no hall created!");
							System.out.println("Proceeding to create new hall...");
							createHall();
						}
						else{
							createShowtimes();
						}
						break;
					}
					case 5:{
						updateShowtimes();
						break;
					}
					case 6:{
						deleteShowtimes();
						break;
					}
					case 7:{
						configureSystem();
						break;
					}
					case 8:{
						if (movieManager.save() && hallManager.save() == true && showtimeManager.save() == true ){
							System.out.println("Saving to movie.dat...");
							System.out.println("Saving to hall.dat...");
							System.out.println("Saving to showtime.dat...");
						}
						else{
							System.out.println("No data is save!");
							break;
						}
						break;
					}
					case 9:{
						login = false;
						break;
					}
				}
			} while(login == true);
		}catch(InputMismatchException e){
			System.out.println("Invalid input type");
			System.out.println("Enter only the number stated");
		}
		catch (IndexOutOfBoundsException e) {
            System.out.println("Rates not initialised or Invalid input");
        }
	}
}


