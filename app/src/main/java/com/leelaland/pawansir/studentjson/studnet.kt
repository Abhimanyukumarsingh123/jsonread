package com.example.studentlist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Address Data Class
data class Address(
    val line1: String,
    val line2: String,
    val city: String,
    val state: String,
    val country: String,
    val pin: String
)

// Student Data Class
data class Student(
    val name: String,
    val age: Int,
    val address: Address
)

// Student List Data Class
data class StudentList(
    val students: List<Student>
)

@Composable
fun DisplayStudents(students: List<Student>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        for (student in students) {
            StudentCard(student)
        }
    }
}

@Composable
fun StudentCard(student: Student) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            BasicText("Name: ${student.name}")
            BasicText("Age: ${student.age}")
            Spacer(modifier = Modifier.height(8.dp))
            AddressDetails(student.address)
        }
    }
}

@Composable
fun AddressDetails(address: Address) {
    Column {
        BasicText("Address:")
        BasicText("  ${address.line1}")
        BasicText("  ${address.line2}")
        BasicText("  ${address.city}, ${address.state} - ${address.pin}")
        BasicText("  ${address.country}")
    }
}