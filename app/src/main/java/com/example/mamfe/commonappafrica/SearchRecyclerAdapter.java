package com.example.mamfe.commonappafrica;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Myo on 6/13/2017.
 * SearchRecyclerAdapter is for setting up the search bar in the main menu.
 * The lists are called, and when the item is clicked, the information on the item is transferred to the next page.
 */

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder>
        implements Filterable {
    private final Context context;
    private ArrayList<String> mOriginalValues;
    private ArrayList<String> mDisplayedValues;
    private int check;
    /**
     * Method SearchRecyclerAdapter, onCreateViewHolder
     * In the recycler, these methods add the college list to the context.
     * This enables the user to search the colleges.
     */
    public SearchRecyclerAdapter(Context context, int check) {
        this.context = context;
        this.mOriginalValues = new ArrayList<>();
        this.mDisplayedValues = new ArrayList<>();
        if (Model.collegeList.size() == 0)
        Model.populateStockList(context);
        this.check = check;
        if (check == 1) mOriginalValues = Model.myColleges;
        else mOriginalValues = Model.collegeList;
        createDisplayedValue(mOriginalValues);
        notifyDataSetChanged();
    }
    
    @Override
    public SearchRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_search, parent, false);
        if (check == 1)
            itemView =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_college, parent, false);
        return new ViewHolder(itemView);
    }
    /**
     * Method onBindViewHolder
     * Gets the college name of the selected item and bind it on to the holder 
     * @param    holder    ViewHolder
     */
    @Override
    public void onBindViewHolder(SearchRecyclerAdapter.ViewHolder holder, int position) {
        String item = mDisplayedValues.get(position);
        holder.itemName.setText(item);
    }
    
    /**
     * Method getItemCount
     * @return int total count of college names displayed
     */
    @Override
    public int getItemCount() {
        return mDisplayedValues.size();
    }


    /**
     * Method getFilter
     * Do the filtering based on user's search input
     *
     * @return Filter the filtered result
     */
    public Filter getFilter() {
        return new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                mDisplayedValues = (ArrayList<String>) results.values; // has the filtered values
                //Log.e("List", mDisplayedValues+"");
                Collections.sort(mDisplayedValues);
                //Log.e("List", mDisplayedValues+"");
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results =
                        new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<String> FilteredArrList = new ArrayList<>();

                if (mOriginalValues == null) {
                    mOriginalValues =
                            new ArrayList<>(mDisplayedValues); // saves the original data in mOriginalValues
                }

        /*
         *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
         *  else does the Filtering and returns FilteredArrList(Filtered)
         */
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = mOriginalValues.size();
                    results.values = mOriginalValues;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < mOriginalValues.size(); i++) {
                        String data = mOriginalValues.get(i);
                        if (data.toLowerCase().contains(constraint.toString())) {
                            FilteredArrList.add(mOriginalValues.get(i));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
    }


    private void createDisplayedValue(ArrayList<String> collegeList) {
        mDisplayedValues = collegeList;
        Collections.sort(mDisplayedValues);
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.collegeName)
        TextView itemName;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.searchRowItemContainer)
        void onClick() {
            FragmentManager manager = ((Activity) context).getFragmentManager();

            int position = getAdapterPosition();
            Model.selected = mDisplayedValues.get(position);
            AppCompatActivity activity = (AppCompatActivity) context;
            Model.websiteSelected = Model.websiteList.get(position);
            Model.rankingSelected = Model.rankingList.get(position);

            Model.rateSelected = Model.rateList.get(position);
            Model.deadlineSelected = Model.deadlineList.get(position);
            Model.totalSelected = Model.totalList.get(position);
            Model.tuitionSelected = Model.tuitionList.get(position);

            Fragment applyFragment = new ApplyFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.frame_container, applyFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        }

    }
}
