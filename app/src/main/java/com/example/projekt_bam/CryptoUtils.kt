package com.example.projekt_bam

import android.content.ContentValues.TAG
import android.security.keystore.KeyProperties
import android.security.keystore.KeyProtection
import android.util.Base64
import android.util.Log
import java.nio.charset.StandardCharsets
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.KeyStore
import java.util.Calendar
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class CryptoUtils {
    fun generateSecretKey (alias: String) {
        val ks = KeyStore.getInstance("AndroidKeyStore").apply {
            load(null)
        }
        val keyGen = KeyGenerator.getInstance("AES")
        keyGen.init(256)

        val secretKey: SecretKey = keyGen.generateKey()

        val start: Calendar = Calendar.getInstance()
        val end: Calendar = Calendar.getInstance()
        end.add(Calendar.YEAR, 2)

        val entry = KeyStore.SecretKeyEntry(secretKey)
        val protectionParameter = KeyProtection.
        Builder(KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
            .setKeyValidityStart(start.time)
            .setKeyValidityEnd(end.time)
            .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
            .build()

        ks.setEntry(alias, entry, protectionParameter)
    }

    fun getKey() {
        val ks = KeyStore.getInstance("AndroidKeyStore").apply {
            load(null)
        }
        val existingKey = ks.getEntry("secret_key", null) as? KeyStore.SecretKeyEntry
        existingKey?.secretKey ?: generateSecretKey("secret_key")
    }

    fun encrypt (alias: String, plainText: String): String? {
        val ks = KeyStore.getInstance("AndroidKeyStore").apply {
            load(null)
        }
        val secretKey = ks.getKey(alias, null)

        try {
            val cipher = Cipher.getInstance("AES/CBC/PKCS7PADDING")
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)
            val cipherText = Base64.encodeToString(cipher.doFinal(plainText.toByteArray()), Base64.DEFAULT)
            val iv = Base64.encodeToString(cipher.iv, Base64.DEFAULT)

            return "${cipherText}.$iv"

        } catch (e: Exception) {
            Log.d(TAG, "encrypt: error msg = ${e.message}")
            return null
        }
    }

    fun decrypt (alias: String, cipherText: String): String? {
        val ks = KeyStore.getInstance("AndroidKeyStore").apply {
            load(null)
        }

//        Log.d("CRYPTO_UTILS","decrypt - cipherText = $cipherText")

        val secretKey = ks.getKey(alias, null)

        val array = cipherText.split(".")

        val cipherData = Base64.decode(array[0], Base64.DEFAULT)
        val iv = Base64.decode(array[1], Base64.DEFAULT)

        try {
            val cipher = Cipher.getInstance("AES/CBC/PKCS7PADDING")
            val spec = IvParameterSpec(iv)


            cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)

            val clearText = cipher.doFinal(cipherData)

            val decodedText = String(clearText, 0, clearText.size, StandardCharsets.UTF_8)

//            Log.d("CRYPTO_UTILS", "decrypt: clearText: $decodedText")

            return decodedText
        } catch (e: BadPaddingException) {
            Log.e(TAG, "decrypt: Bad Padding Exception: ${e.message}")
        } catch (e: IllegalBlockSizeException) {
            Log.e(TAG, "decrypt: Illegal Block Size Exception: ${e.message}")
        } catch (e: InvalidAlgorithmParameterException) {
            Log.e(TAG, "decrypt: Invalid Algorithm Parameter Exception: ${e.message}")
        } catch (e: InvalidKeyException) {
            Log.e(TAG, "decrypt: Invalid Key Exception: ${e.message}")
        } catch (e: Exception) {
            Log.e(TAG, "decrypt: General Exception: ${e.message}")
        }
        return null
        }
    }
