package com.goodhabitstudio.libbyapp.libbyapp;

import android.view.View;
import android.widget.TextView;

/**
 * Created by dondi-mac on 16/11/17.
 */

public class MyHolderBookCard {
    TextView nowReadBook_bookTitle;
    TextView nowReadBook_bookAuthor;
    public MyHolderBookCard(View itemView) {


        nowReadBook_bookTitle = (TextView) itemView.findViewById(R.id.nowReadBook_bookTitle);
        nowReadBook_bookAuthor = (TextView) itemView.findViewById(R.id.nowReadBook_bookAuthor);

    }

}
