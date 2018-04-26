package com.application.mxm.soundtracks.utils;

import android.content.res.AssetManager;
import android.util.SparseArray;

import java.io.InputStream;

public class Utils {
    /**
     * build params to get request
     * @return
     */
    public static SparseArray<String> buildParams(String owner, String repo) {
        SparseArray<String> params = new SparseArray<>();
        params.put(0, owner);
        params.put(1, repo);
        return params;
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
}
