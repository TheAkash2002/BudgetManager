package com.princeakash.budgetmanager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.princeakash.budgetmanager.DatabaseHelper.DateToString;

public class MyAdapter extends RecyclerView.Adapter{

    List<ListItem> listItems = new ArrayList<>();

    public MyAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    private Context context;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case ListItem.TARGET_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.target_item, parent, false);
                return new MyTargetViewHolder(view);
            case ListItem.NON_TARGET_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
                return new MyViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ListItem listItem = listItems.get(position);
        switch(listItem.getType()){
            case ListItem.NON_TARGET_TYPE:
                ((MyViewHolder) holder).textViewAmount.setText(listItem.getAmount());
                ((MyViewHolder) holder).textViewDate.setText(listItem.getDate());
                ((MyViewHolder) holder).textViewCategory.setText(listItem.getCategory());
                ((MyViewHolder) holder).cardView.setCardBackgroundColor(setColor(position));
                break;
            case ListItem.TARGET_TYPE:
                ((MyTargetViewHolder) holder).textViewAmount.setText(listItem.getAmount());
                String[] splitText = listItem.getDate().split("-");
                ((MyTargetViewHolder) holder).textViewDate.setText(DatabaseHelper.DateToString(splitText[0], splitText[1]));
                ((MyTargetViewHolder) holder).cardView.setCardBackgroundColor(setColor(position));
                break;
        }
    }

    /*@Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ListItem listItem = listItems.get(position);
        holder.textViewAmount.setText(listItem.getAmount());
        holder.textViewDate.setText(listItem.getDate());
        holder.textViewCategory.setText(listItem.getCategory());
        holder.cardView.setCardBackgroundColor(setColor(position));
    }*/

    @Override
    public int getItemViewType(int position) {
        switch(listItems.get(position).getType()){
            case ListItem.TARGET_TYPE:
                return ListItem.TARGET_TYPE;
            case ListItem.NON_TARGET_TYPE:
                return ListItem.NON_TARGET_TYPE;
            default:
                return -1;
        }
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewCategory, textViewAmount, textViewDate;
        public CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewAmount = itemView.findViewById(R.id.textViewAmount);
            textViewCategory = itemView.findViewById(R.id.textViewCategory);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            cardView = itemView.findViewById(R.id.cardView);
        }

    }

    public class MyTargetViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewAmount, textViewDate;
        public CardView cardView;

        public MyTargetViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewAmount = itemView.findViewById(R.id.textViewAmount);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

    public int setColor(int position){
        switch(position%5){
            case 0:
                return 0xff42f55a;
            case 1:
                return 0xfff5ce42;
            case 2:
                return 0xfff58742;
            case 3:
                return 0xff42c8f5;
            case 4:
                return 0xffef42f5;
        }
        return 0xff000000;
    }
}
