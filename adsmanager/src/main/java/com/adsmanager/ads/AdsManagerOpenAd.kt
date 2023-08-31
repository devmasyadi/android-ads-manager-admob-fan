package com.adsmanager.ads

import android.app.Activity
import com.adsmanager.admob.AdmobOpenAd
import com.adsmanager.core.CallbackAds
import com.adsmanager.core.CallbackOpenAd
import com.adsmanager.core.NetworkAds

class AdsManagerOpenAd(
    private val admobOpenAd: AdmobOpenAd,
) {

    private var currentActivity: Activity? = null
    private var currentNetworkAds = NetworkAds.ADMOB

    fun setCurrentActivity(activity: Activity) {
        this.currentActivity = activity
        admobOpenAd.currentActivity = currentActivity
    }

    fun getCurrentActivity() = currentActivity

    fun isShowingAd(): Boolean {
        return handleIsShowing(currentNetworkAds)
    }

    fun loadAd(
        activity: Activity,
        primaryNetwork: NetworkAds,
        primaryOpenAdUnitId: String,
        secondaryNetwork: NetworkAds?,
        secondaryOpenAdUnitId: String,
        tertiaryNetwork: NetworkAds?,
        tertiaryOpenAdUnitId: String,
        quaternaryNetwork: NetworkAds?,
        quaternaryOpenAdUnitId: String,
        callbackAds: CallbackAds?
    ) {
        handleLoad(activity, primaryOpenAdUnitId, primaryNetwork, object : CallbackAds() {
            override fun onAdFailedToLoad(error: String?) {
                if (secondaryNetwork == null) callbackAds?.onAdFailedToLoad(error)
                secondaryNetwork?.let {
                    handleLoad(
                        activity,
                        secondaryOpenAdUnitId,
                        secondaryNetwork,
                        object : CallbackAds() {
                            override fun onAdFailedToLoad(error: String?) {
                                if (tertiaryNetwork == null) callbackAds?.onAdFailedToLoad(error)
                                tertiaryNetwork?.let {
                                    handleLoad(
                                        activity,
                                        tertiaryOpenAdUnitId,
                                        tertiaryNetwork,
                                        object : CallbackAds() {
                                            override fun onAdFailedToLoad(error: String?) {
                                                if (quaternaryNetwork == null) callbackAds?.onAdFailedToLoad(
                                                    error
                                                )
                                                quaternaryNetwork?.let {
                                                    handleLoad(
                                                        activity,
                                                        quaternaryOpenAdUnitId,
                                                        quaternaryNetwork,
                                                        callbackAds
                                                    )
                                                }
                                            }

                                            override fun onAdLoaded() {
                                                callbackAds?.onAdLoaded()
                                            }
                                        })
                                }
                            }

                            override fun onAdLoaded() {
                                callbackAds?.onAdLoaded()
                            }
                        })
                }
            }

            override fun onAdLoaded() {
                callbackAds?.onAdLoaded()
            }
        })
    }

    fun showAdIfAvailable(
        activity: Activity,
        primaryNetwork: NetworkAds,
        primaryOpenAdUnitId: String,
        secondaryNetwork: NetworkAds?,
        secondaryOpenAdUnitId: String,
        tertiaryNetwork: NetworkAds?,
        tertiaryOpenAdUnitId: String,
        quaternaryNetwork: NetworkAds?,
        quaternaryOpenAdUnitId: String,
        callbackOpenAd: CallbackOpenAd?
    ) {
        handleShow(activity, primaryOpenAdUnitId, primaryNetwork, object : CallbackOpenAd() {
            override fun onShowAdComplete() {
                callbackOpenAd?.onShowAdComplete()
            }

            override fun onAdFailedToLoad(error: String?) {
                if (secondaryNetwork == null) callbackOpenAd?.onAdFailedToLoad(error)
                secondaryNetwork?.let {
                    handleShow(
                        activity,
                        secondaryOpenAdUnitId,
                        secondaryNetwork,
                        object : CallbackOpenAd() {
                            override fun onShowAdComplete() {
                                callbackOpenAd?.onShowAdComplete()
                            }

                            override fun onAdFailedToLoad(error: String?) {
                                if (tertiaryNetwork == null) callbackOpenAd?.onAdFailedToLoad(error)
                                tertiaryNetwork?.let {
                                    handleShow(
                                        activity,
                                        tertiaryOpenAdUnitId,
                                        tertiaryNetwork,
                                        object : CallbackOpenAd() {
                                            override fun onShowAdComplete() {
                                                callbackOpenAd?.onShowAdComplete()
                                            }

                                            override fun onAdFailedToLoad(error: String?) {
                                                if (quaternaryNetwork == null) callbackOpenAd?.onAdFailedToLoad(
                                                    error
                                                )
                                                quaternaryNetwork?.let {
                                                    handleShow(
                                                        activity,
                                                        quaternaryOpenAdUnitId,
                                                        quaternaryNetwork,
                                                        callbackOpenAd
                                                    )
                                                }
                                            }
                                        }
                                    )
                                }
                            }
                        })
                }
            }
        })
    }


    private fun handleLoad(
        activity: Activity,
        adUnitId: String,
        networkAds: NetworkAds,
        callbackAds: CallbackAds?
    ) {
        if (adUnitId.isEmpty()) {
            callbackAds?.onAdFailedToLoad("adUnit empty")
            return
        }
        when (networkAds) {
            NetworkAds.ADMOB -> admobOpenAd.loadAd(activity, adUnitId, callbackAds)
            else -> {
                callbackAds?.onAdFailedToLoad("Open Ad ${networkAds.name} not available")
            }
        }
    }

    private fun handleShow(
        activity: Activity,
        adUnitId: String,
        networkAds: NetworkAds,
        callbackOpenAd: CallbackOpenAd?
    ) {
        if (adUnitId.isEmpty()) {
            callbackOpenAd?.onAdFailedToLoad("adUnit empty")
            return
        }
        when (networkAds) {
            NetworkAds.ADMOB -> {
                currentNetworkAds = networkAds
                admobOpenAd.showAdIfAvailable(activity, adUnitId, callbackOpenAd)
            }
            else -> {
                callbackOpenAd?.onAdFailedToLoad("Open Ad ${networkAds.name} not available")
            }
        }
    }

    private fun handleIsShowing(networkAds: NetworkAds): Boolean {
        return when (networkAds) {
            NetworkAds.ADMOB -> admobOpenAd.isShowingAd
            else -> false
        }
    }
}
