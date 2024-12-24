package dev.vijayakumar.adminapp.view.ui.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import dev.vijayakumar.adminapp.network.model.PostResponseItem
import dev.vijayakumar.adminapp.state.SearchUIState
import dev.vijayakumar.adminapp.viewmodel.PostViewModel
import kotlinx.coroutines.launch


@Composable
fun PostSearch(viewModel: PostViewModel = hiltViewModel()) {
    var searchQuery by remember { mutableStateOf("") }
    val searchUiState by viewModel.postList.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Search Text Field
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { query ->
                searchQuery = query

                if (query.isNotEmpty()) {

                    viewModel.viewModelScope.launch {
                        viewModel.getPostList(query.toInt())
                    }

                }
            },
            label = { Text("Search Posts") },
            placeholder = { Text("Enter search query") },
            modifier = Modifier.fillMaxWidth()
        )




        Spacer(modifier = Modifier.height(16.dp))

        // Display UI state
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (val state = searchUiState) {
                is SearchUIState.Loading -> {
                    CircularProgressIndicator()
                }

                is SearchUIState.Success -> {
                   // val posts = (searchUiState as SearchUIState.Success).data.post
                    if (state.data.post.isEmpty()) {
                        Text(text = "No Data Found", style = MaterialTheme.typography.bodyLarge)
                    } else {
                        PostSearchContent(state.data.post ,onSaveClick = {post ->
                            viewModel.savePostToDatabase(post)

                        })
                    }
                }

                is SearchUIState.Failure -> {
                    val errorMessage = state.message
                    Text(
                        text = errorMessage ?: "An error occurred",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                SearchUIState.Empty -> {
                    Text(text = "Start searching for posts!", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@Composable
fun PostSearchContent(postLists: List<PostResponseItem> ,  onSaveClick: (PostResponseItem) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(postLists) { post ->
            PostCard(postResponseItem = post,onSaveClick)
        }
    }
}

@Composable
fun PostCard(postResponseItem: PostResponseItem, onSaveClick: (PostResponseItem) -> Unit) {
    val ctx =LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = postResponseItem.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = postResponseItem.body,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )

                Image(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Person Icon",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            onSaveClick(postResponseItem)
                            Toast.makeText(ctx,"Clicked",Toast.LENGTH_SHORT).show()

                        }
                )
            }


        }

    }
}
