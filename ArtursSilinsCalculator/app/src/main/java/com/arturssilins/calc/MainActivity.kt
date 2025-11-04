package com.arturssilins.calc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arturssilins.calc.ui.theme.CalcTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<CalculatorViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalcTheme {
                CalculatorApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun CalculatorApp(viewModel: CalculatorViewModel) {
    val buttonSpacing = 8.dp
    val buttonColor = Color(0xFF444444)
    val operatorColor = Color(0xFFFF9800)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(buttonSpacing)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f),
            contentAlignment = Alignment.BottomEnd
        ) {
            Text(
                text = viewModel.displayText.value,
                color = Color.White,
                fontSize = 80.sp,
                textAlign = TextAlign.End,
                maxLines = 1,
                lineHeight = 90.sp
            )
        }

        Column(
            modifier = Modifier.weight(8f),
            verticalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            val buttonRows = listOf(
                listOf("MC", "MR", "MS", "/"),
                listOf("7", "8", "9", "*"),
                listOf("4", "5", "6", "-"),
                listOf("1", "2", "3", "+"),
                listOf("C", "0", ".", "=")
            )

            buttonRows.forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    row.forEach { label ->
                        val color = when (label) {
                            "+", "-", "*", "/" -> operatorColor
                            "=", "C", "MC", "MR", "MS" -> Color.LightGray
                            else -> buttonColor
                        }
                        val textColor = when (label) {
                            "=", "C", "MC", "MR", "MS" -> Color.Black
                            else -> Color.White
                        }

                        CalculatorButton(
                            label = label,
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f),
                            backgroundColor = color,
                            textColor = textColor
                        ) {
                            when (label) {
                                in "0".."9", "." -> viewModel.onNumberClick(label)
                                in "+", "-", "*", "/" -> viewModel.onOperatorClick(label)
                                "=" -> viewModel.onEqualsClick()
                                "C" -> viewModel.onClearClick()
                                "MS" -> viewModel.onMemorySave()
                                "MR" -> viewModel.onMemoryRead()
                                "MC" -> viewModel.onMemoryClear()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CalculatorButton(
    label: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    textColor: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxHeight(),
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        shape = ButtonDefaults.shape // Circular shape
    ) {
        Text(text = label, fontSize = 32.sp, color = textColor)
    }
}