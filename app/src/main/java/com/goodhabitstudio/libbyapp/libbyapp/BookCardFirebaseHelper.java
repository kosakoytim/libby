//package com.goodhabitstudio.libbyapp.libbyapp;
//
//import android.content.Context;
//import android.util.Log;
//import android.widget.ListView;
//import android.widget.Toast;
//
//import com.firebase.client.Firebase;
//import com.firebase.client.FirebaseError;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.ChildEventListener;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseException;
//import com.google.firebase.database.DatabaseReference;
//
//import java.util.ArrayList;
//
///**
// * Created by dondi-mac on 16/11/17.
// */
//
//public class BookCardFirebaseHelper {
//
//    Context c;
//    ListView listView;
//    BookCardAdapter bookCardAdapter;
//    Firebase firebase;
//    String DB_URL;
//
//    DatabaseReference db;
////    Boolean saved;
//    ArrayList<Book> books=new ArrayList<>();
//
//
//    public BookCardFirebaseHelper(Context c, String DB_URL, ListView listView) {
//        this.c= c;
//        this.DB_URL = DB_URL;
//        this.listView= listView;
//
//        Firebase.setAndroidContext(c);
//        firebase = new Firebase(DB_URL);
//    }
//
//    public void refreshdata() {
//
//        firebase.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                getupdates(dataSnapshot);
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                getupdates(dataSnapshot);
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });
//    }
//
//
//    public void getupdates(DataSnapshot dataSnapshot){
//
//        books.clear();
//
//        for(DataSnapshot ds :dataSnapshot.getChildren()){
//            Book b= new Book();
//            b.setTitle(ds.getValue(Book.class).getTitle());
//            b.setAuthor(ds.getValue(Book.class).getAuthor());
//            books.add(b);
//
//        }
//        if(books.size()>0)
//        {
//            bookCardAdapter=new BookCardAdapter(c, books);
//            listView.setAdapter((BookCardAdapter) bookCardAdapter);
//        }else
//        {
//            Toast.makeText(c, "No data", Toast.LENGTH_SHORT).show();
//        }
//    }
////    public BookCardFirebaseHelper(DatabaseReference db) {
////        this.db = db;
////    }
////    //WRITE IF NOT NULL
////    public Boolean save(Book book)
////    {
////        mAuth = FirebaseAuth.getInstance();
////        FirebaseUser user = mAuth.getCurrentUser();
////        uid = user.getUid();
////
////        if(book==null)
////        {
////            saved=false;
////        }else
////        {
////            try
////            {
////                db.child("Books").push().setValue(book);
////                saved=true;
////            }catch (DatabaseException e)
////            {
////                e.printStackTrace();
////                saved=false;
////            }
////        }
////        return saved;
////    }
//
////    //IMPLEMENT FETCH DATA AND FILL ARRAYLIST
////    private void fetchData(DataSnapshot dataSnapshot)
////    {
////        Log.d("test adapter ","Fetch Data");
////        books.clear();
////        for (DataSnapshot ds : dataSnapshot.getChildren())
////        {
////            Log.d("test adapter ","get children fetch data");
////            Log.d("test adapter ",ds.getKey());
////
////            Book book=ds.getValue(Book.class);
////            books.add(book);
////        }
////    }
////    //RETRIEVE
////    public ArrayList<Book> retrieve()
////    {
////        Log.d("test adapter ","ArrayList");
////        db.child("books").addChildEventListener(new ChildEventListener() {
////            @Override
////            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
////                fetchData(dataSnapshot);
////                Log.d("test adapter ", "on child added");
////            }
////            @Override
////            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
////                fetchData(dataSnapshot);
////                Log.d("test adapter ", "on child changed");
////            }
////            @Override
////            public void onChildRemoved(DataSnapshot dataSnapshot) {
////            }
////            @Override
////            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
////            }
////            @Override
////            public void onCancelled(DatabaseError databaseError) {
////            }
////        });
////        return books;
////    }
//}
