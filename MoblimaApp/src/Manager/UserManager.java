package Manager;

import java.util.*;
import Entities.User;
import Entities.User.USER_TYPE;
/**
 * Represents the UserManager of MoblimaApp that handles the process of validating and creating of Users.
 * @author: CZ2002 Team 3
 * @version: Final version
 * @since 2017-11-16
 */
public class UserManager extends DataManager 
{
	/**
	 *  Ensure that throughout the program, only one instance of UserManager will be created.
	 */
	private static UserManager instance = null;
	/**
	 *  The filename that User Data will be written to and stored.
	 */
	public static final String USER_PATH = "user.dat";
	/**
	 *  Temporary List containing all the User objects during program runtime.
	 */
	private List<User> userList = null;
	/**
	 * To create one instance of the UserManager if it does not exists else it will return
	 * the instance of UserManager that already exists.
	 * @return instance The instance of UserManager that is already created.
	 */
	public static UserManager getInstance() 
	{	
		if(instance == null){
			instance = new UserManager();
		}
		return instance;
	}	
	/**
	 *  Constructor using the Super Class constructor which will be called by the
	 *  getInstance() method if no such instance of UserManager is created yet.
	 *  {@link DataManager} 
	 */
	private UserManager()
	{	
		/**
		 * Using the Super Class Constructor to create the database and file to store the 
		 * object. 
		 * {@link DataManager}
		 */
		super(USER_PATH);
		/*
		 * Load the list of User Objects if there is an existing List.
		 */
		if( read(USER_PATH) instanceof List<?>){
			userList = ((List<User>) this.read(USER_PATH));
		}
		/**
		 * If list is empty, a new list of User Objects will be created and updated whenever
		 * the program is running.
		 * {@link User} 
		 */
		if (userList == null){
			userList = new ArrayList<User>();
		}
	}
	/**
	 * Traverse down the List of User objects to find a match for the given email and password.
	 * {@link User}
	 * This is for Customer type User object.
	 * @param email The email of the User to validate.
	 * @param password The password of the User to validate.
	 * @return true if a match is found.
	 *         <p>
	 *         falise if a match is not found.
	 */        
	public boolean validateCustomer(String email, String password)
	{
		int i = 0;
		while ( i < userList.size()){
			User customer = userList.get(i);
			if (customer.getUserType() == USER_TYPE.Customer && customer.getEmail().equals(email) && customer.getPassword().equals(password))
				return true;// If a match is found, return true
			else 
				i++;//Traverse to the next User object in the List
		}
		return false;// If the Customer does not exists in the List of Customers
	}
	/**
	 * Traverse down the List of User objects to find a match for the given email and password.
	 * {@link User}
	 * This is for Admin type User object.
	 * @param email The email of the User to validate.
	 * @param password The password of the User to validate.
	 * @return true if a match is found.
	 *         <p>
	 *         falise if a match is not found.
	 */        
	public boolean validateAdmin(String email, String password)
	{
		int i = 0;
		User customer = userList.get(i);
		while ( i < userList.size()){
			customer = userList.get(i);
			if (customer.getUserType() == USER_TYPE.Admin && customer.getEmail().equals(email) && customer.getPassword().equals(password))
				return true;// If a match is found, return true
			else 
				i++;//Traverse to the next User object in the List
		}
		return false;// If the Admin does not exists in the List of Users
	}
    /**
     * Creates a new Customer type User object when given the name, password, mobile number, email and age group.
     * {@link User}
     * @param name The User's name.
     * @param password The User's password.
     * @param mobileNumber The User's mobile number.
     * @param email The User's email address.
     * @param ageGroup The User's age group. (Child/Adult/Senior Citizen)
     */
	public void createCustomer(String name, String password, String mobileNumber, String email, String ageGroup)
	{
		userList.add(new User(name, password, mobileNumber, email, ageGroup,User.USER_TYPE.Customer));
	}
	/**
     * Creates a new Admin type User object when given the name, password, mobile number, email and age group.
     * {@link User}
     * @param name The User's name.
     * @param password The User's password.
     * @param mobileNumber The User's mobile number.
     * @param email The User's email address.
     * @param ageGroup The User's age group. (Child/Adult/Senior Citizen)
     */
	public void createAdmin(String name, String password, String mobileNumber, String email, String ageGroup)
	{
		userList.add(new User(name, password, mobileNumber, email, ageGroup,User.USER_TYPE.Admin));
	}
	/**
     * Allows the delete of an existing Admin type User Object when given the email.
     * {@link User}
     * @param email The email address of the User to be deleted.
     * @return true if the Admin type User Object is deleted.
     *         <p>
     *         false if the Admin type User Object does not exists or not deleted.
     */
	public boolean deleteAdmin(String email)
	{
		int i = 0;
		User admin = userList.get(i);
		while ( i < userList.size()){
			admin = userList.get(i);
			if (admin.getUserType() == USER_TYPE.Admin && admin.getEmail().equals(email)){
				userList.remove(i);
				return true;// If a match is found, delete the User object and return true
			}
			else 
				i++;//Traverse to the next User object in the List
		}
		return false;// If the Admin does not exists in the List of User then return false
	}
	/**
	 * Allows admin to set the Rate for different Customer-type depending on Age Group. 
	 * {@link User}
	 * @param ageGroup The age group of Customer-type User. 
	 * @param rate The rate to set for different age group.
	 * @return User The age group and corresponding rate that was set.
	 */
	public User updateAgeRate(String ageGroup, Double rate)
	{
		for (int i = 0 ; i <userList.size(); i++){
			userList.get(i).updateUserAgeRate(ageGroup, rate);
		}
		return userList.get(userList.size()-1);
		}
	/**
	 * Gets the HashMap that stores all the Age Group and corresponding rates.
	 * {@link HashMap}
	 * @return HashMap ageGroupRate The HashMap that stores the Age Group and it's corresponding rates.
	 */
	public HashMap <String, Double> getUserAgeRateHash()
	{
		return userList.get(0).getAgeHash();
	}
	/**
	 * Gets the List of User Objects that are created and from the database.
	 * {@link User}
	 * @return userList The List of User Objects.
	 */
	// Access the List of User Objects
	public List<User> getUserList()
	{
		return this.userList;
	}
	/**
	 * Gets a specific User Object from the List of User Object when give the User's email.
	 * Returns a Null if such User Object does not exists.
	 * @param email The email address of the specific User Object that is being searched for.
	 * @return User The User Object with the matched email address.
	 */
	public User getUserObject(String email)
	{
		int i = 0;
		User customer = userList.get(i);
		while ( i < userList.size()){
			if (customer.getEmail().equals(email))
				return customer;// If a match is found, return the specific User Object
			else{ 
				i++;//Traverse to the next User object in the List
				customer = userList.get(i);
			}
		}
		return null;// If the User Object does not exists in the List of Users, a Null object is returned
	}
	/**
	 * Traverse the List of User Objects to find the matched object and write the transaction info
	 * into the Booking History.
	 * @param email The User's email address to write the booking history.
	 * @param transaction The transaction information to be written into the booking history.
	 * @return true if writing of booking transaction succeed.
	 *         <p>
	 *         falsie if the writing of booking transaction fails.
	 */
	public boolean writeBookingHistory(String email, String transaction)
	{
		int i = 0;
		while ( i < userList.size()){
			User customer = userList.get(i);
			if (customer.getEmail().equals(email)){
				userList.get(i).updateBookingHistory(transaction);// If a match is found, updates the booking history
				return true;
			}
			else 
				i++;//Traverse to the next User object in the List
		}
		return false;// If the User Object is not found, writing of Booking History is unsuccessful.
	}
	/**
	 * Method to write any updates to the User Lists into a file and stored when the Application
	// terminates.
	 * @return true if the saving was successful.
	 *         <p>
	 *         false if the saving was unsuccessful.
	 */
	public boolean save()
	{
		return this.write(userList, USER_PATH);
	}
}

