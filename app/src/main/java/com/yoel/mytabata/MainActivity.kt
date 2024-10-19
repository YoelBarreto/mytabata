
/*

Funcionamiento importante:
Los sets funcionan y cuando termina pasa a una pantalla de completado.
Corre el tiempo que muestra en la pantalla.
Interfaz intuitiva con colores comfortantes.

-------------------------------------------------------------------------

Errores:
Al pausar se reinicia el contador y llegara a números negativos.
No tiene interfaz interactiva la cual pones los datos.
El contador se repite en cada pantalla y no en una clase para cambiarlo.

*/

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
import androidx.compose.ui.text.font.FontWeight
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
                    AppNavigator(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}


@Composable
fun AppNavigator(modifier: Modifier = Modifier) {
    var sets by remember { mutableStateOf(3) }
    var showCounter by remember { mutableStateOf(0) }

    if (sets > 0) {
        when (showCounter) {
            0 -> Espera(
                modifier = modifier,
                setsRemaining = sets,
                onTimerFinish = { showCounter = 1 }
            )
            1 -> Workout(
                modifier = modifier,
                setsRemaining = sets,
                onTimerFinish = { showCounter = 2 }
            )
            2 -> Rest(
                modifier = modifier,
                setsRemaining = sets,
                onTimerFinish = {
                    showCounter = 0
                    sets -= 1
                }
            )
        }
    } else {
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
                modifier = Modifier
                    .alpha(0.5f)
                ,
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                text = "¡COMPLETADO!"
            )
        }
    }
}


// Contador antes del Workout
@Composable
fun Espera(modifier: Modifier = Modifier, setsRemaining: Int, onTimerFinish: () -> Unit) {
    var number: Long by remember { mutableStateOf(5) }
    var theCounter by remember { mutableStateOf(number) }
    val countdown: Long by remember { mutableStateOf(number*1000) }
    var counterState by remember { mutableStateOf(false) }
    var myCounter: CountDownTimer? by remember { mutableStateOf(null) }


    LaunchedEffect(Unit) {
        myCounter = object : CountDownTimer(countdown, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                theCounter = (theCounter - 1)
            }

            override fun onFinish() {
                counterState = false
                onTimerFinish()
            }
        }
        if (!counterState) {
            myCounter?.start()
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
            modifier = Modifier
                .alpha(0.5f)
            ,
            text = "$setsRemaining",
            fontSize = 40.sp
        )
        Text(
            text = theCounter.toString(),
            modifier = Modifier,
            fontSize = 80.sp
        )
        Text(
            modifier = Modifier
                .alpha(0.5f)
            ,
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            text = "¡PREPARATE!"
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
                text = if (counterState) " Pausar " else " Reanudar "
            )
        }
    }
}

@Composable
fun Workout(modifier: Modifier = Modifier, setsRemaining: Int, onTimerFinish: () -> Unit) {
    var number: Long by remember { mutableStateOf(5) }
    var theCounter by remember { mutableStateOf("${number}") }
    val countdown: Long by remember { mutableStateOf(number*1000) }
    var counterState by remember { mutableStateOf(false) }
    var myCounter: CountDownTimer? by remember { mutableStateOf(null) }


    LaunchedEffect(Unit) {
        myCounter = object : CountDownTimer(countdown, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                theCounter = (theCounter.toInt() - 1).toString()
            }

            override fun onFinish() {
                counterState = true
                onTimerFinish()
            }
        }
        if (!counterState) {
            myCounter?.start()
            counterState = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
            .background(Color(0xFF44e372))
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .alpha(0.5f)
            ,
            text = "$setsRemaining",
            fontSize = 40.sp
        )
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
            fontWeight = FontWeight.Bold,
            color = Color.White,
            text = "¡TRABAJA!"
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
                text = if (counterState) " Pausar " else " Reanudar "
            )
        }
    }
}

@Composable
fun Rest(modifier: Modifier = Modifier, setsRemaining: Int, onTimerFinish: () -> Unit) {
    var number: Long by remember { mutableStateOf(5) }
    val countdown: Long by remember { mutableStateOf(number*1000) }
    var theCounter by remember { mutableStateOf("${number}") }
    var counterState by remember { mutableStateOf(false) }
    var myCounter: CountDownTimer? by remember { mutableStateOf(null) }


    LaunchedEffect(Unit) {
        myCounter = object : CountDownTimer(countdown, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                theCounter = (theCounter.toInt() - 1).toString()
            }

            override fun onFinish() {
                counterState = true
                onTimerFinish()
            }
        }
        if (!counterState) {
            myCounter?.start()
            counterState = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
            .background(Color(0xFF1293FF))
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .alpha(0.5f)
            ,
            text = "$setsRemaining",
            fontSize = 40.sp
        )
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
            fontWeight = FontWeight.Bold,
            color = Color.White,
            text = "DESCANSA"
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
                text = if (counterState) " Pausar " else " Reanudar "
            )
        }
    }
}