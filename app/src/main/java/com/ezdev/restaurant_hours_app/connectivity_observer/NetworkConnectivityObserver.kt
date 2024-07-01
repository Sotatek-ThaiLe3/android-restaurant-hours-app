package com.ezdev.restaurant_hours_app.connectivity_observer

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

class NetworkConnectivityObserver @Inject constructor(
    @ApplicationContext context: Context
) : ConnectivityObserver {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun observe(): Flow<ConnectivityObserver.Status> = callbackFlow {
        val currentStatus: ConnectivityObserver.Status = connectivityManager.activeNetwork?.let {
            ConnectivityObserver.Status.AVAILABLE
        } ?: ConnectivityObserver.Status.UNAVAILABLE
        launch {
            send(currentStatus)
        }

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                launch {
                    send(ConnectivityObserver.Status.AVAILABLE)
                }
            }

            override fun onUnavailable() {
                super.onUnavailable()
                launch {
                    send(ConnectivityObserver.Status.UNAVAILABLE)
                }
            }

            override fun onLosing(network: Network, maxMsToLive: Int) {
                super.onLosing(network, maxMsToLive)
                launch {
                    send(ConnectivityObserver.Status.LOSING)
                }
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                launch {
                    send(ConnectivityObserver.Status.LOST)
                }
            }
        }

        connectivityManager.registerDefaultNetworkCallback(callback)
        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }.distinctUntilChanged()
}