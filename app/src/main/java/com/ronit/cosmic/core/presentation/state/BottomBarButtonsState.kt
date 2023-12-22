package com.ronit.cosmic.core.presentation.state

import androidx.compose.ui.graphics.painter.Painter

sealed class BottomBarButtonsState(var selected: Boolean=false) {

    object ArticleButton:BottomBarButtonsState()

    object BookmarksButton:BottomBarButtonsState()
}