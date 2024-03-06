package com.example.pocketdiary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.pocketdiary.Presentation.AddNoteScreen
import com.example.pocketdiary.Presentation.NotesScreen
import com.example.pocketdiary.Presentation.NotesViewModel
import com.example.pocketdiary.data.NotesDatabase
import com.example.pocketdiary.ui.theme.PocketDiaryTheme

class MainActivity : ComponentActivity() {

    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            NotesDatabase::class.java,
            "notes.db"
        ).build()
    }

    private val viewModel by viewModels<NotesViewModel> (
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun<T: ViewModel> create(modelClass: Class<T>): T {
                    return NotesViewModel(database.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PocketDiaryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val state by viewModel.state.collectAsState()
                   val navControler = rememberNavController()

                    NavHost(navController = navControler, startDestination = "NotesScreen" ){
                        composable("NotesScreen"){
                            NotesScreen(
                                state = state,
                                navControler = navControler,
                                onEvent = viewModel::onEvent
                            )
                        }
                        composable("AddNoteScreen"){
                            AddNoteScreen(
                                state = state,
                                navControler = navControler,
                                onEvent = viewModel::onEvent
                            )
                        }
                    }
                }
            }
        }
    }
}

