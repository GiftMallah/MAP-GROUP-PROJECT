package com.example.namibiahockeysunion.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.namibiahockeysunion.R
import kotlinx.coroutines.delay

@Composable
fun SlidingBackground(
    imageList: List<Int>,
    intervalMillis: Long = 60000L // 1 minute
) {
    var currentImageIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(intervalMillis)
            currentImageIndex = (currentImageIndex + 1) % imageList.size
        }
    }

    Crossfade(targetState = currentImageIndex, label = "BackgroundImage") { index ->
        Image(
            painter = painterResource(id = imageList[index]),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onLoginClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val timesNewRoman = FontFamily.Serif

    var selectedItem by remember { mutableStateOf(0) }

    val items = listOf("Home", "Search", "Menu")
    val icons = listOf(Icons.Default.Home, Icons.Default.Search, Icons.Default.Menu)

    val imageList = listOf(
        R.drawable.image3,
        R.drawable.image2,
        R.drawable.image1,
        R.drawable.image
    )

    Box(modifier = Modifier.fillMaxSize()) {
        SlidingBackground(imageList)

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Namibia Hockey's Union",
                                fontSize = 26.sp,
                                fontFamily = timesNewRoman,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFFD9D9D9),
                        titleContentColor = Color(0xFF254C85)
                    )
                )
            },
            bottomBar = {
                NavigationBar(
                    containerColor = Color(0xFF1A231C),
                    tonalElevation = 8.dp
                ) {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = icons[index],
                                    contentDescription = item,
                                    modifier = Modifier.size(40.dp)
                                )
                            },
                            selected = selectedItem == index,
                            onClick = { selectedItem = index },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFFD9D9D9),
                                unselectedIconColor = Color.White,
                                indicatorColor = Color.Transparent
                            )
                        )
                    }
                }
            },
            containerColor = Color.Transparent
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 220.dp)
                        .background(
                            color = Color(0xDDFFFFFF), // semi-transparent white
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(20.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "2025 Season",
                            fontSize = 18.sp,
                            fontFamily = timesNewRoman,
                            color = Color(0xFF254C85)
                        )
                        Text(
                            text = "Registration Now Open",
                            fontSize = 22.sp,
                            fontFamily = timesNewRoman,
                            color = Color(0xFF254C85)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = onLoginClick,
                                modifier = Modifier
                                    .height(50.dp)
                                    .width(130.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF44825D)),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text("Login", fontFamily = timesNewRoman, fontSize = 18.sp)
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Button(
                                onClick = onRegisterClick,
                                modifier = Modifier
                                    .height(50.dp)
                                    .width(130.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF44825D)),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text("Register", fontFamily = timesNewRoman, fontSize = 18.sp)
                            }
                        }
                    }
                }

                val paragraphStyle = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 20.sp,
                    fontFamily = timesNewRoman,
                    textAlign = TextAlign.Start,
                    lineHeight = 22.sp
                )
                val annotatedLink = buildAnnotatedString {
                    append("Visit NHU Website: ")
                    pushStringAnnotation(tag = "URL", annotation = "http://namibiahockey.org")
                    withStyle(
                        style = SpanStyle(color = Color(0xFF1E88E5), fontFamily = timesNewRoman)
                    ) {
                        append("namibiahockey.org")
                    }
                    pop()
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .background(color = Color(0xDDFFFFFF), shape = RoundedCornerShape(12.dp))
                        .padding(20.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // Fixed Title
                        Text(
                            text = "About Us",
                            fontSize = 22.sp,
                            fontFamily = timesNewRoman,
                            color = Color(0xFF254C85)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Scrollable Content
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "The Namibia Hockey Union (NHU) is the governing body for field and indoor hockey in Namibia. Based in Windhoek, it oversees the sport’s development, administration, and representation at both national and international levels.",
                                style = paragraphStyle
                            )

                            Text(
                                text = "The NHU is responsible for organizing national leagues, including the Bank Windhoek National Indoor and Outdoor Leagues, which feature multiple divisions for both men and women. It also manages junior development programs, such as the Mini Hockey League for children aged 8 to 14, and coordinates national teams for international competitions. The union is affiliated with the International Hockey Federation (FIH) and the African Hockey Federation.",
                                style = paragraphStyle
                            )

                            ClickableText(
                                text = annotatedLink,
                                style = paragraphStyle,
                                onClick = { offset ->
                                    annotatedLink.getStringAnnotations("URL", offset, offset)
                                        .firstOrNull()?.let { url ->
                                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url.item))
                                            context.startActivity(intent)
                                        }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
