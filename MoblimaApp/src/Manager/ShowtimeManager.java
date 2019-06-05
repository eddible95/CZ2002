package Manager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import Entities.Showtime;
/**
 * Represents the ShowtimeManager of MoblimaApp that handles the creating, updating, removing of showtimes.
 * @author: CZ2002 Team 3
 * @version: Final version
 * @since 2017-11-16
 */
public class ShowtimeManager extends DataManager 
{

	private static ShowtimeManager instance = null;
	public static final String SHOWTIME_PATH = "showtime.dat";
	private List<Showtime> showtimeList; //each movie will have a list of showtime
	private List<String> hallShowtimeList; //to store a list of showtimes of the same hall code
	private HashMap <String, String> hallShowtimeHash; //to store a hasmap of all the showtime and showday of the same hall code
	private SimpleDateFormat dateFormat;
	private SimpleDateFormat timeFormat; 
	private Calendar currentDate;
	private int year;
	private int month;
	private int day;
	private int minute;
	private int hour;
	private int second;
	
	/**
	 * This method to ensure that only one instance of showtimeManager is created so that if
	   no such Object is created yet, it will create a new showtimeManager object else
	   it will return the existing created showtimeManager object. 
	 * @return instance The instance of showtimeManager that is already created.
	 */
	
	public static ShowtimeManager getInstance() 
	{	
		if(instance == null)
		{
			instance = new ShowtimeManager();
			return instance;
		}
		return instance;
	}
	
