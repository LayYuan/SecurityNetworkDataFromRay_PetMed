/*
 * Copyright (c) 2018 Razeware LLC
 *
 * During the process, you’ll learn the following best practices:
 * 1.Using HTTPS for network calls.
 * 2.Trusting a connection with certificate pinning.
 * 3.Verifying the integrity of transmitted data.
 *
 * HTTP data is transmitted in the clear. This means all the medical information about Pom the
 * Pomeranian, for example, was retrieved unprotected for anyone to view. Many popular tools are
 * available to monitor HTTP traffic. Some examples are Wireshark, mitmproxy, and Charles.
 * HTTPS uses TLS — or Transport Layer Security — to encrypt data in transit.
 *
 *
 *
 *
 *
 */

package com.raywenderlich.android.petmed

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.util.*


class MainActivity : AppCompatActivity(), PetRequester.RequestManagerResponse {

  private val petList: ArrayList<Pet> = ArrayList()
  private lateinit var petRequester: PetRequester
  private lateinit var linearLayoutManager: LinearLayoutManager
  private lateinit var adapter: RecyclerAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    linearLayoutManager = LinearLayoutManager(this)
    recyclerView.layoutManager = linearLayoutManager

    adapter = RecyclerAdapter(petList)
    recyclerView.adapter = adapter

    petRequester = PetRequester(this)
  }

  override fun onStart() {
    super.onStart()
    if (petList.size == 0) {
      retrievePets()
    }
  }

  private fun retrievePets() {
    try {
      petRequester.retrievePets()
    } catch (e: IOException) {
      e.printStackTrace()
    }

  }

  override fun receivedNewPets(results: PetResults) {
    for (pet in results.items) {
      petList.add(pet)
    }
    adapter.notifyItemInserted(petList.size)
  }
}
