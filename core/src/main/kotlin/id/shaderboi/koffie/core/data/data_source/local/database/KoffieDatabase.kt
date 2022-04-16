package id.shaderboi.koffie.core.data.data_source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import id.shaderboi.koffie.core.data.data_source.local.database.dao.CartDao
import id.shaderboi.koffie.core.data.data_source.local.database.entities.CartItemEntity

@Database(entities = [CartItemEntity::class], version = 0)
abstract class KoffieDatabase: RoomDatabase() {
    abstract val cartDao: CartDao
}