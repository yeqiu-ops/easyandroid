package com.yeqiu.easyandroid.utils.image;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.bumptech.glide.util.Preconditions;
import com.yeqiu.easyandroid.R;
import com.yeqiu.easyandroid.utils.CommonUtil;
import com.yeqiu.easyandroid.utils.ScreenUtils;
import com.yeqiu.easyandroid.utils.image.config.GlideConfigImpl;
import com.yeqiu.easyandroid.utils.image.progress.EasyGlideApp;
import com.yeqiu.easyandroid.utils.image.progress.GlideImageViewTarget;
import com.yeqiu.easyandroid.utils.image.progress.GlideRequest;
import com.yeqiu.easyandroid.utils.image.progress.GlideRequests;
import com.yeqiu.easyandroid.utils.image.progress.OnProgressListener;
import com.yeqiu.easyandroid.utils.image.progress.ProgressManager;
import com.yeqiu.easyandroid.utils.image.transformation.BlurTransformation;
import com.yeqiu.easyandroid.utils.image.transformation.BorderTransformation;
import com.yeqiu.easyandroid.utils.image.transformation.GrayscaleTransformation;
import com.yeqiu.easyandroid.utils.image.transformation.RoundedCornersTransformation;

import java.io.File;

/**
 * @project: LearningMachine
 * @author: 小卷子
 * @date: 2022/10/17
 * @describe:
 * @fix:
 */
public class ImageLoader {

    public static int placeHolderImageView = R.drawable.img_placeholder;
    public static int circlePlaceholderImageView = R.drawable.img_placeholder_circle;


    public static void loadImage(Context context, String url, ImageView imageView) {
        loadImage(context, url, imageView, placeHolderImageView);
    }


    public static void loadImage(Context context, Bitmap bitmap, ImageView imageView) {

        BitmapDrawable bitmapDrawable = new BitmapDrawable(imageView.getResources(), bitmap);
        Glide.with(context)
                .setDefaultRequestOptions(new RequestOptions())
                .load(bitmapDrawable)
                .into(imageView);
    }

    public static void loadImage(Context context, String url, ImageView imageView, @DrawableRes int placeHolder) {
        loadImage(context, url, imageView, placeHolder, null);
    }

    public static void loadImage(Context context, String url, ImageView imageView, OnProgressListener onProgressListener) {
        loadImage(context, url, imageView, placeHolderImageView, onProgressListener);
    }


    public static void loadImage(Context context, String url, ImageView imageView, @DrawableRes int placeHolder, OnProgressListener onProgressListener) {

        loadImage(context,
                GlideConfigImpl
                        .builder()
                        .url(url)
                        .isCropCenter(true)
                        .isCrossFade(true)
                        .errorPic(placeHolder)
                        .placeholder(placeHolder)
                        .imageView(imageView)
                        .progressListener(onProgressListener)
                        .build());
    }


    /**
     * 加载本地图片
     *
     * @param context
     * @param drawableId
     * @param imageView
     */
    public static void loadImage(Context context, @RawRes @DrawableRes @Nullable Integer drawableId, ImageView imageView) {
        loadImage(context, GlideConfigImpl
                .builder()
                .drawableId(drawableId)
                .isCropCenter(true)
                .imageView(imageView)
                .build());
    }


    public static void loadCircleImage(Context context, String url, ImageView imageView) {
        loadCircleImage(context, url, imageView, circlePlaceholderImageView);
    }

    public static void loadCircleImage(Context context, String url, ImageView imageView, @DrawableRes int placeHolder) {
        loadCircleImage(context, url, imageView, placeHolder, null);
    }

    public static void loadCircleImage(Context context, String url, ImageView imageView, OnProgressListener onProgressListener) {
        loadCircleImage(context, url, imageView, circlePlaceholderImageView, onProgressListener);
    }

    public static void loadCircleImage(Context context, String url, ImageView imageView,
                                       @DrawableRes int placeHolder, OnProgressListener onProgressListener) {
        loadImage(context,
                GlideConfigImpl
                        .builder()
                        .url(url)
                        .isCropCircle(true)
                        .isCrossFade(true)
                        .errorPic(placeHolder)
                        .placeholder(placeHolder)
                        .imageView(imageView)
                        .progressListener(onProgressListener)
                        .build());
    }


