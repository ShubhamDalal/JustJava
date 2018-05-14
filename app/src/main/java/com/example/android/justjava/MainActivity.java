package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    int numberOfCoffees = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        CheckBox whippedCreamCheck = (CheckBox) findViewById(R.id.whippedCream);
        CheckBox chocolateCheck = (CheckBox) findViewById(R.id.chocolate);
        EditText nameEditText = (EditText) findViewById(R.id.name);

        if (nameEditText.getText().toString().matches("")) {
            Toast.makeText(this, getString(R.string.askName), Toast.LENGTH_SHORT).show();
        } else {

            int price = calculatePrice(numberOfCoffees, whippedCreamCheck.isChecked(), chocolateCheck.isChecked());
            String name = nameEditText.getText().toString();

            String addWhippedCream,addChocolate;
            if (chocolateCheck.isChecked()){
                addChocolate = getString(R.string.yes);
            }else addChocolate = getString(R.string.no);

            if (whippedCreamCheck.isChecked()){
                addWhippedCream = getString(R.string.yes);
            }else addWhippedCream = getString(R.string.no);

            String message = getString(R.string.orderSummaryName, name);
            message += "\n" + getString(R.string.addCream, addWhippedCream);
            message += "\n" + getString(R.string.addChocolate, addChocolate);
            message += "\n" + getString(R.string.quantity2, numberOfCoffees);
            message += "\n" + getString(R.string.total, NumberFormat.getCurrencyInstance().format(price));
            message += "\n" + getString(R.string.thankYou);

            String[] addresses = {"shubhamdalal@hotmail.com"};

            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL, addresses);
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.orderFrom) + " " + name);
            intent.putExtra(Intent.EXTRA_TEXT, message);

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }

    /**
     * This method increases the quantity and displays the given quantity value on the screen.
     */
    public void increaseQuantity(View view) {
        if (numberOfCoffees == 100) {
            Toast.makeText(this, getString(R.string.moreThan), Toast.LENGTH_SHORT).show();
        } else {
            ++numberOfCoffees;
            display(numberOfCoffees);
        }
    }

    /**
     * This method decreases the quantity and displays the given quantity value on the screen.
     */
    public void decreaseQuantity(View view) {
        if (numberOfCoffees == 1) {
            Toast.makeText(this, getString(R.string.lessThan), Toast.LENGTH_SHORT).show();
        } else {
            --numberOfCoffees;
            display(numberOfCoffees);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method calculates total price.
     */
    private int calculatePrice(int quantity, boolean whippedCream, boolean chocolate) {
        int basePrice = 5;
        if (whippedCream)
            basePrice = basePrice + 1;
        if (chocolate)
            basePrice = basePrice + 2;
        return basePrice * quantity;
    }
}
