package Manager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import Entities.Movie;
import Entities.User;
/**
 * Represents the MovieManager of MoblimaApp that handles the creating, updating, removing of movies.
 * @author: CZ2002 Team 3
 * @version: Final version
 * @since 2017-11-16
 */
public class MovieManager extends DataManager 
{
	private static MovieManager instance = null;
	private UserManager userManager;
	public static final String MOVIE_PATH = "movie.dat";
	private List<Movie> movieList = null ;

	/**
	 * To create one instance of the MovieManager if it does not exists else it will return
	 * the instance of MovieManager that already exists.
	 * @return instance The instance of MovieManager that is already created.
	 */
	public static MovieManager getInstance() 
	{	
		if(instance == null)
		{
			instance = new MovieManager();
			return instance;
		}
		return instance;
	}	

	/**
	* This constructor using the Super Class constructor which will be called by the getInstance() method if no such instance of MovieManager is created yet.
	*/
	private MovieManager()
	{	
		super(MOVIE_PATH);
		if (read(MOVIE_PATH) instanceof List <?>){
			movieList = ((List<Movie>) this.read(MOVIE_PATH));
		}
		
		if (movieList == null){
			movieList = new ArrayList<Movie>();
		}
		userManager = UserManager.getInstance();
	}

	/**
	 * This method checks if a particular movie is already in the list.
	 * @param title The title of the movie to be checked.
	 * @return true if exists, else false.
	 */
	public boolean checkMovieExistence(String title)
	{
		for (int i = 0 ; i <movieList.size(); i++){
			if (movieList.get(i).getTitle().toLowerCase().equals (title.replaceAll("\\s+","_").toLowerCase())){
				return true;
			}
		}
		return false;
	}
	/**
	 * This method adds a new movie while taking in the movie listing details. 
	 * @param title The title of the movie.
	 * @param showingStatus The current showing status of the movie. 
	 * @param synopsis The synopsis of the movie.
	 * @param director The director of the movie.
	 * @param cast At least two casts of the movie.
	 * @return Movie The newly created movie object.
	 */
	public Movie addMovie(String title, String showingStatus, String synopsis, String director, String cast)
	{
		//remove whitespaces and change it to underscore in title for further methods for transaction
		movieList.add(new Movie(title.replaceAll("\\s+","_"), showingStatus, synopsis, director, cast)); 
		return movieList.get(movieList.size()-1); //return the movie object so that changes can be made to this movie via other methods in this class
	}
	
	/**
	 * This method performs the removing of a movie based on the inputed movie object.
	 * @param movie The movie object to be removed.
	 * @return true if deletion is successful, else false.
	 */
	public boolean removeMovie(Movie movie)
	{
		return movieList.remove(movie);
	}
	/**
	 * This method returns the movie object by matching the object with the inputed movie title.
	 * @param title The title of movie object to be obtained.
	 * @return Movie The movie object of matching title.
	 */
	
	public Movie getMovieObject(String title)
	{   //assumes each movie title is unique
		for (int i = 0 ; i <movieList.size(); i++){
			if (movieList.get(i).getTitle().equals (title)){
				return movieList.get(i); 
			}
		}
		return null;
	}
	
	/**
	 * This method returns the hash map of movie type and its corresponding rate.
	 * @return HashMap The hash map of all movie types and their rates.
	 */
	public HashMap <String, Double> getMovieTypeRateHash()
	{
		return movieList.get(0).getTypeRateHash(); // since each movie type regardless of movies will be the same rate
	}

	/**
	 * This method returns the list of all movie objects.
	 * @return List The list of all movie objects.
	 */
	public List<Movie> getMovieList()
	{ 
		return movieList;
	}
	
	/**
	 * This method updates the title of the movie.
	 * @param indexToUpdate The index of the movie list to identify the movie to change its title.
	 * @param title The title to be changed to.
	 */
	public void updateTitle(int indexToUpdate, String title)
	{
		movieList.get(indexToUpdate).updateTitle(title.replaceAll("\\s+","_")); //remove whitespaces in title
		//return movieList.get(movieList.size()-1);
	}

