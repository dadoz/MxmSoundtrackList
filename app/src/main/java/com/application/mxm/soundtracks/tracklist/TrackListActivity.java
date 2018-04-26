package com.application.mxm.soundtracks.tracklist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.application.mxm.soundtracks.R;
import com.application.mxm.soundtracks.data.model.Track;
import com.application.mxm.soundtracks.lyric.LyricActivity;
import com.application.mxm.soundtracks.ui.EmptyView;
import com.application.mxm.soundtracks.utils.Utils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;

import static com.application.mxm.soundtracks.MainActivity.TRACK_PARAMS_KEY;

/**
 * stargazer activity
 */
public class TrackListActivity extends DaggerAppCompatActivity implements TrackContract.TrackView, TrackListAdapter.OnTrackItemClickListener, TrackListAdapter.OnTrackLoadMoreClickListener {
    public static final String LYRICS_PARAMS_KEY = "LYRICS_PARAMS_KEY";
    @BindView(R.id.stargazerRecyclerViewId)
    RecyclerView recyclerView;
    @BindView(R.id.stargazerProgressbarId)
    ProgressBar progressBar;
    @BindView(R.id.emptyViewId)
    EmptyView emptyView;

    @Inject
    TrackPresenter presenter;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_list);
        unbinder = ButterKnife.bind(this);
        onInitView();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null)
            unbinder.unbind();
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
    public void onRenderData(List<Track> items) {
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
    private void initRecyclerView(List<Track> items) {
        if (items.size() == 0) {
            return;
        }
        recyclerView.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        if (recyclerView.getAdapter() == null)
            recyclerView.setAdapter(new TrackListAdapter(items, this, this));
        else
            ((TrackListAdapter) recyclerView.getAdapter()).addItems(items);
    }

    @Override
    public void onTrackItemClick(View view, Track item) {
        Intent intent = LyricActivity.buildIntent(this, item.getTrackId());
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

    @Override
    public void onTrackLoadMoreClick(View view) {
        //increment page
        SparseArray<String> params = presenter.getParams();
        int nextPage = Integer.parseInt(params.get(0)) + 1;
        params.setValueAt(0, Integer.toString(nextPage));

        //retrieve new items
        presenter.retrieveItems(params);
    }
}
