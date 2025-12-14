package com.example.firebaseapp.myPackages.ui.xml.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.firebaseapp.R
import com.example.firebaseapp.databinding.HomeFragmentBinding
import com.example.firebaseapp.myPackages.data.local.UserData.Companion.getUser
import com.example.firebaseapp.myPackages.data.models.NoteContent
import com.example.firebaseapp.myPackages.data.models.User
import com.example.firebaseapp.myPackages.data.remote.firebase.database.repo.DataRepo
import com.example.firebaseapp.myPackages.data.remote.firebase.database.repoImp.DataRepoImp
import com.example.firebaseapp.myPackages.ui.compose.components.AlertDialog
import com.example.firebaseapp.myPackages.ui.compose.components.HomeTopBar
import com.example.firebaseapp.myPackages.ui.compose.components.NoteBottomSheet
import com.example.firebaseapp.myPackages.ui.xml.adapters.NoteAdapter
import com.example.firebaseapp.myPackages.utils.getCurrentDate
import com.example.firebaseapp.myPackages.utils.showError
import com.google.firebase.auth.FirebaseAuth

class Home : Fragment(R.layout.home_fragment) {

    private lateinit var binding: HomeFragmentBinding
    private lateinit var navController: NavController
    lateinit var adapter: NoteAdapter
    var dataRepo: DataRepo? = null
    var list = ArrayList<NoteContent>()
    var selectedNote: NoteContent? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = HomeFragmentBinding.bind(view)
        navController = Navigation.findNavController(view)

        dataRepo = DataRepoImp()

        //## Top Bar----------------------------------------------
        binding.composeTopBar.setContent {
            HomeTopBar(
                onLogoutClick = {
                    FirebaseAuth.getInstance().signOut()
                    navController.navigate(R.id.action_dealingWithNote_to_noteFace)
                },
                onNotificationClick = {
                    navController.navigate(R.id.action_dealingWithNote_to_composeHostFragment)
                }
            )

        }
        //## Display Notes-----------------------------------------
        binding.progressBar.isVisible = true

        binding.composeView.setContent {
            var showWarning by remember { mutableStateOf(false) }
            var isLoading by remember { mutableStateOf(false) }
            var writeNote by remember { mutableStateOf(false) }
            var noteContent by remember { mutableStateOf("") }
            var title by remember { mutableStateOf("") }
            var status by remember { mutableStateOf("") }

            //## Delete Confirmation--------------------------------
            if (showWarning) {
                AlertDialog(
                    isLoading = isLoading,
                    title = getString(R.string.are_you_sure),
                    body = stringResource(R.string.you_want_to_delete_this_note),
                    confirmText = getString(R.string.delete),
                    onConfirmClick = {
                        isLoading = true
                        dataRepo?.deleteNote(
                            noteId = selectedNote?.id?:"",
                            onSuccess = {
                                showWarning = false
                                isLoading = false
                            },
                            onFailure = {
                                showWarning = false
                                isLoading = false
                                showError(requireContext())
                            }
                        )
                    },
                    onCancelClick = { showWarning = false }
                )
            }
            //## Get & Display---------------------------------------
            dataRepo?.getUserNotes(
                onSuccess = { notes ->
                    if (!isAdded) return@getUserNotes
                    adapter = NoteAdapter(
                        context = requireContext(),
                        notes = notes,
                        onUpdateClick = {
                            title = it.title?:""
                            noteContent = it.note?:""
                            status = getString(R.string.update)
                            selectedNote = it
                            writeNote = true
                        },
                        onDeleteClick = {
                            selectedNote = it
                            showWarning = true
                        },
                        onItemClick = {
                            val bundle = Bundle().apply {
                                putString("note", it.note)
                                putString("title", it.title)
                                putString("noteId", it.id)
                            }
                            navController.navigate(
                                R.id.action_dealingWithNote_to_displayNote2,
                                bundle
                            )

                        }
                    )
                    binding.listview.adapter = adapter
                    binding.progressBar.isVisible = false

                },
                onFailure = {
                    if (!isAdded) return@getUserNotes
                    binding.progressBar.isVisible = false
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.failed_to_load_notes),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )

            //## Add Note || Update Note--------------------------------
            if (writeNote) {
                NoteBottomSheet(
                    btnName = status,
                    onClick = {
                        isLoading = true
                        when(status){
                            "Add"->{
                                dataRepo?.addNote(
                                    NoteContent(
                                        title = title,
                                        note = noteContent,
                                        date = getCurrentDate(),
                                        user = getUser() ?: User(),
                                    ),
                                    onSuccess = {
                                        isLoading = false
                                        writeNote = false
                                        noteContent = ""
                                        title = ""
                                    },
                                    onFailure = {
                                        isLoading = false
                                        writeNote = false
                                        noteContent = ""
                                        title = ""
                                        showError(requireContext())
                                    }
                                )
                            }
                            "Update"->{
                                dataRepo?.updateNote(
                                    NoteContent(
                                        id = selectedNote?.id?:"",
                                        title = title,
                                        note = noteContent,
                                        date = selectedNote?.date?:"",
                                        user = getUser() ?: User(),
                                    ),
                                    onSuccess = {
                                        isLoading = false
                                        writeNote = false
                                        noteContent = ""
                                        title = ""
                                    },
                                    onFailure = {
                                        isLoading = false
                                        writeNote = false
                                        noteContent = ""
                                        title = ""
                                        showError(requireContext())
                                    }
                                )
                            }
                        }
                    },
                    content = noteContent,
                    title = title,
                    onDismiss = { writeNote = false },
                    isLoading = isLoading,
                    onContentChange = {
                        noteContent = it
                    },
                    onTitleChange = {
                        title = it
                    }
                )
            }
            binding.addNewNote.setOnClickListener() {
                status = getString(R.string.add)
                writeNote = true
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        dataRepo?.cancelRequest()
    }

}
