package cat.naval.atalaya.base.network

import cz.mroczis.netmonster.core.db.model.NetworkType
import cz.mroczis.netmonster.core.model.cell.CellCdma
import cz.mroczis.netmonster.core.model.cell.CellGsm
import cz.mroczis.netmonster.core.model.cell.CellLte
import cz.mroczis.netmonster.core.model.cell.CellNr
import cz.mroczis.netmonster.core.model.cell.CellTdscdma
import cz.mroczis.netmonster.core.model.cell.CellWcdma
import cz.mroczis.netmonster.core.model.cell.ICell
import cz.mroczis.netmonster.core.model.nr.NrNsaState.Connection

class NetworkHelper {
    companion object {
        fun getTechnology(networkType: NetworkType?, cell: ICell?): String {
            return when (networkType?.technology) {
                NetworkType.GPRS,
                NetworkType.EDGE,
                NetworkType.GSM,
                NetworkType.IDEN -> "2G"

                NetworkType.CDMA,
                NetworkType.EVDO_0,
                NetworkType.EVDO_A,
                NetworkType.EVDO_B,
                NetworkType.ONExRTT,
                NetworkType.EHRPD,
                NetworkType.UMTS,
                NetworkType.HSPA,
                NetworkType.HSPAP,
                NetworkType.HSDPA,
                NetworkType.HSUPA,
                NetworkType.TD_SCDMA,
                NetworkType.HSPA_DC -> "3G"

                NetworkType.LTE,
                NetworkType.LTE_CA ->"4G"

                NetworkType.LTE_NR, NetworkType.LTE_CA_NR -> {
                    return if (networkType is NetworkType.Nr.Nsa) {
                        "4G + 5G"
                    } else {
                        "4G"
                    }
                }

                NetworkType.NR -> "5G"

                else -> {
                    return when (cell) {
                        is CellGsm -> "2G"

                        is CellCdma,
                        is CellWcdma,
                        is CellTdscdma -> "3G"

                        is CellLte -> "4G"
                        is CellNr -> "5G"

                        else -> "?G"
                    }
                }
            }
        }

        fun getNetworkType(networkType: NetworkType?): String {
            when (networkType?.technology) {
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
                NetworkType.NR -> return "NR SA"
                NetworkType.LTE_CA -> return "LTE-A"
                NetworkType.HSPA_DC -> return "DC-HSPA"
                NetworkType.LTE_NR, NetworkType.LTE_CA_NR -> {
                    return if (networkType is NetworkType.Nr.Nsa) {
                        if (networkType.nrNsaState.connection == Connection.Connected ){
                            return "NR NSA"
                        }
                        else{
                            return "⚠ NR NSA"
                        }
                    } else {
                        "LTE-A"
                    }
                }

                else ->
                    return "?"
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
