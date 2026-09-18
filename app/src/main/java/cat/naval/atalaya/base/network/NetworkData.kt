package cat.naval.atalaya.base.network

import cz.mroczis.netmonster.core.db.model.NetworkType
import cz.mroczis.netmonster.core.model.cell.ICell
import cz.mroczis.netmonster.core.model.signal.SignalCdma
import cz.mroczis.netmonster.core.model.signal.SignalGsm
import cz.mroczis.netmonster.core.model.signal.SignalLte
import cz.mroczis.netmonster.core.model.signal.SignalNr
import cz.mroczis.netmonster.core.model.signal.SignalTdscdma
import cz.mroczis.netmonster.core.model.signal.SignalWcdma


data class NetworkData(

    val subscriptionId: Int = -1,

    val slotIndex: Int = -1,

    var displayName: String = "",

    var carrierName: String = "",

    var simCarrierName: String = "",

    var networkType: NetworkType? = null,

    var cell: ICell? = null,

    var gsmSignal: List<SignalGsm> = emptyList(),
    var lteSignal: List<SignalLte> = emptyList(),
    var wcdmaSignal: List<SignalWcdma> = emptyList(),
    var cdmaSignal: List<SignalCdma> = emptyList(),
    var nrSignal: List<SignalNr> = emptyList(),
    var tdscdmaSignal: List<SignalTdscdma> = emptyList(),

    )
