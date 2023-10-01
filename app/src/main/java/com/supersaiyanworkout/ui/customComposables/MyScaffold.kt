package com.supersaiyanworkout.ui.customComposables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.supersaiyanworkout.network.ConnectionUtil.connectivityState
import com.supersaiyanworkout.network.sealed.ConnectionState
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn( ExperimentalCoroutinesApi::class)
@Composable
fun MyScaffold(
    modifier: Modifier = Modifier,
    showConnectivity:Boolean = true,
    navHostController: NavHostController,
    snackBarState: SnackbarHostState = remember { SnackbarHostState() },
    content: @Composable (SnackbarHostState) -> Unit,
    bottomBar: @Composable (() -> Unit)? = null,
) {
    //val snackBarHostState = remember { SnackbarHostState() }
    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackBarState) },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .imePadding()
            ) {
                if(showConnectivity){
                    ConnectivityStatus(isConnected)
                }
                content(snackBarState)
            }
        }, bottomBar = { bottomBar?.invoke() })
}
