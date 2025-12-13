package com.example.firebaseapp.myPackages.ui.xml.fragments

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.firebaseapp.R
import com.example.firebaseapp.databinding.FragmentSignUpBinding
import com.example.firebaseapp.myPackages.MainActivity
import com.example.firebaseapp.myPackages.data.remote.firebase.auth.repo.AuthRepo
import com.example.firebaseapp.myPackages.data.remote.firebase.auth.repoImp.AuthRepoImpl
import com.example.firebaseapp.myPackages.data.models.User
import com.example.firebaseapp.myPackages.utils.getCurrentDate

class SignUp : Fragment(R.layout.fragment_sign_up) {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var navController: NavController
    var authRepo: AuthRepo? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpBinding.bind(view)
        navController = Navigation.findNavController(view)

        val activity = activity as MainActivity
        activity.supportActionBar?.hide()

        authRepo = AuthRepoImpl(requireContext())

        binding.register.setOnClickListener {

            signUp()
        }

        activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    //******************************************************************************
    //for register with email and password

    private fun signUp() {
        if (NoFieldEmpty()) {
            VisibilityHandling(true)
            binding.register.isVisible = false
            authRepo?.let {
                it.signUp(
                    User(
                        email = binding.email.text.toString(),
                        password = binding.password.text.toString(),
                        userName = binding.userName.text.toString(),
                        joinDate = getCurrentDate()
                    ),
                    onSuccess = {
                        binding.progressBarSignup.isVisible = false
                        navController.navigate(R.id.action_signUp_to_signIn)

                    },
                    onFailer = {
                        binding.progressBarSignup.isVisible = false
                        binding.register.isVisible = true
                        Toast.makeText(
                            requireContext(),
                            it, Toast.LENGTH_LONG
                        ).show()
                    }
                )
            }
        } else {
            VisibilityHandling(false)
        }
    }

    private fun NoFieldEmpty(): Boolean {
        return (binding.email.text.isNotEmpty() &&
                binding.password.text.isNotEmpty() &&
                binding.userName.text.isNotEmpty())
    }

    private fun VisibilityHandling(
        ok: Boolean
    ) {
        if (ok) {
            binding.emailrequired.isVisible = false
            binding.passwordrequired.isVisible = false
            binding.nameRequired.isVisible = false
            binding.progressBarSignup.isVisible = true
        } else {
            binding.emailrequired.isVisible = binding.email.text.isEmpty()
            binding.passwordrequired.isVisible = binding.password.text.isEmpty()
            binding.nameRequired.isVisible = binding.userName.text.isEmpty()
        }
    }

}