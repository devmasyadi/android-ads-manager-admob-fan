package com.adsmanager.ads

import android.app.Activity
import android.content.Context
import android.widget.RelativeLayout
import com.adsmanager.admob.AdmobAds
import com.adsmanager.core.CallbackAds
import com.adsmanager.core.NetworkAds
import com.adsmanager.core.SizeBanner
import com.adsmanager.core.SizeNative
import com.adsmanager.core.iadsmanager.IInitialize
import com.adsmanager.core.rewards.IRewards
import com.adsmanager.fan.FanAds

class HandleAds(
    private val admobAds: AdmobAds,
    private val fanAds: FanAds,
) {
    fun initialize(
        context: Context,
        appId: String?,
        iInitialize: IInitialize,
        networkAds: NetworkAds
    ) {
        when (networkAds) {
            NetworkAds.ADMOB -> admobAds.initialize(context, appId, iInitialize)
            NetworkAds.FAN -> fanAds.initialize(context, appId, iInitialize)
            else -> {}
        }
    }

    fun setTestDevices(activity: Activity, testDevices: List<String>, networkAds: NetworkAds) {
        when (networkAds) {
            NetworkAds.ADMOB -> admobAds.setTestDevices(activity, testDevices)
            NetworkAds.FAN -> fanAds.setTestDevices(activity, testDevices)
            else -> {}
        }
    }

    fun loadGdpr(activity: Activity, childDirected: Boolean, networkAds: NetworkAds) {
        when (networkAds) {
            NetworkAds.ADMOB -> admobAds.loadGdpr(activity, childDirected)
            NetworkAds.FAN -> fanAds.loadGdpr(activity, childDirected)
            else -> {}
        }
    }

    fun showBanner(
        activity: Activity,
        bannerView: RelativeLayout,
        sizeBanner: SizeBanner,
        networkAds: NetworkAds,
        adUnitId: String,
        callbackAds: CallbackAds?
    ) {
        if (adUnitId.isEmpty()) {
            callbackAds?.onAdFailedToLoad("AdUnit Empty")
            return
        }
        when (networkAds) {
            NetworkAds.ADMOB -> admobAds.showBanner(
                activity,
                bannerView,
                sizeBanner,
                adUnitId,
                callbackAds
            )
            NetworkAds.FAN -> fanAds.showBanner(
                activity,
                bannerView,
                sizeBanner,
                adUnitId,
                callbackAds
            )
            else -> {
                callbackAds?.onAdFailedToLoad("Ads None")
            }
        }
    }

    fun loadInterstitial(
        activity: Activity,
        networkAds: NetworkAds,
        adUnitId: String,
    ) {
        if (adUnitId.isEmpty()) {
            return
        }
        when (networkAds) {
            NetworkAds.ADMOB -> admobAds.loadInterstitial(activity, adUnitId)
            NetworkAds.FAN -> fanAds.loadInterstitial(activity, adUnitId)
            else -> {}
        }
    }

    fun showInterstitial(
        activity: Activity,
        networkAds: NetworkAds,
        adUnitId: String,
        callbackAds: CallbackAds?
    ) {
        if (adUnitId.isEmpty()) {
            callbackAds?.onAdFailedToLoad("AdUnit Empty")
            return
        }
        when (networkAds) {
            NetworkAds.ADMOB -> admobAds.showInterstitial(activity, adUnitId, callbackAds)
            NetworkAds.FAN -> fanAds.showInterstitial(activity, adUnitId, callbackAds)
            else -> {
                callbackAds?.onAdFailedToLoad("Ads None")
            }
        }
    }

    fun showNativeAds(
        activity: Activity,
        nativeView: RelativeLayout,
        sizeNative: SizeNative,
        networkAds: NetworkAds,
        adUnitId: String,
        callbackAds: CallbackAds?
    ) {
        if (adUnitId.isEmpty()) {
            callbackAds?.onAdFailedToLoad("AdUnit Empty")
            return
        }
        when (networkAds) {
            NetworkAds.ADMOB -> admobAds.showNativeAds(
                activity,
                nativeView,
                sizeNative,
                adUnitId,
                callbackAds
            )
            NetworkAds.FAN -> fanAds.showNativeAds(
                activity,
                nativeView,
                sizeNative,
                adUnitId,
                callbackAds
            )
            else -> {
                callbackAds?.onAdFailedToLoad("Ads None")
            }
        }
    }

    fun loadRewards(
        activity: Activity,
        networkAds: NetworkAds,
        adUnitId: String,
    ) {
        if (adUnitId.isEmpty()) {
            return
        }
        when (networkAds) {
            NetworkAds.ADMOB -> admobAds.loadRewards(activity, adUnitId)
            NetworkAds.FAN -> fanAds.loadRewards(activity, adUnitId)
            else -> {}
        }
    }

    fun showRewards(
        activity: Activity,
        networkAds: NetworkAds,
        adUnitId: String,
        callbackAds: CallbackAds?,
        iRewards: IRewards?
    ) {
        if (adUnitId.isEmpty()) {
            callbackAds?.onAdFailedToLoad("AdUnit Empty")
            return
        }
        when (networkAds) {
            NetworkAds.ADMOB -> admobAds.showRewards(activity, adUnitId, callbackAds, iRewards)
            NetworkAds.FAN -> fanAds.showRewards(activity, adUnitId, callbackAds, iRewards)
            else -> {
                callbackAds?.onAdFailedToLoad("Ads None")
            }
        }
    }
}