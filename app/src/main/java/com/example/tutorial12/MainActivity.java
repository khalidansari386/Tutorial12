package com.example.tutorial12;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class MainActivity extends AppCompatActivity {
    RecyclerView rcvUsers;

    RecyclerView.LayoutManager layoutManager;

    RequestQueue requestQueue;
    JsonArrayRequest jsonArrayRequest;
    UserAdapter userAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcvUsers = findViewById(R.id.rcvUsers);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        rcvUsers.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getApplicationContext());
        rcvUsers.setLayoutManager(layoutManager);

        // Add Divider
        DividerItemDecoration dividerItemDecoration =
                new  DividerItemDecoration(rcvUsers.getContext(),LinearLayoutManager.VERTICAL);
        rcvUsers.addItemDecoration(dividerItemDecoration);

        // Applying the LayoutAnimation
        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getApplicationContext(), resId);
        rcvUsers.setLayoutAnimation(animation);

        requestNetworkCall();
    }

    private void requestNetworkCall() {
        requestQueue = Volley.newRequestQueue(MainActivity.this);
        jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                MyUtil.URL_USER,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        MyUtil.userData = response;
                        userAdapter = new UserAdapter(response);
                        rcvUsers.setAdapter(userAdapter);
                        userAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }
}