	/**
	 * This method updates the showing status of the movie.
	 * @param indexToUpdate The index of the movie list to identify the movie to change its showing status.
	 * @param showingStatus The showing status to be changed to.
	 */
	public void updateShowingStatus(int indexToUpdate, String showingStatus)
	{
		if (showingStatus.equals("ENDOFSHOWING") == false){
			movieList.get(indexToUpdate).updateShowingStatus(showingStatus);
			//return movieList.get(movieList.size()-1);
			
		}
		// if showingStatus is changed to "ENDOFSHOWING", the movie will be removed
		else {
			movieList.remove(movieList.get(indexToUpdate));
			//return movieList.get(movieList.size()-1);
		}
	}
	/**
	 * This method updates the synopsis of the movie.
	 * @param indexToUpdate The index of the movie list to identify the movie to change its synopsis.
	 * @param synopsis The synopsis to be changed to.
	 */
	public void updateSynopsis(int indexToUpdate, String synopsis)
	{
		movieList.get(indexToUpdate).updateSynopsis(synopsis);
		//return movieList.get(movieList.size()-1);
	}
	/**
	 * This method updates the director of the movie.
	 * @param indexToUpdate The index of the movie list to identify the movie to change its director.
	 * @param director The director to be changed to.
	 */
	public void updateDirector(int indexToUpdate, String director)
	{
		movieList.get(indexToUpdate).updateDirector(director);
		//return movieList.get(movieList.size()-1);
	}
	/**
	 * This method updates the casts of the movie.
	 * @param indexToUpdate The index of the movie list to identify the movie to change its cast.
	 * @param cast The casts to be changed to.
	 */
	public void updateCast(int indexToUpdate, String cast)
	{
		movieList.get(indexToUpdate).updateCast(cast);
		//return movieList.get(movieList.size()-1);
	}
	/**
	 * This method updates or sets the movie type and its rate.
	 * @param type The movie type to update or set.
	 * @param rate The rate to update or set.
	 */
	public void updateTypeRate(String type, double rate)
	{
		for (int i = 0 ; i <movieList.size(); i++){
			movieList.get(i).updateTypeRate(type, rate);
		}
	}
	/**
	 * This method updates the review of the movie.
	 * @param movie The movie object to append the inputed review.
	 * @param review The inputed review to write to the movie object.
	 */
	public void updateReview(Movie movie, String review)
	{ // write review for a particular movie 
		movieList.get(movieList.indexOf(movie)).updateReviewList(review);
	}
	/**
	 * This method updates the rating of the movie.
	 * @param movie The movie object to append the inputed rating.
	 * @param rating The inputed rating to write to the movie object.
	 */
	public void updateRating(Movie movie, double rating)
	{ // give rating for a particular movie 
		movieList.get(movieList.indexOf(movie)).updateRatingList(rating);
	}
	/**
	 * This method sets the overall rating of all movies.
	 * @return List The list of the overall ratings of all movies.
	 */
	public List <Double> setOverallRating()
	{
		List <Double> overallRatings = new ArrayList <Double> ();
		int numberOfRatings;
		double overallRating;
		for (int i = 0;  i<movieList.size(); i++){
			numberOfRatings =  movieList.get(i).getRatingList().size();
			overallRating= 0;
			if (numberOfRatings > 1){ //if there is less than 2 ratings, overallRating = 0 
				for (int j = 0 ; j < numberOfRatings; j++){
					overallRating += movieList.get(i).getRatingList().get(j);
				}
				overallRating = overallRating / numberOfRatings;
				movieList.get(i).updateOverallRating(overallRating);
				overallRatings.add(overallRating);
			}
		}
		return overallRatings;	
	} 
	/**
	 * This method sets the overall rating for a particular movie.
	 * @param movie The inputed movie object to set its overall rating.
	 * @return Movie The updated movie object with overall rating.
	 */
	public Movie setOverallRating (Movie movie)
	{
		int numberOfRatings =movie.getRatingList().size();
		double overallRating = 0;
		if (numberOfRatings >1){
			for (int i = 0 ; i<numberOfRatings; i++){
				overallRating += movie.getRatingList().get(i);
			}
			overallRating = overallRating / numberOfRatings;
			movieList.get(movieList.indexOf(movie)).updateOverallRating(overallRating);
		}
		return movieList.get(movieList.size()-1);	
	}
	
