package com.example.firebaseapp.myPackages.ui.xml.fragments

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.compose.ui.res.stringResource
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.firebaseapp.R
import com.example.firebaseapp.databinding.FragmentSignInBinding
import com.example.firebaseapp.myPackages.MainActivity
import com.example.firebaseapp.myPackages.data.remote.firebase.auth.repo.AuthRepo
import com.example.firebaseapp.myPackages.data.remote.firebase.auth.repoImp.AuthRepoImpl
import com.example.firebaseapp.myPackages.ui.compose.components.LoadingView

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

        editTextPasswordToggle(binding.password)

        authRepo = AuthRepoImpl(requireContext())

        binding.login.setOnClickListener()
        {
          signIn()
        }

        binding.progressBar.setContent {
            LoadingView()
        }
    }

    //***************************************************************************************
    private fun signIn() {
        if (NoFieldEmpty()) {
           VisibilityHandling(true)
            binding.login.text = ""

            authRepo?.let {
                it.signIn(
                    email = binding.email.text.toString(),
                    password = binding.password.text.toString(),
                    onSuccess = {
                        binding.progressBar.isVisible = false
                        navController.navigate(R.id.action_signIn_to_dealingWithNote)
                    },
                    onFailure = {
                        binding.progressBar.isVisible = false
                        binding.login.text = getString(R.string.sign_in)
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
            binding.progressBar.isVisible = true
        } else {
            binding.emailrequired.isVisible = binding.email.text.isEmpty()
            binding.passwordrequired.isVisible = binding.password.text.isEmpty()
        }
    }
}


fun editTextPasswordToggle(editText: EditText) {
    var isPasswordVisible = false
    editText.transformationMethod = PasswordTransformationMethod()
    editText.setCompoundDrawablesWithIntrinsicBounds(
        R.drawable.lock_open, 0, R.drawable.visibility, 0
    )
    editText.setOnTouchListener { _, event ->
        if (event.action == MotionEvent.ACTION_UP) {
            val drawableEnd = 2
            val drawable = editText.compoundDrawables[drawableEnd]

            if (drawable != null) {
                val bounds = drawable.bounds
                if (event.rawX >= (editText.right - bounds.width())) {

                    isPasswordVisible = !isPasswordVisible

                    if (isPasswordVisible) {
                        editText.transformationMethod = null
                        editText.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.lock_open, 0, R.drawable.visibility_off, 0
                        )
                    } else {
                        editText.transformationMethod = PasswordTransformationMethod()
                        editText.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.lock_open, 0, R.drawable.visibility, 0
                        )
                    }

                    editText.setSelection(editText.text.length)
                    return@setOnTouchListener true
                }
            }
        }
        false
    }
}
