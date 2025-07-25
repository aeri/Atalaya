package cat.naval.atalaya.base.network

data class MccMnc(
    val mcc: Int,
    val mnc: Int,
    val iso: String,
    val country: String,
    val countryCode: String,
    val network: String,
)