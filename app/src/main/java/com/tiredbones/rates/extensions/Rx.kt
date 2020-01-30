package com.tiredbones.rates.extensions

import io.reactivex.Observable
import io.reactivex.ObservableOperator
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action

/**
 * Add the disposable to a CompositeDisposable.
 * @param compositeDisposable CompositeDisposable to add this disposable to
 * @return this instance
 */
fun Disposable.addTo(compositeDisposable: CompositeDisposable): Disposable =
    apply { compositeDisposable.add(this) }

/**
 * This operator calls the given action on the first onNext emission.
 */
fun <T> Observable<T>.doOnFirst(action: () -> Unit): Observable<T> = lift(DoOnFirstOperator(Action { action() }))

private class DoOnFirstObserver<T>(
    private val downstream: Observer<T>,
    private val action: Action
) : Observer<T>, Disposable {

  private var upstream: Disposable? = null
  private var done: Boolean = false

  override fun onSubscribe(d: Disposable) {
    if (upstream != null) {
      d.dispose()
    } else {
      upstream = d
      downstream.onSubscribe(this)
    }
  }

  override fun onNext(item: T) {
    downstream.onNext(item)
    if (!done) {
      action.run()
      done = true
    }
  }

  override fun onError(throwable: Throwable) {
    downstream.onError(throwable)
  }

  override fun onComplete() {
    downstream.onComplete()
  }


  override fun dispose() {
    upstream?.dispose()
  }

  override fun isDisposed(): Boolean {
    return upstream?.isDisposed ?: true
  }
}

private class DoOnFirstOperator<T>(private val action: Action) : ObservableOperator<T, T> {

  override fun apply(observer: Observer<in T>): Observer<in T> {
    return DoOnFirstObserver(observer, action)
  }
}
