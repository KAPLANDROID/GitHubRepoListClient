package com.kaplandroid.githubrepolistclient.ui;

import android.app.Activity;
import android.content.res.Configuration;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.kaplandroid.githubrepolistclient.R;
import com.kaplandroid.githubrepolistclient.adapter.RepoInfoAdapter;
import com.kaplandroid.githubrepolistclient.restapi.RestApiClient;
import com.kaplandroid.githubrepolistclient.restapi.model.response.RepoInfo;
import com.kaplandroid.githubrepolistclient.util.DialogUtil;
import com.kaplandroid.githubrepolistclient.util.EndlessRecyclerViewScrollListener;
import com.squareup.seismic.ShakeDetector;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by KAPLANDROID on 2.09.2016.
 */
@SuppressWarnings("unchecked")
public class MainActivity extends Activity implements ShakeDetector.Listener {

    @BindView(R.id.acMain_rvRepoList)
    RecyclerView rvRepoList;

    @BindView(R.id.acMain_pbProgress)
    ProgressBar pbProgress;
    @BindView(R.id.acMain_pbProgressLoadMore)
    ProgressBar pbProgressLoadMore;


    private int spanCount = 1;
    private int currentPage = 1;
    private final int pageItemCount = 15;
    private final String username = "JakeWharton";
    private final String type = "owner";
    private ArrayList<RepoInfo> repoInfolist = new ArrayList<>();

    private RepoInfoAdapter repoInfoListAdapter;
    private EndlessRecyclerViewScrollListener onRVScrollListener;
    private ShakeDetector sd;
    private SensorManager sensorManager;


    private final String KEY_REPOLIST = "KEY_REPOLIST";
    private final String KEY_SPANCOUNT = "KEY_SPANCOUNT";
    private final String KEY_CURRENTPAGE = "KEY_CURRENTPAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sd = new ShakeDetector(this);


        if (savedInstanceState == null) {
            currentPage = 1;
            getRepoListFromAPI(currentPage);
            DialogUtil.showDialogMessage(MainActivity.this, getString(R.string.welcomeMessage));
        } else {
            repoInfolist = (ArrayList<RepoInfo>) savedInstanceState.getSerializable(KEY_REPOLIST);
            spanCount = savedInstanceState.getInt(KEY_SPANCOUNT);
            currentPage = savedInstanceState.getInt(KEY_CURRENTPAGE);

            pbProgress.setVisibility(View.GONE);
            repoInfoListAdapter = new RepoInfoAdapter(repoInfolist);
            StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
            rvRepoList.setLayoutManager(mLayoutManager);
            rvRepoList.setItemAnimator(new DefaultItemAnimator());
            rvRepoList.setAdapter(repoInfoListAdapter);
            Log.e("savedInstncState Page: ", currentPage + " deserialized list size: " + repoInfolist.size());
            onRVScrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager, currentPage) {

                @Override
                public void onLoadMore(int page, int totalItemsCount) {
                    onLoadMoreRepo();
                }
            };
            rvRepoList.addOnScrollListener(onRVScrollListener);


        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        sd.start(sensorManager);
    }

    @Override
    protected void onStop() {
        /** Stoping Shake Detector*/
        sd.stop();
        super.onDestroy();

    }

    /**
     * Makes api call for given page
     *
     * @param page
     */
    private void getRepoListFromAPI(final int page) {
        Call<ArrayList<RepoInfo>> call = RestApiClient.getAPI().getUserRepoList(username, type, page, pageItemCount);
        call.enqueue(new Callback<ArrayList<RepoInfo>>() {

            /** API Call Response  */
            @Override
            public void onResponse(Call<ArrayList<RepoInfo>> call, Response<ArrayList<RepoInfo>> response) {
                if (response.body() != null) {
                    if (page == 1) {
                        repoInfolist = response.body();
                        pbProgress.setVisibility(View.INVISIBLE);
                        repoInfoListAdapter = new RepoInfoAdapter(repoInfolist);
                        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
                        rvRepoList.setLayoutManager(mLayoutManager);
                        rvRepoList.setItemAnimator(new DefaultItemAnimator());
                        rvRepoList.setAdapter(repoInfoListAdapter);
                        onRVScrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager, page) {

                            @Override
                            public void onLoadMore(int page, int totalItemsCount) {
                                onLoadMoreRepo();
                            }
                        };
                        rvRepoList.addOnScrollListener(onRVScrollListener);
                    } else {
                        pbProgressLoadMore.setVisibility(View.GONE);
                        // List already have items. we need to append new items to list
                        repoInfolist.addAll(response.body());
                        int curSize = repoInfoListAdapter.getItemCount();
                        repoInfoListAdapter.notifyItemRangeInserted(curSize, repoInfolist.size() - 1);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<RepoInfo>> call, Throwable t) {
                DialogUtil.showDialogMessage(MainActivity.this, t.getMessage());
                pbProgress.setVisibility(View.GONE);
                pbProgressLoadMore.setVisibility(View.GONE);
            }
        });
    }

    private void onLoadMoreRepo() {
        currentPage++;
        pbProgressLoadMore.setVisibility(View.VISIBLE);
        getRepoListFromAPI(currentPage);
    }


    /**
     * Shake Detection Listener
     */
    @Override
    public void hearShake() {
        if (repoInfolist.size() > 0) {
            rvRepoList.removeOnScrollListener(onRVScrollListener);
            spanCount++;
            if (spanCount > 3)
                spanCount = 1;
            int firstVisiblePos = ((StaggeredGridLayoutManager) rvRepoList.getLayoutManager()).findFirstVisibleItemPositions(null)[0];
            StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
            rvRepoList.setLayoutManager(mLayoutManager);
            rvRepoList.setAdapter(repoInfoListAdapter);
            // We have to set new LayoutManager to OnScrollListener
            onRVScrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager, currentPage) {

                @Override
                public void onLoadMore(int page, int totalItemsCount) {
                    onLoadMoreRepo();
                }
            };
            rvRepoList.addOnScrollListener(onRVScrollListener);
            rvRepoList.scrollToPosition(firstVisiblePos);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        /** To support screen orientation change*/
        outState.putSerializable(KEY_REPOLIST, repoInfolist);
        outState.putInt(KEY_CURRENTPAGE, currentPage);
        outState.putInt(KEY_SPANCOUNT, spanCount);
        super.onSaveInstanceState(outState);
    }

}
