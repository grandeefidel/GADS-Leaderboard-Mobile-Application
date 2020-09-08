package com.fidelity.fidel_gads_2020_leaderboard.ui.main;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.fidelity.fidel_gads_2020_leaderboard.ApiUtil;
import com.fidelity.fidel_gads_2020_leaderboard.LeanersInfo;
import com.fidelity.fidel_gads_2020_leaderboard.LearnersRecylclerAdapter;
import com.fidelity.fidel_gads_2020_leaderboard.R;
import com.fidelity.fidel_gads_2020_leaderboard.TabbedActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {
    public static final String LEARNING_LEADERS = "/api/hours";
    public static final String HOUR = "hours";
    private RecyclerView rvLearners;

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private View mRoot;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
            mRoot = inflater.inflate(R.layout.content_learners_list, container, false);
            rvLearners = mRoot.findViewById(R.id.list_learners);
            LinearLayoutManager learnerLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
            rvLearners.setLayoutManager(learnerLayoutManager);
            URL url = ApiUtil.buildURL(LEARNING_LEADERS);
            Log.d("url:", url.toString());
            new LearnersQueryTask().execute(url);
            return mRoot;
    }

    public class LearnersQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String result = null;
            try {
                result =  ApiUtil.getJson(searchUrl);

            }catch (Exception e){
                Log.d("Error:", e.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String jsonResult) {
//            final TextView textView = mRoot.findViewById(R.id.section_label);
//            final TextView txtName = mRoot.findViewById(R.id.text_name);
            Log.d("jsonResult:", jsonResult);
            ArrayList<LeanersInfo> learners = ApiUtil.getLearnersFromJson(jsonResult);
            LearnersRecylclerAdapter adapter = new LearnersRecylclerAdapter(learners, HOUR);
            rvLearners.setAdapter(adapter);


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


    }
}