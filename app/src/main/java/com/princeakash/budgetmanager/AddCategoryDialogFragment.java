package com.princeakash.budgetmanager;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCategoryDialogFragment extends DialogFragment {
    private EditText editTextCategory;

    public interface AddCategoryDialogListener{
        public void onDialogPositiveClick(DialogFragment dialog, String categoryName);
    }

    AddCategoryDialogListener listener;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try{
            listener = (AddCategoryDialogListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(getActivity().toString() + " must implement AddCategoryDialogListener.");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.add_new_category_layout, null);
        builder.setView(view)
                .setTitle("Add Category")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String categoryName = editTextCategory.getText().toString();
                        listener.onDialogPositiveClick(AddCategoryDialogFragment.this, categoryName);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        editTextCategory = view.findViewById(R.id.editTextCategoryName);
        return builder.create();
    }
}
