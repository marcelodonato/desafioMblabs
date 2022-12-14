package com.marcelodonato.desafiomblabs.mechanism.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import com.marcelodonato.desafiomblabs.mechanism.file.FileUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class ImageDecoder {

    public static Bitmap changeQuality(Bitmap src, Bitmap.CompressFormat format,
                                        int quality) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        src.compress(format, quality, os);

        byte[] array = os.toByteArray();
        return BitmapFactory.decodeByteArray(array, 0, array.length);
    }

    public static Bitmap decodeScaledBitmapFromSdCard(String filePath, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static Bitmap decodeSampledBitmapFromFile(File imageFile, int reqWidth, int reqHeight) throws IOException {
        // First decode with inJustDecodeBounds=true to check dimensions
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        Bitmap scaledBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
        //check the rotation of the image and display it properly
        ExifInterface exif;
        exif = new ExifInterface(imageFile.getAbsolutePath());
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
        Matrix matrix = new Matrix();
        switch (orientation) {
            case 6:
                matrix.postRotate(90);
                break;
            case 3:
                matrix.postRotate(180);
                break;
            case 8:
                matrix.postRotate(270);
                break;
        }
        scaledBitmap =
                Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                        true);
        return scaledBitmap;
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    public static File saveImageBitmap(Bitmap bitmap, File dir, String name) throws Exception {
        File imageFile = new File(dir, name);
        OutputStream outputStream = new FileOutputStream(imageFile);

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        outputStream.flush();
        outputStream.close();

        return imageFile;
    }

    public static String generateBase64(String filePath) {
        // Retrieve current file
        File imageFile = new File(filePath);
        // Check if file exists
        if (imageFile.exists()) {
            // Encode file
            String pictureEncodedBase64 = FileUtil.encodeFileToBase64(imageFile);

            // get file extension
            String fileExtension = getImageFileExtension(filePath);

            StringBuffer stringBuffer = new StringBuffer("data:image/");
            stringBuffer.append(fileExtension);
            stringBuffer.append(";base64,");
            stringBuffer.append(pictureEncodedBase64);

            // Set picture on user object
            return stringBuffer.toString();
        }

        return null;
    }

    public static String appendBase64Data(String base64) {
        return "";
    }

    public static String getImageFileExtension(String filename) {
        if (filename != null && filename.contains(".")) {
            String filenameArray[] = filename.split("\\.");
            return filenameArray[filenameArray.length - 1];
        }

        return "jpg";
    }
}
