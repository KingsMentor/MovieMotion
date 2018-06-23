package xyz.belvi.instasale.models.secure

import android.content.Context
import android.content.pm.PackageManager
import android.util.Base64
import au.com.helfie.molecheck.constants.SHA_1
import au.com.helfie.molecheck.constants.SHA_256
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Created by zone2 on 12/24/17.
 */
class SecureUtils {
    fun getHashKey(context: Context): String {
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
        } catch (e: Exception) {
        }

        return ""
    }

    private var XYZ = ""
    private var ABC = ""

    fun getDbKey(context: Context): String {
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
        } catch (e: Exception) {
        }

        return ""
    }
}