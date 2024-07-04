package com.glucode.about_you.about

import android.content.Context
import android.net.Uri
import android.os.Bundle
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
    private val PREFS_NAME = "com.glucode.about_you.about.PREFS"
    private val KEY_PROFILE_PIC_URI = "profilePicUri"

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            saveProfilePictureUri(it)
            val profileView = binding.container.findViewWithTag<ProfileCardView>("profileView")
            profileView?.profilePictureUri = it
        }
    }

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
        val engineer = MockData.engineers.first { it.name == engineerName }

        val profileView = ProfileCardView(requireContext()).apply {
            name = engineerName
            techRole = role
            years = engineer.quickStats.years.toString()
            coffees = engineer.quickStats.coffees.toString()
            bugs = engineer.quickStats.bugs.toString()
            tag = "profileView"
            profilePictureUri = getSavedProfilePictureUri() ?: engineer.defaultImageName

            onProfilePictureClickListener = {
                pickImage()
            }

        }

        binding.container.addView(profileView)
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


    private fun pickImage() {
        pickImageLauncher.launch("image/*")
    }

    private fun saveProfilePictureUri(uri: Uri) {
        val sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit {
            putString(KEY_PROFILE_PIC_URI, uri.toString())
        }
        MockData.engineers.find { it.name == arguments?.getString("name") }?.defaultImageName
    }

    private fun getSavedProfilePictureUri(): Uri? {
        val sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, 0)
        val uriString = sharedPreferences.getString(KEY_PROFILE_PIC_URI, null)
        return uriString?.toUri()
    }


}