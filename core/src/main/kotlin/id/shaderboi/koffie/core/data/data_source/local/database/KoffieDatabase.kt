package id.shaderboi.koffie.core.data.data_source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import id.shaderboi.koffie.core.data.data_source.local.database.converter.ProductConverter
import id.shaderboi.koffie.core.data.data_source.local.database.dao.CartDao
import id.shaderboi.koffie.core.data.data_source.local.database.entities.CartItemEntity

@Database(entities = [CartItemEntity::class], version = 0)
@TypeConverters(ProductConverter::class)
abstract class KoffieDatabase : RoomDatabase() {
    abstract val cartDao: CartDao

    companion object {
        const val NAME = "koffie"
    }
}