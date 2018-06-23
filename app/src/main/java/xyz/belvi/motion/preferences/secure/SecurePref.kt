package xyz.belvi.instasale.models.secure.pref

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.securepreferences.SecurePreferences
import com.tozny.crypto.android.AesCbcWithIntegrity
import xyz.belvi.instasale.models.secure.SecureUtils
import java.io.UnsupportedEncodingException
import java.security.GeneralSecurityException

/**
 * Created by zone2 on 12/24/17.
 */
open class SecurePref(context: Context?, className: String) : SecurePreferences(context, secretKey(SecureUtils().getDbKey(context!!), SecureUtils().getHashKey(context)), context.packageName + "." + className + ".xml") {

    private val mContext: Context? = context

    companion object {

        private var MNO: AesCbcWithIntegrity.SecretKeys? = null

        private fun secretKey(key: String, dbKey: String): AesCbcWithIntegrity.SecretKeys? {
            try {
                if (MNO == null)
                    MNO = AesCbcWithIntegrity.generateKeyFromPassword(key, dbKey.toByteArray(), 2)
                return MNO
            } catch (e: GeneralSecurityException) {
                e.printStackTrace()
                return null
            }

        }

        fun encrypt(cleartext: String): String? {
            if (TextUtils.isEmpty(cleartext)) {
                return cleartext
            }
            return AesCbcWithIntegrity.encrypt(cleartext, MNO).toString()
        }
    }

    fun encryptText(cleartext: String): String? {
        return encrypt(cleartext)
    }

    protected fun getContext(): Context {
        return mContext!!
    }

    protected fun clear() {
        edit().clear().commit()
    }

    protected fun putString(key: String, value: String) {
        edit().putString(key, value).commit()
    }

    protected fun putBoolean(key: String, value: Boolean) {
        edit().putBoolean(key, value).commit()
    }

    protected fun putFloat(key: String, value: Float) {
        edit().putFloat(key, value).commit()
    }

    protected fun putLong(key: String, value: Long) {
        edit().putLong(key, value).commit()
    }

    protected fun putInt(key: String, value: Int) {
        edit().putInt(key, value).commit()
    }


}