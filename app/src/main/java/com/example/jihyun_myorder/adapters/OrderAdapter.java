package com.example.jihyun_myorder.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jihyun_myorder.databinding.RvItemOrderBinding;
import com.example.jihyun_myorder.models.OrderItem;

import java.util.ArrayList;

// Jihyun Lee, 141859181
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private final String TAG = "Order Adapter";
    private final Context context;
    private final ArrayList<OrderItem> orderItemList;
    private final OnOrderItemClickListener itemClickListener;

    public OrderAdapter(Context context, ArrayList<OrderItem> orderItemList, OnOrderItemClickListener itemClickListener) {
        this.context = context;
        this.orderItemList = orderItemList;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderViewHolder(RvItemOrderBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        final OrderItem currentOrderItem = orderItemList.get(position);
        holder.bind(context, currentOrderItem, itemClickListener);
    }

    @Override
    public int getItemCount() {
        return this.orderItemList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        RvItemOrderBinding binding;

        public OrderViewHolder(RvItemOrderBinding b) {
            super(b.getRoot());
            binding = b;
        }

        public void bind(Context context, final OrderItem currentOrderItem, final OnOrderItemClickListener clickListener) {
            binding.coffeeType.setText("Coffee Type: " + currentOrderItem.getCoffeeType());
            binding.coffeeSize.setText("Size: " + currentOrderItem.getSize());
            binding.coffeeQuantity.setText("Quantity: " + currentOrderItem.getQuantity());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onOrderItemClicked(currentOrderItem);
                }
            });

            /*if (currentOrder.getCoffeeType().equals("Americano")) {
                binding.imgResult.setImageDrawable(ContextCompat.getDrawable(context,android.R.drawable.americano)));
            }
            if (currentOrder.getCoffeeType().equals("Flat White")) {
                binding.imgResult.setImageDrawable(ContextCompat.getDrawable(context, Integer.parseInt("@drawable/flat_white")));
            }*/

        }

    }
}
;