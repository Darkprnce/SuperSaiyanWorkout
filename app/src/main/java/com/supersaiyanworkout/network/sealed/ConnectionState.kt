package com.supersaiyanworkout.network.sealed

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}