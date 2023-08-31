package com.adsmanager.androidmodule

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.adsmanager.ads.AdsManager
import com.adsmanager.ads.AdsManagerOpenAd
import com.adsmanager.core.*
import com.adsmanager.core.iadsmanager.IInitialize
import com.adsmanager.core.rewards.IRewards
import com.adsmanager.core.rewards.RewardsItem
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val adsManager: AdsManager by inject()
    private val adsManagerOpenAd: AdsManagerOpenAd by inject()

    private val primaryNetworkOpenAd = NetworkAds.ADMOB

    private val primaryAds = NetworkAds.ADMOB
    private val secondaryAds = NetworkAds.FAN

    //    private val secondaryAds = NetworkAds.NONE
//    private val tertiaryAds = NetworkAds.APPLOVIN_DISCOVERY
    private val tertiaryAds = NetworkAds.NONE
    private val quaternaryAds = NetworkAds.APPLOVIN_MAX

    private val adUnitOpenAdId = "208690301"

    private val primaryAppId = ""
    private val secondaryAppId = "208690301"
    private val tertiaryAppId = "208690301"
    private val quaternaryAppId = "208690301"

    private val primaryBannerId = "ca-app-pub-3940256099942544/6300978111XXX"
//    private val secondaryBannerId = "ca-app-pub-3940256099942544/6300978111"

    //        private val secondaryBannerId = "1363711600744576_1363713000744436XXX"
    private val secondaryBannerId = "1363711600744576_1363713000744436"
    private val tertiaryBannerId = "ca-app-pub-3940256099942544/6300978111"
    private val quaternaryBannerId = "62c9e910bbd85680"

    private val primaryInterstitialId = "ca-app-pub-3940256099942544/1033173712"
