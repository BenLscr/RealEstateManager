package com.openclassrooms.realestatemanager.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import com.openclassrooms.realestatemanager.database.AppDatabase
import com.openclassrooms.realestatemanager.models.*

class AppContentProvider : ContentProvider() {

    companion object {
        val AUTHORITY: String = "com.openclassrooms.realestatemanager.provider"
        val TABLE_NAME_PROPERTY: String? = Property::class.simpleName
        val URI_PROPERTY: Uri = Uri.parse("content://$AUTHORITY/$TABLE_NAME_PROPERTY")
        val TABLE_NAME_ADDRESS: String? = Address::class.simpleName
        val URI_ADDRESS: Uri = Uri.parse("content://$AUTHORITY/$TABLE_NAME_ADDRESS")
        val TABLE_NAME_AGENT: String? = Agent::class.simpleName
        val URI_AGENT: Uri = Uri.parse("content://$AUTHORITY/$TABLE_NAME_AGENT")
    }

    override fun onCreate(): Boolean { return true }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        if (context != null ) {
            val propertyId: Long = ContentUris.parseId(uri)
            val cursor: Cursor = AppDatabase.getInstance(context).propertyDao().getPropertyWithCursor(propertyId.toInt())
            cursor.setNotificationUri(context.contentResolver, uri)
            return cursor
        }
        throw IllegalArgumentException("Failed to query row for uri $uri")
    }

    override fun getType(uri: Uri): String? {
        return "vnd.android.cursor.property/$AUTHORITY.$TABLE_NAME_PROPERTY"
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        if (context != null && values != null){
            var id: Long = 0
            if (values.containsKey("path")) {
                id = AppDatabase.getInstance(context).addressDao().insertAddress(Address.fromContentValues(values))
            }
            if (values.containsKey("name") && values.containsKey("firstName")) {
                id = AppDatabase.getInstance(context).agentDao().insertAgent(Agent.fromContentValues(values))
            }
            if (values.containsKey("type")) {
                id = AppDatabase.getInstance(context).propertyDao().insertProperty(Property.fromContentValues(values))
            }
            if (values.containsKey("propertyId") && values.containsKey("locationOfInterestId")) {
                id = AppDatabase.getInstance(context).compositionPropertyAndLocationOfInterestDao()
                        .insertLocationOfInterest(CompositionPropertyAndLocationOfInterest.fromContentValues(values))
            }
            if (values.containsKey("propertyId") && values.containsKey("propertyPhotoId")) {
                id = AppDatabase.getInstance(context).compositionPropertyAndPropertyPhotoDao()
                        .insertPropertyPhoto(CompositionPropertyAndPropertyPhoto.fromContentValues(values))
            }
            if (values.containsKey("isThisTheIllustration")) {
                id = AppDatabase.getInstance(context).propertyPhotoDao()
                        .insertPropertyPhoto(PropertyPhoto.fromContentValues(values))
            }
            if (id != 0.toLong()){
                context.contentResolver.notifyChange(uri, null)
                return ContentUris.withAppendedId(uri, id)
            }
        }
        throw IllegalArgumentException("Failed to insert row into $uri")
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        if (context != null && values != null){
            var count = 0
            if (values.containsKey("path")) {
                count = AppDatabase.getInstance(context).addressDao().updateAddress(Address.fromContentValues(values))
            }
            if (values.containsKey("name") && values.containsKey("firstName")) {
                count = AppDatabase.getInstance(context).agentDao().updateAgent(Agent.fromContentValues(values))
            }
            if (values.containsKey("type")) {
                count = AppDatabase.getInstance(context).propertyDao().updateProperty(Property.fromContentValues(values))
            }
            if (values.containsKey("propertyId") && values.containsKey("locationOfInterestId")) {
                count = AppDatabase.getInstance(context).compositionPropertyAndLocationOfInterestDao()
                        .updateCompositionPropertyAndLocationOfInterest(CompositionPropertyAndLocationOfInterest.fromContentValues(values))
            }
            if (values.containsKey("propertyId") && values.containsKey("propertyPhotoId")) {
                count = AppDatabase.getInstance(context).compositionPropertyAndPropertyPhotoDao()
                        .updateCompositionPropertyAndPropertyPhoto(CompositionPropertyAndPropertyPhoto.fromContentValues(values))
            }
            if (values.containsKey("isThisTheIllustration")) {
                count = AppDatabase.getInstance(context).propertyPhotoDao().updatePropertyPhoto(PropertyPhoto.fromContentValues(values))
            }
            context.contentResolver.notifyChange(uri, null)
            return count
        }
        throw IllegalArgumentException("Failed to update row into $uri")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        throw IllegalArgumentException("Failed to delete row into $uri.")
    }

}