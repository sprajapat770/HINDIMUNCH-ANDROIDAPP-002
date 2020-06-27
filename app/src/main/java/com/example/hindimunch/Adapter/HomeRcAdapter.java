package com.example.hindimunch.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hindimunch.Interface.OnItemClickListner;
import com.example.hindimunch.Model.DataObject;
import com.example.hindimunch.R;

import java.util.ArrayList;
import java.util.List;

public class HomeRcAdapter extends RecyclerView.Adapter<HomeRcAdapter.MyViewHolder> {
    Context context;
    List<DataObject> dataObjectsList = new ArrayList<>();
    OnItemClickListner onItemClickListner;

    public HomeRcAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<DataObject> dataObjectsList) {
        this.dataObjectsList.clear();
        this.dataObjectsList.addAll(dataObjectsList);
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_home_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DataObject dataObject = dataObjectsList.get(position);
        holder.btnText.setText(dataObject.name);
        holder.imgView.setImageResource(dataObject.id);

    }

    @Override
    public int getItemCount() {
        return dataObjectsList.size();
    }

    public DataObject getData(int position) {
        return dataObjectsList.get(position);
    }

    public void setOnItemClickListner(OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Button btnText;
        ImageView imgView;

        public MyViewHolder(View itemView) {
            super(itemView);
            btnText = itemView.findViewById(R.id.btnText);
            imgView = itemView.findViewById(R.id.imgView);

        }
    }
}
