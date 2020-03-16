package com.princeakash.budgetmanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class EditCategoryDialogFragment extends DialogFragment {
    EditText editTextCategory;
    String oldCategoryName;
    public interface EditCategoryDialogListener{
        void onDialogPositiveClick(DialogFragment dialog, String oldCategoryName, String newCategoryName);
    }

    public EditCategoryDialogFragment(String oldCategoryName){
        this.oldCategoryName = oldCategoryName;
    }

    EditCategoryDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            listener = (EditCategoryDialogListener) context;
        } catch(ClassCastException e){
            Log.d(TAG, getActivity().toString() + " must implement EditCategoryDialogListener.");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_new_category_layout, null);
        editTextCategory = view.findViewById(R.id.editTextCategoryName);
        builder.setView(view)
                .setTitle("Update Category")
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newCategoryName = editTextCategory.getText().toString();
                        listener.onDialogPositiveClick(EditCategoryDialogFragment.this, oldCategoryName, newCategoryName);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }
}
