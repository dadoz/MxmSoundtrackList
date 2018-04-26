package com.application.mxm.soundtracks.ui;

import android.content.Context;
import android.os.Parcelable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.application.mxm.soundtracks.R;

import icepick.Icepick;
import icepick.State;

public class RepoOwnerDataView extends RelativeLayout {
    Button findButton;
    TextInputLayout ownerTextInputLayout;
    TextInputLayout repoTextInputLayout;
    @State
    String repo;
    @State
    String owner;

    public RepoOwnerDataView(Context context) {
        super(context);
        initView();
    }

    public RepoOwnerDataView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RepoOwnerDataView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View view = inflate(getContext(), R.layout.repo_owner_textinput_layout, this);
        findButton = (Button) view.findViewById(R.id.findButtonId);
        ownerTextInputLayout = (TextInputLayout) view.findViewById(R.id.ownerTextInputLayoutId);
        repoTextInputLayout = (TextInputLayout) view.findViewById(R.id.repoTextInputLayoutId);
        ownerTextInputLayout.getEditText().addTextChangedListener(new TextWatcherImpl("owner"));
        repoTextInputLayout.getEditText().addTextChangedListener(new TextWatcherImpl("repo"));
    }

    public void setFindButtonOnClickListener(OnClickListener listener) {
        findButton.setOnClickListener(listener);
    }

    public String getOwner() {
        return ownerTextInputLayout.getEditText().getText().toString();
    }

    public String getRepo() {
        return repoTextInputLayout.getEditText().getText().toString();
    }

    public boolean isValidInputData() {
        return !repoTextInputLayout.getEditText().getText().toString().equals("") &&
                !ownerTextInputLayout.getEditText().getText().toString().equals("");
    }

    public void setErrorInputData() {
        if (repoTextInputLayout.getEditText().getText().toString().equals(""))
            repoTextInputLayout.setError(getContext().getString(R.string.no_input_data));

        if (ownerTextInputLayout.getEditText().getText().toString().equals(""))
            ownerTextInputLayout.setError(getContext().getString(R.string.no_input_data));
    }

    @Override public Parcelable onSaveInstanceState() {
        return Icepick.saveInstanceState(this, super.onSaveInstanceState());
    }

    @Override public void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(Icepick.restoreInstanceState(this, state));
        ownerTextInputLayout.getEditText().setText(owner);
        repoTextInputLayout.getEditText().setText(repo);
    }


    /**
     * custom imple of text watcher
     */
    private class TextWatcherImpl implements TextWatcher {
        private final String type;

        TextWatcherImpl(String type) {
            this.type = type;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (type) {
                case "repo":
                    repo = repoTextInputLayout.getEditText().getText().toString();
                    repoTextInputLayout.setError(null);
                    break;
                case "owner":
                    owner = ownerTextInputLayout.getEditText().getText().toString();
                    ownerTextInputLayout.setError(null);
                    break;
            }
        }
    }
}
