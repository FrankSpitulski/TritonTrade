package com.tmnt.tritontrade.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.tmnt.tritontrade.R;


public class Create_Post extends AppCompatActivity {

    private Spinner spinner1,delSpinner;
    private Button theBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__post);

        addItemsOnCategorySpinner();
        addItemsOnDeliverySpinner();
        addListenerOnSpinnerItemSelection();
    }

    public void addItemsOnCategorySpinner(){
        spinner1 = (Spinner) findViewById(R.id.spinner3);
        List<String> categoryList = new ArrayList<String>();
        categoryList.add("Clothes");
        categoryList.add("Food");
        categoryList.add("Furniture");
        categoryList.add("Storage");
        categoryList.add("Supplies");
        categoryList.add("Technology");
        categoryList.add("Textbooks");
        categoryList.add("Transportation");
        categoryList.add("Miscellaneous");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, categoryList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
    }

    public void addItemsOnDeliverySpinner(){
        delSpinner =(Spinner) findViewById(R.id.spinner);
        List<String> deliveryList = new ArrayList<String>();
        deliveryList.add("Pick Up");
        deliveryList.add("Can Deliver");
    }

    public void addListenerOnSpinnerItemSelection() {
        spinner1 = (Spinner) findViewById(R.id.spinner3);
    }

}
