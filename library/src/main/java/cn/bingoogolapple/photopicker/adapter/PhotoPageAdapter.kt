package cn.bingoogolapple.photopicker.adapter

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import cn.bingoogolapple.photopicker.R
import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView

class PhotoPageAdapter(
    fragmentActivity: FragmentActivity,
    val previewPhotos: List<String>
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = previewPhotos.size

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int)
        val fragment = PhotoFragment()
        fragment.arguments = Bundle().apply {
            // Our object is just an integer :-P
            putString("url", previewPhotos[position])
        }

        return fragment
    }
}

private const val ARG_OBJECT = "object"

// Instances of this class are fragments representing a single
// object in our collection.
class PhotoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.bga_pp_item_photo_preview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey("url") }?.apply {
            val url = getString("url")
            val imageView = view.findViewById<SubsamplingScaleImageView>(R.id.image_view)

            if (url?.startsWith("http") == true) {
                Glide.with(view.context)
                    .download(GlideUrl(url))
                    .into(SubsamplingScaleImageViewTarget(imageView))
            } else {
                imageView.setImage(ImageSource.resource(R.mipmap.bga_pp_ic_holder_dark))
                try {
                    imageView.setImage(ImageSource.uri(Uri.parse(url)))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            imageView.setOnTouchListener { v, event ->
                // on view tag
                if (event.action == MotionEvent.ACTION_UP) {
                    // on view tap
                    (activity as BGAPhotoPreviewActivity)?.onViewTap()
                }
                return@setOnTouchListener false
            }

            //bgaImageView.setImage(ImageSource.uri(url?: ""))
//
//            val photoViewAttacher = BGABrowserPhotoViewAttacher(bgaImageView)
//            (context as? OnViewTapListener)?.let { photoViewAttacher.setOnViewTapListener(it) }
//            bgaImageView.setDelegate(BGAImageView.Delegate { drawable ->
//                if (drawable != null && drawable.intrinsicHeight > drawable.intrinsicWidth && drawable.intrinsicHeight > BGAPhotoPickerUtil.getScreenHeight()) {
//                    photoViewAttacher.setIsSetTopCrop(true)
//                    photoViewAttacher.setUpdateBaseMatrix()
//                } else {
//                    photoViewAttacher.update()
//                }
//            })
//
//            BGAImage.display(
//                bgaImageView,
//                mipmap.bga_pp_ic_holder_dark,
//                url,
//                BGAPhotoPickerUtil.getScreenWidth(),
//                BGAPhotoPickerUtil.getScreenHeight()
//            )

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

//        kotlin.runCatching {
//            view?.findViewById<BGAImageView>(R.id.bga_image_view)?.apply {
//                Glide.with(this).clear(this)
//            }
//        }.onFailure {
//            it.printStackTrace()
//        }
    }

}