package Entities;

import java.io.Serializable;
import java.util.*;

/**
 * Represents a user that has registered with the MoblimaApp. 
 * A user can be either an Admin type or Customer type.
 * @author: CZ2002 Team 3
 * @version: Final version
 * @since 2017-11-16
 */
public class User implements Serializable 
{
	/**
	* The name of the user.
	*/
	private String name;
	/**
	* The mobileNumber of the user.
	*/
	private String mobileNumber;
	/**
	* The email of the user.
	*/
	private String email;
	/**
	* The password of the user.
	*/
	private String password;
	/**
	* The ageGroup of the user.
	* Can only be Child, Adult, Senior Citizen.
	* To determine the fees applicable to each Customer.
	*/
	private String ageGroup; 
	/**
	* Enum type to distinguish between a Customer and Admin user.
	*/
	public enum USER_TYPE {Customer, Admin}; 
	/**
	* The user type of the object; Customer or Admin.
	*/
	private USER_TYPE user_type;
	/**
	* A List of String objects storing the bookingHistory of each User.
	* {@link List}
	*/
	private List <String> bookingHistory;
	/** 
	* Rates will be a HashMap with Age Group as key and the corresponding rates as key-values.
	* HashMap is used to create unique and non-duplicate key-value entry
	* 3 Different Groups: Child, Adult, Senior Citizen.
	* {@link HashMap}
	*/
	public HashMap<String, Double> userAgeRate;
	/**
	* Creates a new User with the given name, password, mobileNumber, email, ageGroup and user_type. 
	* BookingHistory and userAgeRate will be initialized with NULL value.
	* @param name This User's name.
	* @param password This User's password.
	* @param mobileNumber This User's mobile number.
	* @param email This User's email address.
	* @param ageGroup This User's age group.
	* @param user_type This User's user type. (Admin or Customer)
	*/
	public User (String name, String password, String mobileNumber, String email, String ageGroup, USER_TYPE user_type){
		this.name = name;
		this.password = password;
		this.mobileNumber = mobileNumber;
		this.email = email; 
		this.ageGroup = ageGroup;
		this.user_type = user_type;
		this.bookingHistory = new ArrayList<String>();
		this.userAgeRate = new HashMap<String, Double>();
	}
	/**
	* Changes the name of this User.
	* @param name This User's name.
	*/
	public void updateName(String name)
	{
		this.name = name;
	}
	/**
	* Changes the password of this User.
	* @param password This User's password.
	*/
	public void updatePassword(String password) 
	{
		this.password = password;
	}
	/**
	* Changes the ageGroup of this User.
	* @param ageGroup This User's ageGroup.
	*                 Only can be Child, Adult, Senior Citizen.
	*/
	public void updateAgeGroup(String ageGroup)
	{
		this.ageGroup = ageGroup;
	}
	/**
	* Changes the user of this User.
	* {@link USER_TYPE}
	* @param user_type This User's user_type.
	*/
	public void updateUserType(USER_TYPE user_type)
	{
		this.user_type = user_type;
	}
	/**
	* Adds new booking transaction to this User.
	* @param transaction This User's transaction.
	*/
	public void updateBookingHistory(String transaction) 
	{
		this.bookingHistory.add(transaction);
	}
	/**
	* Changes the mobileNumber of this User.
	* @param mobileNumber This User's mobileNumber.
	*/
	public void updateMobilenumber(String mobileNumber) 
	{
		this.mobileNumber = mobileNumber;
	}
	/**
	* Changes the ageGroup Rates of different user_type.
	* A HashMap is used, so that it creates a unique entry for each ageGroup Rate
	* @param ageGroup This User's ageGroup.
	* @param amount The amount of the given ageGroup
	*/
	public void updateUserAgeRate(String ageGroup, Double amount) 
	{
		this.userAgeRate.put(ageGroup,amount);
	}
	/**
	* Changes the email of this User.
	* @param email This User's email.
	*/
	public void updateEmail(String email) 
	{
		this.email = email;
	}
	/**
	* Gets the email of this User.
	* @return this Users's email.
	*/
	public String getEmail()
	{
		return email;
	}
	/**
	* Gets the Ticket Rate for different ageGroup User.
	* @param ageGroup The User's age group.
	* @return this User's ageGroup.
	*/
	public Double getUserAgeRate(String ageGroup)
	{ 
		return userAgeRate.get(ageGroup);
	}
	/**
	* Gets the name of this User.
	* @return this Users's name.
	*/
	public String getName()
	{
		return name;
	}
	/**
	* Gets the password of this User.
	* @return this Users's password.
	*/
	public String getPassword() 
	{
		return password;
	}
	/**
	* Gets the ageGroup of this User.
	* @return this Users's ageGroup.
	*/
	public String getAgeGroup()
	{
		return ageGroup;
	}
	/**
	* Gets the user_type of this User.
	* {@link USER_TYPE}
	* @return this Users's user_type.
	*/
	public USER_TYPE getUserType() 
	{
		return user_type;
	}
	/**
	* Gets the List of booking history of this User.
	* {@link List}
	* @return this Users's bookingHistory.
	*/
	public List <String> getBookingHistory() 
	{
		return bookingHistory;
	}
	/**
	* Gets the mobileNumber of this User.
	* @return this Users's mobileNumber.
	*/
	public String getMobilenumber() 
	{
		return mobileNumber;
	}
	/**
	* Gets the HashMap of the customer's Age Group Rate.
	* Each User Age Rate is the same regardless of individual Users
	* {@link HashMap}
	* @return Users's HashMap userAgeRate.
	*/
	public HashMap <String, Double> getAgeHash()
	{
		return this.userAgeRate; 
	}
}