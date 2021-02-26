package com.umb.diaryumb.diary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import com.google.android.material.textview.MaterialTextView
import com.umb.diaryumb.R


interface SelectedIndex {
    fun setSelectedIndex(position: Int)
}

class DiaryAdapter(
    context: Context,
    resource: Int,
    private val items: ArrayList<Diary>
) : ArrayAdapter<Diary>(context, resource, items), SelectedIndex {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun setSelectedIndex(position: Int) {
        val item = items[position]
        item.selected = !item.isSelected()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = inflater.inflate(R.layout.diary_list_item, parent, false)

        if (rowView != null) {
            val id = rowView.findViewById<MaterialTextView>(R.id.id)
            val date = rowView.findViewById<MaterialTextView>(R.id.date)
            val affair = rowView.findViewById<MaterialTextView>(R.id.affair)
            val activity = rowView.findViewById<MaterialTextView>(R.id.activity)
            val cb = rowView.findViewById<CheckBox>(R.id.list_item_check_button)
            cb.setOnClickListener(View.OnClickListener { view ->
                setSelectedIndex(position)
            })
            val item = items[position]
            cb.isChecked = item.selected
            id.text = item.id.toString()
            date.text = item.date
            affair.text = item.affair
            activity.text = item.activity

        }
        return rowView
    }

}
