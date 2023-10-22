package com.kosti.sqlitetarea

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class CustomAdapter(private val dataSet: MutableList<Producto>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTV: TextView
        val descTV: TextView
        val editBtn: Button
        val deleteBtn: Button

        init {
            nameTV = view.findViewById(R.id.nameTV)
            descTV = view.findViewById(R.id.descTV)
            editBtn = view.findViewById(R.id.editBtn)
            deleteBtn = view.findViewById(R.id.deleteBtn)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.row_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.nameTV.text = dataSet[position].name
        viewHolder.descTV.text = dataSet[position].desc

        viewHolder.deleteBtn.setOnClickListener {

            dataSet[position].id?.let { it1 -> SQLiteHelper(viewHolder.itemView.context).deleteProducto(it1) }
            dataSet.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, dataSet.size)
        }

        viewHolder.editBtn.setOnClickListener {
            val bottomSheetFragment = ItemListDialogFragment()
            val bundle = Bundle()

            bundle.putString("name", dataSet[position].name)
            bundle.putString("description", dataSet[position].desc)
            dataSet[position].id?.let { it1 -> bundle.putInt("id", it1) }
            bottomSheetFragment.arguments = bundle
            bottomSheetFragment.setOnDismissListener((viewHolder.itemView.context as MainActivity))
            bottomSheetFragment.show((viewHolder.itemView.context as MainActivity).supportFragmentManager, "BSDialogFragment")

        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size



}

