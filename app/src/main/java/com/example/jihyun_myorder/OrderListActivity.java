package com.example.jihyun_myorder;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jihyun_myorder.adapters.OnOrderItemClickListener;
import com.example.jihyun_myorder.adapters.OrderAdapter;
import com.example.jihyun_myorder.database.Order;
import com.example.jihyun_myorder.databinding.ActivityOrderListBinding;
import com.example.jihyun_myorder.models.OrderItem;
import com.example.jihyun_myorder.viewmodels.OrderViewModel;

import java.util.ArrayList;
import java.util.List;

// Jihyun Lee, 141859181
public class OrderListActivity extends AppCompatActivity implements OnOrderItemClickListener {

    private final String TAG = this.getClass().getCanonicalName();
    private ArrayList<OrderItem> orderItemArrayList;
    private ActivityOrderListBinding binding;
    private OrderAdapter orderAdapter;
    private OrderViewModel orderViewModel;
    private OrderItem tempOrderItem;
    private ItemTouchHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_order_list);
        this.binding = ActivityOrderListBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        this.helper = new ItemTouchHelper(simpleCallback);
        this.helper.attachToRecyclerView(this.binding.rvOrderList);

        this.orderItemArrayList = new ArrayList<>();
        this.orderAdapter = new OrderAdapter(this, this.orderItemArrayList, this);
        this.binding.rvOrderList.setAdapter(orderAdapter);
        this.binding.rvOrderList.setLayoutManager(new LinearLayoutManager(this));
        this.binding.rvOrderList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        this.orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);

        //Get all orders from DB
        this.orderViewModel.getAllOrders().observe(this, new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orderList) {
                if (!orderList.isEmpty()) {
                    Log.e(TAG, "onChanged: orders received from DB" + orderList.toString());
                    orderItemArrayList.clear();

                    for (Order tempOrder : orderList) {
                        tempOrderItem = new OrderItem();
                        tempOrderItem.setOrderID(tempOrder.getOrderID());
                        tempOrderItem.setCoffeeType(tempOrder.getCoffeeType());
                        tempOrderItem.setSize(tempOrder.getSize());
                        tempOrderItem.setQuantity(tempOrder.getQuantity());

                        orderItemArrayList.add(tempOrderItem);
                    }
                    orderAdapter.notifyDataSetChanged();

                } else {
                    Log.e(TAG, "onChanged: empty order list");
                }
            }
        });
    }

    @Override
    public void onOrderItemClicked(OrderItem orderItem) {
        Log.e(TAG, "onOrderItemClicked: Update the order quantity");

        Order selectedOrder = new Order();
        selectedOrder.setOrderID(orderItem.getOrderID());
        selectedOrder.setCoffeeType(orderItem.getCoffeeType());
        selectedOrder.setQuantity(orderItem.getQuantity());
        selectedOrder.setSize(orderItem.getSize());

        AlertDialog.Builder updateAlert = new AlertDialog.Builder(this);
        updateAlert.setTitle("Update quantity");
        updateAlert.setMessage("Provide the updated quantity for this order");

        //Call a separate custom Alert View to get an integer value from the EditText
        final View customAlert = getLayoutInflater().inflate(R.layout.custom_alert, null);
        EditText editText = customAlert.findViewById(R.id.edit_quantity);
        //editText.setText(selectedOrder.getQuantity());
        updateAlert.setView(customAlert);

        updateAlert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                //EditText editText = customAlert.findViewById(R.id.edit_quantity);
                //Update the order in DB
                if (editText.getText() != null) {

                    selectedOrder.setQuantity(Integer.parseInt(editText.getText().toString()));
                    orderViewModel.updateOrder(selectedOrder);
                    orderAdapter.notifyDataSetChanged();

                    Toast.makeText(getApplicationContext(),
                            selectedOrder.getCoffeeType() + " Quantity has been updated ",
                            Toast.LENGTH_LONG).show();
                } else {
                    selectedOrder.setQuantity(0);
                    editText.setError("Quantity cannot be empty");
                }
            }
        });

        updateAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        updateAlert.show();

    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            if (direction == ItemTouchHelper.LEFT) {
                deleteOrder(viewHolder.getAdapterPosition());
            }
        }
    };

    private void deleteOrder(int position) {
        OrderItem selectedOrder = orderItemArrayList.get(position);

        // Ask for confirmation
        AlertDialog.Builder deleteAlert = new AlertDialog.Builder(this);
        deleteAlert.setTitle("Delete Order");
        deleteAlert.setMessage("Are you sure to remove " + selectedOrder.getQuantity() + " cup(s) of " + selectedOrder.getCoffeeType() + "?");

        deleteAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                //Delete the order in DB
                Order orderToDelete = new Order();
                orderToDelete.setOrderID(selectedOrder.getOrderID());
                orderToDelete.setCoffeeType(selectedOrder.getCoffeeType());
                orderToDelete.setSize(selectedOrder.getSize());
                orderToDelete.setQuantity(selectedOrder.getQuantity());

                orderViewModel.deleteOrder(orderToDelete);

                Toast.makeText(getApplicationContext(),
                        " The order for " + selectedOrder.getCoffeeType() + " has been removed",
                        Toast.LENGTH_LONG).show();

            }
        });

        deleteAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                orderAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        deleteAlert.show();

    }

}