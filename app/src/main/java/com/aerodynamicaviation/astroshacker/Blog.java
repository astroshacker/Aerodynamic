package com.aerodynamicaviation.astroshacker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.lang.reflect.Field;

public class Blog extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    startActivity(new Intent(Blog.this, MainActivity.class));
                    break;
                case R.id.navigation_dashboard:
                    startActivity(new Intent(Blog.this, Planes.class));
                    break;
                case R.id.navigation_new:
                    startActivity(new Intent(Blog.this, Blog.class));
                    break;
                case R.id.navigation_notifications:
                    startActivity(new Intent(Blog.this, Reserve.class));
                    break;
            }
            return true;
        }
    };

    public static class BottomNavigationViewHelper {
        @SuppressLint("RestrictedApi")
        private static void disableShiftMode(BottomNavigationView view) {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
            try {
                Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
                shiftingMode.setAccessible(true);
                shiftingMode.setBoolean(menuView, false);
                shiftingMode.setAccessible(false);
                for (int i = 0; i < menuView.getChildCount(); i++) {
                    BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                    //noinspection RestrictedApi
                    item.setShiftingMode(false);
                    // set once again checked value, so view will be updated
                    //noinspection RestrictedApi
                    item.setChecked(item.getItemData().isChecked());
                }
            } catch (NoSuchFieldException e) {
                Log.e("BNVHelper", "Unable to get shift mode field", e);
            } catch (IllegalAccessException e) {
                Log.e("BNVHelper", "Unable to change value of shift mode", e);
            }
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reserve);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        WebView mWebview = (WebView) findViewById(R.id.mWebView);
        mWebview.getSettings().setBuiltInZoomControls(true);
        mWebview.getSettings().setLoadWithOverviewMode(true);
        mWebview.getSettings().setUseWideViewPort(true);
        mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript
        mWebview.getSettings().setDomStorageEnabled(true);
        mWebview.getSettings().setAppCacheEnabled(true);
        mWebview.getSettings().setLoadsImagesAutomatically(true);
        mWebview.setWebViewClient(new WebViewClient());
        mWebview.loadUrl("http://www.aerodynamicaviation.com/blog/");


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation3);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);
    }
}