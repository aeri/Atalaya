<br />
<div align="center">
    <img src="doc/atalaya_banner.png" alt="Banner logo" width="700">
  </a>
<br>
</div>

# About

Atalaya is a cell tower monitor which you can get detailed information about the antenna to which your device is connected, such as its identifiers, technology type, bands in use, and the signal quality with the most representative values.

- It is able to interpret GSM, CDMA, WCDMA, TD-SCDMA, LTE, LTE+, 5G NSA and 5G SA technologies.

- Improve the mobile experience by finding the exact location of the antenna through its identifiers or improving the signal quality and reception by monitoring the reception values.

- Atalaya respects your freedom and privacy so it is and always will be open source. All data will be processed on the device itself without requiring an internet connection.
<a href="https://f-droid.org/es/packages/cat.naval.atalaya">
    <img src="https://fdroid.gitlab.io/artwork/badge/get-it-on.png"
    alt="Get it on F-Droid"
    height="80">
</a>

## Core

Atalaya uses the [NetMonster Core](https://github.com/mroczis/netmonster-core) library to apply post-processing to the cell data, as well as obtain more information than what is usually collected with native Android APIs such as `TelephonyManager`.

This library although retrieves more information this may not be accurate and has some limitations such as inferring the presence of Carrier Aggregation in LTE-A, as well as 5G SA and NSA detection.

There are many types of networks and many types of devices, so it is possible to see discrepancies and different behaviors between them.

Together, we can build an open tool for the community, so all participation and collaboration is highly desirable to incorporate features, improvements and thus have a good tool with which to perform analysis of mobile networks.


## License
Code is released under the GNU General Public License v3.0.
