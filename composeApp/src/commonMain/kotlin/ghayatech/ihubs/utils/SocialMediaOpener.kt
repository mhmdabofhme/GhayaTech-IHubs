package ghayatech.ihubs.utils// في commonMain/kotlin/com/yourproject/utils/SocialMediaOpener.kt

//interface SocialMediaOpener {
//    /**
//     * يفتح محادثة واتساب مع رقم هاتف معين ونص اختياري.
//     * @param number رقم الهاتف مع رمز الدولة (مثال: +966551234567).
//     * @param text نص الرسالة الافتراضي.
//     */
//    fun openWhatsAppChat(number: String, text: String? = null)
//
//    /**
//     * يفتح ملف شخصي على إنستجرام باسم مستخدم معين.
//     * @param username اسم المستخدم (مثال: example_user).
//     */
//    fun openInstagramProfile(username: String)
//
//    /**
//     * يفتح ملف شخصي على فيسبوك بمعرف المستخدم.
//     * @param userId معرف المستخدم (مثال: 1234567890).
//     */
//    fun openFacebookProfile(userId: String)
//    /**
//     * يفتح ملف شخصي على LinkedIn بمعرف المستخدم.
//     * @param userId معرف المستخدم (مثال: example-user).
//     */
//    fun openLinkedInProfile(userId: String)
//}


interface SocialOpener {
    fun openWhatsApp(phone: String, message: String? = null)
    fun openFacebook(pageId: String)
    fun openInstagram(username: String)
    fun openLinkedIn(profileId: String)
    fun openUrl(url: String)
}

