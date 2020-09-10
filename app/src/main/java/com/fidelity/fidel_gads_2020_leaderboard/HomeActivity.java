package com.fidelity.fidel_gads_2020_leaderboard;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
                validate();
            }
        });

    }

    private void validate(){
        final String first_name = firstName.getText().toString().trim();
        final String last_name = lastName.getText().toString().trim();
        final String email_view = emailTV.getText().toString().trim();
        final String git_link = gitLink.getText().toString().trim();

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

        new AlertDialog.Builder(HomeActivity.this)
//                .setTitle("Two Buttons")
                .setMessage("Are you sure?")
                .setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                ViewGroup viewGroup = findViewById(android.R.id.content);
                                LayoutInflater inflater = LayoutInflater.from(HomeActivity.this);
                                View dialogView = inflater.inflate(R.layout.progress_load, viewGroup, false);
                                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                                builder.setView(dialogView);
                                AlertDialog alertDialog = builder.create();
                                alertDialog.setCancelable(true);
                                alertDialog.show();

                                submitProject(first_name,last_name,email_view,git_link, alertDialog);
                            }
                }).show();




    }

    private void submitProject(String first_name, String last_name, String email_view, String git_link, final AlertDialog alertDialog){
        Call<ResponseBody> call = HttpClient
                .getInstance().getApi()
                .submitProject(first_name,last_name,email_view,git_link);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                alertDialog.dismiss();
                if(response.isSuccessful()){
                    getResponseDialog(true);
                }else{
                    getResponseDialog(false);
                }
                Log.d("Respose: ", response.toString());

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                alertDialog.dismiss();
                getResponseDialog(true);

            }
        });
    }

    private void getResponseDialog(boolean b) {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        LayoutInflater inflater = LayoutInflater.from(HomeActivity.this);

        View dialogView = null;
        if(b){

            dialogView= inflater.inflate(R.layout.display_success, viewGroup, false);
        }else{

        dialogView = inflater.inflate(R.layout.display_response, viewGroup, false);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(true);
        alertDialog.show();
    }
}