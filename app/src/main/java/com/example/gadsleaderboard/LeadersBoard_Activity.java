package com.example.gadsleaderboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.gadsleaderboard.controllers.API_Singleton;
import com.example.gadsleaderboard.controllers.RecyclerAdapterOne;
import com.example.gadsleaderboard.models.LearnerOne;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LeadersBoard_Activity extends AppCompatActivity {

    private Toolbar _toolbar;

    private Button _submitButton;
    private ImageView _firstImageView, _secondImageView, _thirdImageView;
    private TextView _firstTextView, _secondTextView, _thirdTextView;
    private RecyclerView _customRecyclerView;

    private RecyclerAdapterOne _mRecyclerAdapterOne;
    private RecyclerView.LayoutManager _mLayoutManager;
    private RequestQueue _mRequestQueue;
    private ArrayList<LearnerOne> learnerOneList;

    private String base_url = "https://gadsapi.herokuapp.com";
    private String json_base_url = "https://jsonplaceholder.typicode.com";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaders_board);

        //  Make a Network Call for a single learner in the Learning Leaders.
        //getSingleLearner();

        _toolbar = findViewById(R.id.custom_toolbar);
        setSupportActionBar(_toolbar);

        _submitButton = findViewById(R.id.submit_button);
        _firstImageView = findViewById(R.id.first_profile_image);
        _secondImageView = findViewById(R.id.second_profile_image);
        _thirdImageView = findViewById(R.id.third_profile_image);

        _firstTextView = findViewById(R.id.first_profile_name);
        _secondTextView = findViewById(R.id.second_profile_name);
        _thirdTextView = findViewById(R.id.third_profile_name);

        _customRecyclerView = findViewById(R.id.custom_recyclerView);

        learnerOneList = new ArrayList<>();

        _submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Submit_Activity.class);
                startActivity(intent);
            }
        });

        //  Make a Network Call for Top 20 learners in the Learning Leaders.
        getLearningLeaders();

    }

    // API call for Top 20 learners in the Learning Leaders.
    private void getLearningLeaders() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();

        //  Initialize the instantiated "requestQueue" we instantiated globally.
        //requestQueue = Volley.newRequestQueue(this);
        _mRequestQueue = API_Singleton.getInstance(this.getApplicationContext()).getRequestQueue();

        /*
         * Let's make a new "JsonObjectRequest" which takes in "Five" parameters.
         * (a) Request method,
         * (b) Request URL,
         * (c) null,
         * (d) new Success response listener and
         * (e) Error response listener
         * */
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
            Request.Method.GET,
            base_url+"/api/skilliq",
            null,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    progressDialog.cancel();

                    for (int i = 0; i < response.length(); i++) {

                        try {
                            JSONObject eachLearnerObject = response.getJSONObject(i);

                            String _mBadgeImageUrl = eachLearnerObject.getString("badgeUrl");
                            String _mName = eachLearnerObject.getString("name");
                            String _mCountry = eachLearnerObject.getString("country");
                            int _mScore = eachLearnerObject.getInt("score");
                            Log.d("TAG:::: ", "onResponse: " + _mName + "..." + _mCountry + "..." + _mBadgeImageUrl);

                            LearnerOne learnerOne = new LearnerOne(_mName, _mCountry, _mScore, _mBadgeImageUrl);

                            learnerOneList.add(learnerOne);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    _mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    _customRecyclerView.setLayoutManager(_mLayoutManager);
                    _customRecyclerView.setHasFixedSize(true);

                    _mRecyclerAdapterOne = new RecyclerAdapterOne(getApplicationContext(), learnerOneList);
                    _customRecyclerView.setAdapter(_mRecyclerAdapterOne);
                };
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    progressDialog.cancel();

                    Log.d("ERROR_TAG", "onErrorResponse: " + error.getMessage());
                };
            }
        );

        /*
         * Let's make use of our initialized "requestQueue" object and add the "jsonObjectRequest" to it.
         * Note: I take this "requestQueue" object as a form of asynchronous way using Volley to make an API call.
         * */
        _mRequestQueue.add(jsonArrayRequest);
    };

};