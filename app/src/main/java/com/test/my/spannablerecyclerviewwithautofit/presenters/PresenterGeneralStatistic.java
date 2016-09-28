package com.test.my.spannablerecyclerviewwithautofit.presenters;

import android.widget.TextView;

import com.test.my.spannablerecyclerviewwithautofit.R;
import com.test.my.spannablerecyclerviewwithautofit.adapters.RecyclerAdapterGeneralStatistic;
import com.test.my.spannablerecyclerviewwithautofit.interfaces.Callback;
import com.test.my.spannablerecyclerviewwithautofit.model.NetModel;
import com.test.my.spannablerecyclerviewwithautofit.model.Post;
import com.test.my.spannablerecyclerviewwithautofit.retrofit.entries.JsonPost;
import com.test.my.spannablerecyclerviewwithautofit.view.MainActivity;

import org.lucasr.twowayview.widget.TwoWayView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Android-dev on 02.08.2016.
 */
public class PresenterGeneralStatistic extends BaseProgressPresenter<PresenterGeneralStatistic.View> {

    private static PresenterGeneralStatistic instance;
    private RecyclerAdapterGeneralStatistic adapter;

    private NetModel netModel = NetModel.instance();

    public static PresenterGeneralStatistic instance() {
        if (instance == null) {
            instance = new PresenterGeneralStatistic();
        }
        return instance;
    }

    public void init() {
        final MainActivity context = (MainActivity) view.getContext();
        TwoWayView recyclerView = view.getRecyclerView();

        adapter = new RecyclerAdapterGeneralStatistic(context);
        adapter.setOnItemClickListener(new RecyclerAdapterGeneralStatistic.OnItemClickListener<RecyclerAdapterGeneralStatistic.Statistic>(){
            public void onClick(RecyclerAdapterGeneralStatistic.Statistic t){

            }
        });

        recyclerView.setAdapter(adapter);
    }

    public void getPosts(){
        final TextView status = view.getStatus();
        showProgress();
        netModel.getPosts(new Callback<List<JsonPost>>() {
            @Override
            public void T(List<JsonPost> jsonPosts) {
                hideProgress();
                ArrayList<Post> list = new ArrayList<Post>();
                for (JsonPost post : jsonPosts) {
                    list.add(new Post(post.getUserId(), post.getId(), post.getTitle(), post.getBody()));
                }
                adapter.setDisplayItems(getListItems(list));
                adapter.notifyDataSetChanged();
                status.setText(view.getContext().getString(android.R.string.ok));
            }
        }, new Callback<String>() {
            @Override
            public void T(String s) {
                status.setText(s);
            }
        });
    }

    private List<RecyclerAdapterGeneralStatistic.Statistic> getListItems(ArrayList<Post> list){

        List<RecyclerAdapterGeneralStatistic.Statistic> allItems = new ArrayList<RecyclerAdapterGeneralStatistic.Statistic>();
        allItems.add(new RecyclerAdapterGeneralStatistic.Statistic(view.getContext().getString(R.string.general_statistic_duration), String.valueOf(list.get(0).getId()), "duration", R.drawable.clock, "Duration last call"));
        allItems.add(new RecyclerAdapterGeneralStatistic.Statistic(view.getContext().getString(R.string.general_statistic_acd), String.valueOf(list.get(1).getId()), "acd", 0, ""));
        allItems.add(new RecyclerAdapterGeneralStatistic.Statistic(view.getContext().getString(R.string.general_statistic_asr), String.valueOf(list.get(2).getId()), "asr", 0, ""));
        allItems.add(new RecyclerAdapterGeneralStatistic.Statistic(view.getContext().getString(R.string.general_statistic_call), String.valueOf(list.get(3).getId()), "call", R.drawable.in_call, "Currently number call"));
        allItems.add(new RecyclerAdapterGeneralStatistic.Statistic(view.getContext().getString(R.string.general_statistic_use), String.valueOf(list.get(4).getId()), "use", R.drawable.in_use, ""));
        allItems.add(new RecyclerAdapterGeneralStatistic.Statistic(view.getContext().getString(R.string.general_statistic_closed), String.valueOf(list.get(5).getId()), "closed", R.drawable.closed, ""));
        allItems.add(new RecyclerAdapterGeneralStatistic.Statistic(view.getContext().getString(R.string.general_statistic_simblock), String.valueOf(list.get(6).getId()), "simblock", R.drawable.block, ""));

        return allItems;
    }

    public interface View extends BasePresenterInterface {
        TwoWayView getRecyclerView();
        TextView getStatus();
    }
}
