package com.idz.studentapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.idz.studentapp.models.Student

class StudentDetailsActivity : AppCompatActivity() {

    private val REQUEST_EDIT_STUDENT = 1  // Unique request code for EditStudentActivity
    private var student: Student? = null
    private var studentPos: Int? = null

    private var studentName: TextView? = null
    private var studentId: TextView? = null
    private var studentPhoneNumber: TextView? = null
    private var studentAddress: TextView? = null
    private var studentChecked: CheckBox? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_student_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        studentName = findViewById(R.id.textViewName)
        studentId = findViewById(R.id.textViewID)
        studentPhoneNumber = findViewById(R.id.textViewPhone)
        studentAddress = findViewById(R.id.textViewAddress)
        studentChecked = findViewById(R.id.checkBoxDetailsChecked)

        // Get the student object from studentsList activity
        student = intent.getParcelableExtra<Student>("student")
        studentPos = intent.getIntExtra("position",-1)

        // Check if student data is passed
        student?.let{displayStudentDetails(it)}

        findViewById<Button>(R.id.buttonEdit).setOnClickListener{
            val intent = Intent(this, EditStudentActivity::class.java)
            intent.putExtra("student", student)
            intent.putExtra("position", studentPos)
            startActivityForResult(intent, REQUEST_EDIT_STUDENT)
        }
    }

    private fun displayStudentDetails(student: Student) {
        studentName?.text = student.name
        studentId?.text = student.id
        studentPhoneNumber?.text = student.phone
        studentAddress?.text = student.address
        studentChecked?.isChecked = student.isChecked
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_EDIT_STUDENT && resultCode == RESULT_OK) {
            if (data != null){
                val updatedStudent = data.getSerializableExtra("updatedStudent") as? Student
                updatedStudent?.let {
                    // Update the student object
                    student = it
                    // Update the TextViews to display the new data
                    displayStudentDetails(it)
                }
            }
            finish()
        }
    }
}