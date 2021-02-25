package kr.co.prnd.persistbottomsheetfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.doOnLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kr.co.prnd.persistbottomsheetfragment.databinding.FragmentPersistBottomSheetBinding

abstract class PersistBottomSheetFragment<CollapseBinding : ViewDataBinding, ExpandBinding : ViewDataBinding>(
    @LayoutRes private val collapseResId: Int,
    @LayoutRes private val expandResId: Int,
    private val heightType: HeightType = HeightType.WRAP,
) : Fragment() {

    private var _binding: FragmentPersistBottomSheetBinding? = null

    @Suppress("MemberVisibilityCanBePrivate")
    protected val binding: FragmentPersistBottomSheetBinding
        get() = _binding ?: throw IllegalAccessException("Can not access destroyed view")

    @Suppress("MemberVisibilityCanBePrivate")
    protected val bottomSheetBehavior by lazy { BottomSheetBehavior.from(binding.flContainer) }

    @Suppress("MemberVisibilityCanBePrivate")
    protected lateinit var collapseBinding: CollapseBinding

    @Suppress("MemberVisibilityCanBePrivate")
    protected lateinit var expandBinding: ExpandBinding

    @Suppress("MemberVisibilityCanBePrivate")
    val isExpanded: Boolean
        get() = when (bottomSheetBehavior.state) {
            BottomSheetBehavior.STATE_EXPANDED,
            BottomSheetBehavior.STATE_HALF_EXPANDED,
            BottomSheetBehavior.STATE_SETTLING,
            -> true
            else -> false
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentPersistBottomSheetBinding.inflate(inflater, container, false).apply {
            collapseBinding = DataBindingUtil.inflate(inflater,
                collapseResId,
                viewCollapseContainer,
                true)
            expandBinding = DataBindingUtil.inflate(inflater,
                expandResId,
                viewExpandContainer,
                true)

            viewContent.layoutParams.height = heightType.layoutParamHeight

        }

        return _binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBottomSheetBehavior()
        collapseBinding.init()
        expandBinding.init()
    }

    private fun ViewDataBinding.init() {
        lifecycleOwner = this@PersistBottomSheetFragment
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupBottomSheetBehavior() = with(bottomSheetBehavior) {
        val bottomSheetCallback =
            BottomSheetCallback(
                this,
                binding.root,
                binding.flContainer,
                binding.viewCollapseContainer,
                binding.viewExpandContainer,
            )
        addBottomSheetCallback(bottomSheetCallback)

        collapseBinding.root.doOnLayout {
            peekHeight = it.height
        }
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun expand() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun collapse() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    @Suppress("MemberVisibilityCanBePrivate")
    open fun handleBackKeyEvent() =
        when {
            !isAdded -> false
            childFragmentManager.backStackEntryCount > 0 -> {
                childFragmentManager.popBackStackImmediate()
                true
            }
            isExpanded -> {
                collapse()
                true
            }
            else -> false
        }

    fun changeHeightType(heightType: HeightType) {
        binding.viewContent.layoutParams.height = heightType.layoutParamHeight
    }

    enum class HeightType(val layoutParamHeight: Int) {
        WRAP(ViewGroup.LayoutParams.WRAP_CONTENT),
        MATCH(ViewGroup.LayoutParams.MATCH_PARENT)
    }
}