//    private val secondaryInterstitialId = "ca-app-pub-3940256099942544/1033173712"

    private val secondaryInterstitialId = "1363711600744576_1508878896227845"
    private val tertiaryInterstitialId = "7263a762d1a5366b"
    private val quaternaryInterstitialId = "1363711600744576_1363713000744436"

    private val primaryNativeId = "ca-app-pub-4764558539538067/9810032480XXX"
    private val secondaryNativeId = "1363711600744576_1508905202891881XXX"
    private val tertiaryNativeId = "91294e31700550f5"
    private val quaternaryNativeId = "91294e31700550f5"

    private val primaryRewardsId = "ca-app-pub-3940256099942544/5224354917"
    private val secondaryRewardsId = "1363711600744576_1508879032894498"
    private val tertiaryRewardsId = "c11378688d2adfd1"
    private val quaternaryRewardsId = "c11378688d2adfd1"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adsManager.initialize(
            this,
            iInitialize = object : IInitialize {
                override fun onInitializationComplete() {

                }
            },
            primaryAds = primaryAds,
            secondaryAds = secondaryAds,
            tertiaryAds = tertiaryAds,
            quaternaryAds = quaternaryAds,
            primaryAppId = primaryAppId,
            secondaryAppId = secondaryAppId,
            tertiaryAppId = tertiaryAppId,
            quaternaryAppId = quaternaryAppId,
        )

        adsManager.setTestDevices(
            this,
            listOf("4362f959-044b-44ed-a177-f671fbaf73c0"),
            primaryAds,
            secondaryAds,
            tertiaryAds,
            quaternaryAds
        )

        adsManager.loadGdpr(
            this@MainActivity,
            false,
            primaryAds,
            secondaryAds,
            tertiaryAds,
            quaternaryAds
        )
        adsManager.loadInterstitial(
            this@MainActivity,
            primaryAds,
            primaryInterstitialId,
            secondaryAds,
            secondaryInterstitialId,
            tertiaryAds,
            tertiaryInterstitialId,
            quaternaryAds,
            quaternaryInterstitialId
        )
        adsManager.loadRewards(
            this@MainActivity,
            primaryAds,
            primaryBannerId,
            secondaryAds,
            secondaryBannerId,
            tertiaryAds,
            tertiaryBannerId,
            quaternaryAds,
            quaternaryRewardsId
        )

        findViewById<Button>(R.id.btnShowOpenApp).setOnClickListener {
            adsManagerOpenAd.showAdIfAvailable(
                this,
                ConfigAds.primaryAds,
                ConfigAds.primaryOpenAdId,
                ConfigAds.secondaryAds,
                ConfigAds.secondaryOpenAdId,
                ConfigAds.tertiaryAds,
                ConfigAds.tertiaryOpenAdId,
                null,
                "",
                object : CallbackOpenAd() {
                    override fun onAdFailedToLoad(error: String?) {
                        Log.e("HALLO", "openAd onAdFailedToLoad: $error")
                    }

                    override fun onAdLoaded() {
                        Log.e("HALLO", "openAd onAdLoaded")
                    }

                    override fun onShowAdComplete() {
                        Log.e("HALLO", "openAd onShowAdComplete")
                    }

                })
        }

        findViewById<Button>(R.id.btnShowBanner).setOnClickListener {
            val bannerView = findViewById<RelativeLayout>(R.id.bannerView)
            adsManager.showBanner(
                this,
                bannerView,
                SizeBanner.SMALL,
                primaryAds,
                primaryBannerId,
                secondaryAds,
                secondaryBannerId,
                tertiaryAds,
                tertiaryBannerId,
                quaternaryAds,
                quaternaryBannerId,
                object : CallbackAds() {
                    override fun onAdFailedToLoad(error: String?) {
                        super.onAdFailedToLoad(error)
                        Log.e("HALLO", "banner error: $error")
                    }

                    override fun onAdLoaded() {
                        super.onAdLoaded()
                        Log.e("HALLO", "banner onAdLoaded")
                    }
                })
        }

        findViewById<Button>(R.id.btnShowInterstitial).setOnClickListener {
            adsManager.showInterstitial(this,
                primaryAds,
                primaryInterstitialId,
                secondaryAds,
                secondaryInterstitialId,
                tertiaryAds,
                tertiaryInterstitialId,
                quaternaryAds,
                quaternaryInterstitialId,
                object : CallbackAds() {
                    override fun onAdFailedToLoad(error: String?) {
                        Log.e("HALLO", "interstitial error: $error")
                    }
                })
        }

        findViewById<Button>(R.id.btnShowRewards).setOnClickListener {
            adsManager.showRewards(
                this,
                primaryAds,
                primaryRewardsId,
                secondaryAds,
                secondaryRewardsId,
                tertiaryAds,
                tertiaryRewardsId,
                quaternaryAds,
                quaternaryRewardsId,
                object : CallbackAds() {
                    override fun onAdFailedToLoad(error: String?) {
                        Log.e("HALLO", "rewards error: $error")
                    }
                },
                object : IRewards {
                    override fun onUserEarnedReward(rewardsItem: RewardsItem?) {
                        Log.i("HALLO", "onUserEarnedReward: $rewardsItem")
                    }

                }
            )
        }

        findViewById<Button>(R.id.btnSmallNative).setOnClickListener {
            val nativeView = findViewById<RelativeLayout>(R.id.nativeView)
            adsManager.showNativeAds(
                this,
                nativeView,
                SizeNative.SMALL,
                primaryAds,
                primaryNativeId,
                secondaryAds,
                secondaryNativeId,
                tertiaryAds,
                tertiaryNativeId,
                quaternaryAds,
                quaternaryNativeId,
                object : CallbackAds() {
                    override fun onAdFailedToLoad(error: String?) {
                        Log.e("HALLO", "Native error: $error")
                    }
                })
        }

        findViewById<Button>(R.id.btnSmallNativeRectangle).setOnClickListener {
            val nativeView = findViewById<RelativeLayout>(R.id.nativeView)
            adsManager.showNativeAds(
                this,
                nativeView,
                SizeNative.SMALL_RECTANGLE,
                primaryAds,
                primaryNativeId,
                secondaryAds,
                secondaryNativeId,
                tertiaryAds,
                tertiaryNativeId,
                quaternaryAds,
                quaternaryNativeId,
                object : CallbackAds() {
                    override fun onAdFailedToLoad(error: String?) {
                        Log.e("HALLO", "Native error: $error")
                    }
                })
        }

        findViewById<Button>(R.id.btnMediumNative).setOnClickListener {
            val nativeView = findViewById<RelativeLayout>(R.id.nativeView)
            adsManager.showNativeAds(
                this,
                nativeView,
                SizeNative.MEDIUM,
                primaryAds,
                primaryNativeId,
                secondaryAds,
                secondaryNativeId,
                tertiaryAds,
                tertiaryNativeId,
                quaternaryAds,
                quaternaryNativeId,
                object : CallbackAds() {
                    override fun onAdFailedToLoad(error: String?) {
                        Log.e("HALLO", "Native error: $error")
                    }
                })
        }


    }


}