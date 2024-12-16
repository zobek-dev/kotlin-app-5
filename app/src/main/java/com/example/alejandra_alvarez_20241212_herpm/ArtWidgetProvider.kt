package com.example.alejandra_alvarez_20241212_herpm

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews

class ArtWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (widgetId in appWidgetIds) {
            val views = RemoteViews(context.packageName, R.layout.widget_layout)
            // Aquí puedes configurar acciones específicas para el widget
            appWidgetManager.updateAppWidget(widgetId, views)
        }
    }
}
