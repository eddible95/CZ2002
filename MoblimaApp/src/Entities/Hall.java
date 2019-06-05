package Entities;

import java.io.Serializable;
import java.util.HashMap;
/**
 * Represents a Hall created for each Cineplex.
 * Each Hall can only be assigned to one Cineplex 
 * @author: CZ2002 Team 3
 * @version: Final version
 * @since 2017-11-16
 */
public class Hall implements Serializable
{
	/**
	* The hall class type of the Hall. (E.g. Silver, Gold, Platinum)
	*/
	private String hallClass;
	/**
	* The hall code unique to each Hall. 
	* Hall Code will be a 3-letter code. (E.g. YHG)
	* The first letter represents the Cineplex the hall belongs to.
	*/
	private String hallCode;
	/**
	* The Cineplex the Hall is assigned to.
	*/
	private String cineplex;
	/**
	* The maximum seating capacity of the Hall.
	*/
	private int capacity;
	/**
	* The number of rows of seats of the Hall.
	*/
	private int rows; 
	/**
	* The number of columns of seats of the Hall.
	*/
	private int columns;
	/** 
	* Class Rates will be stored in a HashMap with Hall Class Type as key and the corresponding rates as key-values.
	* HashMap is used to create unique and non-duplicate key-value entry.
	* 3 Different Keys: Silver/Gold/Platinum.
	* {@link HashMap}
	*/
	public HashMap <String, Double> classRate;
	/**
	* Create a new Hall for each cineplex when given hallClass, hallCode, cineplex, capacity, rows, columns.
	* classRate will be initialized with NULL value.
	* @param hallClass This Hall's hall class type.
	* @param hallCode This Hall's unique hall code.
	* @param cineplex This Hall's cineplex it is assigned to.
	* @param hallCode This Hall's hall code.
	* @param capacity This Hall's maximum capacity.
	* @param rows This Hall's rows of seats.
	* @param columns This Hall's columns of seats.
	*/
	public Hall (String hallClass, String hallCode, String cineplex, int capacity, int rows, int columns)
	{	
		this.hallClass = hallClass;
		this.hallCode = hallCode;
		this.capacity = capacity;
		this.rows = rows;
		this.columns = columns;
		this.classRate = new HashMap<String, Double>(); 
		this.cineplex = cineplex;
	}
	/**
	* Changes the hallCode of this Hall.
	* @param hallCode This Hall's hall code.
	*/
	public void updatetHallCode(String hallCode)
	{
		this.hallCode = hallCode;
	}
	/**
	* Changes the cineplex of this Hall.
	* @param cineplex This Hall's cineplex it is assigned to.
	*/
	public void updatetCineplax(String cineplex)
	{
		this.cineplex = cineplex;
	}
	/**
	* Changes the capacity of this Hall.
	* @param capacity This Hall's capacity.
	*/
	public void updateHallCapacity(int capacity)
	{
		this.capacity = capacity;
	}
	/**
	* Changes the number of rows of seats of this Hall.
	* @param rows This Hall's rows of seats.
	*/
	public void updateRows(int rows)
	{
		this.rows = rows;
	}
	/**
	* Changes the number of columns of seats of this Hall.
	* @param columns This Hall's rows of seats.
	*/
	public void updateColumns(int columns)
	{
		this.columns = columns;
	}
	/**
	* Changes the hall class rates of different hall class types (Eg. Silver, Gold, Platinum).
	* A HashMap is used, so that it creates a unique entry for each hall class rate
	* @param hallClass Represents hall class to update the rate.
	* @param rate Represents the new rate to be updated
	*/
	public void updateClassRate(String hallClass, Double rate)
	{
		this.classRate.put(hallClass, rate);
	}
	
	/**
	* Changes the class type of this Hall.
	* @param hallClass This Hall's class type.
	*/
	public void updateHallType(String hallClass)
	{
		this.hallClass = hallClass;
	}
	/**
	* Gets the hall class of this Hall.
	* @return hallClass Hall's hall class.
	*/
	public String getHallClass()
	{
		return hallClass;
	}
	/**
	* Gets the hall code of this Hall.
	* @return hallCode Hall's hall code.
	*/
	public String getHallCode()
	{
		return hallCode;
	}
	/**
	* Gets the cineplex this Hall belongs to.
	* @return cineplex Hall's cineplex it belongs to.
	*/
	public String getCineplex()
	{
		return cineplex;
	}
	/**
	* Gets the seating capacity of this Hall.
	* @return capacity Hall's seating capacity.
	*/
	public int getHallCapacity()
	{
		return capacity;
	}
	/**
	* Gets the number of rows of seat of this Hall.
	* @return rows Hall's total rows of seats.
	*/
	public int getRows()
	{
		return rows;
	}
	/**
	* Gets the number of columns of seat of this Hall.
	* @return columns Hall's total columns of seats.
	*/
	public int getColumns()
	{
		return columns;
	}
	/**
	* Gets the hall class rates charged for this Hall.
	* @param hallClass This Hall's hall class type.
	* @return classRate Hall's class rate charged.
	*/
	public double getClassRate(String hallClass)
	{ 
		return classRate.get(hallClass);
	}
	/**
	* Gets the HashMap of the Hall Class Rate.
	* Each Hall Class Rate is the same regardless of individual Halls.
	* @return this Hall's HashMap classRate. 
	* {@link HashMap}
	*/
	public HashMap <String, Double> getClassRateHash()
	{
		return classRate;
	}
}
