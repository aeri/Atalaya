package cat.naval.atalaya

import cz.mroczis.netmonster.core.db.model.NetworkType
import cz.mroczis.netmonster.core.model.cell.ICell


class NetworkData {

    var carrierName: String = ""

    var cells: List<ICell> = listOf()

    var networkType: Int = NetworkType.UNKNOWN


}