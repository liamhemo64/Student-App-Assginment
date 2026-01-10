package com.idz.studentapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.idz.studentapp.R
import com.idz.studentapp.models.Student

interface OnItemClickListener {
    fun onItemClick(position: Int, student: Student?)
}

class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imageViewAvatar: ImageView = itemView.findViewById(R.id.imageViewAvatar)
    val textViewStudentName: TextView = itemView.findViewById(R.id.textViewStudentName)
    val textViewStudentID: TextView = itemView.findViewById(R.id.textViewStudentID)
    val checkBoxStudent: CheckBox = itemView.findViewById(R.id.checkBoxStudent)
}

class StudentListAdapter(
    private val students: List<Student>
) : RecyclerView.Adapter<StudentViewHolder>() {

    var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_list_row, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]

        holder.textViewStudentName.text = student.name
        holder.textViewStudentID.text = student.id
        holder.checkBoxStudent.isChecked = student.isChecked

        holder.checkBoxStudent.setOnCheckedChangeListener { _, isChecked ->
            student.isChecked = isChecked
        }

        holder.itemView.setOnClickListener {
            listener?.onItemClick(position, student)
        }
    }

    override fun getItemCount(): Int = students.size
}