package com.example.listcity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    int selectedPosition = -1;  //To track selected item
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);
        String []cities = {};
        dataList = new ArrayList<>();
        //dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        // Handle selection of list items
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position; // Update selected position
            cityList.setItemChecked(position, true); // Highlight the selected item
        });


        //for button
        Button btn = (Button) findViewById(R.id.add_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Add City");

                //Set up the input
                final EditText input = new EditText(MainActivity.this);
                input.setHint("Enter city name");
                builder.setView(input);

                //Set button
                builder.setPositiveButton("Add",(dialog, which) ->{
                    String newCity = input.getText().toString().trim();
                    if (!newCity.isEmpty()){
                        dataList.add(newCity);
                        cityAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", (dialog,which) -> dialog.cancel());

                //Show
                builder.show();
            }
        });
        // Delete button functionality
        Button delBtn = findViewById(R.id.del_btn);
        delBtn.setOnClickListener(v -> {
            if (selectedPosition != -1) { // Check if an item is selected
                dataList.remove(selectedPosition); // Remove selected city
                cityAdapter.notifyDataSetChanged(); // Notify adapter
                selectedPosition = -1; // Reset selection
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}