package com.example.coronatracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.coronatracker.Models.Country;

import java.util.ArrayList;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {

    public static ItemClickListener ItemClickListener;
    private ArrayList<Country> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private ViewGroup parent;
    private int viewType;
    //callback interface for user actions on each item
    private ICountryItemClickedListener listener;

    //interface for handling when and Person item is clicked in various ways
    public interface ICountryItemClickedListener{
        void onCountryClicked(int index);
    }

    // data is passed into the constructor
    public FileAdapter(Context context, ArrayList data, ICountryItemClickedListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.listener = listener;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view, listener);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //String imageId = String.valueOf(Integer.parseInt((mData.get(position).imageResourceId)));
        holder.txtviewcntryname.setText(mData.get(position).name);
        //holder.imageviewcountry.setImageResource(Integer.parseInt((imageId)));
        holder.txtviewrating.setText("Rating: " + mData.get(position).rating);
        holder.txtviewcases.setText("Cases:" + mData.get(position).cases);
        holder.txtviewdeaths.setText("Deaths: " + mData.get(position).deaths);
        //int flags = Integer.parseInt(mData.get(position).imageResourceId.toLowerCase());
        //getflags i = new getflags(flags);
        //holder.imageviewcountry.setImageResource(i);

        /*
        switch (imageId){
            case "ca":
            holder.imageviewcountry.setImageResource(R.drawable.ca);
            break;
            case "cn":
            holder.imageviewcountry.setImageResource(R.drawable.cn);
            case "de":
                holder.imageviewcountry.setImageResource(R.drawable.de);
            case "dk":
                holder.imageviewcountry.setImageResource(R.drawable.dk);
            case "fi":
                holder.imageviewcountry.setImageResource(R.drawable.fi);
            case "in":
                holder.imageviewcountry.setImageResource(R.drawable.in);
            case "jp":
                holder.imageviewcountry.setImageResource(R.drawable.jp);
            case "no":
                holder.imageviewcountry.setImageResource(R.drawable.no);
            case "ru":
                holder.imageviewcountry.setImageResource(R.drawable.ru);
            case "se":
                holder.imageviewcountry.setImageResource(R.drawable.se);
            case "us":
                holder.imageviewcountry.setImageResource(R.drawable.us);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + imageId);
        }
            */


    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    //a method for updating the list - causes the adapter/recyclerview to update
    public void updateList(ArrayList<Country> lists){
        mData = lists;
        notifyDataSetChanged();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtviewcases, txtviewrating, txtviewcntryname, txtviewdeaths;
        ImageView imageviewcountry;


        //custom callback interface for user actions done the view holder item
        ICountryItemClickedListener listener;

        ViewHolder(View itemView, ICountryItemClickedListener countryItemClickedListener) {
            super(itemView);
            txtviewdeaths = itemView.findViewById(R.id.txtviewdeaths);
            txtviewcases = itemView.findViewById(R.id.txtviewcases);
            txtviewrating = itemView.findViewById(R.id.txtviewuserrating);
            imageviewcountry = itemView.findViewById(R.id.imageView);
            txtviewcntryname = itemView.findViewById(R.id.textviewcountry);
            listener = countryItemClickedListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onCountryClicked(getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    Object getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);

    }
}