package com.application.mxm.soundtracks.tracklist;

import android.util.Log;
import android.util.SparseArray;

import com.application.mxm.soundtracks.data.TracksRepository;
import com.application.mxm.soundtracks.ui.ProgressLoader;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class TrackPresenter implements TrackContract.TrackPresenterInterface {
    private static final String TAG = "TrackPresenter";
    private static WeakReference<TrackContract.TrackView> trackView;
    private final TracksRepository repository;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    protected ProgressLoader loader;
    private SparseArray<Object> params = null;

    @Inject
    TrackPresenter(TracksRepository repository) {
        this.repository = repository;
    }

    @Override
    public void unsubscribe() {
        if (compositeDisposable != null)
            compositeDisposable.dispose();
    }

    /**
     * bind view to presenter
     * @param view
     */
    @Override
    public void bindView(TrackContract.TrackView view) {
        trackView = new WeakReference<>(view);
        loader = new ProgressLoader(
                view::showStandardLoading,
                view::hideStandardLoading);
    }

    /**
     * delete view
     */
    @Override
    public void deleteView() {
        trackView.clear();
    }

    /**
     * params
     * 0 -> page
     * 1 -> pageSize
     * 2 -> country
     * 3 -> hasLyrics
     * 
     * retrieve item obs
     * @param params
     */
    @Override
    public void retrieveItems(SparseArray<Object> params) {
        Log.e(TAG, params.toString());
        //set params
        this.params = params;

        //build obs
        compositeDisposable.add(repository
                .getTracks((Integer[]) params.get(0), params.get(1).toString(), params.get(2).toString(), params.get(3).toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(composeLoaderTransformer(loader))
                .doOnError(Throwable::printStackTrace)
                .subscribe(
                        items -> trackView.get().onRenderData(items),
                        error -> trackView.get().onError(error.getMessage())));
    }


    /**
     * compose loader transformer
     * @param loader
     * @param <T>
     * @return
     */
    private <T extends List>ObservableTransformer<T, T> composeLoaderTransformer(ProgressLoader loader) {
        return upstream -> upstream
                .doOnSubscribe(disposable -> loader.show.run())
                .doOnError(error -> loader.hide.run())
                .doOnNext(res -> loader.hide.run());
    }

    /**
     *
     */
    public void retrieveMoreItems() {
        setMorePagedParams();
        //set value
        retrieveItems(params);
    }

    /**
     * get params
     * @return
     */
    public SparseArray<Object> getParams() {
        return params;
    }

    /**
     * get all paged params (from 1 to last page)
     */
    public void setMorePagedParams() {
        Integer[] pages = (Integer[]) params.get(0);
        Integer[] morePages = new Integer[1];
        morePages[0] = pages[pages.length -1] + 1;
        params.setValueAt(0, morePages);
    }

    /**
     * get all paged params (from 1 to last page)
     * @return
     */
    public SparseArray<Object> getAllPagedParams() {
        Integer[] pages = (Integer[]) params.get(0);
        Integer lastPage = pages[pages.length - 1];
        Integer[] allPages = new Integer[lastPage];
        for (int i = 0; i < allPages.length; i ++) {
            allPages[i] = i + 1;
        }

        params.setValueAt(0 , allPages);
        return params;
    }

}
