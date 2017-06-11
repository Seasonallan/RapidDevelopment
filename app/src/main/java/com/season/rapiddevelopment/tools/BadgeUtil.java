package com.season.rapiddevelopment.tools;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;

/**
 * Disc: 桌面图标红点数字改变
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 21:26
 */
public class BadgeUtil {
	public enum Platform {
		samsung, lg, htc, mi, sony
	};

	/**
	 * 清空桌面图标红点数量
	 * @param context
	 */
	public static void clearBadgeCount(Context context){
		try { 
			BadgeUtil.setBadgeCount(context, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 智能选择操作系统
	 * @param context
	 * @param count
	 */
	public static void setBadgeCount(Context context, int count){
		if (Build.MANUFACTURER.equalsIgnoreCase("Xiaomi")) {
			BadgeUtil.setBadgeCount(context, count,
					Platform.mi);
		} else if (Build.MANUFACTURER.equalsIgnoreCase("samsung")) {
			BadgeUtil.setBadgeCount(context, count,
					Platform.samsung);
		} else if (Build.MANUFACTURER.equalsIgnoreCase("htc")) {
			BadgeUtil.setBadgeCount(context, count,
					Platform.htc);
		} else if (Build.MANUFACTURER.equalsIgnoreCase("lg")) {
			BadgeUtil.setBadgeCount(context, count,
					Platform.lg);
		} else if (Build.MANUFACTURER.equalsIgnoreCase("sony")) {
			BadgeUtil.setBadgeCount(context, count,
					Platform.sony);
		}else{
			BadgeUtil.setBadgeCount(context, count, Platform.mi);
		}
	}
	
	/**
	 * 设置红点个数
	 * @param context
	 * @param count
	 * @param platform
	 */
	public static void setBadgeCount(Context context, int count,
			Platform platform) {
		Intent badgeIntent = null;
		if (platform.equals(Platform.samsung)) {
			badgeIntent = new Intent(
					"android.intent.action.BADGE_COUNT_UPDATE");
			badgeIntent.putExtra("badge_count", count);
			badgeIntent.putExtra("badge_count_package_name",
					context.getPackageName());
			badgeIntent.putExtra("badge_count_class_name",
					getLauncherClassName(context));
		}

		if (platform.equals(Platform.mi)) {
			badgeIntent = new Intent("android.intent.action.APPLICATION_MESSAGE_UPDATE");
			badgeIntent
					.putExtra(
							"android.intent.extra.update_application_component_name",
							getLauncherClassName(context));
			badgeIntent.putExtra(
					"android.intent.extra.update_application_message_text",
					count);
			context.sendBroadcast(badgeIntent);
		}

		if (platform.equals(Platform.sony)) {
			badgeIntent = new Intent();
			badgeIntent
					.putExtra(
							"com.sonyericsson.home.intent.extra.badge.SHOW_MESSAGE",
							true);
			badgeIntent
					.setAction("com.sonyericsson.home.action.UPDATE_BADGE");
			badgeIntent
					.putExtra(
							"com.sonyericsson.home.intent.extra.badge.ACTIVITY_NAME",
							getLauncherClassName(context));
			badgeIntent.putExtra(
					"com.sonyericsson.home.intent.extra.badge.MESSAGE",
					count);
			badgeIntent
					.putExtra(
							"com.sonyericsson.home.intent.extra.badge.PACKAGE_NAME",
							context.getPackageName());
		}
		if (platform.equals(Platform.htc)) {
			badgeIntent = new Intent(
					"com.htc.launcher.action.UPDATE_SHORTCUT");
			badgeIntent.putExtra("packagename",
					getLauncherClassName(context));
			badgeIntent.putExtra("count", count);
		}

		if (platform.equals(Platform.lg)) {
			badgeIntent = new Intent(
					"android.intent.action.BADGE_COUNT_UPDATE");
			badgeIntent.putExtra("badge_count_package_name",
					context.getPackageName());
			badgeIntent.putExtra("badge_count_class_name",
					getLauncherClassName(context));
			badgeIntent.putExtra("badge_count", count);
		}
		context.sendBroadcast(badgeIntent);
	}

	/**
	 * 获取应用登录类
	 * @param context
	 * @return
	 */
	private static String getLauncherClassName(Context context) {
		PackageManager packageManager = context.getPackageManager();

		Intent intent = new Intent(Intent.ACTION_MAIN);
		// To limit the components this Intent will resolve to, by setting
		// an
		// explicit package name.
		intent.setPackage(context.getPackageName());
		intent.addCategory(Intent.CATEGORY_LAUNCHER);

		// All Application must have 1 Activity at least.
		// Launcher activity must be found!
		ResolveInfo info = packageManager.resolveActivity(intent,
				PackageManager.MATCH_DEFAULT_ONLY);

		// get a ResolveInfo containing ACTION_MAIN, CATEGORY_LAUNCHER
		// if there is no Activity which has filtered by CATEGORY_DEFAULT
		if (info == null) {
			info = packageManager.resolveActivity(intent, 0);
		}
		return info.activityInfo.name;
	}
}