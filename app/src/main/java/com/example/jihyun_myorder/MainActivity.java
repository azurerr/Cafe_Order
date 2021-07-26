package com.example.jihyun_myorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.example.jihyun_myorder.database.Order;
import com.example.jihyun_myorder.databinding.ActivityMainBinding;
import com.example.jihyun_myorder.models.OrderItem;
import com.example.jihyun_myorder.viewmodels.OrderViewModel;

import java.util.ArrayList;

// Jihyun Lee, 141859181
public class MainActivity extends AppCompatActivity implements OnClickListener {

    ActivityMainBinding binding;
    private final String TAG = this.getClass().getCanonicalName();
    ArrayList<OrderItem> orderItemArrayList = new ArrayList<>();
    private OrderViewModel orderViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        this.binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        this.binding.btnOrder.setOnClickListener(this);

        // Spinner for Size
        Spinner spinner = (Spinner) findViewById(R.id.sp_size);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.size_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Set the size default as Tall
        //int selectPosition= adapter.getPosition("Tall");
        spinner.setSelection(1);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // NumberPicker for Quantity
        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.np_quantity);
        // Set the Quantity values
        numberPicker.setValue(2);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);

        this.orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);

    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            //Log.d(TAG, "Button Clicked!");
            switch (v.getId()) {
                case R.id.btn_order: {
                    if (this.validateData()) {
                        this.saveNewOrder();

                        // Set Alert
                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                        alertBuilder.setTitle("The coffee was added");
                        alertBuilder.setMessage("Want to add another coffee?");
                        alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                resetOrder();
                            }
                        });
                        alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Move to the Order List page
                                goToOrderList();
                            }
                        });
                        alertBuilder.show();
                    } else {
                        // If coffee type is not selected
                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                        alertBuilder.setMessage("Please select Coffee type");
                        alertBuilder.show();
                    }
                }
            }
        }
    }


    private void saveNewOrder() {

        Order newOrder = new Order();

        //Log.d(TAG, "saveNewOrder was called!");
        //set Coffee Type (in radio button group)
        RadioButton selectedCoffeeType = findViewById(this.binding.rdgCoffeeType.getCheckedRadioButtonId());
        newOrder.setCoffeeType(selectedCoffeeType.getText().toString());
        //Log.d(TAG, newOrder.getCoffeeType());

        //set Coffee size (in spinner)
        Spinner spinner = (Spinner) findViewById(R.id.sp_size);
        newOrder.setSize(spinner.getSelectedItem().toString());
        //Log.d(TAG, newOrder.getSize());

        //set Coffee quantity (in number picker)
        newOrder.setQuantity(this.binding.npQuantity.getValue());

        // Save the new Order to database
        this.orderViewModel.insertOrder(newOrder);
        Log.d(TAG, "saveNewOrder: Item inserted" + newOrder.toString());

        //this.orderArrayList.add(newOrder);
        //Log.d(TAG, "saveNewOrder: NewOrder " + newOrder.toString());
        //Log.d(TAG, "saveNewOrder: OrderArray " + orderItemArrayList);
        
    }

    private boolean validateData() {
        boolean validOrder = true;
        RadioButton selectedCoffeeType = findViewById(this.binding.rdgCoffeeType.getCheckedRadioButtonId());
        if (this.binding.rdgCoffeeType.getCheckedRadioButtonId() == -1){
            validOrder = false;
        }
        return validOrder;
    }

    private void resetOrder() {

        RadioButton selectedCoffeeType = findViewById(this.binding.rdgCoffeeType.getCheckedRadioButtonId());
        selectedCoffeeType.setChecked(false);

        // Spinner for Size
        Spinner spinner = (Spinner) findViewById(R.id.sp_size);
        spinner.setSelection(1);

        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.np_quantity);
        numberPicker.setValue(1);

    }

    private void goToOrderList() {
        // create Intent obj
        Intent orderListIntent = new Intent(this, OrderListActivity.class);

        //set Extra data
        //orderListIntent.putParcelableArrayListExtra("EXTRA_ORDER_LIST", this.orderItemArrayList);
        //orderListIntent.putExtra("EXTRA_ORDER_LIST", this.orderArrayList);

        // start a new activity using the defined Intent obj
        startActivity(orderListIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_order_list:{
                this.goToOrderList();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}