package Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
/**
 * Represents a movie that is listed in the Movie Listing of Moblima Cineplex. 
 * @author: CZ2002 Team 3
 * @version: Final version
 * @since 2017-11-16
 */
public class Movie implements Serializable 
{
	/**
	* The title of the movie.
	*/
	private String title;
	/**
	* The showing status of the movie. 
	* It can be "COMINGSOON", "PREVIEW", "NOWSHOWING", "ENDOFSHOW"
	*/
	private String showingStatus;
	/**
	* The synopsis of the movie.
	*/
	private String synopsis;
	/**
	* The director of the movie.
	*/
	private String director;
	/**
	* The casts of the movie.
	* Minimum 2 casts per movie
	*/
	private String cast;
	/**
	* The overall rating of the movie.
	*/
	private double overallRating;
	/**
	* The List of all the past reviewer's ratings of the movie.
	* {@link List}
	*/
	private List<Double> ratingList;
	/**
	* The List of all the past reviewer's reviews of the movie.
	* {@link List}
	*/
	private List<String> reviewList;
	/** 
	* Movie Types Rates will be stored in a HashMap with movie type as key and the corresponding rates as key-values.
	* HashMap is used to create unique and non-duplicate key-value entry.
	* {@link HashMap}
	*/
	public HashMap <String, Double> movieTypeRate; 
	/**
	* Create a new movie listing when given title, showingStatus, synopsis, director, cast.
	* The ratingList, reviewList and movieTypeRate will be initialized with NULL value.
	* @param title This Movie's title.
	* @param showingStatus This Movie's showing status.
	* @param synopsis This Movie's synopsis.
	* @param director This Movie's director.
	* @param cast This Movie's casts.
	*/
	public Movie (String title, String showingStatus, String synopsis, String director, String cast) 
	{
		this.title = title;
		this.showingStatus = showingStatus;
		this.synopsis = synopsis;
		this.director = director;
		this.cast = cast;
		this.overallRating = 0;
		ratingList = new ArrayList<Double>();
		reviewList = new ArrayList<String>();
		this.movieTypeRate = new HashMap<String, Double>(); 
	}
	/**
	* Changes the casts of this Movie.
	* @param cast This Movie's show cast.
	*/
	public void updateCast(String cast) 
	{
		this.cast = cast;
	} 
	/**
	* Changes the title of this Movie.
	* @param title This Movie's title.
	*/
	public void updateTitle(String title) 
	{
		this.title = title;
	}
	/**
	* Changes the showing status of this Movie.
	* @param showingStatus This Movie's showing status.
	*/
	public void updateShowingStatus(String showingStatus) 
	{
		this.showingStatus = showingStatus;
	}
	/**
	* Changes the synopsis of this Movie.
	* @param synopsis This Movie's synopsis.
	*/
	public void updateSynopsis(String synopsis) 
	{
		this.synopsis = synopsis;
	}
	/**
	* Changes the director of this Movie.
	* @param director This Movie's director.
	*/
	public void updateDirector(String director) 
	{
		this.director = director;
	}
	/**
	* Adds a reviewer's rating to the rating list of this Movie.
	* @param rating This Movie's rating by reviewer to be added.
	*/
	public void updateRatingList(Double rating)
	{ 
		this.ratingList.add(rating);
	}
	/**
	* Adds a reviewer's review to the reviewlist of this Movie.
	* @param review This Movie's review by reviewer to be added.
	*/
	public void updateReviewList(String review)
	{ 
		this.reviewList.add(review);
	}
	/**
	* Changes the movie type rates of different movie types (E.g. 3D, Blockbusters, etc).
	* A HashMap is used, so that it creates a unique entry for each movie type rate.
	* @param type Represents different movie type rate to set or update,
	* @param rate Represents the new rate to be updated.
	*/
	public void updateTypeRate(String type, Double rate)
	{
		this.movieTypeRate.put(type, rate);
	}
	/**
	* Adds an overall rating of this Movie.
	* @param overallRating This Movie's overall rating which is the average of all overall rating.
	*                      Only applicable when there are more than 2 ratings.
	*/
	public void updateOverallRating(Double overallRating)
	{
		this.overallRating = overallRating;
	}
	/**
	* Gets the title of this Movie.
	* @return this Movie's title.
	*/
	public String getTitle() 
	{ 
		return title;
	}
	/**
	* Gets the showing status of this Movie.
	* @return this Movie's showingStatus.
	*/
	public String getShowingStatus() 
	{ 
		return showingStatus;
	}
	/**
	* Gets the synopsis of this Movie.
	* @return this Movie's synopsis.
	*/
	public String getSynopsis() 
	{ 
		return synopsis;
	}
	/**
	* Gets the director of this Movie.
	* @return this Movie's director.
	*/
	public String getDirector() 
	{
		return director;
	}
	/**
	* Gets the casts of this Movie.
	* @return this Movie's casts.
	*/
	public String getCast() 
	{ 
		return cast;
	}
	/**
	* Gets the rating list of this Movie.
	* @return this Movie's ratingList.
	*/
	public List<Double> getRatingList()
	{ 
		return ratingList;
	}
	/**
	* Gets the list of reviewer's review of this Movie.
	* @return this Movie's reviewList.
	*/
	public List<String> getReviewList()
	{ 
		return reviewList;
	}
	/**
	* Gets the rate of a particular movie type this Movie.
	* @param type This Movie's movie type
	* @return this Movie's movieTypeRate
	*/
	public double getTypeRate(String type)
	{ 
		return movieTypeRate.get(type);
	}
	/**
	* Gets the HashMap containing all the movie types and corresponding rates.
	* @return HashMap movieTypeRate.
	*/
	public HashMap <String, Double> getTypeRateHash()
	{
		return movieTypeRate;
	}
	/**
	* Gets the overall rating of this Movie
	* @return this Movie's overallRating
	*/
	public double getOverallRating()
	{
		return overallRating;
	}
}
