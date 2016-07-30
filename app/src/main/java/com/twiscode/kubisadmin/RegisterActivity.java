package com.twiscode.kubisadmin;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.twiscode.kubisadmin.POJO.User;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.civProfilePicture)
    CircleImageView civProfilePicture;
    @BindView(R.id.etUsername)
    EditText etUsername;
    @BindView(R.id.etDescription)
    EditText etDescription;
    @BindView(R.id.btnDone)
    Button btnDone;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference database;
    StorageReference storageRef;
    DatabaseReference usersRef;

    Uri selectedImage=null;
    Uri hasilUpload=null;

    private static int RESULT_LOAD_IMAGE = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        reqPermission();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        usersRef = FirebaseDatabase.getInstance().getReference().child("users");
        database = FirebaseDatabase.getInstance().getReference();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://startup-hunt-2dbeb.appspot.com/");
        if(mUser!=null){
            selectedImage=mUser.getPhotoUrl();
            if(selectedImage!=null) {
                Picasso.with(this).load(selectedImage).into(civProfilePicture);
            }
            btnDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(selectedImage!=null) {
                        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
                        StorageReference riversRef = storageRef.child("images/" + selectedImage.getLastPathSegment()+timeStamp);
                        UploadTask uploadTask = riversRef.putFile(selectedImage);

                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                                Toast.makeText(RegisterActivity.this, "Upload image failed.", Toast.LENGTH_SHORT).show();
                                selectedImage=null;
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                String sdownload = downloadUrl.toString();
                                User newUser = new User();
                                newUser.setUid(mUser.getUid());
                                newUser.setName(mUser.getDisplayName());
                                newUser.setImageUrl(sdownload);
                                newUser.setUsername(etUsername.getText().toString());
                                newUser.setDescription(etDescription.getText().toString());

                                usersRef.child(mUser.getUid()).setValue(newUser, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                        if (databaseError == null) {
                                            Intent resultIntent = new Intent();
                                            setResult(RESULT_OK, resultIntent);
                                            finish();
                                        } else {
                                            Toast.makeText(RegisterActivity.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        });

                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this, "Please set your profile picture.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            civProfilePicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    // Show only images, no videos or anything else
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    // Always show the chooser (if there are multiple options available)
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                selectedImage = result.getUri();
                hasilUpload = result.getUri();
                Picasso.with(this).load(hasilUpload).into(civProfilePicture);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Log.v("errorCrop","ini error");
                Exception error = result.getError();
                hasilUpload=null;
            }
            else
            {
                hasilUpload=null;
            }
        }
        else
        {
            hasilUpload=null;
        }
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            hasilUpload = data.getData();
            CropImage.activity(hasilUpload)
                    .setMinCropResultSize(100,100)
                    .setInitialCropWindowPaddingRatio(0)
                    .setFixAspectRatio(true)
                    .setAspectRatio(150,150)
                    .start(this);
        }
    }

    public void reqPermission()
    {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {
                    Toast.makeText(this, "The app was not allowed to read to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }
}
