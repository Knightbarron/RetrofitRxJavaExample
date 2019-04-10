package com.example.retrofittry;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retrofittry.apiservice.BaseApiService;
import com.example.retrofittry.apiservice.UtilsApi;
import com.example.retrofittry.model.Repo;
import com.example.retrofittry.model.ResponseRepos;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvRepos;
    EditText etUsername;

    BaseApiService mApiService;
    ReposAdapter mRepoAdapter;

    List<Repo> repoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvRepos = findViewById(R.id.rvRepos);
        etUsername = findViewById(R.id.etUsername);

        mApiService = UtilsApi.getAPIService();
        
        mRepoAdapter = new ReposAdapter(this,repoList,null);
        rvRepos.setLayoutManager(new LinearLayoutManager(this));
        rvRepos.setItemAnimator(new DefaultItemAnimator());
        rvRepos.setHasFixedSize(true);
        rvRepos.setAdapter(mRepoAdapter);
        
        etUsername.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    String username = etUsername.getText().toString();
                    requestRepos(username);
                    return true;
                }
                return false;
            }

            private void requestRepos(String username) {

                mApiService.requestRepos(username).subscribeOn(Schedulers.io()).observeOn(
                        AndroidSchedulers.mainThread()).subscribe(new Observer<List<ResponseRepos>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<ResponseRepos> responseRepos) {

                        for(int i=0;i<responseRepos.size();i++){

                            String name = responseRepos.get(i).getName();
                            String description = responseRepos.get(i).getDescription();

                            repoList.add(new Repo(name,description));
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, ""+ e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {


                        Toast.makeText(MainActivity.this,"Got Data",Toast.LENGTH_SHORT).show();

                        mRepoAdapter = new ReposAdapter(MainActivity.this,repoList,null);
                        rvRepos.setAdapter(mRepoAdapter);
                        mRepoAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
        

    }
}
