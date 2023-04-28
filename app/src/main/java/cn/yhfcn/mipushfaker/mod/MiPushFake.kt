package cn.yhfcn.mipushfaker.mod

import android.os.Build
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

class MiPushFake : IXposedHookLoadPackage {
    val props: Map<String, String> = mapOf(
        Pair("product.manufacturer", "vivo"),
        Pair("ro.product.vendor.manufacturer", "vivo"),
        Pair("ro.product.brand", "vivo"),
        Pair("ro.product.vendor.brand", "vivo"),
    )

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        XposedHelpers.findAndHookMethod(XposedHelpers.findClass(
            "android.os.SystemProperties",
            lpparam.classLoader
        ),
            "native_get",
            String::class.java,
            String::class.java,
            object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    val key = param.args[0].toString()
                    if (props.containsKey(key)) {
                        param.result = props[key]
                    }
                }
            })

        // android.os.SystemProperties.native_get_int(String,int)

        // android.os.SystemProperties.native_get_int(String,int)
        XposedHelpers.findAndHookMethod(XposedHelpers.findClass(
            "android.os.SystemProperties",
            lpparam.classLoader
        ),
            "native_get_int",
            String::class.java,
            Int::class.javaPrimitiveType,
            object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    val key = param.args[0].toString()
                    if (props.containsKey(key)) {
                        param.result = props[key]
                    }
                }
            })

        // android.os.SystemProperties.native_get_long(String,long)

        // android.os.SystemProperties.native_get_long(String,long)
        XposedHelpers.findAndHookMethod(XposedHelpers.findClass(
            "android.os.SystemProperties",
            lpparam.classLoader
        ),
            "native_get_long",
            String::class.java,
            Long::class.javaPrimitiveType,
            object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    val key = param.args[0].toString()
                    if (props.containsKey(key)) {
                        param.result = props[key]
                    }
                }
            })

        XposedHelpers.setStaticObjectField(Build::class.java, "MANUFACTURER", "vivo")
        XposedHelpers.setStaticObjectField(Build::class.java, "BRAND", "vivo")
    }
}
