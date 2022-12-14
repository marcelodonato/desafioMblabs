package com.marcelodonato.desafiomblabs.controller;

import androidx.annotation.NonNull;

import com.marcelodonato.desafiomblabs.data.repository.MBLabsException;

public interface ControllerListener<T> {

    void onSuccess(@NonNull final T result);

    void onError(@NonNull final MBLabsException errorCode);

    interface Simple {

        void onSuccess();

        void onError();
    }
}
