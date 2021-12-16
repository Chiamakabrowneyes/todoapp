package com.example.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.FileUtils
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils.readLines
import org.apache.commons.io.FileUtils.writeLines
import org.apache.commons.io.IOUtils.writeLines
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOftasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnlongClickLister {
            override fun onItemLongClicked(position: Int) {
                //1. Remove item from list
                listOftasks.removeAt(position)
                //2. Notify adapter of change
                adapter.notifyDataSetChanged()

                saveItems()

            }

        }

        loadItems()

        //Look up recycler view in layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        //create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOftasks, onLongClickListener)
        //Attach the adapter to the recycler view
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val inputTextField = findViewById<EditText>(R.id.addtaskfield)
        //Set up button and input field so that the user can input a task
        //Geta reference to the button
        //Set an onclick listener
        findViewById<Button>(R.id.button).setOnClickListener {
            // Grab the text that the user has entered in the edit tex field
            val userInputtedTask = inputTextField.text.toString()

            // Add that text string to our list of tasks
            listOftasks.add(userInputtedTask)

            //Notify adapter that data has be updated
            adapter.notifyItemInserted(listOftasks.size - 1)

            //Reset the text field
            inputTextField.setText("")

            saveItems()

        }

    }
    // Save the data that the user has inputted
    //Save data by writing and reading from a file 

    //Get the file we need
    fun getDataFile(): File {
        //Every line will represent aa specific task in a list of tasks
        return File(filesDir, "data.txt")
    }

    //Create a method to get the file we need
    // Load the items by reading every line in the date file
    fun loadItems() {
        try {
            listOftasks = org.apache.commons.io.FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }
                           
    //Save items by writing them into our data file
    fun saveItems() {
        try {
            org.apache.commons.io.FileUtils.writeLines(getDataFile(), listOftasks)
        } catch (ioException: IOException) {
            ioException.printStackTrace()

        }


    }

}