    /**
     * 圆角图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadCornerImage(Context context, String url, ImageView imageView) {
        loadCornerImage(context, ScreenUtils.dp2px(20), url, imageView, placeHolderImageView);
    }

    /**
     * 圆角图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadCornerImage(Context context, String url, ImageView imageView,
                                       @DrawableRes int placeHolder) {
        loadCornerImage(context, ScreenUtils.dp2px(20), url, imageView, placeHolder);
    }


    /**
     * 圆角图片
     *
     * @param context
     * @param drawableId
     * @param imageView
     */
    public static void loadCornerImage(Context context, @RawRes @DrawableRes @Nullable Integer drawableId, ImageView imageView) {

        int radius = ScreenUtils.dp2px(20);
        loadImage(context, GlideConfigImpl
                .builder()
                .transformation(new CenterCrop(), new RoundedCornersTransformation(radius, 0))
                .drawableId(drawableId)
                .isCropCenter(true)
                .imageView(imageView)
                .build());

    }

    public static void loadCornerImage(Context context, int radius, String url, ImageView imageView) {
        loadCornerImage(context, radius, url, imageView, placeHolderImageView);
    }


    private static void loadCornerImage(Context context, int radius, String url, ImageView imageView,
                                        int placeHolderImageView) {
        loadCornerImage(context, radius, url, imageView, placeHolderImageView, null);
    }

    public static void loadCornerImage(Context context, int radius, String url, ImageView imageView, @DrawableRes int placeHolder,
                                       OnProgressListener onProgressListener) {
        loadImage(context,
                GlideConfigImpl
                        .builder()
                        .url(url)
                        .transformation(new CenterCrop(), new RoundedCornersTransformation(radius, 0))
                        .isCrossFade(true)
                        .errorPic(placeHolder)
                        .placeholder(placeHolder)
                        .progressListener(onProgressListener)
                        .imageView(imageView)
                        .build());
    }


    /**
     * 加载灰色图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadGrayImage(Context context, String url, ImageView imageView) {
        loadGrayImage(context, url, imageView, placeHolderImageView);
    }

    public static void loadGrayImage(Context context, String url, ImageView imageView, @DrawableRes int placeHolder) {
        loadImage(context,
                GlideConfigImpl
                        .builder()
                        .url(url)
                        .transformation(new CenterCrop(), new GrayscaleTransformation())
                        .isCrossFade(true)
                        .errorPic(placeHolder)
                        .placeholder(placeHolder)
                        .imageView(imageView)
                        .build());
    }


    /**
     * 加载虚化图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadBlurImage(Context context, String url, ImageView imageView) {
        loadBlurImage(context, url, 10, imageView, placeHolderImageView);
    }

    public static void loadBlurImage(Context context, String url, int radius, ImageView imageView) {
        loadBlurImage(context, url, radius, imageView, placeHolderImageView);
    }

    public static void loadBlurImage(Context context, String url, int radius, ImageView imageView, @DrawableRes int placeHolder) {
        loadImage(context,
                GlideConfigImpl
                        .builder()
                        .url(url)
                        .transformation(new CenterCrop(), new BlurTransformation(context, radius))
                        .isCrossFade(true)
                        .errorPic(placeHolder)
                        .placeholder(placeHolder)
                        .imageView(imageView)
                        .build());
    }


    public static void loadBorderImage(Context context, String url, ImageView imageView) {
        loadBorderImage(context, url, 2, Color.parseColor("#ACACAC"), imageView, placeHolderImageView);
    }

    public static void loadBorderImage(Context context, String url, int borderWidth, @ColorInt int borderColor, ImageView imageView) {
        loadBorderImage(context, url, borderWidth, borderColor, imageView, placeHolderImageView);
    }

    public static void loadBorderImage(Context context, String url, int borderWidth, @ColorInt int borderColor, ImageView imageView, @DrawableRes int placeHolder) {
        loadImage(context,
                GlideConfigImpl
                        .builder()
                        .url(url)
                        .transformation(new BorderTransformation(borderWidth, borderColor))
                        .isCrossFade(true)
                        .errorPic(placeHolder)
                        .placeholder(placeHolder)
                        .imageView(imageView)
                        .build());
    }

    /**
     * 提供了一下如下变形类，支持叠加使用
     * BlurTransformation
     * GrayScaleTransformation
     * RoundedCornersTransformation
     * CircleCrop
     * CenterCrop
     */
    public static void loadImageWithTransformation(Context context, String url, ImageView imageView, BitmapTransformation... bitmapTransformations) {
        loadImageWithTransformation(context, url, imageView, R.color.transparent, bitmapTransformations);
    }

