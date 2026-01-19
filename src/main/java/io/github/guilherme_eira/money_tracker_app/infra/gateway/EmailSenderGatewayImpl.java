package io.github.guilherme_eira.money_tracker_app.infra.gateway;

import io.github.guilherme_eira.money_tracker_app.application.gateway.EmailSenderGateway;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderGatewayImpl implements EmailSenderGateway {

    private final JavaMailSender mailSender;

    @Override
    public void sendAccountRecoverEmail(String email, String token) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("noreply@moneytracker.com");
            helper.setTo(email);
            helper.setSubject("Recuperação de Senha | Money Tracker");

            String link = "http://localhost:8080/auth/reset-password?token=" + token;

            String htmlContent = """
                                    <!DOCTYPE html>
                                         <html>
                                         <head>
                                             <meta charset="UTF-8">
                                             <title>Recuperação de Senha</title>
                                             <style>
                                                 body { margin: 0; padding: 0; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f8f9fa; }
                                                 a { text-decoration: none; }
                                             </style>
                                         </head>
                                         <body style="background-color: #f8f9fa; margin: 0; padding: 40px 0;">
                                            \s
                                             <table align="center" border="0" cellpadding="0" cellspacing="0" width="100%%" style="max-width: 600px; margin: auto;">
                                                 <tr>
                                                     <td align="center" style="padding-bottom: 20px;">
                                                         <h2 style="color: #495057; font-size: 24px; font-weight: 600; margin: 0;">Money Tracker</h2>
                                                     </td>
                                                 </tr>
                                                \s
                                                 <tr>
                                                     <td style="background-color: #ffffff; border-radius: 12px; box-shadow: 0 2px 4px rgba(0,0,0,0.05); padding: 40px;">
                                                         <table border="0" cellpadding="0" cellspacing="0" width="100%%">
                                                             <tr>
                                                                 <td style="text-align: center; padding-bottom: 20px;">
                                                                     <div style="background-color: #e9ecef; width: 60px; height: 60px; border-radius: 50%%; margin: 0 auto 20px auto; line-height: 60px; font-size: 30px; color: #0d6efd;">
                                                                         &#128274;
                                                                     </div>
                                                                     <h3 style="color: #212529; font-size: 22px; font-weight: 600; margin: 0;">Esqueceu sua senha?</h3>
                                                                 </td>
                                                             </tr>
                                                             <tr>
                                                                 <td style="color: #6c757d; font-size: 16px; line-height: 1.6; text-align: center; padding-bottom: 30px;">
                                                                     Não se preocupe! Recebemos uma solicitação para redefinir a senha da sua conta <strong>Money Tracker</strong>.
                                                                     <br><br>
                                                                     Para criar uma nova senha, basta clicar no botão abaixo:
                                                                 </td>
                                                             </tr>
                                                             <tr>
                                                                 <td align="center" style="padding-bottom: 30px;">
                                                                     <a href="%s" style="background-color: #0d6efd; color: #ffffff; padding: 14px 32px; border-radius: 6px; font-weight: 600; font-size: 16px; display: inline-block; box-shadow: 0 2px 4px rgba(13, 110, 253, 0.2);">
                                                                         Redefinir Minha Senha
                                                                     </a>
                                                                 </td>
                                                             </tr>
                                                             <tr>
                                                                 <td style="border-top: 1px solid #dee2e6; padding-top: 20px; color: #6c757d; font-size: 13px; text-align: center;">
                                                                     Se você não solicitou essa alteração, pode ignorar este e-mail com segurança.
                                                                     <br><br>
                                                                     <span style="color: #adb5bd;">Ou copie o link:</span><br>
                                                                     <a href="%s" style="color: #0d6efd; word-break: break-all;">%s</a>
                                                                 </td>
                                                             </tr>
                                                         </table>
                                                     </td>
                                                 </tr>
                                                \s
                                                 <tr>
                                                     <td align="center" style="padding-top: 20px; color: #adb5bd; font-size: 12px;">
                                                         &copy; 2026 Money Tracker App.
                                                     </td>
                                                 </tr>
                                             </table>
                                            \s
                                         </body>
                                         </html>
                    """.formatted(link, link, link);

            helper.setText(htmlContent, true);
            mailSender.send(message);

        } catch (MessagingException ex){
            System.err.println("Não foi possível enviar email para " + email);
        }
    }
}

