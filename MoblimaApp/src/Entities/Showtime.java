package Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * Represents a showtime for each Movie. 
 * @author: CZ2002 Team 3
 * @version: Final version
 * @since 2017-11-16
 */
public class Showtime implements Serializable 
{
	/**
	* The title of the movie that is screened for each showtime.
	*/
	private String title;
	/**
	* The time of the movie the movie is screened for each showtime.
	*/
	private String showtime;
	/**
	* The day of the movie the movie is screened for each showtime.
	*/
	private String day;
	/**
	* The hallCode of the hall the movie is screened at for each showtime.
	*/
	private String hallCode;
	/**
	* The capacity of the hall the movie is screened at for each showtime.
	*/
	private int maxCapacity;
	/**
	* Tracks the current layout of each show time to ensure no double booking can occur.
	*/
	private int layout[];
	/** 
	* Day Rates will be stored in a HashMap with day type as key and the corresponding rates as key-values.
	* HashMap is used to create unique and non-duplicate key-value entry.
	* 3 Different Keys: weekday/weekend/PH (i.e. public holiday).
	* {@link HashMap}
	*/
	public HashMap <String, Double> dayRate;
	/** 
	* A List of String objects containing the dates defined as public holiday.
	* {@link List}
	*/
	private List <String> PHDays; 
	/**
	* Create a new Showtime listing for each movie when given title, showtime, day, hallCode, maxCapacity.
	* BookingHistory and userAgeRate will be initialized with NULL value.
	* dayRate and PHDays will be initialized with a NULL value
	* @param title This Showtime's title.
	* @param showtime This Showtime's show timing.
	* @param day This Showtime's day.
	* @param hallCode This Showtime's hall code.
	* @param maxCapacity This Showtime's max capacity.
	*/
	public Showtime(String title, String showtime, String day, String hallCode, int maxCapacity)
	{
		this.title = title;		
		this.showtime = showtime;
		this.day = day;
		this.hallCode = hallCode;
		this.maxCapacity = maxCapacity;
		layout = new int[maxCapacity];
		for (int i = 0; i<maxCapacity; i++){
			layout[i] = 0;
		}
		this.dayRate = new HashMap<String, Double>(); 
		this.PHDays = new ArrayList <String> ();
	}
	/**
	* Changes the showtime of this Showtime.
	* @param showtime This Showtime's show timing.
	*/
	public void updateShowtime(String showtime) 
	{
		this.showtime = showtime;
	}
	/**
	* Changes the day of this Showtime.
	* @param day This Showtime's day.
	*/
	public void updateDay(String day)
	{
		this.day = day;
	}
	/**
	* Changes the title of this Showtime.
	* @param title This Showtime's title.
	*/
	public void updateTitle(String title) 
	{
		this.title = title;
	}
	/**
	* Changes the hallCode of this Showtime.
	* @param hallCode This Showtime's hall code.
	*/
	public void updateHallCode(String hallCode) 
	{
		this.hallCode = hallCode;
	}
	/**
	* Changes the maxCapacity of this Showtime.
	* @param maxCapacity This Showtime's maximum capacity.
	*/
	public void updateMaxCapacity(int maxCapacity) 
	{
		this.maxCapacity = maxCapacity;
	}
	/**
	* Changes the layout of this Showtime.
	* Sets the particular seat to occupied which is "1" based on the row and column.
	* @param index The index which represent index of the seat in Array.
	*/
	public void updateLayout(int index) 
	{
		layout[index] = 1;
	}
	/**
	* Changes the day rates of different day types (Eg. Weekday, Weekend, Public Holiday).
	* A HashMap is used, so that it creates a unique entry for each day rate
	* @param dayType Represents weekday, weekend or public to update the rate.
	* @param rate Represents the new rate to be updated
	*/
	public void updateDayRate(String dayType, Double rate)
	{
		this.dayRate.put(dayType, rate);
	}
	/**
	* Allows the Admin to set the days to be labeled as public holiday.
	* @param toBePH Represents the date to be changed as public holiday and added to the List of Strings PHDAYs.
	*               Date format in string type, eg: Sunday, November 12, 2017
	*/
	public void updatePH (String toBePH)
	{ 
		this.PHDays.add(toBePH); 
	}
	/**
	* Gets the show time of this Showtime.
	* @return this Showtime's showtime.
	*/
	public String getShowtime () 
	{
		return showtime;
	}
	/**
	* Gets the show day of this Showtime.
	* @return this Showtime's day.
	*/
	public String getDay ()
	{
		return day;
	}
	/**
	* Gets the title of this Showtime.
	* @return this Showtime's title.
	*/
	public String getTitle() 
	{
		return title;
	}
	/**
	* Gets the hall code of this Showtime.
	* @return this Showtime's hallCode.
	*/
	public String getHallCode() 
	{
		return hallCode;
	}
	/**
	* Gets the max capacity of this Showtime.
	* @return this Showtime's maxCapacity.
	*/
	public int getMaxCapacity() 
	{
		return maxCapacity;
	}
	/**
	* Gets the current capacity of the hall of each Showtime so that.
	* @return this Showtime's layout.
	*/
	public int[] getLayout() 
	{
		return layout;
	}
	/**
	* Gets the Ticket Rate for different show day depending on whether is it weekday, weekend or public holiday
	* @param dayType Represents weekday, weekend or public to update the rate.
	* @return this Showtime's dayRate.
	*/
	public Double getDayRate(String dayType)
	{ 
		return dayRate.get(dayType);
	}
	/**
	* Gets the HashMap of the Day Type Rate.
	* Each Day Type Rate is the same regardless of individual Showtimes.
	* @return this Showtime's HashMap dayRate. 
	*/
	public HashMap <String, Double> getDayRateHash()
	{
		return dayRate;
	}
	/**
	* Gets the List of String objects containing days set as public holiday.
	* @return this Showtime's List of String Objects PHDays. 
	*/
	public List <String> getPHList()
	{
		return PHDays;
	}
}

