package com.example.androidtestapp.fragments

import android.graphics.Color
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.example.androidtestapp.R
import com.example.androidtestapp.helpers.DataProvider
import com.example.androidtestapp.helpers.Router
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_material.view.*
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent
import kotlin.random.Random

class FragmentMaterial : Fragment(), KoinComponent {
    private lateinit var editTextRandom: TextInputEditText
    private lateinit var textViewWins: TextInputEditText
    private lateinit var textViewLosses: TextInputEditText

    private val colorWinner: Int = Color.parseColor("#33AD2F")
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
        editTextRandom = view.findViewById(R.id.edit_text_random_result)
        textViewWins = view.findViewById(R.id.textViewWinsCount)
        textViewLosses = view.findViewById(R.id.textViewLossesCount)

        view.buttonRandom.setOnClickListener { onClickRenerateRandom(view) }
        textViewWins.setText(countWins.toString(), TextView.BufferType.EDITABLE)
        textViewLosses.setText(countLosses.toString(), TextView.BufferType.EDITABLE)
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

        editTextRandom.setTextColor(colorRes)
        editTextRandom.setText(rnd.toString(), TextView.BufferType.EDITABLE)
        textViewWins.setText(countWins.toString(), TextView.BufferType.EDITABLE)
        textViewLosses.setText(countLosses.toString(), TextView.BufferType.EDITABLE)
    }
}