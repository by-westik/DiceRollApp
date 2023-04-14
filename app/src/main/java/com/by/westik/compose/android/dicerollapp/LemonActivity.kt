package com.by.westik.compose.android.dicerollapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.by.westik.compose.android.dicerollapp.ui.theme.DiceRollAppTheme

class LemonActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiceRollAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LemonAppWithTextAndImage(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

data class Resources(
    val imageResources: Int,
    val stringResources: Int,
    val imageDescriptionResources: Int
)

@Composable
fun LemonAppWithTextAndImage(modifier: Modifier = Modifier) {
    var result by remember { mutableStateOf(1) }
    var clickNumber by remember { mutableStateOf(0) }
    var enabled  by remember { mutableStateOf(true) }
    val context = LocalContext.current

    val resources = when (result) {
        1 -> Resources(R.drawable.lemon_tree, R.string.lemon_tree_description, R.string.lemon_tree)
        2 -> Resources(R.drawable.lemon_squeeze, R.string.lemon_description, R.string.lemon)
        3 -> Resources(R.drawable.lemon_drink, R.string.glass_description, R.string.glass_of_lemonade)
        else -> Resources(R.drawable.lemon_restart, R.string.empty_glass_description, R.string.empty_glass)
    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(resources.stringResources),
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = resources.imageResources),
            contentDescription = stringResource(id = resources.imageDescriptionResources),
            modifier = Modifier
                .border(2.dp, Color(105, 205, 216),
                    shape = RoundedCornerShape(4.dp)
                )
                .clickable(
                onClick = {
                    when (result) {
                        1 -> {
                            clickNumber = (2..4).random()
                            result++
                            Toast.makeText(context, "Press $clickNumber times to squeeze", Toast.LENGTH_SHORT).show()
                        }
                        2 -> {
                            Toast.makeText(context, "Press ${clickNumber - 1} times to squeeze",
                                Toast.LENGTH_SHORT).show()
                            clickNumber--
                            if (clickNumber == 0) result++

                        }
                        3 -> result++
                        else -> result = 1
                    }
                }
            )
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DiceRollAppTheme {
        LemonAppWithTextAndImage(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        )
    }
}