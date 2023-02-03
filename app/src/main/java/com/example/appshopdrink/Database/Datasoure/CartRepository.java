package com.example.appshopdrink.Database.Datasoure;

import com.example.appshopdrink.Database.Model.Model.Cart;

import java.util.List;

import io.reactivex.Flowable;

public class CartRepository implements ICartDataSource{

    private ICartDataSource iCartDataSource;

    public CartRepository(ICartDataSource iCartDataSource) {
        this.iCartDataSource = iCartDataSource;
    }

    private  static CartRepository instance;

    public static CartRepository getInstance(ICartDataSource iCartDataSource)
    {
        if(instance == null)
            instance = new CartRepository(iCartDataSource);
        return instance;
    }
    @Override
    public Flowable<List<Cart>> getCartItems() {
        return iCartDataSource.getCartItems();
    }

    @Override
    public Flowable<List<Cart>> getCartItemById(int cartItemId) {
        return iCartDataSource.getCartItemById(cartItemId);
    }

    @Override
    public int countCartItems() {
        return iCartDataSource.countCartItems();
    }

    @Override
    public void emptyCart() {
        iCartDataSource.emptyCart();
    }

    @Override
    public void insertToCart(Cart... carts) {
        iCartDataSource.insertToCart(carts);
    }

    @Override
    public void updateToCart(Cart... carts) {
        iCartDataSource.updateToCart(carts);
    }

    @Override
        public void deleteCartItem(Cart cart) {
            iCartDataSource.deleteCartItem(cart);

        }
}
