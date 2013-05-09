package com.test.usatoday;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * An activity representing a list of Movie Reviews. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link MovieReviewDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link MovieReviewListFragment} and the item details (if present) is a
 * {@link MovieReviewDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link MovieReviewListFragment.Callbacks} interface to listen for item
 * selections.
 */
public class MovieReviewListActivity extends FragmentActivity implements
		MovieReviewListFragment.Callbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	
	public static MovieReviewLoader mMovieLoader = new MovieReviewLoader();
	
	private boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_moviereview_list);

		if (findViewById(R.id.moviereview_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((MovieReviewListFragment) getSupportFragmentManager()
					.findFragmentById(R.id.moviereview_list))
					.setActivateOnItemClick(true);
		}

		// If exposing deep links, handle intents here.
	}

	/**
	 * Callback method from {@link MovieReviewListFragment.Callbacks} indicating
	 * that the item with the given title was selected.
	 */
	@Override
	public void onItemSelected(String title) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(MovieReviewDetailFragment.ARG_ITEM_TITLE, title);
			MovieReviewDetailFragment fragment = new MovieReviewDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.moviereview_detail_container, fragment)
					.commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this,
					MovieReviewDetailActivity.class);
			detailIntent.putExtra(MovieReviewDetailFragment.ARG_ITEM_TITLE, title);
			startActivity(detailIntent);
		}
	}
}
