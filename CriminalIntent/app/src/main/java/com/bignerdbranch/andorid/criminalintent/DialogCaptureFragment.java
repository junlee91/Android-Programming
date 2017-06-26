package com.bignerdbranch.andorid.criminalintent;


import android.app.AlertDialog;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by Jun on 2017-06-26.
 */

public class DialogCaptureFragment extends DialogFragment {
    private ImageView mPhotoView;
    private Uri mImageUri;
    private static File filepath;

    public static DialogCaptureFragment newInstance(File path){
        DialogCaptureFragment fragment = new DialogCaptureFragment();
        filepath = path;
        return fragment;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_capture, null);

        mImageUri = FileProvider.getUriForFile(getContext(), "com.bignerdbranch.andorid.criminalintent.fileProvider", filepath);

        mPhotoView = (ImageView)v.findViewById(R.id.dialog_capture_picture);
        mPhotoView.setImageURI(mImageUri);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.capture_title)
                .create();
    }
}
