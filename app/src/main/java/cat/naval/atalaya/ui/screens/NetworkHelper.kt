package cat.naval.atalaya.ui.screens

import cz.mroczis.netmonster.core.db.model.NetworkType
import cz.mroczis.netmonster.core.model.cell.CellCdma
import cz.mroczis.netmonster.core.model.cell.CellGsm
import cz.mroczis.netmonster.core.model.cell.CellLte
import cz.mroczis.netmonster.core.model.cell.CellNr
import cz.mroczis.netmonster.core.model.cell.CellTdscdma
import cz.mroczis.netmonster.core.model.cell.CellWcdma
import cz.mroczis.netmonster.core.model.cell.ICell

class NetworkHelper {
    companion object {
        fun decodeTechnology(first: ICell?): String {
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

        fun getNetworkType(technology: Int): String = when (technology) {
            NetworkType.GPRS -> "GPRS"
            NetworkType.EDGE -> "EDGE"
            NetworkType.GSM -> "GSM"
            NetworkType.CDMA -> "CDMA"
            NetworkType.EVDO_0 -> "EVDO rev. 0"
            NetworkType.EVDO_A -> "EVDO rev. A"
            NetworkType.EVDO_B -> "EVDO rev. B"
            NetworkType.ONExRTT -> "1xRTT"
            NetworkType.EHRPD -> "eHRPD"
            NetworkType.IDEN -> "iDen"
            NetworkType.UMTS -> "UMTS"
            NetworkType.HSPA -> "HSPA"
            NetworkType.HSPAP -> "HSPA+"
            NetworkType.HSDPA -> "HSDPA"
            NetworkType.HSUPA -> "HSUPA"
            NetworkType.TD_SCDMA -> "TD-SCDMA"
            NetworkType.LTE -> "LTE"
            NetworkType.IWLAN -> "IWLAN"
            NetworkType.NR -> "SA"
            NetworkType.LTE_CA -> "LTE-A"
            else ->
                "LTE-A";
        }
    }

}