    public static void loadImageWithTransformation(Context context, String url, ImageView imageView, @DrawableRes int placeHolder, BitmapTransformation... bitmapTransformations) {
        loadImage(context,
                GlideConfigImpl
                        .builder()
                        .url(url)
                        .transformation(bitmapTransformations)
                        .isCrossFade(true)
                        .errorPic(placeHolder)
                        .placeholder(placeHolder)
                        .imageView(imageView)
                        .build());
    }


    private static void loadImage(Context context, GlideConfigImpl config) {

        try {
            Preconditions.checkNotNull(context, "Context is required");
            Preconditions.checkNotNull(config, "ImageConfigImpl is required");
            Preconditions.checkNotNull(config.getImageView(), "ImageView is required");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        if (context instanceof Activity) {
            if (((Activity) context).isFinishing() || ((Activity) context).isDestroyed()) {
                return;
            }
        }

        if (CommonUtil.isEmpty(config.getUrl()) && config.getDrawableId() == 0) {
            return;
        }


        GlideRequests requests;
        requests = EasyGlideApp.with(context);
        GlideRequest<Drawable> glideRequest = null;
        if (config.getDrawableId() != 0) {
            glideRequest = requests.load(config.getDrawableId());
        } else {
            glideRequest = requests.load(config.getUrl());
        }
        //缓存策略
        switch (config.getCacheStrategy()) {
            case 1:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.NONE);
                break;
            case 2:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
                break;
            case 3:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.DATA);
                break;
            case 4:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
                break;
            default:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.ALL);
                break;
        }
        if (config.isCrossFade()) {
            DrawableCrossFadeFactory factory = new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();
            glideRequest.transition(withCrossFade(factory));
        }

        if (config.isImageRadius()) {
            glideRequest.transform(new RoundedCorners(config.getImageRadius()));
        }

        if (config.isBlurImage()) {
            glideRequest.transform(new BlurTransformation(context, config.getBlurValue()));
        }
        //glide用它来改变图形的形状
        if (config.getTransformation() != null) {
            glideRequest.transform(config.getTransformation());
        }

        if (config.getPlaceHolderDrawable() != null) {
            glideRequest.placeholder(config.getPlaceHolderDrawable());
        }
        //设置占位符
        if (config.getPlaceholder() != 0) {
            glideRequest.placeholder(config.getPlaceholder());
        }
        //设置错误的图片
        if (config.getErrorPic() != 0) {
            glideRequest.error(config.getErrorPic());
        }
        //设置请求 url 为空图片
        if (config.getFallback() != 0) {
            glideRequest.fallback(config.getFallback());
        }

        if (config.getResizeX() != 0 && config.getResizeY() != 0) {
            glideRequest.override(config.getResizeX(), config.getResizeY());
        } else {
            glideRequest.override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
        }

        if (config.isCropCenter()) {
            glideRequest.centerCrop();
        }

        if (config.isCropCircle()) {
            glideRequest.circleCrop();
        }

        if (config.decodeFormate() != null) {
            glideRequest.format(config.decodeFormate());
        }

        if (config.isFitCenter()) {
            glideRequest.fitCenter();
        }

        if (config.getRequestListener() != null) {
            glideRequest.addListener(config.getRequestListener());
        }

        if (config.getOnProgressListener() != null) {
            ProgressManager.addListener(config.getUrl(), config.getOnProgressListener());
        }

        glideRequest.into(new GlideImageViewTarget(config.getImageView(), config.getUrl()));

    }


    /**
     * 取消图片加载
     */
    public static void cancelLoading(final Context context, ImageView imageView) {

        try {
            Preconditions.checkNotNull(context, "Context is required");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }


        EasyGlideApp.get(context).getRequestManagerRetriever().get(context).clear(imageView);
    }


    public void downloadImage(final Context context, final String url, RequestListener<File> requestListener) {
        Glide.with(context)
                .downloadOnly()
                .load(url)
                .addListener(requestListener)
                .preload();
    }


}
