package dev.vijayakumar.adminapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.vijayakumar.adminapp.network.model.PostResponseItem
import dev.vijayakumar.adminapp.repository.PostRepository
import dev.vijayakumar.adminapp.state.SearchUIState
import dev.vijayakumar.adminapp.viewmodel.PostViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
@OptIn(ExperimentalCoroutinesApi::class)
class PostViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()  // Ensures LiveData or StateFlow updates are handled synchronously
    private lateinit var viewModel: PostViewModel

    @Mock
    private lateinit var repository: PostRepository

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher) // Set main dispatcher to our test dispatcher
       repository = mock(PostRepository::class.java)
        viewModel = PostViewModel(repository)  // Create the view model with the mock repository
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()  // Reset the dispatcher after each test
    }


    @Test
    fun `getPostList should update postList with success state`() = runTest {
        val postId = 1
        val mockPostResponse = listOf(
            PostResponseItem("Body 1", "Email 1", 1, "Name 1", 1),
            PostResponseItem("Body 2", "Email 2", 2, "Name 2", 2)
        )
        `when`(repository.getPostList(postId)).thenReturn(flow { emit(mockPostResponse) })
        viewModel.getPostList(postId)
        advanceUntilIdle()
        assertTrue(viewModel.postList.value is SearchUIState.Success)
        val successState = viewModel.postList.value as SearchUIState.Success
        assertEquals(mockPostResponse, successState.data.post)
    }


    @Test
    fun `getPostList emits Failure state on error`() = runTest {
        // Given
        val postId = 1
        val errorMessage = "Network error"
        `when`(repository.getPostList(postId)).thenReturn(flow { throw RuntimeException(errorMessage) })

        // When
        viewModel.getPostList(postId)

        // Then
        advanceUntilIdle() // Wait for coroutine to finish
        assertTrue(viewModel.postList.value is SearchUIState.Failure)
        val failureState = viewModel.postList.value as SearchUIState.Failure
        assertEquals(errorMessage, failureState.message)
    }

}