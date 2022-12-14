package com.marcelodonato.desafiomblabs.mechanism.camera;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

public interface RetrieveImageListener {
    void onRetrieveImagePath(@NonNull final String path, @NonNull final Bitmap bitmap);
}
