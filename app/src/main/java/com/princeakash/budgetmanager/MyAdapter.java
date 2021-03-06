package com.princeakash.budgetmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    List<ListItem> listItems;
    MyItemListener myItemListener;

    public MyAdapter(List<ListItem> listItems, Context context, MyItemListener listener) {
        this.listItems = listItems;
        this.context = context;
        this.myItemListener = listener;
    }

    private Context context;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view, myItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ListItem listItem = listItems.get(position);
        String[] splitDate = listItem.getDate().split("-");
        if(!listItem.getCategory().equals("Target")) {
            String date = splitDate[2] + " " + DatabaseHelper.DateToString(splitDate[0], splitDate[1]);
            holder.textViewDate.setText(date);
            holder.textViewCategory.setText(listItem.getCategory());

        }
        else{
            holder.textViewDate.setText(DatabaseHelper.DateToString(splitDate[0], splitDate[1]));
            holder.textViewCategory.setVisibility(View.GONE);
        }
        holder.textViewAmount.setText(listItem.getAmount());
        holder.cardView.setCardBackgroundColor(setColor(position));
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public TextView textViewCategory, textViewAmount, textViewDate;
        public CardView cardView;
        public MyItemListener myItemListener;

        public MyViewHolder(@NonNull View itemView, MyItemListener listener) {
            super(itemView);

            textViewAmount = itemView.findViewById(R.id.textViewAmount);
            textViewCategory = itemView.findViewById(R.id.textViewCategory);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            cardView = itemView.findViewById(R.id.cardView);
            this.myItemListener = listener;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myItemListener.onItemClickListener(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            myItemListener.onItemLongClickListener(getAdapterPosition());
            return false;
        }
    }

    public static int setColor(int position){
        switch(position%5){
            case 0:
                return 0xffb8b649;
            case 1:
                return 0xffb2f536;
            case 2:
                return 0xfff58742;
            case 3:
                return 0xff9e907e;
            case 4:
                return 0xffeb94e2;
        }
        return 0xff000000;
    }

    public interface MyItemListener{
        void onItemClickListener(int position);
        void onItemLongClickListener(int position);
    }
}
