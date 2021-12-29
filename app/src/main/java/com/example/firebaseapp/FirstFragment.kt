package com.example.firebaseapp

import android.R.attr
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.firebaseapp.models.Post
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    val mAuth = FirebaseAuth.getInstance()

    var database = FirebaseDatabase.getInstance()
    var postsReference = database.getReference("posts")

    private lateinit var etTitle: EditText
    private lateinit var etDescription: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etTitle = view.findViewById(R.id.etTitle)
        etDescription = view.findViewById(R.id.etDescription)

        val submitButton: Button = view.findViewById(R.id.btnUpload)

        submitButton.setOnClickListener {
            val title: String = etTitle.text.toString()
            val description: String = etDescription.text.toString()

            if(title.isNullOrEmpty() || description.isNullOrEmpty()) {
                return@setOnClickListener
            }
            uploadData(title, description)
        }

    }

    private fun uploadData(title: String, description: String) {
        val currentPost = Post(mAuth.uid!!, title, description)


        postsReference.push().setValue(currentPost)
            .addOnCompleteListener(OnCompleteListener<Void?> { task ->
                if (task.isSuccessful) {
                    Toast.makeText(activity, "Success", Toast.LENGTH_LONG).show()
                    etTitle.setText("")
                    etDescription.setText("")
                } else {
                    Toast.makeText(
                        activity,
                        "Error: " + task.exception!!.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }
}