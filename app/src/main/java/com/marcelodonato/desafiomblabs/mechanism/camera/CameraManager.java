package com.marcelodonato.desafiomblabs.mechanism.camera;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;

import com.marcelodonato.desafiomblabs.R;
import com.marcelodonato.desafiomblabs.mechanism.image.ImageDecoder;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.FileOutputStream;

import permissions.dispatcher.PermissionRequest;

public class CameraManager {

    private final Activity mActivity;
    private RetrieveImageListener mListener;

    public CameraManager(@NonNull final Activity activity) {
        mActivity = activity;
    }

    public void startAndroidCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File imageFile = getImageSourceFile();

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(mActivity, mActivity.getApplicationContext().getPackageName() + ".provider", imageFile));
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
        }

        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        mActivity.startActivityForResult(intent, RequestCode.SELECT_CAMERA);
    }

    public void startAndroidGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        if (intent.resolveActivity(mActivity.getPackageManager()) != null) {
            mActivity.startActivityForResult(intent, RequestCode.SELECT_PICTURE);
        } else {
            Toast.makeText(mActivity, R.string.error_gallery_access, Toast.LENGTH_SHORT).show();
        }
    }

    private File getImageSourceFile() {
        File dir = mActivity.getExternalCacheDir();
        return new File(dir, mActivity.getString(R.string.config_image_path_name));
    }

    public void showRequestProceed(@NonNull final PermissionRequest request) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setMessage(mActivity.getString(R.string.permission_rationale))
                .setCancelable(false)
                .setPositiveButton(mActivity.getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        request.proceed();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void showSettings() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setMessage(R.string.permission_never_ask)
                .setCancelable(false)
                .setPositiveButton(mActivity.getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", mActivity.getPackageName(), null);
                        intent.setData(uri);
                        mActivity.startActivity(intent);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        switch (requestCode) {
            case RequestCode.SELECT_CAMERA:
                if (resultCode == RESULT_OK) {
                    startCropActivity(null);
                }
                break;

            case RequestCode.SELECT_PICTURE:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();

                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = mActivity.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    String filePath = cursor.getString(0);

                    cursor.close();

                    startCropActivity(filePath);
                }
                break;

            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE :
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                   retrieveImage(new File(result.getUri().getPath()));
                } else {
                    showPictureRetrieveError();
                }
                break;
        }
    }

    private void startCropActivity(@Nullable final String filePath) {
        File imageSourceFile;
        if (filePath != null) {
            imageSourceFile = new File(filePath);
        } else {
            imageSourceFile = getImageSourceFile();
        }

        try {
            Bitmap bmp = ImageDecoder.decodeSampledBitmapFromFile(imageSourceFile, 1280, 960);
            FileOutputStream out = new FileOutputStream(imageSourceFile);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out);

        } catch (Exception e) {

        }

        Uri inputUri;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            inputUri = FileProvider.getUriForFile(mActivity, mActivity.getApplicationContext().getPackageName() + ".provider", imageSourceFile);
        } else {
            inputUri = Uri.fromFile(imageSourceFile);
        }
        CropImage.activity(inputUri)
                .setFixAspectRatio(true)
                .setAspectRatio(1, 1)
                .start(mActivity);
    }

    private void retrieveImage(File localImagePath) {
        if (localImagePath == null || !localImagePath.exists()) {
            showPictureRetrieveError();
            return;
        }

        try {

            String imagePath = localImagePath.getAbsolutePath();
            Bitmap bitmap = ImageDecoder.decodeScaledBitmapFromSdCard(imagePath, 500, 500);
            bitmap = ImageDecoder.changeQuality(bitmap, Bitmap.CompressFormat.JPEG, 60);
            File imageDecodedFile = ImageDecoder.saveImageBitmap(bitmap, mActivity.getExternalCacheDir(), mActivity.getString(R.string.config_decoded_image));

            if (mListener != null) {
//                mListener.onRetrieveImagePath(ImageDecoder.generateBase64(imageDecodedFile.getAbsolutePath()), bitmap);
                mListener.onRetrieveImagePath(imageDecodedFile.getAbsolutePath(), bitmap);
            }

        } catch (Exception e) {
            e.printStackTrace();
            showPictureRetrieveError();
        }
    }

    private void showPictureRetrieveError() {
        Toast.makeText(mActivity, R.string.alert_picture_error, Toast.LENGTH_SHORT).show();
    }

    public void setRetrieveImageListener(@NonNull final RetrieveImageListener listener) {
        mListener = listener;
    }
}
