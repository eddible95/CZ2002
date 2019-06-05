package Manager;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Calendar;
import Entities.Showtime;
import Entities.User;
/**
 * Represents the BookingManager of MoblimaApp that handles the process of booking tickets.
 * @author: CZ2002 Team 3
 * @version: Final version
 * @since 2017-11-16
 */
public class BookingManager
{
	/**
	 * Ensure that throughout the program, only one instance of BookingManager will be created.
	 */
	private static BookingManager instance = null;
	/**
	* The instance of userManager to allow BookingManger to have access to User entity through the userManager.
	*  {@link UserManager}
	*/
	private UserManager userManager;
	/**
	* The instance of showtimeManager to allow BookingManger to have access to Showtime entity through the showtimeManager.
	* {@link ShowtimeManager}
	*/
	private ShowtimeManager showtimeManager;
	/**
	* The instance of movieManager to allow BookingManger to have access to Movie entity through the movieManager.
	* {@link MovieManager}
	*/
	private MovieManager movieManager;
	/**
	* The instance of hallManager to allow BookingManger to have access to Hall entity through the hallManager.
	* {@link HallManager}
	*/
	private HallManager hallManager;
	/**
	 * The format of how the date will be displayed.
	 */
	private SimpleDateFormat dateFormat;
	/**
	 * To create one instance of the BookingManager if it does not exists else it will return
	 * the instance of BookingManager that already exists.
	 * @return instance The instance of BookingManager that is already created.
	 */
	public static BookingManager getInstance()
	{
		if (instance == null){
			instance = new BookingManager();
			return instance;
		}
		return instance;
	}
	/**
	* It will instantiate and get the instance of each of the various Managers. 
	* The dateFormat is initialized to year followed by month, date, hour in 24-hour format, and minute. (E.g. 201711162259 signifies 2017, November 16, 22:59).
	*/
	public BookingManager() 
	{
		//so as to make reference to the user data
		userManager = UserManager.getInstance(); 
		showtimeManager = ShowtimeManager.getInstance();
		hallManager = HallManager.getInstance();
		movieManager = MovieManager.getInstance();
		dateFormat =  new SimpleDateFormat("yyyyMMddHHmm"); 
	} 
	/**
	 * This method will check if the user's inputed seat choice is valid. (i.e. the choice is within the range of the layout and is not occupied).
	 * @param showtime The selected showtime object.
	 * @param hallCode The code of the selected hall that screens the movie.
	 * @param userRow The selected row number.
	 * @param userColumn The selected column number.
	 * @return true if it is valid, else false.
	 */
	// checks if the user seat choice is valid (i.e., in the layout and not occupied)
	public boolean validateSeatChoice(Showtime showtime, String hallCode, int userRow, int userColumn)
	{
		int arrayIndex;
		if (userRow > 1) {
			userRow = userRow-1;
			arrayIndex = userRow * hallManager.getHallObject(hallCode).getColumns()+ userColumn -1;
		} 
		else
			arrayIndex = userColumn-1;
		// if user choice of seat position is not in the layout, return false
		if (arrayIndex - 1 > showtime.getLayout().length) {
			return false;
		}
		// if user choice of seat position is occupied, return false
		else if (showtime.getLayout()[arrayIndex] == 1) {
			return false;
		}
		// user choice is neither occupied nor non-existent, return true
		else {
			return true;
		}
	}
	/**
	 * This method generates the ticket price that is based on the type of movie, class of cinema, age of customer, day of the week or public holiday.
	 * @param email The current customer's email.
	 * @param title The selected title.
	 * @param hallClass The hallClass that is tied to the showtime object selected.
	 * @param movieType The selected type of movie.
	 * @param day The selected movie showday.
	 * @return price Price of the ticket.
	 */
	public double generatePrice (String email, String title, String hallClass, String movieType, String day)
	{
		User user;
		// to determine the day rate, default: weekday since higher probability
		String dayType = "weekday"; 
		HashMap <String, Double> dayRateHash = new HashMap <String, Double>();
		double price;
		// set a fixed String Array for weekend 
		String [] weekend =  {"Saturday", "Sunday"}; 	
		// to get the user object based on the email
		user = userManager.getUserObject(email);
		// to get the movie rate based on the type of the movie chosen
		double movieRate =  movieManager.getMovieTypeRateHash().get(movieType);
		// to get the class rate based on the class of the movie
		double classRate = hallManager.getClassRateHash().get(hallClass);
		// to get the user rate based on the age group of the user
		double userRate = userManager.getUserAgeRateHash().get(user.getAgeGroup());
		// to get the hash map of the type of day and day rate
		dayRateHash= showtimeManager.getDayRateHash();
		double dayRate;
		// since day is in the format like "Sunday, November 12, 2017", we just want the day thus split with ","
		String []dayOnly = day.split(","); 
		
		// check if the selected day is public holiday
		for (int i = 0; i < showtimeManager.getPHList().size(); i++){
			if (day.equals(showtimeManager.getPHList().get(i))){
				dayType = "PH";
				break;
			}
		}
	
		// only check for weekend if the selected day is not declared as PH
		if (dayType.equals("PH") == false){
			for (int i = 0 ; i < 2; i++){
				if (weekend[i].equals(dayOnly[0])){
					dayType = "weekend";
					break;
				}
			}
		}
		// to get the rate of the day type
		dayRate = dayRateHash.get(dayType);
		// ticket price is the aggregation of all the 4 type rates
		price = movieRate + classRate +userRate + dayRate;
		return price;
	}
	/**
	 * This method is to generate the booking history which depends on the hall code and the current date from the instance of Calendar class.
	 * Transaction ID needed is also generated in the form of hallCode concatenated with the current date. (E.g. AHS201711162259).
	 * @param email The current customer's email.
	 * @param showtime The selected movie showtime object.
	 * @param price Price of the ticket.
	 * @return newBookingHistory Booking History which includes the transaction ID
	 */
	public String generateTransaction (String email, Showtime showtime, Double price)
	{
		Calendar currentDate;
	    Calendar cal = Calendar.getInstance();
	    // to get the current date
		currentDate = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
		String transactionID; 
		// transactionID has the format: XXXYYYYMMDDhhmm, where Y: year, M : month, D: day, h: hour, m : minutes, XXX: cinema code in letters, eg: MHG201711101520 
		// transaction date will be the date the customer makes his payment on his booking
		transactionID = showtime.getHallCode() + dateFormat.format(currentDate.getTime());
		//newBookingHistory contains TID, Title, Showtime, Price
		String newBookingHistory = "TransactionID: " + transactionID + " " + "|" + " Title: " + showtime.getTitle() + " |" + " Showtime: " + showtime.getDay() +", "+ showtime.getShowtime()+ " |" + " Price: S$ " + price + " (inclusive of 7% GST)" + "\n"  ;
		userManager.writeBookingHistory(email, newBookingHistory);
		return newBookingHistory;
	}
	/**
	 * This method calls the save method in userManager to save all the updates made.
	 * @return true if saving is successful, else false.
	 */
	public boolean save() 
	{
		// only need to save the bookingHistory changes in userManager
		return userManager.save(); 
	}
}
