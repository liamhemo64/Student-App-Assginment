package com.idz.studentapp.models

class Model private constructor() {
    val students: MutableList<Student> = ArrayList()

    companion object {
        val shared = Model()
    }

    init {
        for (i in 0..4) {
            val student = Student(
                name = "Student $i",
                id = i.toString(),
                phone = "0541234567",
                address = "Tel Aviv",
                isChecked = false
            )
            students.add(student)
        }
    }
}