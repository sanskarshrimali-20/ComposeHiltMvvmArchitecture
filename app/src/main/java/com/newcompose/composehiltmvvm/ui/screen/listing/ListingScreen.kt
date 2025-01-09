package com.newcompose.composehiltmvvm.ui.screen.listing

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.newcompose.composehiltmvvm.BuildConfig
import com.newcompose.composehiltmvvm.data.model.MovieListResponse
import com.newcompose.composehiltmvvm.navigation.ComposeSampleScreens
import com.newcompose.composehiltmvvm.ui.screen.detail.DetailScreenArgs
import com.newcompose.composehiltmvvm.util.ExtensionFunctions.isLastItemVisible
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListingScreen(
    navController: NavHostController,
    viewModel: ListingScreenViewModel
) {

    val uiState = viewModel.uiState
    val lazyState = rememberLazyListState()

    LaunchedEffect(key1 = lazyState.isLastItemVisible, block = {
        if (lazyState.isLastItemVisible && !uiState.movieListResponseLoading && viewModel.hasNextPage()) {
            viewModel.fetchMoviesList()
        }
    })

    LaunchedEffect(key1 = true, block = {
        if (uiState.movieListResponseData == null) {
            viewModel.fetchMoviesList()
        }
    })

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Browse Movies", style = MaterialTheme.typography.titleSmall)}
            )
        }, bottomBar = {
            Spacer(modifier = Modifier.windowInsetsBottomHeight(insets = WindowInsets.navigationBars))
        },
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        )
        {
            LazyColumn(
                state = lazyState,
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp, top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                uiState.movieListResponseData?.results?.forEach {
                    item {
                        MoviesListingItem(
                            modifier = Modifier.padding(horizontal = 14.dp),
                            data = it,
                            onClick = {
                                navController.navigate(
                                    ComposeSampleScreens.DetailScreen.route + "/"
                                            + Json.encodeToString(
                                        DetailScreenArgs(
                                            movieId = it?.id?.toString() ?: "",
                                            name = it?.title ?: ""
                                        )
                                    )
                                )
                            }
                        )
                    }
                }

                if (viewModel.uiState.movieListResponseLoading
                    && (viewModel.uiState.movieListResponseData?.results?.isNotEmpty() == true)
                ) {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .fillMaxWidth()
                        )
                    }
                }

                if (viewModel.uiState.movieListResponseData?.page != null && viewModel.uiState.movieListResponseData?.page == viewModel.uiState.movieListResponseData?.totalPages) {
                    item { Text(text = "List end reached", Modifier.fillMaxWidth()) }
                }

                if (viewModel.uiState.movieListResponseError.isNotEmpty()) {
                    item { Text(text = viewModel.uiState.movieListResponseError, modifier = Modifier.align(
                        Alignment.Center)) }
                }
            }

            if (viewModel.uiState.movieListResponseLoading
                && (viewModel.uiState.movieListResponseData == null)
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun MoviesListingItem(modifier: Modifier, data: MovieListResponse.Result?, onClick: () -> Unit) {
    Surface( modifier = modifier
        .height(126.dp)
        .fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        onClick = onClick,
        contentColor = MaterialTheme.colorScheme.primary,
        tonalElevation = 10.dp) {

        Row(
            modifier = Modifier
                .fillMaxHeight()
        ) {
            AsyncImage(
                model = BuildConfig.BASE_IMG_URL + data?.posterPath,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight(),
                contentScale = ContentScale.FillHeight
            )


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = 14.dp, vertical = 14.dp),
                verticalArrangement = Arrangement.SpaceBetween

            ) {
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = data?.title ?: "",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(text = data?.voteAverage?.let { "Rating: $it" } ?: "", style = MaterialTheme.typography.bodyMedium)
                }
                Text(text = data?.releaseDate?.let { "Release date: $it" } ?: "", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }


}