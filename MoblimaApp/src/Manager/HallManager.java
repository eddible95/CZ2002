package Manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import Entities.Hall;
/**
 * Represents the HallManager of MoblimaApp that handles the creating, updating and removing of halls.
 * @author: CZ2002 Team 3
 * @version: Final version
 * @since 2017-11-16
 */
public class HallManager extends DataManager 
{
	// Ensure that throughout the program, only one instance of HallManager will be created
	private static HallManager instance = null;
	// The filename that Hall Objects will be written to and stored.
	public static final String HALL_PATH = "hall.dat";
	// Temporary List containing all the Hall objects during Application runtime
	private List<Hall> hallList = null;
	
	/**
	 * This method to ensure that only one instance of HallManager is created so that if
	   no such Object is created yet, it will create a new HallManager object else
	   it will return the existing created HallManager object. 
	 * @return instance The instance of HallManager that is already created.
	 */
	public static HallManager getInstance() 
	{	
		if(instance == null){
			instance = new HallManager();
		}
		return instance;
	}	
	/**
	 * This constructor using the Super Class constructor which will be called by the getInstance() method if no such instance of HallManager is created yet.
	 */
	private HallManager()
	{	
		// Using the Super Class Constructor to create the database and file to store the 
		// object
		super(HALL_PATH);
		// Load the list of Hall Objects if there is an existing List
		if( read(HALL_PATH) instanceof List<?>){
			hallList = ((List<Hall>) this.read(HALL_PATH));
		}
		// If list is empty, a new list of Hall Objects will be created and updated whenever
		// the program is running
		if (hallList == null){
			hallList = new ArrayList<Hall>();
		}
	}
	
