package com.test.usatoday;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A fragment representing a single Movie Review detail screen. This fragment is
 * either contained in a {@link MovieReviewListActivity} in two-pane mode (on
 * tablets) or a {@link MovieReviewDetailActivity} on handsets.
 */
public class MovieReviewDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_TITLE = "item_title";

	/**
	 * The content this fragment is presenting.
	 */
	private MovieReviewLoader.MovieItem mItem;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public MovieReviewDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_TITLE)) {
			// Load the content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			mItem = MovieReviewLoader.ITEM_MAP.get(getArguments().getString(
					ARG_ITEM_TITLE));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_moviereview_detail,
				container, false);

		// Show the content as text in a TextView.
		if (mItem != null) {
			((TextView) rootView.findViewById(R.id.moviereview_title))
					.setText(mItem.title());
			((TextView) rootView.findViewById(R.id.ratedText))
					.setText(mItem.rated());
			((TextView) rootView.findViewById(R.id.ratingText))
					.setText("USAToday rating: " + mItem.rating());
			((TextView) rootView.findViewById(R.id.reviewText))
					.setText(mItem.snippet());
		}
		
		//Log.i("DetailFragment", "Data" + mItem.toString());

		return rootView;
	}
}
