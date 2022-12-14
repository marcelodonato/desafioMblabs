package com.marcelodonato.desafiomblabs.controller;

import android.os.AsyncTask;
import androidx.annotation.NonNull;

import com.marcelodonato.desafiomblabs.data.repository.MBLabsException;

public abstract class BaseAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    private MBLabsException mErrorCode = null;
    private ControllerListener mControllerListener = null;

    public BaseAsyncTask(@NonNull final ControllerListener callback) {
        mControllerListener = callback;
    }

    protected abstract Result onBackground();

    @SafeVarargs
    @Override
    protected final Result doInBackground(Params... params) {
        try {
            return onBackground();
        } catch (MBLabsException b) {
            mErrorCode = b;
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onPostExecute(Object result) {
        if (mControllerListener != null) {
            if (mErrorCode == null) {
                mControllerListener.onSuccess(result);
            } else {
                mControllerListener.onError(mErrorCode);
            }
        }
    }
}
