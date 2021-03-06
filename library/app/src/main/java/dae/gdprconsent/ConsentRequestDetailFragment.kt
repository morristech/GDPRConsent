package dae.gdprconsent

import android.arch.lifecycle.ViewModelProviders
import android.content.ActivityNotFoundException
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dae.gdprconsent.databinding.FragmentGdprConsentBinding
import java.net.MalformedURLException
import java.net.URL

class ConsentRequestDetailFragment : Fragment() {

    lateinit var request: ConsentRequest
    lateinit var binding: FragmentGdprConsentBinding
    private lateinit var viewModel: ConsentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ConsentViewModel::class.java)

        request = arguments!!.getParcelable(ARG_CONSENT_REQUEST)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gdpr_consent, container, false)

        request.isSeen = true
        requestUpdated()

        binding.request = request
        binding.executePendingBindings()

        binding.consent.setOnClickListener {
            request.isConsented = !request.isConsented
            requestUpdated()
        }

        binding.moreInformationButton.setOnClickListener {
            try {
                val url = URL(request.moreInformation)

                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url.toURI().toString())
                startActivity(intent)
            } catch (e: MalformedURLException) {
                // No URL? No problem. It must just be standard text.
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                // This should be handled better. The device has no facility to display web pages.
            }
        }

        if (TextUtils.isEmpty(request.what)) {
            binding.whatCard.visibility = View.GONE
        }
        if (TextUtils.isEmpty(request.moreInformation)) {
            binding.moreInformationCard.visibility = View.GONE
        }
        if (TextUtils.isEmpty(request.whyNeeded)) {
            binding.whyNeededCard.visibility = View.GONE
        }

        binding.toTop.setOnClickListener {
            binding.contentScroll.smoothScrollTo(0, 0)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        binding.contentScroll.post {
            with(binding) {
                this.toTop.visibility = if (this.contentScroll.canScrollVertically(1) || this.contentScroll.canScrollVertically(-1)) View.VISIBLE else View.INVISIBLE
            }
        }
    }

    private fun requestUpdated() {
        request.save(requireContext())
        viewModel.updateConsentRequest(request)

        with(binding) {
            this.request = request
            this.executePendingBindings()
        }
    }

    companion object {
        const val ARG_CONSENT_REQUEST = "ConsentRequest"

        fun newInstance(request: ConsentRequest): ConsentRequestDetailFragment {
            val fragment = ConsentRequestDetailFragment()
            val bundle = Bundle()
            bundle.putParcelable(ARG_CONSENT_REQUEST, request)
            fragment.arguments = bundle
            return fragment
        }
    }
}
