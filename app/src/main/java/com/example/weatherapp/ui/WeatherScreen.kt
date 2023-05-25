package com.example.weatherapp.ui

import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun WeatherScreen(viewModel:WeatherViewModel){
    Column(
        modifier = Modifier.background(Color.Black)
    ) {
        val focusManager = LocalFocusManager.current
        TextField(
            value= viewModel.text.value,
            onValueChange = viewModel::onTextChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .background(Color.DarkGray),
            placeholder = {
                Text(
                    text = "Enter City",
                    color = Color.Black,
                    fontSize = 16.sp,
                )
                          },
            keyboardActions = KeyboardActions(
                onDone = {
                focusManager.clearFocus()
            }),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            trailingIcon = {
                IconButton(onClick = { viewModel.getWeather()}) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search Button")
                }
            },
            singleLine = true,
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp)
        )

        if(viewModel.isLoading.value){
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
        else{
            viewModel.data.collectAsState().value

                ?.let {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box {
                            AsyncImage(model = "https:${it.current.condition.icon}",
                                contentDescription = "${it.current.condition} image",
                                modifier = Modifier
                                    .padding(10.dp)
                                    .size(150.dp)
                            )
                        }
                        Text(text = it.current.condition.text)
                        Text(
                            text = it.location.name,
                            style = MaterialTheme.typography.h3,
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier.padding(16.dp)
                        )

                        Text(text = it.location.localtime)

                        Spacer(modifier = Modifier.height(20.dp))

                        Box(modifier = Modifier
                            .padding(20.dp)
                            .clip(RoundedCornerShape(15.dp))
                            .fillMaxWidth(0.7f)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFF87CEEB),
                                        Color(0xFF1A237E)
                                    ),
                                    startY = 0f,
                                    endY = Float.POSITIVE_INFINITY
                                )
                            ),
                            contentAlignment = Alignment.Center
                        ) {

                            Text(
                                text = "${it.current.temp_c}℃",
                                style = MaterialTheme.typography.h2,
                                modifier = Modifier

                                    .padding(16.dp),
                                textAlign = TextAlign.Center)
                        }
                        Text(text = "Feels like ${it.current.feelslike_c}\u2103")
                        Text(text = "Wind Speed: ${it.current.wind_kph} kph")
                        Text(text = "Humidity: ${it.current.humidity}%")
                    }
                }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyView(){
    //WeatherScreen()
}