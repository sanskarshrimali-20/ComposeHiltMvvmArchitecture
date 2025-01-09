package com.newcompose.composehiltmvvm.util

import androidx.compose.foundation.lazy.LazyListState

object ExtensionFunctions {

    val LazyListState.isLastItemVisible: Boolean
        get() = layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1

}