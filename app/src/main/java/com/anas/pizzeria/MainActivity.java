package com.anas.pizzeria;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CART_ACTIVITY = 1;
    private List<Coffee> coffeeList;
    private CoffeeAdapter coffeeAdapter;

    private int coffeeCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window window = MainActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryYellow));
        window.setNavigationBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryYellow));
        RecyclerView coffeeRecyclerView = findViewById(R.id.coffeeRecyclerView);
        coffeeList = createcoffeeList();
        coffeeAdapter = new CoffeeAdapter(coffeeList, new CoffeeAdapter.coffeeClickListener() {
            @Override
            public void onPlusClick(int position) {
                addcoffeeToCart(position);
            }

            @Override
            public void onMinusClick(int position) {
                removecoffeeFromCart(position);
            }
        });

        coffeeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        coffeeRecyclerView.setAdapter(coffeeAdapter);

        Button cartButton = findViewById(R.id.cartButton);
        Button historyButton = findViewById(R.id.historyButton);

        cartButton.setOnClickListener(v -> {
            if (coffeeCount > 0) {
                ArrayList<Coffee> selectedcoffee = new ArrayList<>();
                for (Coffee coffee : coffeeList) {
                    if (coffee.getQuantity() > 0) {
                        selectedcoffee.add(coffee);
                    }
                }
                if (selectedcoffee.size() > 0) {
                    double total = calculateTotal(selectedcoffee);
                    openCartActivity(selectedcoffee, total);
                } else {
                    Toast.makeText(MainActivity.this, "No coffee added to the cart", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "No coffee added to the cart", Toast.LENGTH_SHORT).show();
            }
        });

        historyButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        });
    }

    private List<Coffee> createcoffeeList() {
        List<Coffee> coffee = new ArrayList<>();
        coffee.add(new Coffee("Espresso"          , "TK 90"));
        coffee.add(new Coffee("Americano"         , "TK 150"));
        coffee.add(new Coffee("Cappuccino"        , "TK 200"));
        coffee.add(new Coffee("Flavour Cappuccino", "TK 230"));
        coffee.add(new Coffee("Latte"             , "TK 220"));
        coffee.add(new Coffee("Caramel Latte"     , "TK 260"));
        coffee.add(new Coffee("Iced Latte"        , "TK 240"));
        coffee.add(new Coffee("Frappuccino"       , "TK 350"));
        coffee.add(new Coffee("Macchiato"         , "TK 210"));
        coffee.add(new Coffee("Mocha"             , "TK 230"));
        coffee.add(new Coffee("Hazelnut Mocha"    , "TK 320"));
        coffee.add(new Coffee("Affogato"          , "TK 360"));
        coffee.add(new Coffee("Irish Coffee"      , "TK 390"));
        coffee.add(new Coffee("Filter Coffee"     , "TK 160"));
        coffee.add(new Coffee("Cold Brew"         , "TK 290"));
        coffee.add(new Coffee("Vanilla Latte"     , "TK 280"));
        return coffee;
    }

    private void addcoffeeToCart(int position) {
        Coffee coffee = coffeeList.get(position);
        int quantity = coffee.getQuantity();
        if (quantity < 10) {
            coffeeCount++;
            quantity++;
            coffee.setQuantity(quantity);
            coffeeAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Maximum coffee limit reached", Toast.LENGTH_SHORT).show();
        }
    }

    private void removecoffeeFromCart(int position) {
        Coffee coffee = coffeeList.get(position);
        int quantity = coffee.getQuantity();
        if (quantity > 0) {
            coffeeCount--;
            quantity--;
            coffee.setQuantity(quantity);
            coffeeAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Why?", Toast.LENGTH_SHORT).show();
        }
    }

    private double calculateTotal(List<Coffee> coffee) {
        double total = 0;
        for (Coffee Coffee : coffee) {
            int quantity = Coffee.getQuantity();
            double price = Double.parseDouble(Coffee.getPrice().replaceAll("[^\\d.]+", ""));
            total += (quantity * price);
        }
        return total;
    }


    private void openCartActivity(List<Coffee> selectedcoffee, double total) {
        Intent intent = new Intent(MainActivity.this, CartActivity.class);
        intent.putExtra("selectedcoffee", new ArrayList<>(selectedcoffee));
        intent.putExtra("total", total);
        startActivityForResult(intent, REQUEST_CART_ACTIVITY);
    }

    //Sets the Quantities of coffee back to 0 once we confirm the order
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CART_ACTIVITY && resultCode == RESULT_OK) {
            ArrayList<Coffee> updatedcoffee = data.getParcelableArrayListExtra("selectedcoffee");
            if (updatedcoffee != null) {
                for (Coffee coffee: coffeeList) {
                    for (Coffee updatedCoffee : updatedcoffee) {
                        if (Objects.equals(coffee.getName(), updatedCoffee.getName())) {
                            coffee.setQuantity(updatedCoffee.getQuantity());
                        }
                    }
                }
                coffeeAdapter.notifyDataSetChanged();
            }
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        }

    }
}