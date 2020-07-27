package io.keepcoding.eh_ho

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun AppCompatActivity.isFirstTimeCreated(savedInstanceState: Bundle?): Boolean =
    savedInstanceState == null

fun ViewGroup.inflate(idLayout: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(this.context).inflate(idLayout, this, attachToRoot)
