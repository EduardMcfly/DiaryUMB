package com.umb.diaryumb

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.umb.diaryumb.diary.Diary
import com.umb.diaryumb.diary.DiaryAdapter
import com.umb.diaryumb.diary.DiaryModel
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.thread


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    var itemlist = ArrayList<Diary>()
    private lateinit var date: EditText
    private var mYear = 0
    private var mMonth: Int = 0
    private var mDay: Int = 0
    var datePickerDialog: DatePickerDialog? = null
    var diaryModel: DiaryModel = DiaryModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val root = inflater.inflate(R.layout.fragment_first, container, false)

        date = root.findViewById(R.id.date)
        val affair = root.findViewById<EditText>(R.id.affair)
        val activity = root.findViewById<EditText>(R.id.activity)

        val listView = root.findViewById<ListView>(R.id.list)
        val add = root.findViewById<Button>(R.id.add)
        val delete = root.findViewById<Button>(R.id.delete)
        val clear = root.findViewById<Button>(R.id.clear)


        var adapter = DiaryAdapter(
            root.context,
            R.layout.diary_list_item,
            itemlist
        )


        // Adding the items to the list when the add button is pressed
        add.setOnClickListener {

            thread {
                try {
                    val diary = diaryModel.add(
                        date.text.toString(),
                        affair.text.toString(),
                        activity.text.toString()
                    )
                    getActivity()?.runOnUiThread {
                        itemlist.add(
                            diary
                        )
                        listView.adapter = adapter
                        adapter.notifyDataSetChanged()
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            // This is because every time when you add the item the input space or the eidt text space will be cleared
            date.text.clear()
            affair.text.clear()
            activity.text.clear()
        }
        // Clearing all the items in the list when the clear button is pressed
        clear.setOnClickListener {

            itemlist.clear()
            adapter.notifyDataSetChanged()
        }
        // Adding the toast message to the list when an item on the list is pressed
        listView.setOnItemClickListener { adapterView, view, i, l ->
            android.widget.Toast.makeText(
                root.context,
                "You Selected the item --> " + itemlist.get(i),
                android.widget.Toast.LENGTH_SHORT
            ).show()
        }
        // Selecting and Deleting the items from the list when the delete button is pressed
        delete.setOnClickListener {
            val count = listView.count
            var item = count - 1
            while (item >= 0) {
                if (itemlist[item].isSelected()) {
                    adapter.remove(itemlist[item])
                }
                item--
            }
            adapter.notifyDataSetChanged()
        }


        val c: Calendar = Calendar.getInstance()
        mYear = c.get(Calendar.YEAR)
        mMonth = c.get(Calendar.MONTH)
        mDay = c.get(Calendar.DAY_OF_MONTH)
        datePickerDialog = this.context?.let {
            DatePickerDialog(
                it,
                OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    mYear = year
                    mMonth = monthOfYear + 1
                    mDay = dayOfMonth
                    date.setText(
                        "$mDay-$mMonth-$mYear"
                    )
                }, mYear, mMonth, mDay
            )
        }

        date.setOnClickListener {
            onClick(it)
        }
        thread {
            var data = diaryModel.getAll()
            try {
                getActivity()?.runOnUiThread {
                    if (data.size > 0) {
                        itemlist.addAll(data)
                        listView.adapter = adapter
                        adapter.notifyDataSetChanged()
                    }
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        return root

    }

    fun onClick(v: View) {
        datePickerDialog?.show()
    }
}