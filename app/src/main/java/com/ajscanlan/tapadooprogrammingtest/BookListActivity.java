package com.ajscanlan.tapadooprogrammingtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ArrayAdapter;

import com.ajscanlan.tapadooprogrammingtest.model.Book;

import java.util.ArrayList;
import java.util.List;


/**
 * An activity representing a list of Books. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link BookDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link BookListFragment} and the item details
 * (if present) is a {@link BookDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link BookListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class BookListActivity extends AppCompatActivity implements BookListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private BookListFragment mBookListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_app_bar);

        if (findViewById(R.id.book_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((BookListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.book_list))
                    .setActivateOnItemClick(true);
        }
    }

    /**
     * Callback method from {@link BookListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(int position) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putInt(BookDetailFragment.ARG_ITEM_POSITION, position);
            BookDetailFragment fragment = new BookDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.book_detail_container, fragment)
                    .commit();

        } else {

            Intent detailIntent = new Intent(this, BookDetailActivity.class);
            detailIntent.putExtra(BookDetailFragment.ARG_ITEM_POSITION, position);

            startActivity(detailIntent);
        }
    }

    public void refreshList(View view) {
//        ArrayList<Book> books = new ArrayList<>();
//        books.add(new Book(0, 0, null, "A", null, null));
//        books.add(new Book(0, 0, null, "A", null, null));
//        books.add(new Book(0, 0, null, "A", null, null));

        //((BookListFragment)getSupportFragmentManager().findFragmentById(R.id.book_list)).finished(books);
        new DownloadHandler(BookListActivity.this,
                ((BookListFragment)getSupportFragmentManager().findFragmentById(R.id.book_list)),
                DownloadHandler.BASE_URL);
    }
}