	/**
	 * This method is to check if the hall based on the inputed hhallCode is already in the hall list before creating a new hall
	 * @param hallCode The inputed hallCode for checking of existence of hall since hallCode is unique to each hall.
	 * @return true if the hall exists in the hall list already, else false.
	 */
	public boolean checkHallExistence(String hallCode)
	{
		for (int i = 0 ; i < hallList.size(); i++){
			if (hallList.get(i).getHallCode().equals(hallCode)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method creates a new hall based on the inputed configuration of a hall (i.e. hallClass, hallCode, cineplex, capacity, rows, and columns).
	 * @param hallClass The selected hallClass (e.g. Silver).
	 * @param hallCode The unique inputed hallCode (e.g. AMG) in which the first letter allows us to identify which cineplex (e.g. 'A' represents cineplex.AMK which is at Ang Mo Kio).
	 * @param cineplex The selected cineplex to add the new hall to.
	 * @param capacity The selected capacity for the hall layout.
	 * @param rows The number of rows for the hall layout based on the selected capacity.
	 * @param columns The number of columns for the hall layout based on the selected capacity.
	 */
	public void createHall(String hallClass, String hallCode, String cineplex, int capacity, int rows, int columns)
	{
		hallList.add(new Hall(hallClass, hallCode, cineplex, capacity, rows, columns));
		updateClassRate(hallClass, 0.0);
	}
	
	/** 
	 * This method is to modify the capacity of the hall layout by taking in the hallCode which identifies the hall and the inputed capacity value.
	 * @param hallCode The unique inputed hallCode to identify the hall to modify.
	 * @param capacity The inputed capacity to set the number of seats.
	 * @return true if updating is successful, else false.
	 */
	public boolean updateCapacity(String hallCode, int capacity)
	{
		int i = 0;
		while ( i < hallList.size()){
			Hall hall = hallList.get(i);
			if (hall.getHallCode().equals(hallCode)){
				hall.updateHallCapacity(capacity);
				return true;// If a match is found, return true
			}		
			else 
				i++;//Traverse to the next Hall object in the List
		}
		return false;// If the Hall does not exists in the List of Halls
	}
	
	/**
	 * This method is to modify the layout of the hall based on the hallCode, rows and columns
	 * @param hallCode The unique inputed hallCode to identify the hall to modify.
	 * @param rows The inputed number of rows.
	 * @param columns The inputed number of columns.
	 * @return true if updating is successful, else false.
	 */
	public boolean updateLayout(String hallCode, int rows, int columns)
	{
		int i = 0;
		Hall hall = hallList.get(i);
		while ( i < hallList.size()){
			if (hall.getHallCode().equals(hallCode)){
				hall.updateRows(rows);
				hall.updateColumns(columns);
				return true;// If a match is found, return true
			}		
			else {
				i++;//Traverse to the next Hall object in the List
				hall = hallList.get(i);
			}
		}
		return false;// If the Hall does not exists in the List of Halls
	}
	/**
	 * This method is to modify the class type based on hallCode and hallClass.
	 * @param hallCode The unique inputed hallCode to identify the hall to modify.
	 * @param hallClass The inputed hallClass.
	 * @return true if updating is successful, else false.
	 */
	public boolean updateClassType(String hallCode, String hallClass)
	{
		int i = 0;
		Hall hall = hallList.get(i);
		while ( i < hallList.size()){
			if (hall.getHallCode().equals(hallCode)){
				hall.updateHallType(hallClass);
				return true;// If a match is found, return true
			}		
			else {
				i++;
				hall = hallList.get(i);//Traverse to the next Hall object in the List
			}
		}
		return false;// If the Hall does not exists in the List of Halls
	}
	/**
	 * This method is to remove an existing hall based on the hallCode.
	 * @param hallCode The unique inputed hallCode to identify the hall to modify.
	 * @return true if updating is successful, else false.
	 */
	public boolean removeHall(String hallCode)
	{
		int i = 0;
		Hall hall = hallList.get(i);
		while ( i < hallList.size()){
			if (hall.getHallCode().equals(hallCode)){
				hallList.remove(i);
				return true;// If a match is found, return true
			}		
			else {
				i++;//Traverse to the next Hall object in the List
				hall = hallList.get(i);
			}
		}
		return false;// If the Hall does not exists in the List of Halls
	}
	/**
	 * This method is to modify the class rate based on the hallClass and rate.
	 * @param hallClass The inputed hallClass.
	 * @param rate The new inputed rate to change to.
	 */
	public void updateClassRate(String hallClass, Double rate)
	{
		for (int i = 0 ; i <hallList.size(); i++){
			hallList.get(i).updateClassRate(hallClass, rate);
		}
		//return hallList.get(hallList.size()-1);	
	}
	/**
	 * This method is to get a hash map consisting of the hall class and its corresponding rate.
	 * @return hashMap Hash Map of the class and rate.
	 */
	public HashMap <String, Double> getClassRateHash()
	{
		return hallList.get(0).classRate;
	}
	
	/**
	 * This method is to get the list of all the hall objects.
	 * @return hallList List of the hall objects.
	 */
	public List<Hall> getHallList()
	{
		return hallList;
	}
	
	/**
	 * This method is to get a specific hall object from the hall list by searching a match using the inputed hallCode
	 * @param hallCode The unique inputed hallCode to identify the hall to modify.
	 * @return Hall The hall object corresponding to the hallCode.
	 */
	public Hall getHallObject(String hallCode)
	{
		int i = 0;
		Hall hall = hallList.get(i);
		while ( i < hallList.size()){
			if (hall.getHallCode().equals(hallCode))
				return hall;// If a match is found, return the specific Hall Object
			else {
				i++;//Traverse to the next Hall object in the List
				hall = hallList.get(i);
			}
		}
		return null;// If the Hall Object does not exists in the List of Halls, a Null object is returned
	}
	
	/**
	 * Write all the updated hallList into the file path.
	 * @return true if saving is successful, else false.
	 */
	public boolean save()
	{
		return this.write(hallList, HALL_PATH);
	}
}
