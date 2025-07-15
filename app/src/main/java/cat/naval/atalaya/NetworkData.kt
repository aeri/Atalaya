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

    var isAirplaneEnabled: Boolean = false,


    var cells: List<ICell> = listOf(),

    var networkType: NetworkType? = null,

    var gsmSignal: List<SignalGsm> = emptyList(),
    var lteSignal: List<SignalLte> =  emptyList(),
    var wcdmaSignal: List<SignalWcdma> = emptyList(),
    var cdmaSignal: List<SignalCdma> =  emptyList(),
    var nrSignal: List<SignalNr> =  emptyList(),
    var tdscdmaSignal: List<SignalTdscdma> =  emptyList(),


    )