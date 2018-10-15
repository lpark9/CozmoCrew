package com.example.mamfe.commonappafrica;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;


public class SearchFragment extends Fragment {
    @BindView(R.id.searchResult) RecyclerView result;
    @BindView(R.id.searchView)
    EditText searchView;
    SearchRecyclerAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);


        result.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        result.setLayoutManager(mLayoutManager);
        adapter = new SearchRecyclerAdapter(getContext(), 0);
        result.setAdapter(adapter);
        return view;
    }


    @OnTextChanged(R.id.searchView) void onTextChange(CharSequence s) {
        adapter.getFilter().filter(s.toString());
    }


}