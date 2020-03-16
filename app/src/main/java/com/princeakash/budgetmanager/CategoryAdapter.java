package com.princeakash.budgetmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {
    List<String> categories;
    Context context;
    OnCategoryListener onCategoryListener;

    public CategoryAdapter(List<String> categories, Context context, OnCategoryListener listener) {
        this.categories = categories;
        this.context = context;
        this.onCategoryListener = listener;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_item, parent, false);
        return new CategoryHolder(view, onCategoryListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        holder.textViewCategory.setText(categories.get(position));
        holder.cardViewCategory.setCardBackgroundColor(MyAdapter.setColor(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        TextView textViewCategory;
        CardView cardViewCategory;
        OnCategoryListener onCategoryListener;

        public CategoryHolder(@NonNull View itemView, OnCategoryListener listener) {
            super(itemView);
            textViewCategory = itemView.findViewById(R.id.textViewCategory);
            cardViewCategory = itemView.findViewById(R.id.cardViewCategory);
            this.onCategoryListener = listener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCategoryListener.onCategoryClick(getAdapterPosition());
        }


        @Override
        public boolean onLongClick(View v) {
            onCategoryListener.onCategoryLongClick(getAdapterPosition());
            return true;
        }
    }

    public interface OnCategoryListener {
        void onCategoryClick(int position);
        void onCategoryLongClick(int position);
    }
}
