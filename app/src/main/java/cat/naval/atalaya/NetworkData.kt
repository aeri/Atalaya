package cat.naval.atalaya

import cz.mroczis.netmonster.core.db.model.NetworkType
import cz.mroczis.netmonster.core.model.cell.ICell
import cz.mroczis.netmonster.core.model.signal.SignalCdma
import cz.mroczis.netmonster.core.model.signal.SignalGsm
import cz.mroczis.netmonster.core.model.signal.SignalLte
import cz.mroczis.netmonster.core.model.signal.SignalNr
import cz.mroczis.netmonster.core.model.signal.SignalTdscdma
import cz.mroczis.netmonster.core.model.signal.SignalWcdma



data class NetworkData (

    var carrierName: String = "",

    var cells: List<ICell> = listOf(),

    var networkType: Int = NetworkType.UNKNOWN,

    val gsmSignal: MutableList<SignalGsm> = mutableListOf<SignalGsm>(),
    val lteSignal: MutableList<SignalLte> = mutableListOf<SignalLte>(),
    val wcdmaSignal: MutableList<SignalWcdma> = mutableListOf<SignalWcdma>(),
    val cdmaSignal: MutableList<SignalCdma> = mutableListOf<SignalCdma>(),
    val nrSignal: MutableList<SignalNr> = mutableListOf<SignalNr>(),
    val tdscdmaSignal: MutableList<SignalTdscdma> = mutableListOf<SignalTdscdma>(),


    )