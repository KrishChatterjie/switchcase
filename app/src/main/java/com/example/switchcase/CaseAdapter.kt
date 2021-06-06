package com.example.switchcase

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.switchcase.utils.Constants.NUM_CASES


class CaseAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = NUM_CASES

    override fun createFragment(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putString("frag_id", position.toString())
        val fragment = CaseFragment()
        fragment.arguments = bundle
        return fragment
    }
}