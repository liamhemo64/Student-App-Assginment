package com.idz.studentapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.idz.studentapp.adapters.OnItemClickListener
import com.idz.studentapp.adapters.StudentListAdapter
import com.idz.studentapp.adapters.StudentViewHolder
import com.idz.studentapp.models.Model
import com.idz.studentapp.models.Student

class StudentListActivity : AppCompatActivity() {

    private var studentsList = Model.shared.students
    private var adapter: RecyclerView.Adapter<StudentViewHolder> = StudentListAdapter(studentsList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_students_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        (adapter as StudentListAdapter).listener = object : OnItemClickListener {
            override fun onItemClick(position: Int, student: Student?) {
                val intent = Intent(this@StudentListActivity, StudentDetailsActivity::class.java)
                intent.putExtra("student", student)
                intent.putExtra("position", position)
                startActivity(intent)
            }
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewStudents)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        findViewById<FloatingActionButton>(R.id.fabAddStudent).setOnClickListener {
            val intent = Intent(this, NewStudentActivity::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }
}