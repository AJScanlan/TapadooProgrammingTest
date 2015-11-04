package com.ajscanlan.tapadooprogrammingtest;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ajscanlan.tapadooprogrammingtest.download.DownloadCallback;
import com.ajscanlan.tapadooprogrammingtest.download.DownloadDescriptionHandler;
import com.ajscanlan.tapadooprogrammingtest.model.Book;

import java.util.List;

/**
 * A fragment representing a single Book detail screen.
 * This fragment is either contained in a {@link BookListActivity}
 * in two-pane mode (on tablets) or a {@link BookDetailActivity}
 * on handsets.
 */

public class BookDetailFragment extends Fragment implements DownloadCallback {

    //TextView for description
    private TextView descTextView;

    /**
     * The fragment argument representing the position in the list of the item.
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

            //Getting book from list at the position passed in the arguments
            mItem = BookListFragment.mBooks.get(getArguments().getInt(ARG_ITEM_POSITION));

            Activity activity = this.getActivity();

            //Sets the title of the action bar to the tile of the book
            if(((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
                ((AppCompatActivity )getActivity()).getSupportActionBar().setTitle(mItem.getTitle());
            }
        }
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

            descTextView = (TextView) rootView.findViewById(R.id.desc_view);

            /*
                if the items description has been updated display it else display "Loading..."
                 while it is being fetched
             */
            if(mItem.getDescription() != null){
                descTextView.setText(mItem.getDescription());
            } else {
                descTextView.setText(R.string.loading);
                new DownloadDescriptionHandler(this, mItem);
            }

            /*
                get int price as a string with -1 being default value,
                convert from cents/pence to whole value and append currency
            */
            double fullPriceDouble = ((double) mItem.getPrice()) / 100; //TODO: implement BigDecimal
            String priceWithCurrency =  String.valueOf(fullPriceDouble) + " " + mItem.getCurrencyCode();

            ((TextView) rootView.findViewById(R.id.price_view)).setText(priceWithCurrency);
        }

        return rootView;
    }

    /**
     * Used to update description TextView when finished downloading
     */
    @Override
    public void finished(String description) {
        descTextView.setText(description);
    }

    /**
     * Not used in this class
     */
    @Override
    public void finished(List<Book> list) {}

}
