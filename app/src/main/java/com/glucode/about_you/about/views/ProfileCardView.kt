package com.glucode.about_you.about.views

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.glucode.about_you.R
import com.glucode.about_you.databinding.ViewProfileCardBinding

class ProfileCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {
    private val binding: ViewProfileCardBinding =
        ViewProfileCardBinding.inflate(LayoutInflater.from(context), this)

    var name: String? = null
        set(value) {
            field = value
            binding.nameEngineer.text = value
        }
    var techRole: String? = null
        set(value) {
            field = value
            binding.roleEngineer.text = value
        }
    var years: String? = null
        set(value) {
            field = value
            binding.Years.text = value
        }
    var coffees: String? = null
        set(value) {
            field = value
            binding.Coffees.text = value
        }
    var bugs: String? = null
        set(value) {
            field = value
            binding.Bugs.text = value
        }
//    var profilePictureUri: Uri? = null
//        set(value) {
//            field = value
//            binding.profilePictures.setImageURI(profilePictureUri)


    fun setImage(uri: Uri){
        Glide.with(this)
            .load(uri)
            .into(binding.profilePictures)
    }

    val imageView: ImageView
        get() = binding.profilePictures

//    init {
//        radius = resources.getDimension(R.dimen.padding_small)
//        elevation = resources.getDimension(R.dimen.elevation_normal)
//
//
//        binding.profilePictures.setOnClickListener {
//         onProfilePictureClickListener?.invoke()
//        }
//    }
//
//    var onProfilePictureClickListener: (() -> Unit)? = null



}

