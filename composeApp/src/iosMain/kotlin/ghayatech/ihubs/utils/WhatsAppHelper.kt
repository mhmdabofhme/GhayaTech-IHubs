package ghayatech.ihubs.utils

import platform.Foundation.NSURL
import platform.UIKit.UIApplication


actual class WhatsAppHelper {
    //    actual fun openWhatsApp(phone: String) {
//        val urlString = "https://wa.me/${phone.replace("+", "")}"
//        val url = NSURL.URLWithString(urlString)
//        if (url != null && UIApplication.sharedApplication.canOpenURL(url)) {
//            UIApplication.sharedApplication.openURL(url)
//        }
//    }
    actual fun openWhatsApp(phone: String) {
        val phoneClean = phone.replace("+", "")
        val urlString = "https://wa.me/$phoneClean"
        val url = NSURL.URLWithString(urlString)

        val app = UIApplication.sharedApplication

        if (url != null && app.canOpenURL(url)) {
            // openURL deprecated لكن نستخدمها هنا لسهولة التنفيذ في Kotlin/Native
            app.openURL(url)
        } else {
            // يمكن هنا تضيف منطق لعرض رسالة للمستخدم إذا أردت
            println("WhatsApp not installed or cannot open URL")
        }
    }
}