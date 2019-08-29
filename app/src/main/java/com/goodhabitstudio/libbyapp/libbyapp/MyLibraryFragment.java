package com.goodhabitstudio.libbyapp.libbyapp;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;

/**
 * Created by dondi-mac on 15/11/17.
 */

public class MyLibraryFragment extends Fragment {

    private FirebaseAuth mAuth;
    private String uid;
    private RecyclerView recyclerViewMyLibraryInProgress;
    private RecyclerView recyclerViewMyLibraryHistory;


    public static MyLibraryFragment newInstance() {
        MyLibraryFragment fragment = new MyLibraryFragment();
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
        View v = inflater.inflate(R.layout.fragment_mylibrary, container, false);

        //Get Data
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        uid = user.getUid();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("users/"+uid);
        DatabaseReference bookRef = database.getReference("users/"+uid+"/books/list");

        //FIREBASE 1
        recyclerViewMyLibraryInProgress =(RecyclerView) v.findViewById(R.id.recyclerview_in_progress);
        recyclerViewMyLibraryInProgress.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseRecyclerAdapter<Book,viewHolderInProgress> recyclerAdapter1=new FirebaseRecyclerAdapter<Book,viewHolderInProgress>(
                Book.class,
                R.layout.mylibrary_current_book,
                viewHolderInProgress.class,
                bookRef
        ) {
            @Override
            protected void populateViewHolder(viewHolderInProgress viewHolderInProgress, Book book, int position) {
                String status = book.getStatus();
                Log.d("test statuss inprogress",status);
                if(status.equals("done"))
                {
                    Log.d("test status","done");
                    viewHolderInProgress.linearLayout.setVisibility(View.GONE);
                }
                else
                {
                    Log.d("test status","not done");
                    viewHolderInProgress.setNumberOfFinishedPage(book.getProgress_page());
                    viewHolderInProgress.setTotalPages(book.getPages());
                    viewHolderInProgress.setCurrentBook_bookAuthor(book.getAuthor());
                    viewHolderInProgress.setCurrentBook_bookTitle(book.getTitle());
                    viewHolderInProgress.setCurrentBook_topLabel2("21");
                }

            }
        };
        recyclerViewMyLibraryInProgress.setAdapter(recyclerAdapter1);

        //FIREBASE 2
        recyclerViewMyLibraryHistory =(RecyclerView) v.findViewById(R.id.recyclerview_history);
        recyclerViewMyLibraryHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseRecyclerAdapter<Book,viewHolderHistory> recyclerAdapter2=new FirebaseRecyclerAdapter<Book,viewHolderHistory>(
                Book.class,
                R.layout.mylibrary_finished_book,
                viewHolderHistory.class,
                bookRef
        ) {

            public void onDataChanged() {
                // Called each time there is a new data snapshot. You may want to use this method
                // to hide a loading spinner or check for the "no documents" state and update your UI.
                // ...
            }

            @Override
            protected void populateViewHolder(viewHolderHistory viewHolderHistory, Book book, int position) {
                String status = book.getStatus();
                if(status.equals("done"))
                {
                    Log.d("test status","done");
                    viewHolderHistory.setHistoryBook_topLabel2("25");
                    viewHolderHistory.setHistoryBook_bookTitle(book.getTitle());
                    viewHolderHistory.setHistoryBook_bookAuthor(book.getAuthor());
                }
                else
                {
                    Log.d("test status","not done");
                    viewHolderHistory.linearLayout.setVisibility(View.GONE);
                }
            }
        };
        recyclerViewMyLibraryHistory.setAdapter(recyclerAdapter2);

        return v;
    }

    public static class viewHolderInProgress extends RecyclerView.ViewHolder {
        View mView;
        LinearLayout linearLayout;
        TextView tv_numberOfFinishedPage;
        TextView tv_totalPages;
        TextView tv_currentBook_bookTitle;
        TextView tv_currentBook_bookAuthor;
        TextView tv_currentBook_topLabel2;

        public viewHolderInProgress(View itemView) {
            super(itemView);
            mView = itemView;
            linearLayout = (LinearLayout) itemView.findViewById(R.id.layout_current);
            tv_numberOfFinishedPage = (TextView) itemView.findViewById(R.id.numberOfFinishedPage);
            tv_totalPages = (TextView) itemView.findViewById(R.id.totalPages);
            tv_currentBook_bookTitle = (TextView) itemView.findViewById(R.id.currentBook_bookTitle);
            tv_currentBook_bookAuthor = (TextView) itemView.findViewById(R.id.currentBook_bookAuthor);
            tv_currentBook_topLabel2 = (TextView) itemView.findViewById(R.id.currentBook_topLabel2);

        }

        public void setNumberOfFinishedPage(int numberOfFinishedPage) {
            tv_numberOfFinishedPage.setText(String.valueOf(numberOfFinishedPage));
        }

        public void setTotalPages(int totalPages) {
            tv_totalPages.setText(String.valueOf(totalPages));
        }

        public void setCurrentBook_bookTitle(String currentBook_bookTitle) {
            tv_currentBook_bookTitle.setText(currentBook_bookTitle);
        }

        public void setCurrentBook_bookAuthor(String currentBook_bookAuthor) {
            tv_currentBook_bookAuthor.setText(currentBook_bookAuthor);
        }

        public void setCurrentBook_topLabel2(String currentBook_topLabel2) {
            tv_currentBook_topLabel2.setText(currentBook_topLabel2);
        }
    }

    public static class viewHolderHistory extends RecyclerView.ViewHolder {
        View mView;
        LinearLayout linearLayout;
        TextView tv_historyBook_topLabel2;
        TextView tv_historyBook_bookTitle;
        TextView tv_historyBook_bookAuthor;

        public viewHolderHistory(View itemView) {
            super(itemView);
            mView = itemView;
            linearLayout = (LinearLayout) itemView.findViewById(R.id.layout_history);
            tv_historyBook_topLabel2 = (TextView) itemView.findViewById(R.id.historyBook_topLabel2);
            tv_historyBook_bookTitle = (TextView) itemView.findViewById(R.id.historyBook_bookTitle);
            tv_historyBook_bookAuthor = (TextView) itemView.findViewById(R.id.historyBook_bookAuthor);
        }

        public void setHistoryBook_topLabel2(String historyBook_topLabel2) {
            tv_historyBook_topLabel2.setText(historyBook_topLabel2);
        }

        public void setHistoryBook_bookTitle(String historyBook_bookTitle) {
            tv_historyBook_bookTitle.setText(historyBook_bookTitle);
        }

        public void setHistoryBook_bookAuthor(String historyBook_bookAuthor) {
            tv_historyBook_bookAuthor.setText(historyBook_bookAuthor);
        }

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
