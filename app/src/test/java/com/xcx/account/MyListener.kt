package com.xcx.account

/**
 * Created by xuchongxiang on 2021年01月06日.
 */
class MyListener {

    private var l: Listener? = null

    fun setListener(a: Listener.() -> Unit) {
       l = Listener().also(a)
    }

    fun zzz() {
//        aaa?.invoke()
        l?.cancel?.invoke()
        l?.ok?.invoke("zzz")
//        ok?.invoke("zzz")
    }

    class Listener {
        var cancel: (() -> Unit)? = null
        var ok: ((String) -> Unit)? = null

        fun cancel(c: () -> Unit) {
            cancel = c
        }

        fun ok(o: (String) -> Unit) {
            ok = o
        }
    }

}