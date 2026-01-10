package com.idz.studentapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.idz.studentapp.models.Model
import com.idz.studentapp.models.Student

class EditStudentActivity : AppCompatActivity() {
    private var studentList = Model.shared.students
    private var studentPos: Int? = null
    private var student: Student? = null
    private var studentNameEdit: EditText? = null
    private var studentIdEdit: EditText? = null
    private var studentPhoneNumberEdit: EditText? = null
    private var studentAddressEdit: EditText? = null
    private var studentChecked: CheckBox? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_student)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        studentNameEdit = findViewById(R.id.editTextEditName)
        studentIdEdit = findViewById(R.id.editTextEditID)
        studentPhoneNumberEdit = findViewById(R.id.editTextEditPhone)
        studentAddressEdit = findViewById(R.id.editTextEditAddress)
        studentChecked = findViewById(R.id.checkBoxEditChecked)

        student = intent.getParcelableExtra<Student>("student")
        studentPos = intent.getIntExtra("position", -1)

        student?.let{displayStudentDetails(it)}

        findViewById<Toolbar>(R.id.toolbar).setNavigationOnClickListener { finish() }
        findViewById<Button>(R.id.buttonEditSave).setOnClickListener{saveEditedStudent()}
        findViewById<Button>(R.id.buttonDelete).setOnClickListener{deleteEditedStudent()}
        findViewById<Button>(R.id.buttonEditCancel).setOnClickListener{finish()}
    }

    private fun displayStudentDetails(student: Student) {
        studentNameEdit?.setText(student.name)
        studentIdEdit?.setText(student.id)
        studentPhoneNumberEdit?.setText(student.phone)
        studentAddressEdit?.setText(student.address)
        studentChecked?.isChecked = student.isChecked
    }

    private fun saveEditedStudent() {
        // Get the updated data from the EditTexts
        val updatedName = studentNameEdit?.text.toString()
        val updatedId = studentIdEdit?.text.toString()
        val updatedPhone = studentPhoneNumberEdit?.text.toString()
        val updatedAddress = studentAddressEdit?.text.toString()
        val updatedCheck = studentChecked?.isChecked

        // Update the student object with new data
        student?.name = updatedName
        student?.id = updatedId
        student?.phone = updatedPhone
        student?.address = updatedAddress
        student?.isChecked = updatedCheck ?: false
        studentList[studentPos!!] = student!!

        val resultIntent = Intent()
        resultIntent.putExtra("updatedStudent", student)  // Pass the updated student object
        setResult(RESULT_OK, resultIntent)

        Toast.makeText(this, "Student updated", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun deleteEditedStudent() {
        studentPos?.let {studentList.removeAt(it)}
        setResult(RESULT_OK, Intent())
        finish()
    }
}