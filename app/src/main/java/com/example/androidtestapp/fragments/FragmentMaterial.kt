package com.example.androidtestapp.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.example.androidtestapp.R
import kotlinx.android.synthetic.main.fragment_material.view.*
import org.koin.core.component.KoinComponent
import kotlin.random.Random

class FragmentMaterial : Fragment(), KoinComponent {
    private lateinit var editTextRandom:EditText
    private lateinit var textViewWins:TextView
    private lateinit var textViewLosses:TextView

    private val colorWinner: Int = Color.parseColor("#00FF00")
    private val colorLoser: Int = Color.parseColor("#FF0000")
    private var countLosses:Int = 0
    private var countWins:Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_material, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editTextRandom = view.findViewById(R.id.editTextRandom)
        textViewWins = view.findViewById(R.id.textViewWinnerCount)
        textViewLosses = view.findViewById(R.id.textViewLoserCount)

        view.buttonRandom.setOnClickListener { onClickRenerateRandom(view) }
        textViewWins.setText("Wins: $countWins", TextView.BufferType.EDITABLE)
        textViewLosses.setText("Losses: $countLosses", TextView.BufferType.EDITABLE)
    }

    fun onClickRenerateRandom(view: View) {
        var rnd = Random.nextInt(0, 100)
        var colorRes = colorLoser
        if (rnd > 60)
        {
            countWins++
            colorRes = colorWinner
        }
        else  countLosses++

        editTextRandom.setBackgroundColor(colorRes)
        editTextRandom.setText("Your result: $rnd", TextView.BufferType.EDITABLE)
        textViewWins.setText("Wins:\n$countWins", TextView.BufferType.EDITABLE)
        textViewLosses.setText("Losses:\n$countLosses", TextView.BufferType.EDITABLE)
    }
}