package kr.co.prnd.persistbottomsheetfragment

import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import com.google.android.material.bottomsheet.BottomSheetBehavior


class BottomSheetCallback(
    private val behavior: BottomSheetBehavior<out View>,
    private val rootView: View,
    private val containerView: View,
    private val collapseView: View,
    private val expandView: View,
) : BottomSheetBehavior.BottomSheetCallback() {

    private val context = rootView.context

    private val colorDrawable = ColorDrawable(ContextCompat.getColor(context, R.color.black_60))

    init {
        collapseView.alpha = 1f
        expandView.alpha = 0f
        expandView.isInvisible = true

        setupListener()
        rootView.isClickable = false
    }

    private fun setupListener() {
        rootView.setOnClickListener {
            if (behavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
        containerView.setOnClickListener {
            // no-op
        }
    }

    override fun onSlide(bottomSheet: View, slideOffset: Float) {
        setRootBackgroundColor(slideOffset)
        setCollapseVisible(slideOffset)
    }

    private fun setRootBackgroundColor(slideOffset: Float) {
        colorDrawable.alpha = (slideOffset * COLOR_ALPHA_RATIO).toInt()
        rootView.setBackgroundColor(colorDrawable.color)
    }

    private fun setCollapseVisible(slideOffset: Float) {
        val visibleOffset = (1 - slideOffset).coerceIn(0f, 1f)
        collapseView.alpha = visibleOffset
        expandView.alpha = slideOffset
    }

    override fun onStateChanged(bottomSheet: View, @BottomSheetBehavior.State newState: Int) {
        rootView.isClickable = newState == BottomSheetBehavior.STATE_EXPANDED

        val isCollapseInvisible = newState == BottomSheetBehavior.STATE_EXPANDED
        val isExpandInvisible = newState == BottomSheetBehavior.STATE_COLLAPSED

        collapseView.isInvisible = isCollapseInvisible
        expandView.isInvisible = isExpandInvisible
    }

    companion object {
        private const val COLOR_ALPHA_RATIO = 255f
    }
}
