package star.sky.voyager.utils.voyager

import cn.fkj233.ui.activity.data.DataBinding
import cn.fkj233.ui.activity.view.SeekBarWithTextV
import cn.fkj233.ui.activity.view.TextV
import star.sky.voyager.R

object TabletSeekBar {
    fun cn.fkj233.ui.activity.data.BasePage.createTextWithSeekBar(
        isTablet: Boolean,
        key: String,
        tabletRange: Triple<Int, Int, Int>,
        otherRange: Triple<Int, Int, Int>,
        dataBinding: DataBinding.Binding
    ) {
        val sizeData = if (isTablet) tabletRange else otherRange

        val textV = TextV(textId = R.string.font_size)
        val seekBarWithTextV =
            SeekBarWithTextV(key, sizeData.first, sizeData.second, sizeData.third)
        val dataBindingRecv = dataBinding.getRecv(1)

        TextWithSeekBar(textV, seekBarWithTextV, dataBindingRecv)
    }

}