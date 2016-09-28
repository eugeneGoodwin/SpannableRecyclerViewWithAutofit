package com.test.my.spannablerecyclerviewwithautofit.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.test.my.spannablerecyclerviewwithautofit.R;
import com.test.my.spannablerecyclerviewwithautofit.presenters.PresenterGeneralStatistic;

import org.lucasr.twowayview.widget.TwoWayView;

public class MainActivity extends AppCompatActivity implements PresenterGeneralStatistic.View{

    private PresenterGeneralStatistic mainPresenter;
    TwoWayView recyclerView;
    TextView statusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        statusView = (TextView) findViewById(R.id.status);
        recyclerView = (TwoWayView) findViewById(R.id.statisticRecyclerView);

        mainPresenter = new PresenterGeneralStatistic();
        mainPresenter.attachView(this);
        mainPresenter.init();
        mainPresenter.getPosts();
    }

    public void onUpdatePosts(View view){
        mainPresenter.getPosts();
    }

    @Override
    public TextView getStatus(){
        return statusView;
    }

    @Override
    public TwoWayView getRecyclerView(){
        return recyclerView;
    }

    @Override
    public Context getContext() {
        return this;
    }
}
