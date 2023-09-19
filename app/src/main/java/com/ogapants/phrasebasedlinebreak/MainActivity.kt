package com.ogapants.phrasebasedlinebreak

import android.content.Context
import android.graphics.text.LineBreakConfig
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

class PhraseBasedLineBreakTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AppCompatTextView(context, attrs, defStyleAttr) {

    var enablePhraseBasedLineBreak = true // fixme: from xml

    private var firstMeasuredHeight = 0
    private var onMeasureCount = 0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        Log.d("PhraseBasedLineBreakTextView", "onMeasure: ****34__" + log())
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.d("PhraseBasedLineBreakTextView", "onMeasure: ****44__" + log())

        if (firstMeasuredHeight == 0) {
            Log.d("PhraseBasedLineBreakTextView", "onMeasure: ****58__" + log())
            firstMeasuredHeight = measuredHeight
            if (enablePhraseBasedLineBreak) {
                Log.d("PhraseBasedLineBreakTextView", "onMeasure: ****62__" + log())
                setLineBreakWordStyleCompat(true)
            }
        } else if (enablePhraseBasedLineBreak && firstMeasuredHeight < measuredHeight) {
            Log.d("PhraseBasedLineBreakTextView", "onMeasure: ****60__" + log())
            setLineBreakWordStyleCompat(false)
            measure(widthMeasureSpec, heightMeasureSpec)
        }

        onMeasureCount++
    }

    private fun setLineBreakWordStyleCompat(enable: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            lineBreakWordStyle = if (enable) {
                LineBreakConfig.LINE_BREAK_WORD_STYLE_PHRASE
            } else {
                LineBreakConfig.LINE_BREAK_WORD_STYLE_NONE
            }
        }
    }

    private fun log() =
        "hc:${hashCode()}, onMeasureCount:$onMeasureCount, lh:$lineHeight, fmh:$firstMeasuredHeight, mh:$measuredHeight"
}
