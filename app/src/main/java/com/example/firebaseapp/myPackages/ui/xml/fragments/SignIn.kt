package com.example.firebaseapp.myPackages.ui.xml.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.firebaseapp.R
import com.example.firebaseapp.databinding.FragmentSignInBinding
import com.example.firebaseapp.myPackages.MainActivity
import com.example.firebaseapp.myPackages.data.local.UserData.Companion.saveUser
import com.example.firebaseapp.myPackages.data.remote.firebase.auth.repo.AuthRepo
import com.example.firebaseapp.myPackages.data.remote.firebase.auth.repoImp.AuthRepoImpl

class SignIn : Fragment(R.layout.fragment_sign_in) {
    private lateinit var binding: FragmentSignInBinding
    private lateinit var navController: NavController
    var authRepo: AuthRepo? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignInBinding.bind(view)
        navController = Navigation.findNavController(view)

        val activity = activity as MainActivity
        activity.supportActionBar?.hide()

        authRepo = AuthRepoImpl(requireContext())

        binding.login.setOnClickListener()
        {
          signIn()
        }
    }

    //***************************************************************************************
    private fun signIn() {
        if (NoFieldEmpty()) {
           VisibilityHandling(true)
            binding.login.isVisible = false

            authRepo?.let {
                it.signIn(
                    email = binding.email.text.toString(),
                    password = binding.password.text.toString(),
                    onSuccess = {
                        binding.progressBarSignup.isVisible = false
                        var bundle = bundleOf(
                            "key1" to binding.email.text.toString(),
                            "key2" to binding.password.text.toString()
                        )
                        navController.navigate(R.id.action_signIn_to_dealingWithNote, bundle)
                    },
                    onFailer = {
                        binding.progressBarSignup.isVisible = false
                        binding.login.isVisible = true
                        Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                    }
                )
            }
        } else {
            VisibilityHandling(false)
        }
    }

    private fun NoFieldEmpty(): Boolean {
        return (binding.email.text.isNotEmpty() &&
                binding.password.text.isNotEmpty())
    }

    private fun VisibilityHandling(
        ok: Boolean
    ) {
        if (ok) {
            binding.emailrequired.isVisible = false
            binding.passwordrequired.isVisible = false
            binding.progressBarSignup.isVisible = true
        } else {
            binding.emailrequired.isVisible = binding.email.text.isEmpty()
            binding.passwordrequired.isVisible = binding.password.text.isEmpty()
        }
    }
}