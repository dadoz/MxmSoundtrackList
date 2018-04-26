package com.application.mxm.soundtracks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.application.mxm.soundtracks.lyric.LyricActivity;
import com.application.mxm.soundtracks.ui.RepoOwnerDataView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RepoOwnerDataView repoOwnerDataView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindView();
        onInitView();
    }

    private void bindView() {
        repoOwnerDataView = findViewById(R.id.repoOwnerDataViewId);
    }

    /**
     * init view to handle button in custom view interaction
     */
    private void onInitView() {
        repoOwnerDataView.setFindButtonOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (!repoOwnerDataView.isValidInputData()) {
            repoOwnerDataView.setErrorInputData();
            return;
        }

        startActivity(new Intent(this, LyricActivity.class));
    }
}
