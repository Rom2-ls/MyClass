package com.example.myclass

//ref CreateClassActivity.kt et ListClassActivity.kt

//méthode 1
data class Course(val id: String = "", val name: String = "", val nb_students: Int = 1)

//méthode 2 plus clean
//data class Course(val name: String = "", val nb_students: Int = 1, val students: Student)

data class Student(val id: String = "", val role: String = "")