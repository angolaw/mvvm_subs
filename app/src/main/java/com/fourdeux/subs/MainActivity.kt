package com.fourdeux.subs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.fourdeux.subs.data.repository.SubscriberRepository
import com.fourdeux.subs.databinding.ActivityMainBinding
import com.fourdeux.subs.infra.SubscriberDatabase
import com.fourdeux.subs.presentation.SubscriberViewModel
import com.fourdeux.subs.presentation.SubscriberViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var  binding: ActivityMainBinding
    private lateinit var  subscriberViewModel: SubscriberViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = SubscriberDatabase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)
        subscriberViewModel = ViewModelProvider(this, factory)[SubscriberViewModel::class.java]
        binding.myViewModel = subscriberViewModel
        binding.lifecycleOwner = this

        //setContentView(R.layout.activity_main)
    }
}