package com.leelaland.pawansir

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.Gson
import java.io.InputStreamReader
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import android.content.Context
import android.util.Log

data class Student(
    val id: String,
    val name: String,
    val roll_number: String,
    val Class: String,
    val password: String
)

data class StudentList(
    val students: List<Student>
)


fun loadStudents(context: Context): StudentList? {
    return try {
        val inputStream = context.assets.open("student.json")
        val reader = InputStreamReader(inputStream)
        val studentList = Gson().fromJson(reader, StudentList::class.java)
        reader.close()
        studentList
    } catch (e: Exception) {
        Log.e("ContentView", "Error loading students: ${e.message}")
        e.printStackTrace()
        null
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ContentView() {
    val context = LocalContext.current
    val studentList = loadStudents(context) // Load students from JSON

    Log.d("ContentView", "Student List: $studentList")

    Scaffold(topBar = {
        TopAppBar(title = { Text("User Information") })
    }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (studentList != null && studentList.students.isNotEmpty()) {
                items(studentList.students) { student ->
                    StudentView(student = student)
                }
            } else {
                item {
                    Text(
                        text = "No User Data Found",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun StudentView(student: Student) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Student Information", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("ID: ${student.id}", fontSize = 16.sp)
            Text("Name: ${student.name}", fontSize = 16.sp)
            Text("Roll Number: ${student.roll_number}", fontSize = 16.sp)
            Text("Class: ${student.Class}", fontSize = 16.sp)
            Text("Password: ${student.password}", fontSize = 16.sp)
        }
    }
}
