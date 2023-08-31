package com.adsmanager.ads

import android.app.Activity
import android.content.Context
import android.widget.RelativeLayout
import com.adsmanager.core.CallbackAds
import com.adsmanager.core.NetworkAds
import com.adsmanager.core.SizeBanner
import com.adsmanager.core.SizeNative
import com.adsmanager.core.iadsmanager.IInitialize
import com.adsmanager.core.rewards.IRewards

interface IAds {

    fun initialize(
        context: Context,
        iInitialize: IInitialize,
        primaryAds: NetworkAds,
        primaryAppId: String?,
        secondaryAds: NetworkAds?,
        secondaryAppId: String?,
        tertiaryAds: NetworkAds?,
        tertiaryAppId: String?,
        quaternaryAds: NetworkAds?,
        quaternaryAppId: String?,
    )

    fun setTestDevices(
        activity: Activity, testDevices: List<String>,
        primaryAds: NetworkAds,
        secondaryAds: NetworkAds?,
        tertiaryAds: NetworkAds?,
        quaternaryAds: NetworkAds?,
    )

    fun loadGdpr(
        activity: Activity,
        childDirected: Boolean,
        primaryAds: NetworkAds,
        secondaryAds: NetworkAds?,
        tertiaryAds: NetworkAds?,
        quaternaryAds: NetworkAds?,
    )

    fun showBanner(
        activity: Activity,
        bannerView: RelativeLayout,
        sizeBanner: SizeBanner,
        primaryAds: NetworkAds,
        adUnitPrimaryId: String,
        secondaryAds: NetworkAds?,
        adUnitSecondaryId: String,
        tertiaryAds: NetworkAds?,
        adUnitTertiaryAdsId: String,
        quaternaryAds: NetworkAds?,
        adUnitQuaternaryId: String,
        callbackAds: CallbackAds?
    )

    fun loadInterstitial(
        activity: Activity,
        primaryAds: NetworkAds,
        adUnitPrimaryId: String,
        secondaryAds: NetworkAds?,
        adUnitSecondaryId: String,
        tertiaryAds: NetworkAds?,
        adUnitTertiaryAdsId: String,
        quaternaryAds: NetworkAds?,
        adUnitQuaternaryId: String,
    )

    fun showInterstitial(
        activity: Activity,
        primaryAds: NetworkAds,
        adUnitPrimaryId: String,
        secondaryAds: NetworkAds?,
        adUnitSecondaryId: String,
        tertiaryAds: NetworkAds?,
        adUnitTertiaryAdsId: String,
        quaternaryAds: NetworkAds?,
        adUnitQuaternaryId: String,
        callbackAds: CallbackAds?
    )

    fun showNativeAds(
        activity: Activity,
        nativeView: RelativeLayout,
        sizeNative: SizeNative,
        primaryAds: NetworkAds,
        adUnitPrimaryId: String,
        secondaryAds: NetworkAds?,
        adUnitSecondaryId: String,
        tertiaryAds: NetworkAds?,
        adUnitTertiaryAdsId: String,
        quaternaryAds: NetworkAds?,
        adUnitQuaternaryId: String,
        callbackAds: CallbackAds?
    )

    fun loadRewards(
        activity: Activity,
        primaryAds: NetworkAds,
        adUnitPrimaryId: String,
        secondaryAds: NetworkAds?,
        adUnitSecondaryId: String,
        tertiaryAds: NetworkAds?,
        adUnitTertiaryAdsId: String,
        quaternaryAds: NetworkAds?,
        adUnitQuaternaryId: String,
    )

    fun showRewards(
        activity: Activity,
        primaryAds: NetworkAds,
        adUnitPrimaryId: String,
        secondaryAds: NetworkAds?,
        adUnitSecondaryId: String,
        tertiaryAds: NetworkAds?,
        adUnitTertiaryAdsId: String,
        quaternaryAds: NetworkAds?,
        adUnitQuaternaryId: String,
        callbackAds: CallbackAds?,
        iRewards: IRewards?
    )
}

