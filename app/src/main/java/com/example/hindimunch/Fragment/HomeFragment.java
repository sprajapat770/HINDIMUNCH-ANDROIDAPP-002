package com.example.hindimunch.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.hindimunch.Adapter.HomeRcAdapter;
import com.example.hindimunch.Model.DataObject;
import com.example.hindimunch.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    RecyclerView rcylrHome;
    List<DataObject> dataObjectList = new ArrayList<>();
    HomeRcAdapter homeRcAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        rcylrHome = view.findViewById(R.id.rcylrHome);
        LinearLayout lLayout = view.findViewById(R.id.llContainer); // Root ViewGroup in which you want to add textviews
        homeRcAdapter = new HomeRcAdapter(getActivity());
        prepareRecycler(rcylrHome,0);
        for (int i = 1; i < 4; i++) {
            RecyclerView rv = new RecyclerView(getActivity()); // Prepare textview object programmatically
            prepareRecycler(rv, i);
            lLayout.addView(rv); // Add to your ViewGroup using this method
        }

        /*
        homeRcAdapter = new HomeRcAdapter(getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        rcylrHome.setLayoutManager(layoutManager);
        rcylrHome.setAdapter(homeRcAdapter);
        homeRcAdapter.setData(prepareData());*/
    }

    public void prepareRecycler(RecyclerView recyclerView, int data) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(homeRcAdapter);
        homeRcAdapter.setData(prepareData(data));
    }

    private List<DataObject> prepareData(int data) {
        dataObjectList.add(new DataObject(R.drawable.img_quote, "Quote"));
        dataObjectList.add(new DataObject(R.drawable.img_quote, "Quote"));
        dataObjectList.add(new DataObject(R.drawable.img_quote, "Quote"));
        dataObjectList.add(new DataObject(R.drawable.img_quote, "Quote"));
        dataObjectList.add(new DataObject(R.drawable.img_quote, "Quote"));
        dataObjectList.add(new DataObject(R.drawable.img_quote, "Quote"));
        dataObjectList.add(new DataObject(R.drawable.img_quote, "Quote"));
        dataObjectList.add(new DataObject(R.drawable.img_quote, "Quote"));
        return dataObjectList;
    }
}