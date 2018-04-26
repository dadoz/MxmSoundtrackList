package com.application.mxm.soundtracks.lyric;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.application.mxm.soundtracks.R;
import com.application.mxm.soundtracks.data.model.Lyric;
import com.application.mxm.soundtracks.ui.EmptyView;
import com.application.mxm.soundtracks.utils.Utils;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

import static com.application.mxm.soundtracks.tracklist.TrackListActivity.LYRICS_PARAMS_KEY;

/**
 * stargazer activity
 */
public class LyricActivity extends DaggerAppCompatActivity implements LyricContract.LyricsView {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    EmptyView emptyView;

    @Inject
    LyricPresenter presenter;

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
        presenter.retrieveItems(Utils.getLyricsParamsFromBundle(getIntent().getExtras().getBundle(LYRICS_PARAMS_KEY)));
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
    public void onRenderData(Lyric item) {
        progressBar.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        Log.e(getClass().getName(), item.getLyricsBody());
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

    public static Intent buildIntent(Context context, Integer trackId) {
        Bundle bundle = Utils.buildLyricsParams(Integer.toString(trackId));
        Intent intent = new Intent(context, LyricActivity.class);
        intent.putExtra(LYRICS_PARAMS_KEY, bundle);
        return intent;
    }
}
