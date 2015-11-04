package com.ajscanlan.tapadooprogrammingtest.download;

import com.ajscanlan.tapadooprogrammingtest.model.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander Scanlan on 03/11/2015
 *
 * Class is used to implement callback methods in BookDetailFragment and BookListFragment for use
 * with onPostExecute of the download AsyncTasks
 */
public interface DownloadCallback {
    void finished(List<Book> list);

    void finished(String description);
}
