package kata

import jakarta.mail.*
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeBodyPart
import jakarta.mail.internet.MimeMessage
import jakarta.mail.internet.MimeMultipart


class EmailGateway {

    fun send(address: String, subject: String, msg: String) {

        val prop = mapOf(
            "mail.smtp.host" to "localhost",
            "mail.smtp.port" to "587",
            "mail.smtp.auth" to "true",
            "mail.smtp.ssl.trust" to "localhost",
            "mail.smtp.ssl.enable" to "true"
        ).toProperties()

        val session: Session = Session.getInstance(prop, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication =
                PasswordAuthentication("email", "password")
        })

        val message: Message = MimeMessage(session)
        val mimeBodyPart = MimeBodyPart()
        mimeBodyPart.setContent(msg, "text/html; charset=utf-8")
        val multipart = MimeMultipart()
        multipart.addBodyPart(mimeBodyPart)
        message.setContent(multipart)

        message.setFrom(InternetAddress("noreply@example.com"))
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(address))
        message.subject = subject
        message.setContent(multipart)
        Transport.send(message)
    }

}