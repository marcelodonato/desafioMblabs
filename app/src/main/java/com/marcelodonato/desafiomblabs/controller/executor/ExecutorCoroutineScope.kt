package com.marcelodonato.desafiomblabs.controller.executor

import com.marcelodonato.desafiomblabs.data.repository.MBLabsException
import kotlinx.coroutines.Deferred


interface ExecutorCoroutineScope {
    fun cancelJobs()
    fun runCoroutine(run: suspend () -> Unit): ConcurrencyContinuation = ConcurrencyContinuation(run)
    suspend fun <T> runAsync(run: suspend () -> T): Deferred<T>
    infix fun ConcurrencyContinuation.onError(onError: (MBLabsException) -> Unit)
}

inline class ConcurrencyContinuation(val action: suspend () -> Unit)