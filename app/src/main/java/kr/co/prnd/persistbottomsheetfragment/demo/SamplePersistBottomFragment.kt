package kr.co.prnd.persistbottomsheetfragment.demo

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import kr.co.prnd.persistbottomsheetfragment.PersistBottomSheetFragment
import kr.co.prnd.persistbottomsheetfragment.demo.databinding.LayoutCollapseBinding
import kr.co.prnd.persistbottomsheetfragment.demo.databinding.LayoutExpandBinding

class SamplePersistBottomFragment :
    PersistBottomSheetFragment<LayoutCollapseBinding, LayoutExpandBinding>(
        R.layout.layout_collapse,
        R.layout.layout_expand
    ) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collapseBinding.viewSelect.setOnClickListener {
            expand()
        }
    }

    companion object {

        private val TAG = SamplePersistBottomFragment::class.simpleName

        fun show(
            fragmentManager: FragmentManager,
            @IdRes containerViewId: Int,
        ): SamplePersistBottomFragment =
            fragmentManager.findFragmentByTag(TAG) as? SamplePersistBottomFragment
                ?: SamplePersistBottomFragment().apply {
                    fragmentManager.beginTransaction()
                        .replace(containerViewId, this, TAG)
                        .commitAllowingStateLoss()
                }
    }

}
