package com.ajscanlan.tapadooprogrammingtest;

import com.ajscanlan.tapadooprogrammingtest.model.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User1 on 03/11/2015.
 */
public interface DownloadCallback {
    void finished(List<Book> list);
}
