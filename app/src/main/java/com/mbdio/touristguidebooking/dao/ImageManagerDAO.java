package com.mbdio.touristguidebooking.dao;

import android.app.Activity;
import android.content.ContentResolver;
import android.net.Uri;
import android.webkit.MimeTypeMap;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mbdio.touristguidebooking.utils.AppStateManager;

public class ImageManagerDAO {
    static StorageReference storageRef = FirebaseStorage.getInstance().getReference("uploads");

    public static void uploadImageToFirebase(Activity activity, Uri imageUri, ImageManagerCallbacks callback) {
        if (imageUri != null) {
            String filename = AppStateManager.getCurrentUser().getUserID()
                    + "_" + System.currentTimeMillis() + "." + getFileExtension(activity, imageUri);

            StorageReference fileReference = storageRef.child(filename);

            fileReference.putFile(imageUri).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                        String url = uri.toString();
                        callback.onImageUploaded(true, "Image uploaded successfully!", url);
                    });

                } else {
                    callback.onImageUploaded(false, "Error while uploading image, please try again later.",
                            null);
                }
            });
        }
    }

    private static String getFileExtension(Activity activity, Uri uri) {
        ContentResolver cR = activity.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void getDownloadUrl(StorageReference imageRef) {
        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String downloadUrl = uri.toString();
            }
        });
    }

}
