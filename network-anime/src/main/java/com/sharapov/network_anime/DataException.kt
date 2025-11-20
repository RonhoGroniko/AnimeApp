package com.sharapov.network_anime

sealed class DataException(message: String, cause: Throwable? = null) : Exception(message, cause) {
    class Network(cause: Throwable) : DataException("Network error", cause)
    class Server(cause: Throwable) : DataException("Server error", cause)
    class Unknown(cause: Throwable) : DataException("Unknown error", cause)
}