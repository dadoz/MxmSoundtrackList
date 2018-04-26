package com.application.mxm.soundtracks.utils;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.SparseArray;

import java.io.InputStream;

public class Utils {
    /**
     * build params to get request
     * @param page
     * @param pageSize
     * @param country
     * @param fHasLyrics
     * @return
     */
    public static Bundle buildTrackParams(String country, String pageSize, String fHasLyrics, String page) {
        Bundle bundle = new Bundle();
        bundle.putString("PAGE", page);
        bundle.putString("PAGE_SIZE", pageSize);
        bundle.putString("COUNTRY", country);
        bundle.putString("FHASLYRICS", fHasLyrics);
        return bundle;
    }

    /**
     *
     * @param bundle
     * @return
     */
    public static SparseArray<String> getTrackParamsFromBundle(Bundle bundle) {
        SparseArray<String> array = new SparseArray<>();
        array.put(0, bundle.getString("PAGE", null));
        array.put(1, bundle.getString("PAGE_SIZE", null));
        array.put(2, bundle.getString("COUNTRY", null));
        array.put(3, bundle.getString("FHASLYRICS", null));
        return array;
    }

    /**
     *
     * @param trackId
     * @return
     */
    public static Bundle buildLyricsParams(String trackId) {
        Bundle bundle = new Bundle();
        bundle.putString("TRACK_ID", trackId);
        return bundle;
    }

    /**
     *
     * @param bundle
     * @return
     */
    public static SparseArray<String> getLyricsParamsFromBundle(Bundle bundle) {
        SparseArray<String> array = new SparseArray<>();
        array.put(0, bundle.getString("TRACK_ID", null));
        return array;
    }

    /**
     * read file from assets, depending on filename provided
     * @param assets
     * @param filename
     * @return
     */
    public static String readFileFromAssets(AssetManager assets, String filename) {
        try {
            InputStream is = assets.open(filename);
            int size = is.available();

            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            // Convert the buffer into a string.
            return new String(buffer);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getTrackParamsKey(String page, String pageSize, String country, String fHasLyrics) {
        return page + "_" +pageSize + "_" + country + "_" + fHasLyrics;
    }
}
