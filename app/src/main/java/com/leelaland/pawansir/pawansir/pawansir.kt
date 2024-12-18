package com.leelaland.pawansir.pawansir

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader


// Topic data class
data class Topic(
    val id: String = java.util.UUID.randomUUID().toString(),
    val Class: String,
    val topic_name: String,
    val description: String,
    val ai_prompt: String
)



fun loadTopicsFromAssets(context: Context): List<Topic> {
    return try {
        val inputStream = context.assets.open("data.json") // JSON file ka naam yahan mention karein
        val reader = InputStreamReader(inputStream)
        val json = reader.readText()
        reader.close()
        Gson().fromJson(json, object : TypeToken<List<Topic>>() {}.type)
    } catch (e: Exception) {
        e.printStackTrace()
        emptyList() // Agar koi error aaye, to empty list return karein
    }
}

@Composable
fun TopicsScreen(navController: NavController) {
    val context = LocalContext.current
    val topics = remember { loadTopicsFromAssets(context) } // JSON file se topics ko load karna

    if (topics.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Loading topics...", color = Color.Gray)
        }
    } else {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(topics.size) { index ->
                val topic = topics[index]
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    Text(topic.topic_name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text("Class: ${topic.Class}", color = Color.Gray)
                    Text(topic.description, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    ClickableText(
                        text = AnnotatedString("Learn More"),
                        onClick = {
                            navController.navigate("details/${topic.id}")
                        },
                        style = LocalTextStyle.current.copy(color = Color.Blue, fontSize = 14.sp)
                    )
                }
            }
        }
    }
}

@Composable
fun DetailScreen(topicId: String, navController: NavController) {
    val context = LocalContext.current
    val topics = remember { loadTopicsFromAssets(context) }
    val topic = topics.find { it.id == topicId }

    topic?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(it.topic_name, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Text("Class: ${it.Class}", fontSize = 20.sp, color = Color.Gray)
            Text(it.description, fontSize = 18.sp)
            Text("AI Prompt:", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(it.ai_prompt, fontSize = 16.sp, color = Color.DarkGray)
            Button(
                onClick = { startChat(it.ai_prompt) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Start Chat")
            }
        }
    } ?: run {
        Text("Topic not found", color = Color.Red)
    }
}


@Composable
fun MainApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "topics") {
        composable("topics") {
            TopicsScreen(navController)
        }
        composable("details/{topicId}") { backStackEntry ->
            val topicId = backStackEntry.arguments?.getString("topicId")
            topicId?.let {
                DetailScreen(topicId = it, navController = navController)
            }
        }
    }
}

fun startChat(prompt: String) {

    val modifiedPrompt = "$prompt Make the conversation interactive and engaging."

    println("Starting chat with prompt: $modifiedPrompt")

}