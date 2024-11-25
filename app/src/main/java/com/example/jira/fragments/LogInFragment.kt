package com.example.jira.fragments

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.jira.MainActivity
import com.example.jira.R
import com.example.jira.databinding.FragmentLogInBinding
import com.example.jira.fragments.ViewPagerAdapter.ViewPagerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth

class LogInFragment : Fragment() {
    private lateinit var binding: FragmentLogInBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var imageList: List<Int>
    private lateinit var textList: List<String>
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        requireActivity().window.statusBarColor = Color.parseColor("#0062f7")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLogInBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        imageListAdd()
        startIconAndTextAnimation()
        replaceFragment()
        underLineText()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).showBottomBar(false)
    }
    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main, ShowDataFragment())
                .commit()
        }
    }

    private fun underLineText() {
        val text = "Can't log in or sign up?"
        val spannableString = SpannableString(text)
        val cantLogStart = text.indexOf("Can't log in or sign up?")
        val cantLogEnd = cantLogStart + "Can't log in or sign up?".length

        val cantLogClickable = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val dialog = BottomSheetDialog(requireActivity())
                val view = layoutInflater.inflate(R.layout.bottom_sheet_diolog, null)
                dialog.setCancelable(true)
                dialog.setContentView(view)
                dialog.show()

                val loginIntent = view.findViewById<TextView>(R.id.loginHelp)
                loginIntent.setOnClickListener {
                    val loginUrl =
                        "https://support.atlassian.com/atlassian-account/docs/troubleshoot-login-issues-with-your-atlassian-account/"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(loginUrl))
                    startActivity(intent)
                }
            }
        }
        val textColor = Color.parseColor("#d4e3f7")
        spannableString.setSpan(
            cantLogClickable,
            cantLogStart,
            cantLogEnd,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            UnderlineSpan(),
            cantLogStart,
            cantLogEnd,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            ForegroundColorSpan(textColor),
            cantLogStart,
            cantLogEnd,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.cantTv.text = spannableString
        binding.cantTv.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun replaceFragment() {
        binding.logIN.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main, LogInContinueFragment()).addToBackStack(null).commit()
        }
        binding.signIN.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main, LogInContinueFragment()).addToBackStack(null).commit()
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val text = "By signing up, you agree to the\n User Notice and Privacy Policy."
        val spannableString = SpannableString(text)

        val userNoticeStart = text.indexOf("User Notice")
        val userNoticeEnd = userNoticeStart + "User Notice".length
        val privacyPolicyStart = text.indexOf("Privacy Policy")
        val privacyPolicyEnd = privacyPolicyStart + "Privacy Policy".length

        val userNoticeUrl = "https://www.atlassian.com/legal/user-notice/mobile"
        val privacyPolicyUrl = "https://www.atlassian.com/legal/privacy-policy/mobile"

        val userNoticeClickable = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(userNoticeUrl))
                startActivity(intent)
            }
        }

        val privacyPolicyClickable = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(privacyPolicyUrl))
                startActivity(intent)
            }
        }

        val textColor = Color.parseColor("#d4e3f7")
        spannableString.setSpan(
            userNoticeClickable,
            userNoticeStart,
            userNoticeEnd,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            UnderlineSpan(),
            userNoticeStart,
            userNoticeEnd,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            ForegroundColorSpan(textColor),
            userNoticeStart,
            userNoticeEnd,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableString.setSpan(
            privacyPolicyClickable,
            privacyPolicyStart,
            privacyPolicyEnd,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            UnderlineSpan(),
            privacyPolicyStart,
            privacyPolicyEnd,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            ForegroundColorSpan(textColor),
            privacyPolicyStart,
            privacyPolicyEnd,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )


        binding.belowTv.text = spannableString
        binding.belowTv.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun startIconAndTextAnimation() {
        ObjectAnimator.ofFloat(binding.titleText, "translationY", 300f, 0f).apply {
            duration = 1000
            start()
        }
    }

    private fun imageListAdd() {
        imageList = listOf(
            R.drawable.first,
            R.drawable.second,
            R.drawable.third,
            R.drawable.fourth,
            R.drawable.fifth,
            R.drawable.six
        )
        textList = listOf(
            "The #1 software development tool used\nby agile teams.",
            "Track tasks. project status. and time\n spent on work.",
            "Swipe and drag to track.prioritize.and\n filter on the go.",
            "Get real-time notifications about activity\n across all yours sites.",
            "View your Jira Service Management\n requests.incidents.changes.and more.",
            "Track team goals(SLAs)."
        )

        viewPagerAdapter = ViewPagerAdapter(requireActivity(), imageList, textList)
        binding.idViewPager.adapter = viewPagerAdapter

        binding.idDotsIndicator.setViewPager(binding.idViewPager)

    }
}