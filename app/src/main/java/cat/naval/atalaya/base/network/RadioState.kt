package cat.naval.atalaya.base.network

import cz.mroczis.netmonster.core.model.cell.ICell


data class RadioState(

    var isAirplaneEnabled: Boolean = false,

    var cells: List<ICell> = listOf(),

    var networks: List<NetworkData> = emptyList(),

    )
