package com.princeakash.budgetmanager;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class EditTargetDialogFragment extends DialogFragment {
    EditText editTextNewTarget;
    String oldTarget;
    EditTargetDialogListener editTargetDialogListener;
    int callerPosition;

    public EditTargetDialogFragment(int position, String oldTarget){
        this.callerPosition = position;
        this.oldTarget = oldTarget;
    }

    public interface EditTargetDialogListener{
        void onDialogPositiveClick(DialogFragment dialog, String newTarget, int position);
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            editTargetDialogListener = (EditTargetDialogListener) context;
        } catch(ClassCastException e){
            Log.d(TAG, getActivity().toString() + " must implement EditTargetDialogListener.");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_new_category_layout, null);
        editTextNewTarget = (EditText) view.findViewById(R.id.editTextCategoryName);
        editTextNewTarget.setText(oldTarget);

        builder.setView(view)
                .setTitle("Update Target")
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newTarget = editTextNewTarget.getText().toString();
                        editTargetDialogListener.onDialogPositiveClick(EditTargetDialogFragment.this, newTarget, callerPosition);
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
