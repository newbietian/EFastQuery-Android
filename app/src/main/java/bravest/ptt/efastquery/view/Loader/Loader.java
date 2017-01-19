package bravest.ptt.efastquery.view.Loader;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import java.util.ArrayList;

import bravest.ptt.efastquery.model.HistoryModule;
import bravest.ptt.efastquery.provider.HistoryManager;

/**
 * Created by pengtian on 2017/1/19.
 */

public class Loader extends AsyncTask {

    private HistoryManager mHm;

    private View mProgressBar;

    private Loader(HistoryManager hm) {
        this.mHm = hm;
    }

    public static Loader init(HistoryManager hm) {
        return new Loader(hm);
    }

    public Loader progress(View progress) {
        mProgressBar = progress;
        return this;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        if (mHm == null) {
            return null;
        }
        return mHm.getAllHistory();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if (mProgressBar != null && mProgressBar.getVisibility() == View.VISIBLE) {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        if (mProgressBar != null && mProgressBar.getVisibility() == View.VISIBLE) {
            mProgressBar.setVisibility(View.GONE);
        }
    }
}
