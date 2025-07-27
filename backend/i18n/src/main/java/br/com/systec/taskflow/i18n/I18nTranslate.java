package br.com.systec.taskflow.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class I18nTranslate {

   private static ResourceBundleMessageSource messageSource;

   @Autowired
   I18nTranslate(ResourceBundleMessageSource resourceBundleMessageSource) {
      I18nTranslate.messageSource = resourceBundleMessageSource;
   }

   public static String toLocale(String msg) {
      Locale locale = LocaleContextHolder.getLocale();
      try {
         return messageSource.getMessage(msg, null, locale);
      } catch (NoSuchMessageException e) {
         return msg;
      }
   }
   public static String toLocale(String msg, Object ... args) {
      Locale locale = LocaleContextHolder.getLocale();
      try {
         return messageSource.getMessage(msg, args, locale);
      } catch (NoSuchMessageException e) {
         return msg;
      }
   }
}