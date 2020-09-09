package com.fidelity.fidel_gads_2020_leaderboard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity{
    private EditText firstName, lastName,emailTV, gitLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submit_page);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        emailTV = findViewById(R.id.email);
        gitLink = findViewById(R.id.gitlink);

        findViewById(R.id.button_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitProject();
            }
        });

    }

    private void submitProject(){
        String first_name = firstName.getText().toString().trim();
        String last_name = lastName.getText().toString().trim();
        String email_view = emailTV.getText().toString().trim();
        String git_link = gitLink.getText().toString().trim();

        if(first_name.isEmpty()){
            firstName.setError("This field is required");
            firstName.requestFocus();
            return;
        }
        if(last_name.isEmpty()){
            lastName.setError("This field is required");
            lastName.requestFocus();
            return;
        }
        if(email_view.isEmpty()){
            emailTV.setError("This field is required");
            emailTV.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email_view).matches()){
            emailTV.setError("Enter a valid email");
            emailTV.requestFocus();
            return;
        }
        if(git_link.isEmpty()){
            gitLink.setError("This field is required");
            gitLink.requestFocus();
            return;
        }
        Call<ResponseBody> call = HttpClient
                .getInstance().getApi()
                .submitProject(first_name,last_name,email_view,git_link);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                AlertDialog.Builder alert = new AlertDialog.Builder(HomeActivity.this);
                alert.setIcon(R.drawable.toplearner);
//                alert.setTitle("Game Over");
                alert.setCancelable(true);
                alert.setMessage("Submission Successful!");
                alert.show();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                AlertDialog.Builder alert = new AlertDialog.Builder(HomeActivity.this);
                alert.setIcon(R.drawable.toplearner);
//                alert.setTitle("Game Over");
                alert.setCancelable(true);
                alert.setMessage("Submission not Successful");
                alert.show();
            }
        });

    }
}