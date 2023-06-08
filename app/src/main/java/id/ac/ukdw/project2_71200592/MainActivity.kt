package id.ac.ukdw.project2_71200592

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

data class Expense(val id: Int, val keterangan: String, val nominal: Double)

class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var expenseList: MutableList<Expense>
    private lateinit var adapter: ArrayAdapter<Expense>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        dbHelper = DatabaseHelper(this)
        expenseList = dbHelper.getAllExpenses().toMutableList()
        adapter = ArrayAdapter(this, R.layout.biayabarang, expenseList)

        listView.adapter = adapter

        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
            val expense = parent.getItemAtPosition(position) as Expense
            intent.putExtra("expense", expense)
            startActivity(intent)
        }

        val btnTambah = findViewById<Button>(R.id.btnTambah)
        btnTambah.setOnClickListener {
            val keterangan = findViewById<EditText>(R.id.edtKeterangan).text.toString()
            val nominal = findViewById<EditText>(R.id.edtNominal).text.toString().toDouble()

            val expense = Expense(0, keterangan, nominal)
            val id = dbHelper.addExpense(expense)
            expense.id = id.toInt()

            expenseList.add(expense)
            adapter.notifyDataSetChanged()

            clearInputFields()
        }
    }

    private fun clearInputFields() {
        findViewById<EditText>(R.id.edtKeterangan).text.clear()
        findViewById<EditText>(R.id.edtNominal).text.clear()
    }
}
