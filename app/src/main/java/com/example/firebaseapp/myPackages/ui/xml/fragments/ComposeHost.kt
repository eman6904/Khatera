package com.example.firebaseapp.myPackages.ui.xml.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.firebaseapp.myPackages.ui.compose.navigation.ComposeNavGraph

class ComposeHostFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //## Beginning of compose
        return ComposeView(requireContext()).apply {
            setContent {
                ComposeNavGraph()
            }
        }
    }
}
