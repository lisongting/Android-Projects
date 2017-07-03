package com.example.dialogfragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by lisongting on 2017/7/3.
 */

public class PromptDialogFragment extends DialogFragment {
    Button send;

    EditText editText;
    public static PromptDialogFragment newInstance(String prompt) {
        PromptDialogFragment pdf = new PromptDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("prompt", prompt);
        pdf.setArguments(bundle);
        return pdf;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_layout, container, false);
        send = (Button) view.findViewById(R.id.button_send);
        editText = (EditText) view.findViewById(R.id.editText);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editText.getText().toString();
                MainActivity act = (MainActivity) getActivity();
                act.onDialogDone(getTag(),true,s);
            }
        });
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(true);
    }

}
