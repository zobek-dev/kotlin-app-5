package com.example.alejandra_alvarez_20241212_herpm

import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService

class ArtWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return ArtRemoteViewsFactory(this.applicationContext)
    }

    class ArtRemoteViewsFactory(private val context: android.content.Context) : RemoteViewsFactory {

        private val images = listOf(
            R.drawable.art1,
            R.drawable.art2,
            R.drawable.art3,
            R.drawable.art4,
            R.drawable.art5
        )

        override fun onCreate() {
            // No se necesita inicialización para imágenes estáticas
        }

        override fun onDataSetChanged() {
            // No hay datos dinámicos para actualizar
        }

        override fun onDestroy() {
            // Limpieza si es necesario
        }

        override fun getCount(): Int {
            return images.size
        }

        override fun getViewAt(position: Int): RemoteViews {
            val remoteViews = RemoteViews(context.packageName, R.layout.widget_item)
            remoteViews.setImageViewResource(R.id.widgetImageView, images[position])

            // Intent para manejar clics en los elementos del widget (si es necesario)
            val fillInIntent = Intent()
            remoteViews.setOnClickFillInIntent(R.id.widgetImageView, fillInIntent)

            return remoteViews
        }

        override fun getLoadingView(): RemoteViews? {
            return null
        }

        override fun getViewTypeCount(): Int {
            return 1 // Todos los elementos tienen el mismo diseño
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun hasStableIds(): Boolean {
            return true
        }
    }
}
