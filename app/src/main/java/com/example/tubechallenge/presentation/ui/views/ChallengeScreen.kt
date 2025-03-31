package com.example.tubechallenge.presentation.ui.views

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tubechallenge.presentation.service.StopwatchService
import com.example.tubechallenge.presentation.viewmodel.StopViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import com.example.tubechallenge.R
import com.example.tubechallenge.domain.utils.Utils
import com.example.tubechallenge.presentation.theme.Purple40
import com.example.tubechallenge.presentation.theme.PurpleGrey40

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChallengeScreen(viewModel: StopViewModel, onNavigateBack: () -> Unit) {

    val context = LocalContext.current
    val uri = Utils.createImageFile(context)
    var capturedImageUri by remember { mutableStateOf<Uri>(Uri.EMPTY) }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
        if (uri != null) {
            capturedImageUri = uri
        }
    }

    BackHandler(enabled = true) {
        if (StopwatchService.isRunning) {

        } else {
            onNavigateBack()
        }
    }

    Scaffold(
        floatingActionButton = {

            FloatingActionButton(onClick = {
                if (uri != null) {
                    cameraLauncher.launch(uri)
                }
            }) {

                Icon(painterResource(R.drawable.baseline_camera_alt_24), "Camera icon")
            }
        }
    ) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Row(horizontalArrangement = Arrangement.Center) {

                val state = StopwatchService.elapsedTime.collectAsState()
                Text(Utils.formatElapsedTime(state.value, true), fontSize = 60.sp)
            }
            Spacer(Modifier.padding(80.dp))
            AddEditStopScreen(viewModel)
            Spacer(Modifier.padding(80.dp))

            Row(verticalAlignment = Alignment.Bottom) {
                val enableFinishButton = remember { mutableStateOf(true) }
                val hapticFeedback = LocalHapticFeedback.current

                PressAndHoldButton(
                    modifier = Modifier,
                    onActivated = {
                        Intent(context, StopwatchService::class.java).also {
                            it.action = "STOP_STOPWATCH"
                            context.startForegroundService(it)
                        }
                        hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                        enableFinishButton.value = false
                    },
                    text = "Hold to End",
                    isEnabled = enableFinishButton.value,
                    longPressThresholdDuration = 2500
                )
            }

        }
    }
}

@Composable
fun PressAndHoldButton(
    modifier: Modifier = Modifier,
    onActivated: () -> Unit,
    text: String,
    isEnabled: Boolean,
    longPressThresholdDuration: Int = 2500
) {
    val scale = remember { Animatable(1f) }
    var isPressed by remember { mutableStateOf(false) }

    LaunchedEffect(isPressed) {
        if (isPressed) {
            // Smoothly animate to 1.5x size (adjustable)
            scale.animateTo(
                targetValue = 1.5f,
                animationSpec = tween(durationMillis = longPressThresholdDuration)
            )
            if (isEnabled) {
                onActivated()
            }
        } else {
            scale.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 300)
            )
        }
    }

    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        try {
                            awaitRelease()
                        } finally {
                            isPressed = false
                        }
                    }
                )
            }
            .graphicsLayer(
                scaleX = scale.value,
                scaleY = scale.value
            )
            .background(if (isEnabled) Purple40 else PurpleGrey40, shape = CircleShape)
            .size(100.dp)
    ) {
        Text(
            text = text,
            color = White,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