	/**
	 * This constructor assigns the showtimeList with the data from showtime.dat.
	 * It also sets the current time,and date and time format.
	 */
	// this constructor assigns the showtimeList with the data from showtime.dat
	// it also sets the date and time format and the current time
	private ShowtimeManager() 
	{
		super(SHOWTIME_PATH);
		if (read(SHOWTIME_PATH) instanceof List <?>){
			showtimeList = ((List<Showtime>) this.read(SHOWTIME_PATH));
		}
		if (showtimeList == null){
			showtimeList = new ArrayList<Showtime>();
		}
		Calendar cal = Calendar.getInstance();
		// the current time
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);      // month from 0 to 11
		day = cal.get(Calendar.DAY_OF_MONTH);
		hour = cal.get(Calendar.HOUR_OF_DAY);
		minute = cal.get(Calendar.MINUTE);
		second = cal.get(Calendar.SECOND);
	    currentDate = new GregorianCalendar(year, month, day, hour, minute, second); //get today's date and current time
		dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy"); //eg: (Thursday, November 9, 2017)
		timeFormat = new SimpleDateFormat("HH:mm"); // eg: 00:30 
	}
	
	/**
	 * This method is to get the particular showtime object to access the showtime data for setting of layout.
	 * @param title The inputed title to identify the showtimes available.
	 * @param showtime The inputed showtime to narrow the showtimes available to the inputed showtime.
	 * @param day The inputed day to narrow all the showtimes of the day to only the showtimes available on the inputed ady.
	 * @return Showtime Showtime object that is identified by title, showtime and day.
	 */
	// to get the particular showtime object to access the showtime data for setting of layout
	// the showtime object is identified by its title, showtime and day
	public Showtime getShowtimeObject(String title, String showtime, String day) 
	{
		for (int i = 0 ; i <showtimeList.size(); i++)
		{
			if (showtimeList.get(i).getTitle().equals (title)) // if the particular showtime object has the selected title
			{
				if (showtimeList.get(i).getShowtime().equals (showtime) && showtimeList.get(i).getDay().equals(day)) // if the particular showtime object has the selected showtime and showday
				{
					return showtimeList.get(i); // return the showtime object that the user has chosen based on movie and showtime and showday
				}
			}
		}
		return null;
	}
	/**
	 * This method gets the list of all showtime objects of the inputed title.
	 * @param title The inputed title to view the movie showtimes.
	 * @return tempMovieShowtime The list of showtime objects based on the inputed title.
	 */
	public List<Showtime> getMovieShowtimeList(String title)
	{
		List <Showtime> tempMovieShowtime = new ArrayList <Showtime>();
		for (int i = 0 ; i <showtimeList.size(); i++)
		{
			if (showtimeList.get(i).getTitle().equals (title)) // if the particular showtime object has the selected title
			{
				tempMovieShowtime.add(showtimeList.get(i));
			}
		}
		return tempMovieShowtime;
	}

	/**
	 * This method gets a whole list of showtime objects.
	 * @return showtimeList The list of all the showtime objects.
	 */
	public List<Showtime> getShowtimeList ()
	{
		return showtimeList;
	}

	/**
	 * This method gets a whole list of showtime objects based on the selected day for a particular movie.
	 * @param title The title of the movie to get its showtimes.
	 * @param day The selected showday.
	 * @return tempList List of all the showtime objects based on the day and title.
	 */
	// to get all the showtimes of the selected day for a particular movie
	public List<Showtime> getMovieShowtimes(String title, String day)
	{
		List<Showtime> tempList = new ArrayList<Showtime>();
	
		for (int i = 0 ; i < this.showtimeList.size(); i++)
		{
			if (showtimeList.get(i).getTitle().equals (title)) // if the particular showtime object has the selected title
			{
				if (showtimeList.get(i).getDay().equals (day)) // if the particular showtime object has the selected title and day
				{
					tempList.add(showtimeList.get(i)); // adds to the list of all the showtime based on the movie title and day
				}
			}
		}
		return tempList;
	}
	/**
	 * This method returns the showtime object by index of the showtimeList.
	 * @param index The index of the showtimeList.
	 * @return Showtime showtime object based on the index inputed.
	 */
	public Showtime getShowtimeObjectByIndex(int index){
		return showtimeList.get(index);
	}

	/**
	 * This method returns a list of all showtimes based on hallCode.
	 * @param hallCode The unique hallCode to identify the hall.
	 * @param day The day of the show time selected.
	 * @return hallShowtimeList The hallShowtime list of a particular hall based on hallCode.
	 */
	// to store all the showtimes with the same hallCode to return showtimes of the same hall
	public List <String> getHallShowtimeList(String hallCode, String day)
	{
		if (hallShowtimeList == null){
			hallShowtimeList = new ArrayList <String>();
		}
		for (int i = 0; i < showtimeList.size(); i++){
			if (showtimeList.get(i).getHallCode().equals(hallCode)){
				if (showtimeList.get(i).getDay().equals(day)){
					hallShowtimeList.add(showtimeList.get(i).getShowtime());
				}
			}
		}
		return hallShowtimeList;
	}
	/**
	 * This method returns a hash map of an inputed title with all the halls and their corresponding showtimes which are in a list.
	 * @param hallCode The unique hallCode to identify the hall.
	 * @param title The title to search for the showtimes of this movie.
	 * @return dayOfShowtimes Hash map of the different days 
	 */
	public HashMap <String, List <String>> getHallShowtimeHash (String hallCode, String title){
		HashMap <String, List <String>> dayOfShowtimes = new HashMap <String, List <String>>();
		List <String> dayShowtimesList = new ArrayList <String>(); // [day1, showtime, day1, showtime, day2, showtime...]
		List <String> showtimesOfDay = new ArrayList <String>();
		List <String> daysList = new ArrayList <String>();
		boolean sameDay = false;
		if (hallShowtimeHash == null){
			hallShowtimeHash = new HashMap <String, String>();
		}
		// This for loop ensures the showtime list of a particular title do not have duplicated time or day. 
		for (int i = 0; i < showtimeList.size(); i++){
			// First, find all the showtimes of the same title
			if (showtimeList.get(i).getTitle().equals(title)){
				// Next, filter them by the same hallCode
				if (showtimeList.get(i).getHallCode().equals(hallCode)){
					for (int j = 0; j < daysList.size(); j++){
						sameDay = false;
						// sets sameDay = true so as to skip the addition of this day
						if (showtimeList.get(i).getDay().equals(daysList.get(j)) == true){
							sameDay = true;
						}
					}
					if (sameDay == false){
						daysList.add(showtimeList.get(i).getDay());
					}
					dayShowtimesList.add(showtimeList.get(i).getDay());
					dayShowtimesList.add(showtimeList.get(i).getShowtime());
				}
			}
		}
		for (int i = 0; i < daysList.size(); i++){
			showtimesOfDay = new ArrayList <String>();
			for (int j = 0; j < dayShowtimesList.size(); j = j+2){
				if (dayShowtimesList.get(j).equals(daysList.get(i))){
					showtimesOfDay.add(dayShowtimesList.get(j+1));
				}
			}
			dayOfShowtimes.put(daysList.get(i), showtimesOfDay);
		}
		return dayOfShowtimes;
	}
	
	/**
	 * This method allows the input of the number of days to choose from for making amendments to the particular day's showtime.
	 * This method also allows the setting of dates when assigning a public holiday.
	 * @param numOfDays The number of days to display, starting from tomorrow's date.
	 * @return dayList The list of the days consisting of tomorrow's date to the date which is specified by numOfDays.
	 */
	public List<String>  displayDayOption (int numOfDays)
	{ 
		List<String> dayList = new ArrayList<String>(); // a list to store today's and next (numOfDays)'s dates
		Calendar calTemp;
	    calTemp = (Calendar) currentDate.clone();
		for (int i = 0 ; i < numOfDays; i++){
			calTemp.add(Calendar.DAY_OF_MONTH, 1);
			dayList.add(dateFormat.format(calTemp.getTime())); // to store the selected dates in the String Array, dayList
		}
		return dayList; // returns a list of the selected number of dates (including today's) to be displayed
	}
	
	/**
	 * This methods allow choosing the time slots of the selected day for making amendments to showtime.
	 * @param minInterval The minute interval to display a whole range of time with the inputed interval.
	 * @param day The number of days from today's date to identify the selected day.
	 * @return timeList List of time slots to be displayed for the selected day.
	 */
	
	public List<String> displayTimeOption (int minInterval, int day)
	{ 
		// to allow the admin to choose the time Interval to select for making amendments to showtime of the selected day
		List<String> timeList = new ArrayList<String>();
		Calendar selectedDate = (Calendar) currentDate.clone();	// clone so as to make changes to the current date
		selectedDate.add(Calendar.DAY_OF_MONTH, day); // get the day that user selects, i.e. number of "days" after current date
		Calendar end = Calendar.getInstance();
		end = (Calendar) selectedDate.clone();			
		end.add(Calendar.DAY_OF_MONTH,1); // needed to terminate for loop, i.e. will continue to add the minute interval until the next day (00:00)
		end.set(Calendar.HOUR_OF_DAY, 00);
		end.set(Calendar.MINUTE, 00);
		selectedDate.set(Calendar.HOUR_OF_DAY, 11); // assumes that Moblima only starts operating at 11am
		selectedDate.set(Calendar.MINUTE, 00);
		for (selectedDate.getTime(); selectedDate.before(end); selectedDate.add(Calendar.MINUTE, minInterval)) {
			timeList.add(timeFormat.format(selectedDate.getTime())); // append the timeList with the different time slots, increasing by the time interval
		}
		return timeList; 
	}
	
	/**
	 * This method returns a list of all assigned public holiday.
	 * @return List of all the public holidays.
	 */
	public List <String> getPHList()
	{
		return showtimeList.get(0).getPHList();
	}
	/**
	 * This method returns all the day types (i.e weekday, weekend, PHDays) and their corresponding rates.
	 * @return HashMap The hashmap of all the day types and their rates.
	 */
	public HashMap <String, Double> getDayRateHash()
	{
		return showtimeList.get(0).getDayRateHash();
	}
	
	/**
	 * This method allows the adding of public holiday.
	 * @param toBePH The selected day to be assigned as a public holiday.
	 */
	public void setPHDay (String toBePH)
	{
		for (int i = 0 ; i <showtimeList.size(); i++){
			showtimeList.get(i).updatePH(toBePH);;
		}
	}

	/**
	 * This method allows the adding of a new showtime of a particular movie with the selected showtime.
	 * @param title The movie title to add its showtime for screening.
	 * @param showtime The inputed showtime to set to the movie for screening.
	 * @param day The inputed showday to set to the movie for screening.
	 * @param hallCode The unique hallCode to identify which hall to screen this movie.
	 * @param maxCapacity The maximum capacity of the hall.
	 * @return Showtime The showtime object that has been created based on the inputs.
	 */
	// to allow the admin to add a new showtime of a particular movie with selected showtime
	public Showtime addShowtime (String title, String showtime, String day, String hallCode, int maxCapacity)
	{
	/*	Date time_date = timeFormat.parse(showtime); //to convert "showtime" of string type to Date type
		Calendar time_cal = Calendar.getInstance();
		time_cal.setTime(time_date); // to create a Calendar object that sets the time that of "time_date"
		Date day_date = dateFormat.parse(day); //to convert "day" of string type to Date type
		Calendar day_cal = Calendar.getInstance();
		day_cal.setTime(day_date); // to create a Calendar object that sets the date that of "day_date"
		*/
		showtimeList.add(new Showtime(title, showtime, day, hallCode, maxCapacity));
		return showtimeList.get(showtimeList.size()-1); //return the showtime object so that changes can be made to this showtime via other methods in this class
	}

	/**
	 * This method allows the removal of a particular showtime object.
	 * @param showtime The showtime object to be removed.
	 * @return true if removal is successful, else false.
	 */
	public boolean removeShowtime(Showtime showtime)
	{
		return showtimeList.remove(showtime);
	}
	
	/**
	 * This method allows the removal of showtime object based on title.
	 * @param title The title to remove its showtime.
	 */
	public void removeShowtimeByTitle(String title){
		for (int i = 0; i < showtimeList.size(); i++){
			if (showtimeList.get(i).getTitle().equals(title)){
				removeShowtime(showtimeList.get(i));
				i--;//To move the index back as the size changes
			}
		}
	}
	/**
	 * This method updates the title of a movie.
	 * @param newInformation The new title to update to.
	 * @param title The existing title to be changed.
	 */
	public void updateTitle(String newInformation, String title)
	{
		for (int i = 0; i < showtimeList.size(); i++){
			if (showtimeList.get(i).getTitle().equals(title)){
				showtimeList.get(i).updateTitle(newInformation);
			}
		}
	}
	/**
	 * This method updates the showtime of a movie.
	 * @param indexToUpdate The index of the showtimeList to identify the showtime object for updating.
	 * @param showtime The new showtime to update to.
	 */
    public void updateShowtime(int indexToUpdate, String showtime)
    {
    	showtimeList.get(indexToUpdate).updateShowtime(showtime);
	}
    /**
	 * This method updates the day of a movie.
	 * @param indexToUpdate The index of the showtimeList to identify the day object for updating.
	 * @param day The new day to update to.
	 */
    public void updateDay(int indexToUpdate, String day)
    {
    	showtimeList.get(indexToUpdate).updateDay(day);
    }
    /**
	 * This method updates the hallCode of a movie.
	 * @param indexToUpdate The index of the showtimeList to identify the hallCode object for updating.
	 * @param hallCode The new hallCode to update to.
	 */
	public void updateHallCode(int indexToUpdate, String hallCode)
	{
		showtimeList.get(indexToUpdate).updateHallCode(hallCode);
	}
    /**
	 * This method updates the maxCapacity of a movie.
	 * @param indexToUpdate The index of the showtimeList to identify the maxCapacity object for updating.
	 * @param maxCapacity The new maxCapacity to update to.
	 */
	public void updateMaxCapacity(int indexToUpdate, int maxCapacity)
	{
		showtimeList.get(indexToUpdate).updateMaxCapacity(maxCapacity);
	}
	/**
	 * This method updates the layout of a particular showtime after a selection of seat has been made.
	 * @param showtimeObj The showtime object to be changed.
	 * @param maxColumn The maximum number of columns.
	 * @param userRow The selected row to book.
	 * @param userColumn The selected column to book.
	 */
	// Whenever a seat is booked, this method is called to update it from 0 to 1
	public void updateLayout(Showtime showtimeObj, int maxColumn, int userRow, int userColumn)
	{
		int indexToUpdate = 0,index,i=0;
		if (userRow >1){
			userRow = userRow-1;
			index = userRow*maxColumn+userColumn-1;
		}
		else{
			index = userColumn-1;
		}
		for (i = 0; i<showtimeList.size(); i++){
			if (showtimeList.get(i).equals(showtimeObj)){
				indexToUpdate = i;
				break;
			}
		}
		showtimeList.get(indexToUpdate).updateLayout(index);
	}
	
	/**
	 * This method updates the day type and its corresponding rate.
	 * @param dayType The day type to be updated.
	 * @param rate The rate to be updated.
	 */
	public void updateDayRate(String dayType, double rate)
	{
		for (int i = 0 ; i <showtimeList.size(); i++){
			showtimeList.get(i).updateDayRate(dayType, rate);
		}
	}
	/**
	 * This method saves the updated showtimeList to the file path.
	 * @return true if saving is successful, else false.
	 */
	public boolean save()
	{
		return this.write(showtimeList, SHOWTIME_PATH); 
	}
}
