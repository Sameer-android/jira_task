package com.example.jira.fragments

import HomeFragment
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.jira.MainActivity
import com.example.jira.R
import com.example.jira.databinding.FragmentLogInContinueBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LogInContinueFragment : Fragment() {
    private lateinit var binding: FragmentLogInContinueBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.statusBarColor = Color.parseColor("#0052cc")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogInContinueBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(
            getString(R.string.default_web_client_id)
        ).requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        onGoogleClick()
        onTouchListenerRootLayout()
        continueButton()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).showBottomBar(false) // Hides the bottom bar
    }

    private fun continueButton() {
        binding.continueTv.setOnClickListener {
            Toast.makeText(requireContext(), "Please Google Authentication", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main, ShowDataFragment())
                .commit()
        } else {
            binding.googleLayout.setOnClickListener {
                val signInClient = googleSignInClient.signInIntent
                launcher.launch(signInClient)
            }
        }
    }

    private fun onGoogleClick() {
        binding.cantLogIn.setOnClickListener {
            val cantUrl =
                "https://support.atlassian.com/atlassian-account/docs/troubleshoot-login-issues-with-your-atlassian-account/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(cantUrl))
            startActivity(intent)
        }
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                if (task.isSuccessful) {
                    val account: GoogleSignInAccount? = task.result
                    val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                    auth.signInWithCredential(credential).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val isNewUser = it.result.additionalUserInfo?.isNewUser ?: false
                            if (isNewUser) {
                                // First-time user
                                requireActivity().supportFragmentManager.beginTransaction()
                                    .replace(R.id.main, HomeFragment())
                                    .addToBackStack(null)
                                    .commit()
                            } else {
                                // Existing user
                                requireActivity().supportFragmentManager.beginTransaction()
                                    .replace(R.id.main, ShowDataFragment())
                                    .addToBackStack(null)
                                    .commit()
                            }
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Authentication Failed",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

    private fun onTouchListenerRootLayout() {
        binding.logInMain.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val imm =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view?.windowToken, 0)
            }
            false
        }
    }
}