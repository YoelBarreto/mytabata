package com.yoel.mytabata

import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yoel.mytabata.ui.theme.MytabataTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MytabataTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Counter(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
fun Counter(modifier: Modifier = Modifier) {
    var number by remember { mutableStateOf(30) }
    val countdown: Long by remember { mutableStateOf(30*1000) }
    var theCounter by remember { mutableStateOf("${number}") }
    var counterState by remember { mutableStateOf(false) }
    var myCounter: CountDownTimer? by remember { mutableStateOf(null) }


    LaunchedEffect(Unit) {
        myCounter = object : CountDownTimer(countdown, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                theCounter = (theCounter.toInt() - 1).toString()
            }

            override fun onFinish() {
                counterState = false
            }
        }

        if (!counterState) {
            counterState = true
        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
            .background(Color(0xFFffaf42))
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = theCounter,
            modifier = Modifier,
            fontSize = 80.sp
        )
        Text(
            modifier = Modifier
                .alpha(0.5f)
            ,
            fontSize = 50.sp,
            text = "!PREPARATE¡"
        )
        Button(
            modifier = Modifier
                .padding(top = 10.dp),
            onClick = {
                if (counterState) {
                    myCounter?.cancel()
                    counterState = false
                } else {
                    myCounter?.start()
                    counterState = true
                }
            }
        ) {
            Text(
                fontSize = 40.sp
                ,
                text = if (counterState) "┃┃" else " ► "
            )
        }
    }
}
