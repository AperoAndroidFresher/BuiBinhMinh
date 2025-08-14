package com.example.buibinhminh.ui.playlistSong.dragdrop

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun <T> DragDropList(
    items: List<T>,
    onMove: (Int, Int) -> Unit,
    modifier: Modifier = Modifier,
    itemContent: @Composable (T, Boolean) -> Unit
) {
    val scope = rememberCoroutineScope()
    var overscrollJob by remember { mutableStateOf<Job?>(null) }
    val dragDropState = remember {
        object {
            var draggingItemIndex by mutableStateOf<Int?>(null)
            var draggingItemOffset by mutableFloatStateOf(0f)

            val draggingItemKey: Any?
                get() = draggingItemIndex?.let { items[it] }

            fun onDragStart(offset: Int) {
                draggingItemIndex = offset
            }

            fun onDragInterrupted() {
                draggingItemIndex = null
                draggingItemOffset = 0f
            }

            fun onDrag(offset: Float) {
                draggingItemOffset += offset
            }

            fun onSwap(from: Int, to: Int) {
                onMove(from, to)
                draggingItemIndex = to
            }
        }
    }

    val listState = rememberLazyListState()

    LazyColumn(
        modifier = modifier
            .pointerInput(Unit) {
                detectDragGesturesAfterLongPress(
                    onDragStart = { offset ->
                        listState.layoutInfo.visibleItemsInfo
                            .firstOrNull { item -> offset.y.toInt() in item.offset..(item.offset + item.size) }
                            ?.also {
                                dragDropState.onDragStart(it.index)
                            }
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        dragDropState.onDrag(dragAmount.y)

                        // Logic tự động cuộn khi kéo đến cạnh
                        val topThreshold = 150
                        val bottomThreshold = listState.layoutInfo.viewportEndOffset - 150
                        val currentY = change.position.y

                        overscrollJob?.cancel()
                        if (currentY < topThreshold) {
                            overscrollJob = scope.launch { listState.scrollBy(-10f) }
                        } else if (currentY > bottomThreshold) {
                            overscrollJob = scope.launch { listState.scrollBy(10f) }
                        }

                        // Logic hoán đổi vị trí
                        val draggingItem = dragDropState.draggingItemIndex?.let { listState.layoutInfo.visibleItemsInfo.getOrNull(it - listState.firstVisibleItemIndex) }
                        if (draggingItem != null) {
                            val startOffset = draggingItem.offset + dragDropState.draggingItemOffset
                            val endOffset = startOffset + draggingItem.size

                            listState.layoutInfo.visibleItemsInfo
                                .filter { item -> item.index != dragDropState.draggingItemIndex }
                                .firstOrNull {
                                    val isMovingUp = dragDropState.draggingItemOffset < 0
                                    if (isMovingUp) {
                                        startOffset < it.offset + it.size / 2
                                    } else {
                                        endOffset > it.offset + it.size / 2
                                    }
                                }?.also {
                                    dragDropState.onSwap(draggingItem.index, it.index)
                                }
                        }
                    },
                    onDragEnd = {
                        overscrollJob?.cancel()
                        dragDropState.onDragInterrupted()
                    },
                    onDragCancel = {
                        overscrollJob?.cancel()
                        dragDropState.onDragInterrupted()
                    }
                )
            },
        state = listState
    ) {
        itemsIndexed(items, key = { _, item -> item.hashCode() }) { index, item ->
            val isDragging = index == dragDropState.draggingItemIndex
            val displacement by animateFloatAsState(
                if (isDragging) dragDropState.draggingItemOffset else 0f
            )

            Box(
                modifier = Modifier
                    .graphicsLayer {
                        translationY = displacement
                        shadowElevation = if (isDragging) 8f else 0f
                        alpha = if (isDragging && dragDropState.draggingItemKey != item) 0.5f else 1f
                    }
            ) {
                itemContent(item, isDragging)
            }
        }
    }
}
