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

class AdsManager(
    private val handleAds: HandleAds
) : IAds {
    override fun initialize(
        context: Context,
        iInitialize: IInitialize,
        primaryAds: NetworkAds,
        primaryAppId: String?,
        secondaryAds: NetworkAds?,
        secondaryAppId: String?,
        tertiaryAds: NetworkAds?,
        tertiaryAppId: String?,
        quaternaryAds: NetworkAds?,
        quaternaryAppId: String?
    ) {
        handleAds.initialize(context, primaryAppId, iInitialize, primaryAds)
        secondaryAds?.let { handleAds.initialize(context, secondaryAppId, iInitialize, it) }
        tertiaryAds?.let { handleAds.initialize(context, tertiaryAppId, iInitialize, it) }
        quaternaryAds?.let { handleAds.initialize(context, quaternaryAppId, iInitialize, it) }
        iInitialize.onInitializationComplete()
    }

    override fun setTestDevices(
        activity: Activity,
        testDevices: List<String>,
        primaryAds: NetworkAds,
        secondaryAds: NetworkAds?,
        tertiaryAds: NetworkAds?,
        quaternaryAds: NetworkAds?
    ) {
        handleAds.setTestDevices(activity, testDevices, primaryAds)
        secondaryAds?.let { handleAds.setTestDevices(activity, testDevices, it) }
        tertiaryAds?.let { handleAds.setTestDevices(activity, testDevices, it) }
        quaternaryAds?.let { handleAds.setTestDevices(activity, testDevices, it) }
    }

    override fun loadGdpr(
        activity: Activity,
        childDirected: Boolean,
        primaryAds: NetworkAds,
        secondaryAds: NetworkAds?,
        tertiaryAds: NetworkAds?,
        quaternaryAds: NetworkAds?
    ) {
        handleAds.loadGdpr(activity, childDirected, primaryAds)
        secondaryAds?.let { handleAds.loadGdpr(activity, childDirected, it) }
        tertiaryAds?.let { handleAds.loadGdpr(activity, childDirected, it) }
        quaternaryAds?.let { handleAds.loadGdpr(activity, childDirected, it) }
    }

    override fun showBanner(
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
    ) {
        handleAds.showBanner(
            activity,
            bannerView,
            sizeBanner,
            primaryAds,
            adUnitPrimaryId,
            object : CallbackAds() {
                override fun onAdFailedToLoad(error: String?) {
                    if (secondaryAds == null) callbackAds?.onAdFailedToLoad(error)
                    secondaryAds?.let {
                        handleAds.showBanner(
                            activity,
                            bannerView,
                            sizeBanner,
                            secondaryAds,
                            adUnitSecondaryId,
                            object : CallbackAds() {
                                override fun onAdFailedToLoad(error: String?) {
                                    if (tertiaryAds == null) callbackAds?.onAdFailedToLoad(error)
                                    tertiaryAds?.let {
                                        handleAds.showBanner(
                                            activity,
                                            bannerView,
                                            sizeBanner,
                                            tertiaryAds,
                                            adUnitTertiaryAdsId,
                                            object : CallbackAds() {
                                                override fun onAdFailedToLoad(error: String?) {
                                                    if (quaternaryAds == null) callbackAds?.onAdFailedToLoad(
                                                        error
                                                    )
                                                    quaternaryAds?.let {
                                                        handleAds.showBanner(
                                                            activity,
                                                            bannerView,
                                                            sizeBanner,
                                                            quaternaryAds,
                                                            adUnitQuaternaryId,
                                                            callbackAds
                                                        )
                                                    }
                                                }
                                                override fun onAdLoaded() {
                                                    super.onAdLoaded()
                                                    callbackAds?.onAdLoaded()
                                                }
                                            }
                                        )
                                    }
                                }
                                override fun onAdLoaded() {
                                    super.onAdLoaded()
                                    callbackAds?.onAdLoaded()
                                }
                            })
                    }
                }

                override fun onAdLoaded() {
                    super.onAdLoaded()
                    callbackAds?.onAdLoaded()
                }
            })
    }

    override fun loadInterstitial(
        activity: Activity,
        primaryAds: NetworkAds,
        adUnitPrimaryId: String,
        secondaryAds: NetworkAds?,
        adUnitSecondaryId: String,
        tertiaryAds: NetworkAds?,
        adUnitTertiaryAdsId: String,
        quaternaryAds: NetworkAds?,
        adUnitQuaternaryId: String
    ) {
        handleAds.loadInterstitial(activity, primaryAds, adUnitPrimaryId)
        secondaryAds?.let {
            handleAds.loadInterstitial(
                activity, secondaryAds,
                adUnitSecondaryId
            )
        }
        tertiaryAds?.let {
            handleAds.loadInterstitial(
                activity, tertiaryAds,
                adUnitTertiaryAdsId
            )
        }
        quaternaryAds?.let {
            handleAds.loadInterstitial(
                activity, quaternaryAds,
                adUnitQuaternaryId
            )
        }
    }

    override fun showInterstitial(
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
    ) {
        handleAds.showInterstitial(activity, primaryAds, adUnitPrimaryId, object : CallbackAds() {
            override fun onAdLoaded() {
                callbackAds?.onAdLoaded()
            }

            override fun onAdFailedToLoad(error: String?) {
                if (secondaryAds == null) callbackAds?.onAdFailedToLoad(error)
                secondaryAds?.let {
                    handleAds.showInterstitial(
                        activity,
                        secondaryAds,
                        adUnitSecondaryId,
                        object : CallbackAds() {
                            override fun onAdLoaded() {
                                callbackAds?.onAdLoaded()
                            }

                            override fun onAdFailedToLoad(error: String?) {
                                if (tertiaryAds == null) callbackAds?.onAdFailedToLoad(error)
                                tertiaryAds?.let {
                                    handleAds.showInterstitial(
                                        activity,
                                        tertiaryAds,
                                        adUnitTertiaryAdsId,
                                        object : CallbackAds() {
                                            override fun onAdLoaded() {
                                                callbackAds?.onAdLoaded()
                                            }

                                            override fun onAdFailedToLoad(error: String?) {
                                                if (quaternaryAds == null) callbackAds?.onAdFailedToLoad(
                                                    error
                                                )
                                                quaternaryAds?.let {
                                                    handleAds.showInterstitial(
                                                        activity,
                                                        quaternaryAds,
                                                        adUnitQuaternaryId,
                                                        callbackAds
                                                    )
                                                }
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    )
                }
            }
        })
    }

    override fun showNativeAds(
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
    ) {
        handleAds.showNativeAds(
            activity,
            nativeView,
            sizeNative,
            primaryAds,
            adUnitPrimaryId,
            object : CallbackAds() {
                override fun onAdLoaded() {
                    super.onAdLoaded()
                    callbackAds?.onAdLoaded()
                }

                override fun onAdFailedToLoad(error: String?) {
                    if (secondaryAds == null) callbackAds?.onAdFailedToLoad(error)
                    secondaryAds?.let {
                        handleAds.showNativeAds(
                            activity,
                            nativeView,
                            sizeNative,
                            secondaryAds,
                            adUnitSecondaryId,
                            object : CallbackAds() {
                                override fun onAdLoaded() {
                                    super.onAdLoaded()
                                    callbackAds?.onAdLoaded()
                                }

                                override fun onAdFailedToLoad(error: String?) {
                                    if (tertiaryAds == null) callbackAds?.onAdFailedToLoad(error)
                                    tertiaryAds?.let {
                                        handleAds.showNativeAds(
                                            activity,
                                            nativeView,
                                            sizeNative,
                                            tertiaryAds,
                                            adUnitTertiaryAdsId,
                                            object : CallbackAds() {
                                                override fun onAdLoaded() {
                                                    super.onAdLoaded()
                                                    callbackAds?.onAdLoaded()
                                                }

                                                override fun onAdFailedToLoad(error: String?) {
                                                    if (quaternaryAds == null) callbackAds?.onAdFailedToLoad(
                                                        error
                                                    )
                                                    quaternaryAds?.let {
                                                        handleAds.showNativeAds(
                                                            activity,
                                                            nativeView,
                                                            sizeNative,
                                                            quaternaryAds,
                                                            adUnitQuaternaryId,
                                                            callbackAds
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

    override fun loadRewards(
        activity: Activity,
        primaryAds: NetworkAds,
        adUnitPrimaryId: String,
        secondaryAds: NetworkAds?,
        adUnitSecondaryId: String,
        tertiaryAds: NetworkAds?,
        adUnitTertiaryAdsId: String,
        quaternaryAds: NetworkAds?,
        adUnitQuaternaryId: String
    ) {
        handleAds.loadRewards(activity, primaryAds, adUnitPrimaryId)
        secondaryAds?.let {
            handleAds.loadRewards(
                activity, secondaryAds,
                adUnitSecondaryId
            )
        }
        tertiaryAds?.let {
            handleAds.loadRewards(
                activity, tertiaryAds,
                adUnitTertiaryAdsId
            )
        }
        quaternaryAds?.let {
            handleAds.loadRewards(
                activity, quaternaryAds,
                adUnitQuaternaryId
            )
        }
    }

    override fun showRewards(
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
    ) {
        handleAds.showRewards(activity, primaryAds, adUnitPrimaryId, object : CallbackAds() {
            override fun onAdLoaded() {
                callbackAds?.onAdLoaded()
            }

            override fun onAdFailedToLoad(error: String?) {
                if (secondaryAds == null) callbackAds?.onAdFailedToLoad(error)
                secondaryAds?.let {
                    handleAds.showRewards(
                        activity,
                        secondaryAds,
                        adUnitSecondaryId,
                        object : CallbackAds() {
                            override fun onAdLoaded() {
                                callbackAds?.onAdLoaded()
                            }

                            override fun onAdFailedToLoad(error: String?) {
                                if (tertiaryAds == null) callbackAds?.onAdFailedToLoad(error)
                                tertiaryAds?.let {
                                    handleAds.showRewards(
                                        activity,
                                        tertiaryAds,
                                        adUnitTertiaryAdsId,
                                        object : CallbackAds() {
                                            override fun onAdLoaded() {
                                                callbackAds?.onAdLoaded()
                                            }

                                            override fun onAdFailedToLoad(error: String?) {
                                                if (quaternaryAds == null) callbackAds?.onAdFailedToLoad(
                                                    error
                                                )
                                                quaternaryAds?.let {
                                                    handleAds.showRewards(
                                                        activity,
                                                        quaternaryAds,
                                                        adUnitQuaternaryId,
                                                        callbackAds,
                                                        iRewards
                                                    )
                                                }
                                            }
                                        },
                                        iRewards
                                    )
                                }
                            }
                        },
                        iRewards
                    )
                }
            }
        }, iRewards)
    }

}