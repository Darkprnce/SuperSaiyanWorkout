package com.supersaiyanworkout.ui.customComposables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.supersaiyanworkout.model.BottomNavigationItem
import kotlin.math.roundToInt

@Composable
fun CustomBottomBar(
    modifier: Modifier = Modifier,
    list: List<BottomNavigationItem>,
    navController: NavHostController,
    isBottomBarVisible: Boolean = false,
    selected: Int = 0,
    roundCorner: Dp = 20.dp,
    paddingValues: PaddingValues = PaddingValues(bottom = 10.dp),
    innerPaddingValues: PaddingValues = PaddingValues(horizontal = 5.dp),
    elevation: Dp = 3.dp,
    indicatorCorner: RoundedCornerShape = RoundedCornerShape(bottomStart = 5.dp, bottomEnd = 5.dp),
    indicatorHeight: Dp = 5.dp,
    indicatorWidth: Dp = 30.dp,
    indicatorColor: Color = MaterialTheme.colorScheme.primary,
    indicatorPosTop:Boolean =true,
    indicatorShow:Boolean =true,
    itemWidth: Dp = 70.dp,
    iconColor: Color = MaterialTheme.colorScheme.primary,
    txtColor: Color = MaterialTheme.colorScheme.onSurface,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant
) {
    var navPos by remember { mutableIntStateOf(0) }
    var lastpos by remember { mutableStateOf(selected) }
    if (selected != lastpos) {
        lastpos = navPos
    }
    navPos = selected

    val itemWidthPx = with(LocalDensity.current) {
        itemWidth.toPx()
    }

    val indicatorWidthPx = with(LocalDensity.current) {
        indicatorWidth.toPx()
    }

    val centerOffset = ((itemWidthPx - indicatorWidthPx) / 2) + (navPos * itemWidthPx)

    val targetOffset = IntOffset(
        x = centerOffset.roundToInt(),
        y = 0
    )
    val offset by animateIntOffsetAsState(
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        targetValue = targetOffset,
        label = "offset"
    )

    val scrollState = rememberScrollState()

    AnimatedVisibility(
        visible = isBottomBarVisible,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = elevation),
                    colors = CardDefaults.cardColors(containerColor = backgroundColor),
                    shape = RoundedCornerShape(roundCorner),
                    modifier = Modifier.padding(paddingValues)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(innerPaddingValues)
                            .horizontalScroll(scrollState)
                    ){
                    Column {
                        if(indicatorShow){
                            if(indicatorPosTop){
                                Box(
                                    modifier = Modifier
                                        .offset { offset }
                                        .graphicsLayer {
                                            scaleX = 1f
                                            scaleY = 1f
                                        }
                                        .clip(indicatorCorner)
                                        .height(indicatorHeight)
                                        .width(indicatorWidth)
                                        .background(indicatorColor)
                                )
                            }
                        }

                        Row(
                            modifier = Modifier
                                .padding(innerPaddingValues)
                        ) {
                            list.forEachIndexed { index, navigationItem ->
                                val animVisibleState =
                                    remember { MutableTransitionState(false) }

                                animVisibleState.targetState = index == navPos

                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .width(itemWidth)
                                        .noRippleClickable {
                                            lastpos = navPos
                                            navPos = index
                                            navController.navigate(
                                                navigationItem.route
                                            ) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        },
                                ) {
                                    androidx.compose.animation.AnimatedVisibility(
                                        visibleState = animVisibleState,
                                        enter = slideInVertically(
                                            animationSpec = spring(
                                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                                stiffness = Spring.StiffnessLow
                                            ),
                                            initialOffsetY = { it }),
                                        exit = slideOutVertically(
                                            targetOffsetY = { it }),
                                        content = {
                                            CustomText(
                                                navigationItem.label,
                                                isheading = true,
                                                maxLine = 1,
                                                color = txtColor,
                                                modifier = Modifier.padding(
                                                    vertical = 10.dp
                                                )
                                            )
                                        })
                                    androidx.compose.animation.AnimatedVisibility(
                                        //visible = !animVisibleState.currentState && index != navPos,
                                        visible = index != navPos,
                                        enter = slideInVertically(
                                            animationSpec = spring(
                                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                                stiffness = Spring.StiffnessLow
                                            ),
                                            initialOffsetY = { -it }),
                                        exit = slideOutVertically(
                                            targetOffsetY = { -it }),
                                        content = {
                                            Icon(
                                                navigationItem.icon,
                                                tint = iconColor,
                                                contentDescription = "bottomnav_${navigationItem.label}",
                                                modifier = Modifier.padding(
                                                    10.dp
                                                )
                                            )
                                        })
                                }
                            }
                        }
                        if(indicatorShow){
                            if(!indicatorPosTop){
                                Box(
                                    modifier = Modifier
                                        .offset { offset }
                                        .graphicsLayer {
                                            scaleX = 1f
                                            scaleY = 1f
                                        }
                                        .clip(indicatorCorner)
                                        .height(indicatorHeight)
                                        .width(indicatorWidth)
                                        .background(indicatorColor)
                                )
                            }
                        }
                    }
                    }
                }
            }
        })

}