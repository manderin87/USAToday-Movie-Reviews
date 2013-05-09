package com.test.usatoday;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;


public class MovieReviewLoader extends AsyncTask<String, Void, Integer> {
	
	public static List<MovieItem> ITEMS = new ArrayList<MovieItem>();
	public static Map<String, MovieItem> ITEM_MAP = new HashMap<String, MovieItem>();
	
	@Override
	protected Integer doInBackground(String... paths) {
		InputStream in = null;
		InputStreamReader reader = null;
		BufferedReader br = null;
		StringBuilder results = new StringBuilder();
		
		
		
		try {
			URL url;
			url = new URL(paths[0]);
			
			in = url.openStream();
			reader = new InputStreamReader(in);
			
			br = new BufferedReader(reader);
			
			String line = br.readLine();
			
			while(line != null) {
				results.append(line);
				line = br.readLine();
			}
			
			// Parse the JSON Objects
			JSONObject jsonObject = new JSONObject(results.toString());
			
			JSONArray subArray = jsonObject.getJSONArray("MovieReviews");
			
			for(int i=0; i < subArray.length(); i++) {
			     String title = subArray.getJSONObject(i).getString("MovieName").toString();
			     String snippet = subArray.getJSONObject(i).getString("Review").toString().replaceAll("\\<.*?\\>", "");
			     String rated = subArray.getJSONObject(i).getString("MPAARating").toString();
			     String rating = subArray.getJSONObject(i).getString("Rating").toString();
			     
			     addItem(new MovieItem(title, snippet, rated, rating));
			}
			
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
	   
		//Log.i("MovieReviewLoader", "Data: " + results.toString());
	    
	    return 1;
	}
	

	public MovieReviewLoader() {
		this.execute("http://api.usatoday.com/open/reviews/movies/recent?encoding=json&api_key=jejanbnwafxur7n2x2zajauf");
	}
	
	private static void addItem(MovieItem item) {
		ITEMS.add(item);
		ITEM_MAP.put(item.title(), item);
	}
	
	public class MovieItem {
		
		String mTitle = "";
		String mSnippet = "";
		String mRated = "";
		String mRating = "";
		
		public MovieItem(String title, String snippet, String rated, String rating) {
			mTitle = title;
			mSnippet = snippet;
			mRated = rated;
			mRating = rating;
		}
		
		public String title() {
			return mTitle;
		}
		
		public String snippet() {
			return mSnippet;
		}
		
		public String rated() {
			return mRated;
		}
		
		public String rating() {
			return mRating;
		}
		
		@Override
		public String toString() {
			return mTitle + " " + mSnippet + " " + mRated + " " + mRating;
		}
	}

	
}
