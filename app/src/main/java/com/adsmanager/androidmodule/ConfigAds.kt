package com.adsmanager.androidmodule

import com.adsmanager.core.NetworkAds


object ConfigAds {

    var primaryAds: NetworkAds = NetworkAds.ADMOB
    var secondaryAds: NetworkAds? = NetworkAds.ADMOB
    var tertiaryAds: NetworkAds? = NetworkAds.APPLOVIN_MAX

    var primaryOpenAdId = "123"
    var secondaryOpenAdId = "ca-app-pub-3940256099942544/3419835294XX"
    var tertiaryOpenAdId = "fcd4981c18e62771"
    var quaternaryOpenAdId = "123"

    var primaryBannerId: String = "12323"
    var secondaryBannerId: String? = null
    var tertiaryBannerId: String? = null

    var primaryInterstitialId: String = "12323"
    var secondaryInterstitialId: String? = null
    var tertiaryInterstitialId: String? = null

    var primaryNativeId: String = "12323"
    var secondaryNativeId: String? = null
    var tertiaryNativeId: String? = null

    var primaryRewardsId: String = "12323"
    var secondaryRewardsId: String? = null
    var tertiaryRewardsId: String? = null
}