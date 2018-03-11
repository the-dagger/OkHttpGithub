package com.codingblocks.okhttpgithub;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by harshitdwivedi on 11/03/18.
 */

public class NewActivity extends AppCompatActivity {
    TextView userName;
    ProgressBar pb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        String url = getIntent().getStringExtra("URL");
        userName = findViewById(R.id.userName);
        pb = findViewById(R.id.progressBar);
        makeNetworkCall(url);
    }

    public void makeNetworkCall(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                final GithubUserDetail githubUserDetail = parseJson(result);
                NewActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        userName.setText(githubUserDetail.getName());
                        pb.setVisibility(View.GONE);
                        userName.setVisibility(View.VISIBLE);
                    }
                });
            }
        });

    }

    public GithubUserDetail parseJson(String s) {
        GithubUserDetail githubUserDetail = null;
        try {
            JSONObject jsonObject = new JSONObject(s);
            String name = jsonObject.getString("name");
            githubUserDetail = new GithubUserDetail(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return githubUserDetail;
    }


}
