package com.example.namaste;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import android.util.Log;

public class OrderHistory extends AppCompatActivity {

    private RecyclerView orderRecyclerView;
    private OrderAdapter orderAdapter;
    private List<Order> orderList = new ArrayList<>();
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        // Initialize RecyclerView
        orderRecyclerView = findViewById(R.id.orderRecyclerView);
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set the Adapter
        orderAdapter = new OrderAdapter(orderList);
        orderRecyclerView.setAdapter(orderAdapter);

        // Firebase Realtime Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("shipments");

        // Fetch order data
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                orderList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d("OrderHistory", "Data Snapshot: " + snapshot.getValue());

                    // Fetch data and map it to Order class
                    Order order = snapshot.getValue(Order.class);

                    if (order != null) {
                        // Optionally log the fetched order data
                        Log.d("OrderHistory", "Receiver Name: " + order.getReceiverName());
                        Log.d("OrderHistory", "Receiver Address: " + order.getReceiverAddress());
                        Log.d("OrderHistory", "Selected Items: " + order.getSelectedItems());
                        Log.d("OrderHistory", "Sender Name: " + order.getSenderName());

                        // Add the order to the list
                        orderList.add(order);
                    }
                }
                // Notify adapter to refresh the UI
                orderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(OrderHistory.this, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Order model class to represent order data from Firebase
    public static class Order {
        private String receiverAddress;
        private String receiverName;
        private String receiverPhone;
        private String selectedItems;
        private String senderAddress;
        private String senderName;
        private String senderPhone;
        private String specialInstructions;

        // Default constructor for Firebase
        public Order() {}

        // Getters and Setters for all fields
        public String getReceiverAddress() {
            return receiverAddress;
        }

        public void setReceiverAddress(String receiverAddress) {
            this.receiverAddress = receiverAddress;
        }

        public String getReceiverName() {
            return receiverName;
        }

        public void setReceiverName(String receiverName) {
            this.receiverName = receiverName;
        }

        public String getReceiverPhone() {
            return receiverPhone;
        }

        public void setReceiverPhone(String receiverPhone) {
            this.receiverPhone = receiverPhone;
        }

        public String getSelectedItems() {
            return selectedItems;
        }

        public void setSelectedItems(String selectedItems) {
            this.selectedItems = selectedItems;
        }

        public String getSenderAddress() {
            return senderAddress;
        }

        public void setSenderAddress(String senderAddress) {
            this.senderAddress = senderAddress;
        }

        public String getSenderName() {
            return senderName;
        }

        public void setSenderName(String senderName) {
            this.senderName = senderName;
        }

        public String getSenderPhone() {
            return senderPhone;
        }

        public void setSenderPhone(String senderPhone) {
            this.senderPhone = senderPhone;
        }

        public String getSpecialInstructions() {
            return specialInstructions;
        }

        public void setSpecialInstructions(String specialInstructions) {
            this.specialInstructions = specialInstructions;
        }
    }

    // Adapter class for RecyclerView
    public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

        private List<Order> orders;

        public OrderAdapter(List<Order> orders) {
            this.orders = orders;
        }

        @Override
        public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // Inflate the item layout for each order
            View view = getLayoutInflater().inflate(R.layout.item_order, parent, false);
            return new OrderViewHolder(view);
        }

        @Override
        public void onBindViewHolder(OrderViewHolder holder, int position) {
            Order order = orders.get(position);
            holder.receiverName.setText("Receiver: " + order.getReceiverName());
            holder.receiverAddress.setText("Receiver Address: " + order.getReceiverAddress());
            holder.selectedItems.setText("Items: " + order.getSelectedItems());
            holder.senderName.setText("Sender: " + order.getSenderName());
            holder.senderAddress.setText("Sender Address: " + order.getSenderAddress());
            holder.receiverPhone.setText("Receiver Phone: " + order.getReceiverPhone());
            holder.senderPhone.setText("Sender Phone: " + order.getSenderPhone());
        }

        @Override
        public int getItemCount() {
            return orders.size();
        }

        // ViewHolder for each order
        public class OrderViewHolder extends RecyclerView.ViewHolder {
            TextView receiverName, receiverAddress, selectedItems, senderName, senderAddress, receiverPhone, senderPhone;

            public OrderViewHolder(View itemView) {
                super(itemView);
                receiverName = itemView.findViewById(R.id.receiverName_1);
                receiverAddress = itemView.findViewById(R.id.receiverAddress_1);
                selectedItems = itemView.findViewById(R.id.selectedItems_1);
                senderName = itemView.findViewById(R.id.senderName_1);
                senderAddress = itemView.findViewById(R.id.senderAddress_1);
                receiverPhone = itemView.findViewById(R.id.receiverPhone_1);
                senderPhone = itemView.findViewById(R.id.senderPhone_1);
            }
        }
    }
}
