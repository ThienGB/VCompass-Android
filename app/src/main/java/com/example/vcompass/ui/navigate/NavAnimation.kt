package com.example.vcompass.ui.navigate

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavBackStackEntry

private const val durationMilli = 500

@OptIn(ExperimentalAnimationApi::class)
fun AnimatedContentTransitionScope<NavBackStackEntry>.defaultEnterTransition(
    initial: NavBackStackEntry,
    target: NavBackStackEntry,
) = slideIntoContainer(
    towards = AnimatedContentTransitionScope.SlideDirection.Left,
    animationSpec = tween(durationMilli)
) + fadeIn(animationSpec = tween(durationMilli))

@OptIn(ExperimentalAnimationApi::class)
fun AnimatedContentTransitionScope<NavBackStackEntry>.defaultExitTransition(
    initial: NavBackStackEntry,
    target: NavBackStackEntry,
) = slideOutOfContainer(
    towards = AnimatedContentTransitionScope.SlideDirection.Left,
    animationSpec = tween(durationMilli)
) + fadeOut(animationSpec = tween(durationMilli))

@OptIn(ExperimentalAnimationApi::class)
fun AnimatedContentTransitionScope<NavBackStackEntry>.defaultPopEnterTransition(
    initial: NavBackStackEntry,
    target: NavBackStackEntry,
) = slideIntoContainer(
    towards = AnimatedContentTransitionScope.SlideDirection.Right,
    animationSpec = tween(durationMilli)
) + fadeIn(animationSpec = tween(durationMilli))

@OptIn(ExperimentalAnimationApi::class)
fun AnimatedContentTransitionScope<NavBackStackEntry>.defaultPopExitTransition(
    initial: NavBackStackEntry,
    target: NavBackStackEntry,
) = slideOutOfContainer(
    towards = AnimatedContentTransitionScope.SlideDirection.Right,
    animationSpec = tween(durationMilli)
) + fadeOut(animationSpec = tween(durationMilli))
