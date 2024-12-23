package dev.vijayakumar.adminapp.view.ui.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.vijayakumar.adminapp.local.PostEntity
import dev.vijayakumar.adminapp.state.PostUIState
import dev.vijayakumar.adminapp.viewmodel.PostViewModel


@Composable
fun FavoriteScreen(favoriteviewModel: PostViewModel = hiltViewModel()) {

    val ctx = LocalContext.current
     val stateUI = favoriteviewModel.localPostList.collectAsState()


    LaunchedEffect(Unit) {
        favoriteviewModel.postListFromDatabase()

    }


    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 100.dp, start = 16.dp, end = 16.dp)){


        when(stateUI.value){

            PostUIState.Loading -> {
                CircularProgressIndicator()
            }
            is PostUIState.Success -> {
                val posts = (stateUI.value as PostUIState.Success).data
                if (posts.isEmpty()) {
                    Toast.makeText(ctx, "No Data Found", Toast.LENGTH_SHORT).show()
                }else{

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        items(posts) { post ->
                            PostItem(post, onDeleteClick = {favoriteviewModel.deletePostFromDatabase(post)})
                        }
                    }
                }
            }
            is PostUIState.Failure -> {}
            else -> {}
        }

    }

}


@Composable
fun PostItem(post: PostEntity ,  onDeleteClick: (PostEntity) -> Unit) {
    val ctx = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = post.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = post.body,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )

                Image(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "delete Icon",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            onDeleteClick(post)

                            Toast.makeText(ctx,"Deleted",Toast.LENGTH_SHORT).show()

                        }
                )
            }


        }

    }
}