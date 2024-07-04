package com.glucode.about_you.engineers

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.glucode.about_you.databinding.ItemEngineerBinding
import com.glucode.about_you.engineers.models.Engineer

class EngineersRecyclerViewAdapter(
    private var engineers: List<Engineer>,
    private val onClick: (Engineer) -> Unit
) : RecyclerView.Adapter<EngineersRecyclerViewAdapter.EngineerViewHolder>() {
    //private val PREFS_NAME = "com.glucode.about_you.about.PREFS"
    //private val KEY_PROFILE_PIC_URI = "profilePicUri"

    override fun getItemCount() = engineers.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EngineerViewHolder {
        return EngineerViewHolder(ItemEngineerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: EngineerViewHolder, position: Int) {
        holder.bind(engineers[position], onClick)
    }

    inner class EngineerViewHolder(private val binding: ItemEngineerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(engineer: Engineer, onClick: (Engineer) -> Unit) {
            binding.name.text = engineer.name
            binding.role.text = engineer.role
            binding.root.setOnClickListener {
                onClick(engineer)
            }

            //TODO - set profile picture
//            statusIcon.setDrawable(item.icon)
            //binding.profileImage.setImageURI(engineer.defaultImageName)

            // Retrieve the saved profile picture URI from SharedPreferences
            //val sharedPreferences = binding.root.context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
           // val profilePicUriString = sharedPreferences.getString(KEY_PROFILE_PIC_URI, null)
           // val profilePicUri = profilePicUriString

            // Set the profile image URI
            binding.profileImage.setImageURI(engineer.defaultImageName)

        }
    }
}