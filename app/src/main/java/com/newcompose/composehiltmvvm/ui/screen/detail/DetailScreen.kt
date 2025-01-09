package com.newcompose.composehiltmvvm.ui.screen.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.newcompose.composehiltmvvm.BuildConfig
import com.newcompose.composehiltmvvm.R


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavHostController,
    args: String,
    viewModel: DetailScreenViewModel,
) {

    val uiState = viewModel.uiState
    val lazyState = rememberLazyListState()

    LaunchedEffect(key1 = true, block = {
        if (uiState.movieDetailResponseData == null) {
            viewModel.deserializeArgs(args)
        }
    })

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = uiState.movieName, style = MaterialTheme.typography.titleSmall) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            painter = painterResource(id =R.drawable.ic_arrow_back), contentDescription = "navigate back"
                        )
                    }
                }
            )
        }, bottomBar = {
            Spacer(modifier = Modifier.windowInsetsBottomHeight(insets = WindowInsets.navigationBars))
        },
        contentColor = Color.White
    ) {


        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                state = lazyState,
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp, top = 0.dp),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                item {
                    AsyncImage(
                        model = BuildConfig.BASE_IMG_URL + uiState.movieDetailResponseData?.backdropPath,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.FillWidth
                    )
                }

                item { Spacer(modifier = Modifier.height(8.dp)) }

                item {
                    Row(horizontalArrangement = Arrangement.spacedBy(14.dp), modifier = Modifier.padding(horizontal = 14.dp)) {
                        uiState.movieDetailResponseData?.genres?.take(3)?.forEach {
                            Surface(border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary), shape = RoundedCornerShape(4.dp)) {
                                Text(text = it?.name ?: "", Modifier.padding(4.dp))
                            }
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(4.dp)) }

                item {
                    Text(
                        text = uiState.movieDetailResponseData?.title ?: "",
                        style = MaterialTheme.typography.displayLarge,
                        modifier = Modifier.padding(horizontal = 14.dp)
                    )
                }

                item {
                    Text(
                        text = uiState.movieDetailResponseData?.tagline ?: "",
                        style = MaterialTheme.typography.displayMedium,
                        modifier = Modifier.padding(horizontal = 14.dp)
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(28.dp))
                    Text(text = uiState.movieDetailResponseData?.popularity?.let { "Popularity: $it" } ?: "",
                        style = MaterialTheme.typography.displayMedium, modifier = Modifier.padding(horizontal = 14.dp))
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = uiState.movieDetailResponseData?.releaseDate?.let { "Release Date: $it" } ?: "",
                        style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(horizontal = 14.dp))
                }


                item { Spacer(modifier = Modifier.height(8.dp)) }

                item {
                    Text(text = uiState.movieDetailResponseData?.revenue?.let { "Revenue Generated: USD $it" } ?: "",
                        style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(horizontal = 14.dp))
                }


                if (!uiState.movieDetailResponseData?.overview.isNullOrBlank()) {
                    item {
                        Spacer(modifier = Modifier.height(28.dp))
                        Text(
                            text = "Overview",
                            style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(horizontal = 14.dp)
                        )
                    }

                    item { Spacer(modifier = Modifier.height(4.dp)) }

                    item {
                        Text(
                            text = uiState.movieDetailResponseData?.overview ?: "",
                            style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(horizontal = 14.dp)
                        )
                    }
                }


            }

            if (viewModel.uiState.movieDetailResponseLoading
                && (viewModel.uiState.movieDetailResponseData == null)
            ) {
                CircularProgressIndicator()
            }

            if (viewModel.uiState.movieDetailResponseError.isNotEmpty()) {
                Box(modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                    content = {
                        Text(text = viewModel.uiState.movieDetailResponseError)
                    })
            }
        }
    }
}