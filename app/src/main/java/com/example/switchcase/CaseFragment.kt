package com.example.switchcase

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import com.example.switchcase.databinding.FragmentCaseBinding

class CaseFragment : Fragment() {
    private lateinit var binding: FragmentCaseBinding
    private lateinit var editText: EditText
    private var fragID = 0

    private fun toUpperCase(text: String): String {
        return text.uppercase()
    }

    private fun toLowerCase(text: String): String {
        return text.lowercase()
    }

    private fun toTitleCase(text: String): String {
        val words = text.split(" ")
        return words.joinToString(" ") { word -> word.replaceFirstChar { it.uppercase() } }
    }

    private fun toCamelCase(text: String): String {
        val title = toTitleCase(text).split(" ").joinToString("")
        return title.replaceFirstChar { it.lowercase() }
    }

    private fun toPascalCase(text: String): String {
        return toTitleCase(text).split(" ").joinToString("")
    }

    private fun toSnakeCase(text: String): String {
        val words = text.split(" ")
        return words.joinToString("_") { word -> word.lowercase() }
    }


    private fun toKebabCase(text: String): String {
        val words = text.split(" ")
        return words.joinToString("-") { word -> word.lowercase() }
    }


    private fun toDotCase(text: String): String {
        val words = text.split(" ")
        return words.joinToString(".") { word -> word.lowercase() }
    }

    private fun toReverseCase(text: String): String {
        val lines = text.split("\n")
        var revTing = ""
        for (line in lines) {
            revTing += "\n" + line.reversed()
        }
        return revTing.trim()
    }


    private fun toUglyCase(text: String): String {
        val words = text.split(" ")
        return words.joinToString(" ") { word ->
            var newWord = ""
            var counter = 0
            for (i in word.indices) {
                if (i == 0) {
                    newWord = word[i].lowercase()
                    if (word[i].uppercase().toCharArray()[0] == 'L')
                        newWord = word[i].uppercase()
                    if (word[i].isLetter())
                        counter++
                    continue
                }

                if (word[i].isLetter()) {
                    if (word[i].lowercase().toCharArray()[0] == 'i') {
                        newWord += word[i].lowercase()
                        continue
                    } else if (word[i].uppercase().toCharArray()[0] == 'L') {
                        newWord += word[i].uppercase()
                        continue
                    }
                    newWord += if (counter % 2 != 0) word[i].uppercase() else word[i].lowercase()
                    counter++
                } else newWord += word[i]

            }
            newWord
        }
    }


    private fun toGoneCase(text: String): String {
        val lettersMap: HashMap<Char, Char> = hashMapOf(
            'a' to '4',
            'b' to 'ß',
            'c' to '¢',
            'd' to 'D',
            'e' to '3',
            'f' to 'ƒ',
            'g' to 'G',
            'h' to 'H',
            'i' to '1',
            'j' to 'J',
            'k' to 'к',
            'l' to 'L',
            'm' to 'M',
            'n' to 'И',
            'o' to '0',
            'p' to 'ℙ',
            'q' to 'ℚ',
            'r' to 'Я',
            's' to '$',
            't' to 'T',
            'u' to 'µ',
            'v' to 'V',
            'w' to 'Ш',
            'x' to 'Ж',
            'y' to '¥',
            'z' to '2',
        )
        var goneCase = ""
        for (t in text.lowercase()) goneCase += if (t.isLetter()) lettersMap[t] else t
        return goneCase
    }

    private fun toClapCase(text: String): String {
        val lines = text.split("\n")
        var clapTing = ""
        for (line in lines) {
            val words = line.split(" ")
            clapTing += "\n\uD83D\uDC4F" + words.joinToString("\uD83D\uDC4F") + "\uD83D\uDC4F"
        }
        return clapTing.trim()
    }

    fun switchCase(textPara: String) {
        var text = textPara.trim()
        if (text == "") text = context?.getString(R.string.enter_text).toString()
        when (fragID) {
            0 -> binding.textView.text = toUpperCase(text)
            1 -> binding.textView.text = toLowerCase(text)
            2 -> binding.textView.text = toTitleCase(text)
            3 -> binding.textView.text = toCamelCase(text)
            4 -> binding.textView.text = toPascalCase(text)
            5 -> binding.textView.text = toSnakeCase(text)
            6 -> binding.textView.text = toKebabCase(text)
            7 -> binding.textView.text = toDotCase(text)
            8 -> binding.textView.text = toReverseCase(text)
            9 -> binding.textView.text = toUglyCase(text)
            10 -> binding.textView.text = toGoneCase(text)
            11 -> binding.textView.text = toClapCase(text)
            else -> binding.textView.text = text
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_case,
            container,
            false
        )
        fragID = requireArguments().getString("frag_id")?.toInt()!!

        editText = requireActivity().findViewById(R.id.editTextTextMultiLine)
        switchCase(editText.text.toString())
        editText.doOnTextChanged { text, _, _, _ ->
            switchCase(text.toString())
        }

//        Click to copy
        binding.textView.setOnClickListener {
            Toast.makeText(
                context,
                "Text Copied!",
                Toast.LENGTH_LONG
            ).show()
            val clipboard: ClipboardManager? =
                context?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
            val clip = ClipData.newPlainText(
                "EDITED CASE",
                binding.textView.text
            )
            clipboard?.setPrimaryClip(clip)
        }

//        Long Press to share
        binding.textView.setOnLongClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, binding.textView.text.toString())
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
            true
        }

        return binding.root
    }
}