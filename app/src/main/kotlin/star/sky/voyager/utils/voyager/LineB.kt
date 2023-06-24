package star.sky.voyager.utils.voyager

import cn.fkj233.ui.activity.data.DataBinding
import cn.fkj233.ui.activity.view.LineV

object LineB {
    fun cn.fkj233.ui.activity.data.BasePage.lineB(dataBindingRecv: DataBinding.Binding.Recv?): LineV {
        val lineV = LineV(dataBindingRecv)
        itemList.add(lineV)
        return lineV
    }
}