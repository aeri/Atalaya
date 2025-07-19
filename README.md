Atalaya is an app with which you can get detailed information about the cell phone antenna to which your device is connected, such as its identifiers, the type of technologies, bands in use, and the signal quality with the most representative values. You can also use it to improve the mobile experience by finding the exact location of the antenna through its identifiers or improving the signal quality and reception by monitoring the reception values.

It is able to interpret GSM, CDMA, WCDMA, TD-SCDMA, LTE, LTE+, 5G NSA and 5G SA technologies.

Atalaya respects your freedom and privacy so it is and always will be open source. All data will be processed on the device itself without requiring an active internet connection.

## Core

Atalaya uses the [NetMonster Core](https://github.com/mroczis/netmonster-core) library to apply post-processing to the data, as well as obtain more information than what is usually collected with native Android APIs such as TelephonyManager.

This library although retrieves more information this may not be accurate and has some limitations such as inferring the presence of Carrier Aggregation in LTE-A, as well as 5G SA and NSA detection.

There are many types of networks and many types of devices, so it is possible to see discrepancies and different behaviors between them, the goal of this project is to build an open tool for the community, focused on participation and collaboration in order to incorporate features and improvements in terms of detection of such technologies and thus have a good tool with which to perform analysis of mobile networks.


## License
Code is released under the GNU General Public License v3.0.
