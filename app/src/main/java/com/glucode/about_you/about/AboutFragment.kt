package com.glucode.about_you.about

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.glucode.about_you.about.views.ProfileCardView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.edit
import androidx.core.net.toUri
import com.glucode.about_you.about.views.QuestionCardView
import com.glucode.about_you.databinding.FragmentAboutBinding
import com.glucode.about_you.mockdata.MockData

class AboutFragment: Fragment() {

    private lateinit var binding: FragmentAboutBinding
    private lateinit var profileCardView: ProfileCardView
    private val REQUEST_IMAGE_PICK = 1
    private val REQUEST_PERMISSION_READ_EXTERNAL_STORAGE =2
//    private val PREFS_NAME = "com.glucode.about_you.about.PREFS"
//    private val KEY_PROFILE_PIC_URI = "profilePicUri"

//    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
//        uri?.let {
//          //  saveProfilePictureUri(it)
//            val profileView = binding.container.findViewWithTag<ProfileCardView>("profileView")
//            profileView?.profilePictureUri = it
//        }
//    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setProfileCard()
        setUpQuestions()
    }
    //test

    private fun setProfileCard(){

        val engineerName = arguments?.getString("name")
        val role = arguments?.getString("role")
        val engineer = MockData.engineers.firstOrNull { it.name == engineerName }

        engineer?.let{
            profileCardView = ProfileCardView(requireContext())
            profileCardView.name = engineerName
            profileCardView.techRole = role
            profileCardView.years = it.quickStats.years.toString()
            profileCardView.coffees = it.quickStats.coffees.toString()
            profileCardView.bugs = it.quickStats.bugs.toString()

            val defaultImageUri = it.defaultImageName
            if (defaultImageUri != null){
                profileCardView.setImage(defaultImageUri)
        }


//        val profileView = ProfileCardView(requireContext()).apply {
//            name = engineerName
//            techRole = role
//            years = engineer.quickStats.years.toString()
//            coffees = engineer.quickStats.coffees.toString()
//            bugs = engineer.quickStats.bugs.toString()
//            tag = "profileView"
           // profilePictureUri = getSavedProfilePictureUri() ?: engineer.defaultImageName

//            onProfilePictureClickListener = {
//                pickImage()
//            }

        }

        binding.container.addView(profileCardView)

        profileCardView.imageView.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent,REQUEST_IMAGE_PICK)
        }
    }

    private fun setUpQuestions() {
        val engineerName = arguments?.getString("name")
        val engineer = MockData.engineers.first { it.name == engineerName }

        engineer.questions.forEach { question ->
            val questionView = QuestionCardView(requireContext())
            questionView.title = question.questionText
            questionView.answers = question.answerOptions
            questionView.selection = question.answer.index

            binding.container.addView(questionView)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK) {
            val selectedImage: Uri? = data?.data
            selectedImage?.let {
                Log.d("AboutFragment", "Selected Image URI: $it")
                if (::profileCardView.isInitialized) {
                    profileCardView.setImage(it)
                    MockData.engineers.find { it.name == arguments?.getString("name") }?.defaultImageName = it
                    setProfileCard()
                }
            }
        }
    }

//    private fun pickImage() {
//        pickImageLauncher.launch("image/*")
//    }

//    private fun saveProfilePictureUri(uri: Uri) {
//        val sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
//        sharedPreferences.edit {
//            putString(KEY_PROFILE_PIC_URI, uri.toString())
//        }
//        MockData.engineers.find { it.name == arguments?.getString("name") }?.defaultImageName
//    }
//
//    private fun getSavedProfilePictureUri(): Uri? {
//        val sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, 0)
//        val uriString = sharedPreferences.getString(KEY_PROFILE_PIC_URI, null)
//        return uriString?.toUri()
//    }


}