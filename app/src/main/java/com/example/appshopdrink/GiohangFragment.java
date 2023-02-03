package com.example.appshopdrink;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.appshopdrink.Adapter.CartListOrderAdapter;
import com.example.appshopdrink.Database.Model.Model.Cart;
import com.example.appshopdrink.Utils.Common;
import com.example.appshopdrink.Utils.RecyclerItemTouchHelper;
import com.example.appshopdrink.Utils.RecyclerItemTouchHelperListener;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class GiohangFragment extends Fragment implements RecyclerItemTouchHelperListener {

    RecyclerView recyclerView;
    Button endorder;
    RelativeLayout relativeLayout;
    CompositeDisposable compositeDisposable;
    LinearLayout linearLayout;
    private CartListOrderAdapter cartListOrderAdapter;
    List<Cart> listCart = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_giohang, container, false);
      relativeLayout = (RelativeLayout)view.findViewById(R.id.notcart);
        linearLayout = (LinearLayout)view.findViewById(R.id.showcart);
      relativeLayout.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              ((ProfileActivity)getActivity()).changeFragment();
          }
      });
        compositeDisposable = new CompositeDisposable();
        recyclerView = (RecyclerView) view.findViewById(R.id.cart_end_order);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        endorder = (Button) view.findViewById(R.id.endorder);

        endorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Common.cartRepository.countCartItems()==0){
                    ((ProfileActivity)getActivity()).changeFragment();
                    Toast.makeText(getActivity(), "You must order some drink!", Toast.LENGTH_SHORT).show();
                }
                else {
                    startActivity(new Intent(getActivity(), SubmitOrderActivity.class));
                }
            }
        });
        ItemTouchHelper.SimpleCallback simpleCallback = new RecyclerItemTouchHelper(0,ItemTouchHelper.LEFT,  this);
         new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);



        loadCartItems();
        //loadViewCart();

        return view;
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

    @Override
    public void onResume() {
        loadCartItems();
        super.onResume();
    }

    private void displaycartsItem(List<Cart> carts) {
        listCart = carts;
        cartListOrderAdapter = new CartListOrderAdapter(getActivity(),carts);
        recyclerView.setAdapter(cartListOrderAdapter);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if(viewHolder instanceof CartListOrderAdapter.CartListOrderViewHolder){
            String name = listCart.get(viewHolder.getAdapterPosition()).name;

            Cart delete = listCart.get(viewHolder.getAdapterPosition());
            int deleteitem = viewHolder.getAdapterPosition();

            cartListOrderAdapter.notifyItemRemoved(deleteitem);
            Common.cartRepository.deleteCartItem(delete);
            Common.PriceOrder -= delete.price;
            String b = String.valueOf(Common.PriceOrder);
            ((ProfileActivity)getActivity()).initDB();
            ((ProfileActivity)getActivity()).updateCartCount();
        }
    }
}