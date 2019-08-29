//package com.goodhabitstudio.libbyapp.libbyapp;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by dondi-mac on 16/11/17.
// */
//
//public class BookCardAdapter extends RecyclerView.Adapter {
//
////    Context c;
////    ArrayList<Book> books;
////    LayoutInflater inflater;
//
//    List listArray;
//
//    @Override
//    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview, parent, false);
//
//        return new MyViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(MyViewHolder holder, int position) {
//        MyDataSetGet data = listArray.get(position);
//
//        holder.MyText.setText(data.gethello());
//
//    }
//
//    public class MyViewHolder extends RecyclerView.ViewHolder {
//        //Ctrl + O
//        TextView MyText;
//
//
//        public MyViewHolder(View itemView) {
//            super(itemView);
//            MyText = (TextView)itemView.findViewById(R.id.textView);
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return listArray.size();
//    }
//}
