package com.application.mxm.soundtracks.tracklist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.application.mxm.soundtracks.R;
import com.application.mxm.soundtracks.lyric.LyricActivity;
import com.application.mxm.soundtracks.ui.EmptyView;
import com.application.mxm.soundtracks.utils.Utils;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

import static com.application.mxm.soundtracks.MainActivity.TRACK_PARAMS_KEY;

/**
 * stargazer activity
 */
public class TrackListActivity extends DaggerAppCompatActivity implements TrackContract.TrackView, TrackListAdapter.OnTrackItemClickListener {
    public static final String LYRICS_PARAMS_KEY = "LYRICS_PARAMS_KEY";
    RecyclerView recyclerView;
    ProgressBar progressBar;
    EmptyView emptyView;

    @Inject
    TrackPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_list);

        bindView();
        onInitView();
    }

    /**
     * TODO move to butterknife
     */
    private void bindView() {
        recyclerView =  findViewById(R.id.stargazerRecyclerViewId);
        progressBar = findViewById(R.id.stargazerProgressbarId);
        emptyView = findViewById(R.id.emptyViewId);
    }

    /**
     * iit view and retrieve stargazers data
     */
    private void onInitView() {
        initActionbar();
        presenter.bindView(this);
        presenter.retrieveItems(Utils.getTrackParamsFromBundle(getIntent().getExtras().getBundle(TRACK_PARAMS_KEY)));
    }

    /**
     * TODO mv to base activity
     * actionbar set listener and back arrow
     */
    private void initActionbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * todo mv on base
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (presenter != null)
                    presenter.unsubscribe();
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRenderData(List<?> items) {
        progressBar.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        initRecyclerView(items);
    }


    @Override
    public void onError(String error) {
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        Snackbar.make(findViewById(R.id.activity_main), R.string.retrieve_error,
                Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showStandardLoading() {
        Toast.makeText(this, "show loader", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideStandardLoading() {
        Toast.makeText(this, "hide loader", Toast.LENGTH_SHORT).show();
    }

    /**
     * init recycler view binding data by adapter
     * @param items
     */
    private void initRecyclerView(List<?> items) {
        if (items.size() == 0) {
            return;
        }
        recyclerView.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new TrackListAdapter(items, this));
    }

    @Override
    public void onTrackItemClick(View view, int position) {
        Bundle bundle = Utils.buildLyricsParams("bllalala");
        Intent intent = new Intent(this, LyricActivity.class);
        intent.putExtra(LYRICS_PARAMS_KEY, bundle);
        startActivity(intent);

    }

    /**
     *
     * @param context
     * @param country
     * @param pageSize
     * @param hasLyricsCheckbox
     * @param initialPage
     * @return
     */
    public static Intent buildIntent(Context context, String country, String pageSize, String hasLyricsCheckbox, String initialPage) {
        Bundle bundle = Utils.buildTrackParams(country, pageSize, hasLyricsCheckbox, initialPage);
        Intent intent = new Intent(context, TrackListActivity.class);
        intent.putExtra(TRACK_PARAMS_KEY, bundle);
        return intent;
    }

}
