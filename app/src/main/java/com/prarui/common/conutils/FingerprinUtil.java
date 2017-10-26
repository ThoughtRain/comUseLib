package com.prarui.common.conutils;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * Created by Prarui on 2017/10/17.
 */

public class FingerprinUtil {
    //使用对称加密；


    private FingerprintManagerCompat mFingerprintManager;
    private KeyguardManager mKeyManager;
    private CancellationSignal mCancellationSignal;
    private Activity mActivity;
    private KeyStore mKeyStore;
    private KeyGenerator mKeyGenerator;
    static final String DEFAULT_KEY_NAME = "default_key";


    public void AES() {
        try {
            mKeyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (KeyStoreException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to get an instance of KeyStore", e);
        }

        try {
            mKeyGenerator = KeyGenerator
                    .getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to get an instance of KeyGenerator", e);
        }

        Cipher defaultCipher;
        try {
            defaultCipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                    + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("创建Cipher对象失败", e);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            initCipher(defaultCipher, DEFAULT_KEY_NAME);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean initCipher(Cipher cipher, String keyName) {
        try {
            mKeyStore.load(null);
            SecretKey key = (SecretKey) mKeyStore.getKey(keyName, null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }

    public FingerprinUtil(Context ctx) {
        mActivity = (Activity) ctx;
        mFingerprintManager = FingerprintManagerCompat.from(mActivity);
        mKeyManager = (KeyguardManager) mActivity.getSystemService(Context.KEYGUARD_SERVICE);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void callFingerPrintVerify(final IFingerprintResultListener listener) {
        if (!isHardwareDetected()) {
            return;
        }
        if (!isHasEnrolledFingerprints()) {
            if (listener != null) {
                listener.onNoEnroll();
            }
            return;
        }
        if (!isKeyguardSecure()) {
            if (listener != null) {
                listener.onInSecurity();
            }
            return;
        }
        if (listener != null) {
            listener.onSupport();
        }

        if (listener != null) {
            listener.onAuthenticateStart();
        }
        if (mCancellationSignal == null) {
            mCancellationSignal = new CancellationSignal();
        }
        try {
            mFingerprintManager.authenticate(null, 0, mCancellationSignal, new FingerprintManagerCompat.AuthenticationCallback() {
                //多次尝试都失败会走onAuthenticationError，会停止响应一段时间，提示尝试次数过多，请稍后再试。
                @Override
                public void onAuthenticationError(int errMsgId, CharSequence errString) {
                    if (listener != null)
                        listener.onAuthenticateError(errMsgId, errString);
                }

                //指纹验证失败走此方法，例如小米前4次验证失败走onAuthenticationFailed,第5次走onAuthenticationError
                @Override
                public void onAuthenticationFailed() {
                    if (listener != null)
                        listener.onAuthenticateFailed();
                }

                @Override
                public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
                    if (listener != null)
                        listener.onAuthenticateHelp(helpMsgId, helpString);

                }

                //当验证的指纹成功时会回调此函数，然后不再监听指纹sensor
                @Override
                public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
                    if (listener != null)
                        listener.onAuthenticateSucceeded(result);
                }

            }, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 是否录入指纹，有些设备上即使录入了指纹，但是没有开启锁屏密码的话此方法还是返回false
     *
     * @return
     */
    private boolean isHasEnrolledFingerprints() {
        try {
            return mFingerprintManager.hasEnrolledFingerprints();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 是否有指纹识别硬件支持
     *
     * @return
     */
    public boolean isHardwareDetected() {
        try {
            return mFingerprintManager.isHardwareDetected();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断是否开启锁屏密码
     *
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private boolean isKeyguardSecure() {
        try {
            return mKeyManager.isKeyguardSecure();
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * 指纹识别回调接口
     */
    public interface IFingerprintResultListener {
        void onInSecurity();

        void onNoEnroll();

        void onSupport();

        void onAuthenticateStart();

        void onAuthenticateError(int errMsgId, CharSequence errString);

        void onAuthenticateFailed();

        void onAuthenticateHelp(int helpMsgId, CharSequence helpString);

        void onAuthenticateSucceeded(FingerprintManagerCompat.AuthenticationResult result);

    }

    public void cancelAuthenticate() {
        if (mCancellationSignal != null) {
            mCancellationSignal.cancel();
            mCancellationSignal = null;
        }
    }


    public void onDestroy() {
        cancelAuthenticate();
        mKeyManager = null;
        mFingerprintManager = null;

    }
}
