package com.example.appshopdrink;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appshopdrink.Adapter.DonHang;
import com.example.appshopdrink.Adapter.DonhangList;
import com.example.appshopdrink.Database.Datasoure.CartRepository;
import com.example.appshopdrink.Database.Local.CartDataSource;
import com.example.appshopdrink.Database.Local.CartDatabase;
import com.example.appshopdrink.Utils.Common;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nex3z.notificationbadge.NotificationBadge;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager1;
    private TabLayout tabLayout;
    private TrangchuFragment trangchuFragment;
    private DatmonFragment datmonFragment;
    private TaikhoanFragment taikhoanFragment;
    private NotificationBadge badge;
    private ImageView cartorder;
    private GiohangFragment giohangFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = findViewById(R.id.toolbar);
        badge = (NotificationBadge)findViewById(R.id.badge);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        trangchuFragment = new TrangchuFragment();
        datmonFragment = new DatmonFragment();
        taikhoanFragment = new TaikhoanFragment();
        giohangFragment = new GiohangFragment();
        viewPager1 = (ViewPager) findViewById(R.id.view_pager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFrament(trangchuFragment, "Home");
        viewPagerAdapter.addFrament(datmonFragment, "Drink");
        viewPagerAdapter.addFrament(giohangFragment,"Cart");
        viewPagerAdapter.addFrament(taikhoanFragment, "Account");
        viewPager1.setAdapter(viewPagerAdapter);
        setSupportActionBar(toolbar);
        tabLayout.setupWithViewPager(viewPager1);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_baseline_house_24);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_baseline_fastfood_24);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_baseline_shopping_cart_24);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_baseline_account_box_24);
        SharedPreferences prefs = getSharedPreferences("Login", MODE_PRIVATE);
        String phone = prefs.getString("phoneUser", "No name defined");
        Hello(phone);
        FragmentManager fragmentManager =getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        initDB();
        updateCartCount();

    }

    private void Hello(String phone) {
        Bundle bundle = new Bundle();
        bundle.putString("phoneUser", phone);
// set Fragmentclass Arguments
        TaikhoanFragment fragobj = new TaikhoanFragment();
        fragobj.setArguments(bundle);


    }

    public void changeFragment(){
        viewPager1.setCurrentItem(1, true);
    }
    public void initDB() {
        Common.cartDatabase = CartDatabase.getInstance(this);
        Common.cartRepository = CartRepository.getInstance(CartDataSource.getInstance(Common.cartDatabase.cartDAO()));
    }
    public void dangxuat(){
        finish();
    }
    public void updateDonhangCount(){

        String a = Common.url + "/AppShopTraSua/getdonhang.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, a,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<DonHang> donHangs = new ArrayList<>();
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i =0 ; i<array.length();i++){
                                JSONObject object = array.getJSONObject(i);
                                DonHang donHang = new DonHang();
                                donHang.cmt = object.getString("cmt");
                                donHang.date = object.getString("date");
                                donHang.id = object.getString("id");
                                donHang.name = object.getString("name");
                                donHang.detailorder = object.getString("detail");
                                donHang.diachi = object.getString("address");
                                donHang.phone = object.getString("phone");
                                donHang.Tongtien= object.getInt("tongtien");
                                donHang.status = object.getString("status");
                                donHangs.add(donHang);
                            }
                        }
                        catch (Exception e){
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        if(donHangs.size()==0 )
                            tabLayout.getTabAt(3).removeBadge();
                        else
                        {
                            // badge.setVisibility(View.VISIBLE);
                            //badge.setText(String.valueOf(Common.cartRepository.countCartItems()));
                            tabLayout.getTabAt(2).getOrCreateBadge().setNumber(Common.cartRepository.countCartItems());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);

    }

    public void updateCartCount() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(Common.cartRepository.countCartItems()==0 )
                    tabLayout.getTabAt(2).removeBadge();
                else
                {
                   // badge.setVisibility(View.VISIBLE);
                    //badge.setText(String.valueOf(Common.cartRepository.countCartItems()));
                    tabLayout.getTabAt(2).getOrCreateBadge().setNumber(Common.cartRepository.countCartItems());
                }

            }
        });
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments = new ArrayList<>();
        private List<String> framentTitle = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFrament(Fragment fragment, String title) {
            fragments.add(fragment);
            framentTitle.add(title);
        }


        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return framentTitle.get(position);
        }
    }

    public class CustomViewPager extends ViewPager {

        private boolean isPagingEnabled;


        public CustomViewPager(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.isPagingEnabled = false;
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (this.isPagingEnabled) {
                return super.onTouchEvent(event);
            }

            return false;
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent event) {
            if (this.isPagingEnabled) {
                return super.onInterceptTouchEvent(event);
            }

            return false;
        }

        public void setPagingEnabled(boolean b) {
            this.isPagingEnabled = b;
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        updateCartCount();
    }
}