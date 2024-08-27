package cn.bingoogolapple.photopicker.adapter

import android.graphics.drawable.Drawable
import android.media.ImageReader
import android.net.Uri
import cn.bingoogolapple.photopicker.R
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import java.io.File

/**
 * https://github.com/davemorrissey/subsampling-scale-image-view/issues/234
 */
class SubsamplingScaleImageViewTarget(view: SubsamplingScaleImageView)
    : CustomViewTarget<SubsamplingScaleImageView, File>(view) {

    override fun onStart() {
        view.setImage(ImageSource.resource(R.mipmap.bga_pp_ic_holder_dark))
    }

    override fun onResourceReady(resource: File, transition: Transition<in File>?) {
        view.setImage(ImageSource.uri(Uri.fromFile(resource)))
    }

    override fun onLoadFailed(errorDrawable: Drawable?) {
        // Ignore
        view.setImage(ImageSource.resource(R.mipmap.bga_pp_ic_holder_dark))
    }

    override fun onResourceCleared(placeholder: Drawable?) {
        // Ignore
    }
}