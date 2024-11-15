import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.jira.R
import com.example.jira.databinding.FragmentHomeBinding
import com.example.jira.fragments.ShowDataFragment
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        addDataFireStore()
        onTouchListenerRootLayout()

        return binding.root
    }

    private fun addDataFireStore() {
        binding.submitBtn.setOnClickListener {
            val empName = binding.nameEdit.text.toString()
            val code = binding.codeEdit.text.toString()
            val role = binding.role.text.toString()

            if (empName.isEmpty() || code.isEmpty() || role.isEmpty()) {
                Toast.makeText(requireContext(), "Please Fill All The Details", Toast.LENGTH_LONG)
                    .show()
            } else {
                val db = Firebase.firestore
                val user = hashMapOf(
                    "name" to binding.nameEdit.text.toString(),
                    "code" to binding.codeEdit.text.toString(),
                    "role" to binding.role.text.toString(),
                )
                // Add a new document with a generated ID
                db.collection("users")
                    .add(user)
                    .addOnSuccessListener { documentReference ->
                        Toast.makeText(
                            requireContext(),
                            "DocumentSnapshot added with ID: ${documentReference.id}",
                            Toast.LENGTH_LONG
                        ).show()
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.main, ShowDataFragment()).commit()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(
                            requireContext(),
                            "Error: ${e.localizedMessage}",
                            Toast.LENGTH_LONG
                        ).show()
                        Log.d("fasofhaf", "${e.localizedMessage}")
                    }
            }
        }
    }

    private fun onTouchListenerRootLayout() {
        binding.homeMain.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val imm =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view?.windowToken, 0)
            }
            false
        }
    }
}
