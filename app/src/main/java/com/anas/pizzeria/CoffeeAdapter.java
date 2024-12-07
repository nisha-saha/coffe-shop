package com.anas.pizzeria;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CoffeeAdapter extends RecyclerView.Adapter<CoffeeAdapter.coffeeViewHolder> {

    private final List<Coffee> coffeeList;
    private final coffeeClickListener coffeeClickListener;

    public interface coffeeClickListener {
        void onPlusClick(int position);
        void onMinusClick(int position);
    }

    public CoffeeAdapter(List<Coffee> coffeeList, coffeeClickListener coffeeClickListener) {
        this.coffeeList = coffeeList;
        this.coffeeClickListener = coffeeClickListener;
    }

    @NonNull
    @Override
    public coffeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coffee, parent, false);
        return new coffeeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull coffeeViewHolder holder, int position) {
        Coffee coffee = coffeeList.get(position);

        holder.coffeeImageView.setImageResource(R.drawable.coffee_icon);
        holder.nameTextView.setText(coffee.getName());
        holder.priceTextView.setText(coffee.getPrice());
        holder.quantityTextView.setText(String.valueOf(coffee.getQuantity()));

        holder.plusImageView.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                coffeeClickListener.onPlusClick(adapterPosition);
            }
        });

        holder.minusImageView.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                coffeeClickListener.onMinusClick(adapterPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return coffeeList.size();
    }

    public static class coffeeViewHolder extends RecyclerView.ViewHolder {
        ImageView coffeeImageView;
        TextView nameTextView;
        TextView priceTextView;
        TextView quantityTextView;
        ImageView plusImageView;
        ImageView minusImageView;

        public coffeeViewHolder(@NonNull View itemView) {
            super(itemView);
            coffeeImageView = itemView.findViewById(R.id.coffeeImageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);
            plusImageView = itemView.findViewById(R.id.plusImageView);
            minusImageView = itemView.findViewById(R.id.minusImageView);
        }
    }
}

