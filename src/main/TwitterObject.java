package main;

import java.util.HashMap;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterObject implements Comparable<TwitterObject>{

	private Twitter twitter;

	private HashMap<String, Double> TweetList;
	private String ProductName;
	private double total_intensity;
	private double avg_intensity;

	
	public TwitterObject(String ProductName) {
		this.ProductName = ProductName;
		TweetList = new HashMap<>();
		ApiSetup();

	}

	public HashMap<String, Double> GetTweetList() {

		return TweetList;
	}

	public void SearhTweets(int count) {

		Query query = new Query(this.ProductName);
		query.setCount(count);
		query.lang("en");

		QueryResult result = null;
		try {
			result = twitter.search(query);
		} catch (TwitterException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (Status status : result.getTweets()) {

			TweetList.put(status.getText(), (double) 0);
		}
	}

	private void ApiSetup() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey("j253t6MuTCT3DviZ17aR0VlLW")
				.setOAuthConsumerSecret("b51VV4KMT0D2jz4uaznS3r58WHuotG1UCt4Wp9uysagbZpWLqV")
				.setOAuthAccessToken("786336512766017536-t8ykdUP9PqnxEswgv5djQDB9qnjp595")
				.setOAuthAccessTokenSecret("0J5s0ZAR784CwlORD6wY1zvcGaFkKDrOZ6jhkPg9zRoll");
		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();
	}

	@Override
	public int compareTo(TwitterObject compareObject) {
		
		double compareAvgIntensity=((TwitterObject)compareObject).getAvg_intensity() ;
		if(compareAvgIntensity>this.avg_intensity)
			return 1;
		if(compareAvgIntensity<this.avg_intensity)
			return -1;
		return 0;
		
		
	}
	@Override
	public String toString() {
		
		return  "Product name : "+this.ProductName+" Average Intensity : "+String.format("%.3f", this.avg_intensity)+"\n";
	}

	public String getProductName() {
		return ProductName;
	}

	public void setProductName(String productName) {
		ProductName = productName;
	}

	public double getTotal_intensity() {
		return total_intensity;
	}

	public void setTotal_intensity(double total_intensity) {
		this.total_intensity = total_intensity;
	}

	public double getAvg_intensity() {
		return avg_intensity;
	}

	public void setAvg_intensity(double avg_intensity) {
		this.avg_intensity = avg_intensity;
	}

}

