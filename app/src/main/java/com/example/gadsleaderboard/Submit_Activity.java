package com.example.gadsleaderboard;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.gadsleaderboard.controllers.RetrofitBuilder;
import com.example.gadsleaderboard.controllers.RetrofitInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Submit_Activity extends AppCompatActivity {

    private EditText _firstName, _lastName, _email, _gitHub;
    private Button _submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_);


        _firstName = findViewById(R.id.firstName_editText);
        _lastName = findViewById(R.id.lastName_editText);
        _email = findViewById(R.id.email_editText);
        _gitHub = findViewById(R.id.github_editText);
        _submitButton = findViewById(R.id.submit_button);

        final String firstName = _firstName.getText().toString();
        final String lastName = _lastName.getText().toString();
        final String email = _email.getText().toString();
        final String gitHub = _gitHub.getText().toString();



        _submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Submit_Activity.this);
                builder.setTitle(R.string.app_name);
                builder.setMessage("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        RetrofitInterface retrofitInterface = RetrofitBuilder.buildService(RetrofitInterface.class);
                        String mFirstName;
                        Call<Void> submitForm = retrofitInterface.submit(firstName, lastName, email, gitHub);
                        submitForm.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    // Display success dialog
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                                    builder.setTitle("Sure to Submit");
                                    builder.setCancelable(false);
                                    builder.show();

                                    Log.d("TAG", "onResponse: response body: " +response.body() + " response code" +response.code());
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                // Display failure dialog
                                Log.d("TAG", "onResponse: response body:  Oooops! Something went wrong.");
                            }
                        });
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }
}