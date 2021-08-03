package com.example.reverb;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import org.jetbrains.annotations.NotNull;

import java.security.MessageDigest;

public class GlideBlurTransformation extends BitmapTransformation {

    private RenderScript rs;
    public GlideBlurTransformation(Context context){
        super();
        rs= RenderScript.create(context);
    }
    @Override
    protected Bitmap transform(@NonNull @NotNull BitmapPool pool, @NonNull @NotNull Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap blurred_bitmap= toTransform.copy(Bitmap.Config.ARGB_8888,true);

        Allocation input = Allocation.createFromBitmap(rs,blurred_bitmap,Allocation.MipmapControl.MIPMAP_FULL,Allocation.USAGE_SHARED);
        Allocation output= Allocation.createTyped(rs,input.getType());

        ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setInput(input);
        script.setRadius(25);

        script.forEach(output);

        output.copyTo(blurred_bitmap);
        return blurred_bitmap;


    }

    @Override
    public void updateDiskCacheKey(@NonNull @NotNull MessageDigest messageDigest) {
        messageDigest.update("Blur Transformation".getBytes());

    }
}
