package com.example.appshopdrink.Database.Datasoure;

import com.example.appshopdrink.Database.Model.Model.Cart;

import java.util.List;

import io.reactivex.Flowable;

public interface ICartDataSource {
    Flowable<List<Cart>> getCartItems();
    Flowable<List<Cart>> getCartItemById(int cartItemId);
    int countCartItems();
    void emptyCart();
    void insertToCart(Cart...carts);
    void updateToCart(Cart...carts);
    void deleteCartItem(Cart cart);
}
