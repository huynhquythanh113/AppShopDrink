package com.example.appshopdrink;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appshopdrink.Adapter.Category;
import com.example.appshopdrink.Adapter.CategoryAdapter;
import com.example.appshopdrink.Adapter.Photo;
import com.example.appshopdrink.Adapter.PhotoAdapter;
import com.example.appshopdrink.Database.Datasoure.CartRepository;
import com.example.appshopdrink.Database.Local.CartDataSource;
import com.example.appshopdrink.Database.Local.CartDatabase;
import com.example.appshopdrink.Utils.Common;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;



public class TrangchuFragment extends Fragment {


    private ViewPager viewPager;
    private PhotoAdapter photoAdapter;
    private List<Photo> mListPhoto;
    private Timer timer;
    private static final String base = "htpp://192.168.1.9/AppShopTraSua/listproduct.php";
    private List<Category> categoryList;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trangchu, container, false);
        View view1 = inflater.inflate(R.layout.activity_main, container, false);
        ViewPager viewPager1 = (ViewPager) view1.findViewById(R.id.view_pager);
        categoryList = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.lst_menu);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setHasFixedSize(true);
        mListPhoto = getListPhoto();
        viewPager = (ViewPager) view.findViewById(R.id.viewpager1);
        viewPager.onSaveInstanceState();
        CircleIndicator circleIndicator = (CircleIndicator) view.findViewById(R.id.circle_indicator);
        photoAdapter = new PhotoAdapter(getActivity(),mListPhoto);
        viewPager.setAdapter(photoAdapter);
        circleIndicator.setViewPager(viewPager);
        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        progressBar = (ProgressBar)view.findViewById(R.id.progressMenu);



        autoSlideImage();
        viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = viewPager.getCurrentItem();
                Toast.makeText((MainActivity)getActivity(), a, Toast.LENGTH_SHORT).show();
            }
        });
        getproducts();

        return view;
    }


    private List<Photo> getListPhoto() {
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.b));
        list.add(new Photo(R.drawable.d));
        list.add(new Photo(R.drawable.c));
        return list;
    }
    private void autoSlideImage(){
        if(mListPhoto==null || mListPhoto.isEmpty() || viewPager==null){
            return;
        }
        if(timer==null){
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem  = viewPager.getCurrentItem();
                        int totalItem = mListPhoto.size()-1;
                        if(currentItem<totalItem){
                            currentItem++;
                            viewPager.setCurrentItem((currentItem));
                        }
                        else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        },3000,5000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(timer !=null){
            timer.cancel();
            timer=null;
        }
    }
    private void getproducts(){
        String a = Common.url + "/AppShopTraSua/listproduct.php";
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, a,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        String a = "";
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i =0 ; i<array.length();i++){
                                JSONObject object = array.getJSONObject(i);
                                String image = object.getString("image");
                                String name = object.getString("name");
                                String id = object.getString("id");
                                Category category = new Category(image,name,id);
                                categoryList.add(category);
                            }
                        }
                        catch (Exception e){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        adapter = new CategoryAdapter(getActivity(),categoryList);
                        recyclerView.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(getActivity()).add(stringRequest);

    }
}