package com.ajscanlan.tapadooprogrammingtest;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ajscanlan.tapadooprogrammingtest.model.Book;

/**
 * A fragment representing a single Book detail screen.
 * This fragment is either contained in a {@link BookListActivity}
 * in two-pane mode (on tablets) or a {@link BookDetailActivity}
 * on handsets.
 */
public class BookDetailFragment extends Fragment {



    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_POSITION = "item_position";

    /**
     * The model content this fragment is presenting.
     */
    private Book mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BookDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_POSITION)) {
            // Load the model content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = BookListFragment.mBooks.get(getArguments().getInt(ARG_ITEM_POSITION));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getTitle());
            }

            setupTextViews(mItem);
        }
    }

    private void setupTextViews(Book mItem) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_book_detail, container, false);

        // Show the model content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.title_view)).setText(mItem.getTitle());
            ((TextView) rootView.findViewById(R.id.author_view)).setText(mItem.getAuthor());
            ((TextView) rootView.findViewById(R.id.isbn_view)).setText(mItem.getISBN());
            ((TextView) rootView.findViewById(R.id.id_view)).setText(String.valueOf(mItem.getId()));
            ((TextView) rootView.findViewById(R.id.desc_view)).setText(mItem.getDescription());


            /*
                get int price as a string with -1 being default value, convert from cents/pence to whole value
                and append currency
            */
            double fullPriceDouble = ((double) mItem.getPrice()) / 100; //TODO: implement BigDecimal
            String priceWithCurrency =  String.valueOf(fullPriceDouble) + " " + mItem.getCurrencyCode();

            ((TextView) rootView.findViewById(R.id.price_view)).setText(priceWithCurrency);
        }

        return rootView;
    }


}
