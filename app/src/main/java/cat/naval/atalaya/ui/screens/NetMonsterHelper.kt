package cat.naval.atalaya.ui.screens

import cz.mroczis.netmonster.core.model.cell.CellCdma
import cz.mroczis.netmonster.core.model.cell.CellGsm
import cz.mroczis.netmonster.core.model.cell.CellLte
import cz.mroczis.netmonster.core.model.cell.CellNr
import cz.mroczis.netmonster.core.model.cell.CellTdscdma
import cz.mroczis.netmonster.core.model.cell.CellWcdma
import cz.mroczis.netmonster.core.model.cell.ICell

class NetMonsterHelper {
    companion object {
        fun decodeTechnology(first: ICell): String {
            return when (first) {
                is CellGsm -> "2G"
                is CellWcdma -> "3G"
                is CellLte -> "4G"
                is CellNr -> "5G"
                is CellCdma -> "CDMA"
                is CellTdscdma -> "3G"
                else -> "N/A"
            }
        }
    }

}
