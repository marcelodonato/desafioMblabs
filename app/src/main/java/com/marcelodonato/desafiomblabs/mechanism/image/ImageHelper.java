package com.marcelodonato.desafiomblabs.mechanism.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageHelper {

    public static Uri storeImage(Bitmap storedImage) {
        FileOutputStream fileOutputStream;
        File path = Environment.getExternalStorageDirectory();
        File file = new File(path, "shareImage.jpg");

        try {
            fileOutputStream = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);
            storedImage.compress(Bitmap.CompressFormat.JPEG, 100, bos);

            bos.flush();
            bos.close();
            fileOutputStream.flush();
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Uri.parse("file://" + file.getAbsolutePath());
    }

    public static int densityToPixels(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}
