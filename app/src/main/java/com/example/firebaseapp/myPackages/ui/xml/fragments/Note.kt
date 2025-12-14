package com.example.firebaseapp.myPackages.ui.xml.fragments

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.firebaseapp.R
import com.example.firebaseapp.databinding.FragmentDisplayNoteBinding
import com.example.firebaseapp.myPackages.MainActivity
import com.example.firebaseapp.myPackages.data.remote.firebase.database.repo.DataRepo
import com.example.firebaseapp.myPackages.data.remote.firebase.database.repoImp.DataRepoImp
import com.example.firebaseapp.myPackages.ui.compose.components.AlertDialog

class Note : Fragment(R.layout.fragment_display_note) {
    private lateinit var binding: FragmentDisplayNoteBinding
    private lateinit var navController: NavController
    private var fontSize = 15f
    private var dataRepo: DataRepo? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDisplayNoteBinding.bind(view)
        navController = Navigation.findNavController(view)

        val activity = activity as MainActivity
        activity.supportActionBar?.hide()

        dataRepo = DataRepoImp()

        var noteBody = arguments?.getString("note")
        var noteTitle = arguments?.getString("title")
        var noteId = arguments?.getString("noteId")

        binding.notetext.setText(noteBody)
        binding.title.setText(noteTitle)

        //to increase font size
        binding.zoomIn.setOnClickListener()
        {
            if (fontSize < 40f) {
                fontSize += 2f
                binding.notetext.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
            }
        }
        //to decrease font size
        binding.zoomOut.setOnClickListener()
        {
            if (fontSize >= 15f) {
                fontSize -= 2f
                binding.notetext.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
            }
        }
        //to set image
        var images = ArrayList<Int>()
        images.add(R.drawable.image2)
        images.add(R.drawable.image3)
        images.add(R.drawable.image4)
        images.add(R.drawable.image5)
        val pos = (0..3).random()
        Glide.with(requireContext()).load(images[pos]).into(binding.image)

        //share note with others
        binding.composeView.setContent {
            var showWarning by remember { mutableStateOf(false) }
            var isLoading by remember { mutableStateOf(false) }

            if(showWarning){
                AlertDialog(
                    isLoading = isLoading,
                    title = getString(R.string.are_you_sure),
                    body = getString(R.string.you_want_to_share_this_note_with_all_users),
                    confirmText = getString(R.string.share),
                    onConfirmClick = {
                        isLoading = true
                        dataRepo?.shareNote(
                            noteId = noteId?:"",
                            onSuccess = {alreadyShared->
                                isLoading = false
                                showWarning = false
                                if(!alreadyShared)
                                 Toast.makeText(requireContext(),
                                    getString(R.string.note_shared_successfully),
                                    Toast.LENGTH_LONG).show()
                                else
                                    Toast.makeText(requireContext(),
                                        getString(R.string.this_note_already_shared),
                                        Toast.LENGTH_LONG).show()
                            },
                            onFailure = {
                                isLoading = false
                                showWarning = false
                                Toast.makeText(requireContext(),
                                    getString(R.string.failed_to_share_note),
                                    Toast.LENGTH_LONG).show()
                            },
                        )
                    },
                    onCancelClick = {showWarning = false}
                )
            }
            binding.shareNote.setOnClickListener {
                showWarning = true
            }
        }

    }

}