package com.goodhabitstudio.libbyapp.libbyapp;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;


/**
 * Created by dondi-mac on 15/11/17.
 */

public class HomeFragment extends Fragment  {

    private FirebaseAuth mAuth;
    private String uid;
    public int books_finished;
    private int books_year_target;
    private TextView numberOfFinishedBooks;
    private TextView numberOfTargetedBooks;
    private ListView mListView;
    private MainActivity mainActivity;

//    private BookCardAdapter bookCardAdapter;
//    private BookCardFirebaseHelper bookCardFirebaseHelper;
    private ArrayList<Book> books;

    private RecyclerView recyclerViewHome;

    private DonutProgress donutProgress;



    //Loading
    private LinearLayout loading;
    private AVLoadingIndicatorView avi;

    //No Data
    private LinearLayout no_data;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        //LOADING
        loading = (LinearLayout) v.findViewById(R.id.loading_layout);
        loading.setVisibility(View.VISIBLE);
        avi = (AVLoadingIndicatorView) v.findViewById(R.id.avi);
        startAnim();

        //NO DATA
        no_data = (LinearLayout) v.findViewById(R.id.no_data);
        no_data.setVisibility(View.GONE);


        //Get Data
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        uid = user.getUid();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference userRef = database.getReference("users/"+uid);
        DatabaseReference bookRef = database.getReference("users/"+uid+"/books/list");

        numberOfFinishedBooks = (TextView) v.findViewById(R.id.numberOfFinishedBooks);
        numberOfTargetedBooks = (TextView) v.findViewById(R.id.numberOfTargetedBooks);
//        mListView = (ListView) v.findViewById(R.id.currentBook_listView);


        //FIREBASE
        recyclerViewHome =(RecyclerView) v.findViewById(R.id.recyclerview_home);
        recyclerViewHome.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseRecyclerAdapter<Book,viewHolder> recyclerAdapter=new FirebaseRecyclerAdapter<Book,viewHolder>(
                Book.class,
                R.layout.now_read_book_layout,
                viewHolder.class,
                bookRef
        ) {
            @Override
            protected void populateViewHolder(viewHolder viewHolder, Book book, int position) {

                String status = book.getStatus();
                if(status.equals("done"))
                {
                    viewHolder.linearLayout.setVisibility(View.GONE);
                }
                else
                {
                    viewHolder.setTotalPages(String.valueOf(book.getPages()));
                    viewHolder.setFinishedPages(String.valueOf(book.getProgress_page()));
                    viewHolder.setTitle(book.getTitle());
                    viewHolder.setAuthor(book.getAuthor());
                    viewHolder.setReadingDays(book.getDisplay_reading_days());
                    viewHolder.setReadingTime(book.getDisplay_reading_time());
                    viewHolder.setDonutProgress(book.getProgress_page(),book.getPages());
                    viewHolder.getKey(book.getId());
//                    viewHolder.test();
                }


            }
        };
        recyclerViewHome.setAdapter(recyclerAdapter);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                loading.setVisibility(View.GONE);
                getDataTarget(dataSnapshot);
                getListDataBooksReadNow(dataSnapshot, uid, database);


                userRef.child("books").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snap: dataSnapshot.getChildren()) {
                            if(snap.getChildrenCount()==0)
                            {
                                no_data.setVisibility(View.VISIBLE);
                                Log.d("no_data","count 0");

                            }
                            else
                            {
                                Log.d("no_data","count more");
                            }

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        return v;
    }



    public static class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View mView;
        LinearLayout linearLayout;
        TextView tv_nowReadBook_bookTitle;
        TextView tv_nowReadBook_bookAuthor;
        TextView tv_readingDays;
        TextView tv_readingTime;
        TextView tv_totalPages;
        EditText et_finishedPages;
        DonutProgress donutProgress;
        Button updateProgressButton;
        private FirebaseAuth mAuth;
        private String totalPages;
        private int currentPages;
        public String akey;


        public viewHolder(View itemView) {
            super(itemView);
            mAuth = FirebaseAuth.getInstance();
            mView=itemView;
            tv_totalPages = (TextView)itemView.findViewById(R.id.readingProgress_totalPages);
            et_finishedPages = (EditText)itemView.findViewById(R.id.readingProgress_finishedPages);
            linearLayout = (LinearLayout)itemView.findViewById(R.id.layout_current_home);
            tv_nowReadBook_bookTitle = (TextView)itemView.findViewById(R.id.nowReadBook_bookTitle);
            tv_nowReadBook_bookAuthor = (TextView) itemView.findViewById(R.id.nowReadBook_bookAuthor);
            tv_readingDays = (TextView) itemView.findViewById(R.id.nowReadBook_readingDay);
            tv_readingTime = (TextView) itemView.findViewById(R.id.nowReadBook_readingHour);
            donutProgress = (DonutProgress) itemView.findViewById(R.id.page_progress);
            updateProgressButton = (Button) itemView.findViewById(R.id.updateProgressButton);
            updateProgressButton.setOnClickListener( new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    String uid = user.getUid();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference insert = database.getReference("users/"+uid+"/books/list/"+akey);
                    insert.child("progress_page").setValue(Integer.parseInt(et_finishedPages.getText().toString()));
//                    insert.child("pages").setValue(totalPages);
                    if(et_finishedPages.getText().toString().equals(totalPages))
                    {
                        insert.child("status").setValue("done");
//                        int finished = books_;
                    }
                }
            });

        }

        public void setTitle(String title)
        {
            tv_nowReadBook_bookTitle.setText(title);
        }

        public void setAuthor(String author)
        {
            tv_nowReadBook_bookAuthor.setText(author);
        }

        public void setReadingDays(String readingDays)
        {
            tv_readingDays.setText(readingDays);
        }

        public void getKey(String key)
        {
            akey = key;
        }

        public void setTotalPages(String a)
        {
            tv_totalPages.setText(a);
            totalPages = a;
        }

        public void setFinishedPages(String a)
        {
            et_finishedPages.setText(a);
            currentPages = Integer.parseInt(a);
        }

        public void setReadingTime(String readingTime)
        {
            tv_readingTime.setText(readingTime);
        }

        public void setDonutProgress(int min, int max)
        {
            donutProgress.setMax(max);
            int donutPercent = (min * 100) / max;
            Log.d("donut perce",""+donutPercent);
            donutProgress.setDonut_progress(String.valueOf(donutPercent));
        }

//        public void test()
//        {
//            donutProgress.setMax(totalPages);
//            int donutPercent = (currentPages * 100) / totalPages;
//            Log.d("donut perce",""+donutPercent);
//            donutProgress.setDonut_progress(String.valueOf(donutPercent));
//        }


//        public void showReadingProgressDialog(View v){
//            Log.d("test pd","dee");
//
//        }

//        @Override
        public void onClick(View v) {

        }
    }


    public void getDataTarget(DataSnapshot dataSnapshot)
    {
        String year_target = dataSnapshot.child("yearTarget").getValue(String.class);
        String book_read = dataSnapshot.child("bookRead").getValue(String.class);
        books_year_target = Integer.parseInt(year_target);
        books_finished = Integer.parseInt(book_read);
        numberOfTargetedBooks.setText(year_target);
        numberOfFinishedBooks.setText(book_read);
    }

    public void getListDataBooksReadNow(DataSnapshot dataSnapshot, String uid, FirebaseDatabase database)
    {

    }



    void startAnim(){
        avi.smoothToShow();
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser==null)
        {
            Intent i = new Intent(getActivity(), LandingPage.class);
            startActivity(i);
        }
    }

}
