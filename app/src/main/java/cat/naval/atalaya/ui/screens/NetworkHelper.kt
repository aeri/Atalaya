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
                else -> ""
            }
        }

        fun getNetworkType(technology: Int): String {
            when (technology) {
                NetworkType.GPRS -> return "GPRS"
                NetworkType.EDGE -> return "EDGE"
                NetworkType.GSM -> return "GSM"
                NetworkType.CDMA -> return "CDMA"
                NetworkType.EVDO_0 -> return "EVDO rev. 0"
                NetworkType.EVDO_A -> return "EVDO rev. A"
                NetworkType.EVDO_B -> return "EVDO rev. B"
                NetworkType.ONExRTT -> return "1xRTT"
                NetworkType.EHRPD -> return "eHRPD"
                NetworkType.IDEN -> return "iDen"
                NetworkType.UMTS -> return "UMTS"
                NetworkType.HSPA -> return "HSPA"
                NetworkType.HSPAP -> return "HSPA+"
                NetworkType.HSDPA -> return "HSDPA"
                NetworkType.HSUPA -> return "HSUPA"
                NetworkType.TD_SCDMA -> return "TD-SCDMA"
                NetworkType.LTE -> return "LTE"
                NetworkType.IWLAN -> return "IWLAN"
                NetworkType.NR -> return "SA"
                NetworkType.LTE_CA -> return "LTE-A"
                else ->
                    return "LTE-A";
            }

        }
        fun getBandText(cell: ICell): String {
            return when (cell) {
                is CellNr -> "n${cell.band?.number}"
                else -> "B${cell.band?.number}"
            }
        }
    }

}
