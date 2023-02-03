package com.example.appshopdrink;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import com.example.appshopdrink.Adapter.CartListOrderAdapter;
import com.example.appshopdrink.Database.Model.Model.Cart;
import com.example.appshopdrink.Utils.Common;
import com.google.android.material.badge.BadgeUtils;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ShowCartOrder extends AppCompatActivity {

    RecyclerView recyclerView;
    Button endorder;

    CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cart_order);

        compositeDisposable = new CompositeDisposable();

        recyclerView = (RecyclerView) findViewById(R.id.cart_end_order);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        endorder = (Button) findViewById(R.id.endorder);
        loadCartItems();
    }

    private void loadCartItems() {
        compositeDisposable.add(
                Common.cartRepository.getCartItems()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Cart>>() {
                    @Override
                    public void accept(List<Cart> carts) throws Exception {
                            displaycartsItem(carts);
                    }
                })
        );
    }

    private void displaycartsItem(List<Cart> carts) {
        CartListOrderAdapter cartListOrderAdapter = new CartListOrderAdapter(ShowCartOrder.this,carts);
        recyclerView.setAdapter(cartListOrderAdapter);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
}