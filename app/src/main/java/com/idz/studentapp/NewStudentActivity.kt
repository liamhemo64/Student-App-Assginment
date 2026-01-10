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

class NewStudentActivity : AppCompatActivity() {
    private var studentList = Model.shared.students
    private var nameEditText: EditText? = null
    private var idEditText: EditText? = null
    private var phoneEditText: EditText? = null
    private var addressEditText: EditText? = null
    private var checkedCheckBox: CheckBox? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_new_student)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        nameEditText = findViewById(R.id.editTextName)
        idEditText = findViewById(R.id.editTextID)
        phoneEditText = findViewById(R.id.editTextPhone)
        addressEditText = findViewById(R.id.editTextAddress)
        checkedCheckBox = findViewById(R.id.checkBoxNewStudentChecked)

        findViewById<Toolbar>(R.id.toolbar).setNavigationOnClickListener { finish() }
        findViewById<Button>(R.id.buttonSave).setOnClickListener { saveNewStudent() }
        findViewById<Button>(R.id.buttonCancel).setOnClickListener { finish() }
    }

    private fun saveNewStudent() {
        val name = nameEditText?.text.toString().trim()
        val id = idEditText?.text.toString().trim()
        val phone = phoneEditText?.text.toString().trim()
        val address = addressEditText?.text.toString().trim()
        val isChecked = checkedCheckBox?.isChecked ?: false

        if (name.isEmpty() || id.isEmpty()) {
            Toast.makeText(this, "Name and ID are required", Toast.LENGTH_SHORT).show()
            return
        }

        val newStudent = Student(name, id, phone, address, isChecked)
        studentList.add(newStudent)

        val resultIntent = Intent()
        resultIntent.putExtra("newStudent", newStudent)
        setResult(RESULT_OK, resultIntent)

        Toast.makeText(this, "Student added", Toast.LENGTH_SHORT).show()
        finish()
    }
}
