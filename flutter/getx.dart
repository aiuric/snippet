// main.dart

import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:sign_in_flutter/routes/app_pages.dart';
import 'package:sign_in_flutter/services/app_service.dart';
import 'package:sign_in_flutter/theme/app_theme.dart';
import 'package:sign_in_flutter/translation/app_translations.dart';

void main() {
  AppService.initServices();
  runApp(
    GetMaterialApp(
      debugShowCheckedModeBanner: false,
      initialRoute: Routes.INITIAL, // 앱 구동 후 첫 페이지
      defaultTransition: Transition.fade,
      getPages: AppPages.pages,
      // You can change locale using Get.updateLocale(Locale('en', 'US'));
      locale: Get.deviceLocale, // or Locale('en', 'US')
      translations: AppTranslations(),
      theme: AppTheme.lightTheme,
    ),
  );
}