	/**
	 * This method aggregate the sales of each movie and stores in a hash map.
	 * @return rankedMovieHash. Hash map that contains all the movies and their corresponding total sales.
	 */
	// to aggregate the sales of each movie and store in a dictionary
	public HashMap <String, Double> generateTotalSales()
	{
		HashMap <String, Double> movieTotalSales = new HashMap <String, Double>();
		List <String> uniqueMovieList = new ArrayList <String>();
		double sales = 0;
		List <User> tempList = new ArrayList <User> ();
		for (int i =0; i<userManager.getUserList().size(); i++){
			//if (userManager.getUserList().get(i).getUserType() == USER_TYPE.Customer){
				tempList.add(userManager.getUserList().get(i));
			//}
		}
		
		List <String> splitUserBookingHistory = new ArrayList <String> ();
		String [] splitPerTransaction = new String [100];
		List <String> userBookingHistory = new ArrayList <String> ();
		
		// to extract only movie title and price
		for (int i = 0 ; i < tempList.size(); i++){
			if (tempList.get(i).getBookingHistory().size() > 0){
				userBookingHistory = tempList.get(i).getBookingHistory();
				for (int j = 0; j < userBookingHistory.size(); j++) {
					splitPerTransaction = (userBookingHistory.get(j).split(" ")).clone();
					splitUserBookingHistory.add(splitPerTransaction[4]);
					splitUserBookingHistory.add(splitPerTransaction[15]);
				}
			}
		}
		
		// to gather all the same movies together and find the total sales
		// increment 2 because of [movie1, movie1price, movie2, movie2price, ...] to just get the title
		for (int k = 0; k < splitUserBookingHistory.size(); k+=2){
	
			if (movieTotalSales.get(splitUserBookingHistory.get(k)) != null ){
				sales = movieTotalSales.get(splitUserBookingHistory.get(k));
				sales += Double.parseDouble(splitUserBookingHistory.get(k+1));
				movieTotalSales.put(splitUserBookingHistory.get(k), sales);
			}
			else {
				// the movie has not exist in the movieTotalSales hashmap, thus add it in
				sales = Double.parseDouble(splitUserBookingHistory.get(k+1));
				movieTotalSales.put(splitUserBookingHistory.get(k), sales);
				uniqueMovieList.add(splitUserBookingHistory.get(k));
			}
		}
		List <Double> dictList = new ArrayList <Double>();
		LinkedHashMap <String, Double> rankedMoviesHash = new LinkedHashMap <String, Double> ();
		for (int i = 0 ; i < movieTotalSales.size(); i++){
			 dictList.add(movieTotalSales.get(uniqueMovieList.get(i)));
		}
		Collections.sort(dictList, Collections.reverseOrder());
		for (int i = 0 ; i <dictList.size(); i++){
			for (int j = 0; j <uniqueMovieList.size(); j++){
				if (movieTotalSales.get(uniqueMovieList.get(j)).equals(dictList.get(i))){
					rankedMoviesHash.put((uniqueMovieList.get(j)),dictList.get(i));
					break;
				}
			}
		}
		return rankedMoviesHash;
	}
	/**
	 * This method ranks the movies by ratings.
	 * @param overallRatingsList The list consisting of the overall ratings of all movies.
	 * @return rankedMoviesRatingsList List that contains the movie titles based on the overall ratings with the highest as the first index.
	 */
	public List <String> rankByRatings(List <Double> overallRatingsList)
	{
		List <Movie> tempMovieList = (List<Movie>) ((ArrayList) movieList).clone();
		//List <Double> dictList = new ArrayList <Double>();
		List <String> rankedMoviesRatingsList= new ArrayList <String>();
/*		for (int i = 0 ; i < movieList.size(); i++){
			 dictList.add(movieList.get(i).getOverallRating());
		}*/
		Collections.sort(overallRatingsList, Collections.reverseOrder()); 
		for (int j = 0 ; j< overallRatingsList.size(); j++){
			for (int i = 0; i< tempMovieList.size(); i++){
				if (tempMovieList.get(i).getOverallRating() == overallRatingsList.get(j)){
					rankedMoviesRatingsList.add(tempMovieList.get(i).getTitle());
					tempMovieList.remove(i);
					break;
				}
			}
		}
		return rankedMoviesRatingsList;
	}
	/**
	 * This method saves the movieList to the file path.
	 * @return true if save is successful, else false.
	 */
	public boolean save()
	{
		return (this.write(movieList, MOVIE_PATH)); 
	}
}
