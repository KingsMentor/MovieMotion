package xyz.belvi.instasale.models.secure

import android.content.Context
import android.content.pm.PackageManager
import android.util.Base64
import android.util.Log
import xyz.belvi.motion.BuildConfig
import xyz.belvi.motion.constants.SHA_1
import xyz.belvi.motion.constants.SHA_256
import xyz.belvi.motion.constants.STUB_DBKEY
import xyz.belvi.motion.constants.STUB_HASHKEY
import xyz.belvi.motion.utils.motionError
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Created by Nosa Belvi on 12/24/17.
 *
 * @SecureUtils is a utility class for SecurePref
 * noted that @getDbKey and @getHashKey only works if the app is signed
 * a stub response is returned for debug purpose
 *
 */

class SecureUtils {
    // returns sha-1 of the app signature
    fun getHashKey(context: Context?): String {
        context?.let {
            try {
                if (ABC.isEmpty()) {
                    val info = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_SIGNATURES)
                    for (signature in info.signatures) {
                        val md = MessageDigest.getInstance(SHA_1)
                        md.update(signature.toByteArray())
                        val hashKey = String(Base64.encode(md.digest(), 0))
                        ABC = hashKey.trim()

                    }

                }
                return ABC
            } catch (e: NoSuchAlgorithmException) {
                context.motionError(SecureUtils::class.java.name, e.message)
            } catch (e: Exception) {
                context.motionError(SecureUtils::class.java.name, e.message)
            }
        }
        return if (BuildConfig.DEBUG) STUB_HASHKEY else ""
    }

    private var XYZ = ""
    private var ABC = ""

    // returns the first 64 characters of a concatenation of sha256 and sha1 of the app signature
    fun getDbKey(context: Context?): String {
        context?.let {
            try {
                if (XYZ.isEmpty()) {
                    val info = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_SIGNATURES)
                    for (signature in info.signatures) {
                        var md = MessageDigest.getInstance(SHA_256)
                        md.update(signature.toByteArray())
                        val hashKey256 = String(Base64.encode(md.digest(), 0))
                        md = MessageDigest.getInstance(SHA_1)
                        val hashKey1 = String(Base64.encode(md.digest(), 0))
                        XYZ = (hashKey256 + hashKey1).substring(0, 64)
                        return XYZ

                    }
                }
                return XYZ
            } catch (e: NoSuchAlgorithmException) {
                context.motionError(SecureUtils::class.java.name, e.message)
            } catch (e: Exception) {
                context.motionError(SecureUtils::class.java.name, e.message)
            }
        }
        return if (BuildConfig.DEBUG) STUB_DBKEY else ""

    }
}