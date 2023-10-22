package com.kosti.sqlitetarea

import android.content.DialogInterface
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
private const val ARG_PARAM1 = "name"
private const val ARG_PARAM2 = "description"
private const val ARG_PARAM3 = "id"
class ItemListDialogFragment : BottomSheetDialogFragment() {

    private var param1: String? = null
    private var param2: String? = null
    private var param3: Int? = null
    private lateinit var addBtn: Button
    private lateinit var deleteBtn: Button
    private lateinit var etName: EditText
    private lateinit var etDesc: EditText
    private lateinit var helper: SQLiteHelper
    private var onDismissListener: OnDismissListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            param3 = it.getInt(ARG_PARAM3)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        helper = SQLiteHelper(requireContext())


        return inflater.inflate(R.layout.fragment_bottom_sheet,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addBtn = view.findViewById(R.id.btnConfirm)

        etName = view.findViewById(R.id.etName)
        etDesc = view.findViewById(R.id.etDescription)

        if(param1 != null && param2 != null && param3 != null){
            etName.setText(param1)
            etDesc.setText(param2)
            addBtn.text = "Editar"
            addBtn.setOnClickListener {
                Toast.makeText(requireContext(), "Editado correctamente", Toast.LENGTH_SHORT).show()
                helper.editProducto(Producto(id=param3, name = etName.text.toString(), desc = etDesc.text.toString()))
                dismiss()
            }
        } else {
            addBtn.text = "Agregar"
            addBtn.setOnClickListener {
                if(etName.text.isEmpty() || etDesc.text.isEmpty()){
                    Toast.makeText(requireContext(), "Llena todos los campos", Toast.LENGTH_SHORT).show()
                    dismiss()
                }else {
                    Toast.makeText(requireContext(), "Agregado", Toast.LENGTH_SHORT).show()
                    helper.insertProducto(Producto( name = etName.text.toString(), desc = etDesc.text.toString()))
                    dismiss()
                }

            }
        }

    }


    fun setOnDismissListener(listener: OnDismissListener) {
        onDismissListener = listener
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismissListener?.onDismissOnActivity()
    }

    interface OnDismissListener {
        fun onDismissOnActivity()
    }



}