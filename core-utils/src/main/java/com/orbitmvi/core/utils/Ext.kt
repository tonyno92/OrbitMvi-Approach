package com.orbitmvi.core.utils

/**
 * it is a walkaround for mockk framework when it returns a [Result] as type
 *
 * @param R
 * @param T
 * @param onSuccess
 * @param onFailure
 * @return [Result]
 */
inline fun <R, reified T> Result<T>.mockSafeFold(
    onSuccess: (value: T) -> R,
    onFailure: (exception: Throwable) -> R
): R = when {
    isSuccess -> {
        val value = getOrNull()
        try {
            onSuccess(value as T)
        } catch (e: ClassCastException) {
            // This block of code is only executed in testing environment, when we are mocking a
            // function that returns a `Result` object.
            val valueNotNull = value!!
            if ((value as Result<*>).isSuccess) {
                valueNotNull::class.java.getDeclaredField("value").let {
                    it.isAccessible = true
                    it.get(value) as T
                }.let(onSuccess)
            } else {
                valueNotNull::class.java.getDeclaredField("value").let {
                    it.isAccessible = true
                    it.get(value)
                }.let { failure ->
                    failure!!::class.java.getDeclaredField("exception").let {
                        it.isAccessible = true
                        it.get(failure) as Exception
                    }
                }.let(onFailure)
            }
        }
    }
    else -> onFailure(exceptionOrNull() ?: Exception())
}
