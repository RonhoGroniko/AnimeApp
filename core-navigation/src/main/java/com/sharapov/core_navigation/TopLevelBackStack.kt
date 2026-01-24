package com.sharapov.core_navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.NavKey

class TopLevelBackStack<T : NavKey>(private val startKey: T) {

    private val topLevelBackStacks: HashMap<T, SnapshotStateList<T>> = hashMapOf(
        startKey to mutableStateListOf(startKey)
    )

    private val topLevelHistory = mutableStateListOf(startKey)

    var topLevelKey by mutableStateOf(startKey)
        private set

    val backStack = mutableStateListOf<T>(startKey)

    private fun updateBackstack() {
        backStack.clear()
        topLevelHistory.forEach { tabKey ->
            val stack = topLevelBackStacks[tabKey] ?: return@forEach
            backStack.addAll(stack)
        }
    }

    fun switchTopLevel(key: T) {
        if (topLevelBackStacks[key] == null) {
            topLevelBackStacks[key] = mutableStateListOf(key)
        }
        topLevelHistory.add(key)
        topLevelKey = key
        updateBackstack()
    }

    fun add(key: T) {
        topLevelBackStacks[topLevelKey]?.add(key)
        updateBackstack()
    }

    fun removeLast() {
        val currentStack = topLevelBackStacks[topLevelKey] ?: return

        if (currentStack.size > 1) {
            currentStack.removeLastOrNull()
        } else {
            if (topLevelHistory.size > 1) {
                topLevelHistory.removeAt(topLevelHistory.lastIndex)
                topLevelKey = topLevelHistory.last()
            } else {
                return
            }
        }
        updateBackstack()
    }

    fun replaceStack(vararg keys: T) {
        topLevelBackStacks[topLevelKey] = mutableStateListOf(*keys)
        updateBackstack()
    }

    fun dropChildStack() {
        replaceStack(topLevelKey)
    }

    companion object {


        fun saver(): Saver<TopLevelBackStack<Screen>, Any> =
            mapSaver(
                save = { state ->
                    mapOf(
                        "startKey" to state.startKey.toRoute(),
                        "topLevelKey" to state.topLevelKey.toRoute(),
                        "stacks" to state.topLevelBackStacks
                            .map { (key, stack) ->
                                key.toRoute() to stack.map { it.toRoute() }
                            },
                        "history" to state.topLevelHistory.map { it.toRoute() }
                    )
                },
                restore = { map ->
                    val startKeyRoute = map["startKey"] as String
                    val topLevelKeyRoute = map["topLevelKey"] as String

                    @Suppress("UNCHECKED_CAST")
                    val stacksEncoded =
                        map["stacks"] as List<Pair<String, List<String>>>

                    val startKey = startKeyRoute.toScreen()
                    val topLevelKey = topLevelKeyRoute.toScreen()

                    @Suppress("UNCHECKED_CAST")
                    val historyRoutes = map["history"] as List<String>

                    TopLevelBackStack(startKey).apply {
                        topLevelBackStacks.clear()
                        stacksEncoded.forEach { (keyRoute, listRoutes) ->
                            val key = keyRoute.toScreen()
                            val stateList = mutableStateListOf<Screen>()
                            stateList.addAll(listRoutes.map { it.toScreen() })
                            topLevelBackStacks[key] = stateList
                            topLevelHistory.clear()
                            topLevelHistory.addAll(historyRoutes.map { it.toScreen() })
                        }
                        this.topLevelKey = topLevelKey
                        updateBackstack()
                    }
                }
            )
    }
}